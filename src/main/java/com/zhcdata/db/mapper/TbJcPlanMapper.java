package com.zhcdata.db.mapper;

import com.zhcdata.db.model.JcSchedule;
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

    ScoreDto queryScore(@Param("matchId") long matchId);

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

    List<PlanResult2> queryPlanByIdandUser(@Param("id")String id, @Param("uid")String uid);

    void updateStatusPlanById(@Param("id") String id);

    List<TbJcPlan> queryPlanListJxAndZs();

    List<PlanResult1> queryPlanByExpertIdForXg(@Param("id")String pIdList);

    List<PlanResult1> queryPlanByExpertIdForXgAndUser(@Param("id")String pIdList, @Param("userId")String userId);

    List<PlanIdDto> selectPlanIdByMatchId(@Param("matchId")String matchId);

    QueryPlanByMatchIdDto queryPlanInfoByPlanId(@Param("planId")String planId);


    QueryPlanByMatchIdDto queryPlanInfoByPlanIdandUserId(@Param("planId")String planId, @Param("userId")String userId);

    JcSchedule queryPolyGoal(@Param("matchId") String matchId);

    List<PlanResult1> queryPlanByExpertIdForExpert(@Param("id")long id,@Param("planId") String planId, @Param("userId")String userId);
}