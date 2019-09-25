package com.zhcdata.db.mapper;

import com.zhcdata.db.model.TotalScorehalf;

public interface TotalScorehalfMapper {
    int deleteByPrimaryKey(Integer oddsid);

    int insert(TotalScorehalf record);

    int insertSelective(TotalScorehalf record);

    TotalScorehalf selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(TotalScorehalf record);

    int updateByPrimaryKey(TotalScorehalf record);

    TotalScorehalf selectByMatchIdAndCpyAndFristGoal(Integer scheduleid, Integer companyid, Float firstgoal);

    TotalScorehalf selectByMidAndCpy(String mid, String cpy);
}