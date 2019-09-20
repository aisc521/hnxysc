package com.zhcdata.db.mapper;

import com.zhcdata.db.model.TbJcMatch;
import com.zhcdata.jc.dto.MatchPlanResult;
import com.zhcdata.jc.dto.MatchPlanResult1;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TbJcMatchMapper extends Mapper<TbJcMatch> {
    List<MatchPlanResult> queryList(@Param("planId")String id);

    int updateStatus(@Param("status") String status,@Param("score") String score);

    List<MatchPlanResult1> queryList1(@Param("planId") Long planId);
}