package com.zhcdata.jc.quartz.listener;

import com.zhcdata.jc.quartz.manager.QuartzManager;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/5/14 17:15
 */
@Slf4j
@Configuration
public class ApplicationStartQuartzJobListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private QuartzManager quartzManager;
    @Value("${custom.job}")
    String jobFlag;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            if ("true".equals(jobFlag)) {
                quartzManager.startJob();
                log.error("任务已经启动......");
            } else {
                log.error("非定时任务项目，不启动定时任务");
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
//
//    /**
//     * 初始注入scheduler
//     */
//    @Bean(name = "scheduler")
//    public Scheduler scheduler() throws SchedulerException{
//        SchedulerFactory schedulerFactoryBean = new StdSchedulerFactory();
//        return schedulerFactoryBean.getScheduler();
//    }
}
