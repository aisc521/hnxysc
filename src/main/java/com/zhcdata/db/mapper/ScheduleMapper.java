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
     * 查询指定天数之前到5天后的比赛Id
     * @param count 指定天数
     * @return
     */
    List<Integer> selectScheduleIdByDate(int count);

    /**
     * 查询指定比赛，指定类型公司赔率
     * @param matchId 比赛id
     * @param type 1欧赔 2亚盘 3大小球
     * @return
     */
    List<HandicapOddsResult> selectOddsResultsByMatchId(@Param("matchId") Integer matchId, @Param("type") String type);

    List<HandicapOddsDetailsResult> selectOddsResultDetailByMatchId(@Param("matchId") Integer matchId,@Param("companyId") Integer companyId, @Param("type") String type);
}