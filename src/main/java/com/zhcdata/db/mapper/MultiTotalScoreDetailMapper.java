package com.zhcdata.db.mapper;

import com.zhcdata.db.model.MultiTotalScoreDetail;
import org.apache.ibatis.annotations.Param;

public interface MultiTotalScoreDetailMapper {

    //MultiTotalScoreDetail selectByMidAndCpy(String s, String s1);

    int deleteByPrimaryKey(Integer id);

    int insert(MultiTotalScoreDetail record);

    int insertSelective(MultiTotalScoreDetail record);

    MultiTotalScoreDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MultiTotalScoreDetail record);

    int updateByPrimaryKey(MultiTotalScoreDetail record);

    MultiTotalScoreDetail selectByMidAndCpyAndNum(@Param("mid")String mid, @Param("cpy")String cpy,@Param("num") String num);

    void deleteByOddsId(Integer oddsid);
}