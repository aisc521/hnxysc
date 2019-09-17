package com.zhcdata.jc.service.impl;

import com.google.common.collect.Lists;
import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.mapper.TbJcMatchLineupMapper;
import com.zhcdata.db.mapper.TbScoreMapper;
import com.zhcdata.db.model.JcMatchLineupInfo;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.dto.HistoryMatchDto;
import com.zhcdata.jc.dto.IntegralRankingDto;
import com.zhcdata.jc.dto.LineupDataDto;
import com.zhcdata.jc.dto.TeamHistoryStatisticDto;
import com.zhcdata.jc.enums.RedisCodeMsg;
import com.zhcdata.jc.service.ScheduleService;
import com.zhcdata.jc.tools.Const;
import com.zhcdata.jc.tools.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.mapper.JsonMapper;
import org.springside.modules.utils.time.ClockUtil;
import org.springside.modules.utils.time.DateFormatUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void updateMatchAnalysis(int before, int after){
        List<Integer> matchIds = scheduleMapper.selectScheduleIdByTime(before, after);
        log.error("查询到的前后一天的比赛记录数为：{}", matchIds.size());
        for (Integer matchId : matchIds) {
            matchAnalysisByType(matchId, null);
        }
    }

    @Override
    public Map<String, Object> matchAnalysisByType(Integer matchId, String type) {
        //获取比赛信息，包括主客队，赛事id，赛季
        Schedule schedule = scheduleMapper.selectByPrimaryKey(matchId);
        Map<String, Object> map = null;
        //type 为空，更新缓存
        if (Strings.isBlank(type)) {
            matchAnalysisType0(schedule);
            matchAnalysisType1(schedule);
            matchAnalysisType2(schedule);
            matchAnalysisType3(schedule);
            matchAnalysisType4(schedule);
            matchAnalysisType5(schedule);
            matchAnalysisType6(schedule);
            return null;
        }
        switch (type) {
            case "0":
                map = matchAnalysisType0(schedule);
                break;
            case "1":
                map = matchAnalysisType1(schedule);
                break;
            case "2":
                map = matchAnalysisType2(schedule);
                break;
            case "3":
                map = matchAnalysisType3(schedule);
                break;
            case "4":
                map = matchAnalysisType4(schedule);
                break;
            case "5":
                map = matchAnalysisType5(schedule);
                break;
            case "6":
                map = matchAnalysisType6(schedule);
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
        List<IntegralRankingDto> hostInfoList = new ArrayList<>();
        List<IntegralRankingDto> guestInfoList = new ArrayList<>();
        //主队积分数据  积分排行
        //比赛数、胜负、进球、积分、排名、胜率
        //总
        Integer hometeamid = schedule.getHometeamid();
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
        IntegralRankingDto homeNearly6 = scheduleMapper.selectNearlyMatchData(hometeamid, 6);
        hostInfoList.add(homeTotalData);
        hostInfoList.add(homeHomeData);
        hostInfoList.add(homeGuestData);
        hostInfoList.add(homeNearly6);
        //客队积分数据
        //比赛数、胜负、进球、积分、排名、胜率
        //总
        Integer guestteamid = schedule.getGuestteamid();
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
        IntegralRankingDto guestNearly6 = scheduleMapper.selectNearlyMatchData(guestteamid, 6);
        guestInfoList.add(guestTotalData);
        guestInfoList.add(guestHomeData);
        guestInfoList.add(guestGuestData);
        guestInfoList.add(guestNearly6);

        //主队战绩
        List<HistoryMatchDto> homeHistory = scheduleMapper.selectHistoryMatchByTeam(hometeamid, null, null, 10);
        TeamHistoryStatisticDto homeLately = processingHistoryMatchData(homeHistory);
        //客队战绩
        List<HistoryMatchDto> guestHistory = scheduleMapper.selectHistoryMatchByTeam(guestteamid, null, null, 10);
        TeamHistoryStatisticDto guestLately = processingHistoryMatchData(guestHistory);

        //历史交锋
        List<HistoryMatchDto> historyMatchDtos = scheduleMapper.selectHistoryMatchByTwoTeam(hometeamid, guestteamid, null, 6);
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
        redisUtils.hset(key, type, JsonMapper.defaultMapper().toJson(resultMap));
        String timeId = DateFormatUtil.formatDate(Const.YYYYMMDDHHMMSSSSS, ClockUtil.currentDate());
        redisUtils.hset(key, type + "_TIME_ID", timeId, RedisCodeMsg.SOCCER_ANALYSIS.getSeconds());
        resultMap.put("timeId", timeId);
        return resultMap;
    }

    /**
     * 查询同主客
     *
     * @param schedule 赛事对象
     * @return
     */
    private Map<String, Object> matchAnalysisType1(Schedule schedule) {
        String type = "1";
        //子联赛id
        Integer hometeamid = schedule.getHometeamid();
        Integer guestteamid = schedule.getGuestteamid();
        //主队战绩
        List<HistoryMatchDto> homeHistory = scheduleMapper.selectHistoryMatchByTeam(hometeamid, "1", null, 10);
        TeamHistoryStatisticDto homeLately = processingHistoryMatchData(homeHistory);
        //客队战绩
        List<HistoryMatchDto> guestHistory = scheduleMapper.selectHistoryMatchByTeam(guestteamid, "2", null, 10);
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
     * 查询同赛事
     *
     * @param schedule 赛事对象
     * @return
     */
    private Map<String, Object> matchAnalysisType2(Schedule schedule) {
        String type = "2";
        //子联赛id
        Integer sclassid = schedule.getSclassid();
        Integer hometeamid = schedule.getHometeamid();
        Integer guestteamid = schedule.getGuestteamid();
        //主队战绩
        List<HistoryMatchDto> homeHistory = scheduleMapper.selectHistoryMatchByTeam(hometeamid, null, sclassid, 10);
        TeamHistoryStatisticDto homeLately = processingHistoryMatchData(homeHistory);
        //客队战绩
        List<HistoryMatchDto> guestHistory = scheduleMapper.selectHistoryMatchByTeam(guestteamid, null, sclassid, 10);
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
     * 查询无同主客、同赛事
     *
     * @param schedule 赛事对象
     * @return
     */
    private Map<String, Object> matchAnalysisType3(Schedule schedule) {
        String type = "3";
        //子联赛id
        Integer hometeamid = schedule.getHometeamid();
        Integer guestteamid = schedule.getGuestteamid();
        //主队战绩
        List<HistoryMatchDto> homeHistory = scheduleMapper.selectHistoryMatchByTeam(hometeamid, null, null, 10);
        TeamHistoryStatisticDto homeLately = processingHistoryMatchData(homeHistory);
        //客队战绩
        List<HistoryMatchDto> guestHistory = scheduleMapper.selectHistoryMatchByTeam(guestteamid, null, null, 10);
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
    private Map<String, Object> matchAnalysisType4(Schedule schedule) {
        String type = "4";
        //子联赛id
        Integer sclassid = schedule.getSclassid();
        Integer hometeamid = schedule.getHometeamid();
        Integer guestteamid = schedule.getGuestteamid();
        //主队战绩
        List<HistoryMatchDto> homeHistory = scheduleMapper.selectHistoryMatchByTeam(hometeamid, "1", sclassid, 10);
        TeamHistoryStatisticDto homeLately = processingHistoryMatchData(homeHistory);
        //客队战绩
        List<HistoryMatchDto> guestHistory = scheduleMapper.selectHistoryMatchByTeam(guestteamid, "2", sclassid, 10);
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
     * 查询同主客历史交锋
     *
     * @param schedule 赛事对象
     * @return
     */
    private Map<String, Object> matchAnalysisType5(Schedule schedule) {
        String type = "5";
        //子联赛id
        Integer hometeamid = schedule.getHometeamid();
        Integer guestteamid = schedule.getGuestteamid();
        //历史交锋
        List<HistoryMatchDto> historyMatchDtos = scheduleMapper.selectHistoryMatchByTwoTeam(hometeamid, guestteamid, "1", 5);
        TeamHistoryStatisticDto lately = processingHistoryMatchData(historyMatchDtos);

        //拼接数据
        //放入缓存
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("latelyList", historyMatchDtos);
        resultMap.put("Lately", lately);
        String key = RedisCodeMsg.SOCCER_ANALYSIS.getName() + ":" + schedule.getScheduleid();
        redisUtils.hset(key, type, JsonMapper.defaultMapper().toJson(resultMap));
        String timeId = DateFormatUtil.formatDate(Const.YYYYMMDDHHMMSSSSS, ClockUtil.currentDate());
        redisUtils.hset(key, type + "_TIME_ID", timeId, RedisCodeMsg.SOCCER_ANALYSIS.getSeconds());
        resultMap.put("timeId", timeId);
        return resultMap;
    }

    /**
     * 查询非同主客历史交锋
     *
     * @param schedule 赛事对象
     * @return
     */
    private Map<String, Object> matchAnalysisType6(Schedule schedule) {
        String type = "6";
        //子联赛id
        Integer hometeamid = schedule.getHometeamid();
        Integer guestteamid = schedule.getGuestteamid();
        //历史交锋
        List<HistoryMatchDto> historyMatchDtos = scheduleMapper.selectHistoryMatchByTwoTeam(hometeamid, guestteamid, null, 5);
        TeamHistoryStatisticDto lately = processingHistoryMatchData(historyMatchDtos);
        //拼接数据
        //放入缓存
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("latelyList", historyMatchDtos);
        resultMap.put("Lately", lately);
        String key = RedisCodeMsg.SOCCER_ANALYSIS.getName() + ":" + schedule.getScheduleid();
        redisUtils.hset(key, type, JsonMapper.defaultMapper().toJson(resultMap));
        String timeId = DateFormatUtil.formatDate(Const.YYYYMMDDHHMMSSSSS, ClockUtil.currentDate());
        redisUtils.hset(key, type + "_TIME_ID", timeId, RedisCodeMsg.SOCCER_ANALYSIS.getSeconds());
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

}
