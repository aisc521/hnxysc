package com.zhcdata.db.mapper;


import com.zhcdata.db.model.Letgoal;
import com.zhcdata.db.model.Standard;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface LetgoalMapper {

    int deleteByPrimaryKey(Integer oddsid);

    int insert(Letgoal record);

    int insertSelective(Letgoal record);

    Letgoal selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(Letgoal record);

    int updateByPrimaryKey(Letgoal record);

    Letgoal selectByMatchIdAndCompany(String matchId, String cpy);

    Map<String, Object> selectOddsIdAndStartTimeByByMatchIdAndCompany(String matchId, String cpy);

    List<Letgoal> selectByMid(String mid);

    void deleteByMid(int mid);

    void updateOddsByOddsId(Integer oddsid, Float upodds, Float goal, Float downodds, Date modifytime);

    //Letgoal selectByMidAndCpyNew(String mid, String cpy);
}