package com.zhcdata.jc.quartz.job;

import com.google.gson.Gson;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/5/16 16:39
 */
@Component
public class TestJob implements Job {
    /**
     * 日志
     */
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey key = context.getJobDetail().getKey();
        JobKey jobKey = context.getTrigger().getJobKey();
        long start = System.currentTimeMillis();
        LOGGER.error("Instance detail: " + key + " trigger:" + jobKey + "开始执行");
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        LOGGER.error("dataMap:" + new Gson().toJson(dataMap));
        LOGGER.error("Instance detail: " + key + " trigger:" + jobKey + "执行完成");
        long end = System.currentTimeMillis();
        LOGGER.error("Instance detail: " + key + " trigger:" + jobKey + "执行耗时" + ((double) (end - start) / 1000) + "(s)");
    }
}
