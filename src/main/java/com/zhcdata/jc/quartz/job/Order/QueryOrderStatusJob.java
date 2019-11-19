package com.zhcdata.jc.quartz.job.Order;


import com.zhcdata.db.mapper.TbJcPlanMapper;
import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.db.model.TbJcPurchaseDetailed;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.PayService;
import com.zhcdata.jc.service.TbJcPurchaseDetailedService;
import com.zhcdata.jc.service.TbPlanService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

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
@Configuration
@EnableScheduling
@Slf4j
@Component
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
        //查询状态是0(未支付) 1(冻结状态) /支付方式是（微信Native支付_20,支付宝支付_21,微信H5支付_22）的订单
        List<TbJcPurchaseDetailed> tbJcPurchaseDetailedList = tbJcPurchaseDetailedService.queryOrder();
        if(tbJcPurchaseDetailedList.size() > 0){
            for(int i = 0; i < tbJcPurchaseDetailedList.size(); i++){
                TbJcPurchaseDetailed tbJcPurchaseDetailed = tbJcPurchaseDetailedList.get(i);
                Map<String, Object> result = payService.queryOrderStatus(String.valueOf(tbJcPurchaseDetailed.getBuyMoney()),String.valueOf(tbJcPurchaseDetailed.getPayType()),String.valueOf(tbJcPurchaseDetailed.getUserId()),
                        String.valueOf(tbJcPurchaseDetailed.getOrderId()),String.valueOf(tbJcPurchaseDetailed.getSrc()));
                if("2".equals(result.get("status")) || "4".equals(result.get("status"))){//成功/冻结

                    //先判断此订单对应的方案 是否是已停售的状态
                    TbJcPlan tbJcPlan = tbPlanService.queryPlanByPlanId(tbJcPurchaseDetailed.getSchemeId(),tbJcPurchaseDetailed.getUserId());
                    if(tbJcPlan != null){
                        //判断是否已经是首单
                        //根据状态查询是否是首单 ===查询此用户是否有支付成功的状态的订单
                        Integer tbJcPurchaseDetailedList1 = tbJcPurchaseDetailedService.queryIfHaveSuccessOeder(tbJcPurchaseDetailed.getUserId());
                        if(!"2".equals(String.valueOf(tbJcPlan.getStatus()))||tbJcPlan.getCnt()>1){//不是在售状态的方案或者已经买过该定单  执行退款操作
                                if(tbJcPlan.getCnt()>1){
                                    tbJcPurchaseDetailed.setPayStatus(Long.valueOf(5));//买过该方案
                                }else{
                                    tbJcPurchaseDetailed.setPayStatus(Long.valueOf(4));//不在在售状态退款状态
                                }

                                tbJcPurchaseDetailed.setUpdateTime(new Date());
                                //获取返回金额 实际支付金额  ******************  返回字段名称暂时未定
                                tbJcPurchaseDetailed.setThirdMoney(new BigDecimal(String.valueOf(result.get("thirdAmount"))));
                            try {
                                tbJcPurchaseDetailedService.updateTbJcPurchaseDetailed(tbJcPurchaseDetailed,tbJcPurchaseDetailedService,tbPlanService);
                                tbJcPurchaseDetailedService.refundFrozenToMoney(tbJcPlan,tbJcPurchaseDetailedService,payService);
                            } catch (BaseException e) {
                                e.printStackTrace();
                            }
                                continue;
                        }
                        if(new BigDecimal(String.valueOf(result.get("thirdAmount"))).intValue() == 2 && tbJcPurchaseDetailedList1 > 0){//首单两元 但是 此用户已经有支付成功的状态  退款

                            tbJcPurchaseDetailed.setPayStatus(Long.valueOf(5));
                            tbJcPurchaseDetailed.setUpdateTime(new Date());
                            //获取返回金额 实际支付金额  ******************  返回字段名称暂时未定
                            tbJcPurchaseDetailed.setThirdMoney(new BigDecimal(String.valueOf(result.get("thirdAmount"))));
                            try {
                                tbJcPurchaseDetailedService.updateTbJcPurchaseDetailed(tbJcPurchaseDetailed,tbJcPurchaseDetailedService,tbPlanService);
                                tbJcPurchaseDetailedService.refundFrozenToMoney(tbJcPlan,tbJcPurchaseDetailedService,payService);
                            } catch (BaseException e) {
                                e.printStackTrace();
                            }
                        }else{
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

                if("8".equals(result.get("status"))){//失败
                    tbJcPurchaseDetailed.setPayStatus(Long.valueOf(8));
                    tbJcPurchaseDetailed.setUpdateTime(new Date());
                    try {
                        tbJcPurchaseDetailedService.updateTbJcPurchaseDetailed(tbJcPurchaseDetailed,tbJcPurchaseDetailedService,tbPlanService);
                    } catch (BaseException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }


}
