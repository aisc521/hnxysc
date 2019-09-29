package com.zhcdata.db.mapper;

import com.zhcdata.db.model.MultiLetGoalhalfDetail;

public interface MultiLetGoalhalfDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MultiLetGoalhalfDetail record);

    int insertSelective(MultiLetGoalhalfDetail record);

    MultiLetGoalhalfDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MultiLetGoalhalfDetail record);

    int updateByPrimaryKey(MultiLetGoalhalfDetail record);

    MultiLetGoalhalfDetail selectByMidAndCpyAndNum(String mid, String cpy, String num);

    void deleteByOddsId(Integer oddsid);
}