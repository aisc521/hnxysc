package com.zhcdata.jc.quartz.job.redis;

import com.google.common.base.Strings;
import com.zhcdata.jc.dto.DrawNoResult;
import com.zhcdata.jc.service.ScheduleService;
import com.zhcdata.jc.tools.CommonUtils;
import com.zhcdata.jc.tools.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.omg.PortableInterceptor.INACTIVE;
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
            String issues=redisUtils.hget("SOCCER:HSET:zcIssue",  "zcIssue").toString();
            if(!Strings.isNullOrEmpty(issues)){
                issues=issues.substring(0,issues.length()-1);
                String[] issue=issues.split(",");
                List<DrawNoResult> drawNoResultList = scheduleService.queryIssueList(Integer.valueOf(issue[0]));
                if (drawNoResultList != null && drawNoResultList.size() > 0) {
                    String drawNo = "";
                    for (int i = 0; i < drawNoResultList.size(); i++) {
                        drawNo += drawNoResultList.get(i).getDrawNo();
                        if (i != drawNoResultList.size() - 1) {
                            drawNo += ",";
                        }
                    }
                    redisUtils.hset("SOCCER:HSET:DRAWNO", "zc", drawNo + "," + issues);
                    String sds=(String) redisUtils.hget("SOCCER:HSET:DRAWNO", "zc");
                    System.out.println(sds);
                }
            }else {
                log.error("足彩在售期号为空");
            }
        }catch (Exception e){
            log.error("定时任务_更新足彩期号_",e);
        }

    }
}
