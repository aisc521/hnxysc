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
import sun.misc.Cache;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
@Slf4j
@Component
public class MatchListYqylDataAllJob implements Job {

    @Resource
    private ScheduleService scheduleService;

    @Resource
    private MatchListDataJob matchListDataJob;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            SimpleDateFormat df = new SimpleDateFormat("HH");
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
            int date = 0;
            String nowHour = df.format(new Date());    //当前小时
            if (Long.valueOf(nowHour) > 10) {
                //未到11点
                date = 6;
            } else {
                //到11点
                date = 7;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, -date);
            System.out.println(df1.format(calendar.getTime()));

            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(new Date());
            calendar1.add(Calendar.DAY_OF_MONTH, -(date - 1));
            System.out.println(df1.format(calendar1.getTime()));

            String time="";
            //赛果 向前查看6天
            for (int i = 0; i < 6; i++) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                String startDate = df1.format(calendar.getTime()) + " 11:00:00";

                calendar1.add(Calendar.DAY_OF_MONTH, 1);
                String endDate = df1.format(calendar1.getTime()) + " 11:00:00";

                time=startDate.substring(0, 10);

                List<MatchResult1> list = scheduleService.queryMacthListForJob(startDate, endDate, "4", "", "3", null, null, null); //竞彩 正在进行

                matchListDataJob.deal(list,time,"11");
            }

            //赛程 向后查看6天
            for (int i = 0; i < 6; i++) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                String startDate = df1.format(calendar.getTime()) + " 11:00:00";

                calendar1.add(Calendar.DAY_OF_MONTH, 1);
                String endDate = df1.format(calendar1.getTime()) + " 11:00:00";

                time=startDate.substring(0, 10);

                List<MatchResult1> list = scheduleService.queryMacthListForJob(startDate, endDate, "4", "", "2", null, null, null); //竞彩 正在进行
                matchListDataJob.deal(list,time,"22");
            }
        }catch (Exception ex){
            LOGGER.error("定时任务[有球有料 赛程 赛果]");
            ex.printStackTrace();
        }
    }
}
