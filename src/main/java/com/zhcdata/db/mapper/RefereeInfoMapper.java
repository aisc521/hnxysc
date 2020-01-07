package com.zhcdata.db.mapper;

import com.zhcdata.db.model.RefereeInfo;
import org.apache.ibatis.annotations.Param;

public interface RefereeInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(RefereeInfo record);

    int insertSelective(RefereeInfo record);

    RefereeInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RefereeInfo record);

    int updateByPrimaryKey(RefereeInfo record);

    RefereeInfo selectByMidAndTypeAndPid(@Param("mid") Integer mid, @Param("type") Integer type, @Param("pid") Integer pid);
}