package com.zhcdata.jc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhcdata.db.mapper.TbJcPlanMapper;
import com.zhcdata.db.mapper.TbPlayerMapper;
import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.jc.dto.*;
import com.zhcdata.jc.service.TbPlanService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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
    public PageInfo<PlanResult1> queryPlanByExpertId(String id, String planId, String userId,Integer pageNo,Integer pageAmount) {
        PageHelper.startPage(pageNo, pageAmount);

        return new PageInfo<>(tbJcPlanMapper.queryPlanByExpertId(Long.parseLong(id),planId,userId));
    }

    @Override
    public List<TbJcPlan> queryPlanList(String expert, String status) {
        return tbJcPlanMapper.queryPlanList(expert,status);
    }

    @Override
    public ScoreDto queryScore(String matchId) {
        return tbJcPlanMapper.queryScore(Long.parseLong(matchId));
    }

    @Override
    public SPFListDto querySPFList(String matchId) {
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
    public List<LatestPlanReminderDto> queryLatestPlanReminder() {
        return tbJcPlanMapper.queryLatestPlanReminder();
    }

    @Override
    public String queryExpertIdByPlanId(String id) {
        return tbJcPlanMapper.queryExpertIdByPlanId(id);
    }

    @Override
    public List<Map<String, Object>> queryPlanInfo(String id) {
        return tbJcPlanMapper.queryPlanInfo(id);
    }

    @Override
    public Map<String, Long> checkFreeOrPayByUidAndPlanId(String uid, String pid) {
        return tbJcPlanMapper.queryFreeOrPayByUidAndPid(uid,pid);
    }

    @Override
    public List<PlanResult2> queryPlanByIdandUser(String id, String uid) {
        return tbJcPlanMapper.queryPlanByIdandUser(id,uid);
    }

    @Override
    public List<PlanResult1> queryPlanByExpertIdNoPages(String id, String planId, String userId) {
        return tbJcPlanMapper.queryPlanByExpertId(Long.parseLong(id),planId,userId);
    }

    @Override
    public void updateStatusPlanById(String id) {
        tbJcPlanMapper.updateStatusPlanById(id);
    }

    @Override
    public List<TbJcPlan> queryPlanListJxAndZs() {
        return tbJcPlanMapper.queryPlanListJxAndZs();
    }

    @Override
    public PageInfo<PlanResult1> queryPlanByExpertIdForXg(String pIdList, Integer pageNo, int pageAmount) {

        PageHelper.startPage(pageNo, pageAmount);
        return new PageInfo<PlanResult1>(tbJcPlanMapper.queryPlanByExpertIdForXg(pIdList));
    }

    @Override
    public PageInfo<PlanResult1> queryPlanByExpertIdForXgAndUser(String pIdList, String userId, Integer pageNo, int pageAmount) {
        PageHelper.startPage(pageNo, pageAmount);
        return new PageInfo<PlanResult1>(tbJcPlanMapper.queryPlanByExpertIdForXgAndUser(pIdList,userId));
    }
}
