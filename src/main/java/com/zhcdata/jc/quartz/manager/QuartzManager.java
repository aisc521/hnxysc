package com.zhcdata.jc.quartz.manager;

import com.google.gson.Gson;
import com.zhcdata.db.mapper.TbDsQuartzJobMapper;
import com.zhcdata.db.model.TbDsQuartzJob;
import org.apache.logging.log4j.util.Strings;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springside.modules.utils.time.ClockUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/5/14 15:34
 */
@Component
public class QuartzManager {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzManager.class);

    /**
     * 任务调度
     */
    @Resource
    private Scheduler scheduler;

    @Resource
    private TbDsQuartzJobMapper jobMapper;

    private Gson gson = new Gson();

    /**
     * 开始执行定时任务
     */
    public void startJob() throws SchedulerException {
        TbDsQuartzJob quartzJob = new TbDsQuartzJob();
        quartzJob.setDel(false);
        List<TbDsQuartzJob> jobs = jobMapper.select(quartzJob);
        for (TbDsQuartzJob job : jobs) {
            try {
                startJobTask(scheduler, job);
            } catch (ClassNotFoundException e) {
                LOGGER.error("任务生成失败：" + job.toString());
            }
        }
//        startJobTask(scheduler);
        scheduler.start();
    }

    public TbDsQuartzJob queryQuartzJob(int id) {
        TbDsQuartzJob quartzJob = jobMapper.selectByPrimaryKey(id);
        if (quartzJob != null) {
            LOGGER.error("查询定时任务数据为：" + gson.toJson(quartzJob));
        }
        return quartzJob;
    }

    /**
     * 启动定时任务
     */
    private void startJobTask(Scheduler scheduler, TbDsQuartzJob job) throws SchedulerException, ClassNotFoundException {
        String jobDataMapStr = job.getJobDataMap();
        JobDetail jobDetail;
        JobBuilder jobBuilder = JobBuilder.newJob().withIdentity(new JobKey(job.getJobName(), job.getJobGroup()))
                .ofType((Class<Job>) Class.forName(job.getJobClass()));
        if (job.getStartRun() != null && job.getStartRun()) {
            jobBuilder.storeDurably();
        }
        if (Strings.isBlank(jobDataMapStr)) {
            jobDetail = jobBuilder
                    .build();
        } else {
            JobDataMap dataMap = gson.fromJson(jobDataMapStr, JobDataMap.class);
            jobDetail = jobBuilder
                    .usingJobData(dataMap)
                    .build();
        }
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().forJob(jobDetail)
                .withIdentity(job.getJobName(), job.getJobGroup())
                .withSchedule(cronScheduleBuilder).build();

        if (job.getStartRun() != null && job.getStartRun()) {
            Date date = ClockUtil.currentDate();
            date.setTime(date.getTime() + 1000L);
            Trigger startTrigger = TriggerBuilder.newTrigger().forJob(jobDetail)
                    .startAt(date)
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(0).withRepeatCount(SimpleTrigger.MISFIRE_INSTRUCTION_SMART_POLICY))
                    .withIdentity(job.getJobName() + "_STARTUP", job.getJobGroup()).build();
            scheduler.addJob(jobDetail, true);
            scheduler.scheduleJob(cronTrigger);
            scheduler.scheduleJob(startTrigger);
        } else {
            scheduler.scheduleJob(jobDetail, cronTrigger);
        }
    }



    /**
     * 获取Job信息
     *
     * @param name
     * @param group
     */
    public String getjobInfo(String name, String group) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(name, group);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        return String.format("time:%s,state:%s", cronTrigger.getCronExpression(),
                scheduler.getTriggerState(triggerKey).name());
    }


    /**
     * 获取Job信息
     *
     * @param id
     */
    public String getjobInfo(int id) throws SchedulerException {
        TbDsQuartzJob quartzJob = queryQuartzJob(id);
        String result;
        if (quartzJob != null) {
            TriggerKey triggerKey = new TriggerKey(quartzJob.getJobName(), quartzJob.getJobGroup());
            CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            LOGGER.error("tigger:" + gson.toJson(cronTrigger));
            result = String.format("time:%s,state:%s", cronTrigger.getCronExpression(),
                    scheduler.getTriggerState(triggerKey).name());
        } else {
            result = "定时任务不存在";
        }
        return result;
    }

    public boolean addJob(TbDsQuartzJob job) {
        boolean result = false;
        if (!CronExpression.isValidExpression(job.getCron())) {
            LOGGER.error("Illegal cron expression format({})", job.getCron());
            return result;
        }
        try {
            JobDetail jobDetail;
            String jobDataMapStr = job.getJobDataMap();
            JobBuilder jobBuilder = JobBuilder.newJob().withIdentity(new JobKey(job.getJobName(), job.getJobGroup()))
                    .ofType((Class<Job>) Class.forName(job.getJobClass()));
            if (job.getStartRun() != null && job.getStartRun()) {
                jobBuilder.storeDurably();
            }
            if (Strings.isBlank(jobDataMapStr)) {
                jobDetail = jobBuilder
                        .build();
            } else {
                JobDataMap dataMap = gson.fromJson(jobDataMapStr, JobDataMap.class);
                jobDetail = jobBuilder
                        .usingJobData(dataMap)
                        .build();
            }

            Trigger trigger = TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()))
                    .withIdentity(new TriggerKey(job.getJobName(), job.getJobGroup()))
                    .build();
            if (job.getStartRun() != null && job.getStartRun()) {
                Date date = ClockUtil.currentDate();
                date.setTime(date.getTime() + 1000L);
                Trigger startTrigger = TriggerBuilder.newTrigger().forJob(jobDetail)
                        .startAt(date)
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(0).withRepeatCount(SimpleTrigger.MISFIRE_INSTRUCTION_SMART_POLICY))
                        .withIdentity(job.getJobName() + "_STARTUP", job.getJobGroup()).build();
                scheduler.addJob(jobDetail, true);
                scheduler.scheduleJob(trigger);
                scheduler.scheduleJob(startTrigger);
            } else {
                scheduler.scheduleJob(jobDetail, trigger);
            }
            result = true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            LOGGER.error("QuartzManager add job failed");
            result = false;
        }
        return result;
    }

    public boolean addJob(int id) {
        TbDsQuartzJob quartzJob = queryQuartzJob(id);
        boolean result;
        if (quartzJob != null) {
            result = addJob(quartzJob);
            if (result && quartzJob.getDel()) {
                quartzJob.setDel(false);
                jobMapper.updateByPrimaryKeySelective(quartzJob);
            }
        } else {
            result = false;
        }
        return result;
    }

    /**
     * 修改任务的执行时间
     *
     * @param name
     * @param group
     * @param cron  cron表达式
     * @return
     * @throws SchedulerException
     */
    public boolean modifyJob(String name, String group, String cron) throws SchedulerException {
        Date date = null;
        TriggerKey triggerKey = new TriggerKey(name, group);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        String oldTime = cronTrigger.getCronExpression();
        if (!oldTime.equalsIgnoreCase(cron)) {
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group)
                    .withSchedule(cronScheduleBuilder).build();
            date = scheduler.rescheduleJob(triggerKey, trigger);
        }
        return date != null;
    }

    /**
     * 修改任务的执行时间
     *
     * @param id
     * @param cron  cron表达式
     * @return
     * @throws SchedulerException
     */
    public boolean modifyJob(int id, String cron) throws SchedulerException {
        TbDsQuartzJob quartzJob = queryQuartzJob(id);
        boolean result;
        if (quartzJob != null) {
            result = modifyJob(quartzJob.getJobName(),quartzJob.getJobGroup(),cron);
            if (result) {
                quartzJob.setCron(cron);
                int i = jobMapper.updateByPrimaryKeySelective(quartzJob);
                if (i < 1) {
                    LOGGER.error("更新定时任务数据失败");
                }
            }
        } else {
            result = false;
        }
        return result;
    }

    public void reloadAllJob() throws SchedulerException {
        scheduler.clear();
        TbDsQuartzJob quartzJob = new TbDsQuartzJob();
        quartzJob.setDel(false);
        List<TbDsQuartzJob> jobs = jobMapper.select(quartzJob);
        for (TbDsQuartzJob job : jobs) {
            try {
                startJobTask(scheduler, job);
            } catch (ClassNotFoundException e) {
                LOGGER.error("任务生成失败：" + job.toString());
            }
        }
    }

    public boolean reloadJob(int id) throws SchedulerException {
        TbDsQuartzJob quartzJob = queryQuartzJob(id);
        boolean b = scheduler.deleteJob(new JobKey(quartzJob.getJobName(), quartzJob.getJobGroup()));
        if (b) {
            b = addJob(quartzJob);
        }
        return b;
    }

    /**
     * 暂停所有任务
     *
     * @throws SchedulerException
     */
    public void pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 暂停某个任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void pauseJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        scheduler.pauseJob(jobKey);
    }

    /**
     * 暂停某个任务
     *
     * @param id
     * @throws SchedulerException
     */
    public void pauseJob(int id) throws SchedulerException {
        TbDsQuartzJob quartzJob = queryQuartzJob(id);
        if (quartzJob != null) {
            pauseJob(quartzJob.getJobName(), quartzJob.getJobGroup());
        }
    }

    /**
     * 恢复所有任务
     *
     * @throws SchedulerException
     */
    public void resumeAllJob() throws SchedulerException {
        scheduler.resumeAll();
    }

    /**
     * 恢复某个任务
     */
    public void resumeJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        scheduler.resumeJob(jobKey);
    }

    /**
     * 恢复某个任务
     */
    public void resumeJob(int id) throws SchedulerException {
        TbDsQuartzJob quartzJob = queryQuartzJob(id);
        if (quartzJob != null) {
            resumeJob(quartzJob.getJobName(), quartzJob.getJobGroup());
        }
    }

    /**
     * 删除某个任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public boolean deleteJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return false;
        }
        return scheduler.deleteJob(jobKey);
    }

    /**
     * 删除某个任务
     *
     * @param id
     * @throws SchedulerException
     */
    public void deleteJob(int id) throws SchedulerException {
        TbDsQuartzJob quartzJob = queryQuartzJob(id);
        if (quartzJob != null) {
            if (deleteJob(quartzJob.getJobName(), quartzJob.getJobGroup()) && !quartzJob.getDel()) {
                quartzJob.setDel(true);
                jobMapper.updateByPrimaryKeySelective(quartzJob);
            }
        }
    }


    /**
     * 启动一个调度对象
     *
     * @throws SchedulerException
     */
    public void start() throws SchedulerException{
        scheduler.start();
    }

    /**
     * 检查调度是否启动
     *
     * @return
     * @throws SchedulerException
     */
    public boolean isStarted() throws SchedulerException {
        return scheduler.isStarted();
    }

    /**
     * 关闭调度信息
     *
     * @throws SchedulerException
     */
    public void shutdown() throws SchedulerException{
        scheduler.shutdown();
    }

    /**
     * 添加调度的job信息
     *
     * @param jobdetail
     * @param trigger
     * @return
     * @throws SchedulerException
     */
    public Date scheduleJob(JobDetail jobdetail, Trigger trigger)throws SchedulerException{
        return scheduler.scheduleJob(jobdetail, trigger);
    }

    /**
     * 添加相关的触发器
     *
     * @param trigger
     * @return
     * @throws SchedulerException
     */
    public Date scheduleJob(Trigger trigger) throws SchedulerException{
        return scheduler.scheduleJob(trigger);
    }
//
//    /**
//     * 添加多个job任务
//     *
//     * @param triggersAndJobs
//     * @param replace
//     * @throws SchedulerException
//     */
//    public void scheduleJobs(Map<JobDetail, Set<Trigger>> triggersAndJobs, boolean replace) throws SchedulerException{
//        scheduler.scheduleJobs(triggersAndJobs, replace);
//    }

    /**
     * 停止调度Job任务
     *
     * @param triggerkey
     * @return
     * @throws SchedulerException
     */
    public boolean unscheduleJob(TriggerKey triggerkey) throws SchedulerException{
        return scheduler.unscheduleJob(triggerkey);
    }

    /**
     * 停止调度多个触发器相关的job
     *
     * @param triggerKeylist
     * @return
     * @throws SchedulerException
     */
    public boolean unscheduleJobs(List<TriggerKey> triggerKeylist) throws SchedulerException{
        return scheduler.unscheduleJobs(triggerKeylist);
    }

    /**
     * 重新恢复触发器相关的job任务
     *
     * @param triggerkey
     * @param trigger
     * @return
     * @throws SchedulerException
     */
    public Date rescheduleJob(TriggerKey triggerkey, Trigger trigger) throws SchedulerException{
        return scheduler.rescheduleJob(triggerkey, trigger);
    }

    /**
     * 添加相关的job任务
     *
     * @param jobdetail
     * @param flag
     * @throws SchedulerException
     */
    public void addJob(JobDetail jobdetail, boolean flag) throws SchedulerException{
        scheduler.addJob(jobdetail, flag);
    }

    /**
     * 删除相关的job任务
     *
     * @param jobkey
     * @return
     * @throws SchedulerException
     */
    public boolean deleteJob(JobKey jobkey) throws SchedulerException{
        return scheduler.deleteJob(jobkey);
    }

    /**
     * 删除相关的多个job任务
     *
     * @param jobKeys
     * @return
     * @throws SchedulerException
     */
    public boolean deleteJobs(List<JobKey> jobKeys)throws SchedulerException{
        return scheduler.deleteJobs(jobKeys);
    }

    /**
     * @param jobkey
     * @throws SchedulerException
     */
    public void triggerJob(JobKey jobkey) throws SchedulerException{
        scheduler.triggerJob(jobkey);
    }

    public void triggerJob(int id) throws SchedulerException {
        TbDsQuartzJob quartzJob = queryQuartzJob(id);
        if (quartzJob != null) {
            triggerJob(new JobKey(quartzJob.getJobName(), quartzJob.getJobGroup()));
        }
    }

    /**
     * @param jobkey
     * @param jobdatamap
     * @throws SchedulerException
     */
    public void triggerJob(JobKey jobkey, JobDataMap jobdatamap)throws SchedulerException{
        scheduler.triggerJob(jobkey, jobdatamap);
    }

    /**
     * 停止一个job任务
     *
     * @param jobkey
     * @throws SchedulerException
     */
    public void pauseJob(JobKey jobkey) throws SchedulerException{
        scheduler.pauseJob(jobkey);
    }

    /**
     * 停止多个job任务
     *
     * @param groupmatcher
     * @throws SchedulerException
     */
    public void pauseJobs(GroupMatcher<JobKey> groupmatcher) throws SchedulerException{
        scheduler.pauseJobs(groupmatcher);
    }

    /**
     * 停止使用相关的触发器
     *
     * @param triggerkey
     * @throws SchedulerException
     */
    public void pauseTrigger(TriggerKey triggerkey) throws SchedulerException{
        scheduler.pauseTrigger(triggerkey);
    }

    public void pauseTriggers(GroupMatcher<TriggerKey> groupmatcher) throws SchedulerException{
        scheduler.pauseTriggers(groupmatcher);
    }

    /**
     * 恢复相关的job任务
     *
     * @param jobkey
     * @throws SchedulerException
     */
    public void resumeJob(JobKey jobkey) throws SchedulerException{
        scheduler.pauseJob(jobkey);
    }

    public void resumeJobs(GroupMatcher<JobKey> matcher) throws SchedulerException{
        scheduler.resumeJobs(matcher);
    }

    public void resumeTrigger(TriggerKey triggerkey)throws SchedulerException{
        scheduler.resumeTrigger(triggerkey);
    }

    public void resumeTriggers(GroupMatcher<TriggerKey> groupmatcher) throws SchedulerException{
        scheduler.resumeTriggers(groupmatcher);
    }

    /**
     * 暂停调度中所有的job任务
     *
     * @throws SchedulerException
     */
    public void pauseAll() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 恢复调度中所有的job的任务
     *
     * @throws SchedulerException
     */
    public void resumeAll() throws SchedulerException{
        scheduler.resumeAll();
    }
}
