package com.zhcdata.db.mapper;

import com.zhcdata.db.model.InjuryPlayerSuspend;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface InjuryPlayerSuspendMapper extends Mapper<InjuryPlayerSuspend> {

    InjuryPlayerSuspend selectByMidTidPid(@Param("scheduleId")Integer scheduleId,@Param("teamid") Integer teamid, @Param("playerid")Integer playerid);

}