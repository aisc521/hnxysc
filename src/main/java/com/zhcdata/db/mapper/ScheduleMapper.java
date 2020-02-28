package com.zhcdata.db.mapper;


import com.zhcdata.db.model.JcMatchLottery;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.dto.*;
import com.zhcdata.jc.tools.CustomInterfaceMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ScheduleMapper {

    int deleteByPrimaryKey(Integer scheduleid);

    int insert(Schedule record);

    int insertSelective(Schedule record);

    Schedule selectByPrimaryKey(Integer scheduleid);

    int updateByPrimaryKeySelective(Schedule record);

    int updateByPrimaryKeyWithBLOBs(Schedule record);

    int updateByPrimaryKey(Schedule record);

    List<Schedule> selectPastAndFutureNoEnd(@Param("past") String past, @Param("future") String future, @Param("i") String i);

    /**
     * 按日期查询指定天数之间的比赛Id
     *
     * @param before 指定天数之前
     * @param after  指定天数之后
     * @return
     */
    List<Integer> selectScheduleIdByDate(@Param("before") int before, @Param("after") int after);

    /**
     * 按时间查询指定天数之间的比赛Id
     *
     * @param before 指定天数之前
     * @param after  指定天数之后
     * @return
     */
    List<Integer> selectScheduleIdByTime(@Param("before") int before, @Param("after") int after);

    /**
     * 查询指定比赛，指定类型的公司赔率
     *
     * @param matchId 比赛id
     * @param type    1欧赔 2亚盘 3大小球
     * @return
     */
    List<HandicapOddsResult> selectOddsResultsByMatchId(@Param("matchId") Integer matchId, @Param("type") String type);

    /**
     * 查询指定比赛，指定类型，指定公司的赔率
     *
     * @param matchId   比赛id
     * @param companyId 公司id
     * @param type      1欧赔 2亚盘 3大小球
     * @return
     */
    List<HandicapOddsDetailsResult> selectOddsResultDetailByMatchId(@Param("matchId") Integer matchId, @Param("companyId") Integer companyId, @Param("type") String type);

    /**
     * 根据队伍id获取指定场次的统计数据
     *
     * @param teamId
     * @param count
     * @return
     */
    IntegralRankingDto selectNearlyMatchData(@Param("teamId") Integer teamId, @Param("matchTime") Date matchTime, @Param("count") int count);

    /**
     * 近期战绩 根据球队、赛事、主客获取对应个数比赛记录
     *
     * @param teamId   球队id
     * @param type     1 主队 2客队 其他为任意
     * @param sclassid 赛事类型id 为空不查
     * @param count    个数
     * @return
     */
    List<HistoryMatchDto> selectHistoryMatchByTeam(@Param("teamId") Integer teamId, @Param("type") String type,
                                                   @Param("sclassId") Integer sclassid, @Param("matchTime") Date matchTime,
                                                   @Param("count") int count);

    /**
     * 主客队历史交锋
     *
     * @param homeTeamId  主队id
     * @param guestTeamId 客队id
     * @param type        1同主客
     * @param count       查询记录数
     * @return
     */
    List<HistoryMatchDto> selectHistoryMatchByTwoTeam(@Param("homeTeamId") Integer homeTeamId,
                                                      @Param("guestTeamId") Integer guestTeamId,
                                                      @Param("matchTime") Date matchTime,
                                                      @Param("type") String type,
                                                      @Param("count") int count);

    /**
     * 根据日期查询比赛赔率数据-同赔精选使用
     *
     * @param date
     * @return
     */
    List<SameOddsDto> selectMatchAndOdds(String date);

    /**
     * 根据比赛赔率数据查询同赔、胜负数量
     *
     * @param sameOddsDto
     * @return
     */
    SameOddsStatisticDto selectSameOddsStatistic(SameOddsDto sameOddsDto);

    /**
     * 修改主客队角球数
     *
     * @param homeCorner      主队角球
     * @param homeCornerHalf  主队半场角球
     * @param guestCorner     客队角球
     * @param guestCornerHalf 客队半场角球
     * @param ScheduleID      赛程ID
     * @return
     */
    int updateByScheduleID(@Param("homeCorner") String homeCorner,
                           @Param("homeCornerHalf") String homeCornerHalf,
                           @Param("guestCorner") String guestCorner,
                           @Param("guestCornerHalf") String guestCornerHalf,
                           @Param("ScheduleID") String ScheduleID
    );

    List<MatchResult1> queryMacthListForJob(@Param("startTime") String startDate,
                                            @Param("endTime") String endDate,
                                            @Param("type") String type,
                                            @Param("userId") String userId,
                                            @Param("state") String state, @Param("issueNum") String issueNum,
                                            @Param("panKouTypeList") List<String> panKouType,
                                            @Param("matchTypeList") List<String> matchType);

    String queryZcNum(@Param("startDate") String startDate, @Param("endTime") String endDate);

    String queryBdNum(@Param("startDate") String startDate, @Param("endTime") String endDate);

    List<MatchResult1> queryAttentionList(@Param("userId") long userId);

    List<Integer> selectMatchIdExceedNow();

    List<DrawNoResult> queryList(@Param("startDate") String startDate);

    List<DrawNoResult> queryIssueList(@Param("issue") Integer issue);

    IconAndTimeDto selectIconAndTime(@Param("matchId") Integer matchId);

    List<Schedule> selectStatusChangedToday(@Param("sat") String sat, @Param("end") String end, @Param("now") String now);

    /* List<Integer> selectMatchIdExceedNowBd();
     */
    MatchInfoForBdDto quertMatchInfo(@Param("matchId") Integer matchId);

    List<Schedule> selectByStatusInTime(@Param("status") int status, @Param("sat") Date sat, @Param("end") Date end);

    List<JcMatchLottery> selectNextIssueNum();

    List<JcMatchLottery> selectLastIssueNum(@Param("issueNum") String issueNum);

    List<MatchResult1> selectNoFinishMatch();

    List<MatchResult1> selectNowIssueNum();

    List<Schedule> queryMatchByStatus();

    Schedule queryLastNoCupMatchByTeam(@Param("teamId") Integer teamId, @Param("date") Date matchTime);

    List<MatchResult1> queryMatchType(@Param("matchTime") String matchTime);
}