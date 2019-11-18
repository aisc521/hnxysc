package com.zhcdata.jc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhcdata.db.mapper.TbJcPlanMapper;
import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.db.model.TbJcRecordFocus;
import com.zhcdata.jc.dto.*;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.TbPlanService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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
        List<PlanResult1> list = tbJcPlanMapper.queryPlanByExpertId(Long.parseLong(id),planId,userId);
        return new PageInfo<>(list);
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
    public void updateStatusPlanById(String id,int status) {
        tbJcPlanMapper.updateStatusPlanById(id,status);
    }

    @Override
    public int updateStatusPlanByIdAndStatus(String s, int status, int oldStatus) {
        return tbJcPlanMapper.updateStatusPlanByIdAndStatus(s,status,oldStatus);
    }

    @Override
    public List<TbJcPlan> queryPlanListJxAndZs() {
        return tbJcPlanMapper.queryPlanListJxAndZs();
    }

    @Override
    public List<TbJcPlan> queryPlanListSale() {
        return tbJcPlanMapper.queryPlanListSale();
    }

    @Override
    public PageInfo<PlanResult1> queryPlanByExpertIdForXg(String[] pIdList, Integer pageNo, int pageAmount) {

        PageHelper.startPage(pageNo, pageAmount);
        List<PlanResult1> list = tbJcPlanMapper.queryPlanByExpertIdForXg(pIdList);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<PlanResult1> queryPlanByExpertIdForXgAndUser(String[] pIdList, String userId, Integer pageNo, int pageAmount) {
        PageHelper.startPage(pageNo, pageAmount);
        List<PlanResult1> list = tbJcPlanMapper.queryPlanByExpertIdForXgAndUser(pIdList,userId);
        return new PageInfo<>(list);
    }

    @Override
    public  PageInfo<PlanIdDto> selectPlanIdByMatchId(String matchId,Integer pageNo,Integer pageAmount) {

        PageHelper.startPage(pageNo, pageAmount);
        List<PlanIdDto> list = tbJcPlanMapper.selectPlanIdByMatchId(matchId);
        return new PageInfo<>(list);
    }

    @Override
    public QueryPlanByMatchIdDto queryPlanInfoByPlanId(String planId) {
        return tbJcPlanMapper.queryPlanInfoByPlanId(planId);
    }

    @Override
    public QueryPlanByMatchIdDto queryPlanInfoByPlanIdandUserId(String planId, String userId) {
        return tbJcPlanMapper.queryPlanInfoByPlanIdandUserId(planId,userId);
    }

    @Override
    public JcSchedule queryPolyGoal(String matchId) {
        return tbJcPlanMapper.queryPolyGoal(matchId);
    }

    @Override
    public PageInfo<PlanResult1> queryPlanByExpertIdForExpert(String id, String planId, String userId, Integer pageNo, int pageAmount) {
        PageHelper.startPage(pageNo, pageAmount);
        List<PlanResult1> list = tbJcPlanMapper.queryPlanByExpertIdForExpert(Long.parseLong(id),planId,userId);
        return new PageInfo<>(list);
    }

    @Override
    public TbJcPlan queryOnePlan(String expertId) {
        return tbJcPlanMapper.queryOnePlan(expertId);
    }

    @Override
    public PageInfo<PlanResult1> queryHotPlan(String userId, Integer pageNo, int pageAmount) {
        PageHelper.startPage(pageNo, pageAmount);
        List<PlanResult1> list = tbJcPlanMapper.queryHotPlan(userId);
        return new PageInfo<>(list);
    }

    @Override
    public int updatePlanByPlanId(TbJcPlan tbJcPlan) throws BaseException {
        Example example1 = new Example(TbJcPlan.class);
        example1.createCriteria().andEqualTo("id",tbJcPlan.getId());
        int i = tbJcPlanMapper.updateByExample(tbJcPlan,example1);
        if(i <= 0){
            throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                    ProtocolCodeMsg.UPDATE_FAILE.getMsg());
        }
        return i;
    }

    @Override
    public List<QueryPlanByMatchIdDto> queryPlanByPlanIdList(String[] planIdDtoList) {
        return tbJcPlanMapper.queryPlanByPlanIdList(planIdDtoList);
    }

    @Override
    public List<QueryPlanByMatchIdDto> queryPlanInfoByPlanIdandUserIdList(String[] planIdDtoList, String userId) {
        return tbJcPlanMapper.queryPlanInfoByPlanIdandUserIdList(planIdDtoList,userId);
    }


}
