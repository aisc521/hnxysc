package com.zhcdata.db.mapper;

import com.zhcdata.db.model.JcMatchLive;
import com.zhcdata.jc.dto.TextLiving;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface JcMatchLiveMapper extends Mapper<JcMatchLive> {
    List<TextLiving> queryTextLivingList(@Param("matchId") int matchId);
}