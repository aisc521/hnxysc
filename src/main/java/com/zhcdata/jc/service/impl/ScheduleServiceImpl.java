package com.zhcdata.jc.service.impl;

import java.util.Date;

import com.fasterxml.jackson.databind.JavaType;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.mapper.TbJcMatchLineupMapper;
import com.zhcdata.db.mapper.TbSclassMapper;
import com.zhcdata.db.mapper.TbScoreMapper;
import com.zhcdata.db.model.JcMatchLineupInfo;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.db.model.SclassInfo;
import com.zhcdata.jc.dto.*;
import com.zhcdata.jc.enums.RedisCodeMsg;
import com.zhcdata.jc.service.ScheduleService;
import com.zhcdata.jc.tools.Const;
import com.zhcdata.jc.tools.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.mapper.JsonMapper;
import org.springside.modules.utils.time.ClockUtil;
import org.springside.modules.utils.time.DateFormatUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/16 11:14
 */
@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Resource
    private ScheduleMapper scheduleMapper;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private TbJcMatchLineupMapper matchLineupMapper;
    @Resource
    private TbScoreMapper scoreMapper;
    @Autowired
    private TbSclassMapper sclassMapper;

    @Override
    public Schedule queryScheduleById(Long idBet007) {
        return scheduleMapper.selectByPrimaryKey(Integer.parseInt(String.valueOf(idBet007)));
    }

    @Override
    public Map<String, Object> queryLineupDataByMatch(Long matchId) {
        Map<String, Object> map = new HashMap<>(3);
        //根据比赛id获取阵容
        JcMatchLineupInfo lineupInfo = matchLineupMapper.selectByPrimaryKey(matchId);
        if (lineupInfo != null) {
            //处理阵容数据
            List<LineupDataDto> infoList = processingLineupData(lineupInfo);
            //组装数据
            map.put("hostTeamLineUp", lineupInfo.getHomeArray());
            map.put("guestTeamLineUp", lineupInfo.getAwayArray());
            map.put("infoList", infoList);
        }
        map.putIfAbsent("hostTeamLineUp", "");
        map.putIfAbsent("guestTeamLineUp", "");
        map.putIfAbsent("infoList", Lists.newArrayList());
        //处理redis
        String key = RedisCodeMsg.SOCCER_LINEUP_DATA.getName() + ":" + matchId;
        redisUtils.hset(key, "data", JsonMapper.defaultMapper().toJson(map));
        String timeId = DateFormatUtil.formatDate(Const.YYYYMMDDHHMMSSSSS, ClockUtil.currentDate());
        redisUtils.hset(key, "TIME_ID", timeId, RedisCodeMsg.SOCCER_LINEUP_DATA.getSeconds());
        map.put("timeId", timeId);
        return map;
    }

    @Override
    public void updateLineupDataRedis(int before, int after) {
        List<Integer> matchIds = scheduleMapper.selectScheduleIdByTime(before, after);
        log.error("查询到的前后一天的比赛记录数为：{}", matchIds.size());
        for (Integer matchId : matchIds) {
            queryLineupDataByMatch(matchId.longValue());
        }
    }

    /**
     * 处理阵容数据
     *
     * @param lineupInfo 阵容对象
     * @return 返回阵容列表
     * @Author Yore
     * @Date 2019-09-17 14:35
     */
    private List<LineupDataDto> processingLineupData(JcMatchLineupInfo lineupInfo) {
        List<LineupDataDto> list = new ArrayList<>();
        String homeLineup = lineupInfo.getHomeLineup();
        if (Strings.isNotBlank(homeLineup)) {
            processingLineupString("1", "1", list, homeLineup);
        }
        String homeBackup = lineupInfo.getHomeBackup();
        if (Strings.isNotBlank(homeBackup)) {
            processingLineupString("1", "2", list, homeBackup);
        }
        String awayLineup = lineupInfo.getAwayLineup();
        if (Strings.isNotBlank(awayLineup)) {
            processingLineupString("2", "1", list, awayLineup);
        }
        String awayBackup = lineupInfo.getAwayBackup();
        if (Strings.isNotBlank(awayBackup)) {
            processingLineupString("2", "2", list, awayBackup);
        }
        return list;
    }

    /**
     * 拼接出场阵容对象并放入list
     *
     * @param flag   主客队 1.主队，2客队
     * @param type   首发替补  1 首发,2替补
     * @param list   阵容list
     * @param lineup 阵容数据
     * @Author Yore
     * @Date 2019-09-17 14:35
     */
    private void processingLineupString(String flag, String type, List<LineupDataDto> list, String lineup) {
        String[] split = lineup.split(";");
        for (String s : split) {
            String[] split1 = s.split(",");
            LineupDataDto dataDto = new LineupDataDto();
            //球员id
            dataDto.setPlayerId(Integer.parseInt(split1[0]));
            //球员号码
            dataDto.setPlayerNum(split1[3]);
            //球员名
            dataDto.setPlayerName(split1[1]);
            // 【位置】
            // 4列：
            // 0:守门员，1:后卫，2:后腰，3:中场，4:前锋
            // 5列：
            // 0:守门员，1:后卫，2:后腰，3:中场，4:前腰，5:前锋
            dataDto.setPlayerRole(split1[4]);
            //1 首发,2替补
            dataDto.setType(type);
            //1.主队，2客队
            dataDto.setFlag(flag);
            list.add(dataDto);
        }
    }


    @Override
    public void updateMatchAnalysis(int before, int after) {
        List<Integer> matchIds = scheduleMapper.selectScheduleIdByTime(before, after);
        log.error("查询到的前后一天的比赛记录数为：{}", matchIds.size());
        for (Integer matchId : matchIds) {
            try {
                matchAnalysisByType(matchId, null, null);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("比赛{}分析数据更新异常", matchId);
            }
        }
    }

    @Override
    public Map<String, Object> matchAnalysisByType(Integer matchId, String type, String select) {
        //获取比赛信息，包括主客队，赛事id，赛季
        Schedule schedule = scheduleMapper.selectByPrimaryKey(matchId);
        Map<String, Object> map = null;
        if (schedule == null) {
            return null;
        }
        //type 为空，更新缓存
        if (Strings.isBlank(type)) {
            long l0 = ClockUtil.currentTimeMillis();
            matchAnalysisType0(schedule);
            for (int i = 0; i < 2; i++) {
                long l1 = ClockUtil.currentTimeMillis();
                matchAnalysisType1(schedule, i + "");
                long l2 = ClockUtil.currentTimeMillis();
                matchAnalysisType2(schedule, i + "");
                long l3 = ClockUtil.currentTimeMillis();
                matchAnalysisType3(schedule, i + "");
                long l4 = ClockUtil.currentTimeMillis();
                matchAnalysisType4(schedule, i + "");
                long l5 = ClockUtil.currentTimeMillis();
                matchAnalysisType5(schedule, i + "");
                long l6 = ClockUtil.currentTimeMillis();
                matchAnalysisType6(schedule, i + "");
                long l = ClockUtil.currentTimeMillis();
                log.error("赛事{}分析数据总计算耗时{}ms", matchId, l - l0);
            }
            return null;
        }
        switch (type) {
            case "0":
                map = matchAnalysisType0(schedule);
                break;
            case "1":
                map = matchAnalysisType1(schedule, select);
                break;
            case "2":
                map = matchAnalysisType2(schedule, select);
                break;
            case "3":
                map = matchAnalysisType3(schedule, select);
                break;
            case "4":
                map = matchAnalysisType4(schedule, select);
                break;
            case "5":
                map = matchAnalysisType5(schedule, select);
                break;
            case "6":
                map = matchAnalysisType6(schedule, select);
                break;
            default:
                break;
        }
        return map;
    }

    /**
     * 查询所有数据
     *
     * @param schedule 赛事对象
     * @return
     */
    private Map<String, Object> matchAnalysisType0(Schedule schedule) {
        String type = "0";
        //赛季
        String season = schedule.getMatchseason();
        //赛事类型id
        Integer sclassid = schedule.getSclassid();
        //子联赛id
        Integer subSclassID = schedule.getSubsclassid();
        Date matchtime = schedule.getMatchtime();
        List<IntegralRankingDto> hostInfoList = new ArrayList<>();
        List<IntegralRankingDto> guestInfoList = new ArrayList<>();
        List<SclassInfo> sclassInfos = sclassMapper.querySClass(String.valueOf(sclassid));
        //联赛为true，杯赛为false
        //判断当前比赛是否是杯赛
        boolean flag = false;
        if (sclassInfos != null && sclassInfos.size() > 0) {
            flag = Short.parseShort("2") == sclassInfos.get(0).getKind();
        }

        //主队积分数据  积分排行
        Integer hometeamid = schedule.getHometeamid();
        //比赛数、胜负、进球、积分、排名、胜率
        if (flag) {
            Schedule beforeSchedule = scheduleMapper.queryLastNoCupMatchByTeam(hometeamid, schedule.getMatchtime());
            if (beforeSchedule != null) {
                sclassid = beforeSchedule.getSclassid();
                subSclassID = beforeSchedule.getSubsclassid();
            }
        }
        //总
        IntegralRankingDto homeTotalData = scoreMapper.queryIntegralRanking(sclassid, subSclassID, null, hometeamid, season);
        if (homeTotalData == null) {
            homeTotalData = new IntegralRankingDto();
        }
        //主
        IntegralRankingDto homeHomeData = scoreMapper.queryIntegralRanking(sclassid, subSclassID, 1, hometeamid, season);
        if (homeHomeData == null) {
            homeHomeData = new IntegralRankingDto();
        }
        //客
        IntegralRankingDto homeGuestData = scoreMapper.queryIntegralRanking(sclassid, subSclassID, 0, hometeamid, season);
        if (homeGuestData == null) {
            homeGuestData = new IntegralRankingDto();
        }

        //近六场
        IntegralRankingDto homeNearly6 = scheduleMapper.selectNearlyMatchData(hometeamid, matchtime, 6);
        hostInfoList.add(homeTotalData);
        hostInfoList.add(homeHomeData);
        hostInfoList.add(homeGuestData);
        hostInfoList.add(homeNearly6);
        //客队积分数据
        Integer guestteamid = schedule.getGuestteamid();
        //比赛数、胜负、进球、积分、排名、胜率
        if (flag) {
            Schedule beforeSchedule = scheduleMapper.queryLastNoCupMatchByTeam(guestteamid, schedule.getMatchtime());
            if (beforeSchedule != null) {
                sclassid = beforeSchedule.getSclassid();
                subSclassID = beforeSchedule.getSubsclassid();
            }
        }
        //总
        IntegralRankingDto guestTotalData = scoreMapper.queryIntegralRanking(sclassid, subSclassID, null, guestteamid, season);
        if (guestTotalData == null) {
            guestTotalData = new IntegralRankingDto();
        }
        //主
        IntegralRankingDto guestHomeData = scoreMapper.queryIntegralRanking(sclassid, subSclassID, 1, guestteamid, season);
        if (guestHomeData == null) {
            guestHomeData = new IntegralRankingDto();
        }
        //客
        IntegralRankingDto guestGuestData = scoreMapper.queryIntegralRanking(sclassid, subSclassID, 0, guestteamid, season);
        if (guestGuestData == null) {
            guestGuestData = new IntegralRankingDto();
        }
        //近六场
        IntegralRankingDto guestNearly6 = scheduleMapper.selectNearlyMatchData(guestteamid, matchtime, 6);
        guestInfoList.add(guestTotalData);
        guestInfoList.add(guestHomeData);
        guestInfoList.add(guestGuestData);
        guestInfoList.add(guestNearly6);

        //主队战绩
        List<HistoryMatchDto> homeHistory = scheduleMapper.selectHistoryMatchByTeam(hometeamid, null, null, matchtime, 10);
        TeamHistoryStatisticDto homeLately = processingHistoryMatchData(homeHistory);
        //客队战绩
        List<HistoryMatchDto> guestHistory = scheduleMapper.selectHistoryMatchByTeam(guestteamid, null, null, matchtime, 10);
        TeamHistoryStatisticDto guestLately = processingHistoryMatchData(guestHistory);

        //历史交锋
        List<HistoryMatchDto> historyMatchDtos = scheduleMapper.selectHistoryMatchByTwoTeam(hometeamid, guestteamid, matchtime, null, 6);
        TeamHistoryStatisticDto lately = processingHistoryMatchData(historyMatchDtos);

        //拼接数据
        //放入缓存
        Map<String, Object> resultMap = new HashMap<>(2);
        //积分信息
        resultMap.put("hostInfoList", hostInfoList);
        resultMap.put("guestInfoList", guestInfoList);
        //战绩信息
        resultMap.put("homeLatelyList", homeHistory);
        resultMap.put("guestLatelyList", guestHistory);
        resultMap.put("latelyList", historyMatchDtos);
        resultMap.put("homeLately", homeLately);
        resultMap.put("guestLately", guestLately);
        resultMap.put("Lately", lately);
        String key = RedisCodeMsg.SOCCER_ANALYSIS.getName() + ":" + schedule.getScheduleid();
        redisUtils.hset(key, type + "_0", JsonMapper.defaultMapper().toJson(resultMap));
        String timeId = DateFormatUtil.formatDate(Const.YYYYMMDDHHMMSSSSS, ClockUtil.currentDate());
        redisUtils.hset(key, type + "_0" + "_TIME_ID", timeId, RedisCodeMsg.SOCCER_ANALYSIS.getSeconds());
        resultMap.put("timeId", timeId);
        return resultMap;
    }

    /**
     * 查询同主客
     *
     * @param schedule 赛事对象
     * @return
     */
    private Map<String, Object> matchAnalysisType1(Schedule schedule, String select) {
        String type = "1";
        //子联赛id
        Integer hometeamid = schedule.getHometeamid();
        Integer guestteamid = schedule.getGuestteamid();
        Date matchtime = schedule.getMatchtime();
        //主队战绩
        int count = 10;
        if ("1".equals(select)) {
            count = 20;
        }
        List<HistoryMatchDto> homeHistory = scheduleMapper.selectHistoryMatchByTeam(hometeamid, "1", null, matchtime, count);
        TeamHistoryStatisticDto homeLately = processingHistoryMatchData(homeHistory);
        //客队战绩
        List<HistoryMatchDto> guestHistory = scheduleMapper.selectHistoryMatchByTeam(guestteamid, "2", null, matchtime, count);
        TeamHistoryStatisticDto guestLately = processingHistoryMatchData(guestHistory);

        //拼接数据
        //放入缓存
        Map<String, Object> resultMap = new HashMap<>(2);
        //战绩信息
        resultMap.put("homeLatelyList", homeHistory);
        resultMap.put("guestLatelyList", guestHistory);
        resultMap.put("homeLately", homeLately);
        resultMap.put("guestLately", guestLately);
        String key = RedisCodeMsg.SOCCER_ANALYSIS.getName() + ":" + schedule.getScheduleid();
        redisUtils.hset(key, type + "_" + select, JsonMapper.defaultMapper().toJson(resultMap));
        String timeId = DateFormatUtil.formatDate(Const.YYYYMMDDHHMMSSSSS, ClockUtil.currentDate());
        redisUtils.hset(key, type + "_" + select + "_TIME_ID", timeId, RedisCodeMsg.SOCCER_ANALYSIS.getSeconds());
        resultMap.put("timeId", timeId);
        return resultMap;
    }

    /**
     * 查询同赛事
     *
     * @param schedule 赛事对象
     * @return
     */
    private Map<String, Object> matchAnalysisType2(Schedule schedule, String select) {
        String type = "2";
        //子联赛id
        Integer sclassid = schedule.getSclassid();
        Integer hometeamid = schedule.getHometeamid();
        Integer guestteamid = schedule.getGuestteamid();
        Date matchtime = schedule.getMatchtime();
        //主队战绩
        int count = 10;
        if ("1".equals(select)) {
            count = 20;
        }
        List<HistoryMatchDto> homeHistory = scheduleMapper.selectHistoryMatchByTeam(hometeamid, null, sclassid, matchtime, count);
        TeamHistoryStatisticDto homeLately = processingHistoryMatchData(homeHistory);
        //客队战绩
        List<HistoryMatchDto> guestHistory = scheduleMapper.selectHistoryMatchByTeam(guestteamid, null, sclassid, matchtime, count);
        TeamHistoryStatisticDto guestLately = processingHistoryMatchData(guestHistory);

        //拼接数据
        //放入缓存
        Map<String, Object> resultMap = new HashMap<>(2);
        //战绩信息
        resultMap.put("homeLatelyList", homeHistory);
        resultMap.put("guestLatelyList", guestHistory);
        resultMap.put("homeLately", homeLately);
        resultMap.put("guestLately", guestLately);
        String key = RedisCodeMsg.SOCCER_ANALYSIS.getName() + ":" + schedule.getScheduleid();
        redisUtils.hset(key, type + "_" + select, JsonMapper.defaultMapper().toJson(resultMap));
        String timeId = DateFormatUtil.formatDate(Const.YYYYMMDDHHMMSSSSS, ClockUtil.currentDate());
        redisUtils.hset(key, type + "_" + select + "_TIME_ID", timeId, RedisCodeMsg.SOCCER_ANALYSIS.getSeconds());
        resultMap.put("timeId", timeId);
        return resultMap;
    }

    /**
     * 查询无同主客、同赛事
     *
     * @param schedule 赛事对象
     * @return
     */
    private Map<String, Object> matchAnalysisType3(Schedule schedule, String select) {
        String type = "3";
        //子联赛id
        Integer hometeamid = schedule.getHometeamid();
        Integer guestteamid = schedule.getGuestteamid();
        Date matchtime = schedule.getMatchtime();
        //主队战绩
        int count = 10;
        if ("1".equals(select)) {
            count = 20;
        }
        List<HistoryMatchDto> homeHistory = scheduleMapper.selectHistoryMatchByTeam(hometeamid, null, null, matchtime, count);
        TeamHistoryStatisticDto homeLately = processingHistoryMatchData(homeHistory);
        //客队战绩
        List<HistoryMatchDto> guestHistory = scheduleMapper.selectHistoryMatchByTeam(guestteamid, null, null, matchtime, count);
        TeamHistoryStatisticDto guestLately = processingHistoryMatchData(guestHistory);

        //拼接数据
        //放入缓存
        Map<String, Object> resultMap = new HashMap<>(2);
        //战绩信息
        resultMap.put("homeLatelyList", homeHistory);
        resultMap.put("guestLatelyList", guestHistory);
        resultMap.put("homeLately", homeLately);
        resultMap.put("guestLately", guestLately);
        String key = RedisCodeMsg.SOCCER_ANALYSIS.getName() + ":" + schedule.getScheduleid();
        redisUtils.hset(key, type, JsonMapper.defaultMapper().toJson(resultMap));
        String timeId = DateFormatUtil.formatDate(Const.YYYYMMDDHHMMSSSSS, ClockUtil.currentDate());
        redisUtils.hset(key, type + "_TIME_ID", timeId, RedisCodeMsg.SOCCER_ANALYSIS.getSeconds());
        resultMap.put("timeId", timeId);
        return resultMap;
    }

    /**
     * 查询同主客、同赛事
     *
     * @param schedule 赛事对象
     * @return
     */
    private Map<String, Object> matchAnalysisType4(Schedule schedule, String select) {
        String type = "4";
        //子联赛id
        Integer sclassid = schedule.getSclassid();
        Integer hometeamid = schedule.getHometeamid();
        Integer guestteamid = schedule.getGuestteamid();
        Date matchtime = schedule.getMatchtime();
        //主队战绩
        int count = 10;
        if ("1".equals(select)) {
            count = 20;
        }
        List<HistoryMatchDto> homeHistory = scheduleMapper.selectHistoryMatchByTeam(hometeamid, "1", sclassid, matchtime, count);
        TeamHistoryStatisticDto homeLately = processingHistoryMatchData(homeHistory);
        //客队战绩
        List<HistoryMatchDto> guestHistory = scheduleMapper.selectHistoryMatchByTeam(guestteamid, "2", sclassid, matchtime, count);
        TeamHistoryStatisticDto guestLately = processingHistoryMatchData(guestHistory);

        //拼接数据
        //放入缓存
        Map<String, Object> resultMap = new HashMap<>(2);
        //战绩信息
        resultMap.put("homeLatelyList", homeHistory);
        resultMap.put("guestLatelyList", guestHistory);
        resultMap.put("homeLately", homeLately);
        resultMap.put("guestLately", guestLately);
        String key = RedisCodeMsg.SOCCER_ANALYSIS.getName() + ":" + schedule.getScheduleid();
        redisUtils.hset(key, type + "_" + select, JsonMapper.defaultMapper().toJson(resultMap));
        String timeId = DateFormatUtil.formatDate(Const.YYYYMMDDHHMMSSSSS, ClockUtil.currentDate());
        redisUtils.hset(key, type + "_" + select + "_TIME_ID", timeId, RedisCodeMsg.SOCCER_ANALYSIS.getSeconds());
        resultMap.put("timeId", timeId);
        return resultMap;
    }

    /**
     * 查询同主客历史交锋
     *
     * @param schedule 赛事对象
     * @return
     */
    private Map<String, Object> matchAnalysisType5(Schedule schedule, String select) {
        String type = "5";
        //子联赛id
        Integer hometeamid = schedule.getHometeamid();
        Integer guestteamid = schedule.getGuestteamid();
        Date matchtime = schedule.getMatchtime();
        //历史交锋
        int count = 6;
        if ("1".equals(select)) {
            count = 12;
        }
        List<HistoryMatchDto> historyMatchDtos = scheduleMapper.selectHistoryMatchByTwoTeam(hometeamid, guestteamid, matchtime, "1", count);
        TeamHistoryStatisticDto lately = processingHistoryMatchData(historyMatchDtos);

        //拼接数据
        //放入缓存
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("latelyList", historyMatchDtos);
        resultMap.put("Lately", lately);
        String key = RedisCodeMsg.SOCCER_ANALYSIS.getName() + ":" + schedule.getScheduleid();
        redisUtils.hset(key, type + "_" + select, JsonMapper.defaultMapper().toJson(resultMap));
        String timeId = DateFormatUtil.formatDate(Const.YYYYMMDDHHMMSSSSS, ClockUtil.currentDate());
        redisUtils.hset(key, type + "_" + select + "_TIME_ID", timeId, RedisCodeMsg.SOCCER_ANALYSIS.getSeconds());
        resultMap.put("timeId", timeId);
        return resultMap;
    }

    /**
     * 查询非同主客历史交锋
     *
     * @param schedule 赛事对象
     * @return
     */
    private Map<String, Object> matchAnalysisType6(Schedule schedule, String select) {
        String type = "6";
        //子联赛id
        Integer hometeamid = schedule.getHometeamid();
        Integer guestteamid = schedule.getGuestteamid();
        Date matchtime = schedule.getMatchtime();
        //历史交锋
        int count = 6;
        if ("1".equals(select)) {
            count = 12;
        }
        List<HistoryMatchDto> historyMatchDtos = scheduleMapper.selectHistoryMatchByTwoTeam(hometeamid, guestteamid, matchtime, null, count);
        TeamHistoryStatisticDto lately = processingHistoryMatchData(historyMatchDtos);
        //拼接数据
        //放入缓存
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("latelyList", historyMatchDtos);
        resultMap.put("Lately", lately);
        String key = RedisCodeMsg.SOCCER_ANALYSIS.getName() + ":" + schedule.getScheduleid();
        redisUtils.hset(key, type + "_" + select, JsonMapper.defaultMapper().toJson(resultMap));
        String timeId = DateFormatUtil.formatDate(Const.YYYYMMDDHHMMSSSSS, ClockUtil.currentDate());
        redisUtils.hset(key, type + "_" + select + "_TIME_ID", timeId, RedisCodeMsg.SOCCER_ANALYSIS.getSeconds());
        resultMap.put("timeId", timeId);
        return resultMap;
    }

    private TeamHistoryStatisticDto processingHistoryMatchData(List<HistoryMatchDto> list) {
        int winCount = 0;
        int flatCount = 0;
        int loseCount = 0;
        int goalCount = 0;
        int lostCount = 0;
        for (HistoryMatchDto historyMatchDto : list) {
            String hostScore = historyMatchDto.getHostScore();
            String guestScore = historyMatchDto.getGuestScore();
            String panlu = historyMatchDto.getPanlu();
            int hostScoreInt = Integer.parseInt(hostScore);
            int guestScoreInt = Integer.parseInt(guestScore);
            if (hostScoreInt == guestScoreInt) {
                flatCount++;
            } else if (hostScoreInt > guestScoreInt) {
                if ("1".equals(panlu)) {
                    winCount++;
                } else {
                    loseCount++;
                }
            } else {
                if ("2".equals(panlu)) {
                    winCount++;
                } else {
                    loseCount++;
                }
            }
            if ("1".equals(panlu)) {
                goalCount = goalCount + hostScoreInt;
                lostCount = lostCount + guestScoreInt;
            }
            if ("2".equals(panlu)) {
                goalCount = goalCount + guestScoreInt;
                lostCount = lostCount + hostScoreInt;
            }
        }
        TeamHistoryStatisticDto result = new TeamHistoryStatisticDto();
        result.setWin(String.valueOf(winCount));
        result.setFlat(String.valueOf(flatCount));
        result.setLose(String.valueOf(loseCount));
        result.setGoal(String.valueOf(goalCount));
        result.setLost(String.valueOf(lostCount));
        return result;
    }

    @Override
    public void sameOddsMatchCompute(String date) throws ParseException {
        //根据日志查询本日（今日11点到明日11点）所有比赛
        List<SameOddsDto> sameOddsDtos = scheduleMapper.selectMatchAndOdds(date);
        //计算几率、同赔火焰，并删除不符合条件的记录（同赔记录小于15条）
        processingProbability(sameOddsDtos);
        //判断今日是否周末
        Calendar instance = Calendar.getInstance();
        instance.setTime(DateFormatUtil.pareDate(Const.YYYY_MM_DD, date));
        int i = instance.get(Calendar.DAY_OF_WEEK);
        //1.0-1.5，1.5-2.0，2.0-2.7三个档次分别获取的个数,平时个数为3-4-3，周末为5-6-4
        int count1 = 3;
        int count2 = 4;
        int count3 = 3;
        int limit = 10;
        if (i == Calendar.SUNDAY || i == Calendar.SATURDAY) {
            count1 = 5;
            count2 = 6;
            count3 = 4;
            limit = 15;
        }
        //循环判断比赛放入各部分list集合
        List<SameOddsDto> jcList = new ArrayList<>();
        List<SameOddsDto> bdList = new ArrayList<>();
        List<SameOddsDto> zcList = new ArrayList<>();
        List<SameOddsDto> allList = new ArrayList<>();
        processingSameOddsDtoGroup(count1, "1.0", "1.5", sameOddsDtos, jcList, bdList, zcList, allList);
        processingSameOddsDtoGroup(count2, "1.5", "2.0", sameOddsDtos, jcList, bdList, zcList, allList);
        processingSameOddsDtoGroup(count3, "2.0", "2.7", sameOddsDtos, jcList, bdList, zcList, allList);
        //list排序
        allList.sort((o1, o2) -> new BigDecimal(o2.getTpeiWinOdds()).compareTo(new BigDecimal(o1.getTpeiWinOdds())));
        jcList.sort((o1, o2) -> new BigDecimal(o2.getTpeiWinOdds()).compareTo(new BigDecimal(o1.getTpeiWinOdds())));
        bdList.sort((o1, o2) -> new BigDecimal(o2.getTpeiWinOdds()).compareTo(new BigDecimal(o1.getTpeiWinOdds())));
        zcList.sort((o1, o2) -> new BigDecimal(o2.getTpeiWinOdds()).compareTo(new BigDecimal(o1.getTpeiWinOdds())));

        log.error("【生成同赔精选】日期{}生成同赔精选，目前同赔记录不小于15条的竞彩列表比赛数：{},北单列表比赛数：{},足彩列表比赛数：{},全部列表比赛数：{},今日截取比赛数为：{}",
                date, jcList.size(), bdList.size(), zcList.size(), allList.size(), limit);
        //截取需要的比赛
        if (limit < jcList.size()) {
            jcList = jcList.subList(0, limit);
        }
        if (limit < bdList.size()) {
            bdList = bdList.subList(0, limit);
        }
        if (limit < zcList.size()) {
            zcList = zcList.subList(0, limit);
        }

        //设置缓存
        String key = RedisCodeMsg.SOCCER_SAME_ODDS_MATCH.getName() + ":" + date;
        String timeId = DateFormatUtil.formatDate(Const.YYYYMMDDHHMMSSSSS, ClockUtil.currentDate());
        long seconds = RedisCodeMsg.SOCCER_SAME_ODDS_MATCH.getSeconds();
        String type = "0";
        String s = processingSameOddsTypeToLottery(type);
        String item = type + "_" + s;
        redisUtils.hset(key, item, JsonMapper.defaultMapper().toJson(allList));
        redisUtils.hset(key, item + "_TIME_ID", timeId, seconds);
        type = "1";
        s = processingSameOddsTypeToLottery(type);
        item = type + "_" + s;
        redisUtils.hset(key, item, JsonMapper.defaultMapper().toJson(jcList));
        redisUtils.hset(key, item + "_TIME_ID", timeId, seconds);
        type = "2";
        s = processingSameOddsTypeToLottery(type);
        item = type + "_" + s;
        redisUtils.hset(key, item, JsonMapper.defaultMapper().toJson(bdList));
        redisUtils.hset(key, item + "_TIME_ID", timeId, seconds);
        type = "3";
        s = processingSameOddsTypeToLottery(type);
        item = type + "_" + s;
        redisUtils.hset(key, item, JsonMapper.defaultMapper().toJson(zcList));
        redisUtils.hset(key, item + "_TIME_ID", timeId, seconds);
    }


    @Override
    public void updateSameOddsMatchData(String date) {
        String key = RedisCodeMsg.SOCCER_SAME_ODDS_MATCH.getName() + ":" + date;
        JsonMapper jsonMapper = JsonMapper.defaultMapper();
        JavaType javaType = jsonMapper.buildCollectionType(List.class, SameOddsDto.class);
        for (int i = 0; i < 4; i++) {
            String type = i + "";
            String s = processingSameOddsTypeToLottery(type);
            String item = type + "_" + s;
            String redisData = (String) redisUtils.hget(key, item);
            if (Strings.isNotBlank(redisData)) {
                List<SameOddsDto> list = jsonMapper.fromJson(redisData, javaType);
                for (SameOddsDto sameOddsDto : list) {
                    Schedule schedule = scheduleMapper.selectByPrimaryKey(Integer.parseInt(sameOddsDto.getMatchId()));
                    if (schedule != null) {
                        changeSameOddsMatchInfo(sameOddsDto, schedule);
                    }
                }
                long seconds = RedisCodeMsg.SOCCER_SAME_ODDS_MATCH.getSeconds();
                redisUtils.hset(key, item, jsonMapper.toJson(list));
                String timeId = DateFormatUtil.formatDate(Const.YYYYMMDDHHMMSSSSS, ClockUtil.currentDate());
                redisUtils.hset(key, item + "_TIME_ID", timeId, seconds);
            }
        }
    }


    /**
     * 筛选同赔精选比赛
     *
     * @param maxCount     本类需要记录数
     * @param startOdds    本类开始赔率
     * @param endOdds      本类结束赔率
     * @param sameOddsDtos 未筛选的同赔比赛记录
     * @param jcList       筛选后的竞彩同赔记录
     * @param bdList       筛选后的北单同赔记录
     * @param zcList       筛选后的足彩同赔记录
     * @param allList      筛选后的全部同赔记录
     */
    private void processingSameOddsDtoGroup(int maxCount, String startOdds, String endOdds,
                                            List<SameOddsDto> sameOddsDtos, List<SameOddsDto> jcList,
                                            List<SameOddsDto> bdList, List<SameOddsDto> zcList, List<SameOddsDto> allList) {
        //记录全部list已存放记录数
        int allCount = 0;
        //是否需要向全部list中放数据
        boolean flag = true;
        for (int i = 1; i <= 4; i++) {
            //记录各子类型存放记录数
            int count = 0;
            for (SameOddsDto sameOddsDto : sameOddsDtos) {
                if (String.valueOf(i).equals(sameOddsDto.getLotteryType())) {
                    Double originalOdds;
                    //flag 为0，获取主队胜赔率否则获取主队负赔率
                    if (0 == sameOddsDto.getFlag()) {
                        originalOdds = sameOddsDto.getTpeiWinHandicap();
                    } else {
                        originalOdds = sameOddsDto.getTpeilLoseHandicap();
                    }
                    //判断是否在赔率范围内
                    if (new BigDecimal(originalOdds).compareTo(new BigDecimal(startOdds)) >= 0 &&
                            new BigDecimal(originalOdds).compareTo(new BigDecimal(endOdds)) <= 0) {
                        //如果类型为1 为竞彩
                        if (i == 1) {
                            sameOddsDto.setMatchType("1");
                            //设置期次文字
                            sameOddsDto.setWeekNum(sameOddsDto.getNoId());
                            if (!jcList.contains(sameOddsDto)) {
                                jcList.add(sameOddsDto);
                                count++;
                            }
                            //如果类型为2 为北单
                        } else if (i == 2) {
                            sameOddsDto.setMatchType("2");
                            //设置期次文字
                            sameOddsDto.setIssueBD(sameOddsDto.getIssueNum());
                            sameOddsDto.setNum(sameOddsDto.getNoId());
                            if (!bdList.contains(sameOddsDto)) {
                                bdList.add(sameOddsDto);
                                count++;
                            }
                            //如果类型为3 为足彩
                        } else if (i == 3) {
                            sameOddsDto.setMatchType("3");
                            //设置期次文字
                            sameOddsDto.setIssueZC(sameOddsDto.getIssueNum());
                            sameOddsDto.setNum(sameOddsDto.getNoId());
                            if (!zcList.contains(sameOddsDto)) {
                                zcList.add(sameOddsDto);
                                count++;
                            }
                        }
                        //否则为外围
                        //如果本类已经满足条件，进入下次类型
                        if (count >= maxCount) {
                            continue;
                        }
                        if (flag) {
                            //如果当前比赛不存在AllList，向Alllist中添加
                            if (!allList.contains(sameOddsDto)) {
                                allList.add(sameOddsDto);
                                allCount++;
                                //如果已经满足，则不需要再向AllList中添加数据
                                if (allCount >= maxCount) {
                                    flag = false;
                                    if (4 == i) {
                                        break;
                                    }
                                }
                            }
                        } else {
                            //如果AllList中数据已满并且竞彩、北单、足彩已跑完数据，结束本次循环
                            if (i == 4) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * 计算几率，返回同配数据不够15条的记录
     *
     * @param list
     * @return
     */
    private void processingProbability(List<SameOddsDto> list) {
        List<SameOddsDto> removeList = new ArrayList<>();
        for (SameOddsDto sameOddsDto : list) {
            SameOddsStatisticDto statisticDto = scheduleMapper.selectSameOddsStatistic(sameOddsDto);
            int total = statisticDto.getTotal();
            if (total < 15) {
                removeList.add(sameOddsDto);
            } else {
                changeSameOddsMatchInfo(sameOddsDto, null);
                if (Integer.parseInt(sameOddsDto.getMatchState()) >= 4) {
                    removeList.add(sameOddsDto);
                    continue;
                }
                //默认全部无火，判断概率后赋值
                sameOddsDto.setFireFlagWin(0);
                sameOddsDto.setFireFlagFlat(0);
                sameOddsDto.setFireFlagLose(0);
                processingOdds(sameOddsDto, statisticDto);
            }
        }

        list.removeAll(removeList);
    }

    private void changeSameOddsMatchInfo(SameOddsDto dto, Schedule schedule) {
        //如果存在schedule对象，则使用，否则使用SameOddsDto的数据
        String matchState = dto.getMatchState();
        Date matchTime1 = dto.getMatchTime1();
        Date matchTime2 = dto.getMatchTime2();
        String matchMinute = null;
        String text = "未";
        if (null != schedule) {
            matchState = String.valueOf(schedule.getMatchstate());
            matchTime1 = schedule.getMatchtime();
            try {
                String matchtime2 = schedule.getMatchtime2();
                if (Strings.isNotBlank(matchtime2)) {
                    matchTime2 = DateFormatUtil.pareDate("yyyy-MM-dd HH:mm:ss", matchtime2);
                }
            } catch (ParseException e) {
                log.error("半场比赛时间转换错误");
                e.printStackTrace();
            }

            dto.setHomeHalfScore(schedule.getHomehalfscore() == null ? null : String.valueOf(schedule.getHomehalfscore()));
            dto.setGuestHalfScore(schedule.getGuesthalfscore() == null ? null : String.valueOf(schedule.getGuesthalfscore()));
            dto.setHomeRedCard(schedule.getHomeRed() == null ? null : String.valueOf(schedule.getHomeRed()));
            dto.setGuestRedCard(schedule.getGuestRed() == null ? null : String.valueOf(schedule.getGuestRed()));
            dto.setHomeYlwCard(schedule.getHomeYellow() == null ? null : String.valueOf(schedule.getHomeYellow()));
            dto.setGuestYlwCard(schedule.getGuestYellow() == null ? null : String.valueOf(schedule.getGuestYellow()));
            dto.setHomeScore(schedule.getHomescore() == null ? null : String.valueOf(schedule.getHomescore()));
            dto.setGuestScore(schedule.getGuestscore() == null ? null : String.valueOf(schedule.getGuestscore()));
        }
        //格式化比赛时间
        String matchTime = DateFormatUtil.formatDate("HH:mm", matchTime1);
        if ("0".equals(matchState)) {
            //未开场
            matchState = "0";
        } else if ("1".equals(matchState)) {
            //上半场
            matchState = "1";
            if (matchTime2 != null) {
                //计算上半场进行的时长 matchTime2为半场开始时间
                long l = ClockUtil.currentTimeMillis();
                long time2 = matchTime2.getTime();
                long minute = (l - time2) / 1000 / 60;
                text = minute + "'";
                matchMinute = minute + "";
            } else {
                text = "1'";
                matchMinute = "1";
            }
        } else if ("2".equals(matchState)) {
            //中场
            matchState = "2";
            text = "中";
        } else if ("3".equals(matchState)) {
            //下半场
            matchState = "3";
            if (matchTime2 != null) {
                //计算下半场进行的时长 matchTime2为半场开始时间，在计算的时间上+45分钟
                long l = ClockUtil.currentTimeMillis();
                long time2 = matchTime2.getTime();
                long minute = (l - time2) / 1000 / 60 + 45;
                text = minute + "'";
                matchMinute = minute + "";
            } else {
                text = "46'";
                matchMinute = "46";
            }
        } else if ("4".equals(matchState)) {
            //加时
            matchState = "-1";
            text = "(完)";
            matchMinute = "90";
        } else if ("5".equals(matchState)) {
            //点球
            matchState = "-1";
            text = "(完)";
            matchMinute = "90";
        } else if ("-1".equals(matchState)) {
            //完结
            matchState = "-1";
            text = "完";
        } else if ("-14".equals(matchState)) {
            //推迟
            text = "推迟";
            matchState = "0";
        } else if ("-12".equals(matchState)) {
            //腰斩
            matchState = "0";
            text = "腰斩";
        } else if ("-13".equals(matchState)) {
            //中断
            matchState = "-13";
            text = "腰斩";
        } else if ("-10".equals(matchState)) {
            //取消
            matchState = "0";
            text = "取消";
        } else if ("-11".equals(matchState)) {
            //待定
            matchState = "-11";
            text = "待定";
        }
        dto.setMatchState(matchState);
        dto.setMatchMinute(matchMinute);
        dto.setMatchTime(matchTime);
        dto.setText(text);
    }

    /**
     * 根据查询到的胜平负数据，计算概率，设置火焰值（同赔精选热度火焰  0无  2中  3大）
     *
     * @param sameOddsDto
     * @param statisticDto
     */
    private void processingOdds(SameOddsDto sameOddsDto, SameOddsStatisticDto statisticDto) {
        //同赔总数
        int total = statisticDto.getTotal();
        //同赔胜场数
        int winCount = statisticDto.getWinCount();
        //同赔平场数
        int flatCount = statisticDto.getFlatCount();
        //同赔负场数
        int loseCount = statisticDto.getLoseCount();
        //计算让球盘口后的胜场数
        int winPanCount = statisticDto.getWinPanCount();
        //计算让球盘口后的平场数
        int flatPanCount = statisticDto.getFlatPanCount();
        //计算让球盘口后的负场数
        int losePanCount = statisticDto.getLosePanCount();
        //胜、平、负 概率，保留2位小数
        // 胜 >= 5 进一
        BigDecimal winOdds = new BigDecimal(winCount).divide(new BigDecimal(total), 2, BigDecimal.ROUND_HALF_UP);
        sameOddsDto.setTpeiWinOdds(winOdds.toString());
        //平 >5 进一
        BigDecimal flatOdds = new BigDecimal(flatCount).divide(new BigDecimal(total), 2, BigDecimal.ROUND_HALF_DOWN);
        sameOddsDto.setTpeiFlatOdds(flatOdds.toString());
        //最后一个概率用1-胜概率-平概率
        BigDecimal loseOdds = BigDecimal.ONE.subtract(winOdds).subtract(flatOdds);
        sameOddsDto.setTpeilLoseOdds(loseOdds.toString());
//        //如果负概率减出来小于0，则概率为0，并且将胜、平中概率小的刨去负数概率
//        if (loseOdds.compareTo(BigDecimal.ZERO) < 0) {
//            sameOddsDto.setTpeilLoseOdds(BigDecimal.ZERO.toString());
//            if (winOdds.compareTo(flatOdds) < 0) {
//                winOdds = winOdds.add(loseOdds);
//            } else {
//                flatOdds = flatOdds.add(loseOdds);
//            }
//        }
        //减去让球后的胜、平、负 概率，保留2位小数
        Double goal = sameOddsDto.getFirstGoal();
        sameOddsDto.setTpanWinHandicap(goal);
        BigDecimal winPanOdds = new BigDecimal(winPanCount).divide(new BigDecimal(total), 2, BigDecimal.ROUND_HALF_UP);
        sameOddsDto.setTpanWinOdds(winPanOdds.toString());
        sameOddsDto.setTpanFlatHandicap(goal);
        BigDecimal flatPanOdds = new BigDecimal(flatPanCount).divide(new BigDecimal(total), 2, BigDecimal.ROUND_HALF_DOWN);
        sameOddsDto.setTpanFlatOdds(flatPanOdds.toString());
        sameOddsDto.setTpanLoseHandicap(goal);
        //最后一个概率用1-胜概率-平概率
        BigDecimal losePanOdds = BigDecimal.ONE.subtract(winPanOdds).subtract(flatPanOdds);
        sameOddsDto.setTpanLoseOdds(losePanOdds.toString());

        //若单项选择超过70%，则给这个打上标签。标签是长火
        if (winOdds.compareTo(new BigDecimal("0.7")) >= 0) {
            sameOddsDto.setFireFlagWin(3);
        } else if (flatOdds.compareTo(new BigDecimal("0.7")) >= 0) {
            sameOddsDto.setFireFlagFlat(3);
        } else if (loseOdds.compareTo(new BigDecimal("0.7")) >= 0) {
            sameOddsDto.setFireFlagLose(3);
        } else
            //若双项（胜平，胜负，平负）取max大于80%，则给这两个打上标签，其中几率较高的是长火，较低的短火.再做个判断如果几率都小于50%，则都是小火
            if (winOdds.add(flatOdds).compareTo(new BigDecimal("0.8")) >= 0) {
                if (winOdds.compareTo(flatOdds) >= 0) {
                    sameOddsDto.setFireFlagWin(3);
                    sameOddsDto.setFireFlagFlat(2);
                } else {
                    sameOddsDto.setFireFlagWin(2);
                    sameOddsDto.setFireFlagFlat(3);
                }
                if (winOdds.compareTo(new BigDecimal("0.5")) < 0 && flatOdds.compareTo(new BigDecimal("0.5")) < 0) {
                    sameOddsDto.setFireFlagWin(2);
                    sameOddsDto.setFireFlagFlat(2);
                }
            } else if (loseOdds.add(winOdds).compareTo(new BigDecimal("0.8")) >= 0) {
                if (winOdds.compareTo(loseOdds) >= 0) {
                    sameOddsDto.setFireFlagWin(3);
                    sameOddsDto.setFireFlagLose(2);
                } else {
                    sameOddsDto.setFireFlagWin(2);
                    sameOddsDto.setFireFlagLose(3);
                }
                if (winOdds.compareTo(new BigDecimal("0.5")) < 0 && loseOdds.compareTo(new BigDecimal("0.5")) < 0) {
                    sameOddsDto.setFireFlagWin(2);
                    sameOddsDto.setFireFlagLose(2);
                }
            } else if (loseOdds.add(flatOdds).compareTo(new BigDecimal("0.8")) >= 0) {
                if (flatOdds.compareTo(loseOdds) >= 0) {
                    sameOddsDto.setFireFlagFlat(3);
                    sameOddsDto.setFireFlagLose(2);
                } else {
                    sameOddsDto.setFireFlagFlat(2);
                    sameOddsDto.setFireFlagLose(3);
                }
                if (flatOdds.compareTo(new BigDecimal("0.5")) < 0 && loseOdds.compareTo(new BigDecimal("0.5")) < 0) {
                    sameOddsDto.setFireFlagFlat(2);
                    sameOddsDto.setFireFlagLose(2);
                }
            }
        //不满足以上条件的就不打标签

    }

    @Override
    public String processingSameOddsTypeToLottery(String type) {
        String lottery;
        if (Strings.isBlank(type)) {
            lottery = "all";
        } else {
            switch (type) {
                case "1":
                    lottery = "JCZQ";
                    break;
                case "2":
                    lottery = "BJDC";
                    break;
                case "3":
                    lottery = "SF14";
                    break;
                default:
                    lottery = "all";
            }
        }
        return lottery;
    }


    /**
     * 定时任务查询比赛列表
     *
     * @param startDate
     * @param endDate
     * @param type
     * @param userId
     * @param state
     * @return
     */
    @Override
    public List<MatchResult1> queryMacthListForJob(String startDate, String endDate, String type, String userId, String state,String issueNum,List<String> panKouType,List<String> matchType) {
        return scheduleMapper.queryMacthListForJob(startDate, endDate, type, userId, state,issueNum,panKouType,matchType);
    }

    @Override
    public String queryZcNum(String startDate, String endDate) {
        return scheduleMapper.queryZcNum(startDate, endDate);
    }

    @Override
    public String queryBdNum(String startDate, String endDate) {
        return scheduleMapper.queryBdNum(startDate, endDate);
    }

    @Override
    public PageInfo<MatchResult1> queryAttentionList(String userId, String pageNo, String pageAmount) {
        PageHelper.startPage(Integer.parseInt(pageNo), Integer.parseInt(pageAmount));
        List<MatchResult1> matchResult1s = scheduleMapper.queryAttentionList(Long.parseLong(userId));
        return new PageInfo<>(matchResult1s);
    }

    @Override
    public List<Integer> selectMatchIdExceedNow() {
        return scheduleMapper.selectMatchIdExceedNow();
    }

    @Override
    public List<DrawNoResult> queryList(String startDate) {
        return scheduleMapper.queryList(startDate);
    }

    @Override
    public List<DrawNoResult> queryIssueList(Integer issue) {
        return scheduleMapper.queryIssueList(issue);
    }

    @Override
    public IconAndTimeDto selectIconAndTime(Integer matchId) {
        return scheduleMapper.selectIconAndTime(matchId);
    }

    /*@Override
    public List<Integer> selectMatchIdExceedNowBd() {
        return scheduleMapper.selectMatchIdExceedNowBd();
    }*/

    @Override
    public MatchInfoForBdDto quertMatchInfo(Integer matchId) {
        return scheduleMapper.quertMatchInfo(matchId);
    }

    @Override
    public List<Schedule> queryMatchByStatus() {
        return scheduleMapper.queryMatchByStatus();
    }


    public static void main(String[] args) {
        BigDecimal flatOdds1 = new BigDecimal("6").divide(new BigDecimal("1000"), 2, BigDecimal.ROUND_HALF_DOWN);
        BigDecimal flatOdds2 = new BigDecimal("5").divide(new BigDecimal("1000"), 2, BigDecimal.ROUND_HALF_DOWN);
        BigDecimal flatOdds3 = new BigDecimal("6").divide(new BigDecimal("1000"), 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal flatOdds4 = new BigDecimal("5").divide(new BigDecimal("1000"), 2, BigDecimal.ROUND_HALF_UP);
        System.out.println("ROUND_HALF_DOWN："+flatOdds1.toString());
        System.out.println("ROUND_HALF_DOWN："+flatOdds2.toString());
        System.out.println("ROUND_HALF_UP："+flatOdds3.toString());
        System.out.println("ROUND_HALF_UP："+flatOdds4.toString());
    }

}
