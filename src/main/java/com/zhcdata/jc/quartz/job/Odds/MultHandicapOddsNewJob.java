package com.zhcdata.jc.quartz.job.Odds;

import com.zhcdata.jc.service.MultHandicapOddsService;
import com.zhcdata.jc.tools.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 20.多盘口赔率：即时赔率接口
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
//@Configuration
//@EnableScheduling
@Component
@Slf4j
public class MultHandicapOddsNewJob implements Job {

    //亚赔（让球盘）即时数据:
    @Resource(name = "multHandicapHandleServiceImpl")
    MultHandicapOddsService multHandicapHandleServiceImpl;

    //欧赔（胜平负）
    @Resource(name = "multOddsHandleServiceImpl")
    MultHandicapOddsService multOddsHandleServiceImpl;

    //大小球
    @Resource(name = "multSizesBallsHandleServiceImpl")
    MultHandicapOddsService multSizesBallsHandleServiceImpl;

    //半场亚赔（让球盘）
    @Resource(name = "multHalfHandicapHandleServiceImpl")
    MultHandicapOddsService multHalfHandicapHandleServiceImpl;

    //半场大小球
    @Resource(name = "multHalfSizesBallsHandleServiceImpl")
    MultHandicapOddsService multHalfSizesBallsHandleServiceImpl;

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("多盘即时赔率解析开始");
        String str = null;
        try {
            str = HttpUtils.httpGet("http://interface.win007.com/zq/Odds_Mult.aspx", null);
        } catch (Exception e) {
            log.error("获取多盘口即时赔率失败");
            return;
        }
        String[] oddsCollection = str.split("\\u0024");
        MultHandicapOddsService array[] = {multHandicapHandleServiceImpl, multOddsHandleServiceImpl, multSizesBallsHandleServiceImpl, multHalfHandicapHandleServiceImpl, multHalfSizesBallsHandleServiceImpl};
        for (int i = 0; i < array.length; i++) {
            array[i].changeHandle(oddsCollection[i].split(";"));
        }
        log.info("多盘即时赔率解析结束");
    }
}