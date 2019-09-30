package com.zhcdata.db.mapper;

import com.zhcdata.db.model.StandardHalf;

import java.util.List;

public interface StandardHalfMapper {

    int insert(StandardHalf record);

    int insertSelective(StandardHalf record);

    StandardHalf selectByMidAndCpy(Integer mid, Integer cpy);

    int deleteByPrimaryKey(Integer oddsid);

    StandardHalf selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(StandardHalf record);

    int updateByPrimaryKey(StandardHalf record);

    List<StandardHalf> selectByMid(String mid);

    void deleteByMid(String mid);
}