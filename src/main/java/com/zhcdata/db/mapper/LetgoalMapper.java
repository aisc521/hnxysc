package com.zhcdata.db.mapper;


import com.zhcdata.db.model.Letgoal;

public interface LetgoalMapper {

    int deleteByPrimaryKey(Integer oddsid);

    int insert(Letgoal record);

    int insertSelective(Letgoal record);

    Letgoal selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(Letgoal record);

    int updateByPrimaryKey(Letgoal record);

    Letgoal selectByMatchIdAndCompany(String matchId, String cpy);

}