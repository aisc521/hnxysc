package com.zhcdata.db.mapper;

import com.zhcdata.db.model.TeamInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbTeamMapper {
    List<TeamInfo> queryTeam(@Param("TeamID") String TeamID);

    int insertSelective(TeamInfo teamInfo);

    int updateByPrimaryKeySelective(TeamInfo teamInfo);
}