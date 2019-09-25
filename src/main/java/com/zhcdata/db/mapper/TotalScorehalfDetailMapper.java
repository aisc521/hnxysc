package com.zhcdata.db.mapper;

import com.zhcdata.db.model.TotalScorehalfDetail;

public interface TotalScorehalfDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TotalScorehalfDetail record);

    int insertSelective(TotalScorehalfDetail record);

    TotalScorehalfDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TotalScorehalfDetail record);

    int updateByPrimaryKey(TotalScorehalfDetail record);

    TotalScorehalfDetail selectByMidAndCpy(String mid, String cpy);
}