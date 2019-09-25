package com.zhcdata.db.mapper;


import com.zhcdata.db.model.TotalScore;

public interface TotalScoreMapper {

    int deleteByPrimaryKey(Integer oddsid);

    int insert(TotalScore record);

    int insertSelective(TotalScore record);

    TotalScore selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(TotalScore record);

    int updateByPrimaryKey(TotalScore record);

    TotalScore selectTotalScoreByMatchAndCpyAndFristHandicap(Integer scheduleid, Integer companyid, Float firstgoal);

    TotalScore selectTotalScoreByMatchAndCpy(Integer scheduleid, Integer companyid);
}