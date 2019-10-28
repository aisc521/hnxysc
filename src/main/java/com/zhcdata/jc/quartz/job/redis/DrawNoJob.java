package com.zhcdata.jc.quartz.job.redis;

import com.zhcdata.jc.dto.DrawNoResult;
import com.zhcdata.jc.service.ScheduleService;
import com.zhcdata.jc.tools.CommonUtils;
import com.zhcdata.jc.tools.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 获取足彩期号 定时任务
 * @Author cuishuai
 * @Date 2019/9/24 13:14
 */
@Configuration
@EnableScheduling
@Slf4j
public class DrawNoJob implements Job {

    @Resource
    private CommonUtils commonUtils;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private ScheduleService scheduleService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try{
            List<DrawNoResult> drawNoResultList = scheduleService.queryList(commonUtils.getSE().split(",")[0]);
            if (drawNoResultList != null && drawNoResultList.size() > 0) {
                String drawNo = "";
                for (int i = 0; i < drawNoResultList.size(); i++) {
                    drawNo += drawNoResultList.get(i).getDrawNo();
                    if (i != drawNoResultList.size() - 1) {
                        drawNo += ",";
                    }
                }
                redisUtils.hset("SOCCER:HSET:DRAWNO", "zc", drawNo);
            }
        }catch (Exception e){
            log.error("定时任务_更新足彩期号_",e);
        }

    }
}
