package com.zhcdata.db.mapper;

import com.zhcdata.db.model.LetGoalhalf;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface LetGoalhalfMapper {
    int deleteByPrimaryKey(Integer oddsid);

    int insert(LetGoalhalf record);

    int insertSelective(LetGoalhalf record);

    LetGoalhalf selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(LetGoalhalf record);

    int updateByPrimaryKey(LetGoalhalf record);

    LetGoalhalf selectByMatchIdAndCmpAndFristGoal(@Param("scheduleid")Integer scheduleid, @Param("companyid")Integer companyid, @Param("firstgoal")Float firstgoal);

    LetGoalhalf selectByMatchIdAndCmp(@Param("mid")Integer mid, @Param("cpy")Integer cpy);

    List<LetGoalhalf> selectByMid(String id);

    void deleteByMid(String mid);

    void updateOddsByOddsId(@Param("oddsid")Integer oddsid, @Param("upodds")Float upodds, @Param("goal")Float goal, @Param("downodds")Float downodds, @Param("time")Date time);
}