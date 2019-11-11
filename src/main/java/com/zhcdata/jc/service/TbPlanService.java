package com.zhcdata.jc.service;

import com.github.pagehelper.PageInfo;
import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.jc.dto.*;
import com.zhcdata.jc.exception.BaseException;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TbPlanService {
    /**
     * 查询该专家正在进行的方案数量
     * @param s
     * @return
     */
    int queryPlanCountByExpertId(String s);

    /**
     * 查询某专家 所有方案
     * @param id
     * @param o
     * @return
     */
    PageInfo<PlanResult1> queryPlanByExpertId(String id, String planId, String userId,Integer pageNo,Integer pageAmount);

    /**
     * 正在进行的方案
     * @param expert
     * @param status
     * @return
     */
    List<TbJcPlan> queryPlanList(String expert, String status);

    ScoreDto queryScore(String matchId);

    SPFListDto querySPFList(String matchId);

    int updateStatus(String isRight, String planHit, String id);

    List<PlanResult2> queryPlanById(String id);

    List<PlanResult1> queryPlanId(String id);

    List<PlanResult1> queryPlanByExpertId1(String id);

    TbJcPlan queryPlanByPlanId(Long schemeId);

    List<LatestPlanReminderDto>  queryLatestPlanReminder();

    String queryExpertIdByPlanId(String id);

    List<Map<String, Object>> queryPlanInfo(String id);

    Map<String, Long> checkFreeOrPayByUidAndPlanId(String uid, String pid);

    List<PlanResult2> queryPlanByIdandUser(String id, String uid);

    List<PlanResult1> queryPlanByExpertIdNoPages(String id, String planId, String userId);

    void updateStatusPlanById(String s,int status);

    int updateStatusPlanByIdAndStatus(String s,int status,int oldStatus);

    List<TbJcPlan> queryPlanListJxAndZs();

    List<TbJcPlan> queryPlanListSale();

    PageInfo<PlanResult1> queryPlanByExpertIdForXg(String[] pIdList, Integer integer, int i);

    PageInfo<PlanResult1> queryPlanByExpertIdForXgAndUser(String[] pIdList, String userId, Integer integer, int i);

    PageInfo<PlanIdDto> selectPlanIdByMatchId(String matchId,Integer pageNo,Integer pageAmount);

    QueryPlanByMatchIdDto queryPlanInfoByPlanId(String planId);


    QueryPlanByMatchIdDto queryPlanInfoByPlanIdandUserId(String planId, String userId);

    JcSchedule queryPolyGoal(String matchId);

    PageInfo<PlanResult1> queryPlanByExpertIdForExpert(String id, String planId, String userId, Integer integer, int i);

    TbJcPlan queryOnePlan(String expertId);

    PageInfo<PlanResult1> queryHotPlan(String userId, Integer integer, int i);

    int updatePlanByPlanId(TbJcPlan tbJcPlan) throws BaseException;
}
