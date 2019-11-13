package com.zhcdata.db.mapper;

import com.zhcdata.db.model.InjuryPlayerSuspend;
import tk.mybatis.mapper.common.Mapper;

public interface InjuryPlayerSuspendMapper extends Mapper<InjuryPlayerSuspend> {

    InjuryPlayerSuspend selectByMidTidPid(Integer scheduleId, Integer teamid, Integer playerid);

}