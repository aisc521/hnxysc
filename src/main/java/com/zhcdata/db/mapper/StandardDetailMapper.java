package com.zhcdata.db.mapper;

import com.zhcdata.db.model.StandardDetail;
import org.apache.ibatis.annotations.Param;

public interface StandardDetailMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(StandardDetail record);

    int insertSelective(StandardDetail record);

    StandardDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StandardDetail record);

    int updateByPrimaryKey(StandardDetail record);

    StandardDetail selectByMidAndCpy(@Param("mid") String mid, @Param("cpy") String cpy);

    void deleteByOddsId(Integer oddsid);
}