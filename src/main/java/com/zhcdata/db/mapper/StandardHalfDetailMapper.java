package com.zhcdata.db.mapper;

import com.zhcdata.db.model.StandardHalfDetail;

public interface StandardHalfDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StandardHalfDetail record);

    int insertSelective(StandardHalfDetail record);

    StandardHalfDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StandardHalfDetail record);

    int updateByPrimaryKey(StandardHalfDetail record);

    void deleteByOddsId(Integer oddsid);
}