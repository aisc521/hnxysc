package com.zhcdata.db.mapper;

import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.jc.dto.*;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface TbJcPlanMapper extends Mapper<TbJcPlan> {
    int queryPlanCountByExpertId(@Param("expertId")String expertId);

    List<PlanResult1> queryPlanByExpertId(@Param("id") long expertId, @Param("planId")String planId,@Param("userId")String userId);

    List<TbJcPlan> queryPlanList(@Param("expert") String expert, @Param("status") String status);

    List<TbScoreResult> queryScore(@Param("matchId") long matchId);

    SPFListDto querySPFList(@Param("MatchId") long MatchId);

    int updateStatus(@Param("isRight") String isRight, @Param("planHit") String planHit, @Param("id") String id);

    List<PlanResult2> queryPlanById(@Param("id") String id);

    List<PlanResult1> queryPlanId(@Param("planId") String planId);

    List<PlanResult1> queryPlanByExpertId1(@Param("id") long expertId);

    TbJcPlan queryPlanByPlanId(@Param("id")Long schemeId);

    List<LatestPlanReminderDto> queryLatestPlanReminder();

    String queryExpertIdByPlanId(String id);

    List<Map<String, Object>> queryPlanInfo(String pid);

    Map<String, Long> queryFreeOrPayByUidAndPid(@Param("uid") String uid, @Param("pid") String pid);
}