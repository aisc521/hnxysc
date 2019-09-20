package com.zhcdata.jc.service;

import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.jc.dto.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    List<PlanResult1> queryPlanByExpertId(String id, String planId);

    /**
     * 正在进行的方案
     * @param expert
     * @param status
     * @return
     */
    List<TbJcPlan> queryPlanList(String expert, String status);

    List<TbScoreResult> queryScore(String matchId);

    List<TbSPFInfo> querySPFList(String matchId);

    int updateStatus(String isRight, String planHit, String id);

    List<PlanResult2> queryPlanById(String id);

    List<PlanResult1> queryPlanId(String id);
}
