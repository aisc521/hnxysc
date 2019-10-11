package com.zhcdata.db.mapper;


import com.zhcdata.db.model.MultiTotalScore;

import java.util.Date;
import java.util.List;

public interface MultiTotalScoreMapper {
    int deleteByPrimaryKey(Integer oddsid);

    int insert(MultiTotalScore record);

    int insertSelective(MultiTotalScore record);

    MultiTotalScore selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(MultiTotalScore record);

    int updateByPrimaryKey(MultiTotalScore record);

    MultiTotalScore selectByMatchIdAndCpyAndNum(Integer scheduleid, Integer companyid, Short num);

    MultiTotalScore selectTotalScoreByMatchAndCpyAndNum(int mid, int cpy, int num);

    List<MultiTotalScore> selectByMid(String mid);

    void deleteByMid(String mid);

    void updateOddsByOddsId(Integer oddsid, Date addtime, Float upodds, Float goal, Float downodds);
    
}