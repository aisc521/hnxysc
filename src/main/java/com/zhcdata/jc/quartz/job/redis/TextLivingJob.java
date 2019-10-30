package com.zhcdata.jc.quartz.job.redis;

import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.service.ScheduleService;
import com.zhcdata.jc.service.TextLivingService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 直播定时任务
 * @Author cuishuai
 * @Date 2019/10/30 15:08
 */
@Slf4j
@Component
public class TextLivingJob implements Job {
    @Resource
    private TextLivingService textLivingService;

    @Resource
    private ScheduleService scheduleService;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("文字直播定时任务开始");
        //查询所有 已经开始的  比赛id
        List<Schedule> schedules = scheduleService.queryMatchByStatus();
        if(schedules != null && schedules.size() > 0){
            log.info("正在进行中的比赛场次:" + schedules.size());
            for(int i = 0; i < schedules.size(); i++){
                log.info("正在进行中的比赛ID:" + schedules.get(i).getScheduleid());
                textLivingService.updateTextLiveRedisDataSelective(schedules.get(i).getScheduleid());
            }
        }
        log.info("文字直播定时任务结束");

    }
}
