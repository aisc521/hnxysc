package com.zhcdata.jc.quartz.job.redis;

import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.jc.service.*;
import com.zhcdata.jc.tools.ExpertLevelUtils;
import com.zhcdata.jc.tools.RedisUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    ExpertLevelUtils expertLevelUtils;
    @Resource
    private TbJcPurchaseDetailedService purchaseDetailedService;

    @Resource
    private CalculationPlanService calculationPlanService;

    @Resource
    CalculationPlanNewService calculationPlanNewService;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      /*  try {
            LOGGER.info("[计算方案是否命中定时任务开启]" + df.format(new Date()));

            //需要查询方案大于最小开始比赛时间的方案
            List<TbJcPlan> planResults = tbPlanService.queryPlanListJxAndZs(); //正在进行的方案列表 和在售的
            if (planResults != null && planResults.size() > 0) {
                calculationPlanService.calculationPlan(planResults);
            }
            LOGGER.info("[计算方案是否命中定时任务结束]" + df.format(new Date()) + " 共：" + planResults.size() + "个方案");

        } catch (Exception ex) {
            LOGGER.error("计算方案是否命中定时任务异常：" + ex);
        }*/

        expertPlan();
    }


    public void expertPlan(){

        LOGGER.info("[计算方案是否命中定时任务开启]" + df.format(new Date()));
        //处理在售的 把再售改成进行中
        salePlan();

        //处理进行中的 把进行中改成完成的
        calcuPlan();

        LOGGER.info("[计算方案是否命中定时任务结束]" + df.format(new Date()));

    }
    /**
     * 把在售的改在进行中的
     */
    public void salePlan(){
        List<TbJcPlan> list = tbPlanService.queryPlanListSale(); //查询在售的方案
        if(list==null||list.size()<1){
            LOGGER.info("[计算方案是否命中定时任务]  =  没有需要改成进行中的方案");
            return;
        }
        for (TbJcPlan TbJcPlan :list){
            try{
                calculationPlanService.updatePlanStatus(TbJcPlan);
            }catch (Exception e){
                LOGGER.error("修改失败"+" 方案id为"+TbJcPlan.getId(),e);
                e.printStackTrace();
            }

        }

    }
    //计算方案处理
    public void calcuPlan(){

        List<TbJcPlan> planResultsList = tbPlanService.queryPlanListJxAndZs(); //正在进行的方案列表 和在售的

        if(planResultsList == null||planResultsList.size()<1){
            LOGGER.info("[计算方案是否命中定时任务]  =  没有需要计算结束的方案");
        }
        for(TbJcPlan tbJcPlan : planResultsList){
            try{
                calculationPlanNewService.calculationPlan(tbJcPlan);
            }catch (Exception e){
                LOGGER.error("计算方案异常 "+" 方案id为"+tbJcPlan.getId(),e);
            }
        }

    }
}
