package com.zhcdata.jc.quartz.job.redis;

import com.zhcdata.jc.protocol.impl.MatchBettingCollectProtocol;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;

/**
 * @Description 获取五种玩法的 定时任务
 * @Author cuishuai
 * @Date 2019/9/20 15:17
 */
public class MatchBettingJob implements Job {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private MatchBettingCollectProtocol matchBettingCollectBiz;
    private boolean nowRun = false;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            if (nowRun) {
                LOGGER.error("MatchBettingJob仍在运行");
                return;
            }
            nowRun = true;
            matchBettingCollectBiz.autoWork();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            nowRun = false;
        }
    }
}
