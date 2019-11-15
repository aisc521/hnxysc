package com.zhcdata.db.mapper;

import com.zhcdata.db.model.MultiLetGoalhalf;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MultiLetGoalhalfMapper {
    int deleteByPrimaryKey(Integer oddsid);

    int insert(MultiLetGoalhalf record);

    int insertSelective(MultiLetGoalhalf record);

    MultiLetGoalhalf selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(MultiLetGoalhalf record);

    int updateByPrimaryKey(MultiLetGoalhalf record);

    MultiLetGoalhalf selectByMatchIdAndCmpAndFristGoalAndNum(@Param("scheduleid")Integer scheduleid, @Param("companyid")Integer companyid, @Param("firstgoal")Float firstgoal, @Param("num")Short num);

    MultiLetGoalhalf selectByMatchIdAndCmpAndNum(@Param("mid")String mid, @Param("cpy")String cpy, @Param("num")String num);

    List<MultiLetGoalhalf> selectByMid(String id);

    void deleteByMid(String mid);

    void updateOddsByOddsId(@Param("oddsid")Integer oddsid, @Param("upodds")Float upodds, @Param("goal")Float goal, @Param("downodds")Float downodds, @Param("addtime")Date addtime);
}