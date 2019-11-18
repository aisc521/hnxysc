package com.zhcdata.db.mapper;

import com.zhcdata.db.model.TotalScorehalf;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TotalScorehalfMapper {
    int deleteByPrimaryKey(Integer oddsid);

    int insert(TotalScorehalf record);

    int insertSelective(TotalScorehalf record);

    TotalScorehalf selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(TotalScorehalf record);

    int updateByPrimaryKey(TotalScorehalf record);

    TotalScorehalf selectByMatchIdAndCpyAndFristGoal(@Param("scheduleid")Integer scheduleid, @Param("companyid")Integer companyid, @Param("firstgoal")Float firstgoal);

    TotalScorehalf selectByMidAndCpy(@Param("mid")String mid, @Param("cpy")String cpy);

    List<TotalScorehalf> selectByMid(String mid);

    void deleteByMid(String mid);

    void updateOddsByOddsId(@Param("oddsid")Integer oddsid, @Param("upodds")Float upodds, @Param("downodds")Float downodds, @Param("modifytime")Date modifytime, @Param("goal")Float goal);

}