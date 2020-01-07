package com.zhcdata.db.mapper;

import com.zhcdata.db.model.InjuryBriefing;
import org.apache.ibatis.annotations.Param;

public interface InjuryBriefingMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(InjuryBriefing record);

    int insertSelective(InjuryBriefing record);

    InjuryBriefing selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InjuryBriefing record);

    int updateByPrimaryKey(InjuryBriefing record);

    InjuryBriefing selectByMidAndTeam(@Param("mid")Integer mid, @Param("team")String team, @Param("mark")String mark);

}