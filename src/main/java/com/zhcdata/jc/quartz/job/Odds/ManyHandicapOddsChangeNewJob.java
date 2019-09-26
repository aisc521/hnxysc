package com.zhcdata.jc.quartz.job.Odds;

import com.zhcdata.jc.service.ManyHandicapOddsChangeService;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.MoreHandicapOddsARsp;
import com.zhcdata.jc.xml.rsp.MoreHandicapOddsLisAlltRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 21.多盘口赔率：30秒内变化赔率接口
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableScheduling
@Component
@Slf4j
public class ManyHandicapOddsChangeNewJob {

    @Resource(name="changeHalfHandicapHandleServiceImpl")
    ManyHandicapOddsChangeService changeHalfHandicapHandleServiceImpl;

    @Resource(name="changeHalfSizesBallsHandleServiceImpl")
    ManyHandicapOddsChangeService changeHalfSizesBallsHandleServiceImpl;

    @Resource(name="changeHandicapHandleServiceImpl")
    ManyHandicapOddsChangeService changeHandicapHandleServiceImpl;

    @Resource(name="changeOddsHandleServiceImpl")
    ManyHandicapOddsChangeService changeOddsHandleServiceImpl;

    @Resource(name="changeSizesBallsHandleServiceImpl")
    ManyHandicapOddsChangeService changeSizesBallsHandleServiceImpl;

    @Scheduled(cron = "0/30 * * * * ?")
    public void execute() {

        ManyHandicapOddsChangeService array[] = {changeHandicapHandleServiceImpl,changeOddsHandleServiceImpl,changeSizesBallsHandleServiceImpl,changeHalfHandicapHandleServiceImpl,changeHalfSizesBallsHandleServiceImpl};

        String url = "http://interface.win007.com/zq/ch_odds_m.xml";
        MoreHandicapOddsLisAlltRsp rsp  = (MoreHandicapOddsLisAlltRsp) new QiuTanXmlComm().handleMothodHttpGet(url,MoreHandicapOddsLisAlltRsp.class,List.class,MoreHandicapOddsARsp.class);
        for(ManyHandicapOddsChangeService bean:array){
            bean.changeHandle(rsp);
        }
    }
}