package com.zhcdata.db.mapper;

import com.zhcdata.db.model.MultiTotalScorehalfDetail;
import org.apache.ibatis.annotations.Param;

public interface MultiTotalScorehalfDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MultiTotalScorehalfDetail record);

    int insertSelective(MultiTotalScorehalfDetail record);

    MultiTotalScorehalfDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MultiTotalScorehalfDetail record);

    int updateByPrimaryKey(MultiTotalScorehalfDetail record);

    MultiTotalScorehalfDetail selectByMidAndCpyAndNum(@Param("mid") String mid, @Param("cpy")String cpy, @Param("num")String num);

    void deleteByOddsId(Integer oddsid);
}