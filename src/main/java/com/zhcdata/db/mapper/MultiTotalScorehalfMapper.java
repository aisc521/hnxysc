package com.zhcdata.db.mapper;

import com.zhcdata.db.model.MultiTotalScorehalf;

public interface MultiTotalScorehalfMapper {
    int deleteByPrimaryKey(Integer oddsid);

    int insert(MultiTotalScorehalf record);

    int insertSelective(MultiTotalScorehalf record);

    MultiTotalScorehalf selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(MultiTotalScorehalf record);

    int updateByPrimaryKey(MultiTotalScorehalf record);

    MultiTotalScorehalf selectByMatchIdAndCpyAndNum(Integer scheduleid, Integer companyid, Short num);

    MultiTotalScorehalf selectByMidAndCpyAndNum(String mid, String cpy, String num);
}