package com.zhcdata.db.mapper;

import com.zhcdata.db.model.JcMatchJczq;
import io.lettuce.core.dynamic.annotation.Param;
import tk.mybatis.mapper.common.Mapper;

public interface JcMatchJczqMapper extends Mapper<JcMatchJczq> {
    JcMatchJczq queryJcLotterTypeJcByIDBet007(@Param("idBet007") long idBet007);

    JcMatchJczq queryJcLotterTypeJcByNoId(@Param("noId") String id);
}