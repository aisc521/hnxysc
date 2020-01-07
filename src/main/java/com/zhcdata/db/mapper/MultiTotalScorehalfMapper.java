package com.zhcdata.db.mapper;

import com.zhcdata.db.model.MultiTotalScorehalf;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MultiTotalScorehalfMapper {
    int deleteByPrimaryKey(Integer oddsid);

    int insert(MultiTotalScorehalf record);

    int insertSelective(MultiTotalScorehalf record);

    MultiTotalScorehalf selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(MultiTotalScorehalf record);

    int updateByPrimaryKey(MultiTotalScorehalf record);

    MultiTotalScorehalf selectByMatchIdAndCpyAndNum(@Param("scheduleid")Integer scheduleid, @Param("companyid")Integer companyid, @Param("num")Short num);

    MultiTotalScorehalf selectByMidAndCpyAndNum(@Param("mid")String mid, @Param("cpy")String cpy, @Param("num")String num);

    List<MultiTotalScorehalf> selectByMid(String mid);

    void deleteByMid(String mid);

    void updateOddsByOddsId(@Param("oddsid")Integer oddsid, @Param("goal")Float goal, @Param("upodds")Float upodds, @Param("downodds")Float downodds, @Param("addtime")Date addtime);
}