package com.zhcdata.db.mapper;


import com.zhcdata.db.model.MultiLetgoal;

public interface MultiLetgoalMapper {

    int deleteByPrimaryKey(Integer oddsid);

    int insert(MultiLetgoal record);

    int insertSelective(MultiLetgoal record);

    MultiLetgoal selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(MultiLetgoal record);

    int updateByPrimaryKey(MultiLetgoal record);

    MultiLetgoal selectByMatchIdAndCompanyAndHandicap(String matchId, String cpy, String frist_goal);

    MultiLetgoal selectByMatchIdAndCompanyAndHandicapNum(String matchId, String cpy, Short num);
}