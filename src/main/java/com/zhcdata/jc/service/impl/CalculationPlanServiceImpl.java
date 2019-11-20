package com.zhcdata.jc.service.impl;

import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.db.model.TbJcExpert;
import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.db.model.TbJcPurchaseDetailed;
import com.zhcdata.jc.dto.MatchPlanResult;
import com.zhcdata.jc.dto.ScoreDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.*;
import com.zhcdata.jc.tools.ExpertLevelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/11/4 11:12
 */
@Service
public class CalculationPlanServiceImpl implements CalculationPlanService {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    TbPlanService tbPlanService;

    @Resource
    TbJcMatchService tbJcMatchService;
    @Resource
    TbJcExpertService tbJcExpertService;

    @Resource
    PayService payService;

    @Resource
    ExpertLevelUtils expertLevelUtils;
    @Resource
    TbJcPurchaseDetailedService purchaseDetailedService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void calculationPlan(List<TbJcPlan> planResults) throws BaseException {
        for (int i = 0; i < planResults.size(); i++) {

            try {
                int result = 1;         //标识该方案是否中出,有一场未中,就是不中
                int z_count = 0;        //已中方案数量 例如推3中1,推3中2
                int ed=0;               //该方案已结束的比赛
                int start=0;            //该方案已经开始的个数
                List<MatchPlanResult> matchPlanResults = tbJcMatchService.queryList(String.valueOf(planResults.get(i).getId())); //该方案的赛事信息
                if (matchPlanResults != null && matchPlanResults.size() > 0) {
                    for (int k = 0; k < matchPlanResults.size(); k++) {
                        String planInfo = matchPlanResults.get(k).getPlanInfo();

                        if(matchPlanResults.get(k).getId().equals("235")){
                            String sd="";
                        }

                        ScoreDto scoreDto = tbPlanService.queryScore(matchPlanResults.get(k).getMatchId()); //该赛事得分信息
                        if (scoreDto != null) {
                            //判断方案状态
                            //if (!scoreDto.getStatusType().equals("finished") && !scoreDto.getStatusType().equals("notstarted")) {
                            //    flag = "1";
                            //}

                            if(scoreDto.getStatusType().equals("finished")) {
                                ed += 1;        //已结束个数
                            }

                            if(scoreDto.getStatusType().equals("'inprogress'")) {
                                start += 1;        //已经开始的个数
                            }


                            if (scoreDto.getStatusType().equals("finished")) {
                                int hScore1 = Integer.parseInt(scoreDto.getHomeScore());
                                int vScore1 = Integer.parseInt(scoreDto.getGuestScore());
                                //该赛事已结束，计算方案
                                int hScore = Integer.parseInt(scoreDto.getHomeScore());
                                int vScore = Integer.parseInt(scoreDto.getGuestScore());
                                int z = 0;                                             //胜平负和让球胜平负,有一个中了,就算中

                                String spf = planInfo.split("\\|")[0];          //胜平负
                                String rqspf = planInfo.split("\\|")[1];        //让球胜平负
                                if (!spf.equals("0,0,0")) {
                                    if (!spf.split(",")[0].equals("0")) {
                                        //买胜
                                        if (hScore > vScore) {
                                            z = 1;
                                        }
                                    }

                                    if (!spf.split(",")[1].equals("0")) {
                                        //买平
                                        if (hScore == vScore) {
                                            z = 1;
                                        }
                                    }

                                    if (!spf.split(",")[2].equals("0")) {
                                        //买负
                                        if (hScore < vScore) {
                                            z = 1;
                                        }
                                    }
                                } else if(!rqspf.equals("0,0,0")) {
                                    JcSchedule jcSchedule = tbPlanService.queryPolyGoal(matchPlanResults.get(k).getMatchId());
                                    if (jcSchedule != null) {
                                        int rq = (new Double(jcSchedule.getPolygoal())).intValue();
                                        hScore = hScore + rq;
                                        if (!rqspf.split(",")[0].equals("0")) {
                                            //买胜
                                            if (hScore > vScore) {
                                                z = 1;
                                            }
                                        }

                                        if (!rqspf.split(",")[1].equals("0")) {
                                            //买平
                                            if (hScore == vScore) {
                                                z = 1;
                                            }
                                        }

                                        if (!rqspf.split(",")[2].equals("0")) {
                                            //买负
                                            if (hScore < vScore) {
                                                z = 1;
                                            }
                                        }
                                    } else {
                                        //未查到让球数
                                        LOGGER.info("让球胜平负查询让球数为空,比赛id为:" + matchPlanResults.get(k).getMatchId());
                                    }
                                }else {
                                    //胜平负、让球胜平负值都为0
                                    LOGGER.info("方案(id:" + planResults.get(i).getId() + "),赛事(id:" + matchPlanResults.get(k).getMatchId() + ")未选择胜平负,无法计算");
                                }

                                if(z==0){
                                    result = 0;     //有一场赛事未中,该方案未中
                                }else if(z==1){
                                    z_count += 1;   //当前方案中的赛事,中一场就+1
                                }

                                //修改当前赛事
                                tbJcMatchService.updateStatus(String.valueOf(z), hScore1 + ":" + vScore1, matchPlanResults.get(k).getId());
                            }
                        }
                    }

                    if (matchPlanResults.size()==ed) {
                        //0 已结束 1 进行中 2 在售
                        tbPlanService.updateStatusPlanById(String.valueOf(planResults.get(i).getId()),0);
                    }else if(ed > 0 && ed < matchPlanResults.size()){
                        tbPlanService.updateStatusPlanById(String.valueOf(planResults.get(i).getId()),1);
                    }else if(ed == 0){
                        //全部都不是已经结束的状态
                        //判断是否有进行中的比赛
                        if(start == 0){//没有正在进行中的比赛
                            tbPlanService.updateStatusPlanById(String.valueOf(planResults.get(i).getId()),2);
                        }
                        if(start > 0){//都是进行中的比赛
                            tbPlanService.updateStatusPlanById(String.valueOf(planResults.get(i).getId()),1);
                        }

                    }

                    if (result == 0) {
                        //未中
                        tbPlanService.updateStatus("0", matchPlanResults.size() + "中" + z_count, String.valueOf(planResults.get(i).getId()));
                        TbJcPlan tb = planResults.get(i);
                        //refundFrozenToMoney(tb);
                    } else if (result == 1) {
                        //已中
                        if(matchPlanResults.size()==ed){
                            tbPlanService.updateStatus("1", matchPlanResults.size() + "中" + z_count, String.valueOf(planResults.get(i).getId()));

                            TbJcPlan tb = planResults.get(i);//专家经验值+3
                            UpdateExpert(tb);
                            //扣
                            //deductFrozen(tb);
                        }

                    }
                } else {
                    LOGGER.info("方案(id:" + planResults.get(i).getId() + ")中未查到选择赛事");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updatePlanStatus(TbJcPlan tbJcPlan) throws BaseException {

        int chint = tbPlanService.updateStatusPlanByIdAndStatus(String.valueOf(tbJcPlan.getId()),1,2);

        if(chint<1){
            throw new BaseException("方案id="+tbJcPlan.getId()+"修改失败");
        }

    }

    /**
     * 更新专家经验值
     * @param
     * @throws BaseException
     */
    @Transactional(rollbackFor = Exception.class)
    public void UpdateExpert( TbJcPlan tb) throws BaseException {
        //更新专家经验  + 3
        TbJcExpert tbJcExpert = tbJcExpertService.queryExpertDetailsById(Integer.parseInt(String.valueOf(tb.getAscriptionExpert())));
        Integer pop = Integer.valueOf(String.valueOf(tbJcExpert.getExperience()));
        if(pop == null){
            pop = 0;
        }
        Integer pop1 = Integer.valueOf(String.valueOf(tbJcExpert.getStartExperience()));
        String dj = expertLevelUtils.expertLeve(pop + 3 + pop1);
        tbJcExpert.setGrade(Long.valueOf(dj));
        tbJcExpert.setExperience(Long.valueOf(pop + 3));
        Example example1 = new Example(TbJcExpert.class);
        example1.createCriteria().andEqualTo("id",tbJcExpert.getId());

        int h = tbJcExpertService.updateByExample(tbJcExpert,example1);
        if(h <= 0){
            throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                    ProtocolCodeMsg.UPDATE_FAILE.getMsg());
        }
        LOGGER.error("专家 ：" + tbJcExpert.getNickName() + "=增加经验值3成功" );
    }

    /**
     * 退款
     */
    @Transactional(rollbackFor = Exception.class)
    public void refundFrozenToMoney(TbJcPlan planResults) throws BaseException {
        Map result = new HashMap();
        List<TbJcPurchaseDetailed> tbJcPurchaseDetailedList = purchaseDetailedService.queryTbJcPurchaseDetailedByPlanId(planResults.getId());
        if(tbJcPurchaseDetailedList.size() > 0){
            for(int h = 0; h < tbJcPurchaseDetailedList.size(); h++){
                TbJcPurchaseDetailed tbJcPurchaseDetailed = tbJcPurchaseDetailedList.get(h);
                //判断是否是冻结状态  以及订单号 是否是 冻结的订单号
                String pay_status = String.valueOf(tbJcPurchaseDetailed.getPayStatus());
                String order_id = tbJcPurchaseDetailed.getOrderId();
                String[] order = order_id.split("-");
                String payType = String.valueOf(tbJcPurchaseDetailed.getPayType());
                String remark = "";
                if("20".equals(payType)){
                   // remark = "微信支付-方案未中退款";
                    remark = "方案未中退款";
                    if("1".equals(pay_status) && "JCZF".equals(order[0])){//退款
                        result = payService.refundFrozenToMoney(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(), BigDecimal.valueOf(tbJcPurchaseDetailed.getBuyMoney()),remark,tbJcPurchaseDetailed.getSrc());
                    }
                }
                if("21".equals(payType)){
                    //remark = "支付宝支付-方案未中退款";
                    remark = "方案未中退款";
                    if("1".equals(pay_status) && "JCZF".equals(order[0])){//退款
                        result = payService.refundFrozenToMoney(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(), BigDecimal.valueOf(tbJcPurchaseDetailed.getBuyMoney()),remark,tbJcPurchaseDetailed.getSrc());
                    }
                }
                if("22".equals(payType)){
                   // remark = "微信支付-方案未中退款";
                    remark = "方案未中退款";
                    if("1".equals(pay_status) && "JCZF".equals(order[0])){//退款
                        result = payService.refundFrozenToMoney(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(), BigDecimal.valueOf(tbJcPurchaseDetailed.getBuyMoney()),remark,tbJcPurchaseDetailed.getSrc());
                    }
                }
                if("0".equals(payType)){
                    //remark = "余额支付-方案未中退款";
                    remark = "方案未中退款";
                    if("1".equals(pay_status) && "JCZF".equals(order[0])){//退款
                        result = payService.refundFrozenToMoney(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(), BigDecimal.valueOf(tbJcPurchaseDetailed.getBuyMoney()),remark,tbJcPurchaseDetailed.getSrc());
                    }
                }
                if("99".equals(payType)){
                    //remark = "点播卡支付-方案未中退款";
                    remark = "方案未中退款";
                    if("1".equals(pay_status) && "JCZF".equals(order[0])){//退款
                        result = payService.refundDiscount(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(),"1",remark,tbJcPurchaseDetailed.getSrc());
                    }
                }
                String resCode = String.valueOf(result.get("resCode"));
                if("000000".equals(resCode)){//退款成功
                    //更新订单表信息
                    tbJcPurchaseDetailed.setPayStatus(Long.valueOf(3));
                    Example example = new Example(TbJcPurchaseDetailed.class);
                    example.createCriteria().andEqualTo("id",tbJcPurchaseDetailed.getId());
                    int j = purchaseDetailedService.updateByExampleSelective(tbJcPurchaseDetailed,example);
                    if(j <= 0){
                        throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                                ProtocolCodeMsg.UPDATE_FAILE.getMsg());
                    }
                }
                LOGGER.error("用户 ：" + tbJcPurchaseDetailed.getUserId() + "退款成功====退款金额:" +  tbJcPurchaseDetailed.getBuyMoney() + "退款类型:" + remark);
            }
        }


    }

    /**
     * 扣款
     */
    @Transactional(rollbackFor = Exception.class)
    public void deductFrozen(TbJcPlan tb) throws BaseException {
        Map result = new HashMap();
        List<TbJcPurchaseDetailed> tbJcPurchaseDetailedList = purchaseDetailedService.queryTbJcPurchaseDetailedByPlanId(tb.getId());
        if(tbJcPurchaseDetailedList.size() > 0){
            for(int h = 0; h < tbJcPurchaseDetailedList.size(); h++){
                TbJcPurchaseDetailed tbJcPurchaseDetailed = tbJcPurchaseDetailedList.get(h);
                String remark = "";
                String payType = String.valueOf(tbJcPurchaseDetailed.getPayType());
                if("20".equals(payType)){
                    remark = "微信支付-方案未中退款";
                }
                if("21".equals(payType)){
                    remark = "支付宝支付-方案未中退款";
                }
                if("22".equals(payType)){
                    remark = "微信支付-方案未中退款";
                }
                if("0".equals(payType)){
                    remark = "余额支付-方案未中退款";
                }
                if("99".equals(payType)){
                    remark = "点播卡支付-方案未中退款";
                }
                result = payService.deductFrozen(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(), tbJcPurchaseDetailed.getThirdMoney(),remark,tbJcPurchaseDetailed.getSrc());
                String resCode = String.valueOf(result.get("resCode"));
                if("000000".equals(resCode)){
                    //更新订单表 为支付成功的状态
                    //更新订单表信息
                    tbJcPurchaseDetailed.setPayStatus(Long.valueOf(2));
                    Example example = new Example(TbJcPurchaseDetailed.class);
                    example.createCriteria().andEqualTo("id",tbJcPurchaseDetailed.getId());
                    int j = purchaseDetailedService.updateByExampleSelective(tbJcPurchaseDetailed,example);
                    if(j <= 0){
                        throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                                ProtocolCodeMsg.UPDATE_FAILE.getMsg());
                    }
                }

                LOGGER.error("用户 ：" + tbJcPurchaseDetailed.getUserId() + "扣款成功====扣款金额:" +  tbJcPurchaseDetailed.getBuyMoney() + "扣款类型:" + remark);
            }
        }
    }
}
