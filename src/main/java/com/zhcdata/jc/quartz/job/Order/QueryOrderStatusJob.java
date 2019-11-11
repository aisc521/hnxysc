package com.zhcdata.jc.quartz.job.Order;

import com.zhcdata.db.model.TbJcExpert;
import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.db.model.TbJcPurchaseDetailed;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.PayService;
import com.zhcdata.jc.service.TbJcExpertService;
import com.zhcdata.jc.service.TbJcPurchaseDetailedService;
import com.zhcdata.jc.service.TbPlanService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
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
    private TbJcExpertService tbJcExpertService;
    @Resource
    private TbPlanService tbPlanService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //查询状态是0(未支付) 1(冻结状态) /支付方式是（微信Native支付_20,支付宝支付_21,微信H5支付_22）的订单
        List<TbJcPurchaseDetailed> tbJcPurchaseDetailedList = tbJcPurchaseDetailedService.queryOrder();
        if(tbJcPurchaseDetailedList.size() > 0){
            for(int i = 0; i < tbJcPurchaseDetailedList.size(); i++){
                TbJcPurchaseDetailed tbJcPurchaseDetailed = tbJcPurchaseDetailedList.get(i);
                Map<String, Object> result = payService.queryOrderStatus(String.valueOf(tbJcPurchaseDetailed.getBuyMoney()),String.valueOf(tbJcPurchaseDetailed.getPayType()),String.valueOf(tbJcPurchaseDetailed.getUserId()),
                        String.valueOf(tbJcPurchaseDetailed.getOrderId()),String.valueOf(tbJcPurchaseDetailed.getSrc()));
                if("2".equals(result.get("status"))){//成功
                    String first = tbJcPurchaseDetailed.getFirst();
                    if("1".equals(first)){//首次
                        tbJcPurchaseDetailed.setPayStatus(Long.valueOf(2));
                    }else{
                        tbJcPurchaseDetailed.setPayStatus(Long.valueOf(1));
                    }
                    tbJcPurchaseDetailed.setUpdateTime(new Date());
                    //获取返回金额 实际支付金额  ******************  返回字段名称暂时未定
                    tbJcPurchaseDetailed.setThirdMoney(Long.valueOf(String.valueOf(result.get("thirdAmount"))));
                    try {
                        updateTbJcPurchaseDetailed(tbJcPurchaseDetailed);
                    } catch (BaseException e) {
                        e.printStackTrace();
                    }
                }
                if("8".equals(result.get("status"))){//失败
                    tbJcPurchaseDetailed.setPayStatus(Long.valueOf(8));
                    tbJcPurchaseDetailed.setUpdateTime(new Date());
                    try {
                        updateTbJcPurchaseDetailed(tbJcPurchaseDetailed);
                    } catch (BaseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public void updateTbJcPurchaseDetailed(TbJcPurchaseDetailed tbJcPurchaseDetailed) throws BaseException {
        Example example = new Example(TbJcPurchaseDetailed.class);
        example.createCriteria().andEqualTo("id",tbJcPurchaseDetailed.getId());
        int j = tbJcPurchaseDetailedService.updateByExampleSelective(tbJcPurchaseDetailed,example);
        if(j <= 0){
            throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                    ProtocolCodeMsg.UPDATE_FAILE.getMsg());
        }


        //更新专家信息
       /* TbJcExpert tbJcExpert = tbJcExpertService.queryExpertDetailsById(Integer.valueOf(String.valueOf(tbJcPurchaseDetailed.getExpertId())));
        Integer pop = tbJcExpert.getPopularity();
        if(pop == null){
            pop = 0;
        }
        tbJcExpert.setPopularity(pop + 10);
        Example example1 = new Example(TbJcExpert.class);
        example1.createCriteria().andEqualTo("id",tbJcExpert.getId());

        int h = tbJcExpertService.updateByExample(tbJcExpert,example1);
        if(h <= 0){
            throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                    ProtocolCodeMsg.UPDATE_FAILE.getMsg());
        }*/

        //增加对应方案的人气值
        TbJcPlan tbJcPlan1 = tbPlanService.queryPlanByPlanId(tbJcPurchaseDetailed.getSchemeId());
        Integer pop = tbJcPlan1.getPlanPopularity();
        if(pop == null){
            pop = 0;
        }
        tbJcPlan1.setPlanPopularity(pop + 10);
        tbPlanService.updatePlanByPlanId(tbJcPlan1);
    }

}
