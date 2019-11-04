package com.zhcdata.jc.quartz.job;

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
        //LOGGER.error("Instance detail: " + key + " trigger:" + jobKey + "开始执行");
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        //LOGGER.error("dataMap:" + new Gson().toJson(dataMap));
        //LOGGER.error("Instance detail: " + key + " trigger:" + jobKey + "执行完成");
        long end = System.currentTimeMillis();
        //LOGGER.error("Instance detail: " + key + " trigger:" + jobKey + "执行耗时" + ((double) (end - start) / 1000) + "(s)");
    }
    public static void main(String args[]){
        String str = "<?xml  version=\"1.0\" encoding=\"utf-8\"?><c><a><h>1650878,3,-1,0.92,0.94,False,True,1,2019-11-04 05:59:09,False,3</h><h>1650878,3,-0.75,1.23,0.67,False,True,2,2019-11-04 05:59:10,False,3</h><h>1650878,12,-1,0.88,0.96,False,True,1,2019-11-04 05:59:10,False,3</h><h>1650878,12,-0.75,1.14,0.74,False,True,2,2019-11-04 05:59:10,False,3</h><h>1650878,23,-1,0.93,0.95,False,True,1,2019-11-04 05:59:06,False,3</h><h>1650878,23,-0.75,1.25,0.68,False,True,2,2019-11-04 05:59:06,False,3</h><h>1650878,31,-1,0.88,0.96,False,True,1,2019-11-04 05:58:53,False,3</h><h>1650878,31,-0.75,1.14,0.71,False,True,2,2019-11-04 05:58:53,False,3</h><h>1650878,42,-1,0.85,0.92,False,True,1,2019-11-04 05:59:08,False,3</h><h>1650878,42,-0.75,1.16,0.67,False,True,2,2019-11-04 05:59:08,False,3</h><h>1650878,47,-1,0.90,0.98,False,True,1,2019-11-04 05:59:09,False,3</h><h>1650878,47,-0.75,1.23,0.70,False,True,2,2019-11-04 05:59:09,False,3</h><h>1650878,47,-1.25,0.65,1.32,False,True,3,2019-11-04 05:59:10,False,3</h><h>1650878,47,-0.5,1.57,0.54,False,True,4,2019-11-04 05:59:10,False,3</h><h>1667147,3,1,1.02,0.82,False,True,1,2019-11-04 05:59:09,False,3</h><h>1667147,17,1,0.96,0.88,False,True,1,2019-11-04 05:59:03,False,3</h><h>1667147,23,1,0.99,0.87,False,True,1,2019-11-04 05:59:12,False,3</h><h>1667147,24,1,0.97,0.87,False,True,2,2019-11-04 05:59:02,False,3</h><h>1667147,35,1,0.99,0.88,False,True,1,2019-11-04 05:59:06,False,3</h><h>1667147,42,1,0.96,0.84,False,True,1,2019-11-04 05:59:08,False,3</h><h>1667147,42,0.75,0.67,1.20,False,True,2,2019-11-04 05:59:09,False,3</h><h>1667147,42,1.25,1.31,0.59,False,True,3,2019-11-04 05:59:09,False,3</h><h>1667147,47,1,0.92,0.88,False,True,1,2019-11-04 05:59:07,False,3</h><h>1667147,47,1.25,1.30,0.61,False,True,2,2019-11-04 05:59:08,False,3</h><h>1667147,47,0.75,0.63,1.25,False,True,3,2019-11-04 05:59:08,False,3</h><h>1667147,47,1.5,1.68,0.46,False,True,4,2019-11-04 05:59:08,False,3</h><h>1667151,12,0.25,0.99,0.86,False,True,1,2019-11-04 05:58:55,False,3</h><h>1667151,17,0.25,1.03,0.81,False,True,1,2019-11-04 05:59:03,False,3</h><h>1667151,24,0.25,1.03,0.81,False,True,2,2019-11-04 05:59:01,False,3</h><h>1667151,42,0,0.64,1.26,False,True,2,2019-11-04 05:59:08,False,3</h><h>1667151,47,0.5,1.58,0.51,False,True,2,2019-11-04 05:59:08,False,3</h><h>1667151,47,0.75,2.39,0.32,False,True,4,2019-11-04 05:59:09,False,3</h><h>1677054,12,1.5,1.50,0.53,False,True,2,2019-11-04 05:58:52,False,3</h><h>1677054,17,1,0.78,1.16,False,True,1,2019-11-04 05:59:02,False,3</h><h>1677054,17,1.25,1.20,0.75,False,True,2,2019-11-04 05:59:02,False,3</h><h>1677054,24,1,0.78,1.16,False,True,1,2019-11-04 05:59:00,False,3</h><h>1677054,24,1.25,1.20,0.75,False,True,2,2019-11-04 05:59:00,False,3</h><h>1677055,22,0.75,1.10,0.82,False,False,1,2019-11-04 05:58:59,False,2</h><h>1677055,24,0.5,0.83,1.09,False,False,1,2019-11-04 05:59:04,False,2</h><h>1677055,24,0.75,1.09,0.83,False,False,2,2019-11-04 05:59:04,False,2</h><h>1677055,42,0.75,1.10,0.81,False,False,1,2019-11-04 05:59:01,False,2</h><h>1677055,42,0.5,0.79,1.10,False,False,2,2019-11-04 05:59:02,False,2</h><h>1677055,47,0.5,0.80,1.11,False,False,3,2019-11-04 05:59:01,False,2</h><h>1677056,3,1.5,1.04,0.86,False,True,1,2019-11-04 05:59:10,False,2</h><h>1677056,23,1.5,1.04,0.88,False,False,1,2019-11-04 05:59:02,False,2</h><h>1677056,24,1.5,1.02,0.90,False,False,1,2019-11-04 05:59:04,False,2</h><h>1677057,3,0.75,0.88,1.02,False,True,1,2019-11-04 05:58:59,False,2</h><h>1677057,3,1,1.28,0.68,False,True,3,2019-11-04 05:58:59,False,2</h><h>1677057,23,0.75,0.90,1.02,False,False,1,2019-11-04 05:58:54,False,2</h><h>1677057,35,0.75,0.90,1.03,False,False,1,2019-11-04 05:58:56,False,2</h><h>1677057,35,1,1.30,0.69,False,False,3,2019-11-04 05:58:56,False,2</h><h>1677057,42,0.75,0.87,1.01,False,False,1,2019-11-04 05:59:01,False,2</h><h>1677057,42,0.5,0.67,1.27,False,False,2,2019-11-04 05:59:01,False,2</h><h>1677057,42,1,1.27,0.67,False,False,3,2019-11-04 05:59:01,False,2</h><h>1726802,31,0,0.87,0.97,False,True,1,2019-11-04 05:58:54,False,3</h><h>1726802,31,0.25,2.00,0.34,False,True,2,2019-11-04 05:59:07,False,3</h><h>172680";
        System.out.println(str.length());
    }
}
