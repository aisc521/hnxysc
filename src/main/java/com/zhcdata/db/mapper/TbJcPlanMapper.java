package com.zhcdata.db.mapper;

import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.jc.dto.PlanResult1;
import com.zhcdata.jc.dto.PlanResult2;
import com.zhcdata.jc.dto.TbSPFInfo;
import com.zhcdata.jc.dto.TbScoreResult;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TbJcPlanMapper extends Mapper<TbJcPlan> {
    int queryPlanCountByExpertId(@Param("expertId")String expertId);

    List<PlanResult1> queryPlanByExpertId(@Param("id") long expertId, @Param("planId")String planId);

    List<TbJcPlan> queryPlanList(@Param("expert") String expert, @Param("status") String status);

    List<TbScoreResult> queryScore(@Param("matchId") long matchId);

    List<TbSPFInfo> querySPFList(@Param("MatchId") long MatchId);

    int updateStatus(@Param("isRight") String isRight, @Param("planHit") String planHit, @Param("id") String id);

    List<PlanResult2> queryPlanById(@Param("id") String id);

    List<PlanResult1> queryPlanId(@Param("planId") String planId);

    List<PlanResult1> queryPlanByExpertId1(@Param("id") long expertId);
}