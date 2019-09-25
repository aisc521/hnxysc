package com.zhcdata.db.mapper;

import com.zhcdata.db.model.MultiLetGoalhalf;

public interface MultiLetGoalhalfMapper {
    int deleteByPrimaryKey(Integer oddsid);

    int insert(MultiLetGoalhalf record);

    int insertSelective(MultiLetGoalhalf record);

    MultiLetGoalhalf selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(MultiLetGoalhalf record);

    int updateByPrimaryKey(MultiLetGoalhalf record);

    MultiLetGoalhalf selectByMatchIdAndCmpAndFristGoalAndNum(Integer scheduleid, Integer companyid, Float firstgoal,Short num);

    MultiLetGoalhalf selectByMatchIdAndCmpAndNum(String mid, String cpy, String num);
}