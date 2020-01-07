package com.zhcdata.db.mapper;


import com.zhcdata.db.model.ClubRecordInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExchangeClubRecordMapper {
    int insertSelective(ClubRecordInfo clubRecordInfo);

    int updateByPrimaryKeySelective(ClubRecordInfo clubRecordInfo);

    List<ClubRecordInfo> selectByPrimaryKey(@Param("id") Integer id);
}