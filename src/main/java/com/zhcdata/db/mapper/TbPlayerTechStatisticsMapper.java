package com.zhcdata.db.mapper;

import com.zhcdata.db.model.PTSInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbPlayerTechStatisticsMapper {
    List<PTSInfo> queryPTS(@Param("scheduleID") String scheduleID, @Param("teamID") String teamID, @Param("playerID") String playerID);

    int insertSelective(PTSInfo ptsInfo);

    int updateByPrimaryKeySelective(PTSInfo ptsInfo);
}