package com.zhcdata.db.mapper;

import com.zhcdata.db.model.TotalScorehalf;

import java.util.Date;
import java.util.List;

public interface TotalScorehalfMapper {
    int deleteByPrimaryKey(Integer oddsid);

    int insert(TotalScorehalf record);

    int insertSelective(TotalScorehalf record);

    TotalScorehalf selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(TotalScorehalf record);

    int updateByPrimaryKey(TotalScorehalf record);

    TotalScorehalf selectByMatchIdAndCpyAndFristGoal(Integer scheduleid, Integer companyid, Float firstgoal);

    TotalScorehalf selectByMidAndCpy(String mid, String cpy);

    List<TotalScorehalf> selectByMid(String mid);

    void deleteByMid(String mid);

    void updateOddsByOddsId(Integer oddsid, Float upodds, Float downodds, Date modifytime, Float goal);

}