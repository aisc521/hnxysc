package com.zhcdata.db.mapper;

import com.zhcdata.db.model.PlayerInTeamInfo;
import com.zhcdata.db.model.TeamInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbPlayerInTeamMapper {

    List<PlayerInTeamInfo> queryPlayerInTeam(@Param("ID") String ID);

    int insertSelective(PlayerInTeamInfo playerInTeamInfo);

    int updateByPrimaryKeySelective(PlayerInTeamInfo playerInTeamInfo);
}