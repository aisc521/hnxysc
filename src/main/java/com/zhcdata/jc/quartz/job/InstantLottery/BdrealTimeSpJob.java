package com.zhcdata.jc.quartz.job.InstantLottery;

import com.zhcdata.db.model.JcMatchJczq;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.BdrealTimeSp.*;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.Odds.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * @Description 对应接口 34.1北京单场实时SP值
 * @Author cuishuai
 * @Date 2019/9/11 11:52
 */
@Configuration
@EnableScheduling
@Slf4j
public class BdrealTimeSpJob {
    @Value("${custom.qiutan.url.bdRealTimeSpUrl}")
    String requestUrl;


    /**
     * 四分钟
     */
    @Scheduled(cron = "0 0/4 * * * ?")
    public void execute(){
        log.error("北京单场实时SP值定时任务启动");
        long s = System.currentTimeMillis();
        try {
            BdrealimeSpFirstRsp object  = (BdrealimeSpFirstRsp) new QiuTanXmlComm().handleMothod(requestUrl,BdrealimeSpFirstRsp.class,BdrealimeSpRsp.class,BdrealimeSpSpfRsp.class,
                    BdrealimeSpBfRsp.class,BdrealimeSpBqcRsp.class,BdrealimeSpSxdsRsp.class);
        } catch (Exception e) {
            log.error("北京单场实时SP值定时任务启动异常",e);
            e.printStackTrace();
        }
        log.error("北京单场实时SP值定时任务启动,耗时："+(System.currentTimeMillis()-s)+"毫秒");
    }
}
