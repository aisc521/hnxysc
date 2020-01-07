package com.zhcdata.db.mapper;


import com.zhcdata.db.model.Standard;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface StandardMapper {

    int deleteByPrimaryKey(Integer oddsid);

    int insert(Standard record);

    int insertSelective(Standard record);

    Standard selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(Standard record);

    int updateByPrimaryKey(Standard record);

    Standard selectMatchIdAndCpy(@Param("scheduleid")Integer scheduleid,@Param("companyid") Integer companyid);

    Standard selectByMidAndCpy(@Param("mid")String mid, @Param("cpy")String cpy);

    List<Standard> selectByMid(String mid);

    void deleteByMid(String mid);

    void updateOddsByOddsId(@Param("oddsid") Integer oddsid, @Param("homewin") Float homewin,@Param("standoff") Float standoff, @Param("guestwin")Float guestwin,@Param("modifytime") Date modifytime);


    //Standard selectByMidAndCpyNew(String mid, String cpy);
}