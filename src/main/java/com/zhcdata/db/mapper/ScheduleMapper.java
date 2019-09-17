package com.zhcdata.db.mapper;


import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.dto.HandicapOddsDetailsResult;
import com.zhcdata.jc.dto.HandicapOddsResult;
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
     * @param before 指定天数之前
     * @param after 指定天数之后
     * @return
     */
    List<Integer> selectScheduleIdByDate(@Param("before")int before,@Param("after")int after);

    /**
     * 按时间查询指定天数之间的比赛Id
     * @param before 指定天数之前
     * @param after 指定天数之后
     * @return
     */
    List<Integer> selectScheduleIdByTime(@Param("before")int before,@Param("after")int after);

    /**
     * 查询指定比赛，指定类型的公司赔率
     * @param matchId 比赛id
     * @param type 1欧赔 2亚盘 3大小球
     * @return
     */
    List<HandicapOddsResult> selectOddsResultsByMatchId(@Param("matchId") Integer matchId, @Param("type") String type);

    /**
     * 查询指定比赛，指定类型，指定公司的赔率
     * @param matchId 比赛id
     * @param companyId 公司id
     * @param type 1欧赔 2亚盘 3大小球
     * @return
     */
    List<HandicapOddsDetailsResult> selectOddsResultDetailByMatchId(@Param("matchId") Integer matchId,@Param("companyId") Integer companyId, @Param("type") String type);
}