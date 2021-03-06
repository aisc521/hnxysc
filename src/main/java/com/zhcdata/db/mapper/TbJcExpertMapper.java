package com.zhcdata.db.mapper;

import com.zhcdata.db.model.TbJcExpert;
import com.zhcdata.jc.dto.ExpertInfo;
import com.zhcdata.jc.dto.ExpertInfoBdDto;
import com.zhcdata.jc.dto.ExpertInfoDto;
import com.zhcdata.jc.dto.HotExpertDto;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TbJcExpertMapper extends Mapper<TbJcExpert> {
    List<ExpertInfo> queryHotExperts(@Param("type") String type);

    ExpertInfo queryExpertDetails(@Param("expertId")long l);

    List<ExpertInfo> queryExperts(@Param("time")String time);

    List<ExpertInfo> queryExpertsAll();

    List<ExpertInfoBdDto> queryExpertsByType(@Param("type") String type);

    List<ExpertInfo> query(@Param("userId")String userId);

    TbJcExpert queryExpertDetailsById(@Param("id")int id);

    void updatePopAddOneByEid(@Param("uid") int eid,@Param("pop") int pop);

    ExpertInfo queryExpertDetailsAndUser(@Param("expertId")String expertId, @Param("userId")String userId);

    List<ExpertInfoDto> queryExpertInfo(@Param("userId")String userId);

    List<HotExpertDto> queryExpertHotInfoOrder();

    List<HotExpertDto> queryExpertHotInfoOrderLimitSeven();
}