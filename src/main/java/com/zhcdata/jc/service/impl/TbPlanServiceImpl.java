package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.TbJcPlanMapper;
import com.zhcdata.db.mapper.TbPlayerMapper;
import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.jc.dto.*;
import com.zhcdata.jc.service.TbPlanService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/20 13:52
 */
@Service
public class TbPlanServiceImpl implements TbPlanService {
    @Resource
    private TbJcPlanMapper tbJcPlanMapper;
    @Override
    public int queryPlanCountByExpertId(String expertId) {
        return tbJcPlanMapper.queryPlanCountByExpertId(expertId);
    }

    @Override
    public List<PlanResult1> queryPlanByExpertId(String id, String planId) {
            return tbJcPlanMapper.queryPlanByExpertId(Long.parseLong(id),planId);
    }

    @Override
    public List<TbJcPlan> queryPlanList(String expert, String status) {
        return tbJcPlanMapper.queryPlanList(expert,status);
    }

    @Override
    public List<TbScoreResult> queryScore(String matchId) {
        return tbJcPlanMapper.queryScore(Long.parseLong(matchId));
    }

    @Override
    public List<TbSPFInfo> querySPFList(String matchId) {
        return tbJcPlanMapper.querySPFList(Long.parseLong(matchId));
    }

    @Override
    public int updateStatus(String isRight, String planHit, String id) {
        return tbJcPlanMapper.updateStatus(isRight,planHit,id);
    }

    @Override
    public List<PlanResult2> queryPlanById(String id) {
        return tbJcPlanMapper.queryPlanById(id);
    }

    @Override
    public List<PlanResult1> queryPlanId(String id) {
        return tbJcPlanMapper.queryPlanId(id);
    }

    @Override
    public List<PlanResult1> queryPlanByExpertId1(String id) {
        return tbJcPlanMapper.queryPlanByExpertId1(Long.parseLong(id));
    }

    @Override
    public TbJcPlan queryPlanByPlanId(Long schemeId) {

        return tbJcPlanMapper.queryPlanByPlanId(schemeId);
    }

    @Override
    public LatestPlanReminderDto queryLatestPlanReminder() {
        return tbJcPlanMapper.queryLatestPlanReminder();
    }
}
