package com.zhcdata.db.mapper;

import com.zhcdata.db.model.LetGoalhalf;

public interface LetGoalhalfMapper {
    int deleteByPrimaryKey(Integer oddsid);

    int insert(LetGoalhalf record);

    int insertSelective(LetGoalhalf record);

    LetGoalhalf selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(LetGoalhalf record);

    int updateByPrimaryKey(LetGoalhalf record);

    LetGoalhalf selectByMatchIdAndCmpAndFristGoal(Integer scheduleid, Integer companyid, Float firstgoal);
}