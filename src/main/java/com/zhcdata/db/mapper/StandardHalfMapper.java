package com.zhcdata.db.mapper;

import com.zhcdata.db.model.StandardHalf;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StandardHalfMapper {

    int insert(StandardHalf record);

    int insertSelective(StandardHalf record);

    StandardHalf selectByMidAndCpy(@Param("mid")Integer mid, @Param("cpy")Integer cpy);

    int deleteByPrimaryKey(Integer oddsid);

    StandardHalf selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(StandardHalf record);

    int updateByPrimaryKey(StandardHalf record);

    List<StandardHalf> selectByMid(String mid);

    void deleteByMid(String mid);
}