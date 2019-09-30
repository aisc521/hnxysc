package com.zhcdata.db.mapper;


import com.zhcdata.db.model.TotalScore;

import java.util.Date;
import java.util.List;

public interface TotalScoreMapper {

    int deleteByPrimaryKey(Integer oddsid);

    int insert(TotalScore record);

    int insertSelective(TotalScore record);

    TotalScore selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(TotalScore record);

    int updateByPrimaryKey(TotalScore record);

    TotalScore selectTotalScoreByMatchAndCpyAndFristHandicap(Integer scheduleid, Integer companyid, Float firstgoal);

    TotalScore selectTotalScoreByMatchAndCpy(Integer scheduleid, Integer companyid);

    List<TotalScore> selectByMid(String mid);

    void deleteByMid(String mid);

    void updateOddsByOddsId(Integer oddsid, Date modifytime, Float upodds, Float goal, Float downodds);

    //void updateOddsByOddsId(Integer oddsid, Date modifytime, Float upodds, Float goal, Float downodds);
}