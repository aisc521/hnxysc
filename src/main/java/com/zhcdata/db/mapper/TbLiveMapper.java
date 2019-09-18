package com.zhcdata.db.mapper;


import com.zhcdata.db.model.LiveInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbLiveMapper {
    List<LiveInfo> queryLive(@Param("ID") String ID);

    int insertSelective(LiveInfo liveInfo);
}