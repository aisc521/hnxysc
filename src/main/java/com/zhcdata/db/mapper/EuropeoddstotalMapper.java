package com.zhcdata.db.mapper;


import com.zhcdata.db.model.Europeoddstotal;

public interface EuropeoddstotalMapper {

    int deleteByPrimaryKey(Integer scheduleid);

    int insert(Europeoddstotal record);

    int insertSelective(Europeoddstotal record);

    Europeoddstotal selectByPrimaryKey(Integer scheduleid);

    int updateByPrimaryKeySelective(Europeoddstotal record);

    int updateByPrimaryKey(Europeoddstotal record);
}