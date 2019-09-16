package com.zhcdata.db.mapper;


import com.zhcdata.db.model.MultiTotalScore;

public interface MultiTotalScoreMapper {
    int deleteByPrimaryKey(Integer oddsid);

    int insert(MultiTotalScore record);

    int insertSelective(MultiTotalScore record);

    MultiTotalScore selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(MultiTotalScore record);

    int updateByPrimaryKey(MultiTotalScore record);

    MultiTotalScore selectByMatchIdAndCpyAndNum(Integer scheduleid, Integer companyid, Short num);
}