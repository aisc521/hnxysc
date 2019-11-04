package com.zhcdata.jc.quartz.job.redis;

import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.db.model.TbJcExpert;
import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.db.model.TbJcPurchaseDetailed;
import com.zhcdata.jc.dto.*;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.*;
import com.zhcdata.jc.tools.ExpertLevelUtils;
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
import java.util.*;

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

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            LOGGER.info("[计算方案是否命中定时任务开启]" + df.format(new Date()));
            List<TbJcPlan> planResults = tbPlanService.queryPlanListJxAndZs(); //正在进行的方案列表 和在售的
            if (planResults != null && planResults.size() > 0) {
                calculationPlanService.calculationPlan(planResults);
            }
            LOGGER.info("[计算方案是否命中定时任务结束]" + df.format(new Date()) + " 共：" + planResults.size() + "个方案");

        } catch (Exception ex) {
            LOGGER.error("计算方案是否命中定时任务异常：" + ex);
        }
    }


}
