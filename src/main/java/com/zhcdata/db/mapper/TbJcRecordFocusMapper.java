package com.zhcdata.db.mapper;

import com.zhcdata.db.model.TbJcRecordFocus;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface TbJcRecordFocusMapper extends Mapper<TbJcRecordFocus> {
    TbJcRecordFocus queryRecordFocus(@Param("userId") Long userId, @Param("expertId") Long expertId);

}