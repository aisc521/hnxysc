package com.zhcdata.db.mapper;

import com.zhcdata.db.model.SclassInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbSclassMapper {
    List<SclassInfo> querySClass(@Param("SClassID") String SClassID);

    List<SclassInfo> querySClassList(@Param("year") String year);

    int insertSelective(SclassInfo sclassInfo);

    int updateByPrimaryKeySelective(SclassInfo sclassInfo);
}