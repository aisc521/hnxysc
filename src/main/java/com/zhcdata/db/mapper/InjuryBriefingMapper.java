package com.zhcdata.db.mapper;

import com.zhcdata.db.model.InjuryBriefing;

public interface InjuryBriefingMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(InjuryBriefing record);

    int insertSelective(InjuryBriefing record);

    InjuryBriefing selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InjuryBriefing record);

    int updateByPrimaryKey(InjuryBriefing record);

    InjuryBriefing selectByMidAndTeam(Integer mid, String team,String mark);

}