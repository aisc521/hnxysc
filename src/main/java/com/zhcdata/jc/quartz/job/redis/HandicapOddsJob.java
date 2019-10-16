package com.zhcdata.jc.quartz.job.redis;

import com.google.gson.Gson;
import com.zhcdata.jc.service.HandicapOddsService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/9/16 18:12
 */
@Slf4j
@Component
public class HandicapOddsJob implements Job {
    @Resource
    private HandicapOddsService handicapOddsService;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey key = context.getJobDetail().getKey();
        JobKey jobKey = context.getTrigger().getJobKey();
        long start = System.currentTimeMillis();
        log.error("Instance detail: " + key + " trigger:" + jobKey + "开始执行");
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String type = dataMap.getString("type");
        int days = dataMap.getIntFromString("days");
        if ("detail".equals(type)) {
            handicapOddsService.updateHandicapOddsDetailData(days);
        } else {
            handicapOddsService.updateHandicapOddsData(days);
        }
        log.error("dataMap:" + new Gson().toJson(dataMap));
        log.error("Instance detail: " + key + " trigger:" + jobKey + "执行完成");
        long end = System.currentTimeMillis();
        log.error("Instance detail: " + key + " trigger:" + jobKey + "执行耗时" + ((double) (end - start) / 1000) + "(s)");
    }
}
