package com.zhcdata.db.mapper;

import com.zhcdata.db.model.JcSchedule;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface JcScheduleMapper extends Mapper<JcSchedule> {
    JcSchedule queryJcScheduleByBet007(@Param("bet007Id") long bet007);

    JcSchedule queryJcScheduleByMatchID(@Param("matchid") String id,@Param("startTime") String startTime,@Param("endTime") String endTime);
}