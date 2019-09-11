package com.zhcdata.jc.quartz.job.InstantLottery;

import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.BdrealTimeSp.*;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.BjDcLotteryQuery.BjDcLotteryQueryFirstRsp;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.BjDcLotteryQuery.BjDcLotteryQueryRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @Description 对应接口 34.2北京单场开奖查询
 * @Author cuishuai
 * @Date 2019/9/11 13:24
 */
@Configuration
@EnableScheduling
@Slf4j
public class BjDcLotteryQueryJob {
    @Value("${custom.qiutan.url.bdLotterQueryUrl}")
    String requestUrl;


    /**
     * 四分钟
     */
    @Scheduled(cron = "0 0/4 * * * ?")
    public void execute(){
        log.error("北京单场实时SP值定时任务启动");
        long s = System.currentTimeMillis();
        try {
            BjDcLotteryQueryFirstRsp object  = (BjDcLotteryQueryFirstRsp) new QiuTanXmlComm().handleMothod(requestUrl,BjDcLotteryQueryFirstRsp.class,BjDcLotteryQueryRsp.class);
        } catch (Exception e) {
            log.error("北京单场实时SP值定时任务启动异常",e);
            e.printStackTrace();
        }
        log.error("北京单场实时SP值定时任务启动,耗时："+(System.currentTimeMillis()-s)+"毫秒");
    }
}
