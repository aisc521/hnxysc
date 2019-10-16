package com.zhcdata.db.mapper;


import com.zhcdata.db.model.CupMatch;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CupMatchMapper {
    int insertSelective(CupMatch cupMatch);

    List<CupMatch> selectList(@Param("sclassID") String sclassID,@Param("grouping") String grouping,@Param("matchseason") String matchseason);

    int updateByPrimaryKeySelective(CupMatch cupMatch);
}