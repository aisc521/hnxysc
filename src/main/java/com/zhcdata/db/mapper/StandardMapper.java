package com.zhcdata.db.mapper;


import com.zhcdata.db.model.Standard;

public interface StandardMapper {

    int deleteByPrimaryKey(Integer oddsid);

    int insert(Standard record);

    int insertSelective(Standard record);

    Standard selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(Standard record);

    int updateByPrimaryKey(Standard record);

    Standard selectMatchIdAndCpy(Integer scheduleid, Integer companyid);

    Standard selectByMidAndCpy(String mid, String cpy);

    //Standard selectByMidAndCpyNew(String mid, String cpy);
}