package com.zhcdata.db.mapper;

import com.zhcdata.db.model.LetGoalhalfDetail;
import org.apache.ibatis.annotations.Param;

public interface LetGoalhalfDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LetGoalhalfDetail record);

    int insertSelective(LetGoalhalfDetail record);

    LetGoalhalfDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LetGoalhalfDetail record);

    int updateByPrimaryKey(LetGoalhalfDetail record);

    LetGoalhalfDetail selectByMidAndCpy(@Param("mid")String mid,@Param("cpy") String cpy);

    void deleteByOddsId(Integer oddsid);
}