package com.zhcdata.db.mapper;

import com.github.pagehelper.PageInfo;
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

    int updateStatus(@Param("isRight") String isRight, @Param("planHit") String planHit, @Param("id") String id,@Param("flag") String flag,@Param("orderBy")Integer orderBy);

    List<PlanResult2> queryPlanById(@Param("id") String id);

    List<PlanResult1> queryPlanId(@Param("planId") String planId);

    List<PlanResult1> queryPlanByExpertId1(@Param("id") long expertId);

    TbJcPlan queryPlanByPlanId(@Param("id")Long schemeId);

    TbJcPlan queryPlanByPlanIdAndUserId(@Param("id")Long schemeId,@Param("userId")Long userId);

    List<LatestPlanReminderDto> queryLatestPlanReminder();

    String queryExpertIdByPlanId(String id);

    List<Map<String, Object>> queryPlanInfo(@Param("pid") String pid,@Param("matchPlanType") String matchPlanType);

    Map<String, Long> queryFreeOrPayByUidAndPid(@Param("uid") String uid, @Param("pid") String pid);

    List<PlanResult2> queryPlanByIdandUser(@Param("id")String id, @Param("uid")String uid);

    void updateStatusPlanById(@Param("id") String id,@Param("status") int status);

    int updateStatusPlanByIdAndStatus(@Param("id") String id,@Param("status") int status,@Param("oldStatus") int oldStatus);

    List<TbJcPlan> queryPlanListJxAndZs();

    List<TbJcPlan> queryPlanListSale();

    List<PlanResult1> queryPlanByExpertIdForXg(@Param("idList")String[] pIdList);

    List<PlanResult1> queryPlanByExpertIdForXgAndUser(@Param("idList")String[] pIdList, @Param("userId")String userId);

    String queryMatchStatus(@Param("matchId")String matchId);

    List<PlanIdDto> selectPlanIdByMatchId(@Param("matchId")String matchId,@Param("matchStatus")String matchStatus);

    QueryPlanByMatchIdDto queryPlanInfoByPlanId(@Param("planId")String planId);


    QueryPlanByMatchIdDto queryPlanInfoByPlanIdandUserId(@Param("planId")String planId, @Param("userId")String userId);

    JcSchedule queryPolyGoal(@Param("matchId") String matchId);

    List<PlanResult1> queryPlanByExpertIdForExpert(@Param("id")long id,@Param("planId") String planId, @Param("userId")String userId);

    TbJcPlan queryOnePlan(@Param("expertId")String expertId);

    List<PlanResult1> queryHotPlan(@Param("type")String type,@Param("userId")String userId);

    List<QueryPlanByMatchIdDto> queryPlanByPlanIdList(@Param("planIdDtoList")String[] planIdDtoList,@Param("orderInfo")String orderInfo);

    List<QueryPlanByMatchIdDto> queryPlanInfoByPlanIdandUserIdList(@Param("planIdDtoList")String[] planIdDtoList, @Param("userId")String userId,@Param("orderInfo")String orderInfo);
}