package com.zhcdata.db.mapper;

import com.zhcdata.db.model.MultiLetGoalDetail;

public interface MultiLetGoalDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MultiLetGoalDetail record);

    int insertSelective(MultiLetGoalDetail record);

    MultiLetGoalDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MultiLetGoalDetail record);

    int updateByPrimaryKey(MultiLetGoalDetail record);

    MultiLetGoalDetail selectByMidAndCpyAndNum(String mid, String cpy, String num);

    void deleteByOddsId(Integer oddsid);
}