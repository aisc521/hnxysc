package com.zhcdata.jc.service.impl;

import com.google.common.collect.Lists;
import com.zhcdata.db.mapper.EuropeOddsMapper;
import com.zhcdata.db.mapper.LetgoalMapper;
import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.jc.dto.AnalysisDto;
import com.zhcdata.jc.dto.AnalysisMatchDto;
import com.zhcdata.jc.dto.HandicapOddsDetailsResult;
import com.zhcdata.jc.dto.HandicapOddsResult;
import com.zhcdata.jc.enums.RedisCodeMsg;
import com.zhcdata.jc.service.HandicapOddsService;
import com.zhcdata.jc.tools.Const;
import com.zhcdata.jc.tools.FileUtils;
import com.zhcdata.jc.tools.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.mapper.JsonMapper;
import org.springside.modules.utils.time.ClockUtil;
import org.springside.modules.utils.time.DateFormatUtil;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/**
 * Title:
 * Description:盘口、赔率相关service
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/9/16 10:09
 */
@Slf4j
@Service
public class HandicapOddsServiceImpl implements HandicapOddsService {
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ScheduleMapper scheduleMapper;
    @Resource
    private EuropeOddsMapper europeOddsMapper;
    @Resource
    private LetgoalMapper letgoalMapper;

    @Value("${custom.qiutan.url.yuMing}")
    private String imagePrefix;
    @Value("${custom.qiutan.url.imageUrl}")
    private String imagUrl;
    @Value("${custom.qiutan.url.localUrl}")
    private String localUrl;

    private String[] types = {"1", "2", "3"};
    public static int[] OP_COM = {16, 80, 81, 82, 90, 104, 115, 158, 255, 281, 545, 1129};
    public static int[] OTHER_COM = {1,3,8,12,17,23,24,31};

    @Override
    public void updateHandicapOddsData(int count) {
        log.error("查询比赛");
        //根据条件查询比赛id
        List<Integer> list = scheduleMapper.selectScheduleIdByDate(count, 5);
        log.error("查询{}天前的比赛数量为{}", count, list.size());
        log.error("开始遍历比赛，查询数据");
        long l = ClockUtil.currentTimeMillis();
        //遍历比赛
        for (Integer matchId : list) {
            String key = RedisCodeMsg.SOCCER_HSET_ODDS_QT.getName() + ":" + matchId;
            boolean updateFlag = false;
            //遍历类型
            for (String type : types) {
                //获取对应赔率
                List<HandicapOddsResult> results = scheduleMapper.selectOddsResultsByMatchId(matchId, type);
                if (results != null && results.size() > 0) {
                    updateFlag = true;
                    //更新redis
                    updateRedis(key, type, results,RedisCodeMsg.SOCCER_HSET_ODDS_QT.getSeconds());
                }
            }
            if (updateFlag) {
                redisUtils.hset(key, "TIME_ID", DateFormatUtil.formatDate(Const.YYYYMMDDHHMMSSSSS, ClockUtil.currentDate()), RedisCodeMsg.SOCCER_HSET_ODDS_QT.getSeconds());
            }
        }
        long e = ClockUtil.currentTimeMillis();
        log.error("比赛数据计算完毕，总耗时为：{}ms", e - l);


    }

    @Override
    public void updateHandicapOddsDetailData(int count) {
        //根据条件查询比赛id
        List<Integer> list = scheduleMapper.selectScheduleIdByDate(count,5);
        for (Integer matchId : list) {
            //遍历比赛
            String key = RedisCodeMsg.SOCCER_ODDS_DETAIL.getName() + ":" + matchId;
            for (String type : types) {
                //遍历类型
                int[] coms = null;
                //根据类型获取博彩公司
                if ("1".equals(type)) {
                    coms = OP_COM;
                } else {
                    coms = OTHER_COM;
                }
                for (int comId : coms) {
                    //获取对应赔率
                    List<HandicapOddsDetailsResult> results = scheduleMapper.selectOddsResultDetailByMatchId(matchId, comId, type);
                    //更新redis
                    updateRedis(key, type + ":" + comId, results, RedisCodeMsg.SOCCER_ODDS_DETAIL.getSeconds());
                }
            }
        }
    }

    /**
     * 同初赔、初盘比赛数据
     * @param type TPEI 同赔   TPAN 同盘 非空
     * @param matchId 基础比赛id 非空
     * @param oddsCompany 查询指定公司赔率、盘口 非空
     * @param matchType 赛事类型 1全部 2五大联赛 2北单竞彩 4其他
     * @param beginDate 指定开始时间 可空
     * @param chgTimes  变盘次数 0，1，2，3，4+(传值为4)，全部-1（全部为空）
     * @param pam       低赔方初赔至终赔变化幅度差(绝对值)
     *                  同赔单选 1：0-0.1   2：0.1-0.3   3：0.3-0.5   4：0.5+   0：全部
     *                  同盘复选 变盘次数为0次时使用，复选时已竖线|分隔 1：0-0.05   2：0.05-0.1   3：0.1-0.2   4：0.2-0.3   5：0.3-0.5   6：0.5+   0：全部
     * @return
     */
    @Override
    public Map<String, Object> sameBeginOddsGoalMatch(String type, Integer matchId, String oddsCompany, Integer matchType, String beginDate, Integer chgTimes, String pam) {
        AnalysisDto result = null;
        Map<String, Object> map = new HashMap<>(25);
        Integer companyId = changeCompanyIdByParamType(type, oddsCompany);
        if ("TPEI".equalsIgnoreCase(type)) {
            //同赔分析
            //根据比赛id，查询欧赔对应公司的赔率
            Double startChange = getSameOddsStartChange(pam);
            Double endChange = getSameOddsEndChange(pam);
            result = europeOddsMapper.queryOddsByCompanyAndMatch(matchId, companyId, matchType, beginDate, startChange, endChange);
        } else {
            //同盘分析
            //根据比赛id，查询亚盘对应公司的赔率（澳彩）
            List<Map<String, Double>> change = getSameHandicapChange(chgTimes, pam);
            result = letgoalMapper.queryHandicapsByCompanyAndMatch(matchId, companyId);
            if (result != null) {
                Double satWin = result.getSatWin();
                Double satLose = result.getSatLose();
                if (change == null || change.size() == 0) {
                    List<AnalysisMatchDto> list = letgoalMapper.querySameHandicapsMatchByOdds(companyId, matchType, beginDate, chgTimes, result.getOddsId()
                            , satWin, satLose, result.getSatFlat());
                    result.setList(list);
                } else {
                    List<AnalysisMatchDto> list = letgoalMapper.querySameHandicapsMatchByChangeOdds(companyId, matchType, change, beginDate, result.getOddsId()
                            , satWin, satLose, result.getSatFlat());
                    result.setList(list);
                }
            } else {
                result = letgoalMapper.queryHandicapsBySchedule(matchId);
            }
        }
        if (result != null) {
            if (!redisUtils.sHasKey("SOCCER:TEAM_IMAGE",result.getHomeId())) {
                //主队图片
                String img = result.getHostIcon();
                if (Strings.isNotBlank(img)) {
                    String localUrl1 = localUrl + img;
                    File file = new File(localUrl1);
                    String parentStr = file.getParent();
                    File parent = new File(parentStr);
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    FileUtils.downloadPicture(imagUrl + img + "?win007=sell", localUrl1);
                    redisUtils.sAdd("SOCCER:TEAM_IMAGE", result.getHomeId());
                }
            }

            if (!redisUtils.sHasKey("SOCCER:TEAM_IMAGE",result.getGuestId())) {
                //客队图片
                String img = result.getGuestIcon();
                if (Strings.isNotBlank(img)) {
                    String localUrl1 = localUrl + img;
                    File file = new File(localUrl1);
                    String parentStr = file.getParent();
                    File parent = new File(parentStr);
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    FileUtils.downloadPicture(imagUrl + img + "?win007=sell", localUrl1);
                    redisUtils.sAdd("SOCCER:TEAM_IMAGE", result.getGuestId());
                }
            }
            map.put("hostIcon", Strings.isNotBlank(result.getHostIcon())?imagePrefix + "" + result.getHostIcon():"");
            map.put("guestIcon", Strings.isNotBlank(result.getGuestIcon())?imagePrefix + "" + result.getGuestIcon():"");
            map.put("hostName", result.getHostName());
            map.put("guestName", result.getGuestName());
            map.put("matchDate", result.getMatchDate());
            if ("TPEI".equalsIgnoreCase(type)) {
                map.put("satWin", result.getSatWin() == null? "0":result.getSatWin());
                map.put("satLose", result.getSatLose() == null? "0":result.getSatLose());
                map.put("satFlat", result.getSatFlat() == null? "0":result.getSatFlat());
            }
            List<AnalysisMatchDto> list = result.getList();
            map.put("totalCount", list == null ? 0 : list.size());
            map.put("matchTotal", list == null ? 0 : list.size());
            Integer winCount = getMatchCountByResult(list, 3);
            Integer flatCount = getMatchCountByResult(list, 1);
            Integer loseCount = getMatchCountByResult(list, 0);
            map.put("matchWin", winCount);
            map.put("matchLose", loseCount);
            map.put("matchFlat", flatCount);
            if (list == null || list.size() == 0) {
                map.put("ratioWin", "0");
                map.put("ratioLose", "0");
                map.put("ratioFlat", "0");
            } else {
                BigDecimal total = new BigDecimal(list.size());
                BigDecimal win = new BigDecimal(winCount);
                BigDecimal flat = new BigDecimal(flatCount);
                BigDecimal winOdds = win.divide(total, 2, BigDecimal.ROUND_HALF_UP);
                BigDecimal flatOdds = flat.divide(total, 2, BigDecimal.ROUND_HALF_UP);
                BigDecimal loseOdds = BigDecimal.ONE.subtract(winOdds).subtract(flatOdds);
                map.put("ratioWin", winOdds);
                map.put("ratioFlat", flatOdds);
                map.put("ratioLose", loseOdds);
            }
            map.put("list", list);
        }

        return map;
    }

    @Override
    public Map<String, Object> queryMatchDataByOdds(Double satSat, Double endEnd, String oddsCompany, Integer matchType, String beginDate, String pam,String zkFlag) {
        Map<String, Object> map = new HashMap<>(25);
        Integer companyId = changeCompanyIdByParamType("TPEI", oddsCompany);
        //同赔分析
        //根据比赛id，查询欧赔对应公司的赔率
        Double startChange = getSameOddsStartChange(pam);
        Double endChange = getSameOddsEndChange(pam);
        List<AnalysisMatchDto> list = europeOddsMapper.querySameOddsMatchByFlagAndOdds(companyId, matchType, startChange, endChange, beginDate, zkFlag, satSat, endEnd);

        Map<String, Object> winMap = new HashMap<>(25);
        Map<String, Object> flatMap = new HashMap<>(25);
        Map<String, Object> loseMap = new HashMap<>(25);

        map.put("totalCount", list == null ? 0 : list.size());
        Integer winCount = getMatchCountByResult(list, 3);
        Integer flatCount = getMatchCountByResult(list, 1);
        Integer loseCount = getMatchCountByResult(list, 0);
        map.put("mwin", winMap);
        map.put("mlose", flatMap);
        map.put("mflat", loseMap);
        winMap.put("mcount", winCount);
        flatMap.put("mcount", flatCount);
        loseMap.put("mcount", loseCount);
        if (list == null || list.size() == 0) {
            winMap.put("ratio", "0");
            flatMap.put("ratio", "0");
            loseMap.put("ratio", "0");
        } else {
            BigDecimal total = new BigDecimal(list.size());
            BigDecimal win = new BigDecimal(winCount);
            BigDecimal flat = new BigDecimal(flatCount);
            BigDecimal winOdds = win.divide(total, 2, BigDecimal.ROUND_HALF_UP);
            BigDecimal flatOdds = flat.divide(total, 2, BigDecimal.ROUND_HALF_UP);
            BigDecimal loseOdds = BigDecimal.ONE.subtract(winOdds).subtract(flatOdds);
            winMap.put("ratio", winOdds);
            flatMap.put("ratio", flatOdds);
            loseMap.put("ratio", loseOdds);
        }
        map.put("list", list);

        return map;
    }

    @Override
    public Map<String, Object> queryMatchDataByHandicap(Double satGoal, Double endGoal, String oddsCompany, Integer matchType, String beginDate, String pam, String satOdds, String endOdds, Integer changeTimes) {
        Map<String, Object> map = new HashMap<>(25);
        Integer companyId = changeCompanyIdByParamType("TPAN", oddsCompany);
        //同盘分析
        //根据比赛id，查询亚盘对应公司的赔率（澳彩）
        List<Map<String, Double>> change = getSameHandicapChange(changeTimes, pam);
        Double startSatOdds = getGoalWaterStartValue(satOdds);
        Double endSatOdds = getGoalWaterEndValue(satOdds);
        Double startEndOdds = getGoalWaterStartValue(endOdds);
        Double endEndOdds = getGoalWaterEndValue(endOdds);
        List<AnalysisMatchDto> list = null;
        if (change == null || change.size() == 0) {
            list = letgoalMapper.querySameHandicapsMatchByOddsAndNoChange(companyId, matchType,satGoal,endGoal, beginDate, changeTimes,
                    startSatOdds,endSatOdds,startEndOdds,endEndOdds);
        } else {
            list = letgoalMapper.querySameHandicapsMatchByOddsAndChange(companyId, matchType, satGoal, endGoal, change, beginDate, changeTimes,
                    startSatOdds, endSatOdds, startEndOdds, endEndOdds);
        }

        Map<String, Object> winMap = new HashMap<>(25);
        Map<String, Object> flatMap = new HashMap<>(25);
        Map<String, Object> loseMap = new HashMap<>(25);

        map.put("totalCount", list == null ? 0 : list.size());
        Integer winCount = getMatchCountByResult(list, 3);
        Integer flatCount = getMatchCountByResult(list, 1);
        Integer loseCount = getMatchCountByResult(list, 0);
        map.put("mwin", winMap);
        map.put("mlose", loseMap);
        map.put("mflat", flatMap);
        winMap.put("mcount", winCount);
        flatMap.put("mcount", flatCount);
        loseMap.put("mcount", loseCount);
        if (list == null || list.size() == 0) {
            winMap.put("ratio", "0");
            flatMap.put("ratio", "0");
            loseMap.put("ratio", "0");
        } else {
            BigDecimal total = new BigDecimal(list.size());
            BigDecimal win = new BigDecimal(winCount);
            BigDecimal flat = new BigDecimal(flatCount);
            BigDecimal winOdds = win.divide(total, 2, BigDecimal.ROUND_HALF_UP);
            BigDecimal flatOdds = flat.divide(total, 2, BigDecimal.ROUND_HALF_UP);
            BigDecimal loseOdds = BigDecimal.ONE.subtract(winOdds).subtract(flatOdds);
            winMap.put("ratio", winOdds);
            flatMap.put("ratio", flatOdds);
            loseMap.put("ratio", loseOdds);
        }
        map.put("list", list);
        return map;
    }

    /**
     *
     * @param key
     * @param type
     * @param results
     * @param time
     */
    private void updateRedis(String key, String type, List results,long time) {
        String json = JsonMapper.defaultMapper().toJson(results);
        String timeIdKey = type + "_" + "TIME_ID";
        String timeId = DateFormatUtil.formatDate(Const.YYYYMMDDHHMMSSSSS, ClockUtil.currentDate());
        redisUtils.hset(key, type, json);
        redisUtils.hset(key, timeIdKey, timeId);
        redisUtils.expire(key, time);
    }

    /**
     * 获取指定赛果个数
     * @param list      比赛集合
     * @param result    需要统计的赛果类型  3 胜 1平 0负
     * @return
     */
    private Integer getMatchCountByResult(List<AnalysisMatchDto> list, int result) {
        int count = 0;
        if (list != null) {
            for (AnalysisMatchDto matchDto : list) {
                if (matchDto.getResult() == result) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 同盘复选 变盘次数为0次时使用，复选时已竖线|分隔 1：0-0.05   2：0.05-0.1   3：0.1-0.2   4：0.2-0.3   5：0.3-0.5   6：0.5+   0：全部
     * @param changeTimes 变盘次数
     * @param pam
     * @return
     */
    private List<Map<String,Double>> getSameHandicapChange(Integer changeTimes,String pam) {
        List<String> list = Arrays.asList(pam.split("|"));
        //变盘次数不为0或选择项包含全部，或全选，返回空list
        if (0 != changeTimes || list.contains("") || list.contains("0") || list.size() == 6) {
            return Lists.newArrayList();
        }
        List<Map<String, Double>> result = new ArrayList<>();
        for (String s : list) {
            Map<String, Double> map = new HashMap<>(2);
            switch (s) {
                case "1":
                    map.put("start", 0D);
                    map.put("end", 0.05D);
                    break;
                case "2":
                    map.put("start", 0.05D);
                    map.put("end", 0.1D);
                    break;
                case "3":
                    map.put("start", 0.1D);
                    map.put("end", 0.2D);
                    break;
                case "4":
                    map.put("start", 0.2D);
                    map.put("end", 0.3D);
                    break;
                case "5":
                    map.put("start", 0.3D);
                    map.put("end", 0.5D);
                    break;
                case "6":
                    map.put("start", 0.5D);
                    break;
                default:
                    break;
            }
            result.add(map);
        }
        return result;
    }


    /**
     * 水位,0-7
     * 1：小于0.72
     * 2：0.73-0.84
     * 3：0.85-0.90
     * 4：0.91-0.99
     * 5：1.0-1.05
     * 6：1.06-1.18
     * 7：大于1.18
     * 0：全部
     * @param num
     * @return
     */
    private Double getGoalWaterStartValue(String num) {
        Double startChange = null;
        switch (num) {
            case "2":
                startChange = 0.73d;
                break;
            case "3":
                startChange = 0.85d;
                break;
            case "4":
                startChange = 0.91d;
                break;
            case "5":
                startChange = 1.0d;
                break;
            case "6":
                startChange = 1.06d;
                break;
            case "7":
                startChange = 1.18d;
                break;
            default:
                break;
        }
        return startChange;
    }

    /**
     * 水位,0-7
     * 1：小于0.72
     * 2：0.73-0.84
     * 3：0.85-0.90
     * 4：0.91-0.99
     * 5：1.0-1.05
     * 6：1.06-1.18
     * 7：大于1.18
     * 0：全部
     * @param num
     * @return
     */
    private Double getGoalWaterEndValue(String num) {
        Double endChange = null;
        switch (num) {
            case "1":
                endChange = 0.73d;
                break;
            case "2":
                endChange = 0.85d;
                break;
            case "3":
                endChange = 0.91d;
                break;
            case "4":
                endChange = 1.0d;
                break;
            case "5":
                endChange = 1.06d;
                break;
            case "6":
                endChange = 1.18d;
                break;
            default:
                break;
        }
        return endChange;
    }

    /**
     * 同赔单选 1：0-0.1   2：0.1-0.3   3：0.3-0.5   4：0.5+   5：全部
     * @param pam
     * @return
     */
    private Double getSameOddsStartChange(String pam) {
        Double startChange = null;
            switch (pam) {
                case "1":
                    startChange = 0D;
                    break;
                case "2":
                    startChange = 0.1D;
                    break;
                case "3":
                    startChange = 0.3D;
                    break;
                case "4":
                    startChange = 0.5D;
                    break;
                default:
                    break;
            }
        return startChange;
    }

    /**
     * 同赔单选 1：0-0.1   2：0.1-0.3   3：0.3-0.5   4：0.5+   5：全部
     * @param pam
     * @return
     */
    private Double getSameOddsEndChange(String pam) {
        Double endChange = null;
        switch (pam) {
            case "1":
                endChange = 0.1D;
                break;
            case "2":
                endChange = 0.3D;
                break;
            case "3":
                endChange = 0.5D;
                break;
            default:
                break;
        }
        return endChange;
    }


    private int changeCompanyIdByParamType(String type, String oddsCompany) {
        if ("TPAN".equalsIgnoreCase(type)) {
            //澳门
            return 1;
        } else {
            switch (oddsCompany) {
                case "1":
                    //BET365
                    return 281;
                case "2":
                    //威廉希尔
                    return 115;
                case "3":
                    //立博
                    return 82;
                case "4":
                    //bwin
                    return 255;
                case "5":
                    //竞彩
                    return 1129;
                default:
                    return 0;
            }
        }
    }
}
