package com.zhcdata.jc.quartz.job.InstantLottery;

import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.BdrealTimeSp.*;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.LotteryScore.LotteryScoreFirstRsp;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.LotteryScore.LotteryScoreRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @Description 对应接口 35.彩票比赛的比分数据
 * @Author cuishuai
 * @Date 2019/9/11 13:32
 */
@Configuration
@EnableScheduling
@Slf4j
public class LotteryScoreJob {
    @Value("${custom.qiutan.url.lotterScoreUrl}")
    String requestUrl;
    /**
     * 1分钟
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void execute(){
        log.error("彩票比赛的比分数据定时任务启动");
        long s = System.currentTimeMillis();
        try {
            LotteryScoreFirstRsp object  = (LotteryScoreFirstRsp) new QiuTanXmlComm().handleMothod(requestUrl,LotteryScoreFirstRsp.class,LotteryScoreRsp.class);
        } catch (Exception e) {
            log.error("彩票比赛的比分数据定时任务启动异常",e);
            e.printStackTrace();
        }
        log.error("彩票比赛的比分数据定时任务启动,耗时："+(System.currentTimeMillis()-s)+"毫秒");
    }
}
