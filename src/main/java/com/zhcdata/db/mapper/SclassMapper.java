package com.zhcdata.db.mapper;

import com.zhcdata.db.model.Sclass;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SclassMapper extends Mapper<Sclass> {
    List<Sclass> querySclassInfo();
}