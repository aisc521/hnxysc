package com.zhcdata.jc.controller;

import com.zhcdata.jc.dto.JsonResult;
import com.zhcdata.jc.quartz.manager.QuartzManager;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/5/15 11:14
 */
@Controller
public class QuartzController {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProtocolController.class);

    @Resource
    private QuartzManager quartzManager;

    /**
     * 启动定时任务调度
     *
     * @return
     */
    @GetMapping("/quartz/start")
    @ResponseBody
    public JsonResult start() {
        try {
            quartzManager.start();
        } catch (SchedulerException e) {
            LOGGER.error("定时任务启动失败", e);
            return JsonResult.failure("定时任务启动失败", e);
        }
        return JsonResult.successMsg("定时任务启动成功");
    }

    /**
     * 查询定时任务信息
     *
     * @param id
     * @return
     */
    @GetMapping("/quartz/info/{id}")
    @ResponseBody
    public Object queryInfoJob(@PathVariable Integer id) throws SchedulerException {
        return quartzManager.getjobInfo(id);
    }

    /**
     * 添加定时任务
     *
     * @param id
     * @return
     */
    @GetMapping("/quartz/add/{id}")
    @ResponseBody
    public JsonResult addJob(@PathVariable Integer id) {
        if (quartzManager.addJob(id)) {
            return JsonResult.successMsg("添加成功 id:" + id);
        } else {
            return JsonResult.failure("定时任务添加失败，id：" + id);
        }
    }

    /**
     * 修改定时任务执行时间
     *
     * @param id
     * @param cron
     * @return
     */
    @RequestMapping("/quartz/update/{id}")
    @ResponseBody
    public JsonResult modifyJob(@PathVariable Integer id, @RequestParam String cron) throws SchedulerException {
        if (quartzManager.modifyJob(id, cron)) {
            return JsonResult.successMsg("更新成功 id:" + id + " cron：" + cron);
        } else {
            return JsonResult.failure("定时任务更新失败，id：" + id);
        }
    }

    /**
     * 暂停全部定时任务
     *
     * @return
     */
    @GetMapping("/quartz/pause")
    @ResponseBody
    public JsonResult pause() {
        try {
            quartzManager.pauseAll();
        } catch (SchedulerException e) {
            LOGGER.error("全部定时任务暂停失败", e);
            return JsonResult.failure("全部定时任务暂停失败", e);
        }
        return JsonResult.successMsg("全部定时任务暂停成功");
    }


    /**
     * 暂停某个定时任务
     *
     * @return
     */
    @GetMapping("/quartz/pause/{id}")
    @ResponseBody
    public JsonResult pause(@PathVariable Integer id) {
        try {
            quartzManager.pauseJob(id);
        } catch (SchedulerException e) {
            LOGGER.error("定时任务暂停失败", e);
            return JsonResult.failure("定时任务暂停失败", e);
        }
        return JsonResult.successMsg("定时任务暂停成功");
    }

    /**
     * 恢复全部定时任务
     *
     * @return
     */
    @GetMapping("/quartz/resume")
    @ResponseBody
    public JsonResult resume() {
        try {
            quartzManager.resumeAll();
        } catch (SchedulerException e) {
            LOGGER.error("全部定时任务恢复失败", e);
            return JsonResult.failure("全部定时任务恢复失败", e);
        }
        return JsonResult.successMsg("全部定时任务恢复成功");
    }


    /**
     * 恢复某个定时任务
     *
     * @return
     */
    @GetMapping("/quartz/resume/{id}")
    @ResponseBody
    public JsonResult resume(@PathVariable Integer id) {
        try {
            quartzManager.resumeJob(id);
        } catch (SchedulerException e) {
            LOGGER.error("定时任务恢复失败", e);
            return JsonResult.failure("定时任务恢复失败", e);
        }
        return JsonResult.successMsg("定时任务恢复成功");
    }

    /**
     * 删除某个定时任务
     *
     * @param id
     * @return
     */
    @GetMapping("/quartz/delete/{id}")
    @ResponseBody
    public JsonResult delete(@PathVariable Integer id) {
        try {
            quartzManager.deleteJob(id);
        } catch (SchedulerException e) {
            LOGGER.error("定时任务删除失败", e);
            return JsonResult.failure("定时任务删除失败", e);
        }
        return JsonResult.successMsg("定时任务删除成功");
    }

    @GetMapping("/quartz/status")
    @ResponseBody
    public JsonResult status() {
        try {
            return JsonResult.success(quartzManager.isStarted());
        } catch (SchedulerException e) {
            LOGGER.error("定时任务调度查询失败", e);
            return JsonResult.failure("定时任务调度查询失败", e);
        }
    }

    @GetMapping({"/quartz/shutdown","/quartz/stop"})
    @ResponseBody
    public JsonResult shutdown() {
        try {
            quartzManager.shutdown();
        } catch (SchedulerException e) {
            LOGGER.error("定时任务调度器停止失败", e);
            return JsonResult.failure("定时任务调度器停止失败", e);
        }
        return JsonResult.successMsg("定时任务调度器停止成功");
    }

    @GetMapping("/quartz/reload")
    @ResponseBody
    public JsonResult reload() {
        try {
            quartzManager.reloadAllJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
            LOGGER.error("定时任务调度器重载失败", e);
            return JsonResult.failure("定时任务调度器重载失败", e);
        }
        return JsonResult.successMsg("定时任务调度器重载成功");
    }

    @GetMapping("/quartz/reload/{id}")
    @ResponseBody
    public JsonResult reload(@PathVariable Integer id) {
        try {
            quartzManager.reloadJob(id);
        } catch (SchedulerException e) {
            e.printStackTrace();
            LOGGER.error("定时任务" + id + "重载失败", e);
            return JsonResult.failure("定时任务" + id + "重载失败", e);
        }
        return JsonResult.successMsg("定时任务" + id + "重载成功");
    }


    /**
     * 触发某个定时任务
     *
     * @param id
     * @return
     */
    @GetMapping("/quartz/trigger/{id}")
    @ResponseBody
    public JsonResult trigger(@PathVariable Integer id) throws SchedulerException {
        quartzManager.triggerJob(id);
        return JsonResult.successMsg("触发成功");
    }


    /**
     * 检查tomcat是否在运行
     * @return
     */
    @GetMapping("/looking")
    @ResponseBody
    public String looking() {
        return "0000";
    }
}
