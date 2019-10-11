package com.zhcdata.db.mapper;

import com.zhcdata.db.model.Letgoal_goal;

import java.util.Date;

public interface Letgoal_goalMapper {

    int deleteByPrimaryKey(Long oddsid);

    int insert(Letgoal_goal record);

    int insertSelective(Letgoal_goal record);

    Letgoal_goal selectByPrimaryKey(Long oddsid);

    int updateByPrimaryKeySelective(Letgoal_goal record);

    int updateByPrimaryKey(Letgoal_goal record);

    int updateCountAddOne(Integer oddsid, Date date);

}