package com.zhcdata.db.mapper;


import com.zhcdata.db.model.MultiTotalScore;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MultiTotalScoreMapper {
    int deleteByPrimaryKey(Integer oddsid);

    int insert(MultiTotalScore record);

    int insertSelective(MultiTotalScore record);

    MultiTotalScore selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(MultiTotalScore record);

    int updateByPrimaryKey(MultiTotalScore record);

    MultiTotalScore selectByMatchIdAndCpyAndNum(@Param("scheduleid")Integer scheduleid, @Param("companyid") Integer companyid, @Param("num") Short num);

    MultiTotalScore selectTotalScoreByMatchAndCpyAndNum(@Param("mid")int mid, @Param("cpy")int cpy, @Param("num")int num);

    List<MultiTotalScore> selectByMid(String mid);

    void deleteByMid(String mid);

    void updateOddsByOddsId(@Param("oddsid") Integer oddsid, @Param("addtime")Date addtime, @Param("upodds")Float upodds,@Param("goal") Float goal, @Param("downodds")Float downodds);
    
}