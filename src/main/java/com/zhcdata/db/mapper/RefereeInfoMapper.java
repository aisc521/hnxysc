package com.zhcdata.db.mapper;

import com.zhcdata.db.model.RefereeInfo;

public interface RefereeInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(RefereeInfo record);

    int insertSelective(RefereeInfo record);

    RefereeInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RefereeInfo record);

    int updateByPrimaryKey(RefereeInfo record);

    RefereeInfo selectByMidAndTypeAndPid(Integer mid, Integer type, Integer pid);
}