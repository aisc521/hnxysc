package com.zhcdata.db.mapper;

import com.zhcdata.db.model.JcMatchBjdcreslut;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface JcMatchBjdcreslutMapper extends Mapper<JcMatchBjdcreslut> {
    JcMatchBjdcreslut queryJcMatchBjdcreslutByBet007(@Param("bet007") int i);
}