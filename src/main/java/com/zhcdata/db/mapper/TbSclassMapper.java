package com.zhcdata.db.mapper;

import com.zhcdata.db.model.SclassInfo;
import com.zhcdata.db.model.TeamInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbSclassMapper {
    List<SclassInfo> querySClass(@Param("SClassID") String SClassID);

    int insertSelective(SclassInfo sclassInfo);
}