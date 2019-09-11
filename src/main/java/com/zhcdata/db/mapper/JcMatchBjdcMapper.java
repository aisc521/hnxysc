package com.zhcdata.db.mapper;

import com.zhcdata.db.model.JcMatchBjdc;
import io.lettuce.core.dynamic.annotation.Param;
import tk.mybatis.mapper.common.Mapper;

public interface JcMatchBjdcMapper extends Mapper<JcMatchBjdc> {
    JcMatchBjdc queryJcLotterTypeBdByIDBet007(@Param("idBet007") long idBet007);
}