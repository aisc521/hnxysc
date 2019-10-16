package com.zhcdata.db.mapper;

import com.zhcdata.db.model.SclassInfo;
import com.zhcdata.db.model.SclassType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbSclassMapper {
    List<SclassInfo> querySClass(@Param("SClassID") String SClassID);

    List<SclassInfo> querySClassList(@Param("year") String year);

    /**
     * 
     * @param s
     * @param e
     * @return
     */
    List<SclassInfo> querySClassIDList(@Param("s") String s,@Param("e") String e);

    int insertSelective(SclassInfo sclassInfo);

    int updateByPrimaryKeySelective(SclassInfo sclassInfo);

    int insertSclassTypeSelective(SclassType sclassType);

    List<SclassType> querySclassTypeList(@Param("sclassID") String sclassID);

    int updateSclassTypeByPrimaryKeySelective(SclassType sclassType);
}