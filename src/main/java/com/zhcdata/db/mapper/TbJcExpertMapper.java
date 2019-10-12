package com.zhcdata.db.mapper;

import com.zhcdata.db.model.TbJcExpert;
import com.zhcdata.jc.dto.ExpertInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TbJcExpertMapper extends Mapper<TbJcExpert> {
    List<ExpertInfo> queryHotExperts(@Param("type") String type);

    ExpertInfo queryExpertDetails(@Param("expertId")long l);

    List<ExpertInfo> queryExperts();

    List<ExpertInfo> queryExpertsByType(@Param("type") String type);

    List<ExpertInfo> query(@Param("userId")String userId);

    TbJcExpert queryExpertDetailsById(@Param("id")int id);
}