package com.zhcdata.db.mapper;

import com.zhcdata.db.model.MultiTotalScorehalf;

import java.util.Date;
import java.util.List;

public interface MultiTotalScorehalfMapper {
    int deleteByPrimaryKey(Integer oddsid);

    int insert(MultiTotalScorehalf record);

    int insertSelective(MultiTotalScorehalf record);

    MultiTotalScorehalf selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(MultiTotalScorehalf record);

    int updateByPrimaryKey(MultiTotalScorehalf record);

    MultiTotalScorehalf selectByMatchIdAndCpyAndNum(Integer scheduleid, Integer companyid, Short num);

    MultiTotalScorehalf selectByMidAndCpyAndNum(String mid, String cpy, String num);

    List<MultiTotalScorehalf> selectByMid(String mid);

    void deleteByMid(String mid);

    void updateOddsByOddsId(Integer oddsid, Float goal, Float upodds, Float downodds, Date addtime);
}