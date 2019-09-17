package com.zhcdata.db.mapper;


import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.dto.HandicapOddsDetailsResult;
import com.zhcdata.jc.dto.HandicapOddsResult;
import com.zhcdata.jc.dto.HistoryMatchDto;
import com.zhcdata.jc.dto.IntegralRankingDto;
import com.zhcdata.jc.tools.CustomInterfaceMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ScheduleMapper {

    int deleteByPrimaryKey(Integer scheduleid);

    int insert(Schedule record);

    int insertSelective(Schedule record);

    Schedule selectByPrimaryKey(Integer scheduleid);

    int updateByPrimaryKeySelective(Schedule record);

    int updateByPrimaryKeyWithBLOBs(Schedule record);

    int updateByPrimaryKey(Schedule record);

    List<Schedule> selectPastAndFutureNoEnd(String past, String future, int i);

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
    IntegralRankingDto selectNearlyMatchData(@Param("teamId") Integer teamId, @Param("count") int count);

    /**
     * 近期战绩 根据球队、赛事、主客获取对应个数比赛记录
     * @param teamId 球队id
     * @param type   1 主队 2客队 其他为任意
     * @param sclassid 赛事类型id 为空不查
     * @param count 个数
     * @return
     */
    List<HistoryMatchDto> selectHistoryMatchByTeam(@Param("teamId") Integer teamId, @Param("type") String type,
                                                   @Param("sclassId") Integer sclassid, @Param("count") int count);

    /**
     * 主客队历史交锋
     * @param homeTeamId 主队id
     * @param guestTeamId 客队id
     * @param type 1同主客
     * @param count 查询记录数
     * @return
     */
    List<HistoryMatchDto> selectHistoryMatchByTwoTeam(@Param("homeTeamId") Integer homeTeamId,
                                                      @Param("guestTeamId") Integer guestTeamId,
                                                      @Param("type") String type,
                                                      @Param("count") int count);

}