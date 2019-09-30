package com.zhcdata.db.mapper;


import com.zhcdata.db.model.Standard;

import java.util.Date;
import java.util.List;

public interface StandardMapper {

    int deleteByPrimaryKey(Integer oddsid);

    int insert(Standard record);

    int insertSelective(Standard record);

    Standard selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(Standard record);

    int updateByPrimaryKey(Standard record);

    Standard selectMatchIdAndCpy(Integer scheduleid, Integer companyid);

    Standard selectByMidAndCpy(String mid, String cpy);

    List<Standard> selectByMid(String mid);

    void deleteByMid(String mid);

    void updateOddsByOddsId(Integer oddsid, Float homewin, Float standoff, Float guestwin, Date modifytime);


    //Standard selectByMidAndCpyNew(String mid, String cpy);
}