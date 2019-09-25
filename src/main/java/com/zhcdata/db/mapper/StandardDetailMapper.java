package com.zhcdata.db.mapper;

import com.zhcdata.db.model.StandardDetail;

public interface StandardDetailMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(StandardDetail record);

    int insertSelective(StandardDetail record);

    StandardDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StandardDetail record);

    int updateByPrimaryKey(StandardDetail record);

    StandardDetail selectByMidAndCpy(String mid, String cpy);

}