package com.zhcdata.db.mapper;

import com.zhcdata.db.model.MultiTotalScorehalfDetail;

public interface MultiTotalScorehalfDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MultiTotalScorehalfDetail record);

    int insertSelective(MultiTotalScorehalfDetail record);

    MultiTotalScorehalfDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MultiTotalScorehalfDetail record);

    int updateByPrimaryKey(MultiTotalScorehalfDetail record);

    MultiTotalScorehalfDetail selectByMidAndCpyAndNum(String mid, String cpy, String num);

    void deleteByOddsId(Integer oddsid);
}