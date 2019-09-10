package com.zhcdata.db.mapper;

import com.zhcdata.db.model.JcLotterTypeZc;
import tk.mybatis.mapper.common.Mapper;

public interface JcLotterTypeZcMapper extends Mapper<JcLotterTypeZc> {
    JcLotterTypeZc queryJcLotterTypeZcByIDBet007(long idBet007);
}