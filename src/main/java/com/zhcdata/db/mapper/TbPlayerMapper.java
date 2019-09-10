package com.zhcdata.db.mapper;

import com.zhcdata.db.model.PlayerInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbPlayerMapper{
    List<PlayerInfo> queryPlayer(@Param("PlayerID") String PlayerID);
}