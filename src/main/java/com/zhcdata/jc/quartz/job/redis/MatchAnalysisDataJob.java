package com.zhcdata.jc.quartz.job.redis;

import com.zhcdata.jc.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Title:
 * Description:更新比赛分析数据Redis定时任务
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/9/17 20:14
 */
@Slf4j
@Component
public class MatchAnalysisDataJob implements Job {
    @Resource
    private ScheduleService scheduleService;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey key = context.getJobDetail().getKey();
        JobKey jobKey = context.getTrigger().getJobKey();
        long start = System.currentTimeMillis();
        log.error("Instance detail: " + key + " trigger:" + jobKey + "开始执行");
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        Integer before = dataMap.getIntegerFromString("before");
        Integer after = dataMap.getIntegerFromString("after");
        if (before == null) {
            before = 1;
        }
        if (after == null) {
            after = 1;
        }
        scheduleService.updateMatchAnalysis(before, after);
        log.error("Instance detail: " + key + " trigger:" + jobKey + "执行完成");
        long end = System.currentTimeMillis();
        log.error("Instance detail: " + key + " trigger:" + jobKey + "执行耗时" + ((double) (end - start) / 1000) + "(s)");
    }
}
