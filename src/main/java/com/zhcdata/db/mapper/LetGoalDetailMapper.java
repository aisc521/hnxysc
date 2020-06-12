package com.zhcdata.db.mapper;

import com.zhcdata.db.model.LetGoalDetail;
import org.apache.ibatis.annotations.Param;

public interface LetGoalDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LetGoalDetail record);

    int insertSelective(LetGoalDetail record);

    LetGoalDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LetGoalDetail record);

    int updateByPrimaryKey(LetGoalDetail record);

    LetGoalDetail selectByMatchAndCpyOrderByTimeDescLimit1(@Param("id")int id, @Param("cpy")int cpy);

    LetGoalDetail selectByMatchAndCpyOrderByTimeAscLimit1(@Param("id")int id, @Param("cpy")int cpy);

    void deleteByOddsId(Integer oddsid);

    int deleteletgoaldetailByOddsid(@Param("matchId")String matchId);
}