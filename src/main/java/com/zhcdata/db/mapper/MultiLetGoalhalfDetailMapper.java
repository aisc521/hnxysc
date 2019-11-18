package com.zhcdata.db.mapper;

import com.zhcdata.db.model.MultiLetGoalhalfDetail;
import org.apache.ibatis.annotations.Param;

public interface MultiLetGoalhalfDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MultiLetGoalhalfDetail record);

    int insertSelective(MultiLetGoalhalfDetail record);

    MultiLetGoalhalfDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MultiLetGoalhalfDetail record);

    int updateByPrimaryKey(MultiLetGoalhalfDetail record);

    MultiLetGoalhalfDetail selectByMidAndCpyAndNum(@Param("mid")String mid, @Param("cpy")String cpy, @Param("num")String num);

    void deleteByOddsId(@Param("oddsid")Integer oddsid);
}