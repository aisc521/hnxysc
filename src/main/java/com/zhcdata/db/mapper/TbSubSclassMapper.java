package com.zhcdata.db.mapper;

import com.zhcdata.db.model.SubSclassInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbSubSclassMapper {

    List<SubSclassInfo> querySubSclass(@Param("subsclassID") String subsclassID);

    List<SubSclassInfo> querySubSclassID(@Param("SclassID") String SclassID,@Param("IncludeSeason") String IncludeSeason);

    int insertSelective(SubSclassInfo subSclassInfo);

    int updateByPrimaryKeySelective(SubSclassInfo subSclassInfo);
}