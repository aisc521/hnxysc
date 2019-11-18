package com.zhcdata.db.mapper;

import com.zhcdata.db.model.MultiLetGoalDetail;
import org.apache.ibatis.annotations.Param;

public interface MultiLetGoalDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MultiLetGoalDetail record);

    int insertSelective(MultiLetGoalDetail record);

    MultiLetGoalDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MultiLetGoalDetail record);

    int updateByPrimaryKey(MultiLetGoalDetail record);

    MultiLetGoalDetail selectByMidAndCpyAndNum(@Param("mid")String mid, @Param("cpy")String cpy, @Param("num")String num);

    void deleteByOddsId(Integer oddsid);
}