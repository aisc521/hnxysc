package com.zhcdata.db.mapper;

import com.zhcdata.db.model.CupMatchInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbCupMatchMapper {
    List<CupMatchInfo> queryCupMatch(@Param("ID") String ID);
}