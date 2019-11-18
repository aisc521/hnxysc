package com.zhcdata.db.mapper;

import com.zhcdata.db.model.TotalScoreDetail;
import org.apache.ibatis.annotations.Param;

public interface TotalScoreDetailMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(TotalScoreDetail record);

    int insertSelective(TotalScoreDetail record);

    TotalScoreDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TotalScoreDetail record);

    int updateByPrimaryKey(TotalScoreDetail record);

    TotalScoreDetail selectByMidAndCpy(@Param("mid")String mid, @Param("cpy")String cpy);

    void deleteByOddsId(Integer oddsid);
}