package com.zhcdata.db.mapper;


import com.zhcdata.db.model.TotalScore;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface TotalScoreMapper {

    int deleteByPrimaryKey(Integer oddsid);

    int insert(TotalScore record);

    int insertSelective(TotalScore record);

    TotalScore selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(TotalScore record);

    int updateByPrimaryKey(TotalScore record);

    TotalScore selectTotalScoreByMatchAndCpyAndFristHandicap(@Param("scheduleid")Integer scheduleid, @Param("companyid")Integer companyid, @Param("firstgoal")Float firstgoal);

    TotalScore selectTotalScoreByMatchAndCpy(@Param("scheduleid")Integer scheduleid, @Param("companyid")Integer companyid);

    List<TotalScore> selectByMid(String mid);

    void deleteByMid(String mid);

    void updateOddsByOddsId(@Param("oddsid")Integer oddsid, @Param("modifytime")Date modifytime, @Param("upodds")Float upodds, @Param("goal")Float goal, @Param("downodds")Float downodds);

    //void updateOddsByOddsId(Integer oddsid, Date modifytime, Float upodds, Float goal, Float downodds);
}