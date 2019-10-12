package com.zhcdata.db.mapper;

import com.zhcdata.db.model.TbJcUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface TbJcUserMapper extends Mapper<TbJcUser> {
    TbJcUser queryTbJcUserById(@Param("id") Long userId);
}