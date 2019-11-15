package com.zhcdata.jc.controller;

import com.zhcdata.jc.dto.JsonResult;
import com.zhcdata.jc.service.GetMatchInfoByIdListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
public class ManualTriggerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProtocolController.class);

    private final SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");

    @Resource
    GetMatchInfoByIdListService getMatchInfoByIdListService;

    @GetMapping("/manual/start/{date}")
    @ResponseBody
    public JsonResult start(@PathVariable String date) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(day.parse(date));
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            String startDate=day.format(calendar.getTime()).substring(0, 10);

            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(day.parse(date));
            calendar1.add(Calendar.DAY_OF_MONTH, 3);
            String endDate=day.format(calendar1.getTime()).substring(0, 10);

            getMatchInfoByIdListService.dealMatch(startDate,endDate);
            LOGGER.info("已触发成功(" + date + "补赛事状态)");
        } catch (Exception e) {
            LOGGER.error("触发成功", e);
            return JsonResult.failure("定时任务启动失败", e);
        }
        return JsonResult.successMsg("定时任务启动成功");
    }
}
