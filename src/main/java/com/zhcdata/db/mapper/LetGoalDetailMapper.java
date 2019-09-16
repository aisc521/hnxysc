package com.zhcdata.db.mapper;

import com.zhcdata.db.model.LetGoalDetail;

public interface LetGoalDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LetGoalDetail record);

    int insertSelective(LetGoalDetail record);

    LetGoalDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LetGoalDetail record);

    int updateByPrimaryKey(LetGoalDetail record);
}