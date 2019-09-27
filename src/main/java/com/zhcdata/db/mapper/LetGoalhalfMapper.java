package com.zhcdata.db.mapper;

import com.zhcdata.db.model.LetGoalhalf;

import java.util.List;

public interface LetGoalhalfMapper {
    int deleteByPrimaryKey(Integer oddsid);

    int insert(LetGoalhalf record);

    int insertSelective(LetGoalhalf record);

    LetGoalhalf selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(LetGoalhalf record);

    int updateByPrimaryKey(LetGoalhalf record);

    LetGoalhalf selectByMatchIdAndCmpAndFristGoal(Integer scheduleid, Integer companyid, Float firstgoal);

    LetGoalhalf selectByMatchIdAndCmp(Integer mid, Integer cpy);

    List<LetGoalhalf> selectByMid(String id);

    void deleteByMid(String mid);
}