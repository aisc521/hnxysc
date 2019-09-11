package com.zhcdata.db.mapper;

import com.zhcdata.db.model.JcMatchZc;
import io.lettuce.core.dynamic.annotation.Param;
import tk.mybatis.mapper.common.Mapper;

public interface JcMatchZcMapper extends Mapper<JcMatchZc> {
    JcMatchZc queryJcLotterTypeZcByIDBet007(@Param("idBet007")long idBet007);
}