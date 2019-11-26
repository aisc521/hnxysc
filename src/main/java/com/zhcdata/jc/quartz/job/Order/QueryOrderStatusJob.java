package com.zhcdata.jc.quartz.job.Order;


import com.zhcdata.db.mapper.TbJcPlanMapper;
import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.db.model.TbJcPurchaseDetailed;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.PayService;
import com.zhcdata.jc.service.TbJcPurchaseDetailedService;
import com.zhcdata.jc.service.TbPlanService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 查询竞彩订单状态定时任务
 * @Author cuishuai
 * @Date 2019/10/11 9:27
 */
@Slf4j
@Component
@DisallowConcurrentExecution
public class QueryOrderStatusJob implements Job {

    @Resource
    private TbJcPurchaseDetailedService tbJcPurchaseDetailedService;
    @Resource
    private PayService payService;
    @Resource
    private TbPlanService tbPlanService;
    @Resource
    private TbJcPlanMapper tbJcPlanMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        long start = System.currentTimeMillis();
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String type = dataMap.getString("type"); // 1 为五分钟之内的 2五分钟以后30分钟一次
        List<TbJcPurchaseDetailed> tbJcPurchaseDetailedList  = null;

        log.info(" 需要冻结的订单 start.....type="+type);

        if("1".equals(type)|| StringUtils.isBlank(type)){ //只查五分钟之内的
            //查询状态是0(未支付) 1(冻结状态) /支付方式是（微信Native支付_20,支付宝支付_21,微信H5支付_22）的订单
            tbJcPurchaseDetailedList = tbJcPurchaseDetailedService.queryOrder();
        }else{
            tbJcPurchaseDetailedList = tbJcPurchaseDetailedService.queryOrderFive();
        }

        if(tbJcPurchaseDetailedList == null||tbJcPurchaseDetailedList.size()<1){
            log.info(" 没有需要冻结的订单");
            return;
        }
        handleLogic(tbJcPurchaseDetailedList);
        long end = System.currentTimeMillis();
        log.info(" 需要冻结的订单 end 耗时 "+(end-start)+".....");
    }

    public void handleLogic(List<TbJcPurchaseDetailed> tbJcPurchaseDetailedList){
        for(int i = 0; i < tbJcPurchaseDetailedList.size(); i++){
            TbJcPurchaseDetailed tbJcPurchaseDetailed = tbJcPurchaseDetailedList.get(i);
            Map<String, Object> result = payService.queryOrderStatus(String.valueOf(tbJcPurchaseDetailed.getBuyMoney()),String.valueOf(tbJcPurchaseDetailed.getPayType()),String.valueOf(tbJcPurchaseDetailed.getUserId()),
                    String.valueOf(tbJcPurchaseDetailed.getOrderId()),String.valueOf(tbJcPurchaseDetailed.getSrc()));
            if("4".equals(result.get("status"))){//成功/冻结
                //先判断此订单对应的方案 是否是已停售的状态
                TbJcPlan tbJcPlan = tbPlanService.queryPlanByPlanId(tbJcPurchaseDetailed.getSchemeId(),tbJcPurchaseDetailed.getUserId());
                if(tbJcPlan != null){
                    //判断是否已经是首单
                    //根据状态查询是否是首单 ===查询此用户是否有支付成功的状态的订单
                    Integer tbJcPurchaseDetailedList1 = tbJcPurchaseDetailedService.queryIfHaveSuccessOeder(tbJcPurchaseDetailed.getUserId());
                    if(!"2".equals(String.valueOf(tbJcPlan.getStatus()))){//不是在售状态的方案或者已经买过该定单  执行退款操作
                        operateRefundFrozenToMoney(tbJcPurchaseDetailed,result,4l);//4 不在可售状态退款，
                    } // 首单重复退款
                    else if(new BigDecimal(String.valueOf(result.get("thirdAmount"))).intValue() == 2 && tbJcPurchaseDetailedList1 > 0){//首单两元 但是 此用户已经有支付成功的状态  退款
                        operateRefundFrozenToMoney(tbJcPurchaseDetailed,result,5l);
                    } //普通订单重复退款
                    else if(tbJcPlan.getCnt()>0){
                        operateRefundFrozenToMoney(tbJcPurchaseDetailed,result,6l);
                    }
                    else{
                        //判断是否已经是首单
                        if(tbJcPurchaseDetailedList1 <= 0){//首次
                            /*tbJcPurchaseDetailed.setPayStatus(Long.valueOf(2));*/
                            tbJcPurchaseDetailed.setFirst("1");
                        }else{
                            /*tbJcPurchaseDetailed.setPayStatus(Long.valueOf(1));*/
                            tbJcPurchaseDetailed.setFirst("0");
                        }
                        if("2".equals(result.get("status"))){
                            tbJcPurchaseDetailed.setPayStatus(Long.valueOf(2));//付款成功
                        }else if("4".equals(result.get("status"))){
                            tbJcPurchaseDetailed.setPayStatus(Long.valueOf(1));//冻结
                        }

                        tbJcPurchaseDetailed.setUpdateTime(new Date());

                        //获取返回金额 实际支付金额  ******************  返回字段名称暂时未定
                        tbJcPurchaseDetailed.setThirdMoney(new BigDecimal(String.valueOf(result.get("thirdAmount"))));
                        try {
                            tbJcPurchaseDetailedService.updateTbJcPurchaseDetailed(tbJcPurchaseDetailed,tbJcPurchaseDetailedService,tbPlanService);

                            /* ***增加对应方案的人气值-------------*/
                            tbJcPurchaseDetailedService.addPlanPopularity(tbJcPlan);
                            /* ***增加对应方案的人气值-------------*/

                        } catch (BaseException e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    log.info("支付回调，无对应的方案信息");
                }
            }
            else if("8".equals(result.get("status"))){//失败
                tbJcPurchaseDetailed.setPayStatus(Long.valueOf(8));
                tbJcPurchaseDetailed.setUpdateTime(new Date());
                try {
                    tbJcPurchaseDetailedService.updateTbJcPurchaseDetailed(tbJcPurchaseDetailed,tbJcPurchaseDetailedService,tbPlanService);
                } catch (BaseException e) {
                    e.printStackTrace();
                }
            }else{//返回状态不无法处理
                log.error("订单发起冻结返回状诚无法处理 订单号" + tbJcPurchaseDetailed.getOrderId() + "返回状态:"+result.get("status"));
            }
        }
    }

    public void operateRefundFrozenToMoney(TbJcPurchaseDetailed tbJcPurchaseDetailed,Map<String, Object> result,Long payStatus){
        try {
            String type = "";

            if(payStatus == 4){
                type = "2";
            }else if(payStatus == 5){
                type = "3";
            }else if(payStatus == 6){
                type = "1";
            }
            String resCode = tbJcPurchaseDetailedService.refundFrozenToMoney(type,tbJcPurchaseDetailed,payService);
            if("000000".equals(resCode) || "109023".equals(resCode) || "010124".equals(resCode)){//退款成功
                //更新订单表信息
                tbJcPurchaseDetailed.setAwardStatus(Long.valueOf(0));
                tbJcPurchaseDetailed.setPayStatus(payStatus);//4 不在可售状态退款，5首单重负退款 6 不是首单重复退款
                tbJcPurchaseDetailed.setUpdateTime(new Date());
                //获取返回金额 实际支付金额  ******************  返回字段名称暂时未定
                tbJcPurchaseDetailed.setThirdMoney(new BigDecimal(String.valueOf(result.get("thirdAmount"))));
                if(result.get("thirdAmount")!=null){
                    tbJcPurchaseDetailed.setPayId(String.valueOf(result.get("payingId")));
                }
                Example example = new Example(TbJcPurchaseDetailed.class);
                example.createCriteria().andEqualTo("id",tbJcPurchaseDetailed.getId());
                int j = tbJcPurchaseDetailedService.updateByExampleSelective(tbJcPurchaseDetailed,example);
                if(j <= 0){
                    throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                            ProtocolCodeMsg.UPDATE_FAILE.getMsg());
                }
                log.error("用户 ：" + tbJcPurchaseDetailed.getUserId() + "退款成功====退款金额:" +  tbJcPurchaseDetailed.getBuyMoney());

            }else{
                log.error("用户 ：" + tbJcPurchaseDetailed.getUserId() + "退款失败====退款金额:" +  tbJcPurchaseDetailed.getBuyMoney() );

            }
        } catch (BaseException e) {
            e.printStackTrace();
        }
    }

}
