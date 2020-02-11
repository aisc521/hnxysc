package com.zhcdata.jc.service.impl;

import com.zhcdata.db.model.TbJcExpert;
import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.db.model.TbJcPurchaseDetailed;
import com.zhcdata.jc.dto.MatchPlanResult;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/11/4 11:12
 */
@Service
public class CalculationPlanNewServiceImpl implements CalculationPlanNewService{
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
    public void calculationPlan(TbJcPlan tbJcPlan) throws BaseException {

        //判断这个方案是否全部开完奖
        Map<String,Integer> map = tbJcMatchService.queryMatchStatus(tbJcPlan.getId());
        LOGGER.info("需要处理的方案 id = "+tbJcPlan.getId()+"map a="+String.valueOf(map.get("a")+" map b "+String.valueOf(map.get("b"))));
        if(!String.valueOf(map.get("a")).equals(String.valueOf(map.get("b")))){
            LOGGER.info("该方案 id = "+tbJcPlan.getId()+"  有比赛没有出结果，无法计算");
            return;
        }
        LOGGER.info("需要处理的方案 id = "+tbJcPlan.getId());
        List<MatchPlanResult> matchPlanResultsList = tbJcMatchService.queryList(String.valueOf(tbJcPlan.getId())); //该方案的赛事信息
        int z_count = 0;        //已中方案数量 例如推3中1,推3中2
        int cancel = 0 ;        //存在有取消，推迟和腰斩的比赛标识
        for(MatchPlanResult matchPlanResult :matchPlanResultsList){

            int z = 0;              //胜平负和让球胜平负,有一个中了,就算中
            String planInfo = matchPlanResult.getPlanInfo();
            int homeScore = Integer.parseInt(matchPlanResult.getHomeScore());
            int guestScore = Integer.parseInt(matchPlanResult.getGuestScore());
            String allSCore = homeScore+":"+guestScore;
            int rq = new BigDecimal(matchPlanResult.getPolyGoal()).intValue();

            LOGGER.error("比赛计算-比赛id="+matchPlanResult.getMatchId()+" 比分="+allSCore+" 让"+rq+" 球 比赛状态="+matchPlanResult.getMatchState());
            if("-1".equals(matchPlanResult.getMatchState())){
                String spf = planInfo.split("\\|")[0];          //胜平负
                String rqspf = planInfo.split("\\|")[1];        //让球胜平负
                if(isWinAwad(spf,homeScore,guestScore ,0)||isWinAwad(rqspf,homeScore,guestScore ,rq)){
                    z_count = z_count+1;
                    z = 1;
                }
            }else{
                z_count = z_count+1;
                z = 1;
                cancel = 1;
                allSCore = "vs";//取消的比赛比分改成vs
            }

            tbJcMatchService.updateStatus(String.valueOf(z), allSCore, matchPlanResult.getId());
         }
        //0 已结束 1 进行中 2 在售
        tbPlanService.updateStatusPlanById(String.valueOf(tbJcPlan.getId()),0);
        if(z_count > 0&&matchPlanResultsList.size() == z_count){

            UpdateExpert(tbJcPlan);
            if(cancel == 1){ //退款
                LOGGER.info("方案id="+tbJcPlan.getId()+" 退款操作-比赛异常退款");
                tbPlanService.updateStatus("1", matchPlanResultsList.size() + "中" + z_count, String.valueOf(tbJcPlan.getId()),"0");
                refundFrozenToMoney(tbJcPlan,"2");
            }else{//扣款
                LOGGER.info("方案id="+tbJcPlan.getId()+" 扣款操作-方案命中");
                tbPlanService.updateStatus("1", matchPlanResultsList.size() + "中" + z_count, String.valueOf(tbJcPlan.getId()),"1");
                deductFrozen(tbJcPlan);
            }
        }else{
            LOGGER.info("方案id="+tbJcPlan.getId()+" 退款操作-方案末中");
            tbPlanService.updateStatus("0", matchPlanResultsList.size() + "中" + z_count, String.valueOf(tbJcPlan.getId()),"0");
            refundFrozenToMoney(tbJcPlan,"1");
        }
    }
    public boolean isWinAwad(String code,int homeScore,int guestScore ,int rq) throws BaseException {
        String []codeArray = code.split(",");
        if(codeArray.length!=3){
            throw  new BaseException("投注格式非法,投注格式="+code);
        }
        homeScore = homeScore + rq; //主队比赛加让球
        if ("0,0,0".equals(code)) {
            return false;
        }
        if(judgeZero(codeArray[0])){
            if (homeScore > guestScore) {
               return true;
            }
        }
        if(judgeZero(codeArray[1])){
            if (homeScore == guestScore) {
                return true;
            }
        }
        if(judgeZero(codeArray[2])){
            if (homeScore < guestScore) {
                return true;
            }
        }

        return false;

    }

    public boolean judgeZero(String str){
        if(!"0".equals(str)){
            return true;
        }
        return false;
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
     * type 1 方案未中退款，2方案里有取消比赛的退款
     *
     */
    @Transactional(rollbackFor = Exception.class)
    public void refundFrozenToMoney(TbJcPlan planResults,String type) throws BaseException {
        Map result = new HashMap();
        List<TbJcPurchaseDetailed> tbJcPurchaseDetailedList = purchaseDetailedService.queryTbJcPurchaseDetailedByPlanId(planResults.getId());
        if(tbJcPurchaseDetailedList.size() > 0){
            for(int h = 0; h < tbJcPurchaseDetailedList.size(); h++){

                TbJcPurchaseDetailed tbJcPurchaseDetailed = tbJcPurchaseDetailedList.get(h);
                LOGGER.info("需要处理的退款订单 orderId= "+tbJcPurchaseDetailed.getOrderId());
                if("1".equals(tbJcPurchaseDetailed.getFirst())){//判断是否是首单   首单  不退款
                    LOGGER.info("首单订单，不退款:" + "用户id:" + tbJcPurchaseDetailed.getUserId() + "===" + "订单id:" + tbJcPurchaseDetailed.getOrderId());
                    //执行扣款操作
                    deductFrozenOne(tbJcPurchaseDetailed);
                    continue;
                }
                //判断是否是免费的点播卡支付  如果是 不退款
                if("4".equals(tbJcPurchaseDetailed.getPlanPayType()) && "97".equals(tbJcPurchaseDetailed.getPayType()) ){
                    LOGGER.info("首单订单，不退款:" + "用户id:" + tbJcPurchaseDetailed.getUserId() + "===" + "订单id:" + tbJcPurchaseDetailed.getOrderId());
                    //执行扣款操作
                    deductFrozenOne(tbJcPurchaseDetailed);
                    continue;
                }
                //判断是否是冻结状态  以及订单号 是否是 冻结的订单号
                String pay_status = String.valueOf(tbJcPurchaseDetailed.getPayStatus());
                String order_id = tbJcPurchaseDetailed.getOrderId();
                String[] order = order_id.split("-");
                String payType = String.valueOf(tbJcPurchaseDetailed.getPayType());
                //判断是否支付成功
                if("0".equals(tbJcPurchaseDetailed.getPayStatus())){//未成功
                    LOGGER.error("用户 ：" + tbJcPurchaseDetailed.getUserId() + "退款====付款未成功:" +  tbJcPurchaseDetailed.getBuyMoney() + "订单号:" + tbJcPurchaseDetailed.getOrderId());
                    continue;
                }
                String remark = "";
                if("1".equals(type)){
                    remark = "方案未中退款";
                }else if("2".equals(type)){
                    remark = "比赛取消退款";
                }
                if("20".equals(payType)){
                    //remark = "方案未中退款";
                    if("1".equals(pay_status) && "JCZF".equals(order[0])){//退款
                        result = payService.refundFrozenToMoney(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(), BigDecimal.valueOf(tbJcPurchaseDetailed.getBuyMoney()),remark,tbJcPurchaseDetailed.getSrc());
                    }
                }
                if("21".equals(payType)){
                    //remark = "方案未中退款";
                    if("1".equals(pay_status) && "JCZF".equals(order[0])){//退款
                        result = payService.refundFrozenToMoney(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(), BigDecimal.valueOf(tbJcPurchaseDetailed.getBuyMoney()),remark,tbJcPurchaseDetailed.getSrc());
                    }
                }
                if("22".equals(payType)){
                    //remark = "方案未中退款";
                    if("1".equals(pay_status) && "JCZF".equals(order[0])){//退款
                        result = payService.refundFrozenToMoney(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(), BigDecimal.valueOf(tbJcPurchaseDetailed.getBuyMoney()),remark,tbJcPurchaseDetailed.getSrc());
                    }
                }
                if("0".equals(payType)){
                    //remark = "方案未中退款";
                    if("1".equals(pay_status) && "JCZF".equals(order[0])){//退款
                        result = payService.refundFrozenToMoney(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(), BigDecimal.valueOf(tbJcPurchaseDetailed.getBuyMoney()),remark,tbJcPurchaseDetailed.getSrc());
                    }
                }
                //普通点播卡退款
                if("99".equals(payType)){
                    //remark = "方案未中退款";
                    if("1".equals(pay_status) && "JCZF".equals(order[0])){//退款
                        result = payService.refundDiscount(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(),"1",remark,tbJcPurchaseDetailed.getSrc());
                    }
                }
                //点播卡周卡退款
                if("98".equals(payType)){
                    //remark = "方案未中退款";
                    if("1".equals(pay_status) && "JCZF".equals(order[0])){//退款
                        result = payService.refundDiscount(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(),"5",remark,tbJcPurchaseDetailed.getSrc());
                    }
                }
                if("77".equals(payType)&&"0".equals(tbJcPurchaseDetailed.getCouponType())){
                    //remark = "优惠卷上来直接扣除";
                    if("1".equals(pay_status) && "JCZF".equals(order[0])){//优惠卷上来直接扣除
                        result = payService.deductCoupons(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getCouponId(),tbJcPurchaseDetailed.getOrderId(),"1",tbJcPurchaseDetailed.getSrc());
                    }
                }
                String resCode = String.valueOf(result.get("resCode"));
                if("000000".equals(resCode) || "109023".equals(resCode) || "010124".equals(resCode)){//退款成功
                    //更新订单表信息
                    tbJcPurchaseDetailed.setPayStatus(Long.valueOf(3));
                    tbJcPurchaseDetailed.setAwardStatus(Long.valueOf(0));
                    tbJcPurchaseDetailed.setUpdateTime(new Date());
                    Example example = new Example(TbJcPurchaseDetailed.class);
                    example.createCriteria().andEqualTo("id",tbJcPurchaseDetailed.getId());
                    int j = purchaseDetailedService.updateByExampleSelective(tbJcPurchaseDetailed,example);
                    if(j <= 0){
                        throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                                ProtocolCodeMsg.UPDATE_FAILE.getMsg());
                    }
                    LOGGER.error("用户 ：" + tbJcPurchaseDetailed.getUserId() + "退款成功====退款金额:" +  tbJcPurchaseDetailed.getBuyMoney() + "退款类型:" + remark + "方案id:" + tbJcPurchaseDetailed.getSchemeId());

                }else{
                    LOGGER.error("用户 ：" + tbJcPurchaseDetailed.getUserId() + "退款失败====退款金额:" +  tbJcPurchaseDetailed.getBuyMoney() + "退款类型:" + remark + "订单号:" + tbJcPurchaseDetailed.getOrderId() + "方案id:" + tbJcPurchaseDetailed.getSchemeId());

                }
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
                LOGGER.info("需要处理的扣款订单 orderId= "+tbJcPurchaseDetailed.getOrderId());
                String remark = "";
                String payType = String.valueOf(tbJcPurchaseDetailed.getPayType());
                if("20".equals(payType)){
                    remark = "方案已中扣款";
                }
                if("21".equals(payType)){
                    remark = "方案已中扣款";
                }
                if("22".equals(payType)){
                    remark = "方案已中扣款";
                }
                if("0".equals(payType)){
                    remark = "方案已中扣款";
                }
                if("99".equals(payType)){
                    remark = "方案已中扣款";
                }
                //判断是否支付成功
                if("0".equals(tbJcPurchaseDetailed.getPayStatus())){//未成功
                    LOGGER.error("用户 ：" + tbJcPurchaseDetailed.getUserId() + "扣款====付款未成功:" +  tbJcPurchaseDetailed.getBuyMoney() + "扣款类型:" + remark +  "订单号:" + tbJcPurchaseDetailed.getOrderId() + "方案id:" + tbJcPurchaseDetailed.getSchemeId());
                    continue;
                }
                //判断是否是点播状态
                if("99".equals(tbJcPurchaseDetailed.getPayType().toString())){
                    //更新订单表 为支付成功的状态
                    //更新订单表信息
                    updatePd(tbJcPurchaseDetailed);

                }else{
                    if("77".equals(payType)&&"0".equals(tbJcPurchaseDetailed.getCouponType())){
                        //remark = "优惠卷上来直接扣除";
                        //优惠卷上来直接扣除
                        result = payService.deductCoupons(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getCouponId(),tbJcPurchaseDetailed.getOrderId(),"1",tbJcPurchaseDetailed.getSrc());
                    }
                    result = payService.deductFrozen(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(), new BigDecimal(tbJcPurchaseDetailed.getBuyMoney()),remark,tbJcPurchaseDetailed.getSrc());
                }
                String resCode = String.valueOf(result.get("resCode"));
                if("000000".equals(resCode) || "109024".equals(resCode) || "010124".equals(resCode)){
                    //更新订单表 为支付成功的状态
                    //更新订单表信息
                    updatePd(tbJcPurchaseDetailed);
                    LOGGER.error("用户 ：" + tbJcPurchaseDetailed.getUserId() + "扣款成功====扣款金额:" +  tbJcPurchaseDetailed.getThirdMoney() + "扣款类型:" + remark + "订单号:" + tbJcPurchaseDetailed.getOrderId() + "方案id:" + tbJcPurchaseDetailed.getSchemeId());

                }else{
                    LOGGER.error("用户 ：" + tbJcPurchaseDetailed.getUserId() + "扣款失败====扣款金额:" +  tbJcPurchaseDetailed.getThirdMoney() + "扣款类型:" + remark + "订单号:" + tbJcPurchaseDetailed.getOrderId() + "方案id:" + tbJcPurchaseDetailed.getSchemeId());

                }

            }
        }
    }

    public void updatePd(TbJcPurchaseDetailed tbJcPurchaseDetailed) throws BaseException {
        tbJcPurchaseDetailed.setPayStatus(Long.valueOf(2));
        tbJcPurchaseDetailed.setAwardStatus(Long.valueOf(1));
        tbJcPurchaseDetailed.setUpdateTime(new Date());
        Example example = new Example(TbJcPurchaseDetailed.class);
        example.createCriteria().andEqualTo("id",tbJcPurchaseDetailed.getId());
        int j = purchaseDetailedService.updateByExampleSelective(tbJcPurchaseDetailed,example);
        if(j <= 0){
            throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                    ProtocolCodeMsg.UPDATE_FAILE.getMsg());
        }
        LOGGER.error("用户 ：" + tbJcPurchaseDetailed.getUserId() + "扣款成功====扣款金额:" +  tbJcPurchaseDetailed.getBuyMoney());
    }


    /**
     * 扣款
     */
    @Transactional(rollbackFor = Exception.class)
    public void deductFrozenOne(TbJcPurchaseDetailed tbJcPurchaseDetailed) throws BaseException {
                LOGGER.info("首单订单,扣款操作:" + "用户id:" + tbJcPurchaseDetailed.getUserId() + "===" + "订单id:" + tbJcPurchaseDetailed.getOrderId());
                Map result = new HashMap();
                String remark = "";
                String payType = String.valueOf(tbJcPurchaseDetailed.getPayType());
                if("20".equals(payType)){
                    remark = "方案已中扣款";
                }
                if("21".equals(payType)){
                    remark = "方案已中扣款";
                }
                if("22".equals(payType)){
                    remark = "方案已中扣款";
                }
                if("0".equals(payType)){
                    remark = "方案已中扣款";
                }
                if("99".equals(payType) || "98".equals(payType) || "97".equals(payType)){
                    remark = "方案已中扣款";
                }
                //判断是否是点播状态
                if("99".equals(tbJcPurchaseDetailed.getPayType().toString()) || "98".equals(tbJcPurchaseDetailed.getPayType().toString()) || "97".equals(tbJcPurchaseDetailed.getPayType().toString())){
                    //更新订单表 为支付成功的状态
                    //更新订单表信息
                    updatePd(tbJcPurchaseDetailed);

                }else{
                    result = payService.deductFrozen(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(), new BigDecimal(tbJcPurchaseDetailed.getBuyMoney()),remark,tbJcPurchaseDetailed.getSrc());
                }
                String resCode = String.valueOf(result.get("resCode"));
                if("000000".equals(resCode) || "109024".equals(resCode) || "010124".equals(resCode)){
                    //更新订单表 为支付成功的状态
                    //更新订单表信息
                    updatePd(tbJcPurchaseDetailed);
                    LOGGER.error("首单用户 ：" + tbJcPurchaseDetailed.getUserId() + "扣款成功====扣款金额:" +  tbJcPurchaseDetailed.getThirdMoney() + "扣款类型:" + remark + "订单号:" + tbJcPurchaseDetailed.getOrderId() + "方案id:" + tbJcPurchaseDetailed.getSchemeId());

                }else{
                    LOGGER.error("首单用户 ：" + tbJcPurchaseDetailed.getUserId() + "扣款失败====扣款金额:" +  tbJcPurchaseDetailed.getThirdMoney() + "扣款类型:" + remark + "订单号:" + tbJcPurchaseDetailed.getOrderId() + "方案id:" + tbJcPurchaseDetailed.getSchemeId());

                }

    }

}
