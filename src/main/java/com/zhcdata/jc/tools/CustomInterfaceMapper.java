package com.zhcdata.jc.tools;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @author Yore
 */
public interface CustomInterfaceMapper<T> extends Mapper<T>,InsertListMapper<T> {

}
