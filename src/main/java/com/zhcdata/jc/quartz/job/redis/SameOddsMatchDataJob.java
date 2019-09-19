package com.zhcdata.jc.quartz.job.redis;

import com.zhcdata.jc.service.ScheduleService;
import com.zhcdata.jc.tools.Const;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;
import org.springside.modules.utils.time.DateFormatUtil;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;
import java.util.Calendar;

/**
 * Title:
 * Description:根据日期计算、更新同赔精选数据
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/9/19 20:23
 */
@Slf4j
@Component
public class SameOddsMatchDataJob implements Job {
    @Resource
    private ScheduleService scheduleService;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey key = context.getJobDetail().getKey();
        JobKey jobKey = context.getTrigger().getJobKey();
        long start = System.currentTimeMillis();
        log.error("Instance detail: " + key + " trigger:" + jobKey + "开始执行");
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String type = dataMap.getString("method");
        Calendar instance = Calendar.getInstance();
        String date = DateFormatUtil.formatDate(Const.YYYY_MM_DD, instance.getTime());
        if ("update".equals(type)) {
            //更新今日同赔数据
            log.error("更新今日{}同赔数据", date);
            scheduleService.updateSameOddsMatchData(date);
            instance.add(Calendar.DAY_OF_YEAR, -1);
            date = DateFormatUtil.formatDate(Const.YYYY_MM_DD, instance.getTime());
            log.error("更新昨日{}同赔数据", date);
            scheduleService.updateSameOddsMatchData(date);
        } else {
            log.error("生成今日{}同赔数据", date);
            try {
                scheduleService.sameOddsMatchCompute(date);
            } catch (ParseException e) {
                log.error("生成同赔数据失败，日期转换失败");
                e.printStackTrace();
            }
        }
        log.error("Instance detail: " + key + " trigger:" + jobKey + "执行完成");
        long end = System.currentTimeMillis();
        log.error("Instance detail: " + key + " trigger:" + jobKey + "执行耗时" + ((double) (end - start) / 1000) + "(s)");
    }
}
