package com.zhcdata.jc.quartz.job.Odds;

import com.zhcdata.jc.service.ManyHandicapOddsChangeService;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.MoreHandicapOddsARsp;
import com.zhcdata.jc.xml.rsp.MoreHandicapOddsLisAlltRsp;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * 21.多盘口赔率：30秒内变化赔率接口
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
//@Configuration
//@EnableScheduling
@Component
@Slf4j
public class ManyHandicapOddsChangeNewJob implements Job {

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


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.error("21.多盘口赔率：30秒内变化赔率接口开始");
        long start = System.currentTimeMillis();
        ManyHandicapOddsChangeService array[] = {changeHandicapHandleServiceImpl,changeOddsHandleServiceImpl,changeSizesBallsHandleServiceImpl,changeHalfHandicapHandleServiceImpl,changeHalfSizesBallsHandleServiceImpl};
        //ManyHandicapOddsChangeService array[] = {changeHandicapHandleServiceImpl};

        String randomParam = "?uuid"+UUID.randomUUID().toString().replace("-", "").substring(0,8)+"="+System.currentTimeMillis();
        String url = "http://interface.win007.com/zq/ch_odds_m.xml"+randomParam;
        MoreHandicapOddsLisAlltRsp rsp  = (MoreHandicapOddsLisAlltRsp) new QiuTanXmlComm().handleMothodHttpGet(url,MoreHandicapOddsLisAlltRsp.class,List.class,MoreHandicapOddsARsp.class);
        for(ManyHandicapOddsChangeService bean:array){
            bean.changeHandle(rsp);
        }
        log.error("21.多盘口赔率：30秒内变化赔率接口结束,耗时"+(System.currentTimeMillis()-start)+"ms");
    }


    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            String randomParam = "?uuid"+UUID.randomUUID().toString().replace("-", "").substring(0,8)+"="+System.currentTimeMillis();
            String url = "http://interface.win007.com/zq/ch_odds_m.xml"+randomParam;
            System.out.println(url);
            Thread.sleep(1);
        }
    }
}