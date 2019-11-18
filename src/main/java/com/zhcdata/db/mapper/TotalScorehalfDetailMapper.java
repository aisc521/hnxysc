package com.zhcdata.db.mapper;

import com.zhcdata.db.model.TotalScorehalfDetail;
import com.zhcdata.jc.dto.SimplifyOdds;
import org.apache.ibatis.annotations.Param;

public interface TotalScorehalfDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TotalScorehalfDetail record);

    int insertSelective(TotalScorehalfDetail record);

    TotalScorehalfDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TotalScorehalfDetail record);

    int updateByPrimaryKey(TotalScorehalfDetail record);

    TotalScorehalfDetail selectByMidAndCpy(@Param("mid") String mid, @Param("cpy") String cpy);

    SimplifyOdds selectTotalScorehalfSloByMidAndCpy(@Param("mid")String mid, @Param("cpy")String cpy);

    void deleteByOddsId(Integer oddsid);
}