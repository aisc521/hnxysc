package com.zhcdata.db.mapper;

import com.zhcdata.db.model.TbSclassInfo;
import com.zhcdata.db.model.TeamInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbSclassInfoMapper {

    List<TbSclassInfo> querySclassInfo(@Param("NameCN") String NameCN);

    int insertSelective(TbSclassInfo tbSclassInfo);
}