package com.zhcdata.jc.quartz.job.redis;

import com.zhcdata.db.model.TbJcExpert;
import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.db.model.TbJcPurchaseDetailed;
import com.zhcdata.jc.dto.MatchPlanResult;
import com.zhcdata.jc.dto.SPFListDto;
import com.zhcdata.jc.dto.TbSPFInfo;
import com.zhcdata.jc.dto.TbScoreResult;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.*;
import com.zhcdata.jc.tools.RedisUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 计算方案 是否命中
 * @Author cuishuai
 * @Date 2019/9/20 14:27
 */
public class CalculationPlanJob implements Job {
    @Resource
    private RedisUtils redisUtils;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private TbPlanService tbPlanService;

    @Resource
    private TbJcMatchService tbJcMatchService;
    @Resource
    private TbJcExpertService tbJcExpertService;

    @Resource
    private PayService payService;

    @Resource
    private TbJcPurchaseDetailedService purchaseDetailedService;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            LOGGER.info("[计算方案是否命中定时任务开启]" + df.format(new Date()));
            //List<TbJcPlan> planResults = tbPlanService.queryPlanList(null, "1"); //正在进行的方案列表 和在售的
            List<TbJcPlan> planResults = tbPlanService.queryPlanListJxAndZs(); //正在进行的方案列表 和在售的
            if (planResults != null && planResults.size() > 0) {
                for (int i = 0; i < planResults.size(); i++) {

                    int result = 1;     //标识该方案是否中出,有一场未中,就是不中
                    int z_count = 0;      //已中方案数量 例如推3中1,推3中2
                    List<MatchPlanResult> matchPlanResults = tbJcMatchService.queryList(String.valueOf(planResults.get(i).getId())); //该方案的赛事信息
                    if (matchPlanResults != null && matchPlanResults.size() > 0) {
                        String flag = "0";// 0 结束  1开始

                        for (int k = 0; k < matchPlanResults.size(); k++) {
                            String planInfo = matchPlanResults.get(k).getPlanInfo();

                            List<TbScoreResult> scoreResults = tbPlanService.queryScore(matchPlanResults.get(k).getMatchId()); //该赛事得分信息
                            if (scoreResults != null && scoreResults.size() > 0) {
                                if (!scoreResults.get(0).getStatusType().equals("finished") && !scoreResults.get(0).getStatusType().equals("notstarted")) {
                                    flag = "1";
                                }

                                if (scoreResults.get(0).getStatusType().equals("finished")) {
                                    //该赛事已结束，计算方案
                                    Double hScore = Double.valueOf(scoreResults.get(0).getValue());
                                    Double vScore = Double.valueOf(scoreResults.get(1).getValue());
                                    int z = 0; //胜平负和让球胜平负,有一个中了,就算中

                                    String spf = planInfo.split("\\|")[0];      //胜平负
                                    String rqspf = planInfo.split("\\|")[1];    //让球胜平负
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
                                    } else {
                                        SPFListDto spfs = tbPlanService.querySPFList(matchPlanResults.get(k).getMatchId());
                                        if (spfs != null ) {
                                            hScore = hScore + Double.parseDouble(spfs.getAwayTeamRangballs());
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
                                        }
                                    }

                                    if (z == 0) {
                                        //胜平负,都没中
                                        result = 0;
                                    } else {
                                        //推荐比赛，中一场+1
                                        z_count += 1;
                                        result = 1;
                                    }

                                    //处理单场比赛结果、中奖状态
                                    tbJcMatchService.updateStatus(String.valueOf(z), hScore + ":" + vScore,matchPlanResults.get(k).getMatchId());

                                }else{
                                    result = 2;
                                }
                                //未结束的不处理
                            }
                        }

                        if("1".equals(flag)){
                            tbPlanService.updateStatusPlanById(String.valueOf(planResults.get(i).getId()));
                        }

                        if (result == 0) {
                            //未中
                            tbPlanService.updateStatus("0", matchPlanResults.size() + "中" + z_count, String.valueOf(planResults.get(i).getId()));
                            TbJcPlan tb = planResults.get(i);
                            //refundFrozenToMoney(tb);
                        } else if(result == 1){
                            //已中
                            tbPlanService.updateStatus("1", matchPlanResults.size() + "中" + z_count, String.valueOf(planResults.get(i).getId()));

                            TbJcPlan tb = planResults.get(i);//专家经验值+3
                            UpdateExpert(tb);
                            //扣款

                            //deductFrozen(tb);
                        }
                    }
                }
            }
            LOGGER.info("[计算方案是否命中定时任务结束]" + df.format(new Date()) + " 共：" + planResults.size() + "个方案");

        } catch (Exception ex) {
            LOGGER.error("计算方案是否命中定时任务异常：" + ex);
        }
    }

    /**
     * 更新专家经验值
     * @param
     * @throws BaseException
     */
    public void UpdateExpert( TbJcPlan tb) throws BaseException {
        //更新专家经验  + 3
        TbJcExpert tbJcExpert = tbJcExpertService.queryExpertDetailsById(Integer.parseInt(String.valueOf(tb.getAscriptionExpert())));
        Integer pop = Integer.valueOf(String.valueOf(tbJcExpert.getExperience()));
        if(pop == null){
            pop = 0;
        }
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
                    remark = "微信支付-方案未中退款";
                    if("1".equals(pay_status) && "JCZF".equals(order[0])){//退款
                        result = payService.refundFrozenToMoney(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(), BigDecimal.valueOf(tbJcPurchaseDetailed.getBuyMoney()),remark,tbJcPurchaseDetailed.getSrc());
                    }
                }
                if("21".equals(payType)){
                    remark = "支付宝支付-方案未中退款";
                    if("1".equals(pay_status) && "JCZF".equals(order[0])){//退款
                        result = payService.refundFrozenToMoney(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(), BigDecimal.valueOf(tbJcPurchaseDetailed.getBuyMoney()),remark,tbJcPurchaseDetailed.getSrc());
                    }
                }
                if("22".equals(payType)){
                    remark = "微信支付-方案未中退款";
                    if("1".equals(pay_status) && "JCZF".equals(order[0])){//退款
                        result = payService.refundFrozenToMoney(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(), BigDecimal.valueOf(tbJcPurchaseDetailed.getBuyMoney()),remark,tbJcPurchaseDetailed.getSrc());
                    }
                }
                if("0".equals(payType)){
                    remark = "余额支付-方案未中退款";
                    if("1".equals(pay_status) && "JCZF".equals(order[0])){//退款
                        result = payService.refundFrozenToMoney(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(), BigDecimal.valueOf(tbJcPurchaseDetailed.getBuyMoney()),remark,tbJcPurchaseDetailed.getSrc());
                    }
                }
                if("99".equals(payType)){
                    remark = "点播卡支付-方案未中退款";
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
                result = payService.deductFrozen(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(), BigDecimal.valueOf(tbJcPurchaseDetailed.getThirdMoney()),remark,tbJcPurchaseDetailed.getSrc());
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
