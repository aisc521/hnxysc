package com.zhcdata.db.mapper;


import com.zhcdata.db.model.Schedule;
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

    List<Schedule> selectScheduleListByMatchTime(@Param("past") String past, @Param("future") String future);
}