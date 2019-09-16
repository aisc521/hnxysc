package com.zhcdata.db.mapper;

import com.zhcdata.db.model.JcSchedulesp;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface JcSchedulespMapper extends Mapper<JcSchedulesp> {
    JcSchedulesp queryJcSchedulespByScId(@Param("bet007Id") int bet007);
}