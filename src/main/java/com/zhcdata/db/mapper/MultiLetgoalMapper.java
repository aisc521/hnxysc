package com.zhcdata.db.mapper;


import com.zhcdata.db.model.MultiLetgoal;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface MultiLetgoalMapper {

    int deleteByPrimaryKey(Integer oddsid);

    int insert(MultiLetgoal record);

    int insertSelective(MultiLetgoal record);

    MultiLetgoal selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(MultiLetgoal record);

    int updateByPrimaryKey(MultiLetgoal record);

    MultiLetgoal selectByMatchIdAndCompanyAndHandicap(@Param("matchId")String matchId, @Param("cpy")String cpy, @Param("frist_goal")String frist_goal);

    MultiLetgoal selectByMatchIdAndCompanyAndHandicapNum(@Param("matchId")String matchId, @Param("cpy")String cpy, @Param("num")Short num);

    List<MultiLetgoal> selectByMid(String id);

    void deleteByMid(String mid);

    void updateOddsByOddsId(@Param("oddsid")Integer oddsid, @Param("upodds")Float upodds, @Param("goal")Float goal, @Param("downodds")Float downodds, @Param("addtime")Date addtime);
}