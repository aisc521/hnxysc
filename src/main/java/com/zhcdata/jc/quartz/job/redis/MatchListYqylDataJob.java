package com.zhcdata.jc.quartz.job.redis;

import com.zhcdata.jc.dto.MatchResult1;
import com.zhcdata.jc.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
@Slf4j
@Component
public class MatchListYqylDataJob implements Job {
    @Resource
    private ScheduleService scheduleService;

    @Resource
    private MatchListDataJob matchListDataJob;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        try {
            //即时比赛30秒
            SimpleDateFormat df = new SimpleDateFormat("HH");
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
            String startDate="";  //开始时间
            String endDate="";    //结束时间
            String nowHour = df.format(new Date());    //当前小时
            if (Long.valueOf(nowHour) > 10) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                startDate=df1.format(calendar.getTime()) + " 11:00:00";

                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(new Date());
                calendar1.add(Calendar.DAY_OF_MONTH, 1);
                endDate=df1.format(calendar1.getTime()) + " 11:00:00";
            } else {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                startDate=df1.format(calendar.getTime()) + " 11:00:00";

                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(new Date());
                endDate=df1.format(calendar1.getTime()) + " 11:00:00";
            }

            String time=startDate.substring(0, 10);

            //更新当日所有
            List<MatchResult1> list1=new ArrayList<>();
            List<MatchResult1> list1_1 = scheduleService.queryMacthListForJob(startDate, endDate, "4","","1",null,null,null); //正在进行
            list1.addAll(list1_1);
            List<MatchResult1> list1_2 = scheduleService.queryMacthListForJob(startDate, endDate, "4","","2",null,null,null); //未开始
            list1.addAll(list1_2);
            List<MatchResult1> list1_3 = scheduleService.queryMacthListForJob(startDate, endDate, "4","","3",null,null,null); //已结束
            list1.addAll(list1_3);
            matchListDataJob.deal(list1,time,"33","yqyl");


            //更新当日赛程
            List<MatchResult1> list_match = scheduleService.queryMacthListForJob(startDate, endDate, "4", "", "2", null, null, null); //竞彩 正在进行
            matchListDataJob.deal(list_match,time,"22","yqyl");

            //更新当日赛果
            List<MatchResult1> list_result = scheduleService.queryMacthListForJob(startDate, endDate, "4", "", "3", null, null, null); //竞彩 正在进行

            matchListDataJob.deal(list_result,time,"11","yqyl");

        }catch (Exception ex){
            LOGGER.error("定时任务[有球有料 即时更新]");
            ex.printStackTrace();
        }
    }
}
