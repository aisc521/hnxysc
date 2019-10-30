package com.zhcdata.jc.quartz.job.Odds;

import com.zhcdata.jc.service.MultHandicapOddsService;
import com.zhcdata.jc.tools.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

import static com.zhcdata.jc.quartz.job.Odds.FlagInfo.*;

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
        System.err.println("亚盘list条目："+multi_yp.size());
        System.err.println("欧赔list条目："+multi_ou.size());
        System.err.println("大小list条目："+multi_dx.size());
        System.err.println("半场亚盘list条目："+multi_hyp.size());
        System.err.println("半场大小list条目："+multi_hdx.size());
        log.info("多盘即时赔率解析开始");

        String str = "";
        try {
            str = HttpUtils.httpGet("http://interface.win007.com/zq/Odds_Mult.aspx", null);
        } catch (Exception e) {
            log.error("获取多盘口即时赔率失败",e);
            return;
        }
        String[] oddsCollection = str.split("\\u0024");
        MultHandicapOddsService[] array = {multHandicapHandleServiceImpl, multOddsHandleServiceImpl, multSizesBallsHandleServiceImpl, multHalfHandicapHandleServiceImpl, multHalfSizesBallsHandleServiceImpl};
        for (int i = 0; i < array.length; i++) {
            try {
                array[i].changeHandle(oddsCollection[i].split(";"));
            }catch (ArrayIndexOutOfBoundsException e){
                log.error("获取多盘口即时赔率数组角标越界");
                log.error(str);
                log.error(Arrays.toString(array));
                log.error(Arrays.toString(oddsCollection));
                e.printStackTrace();
            }
        }
        log.info("多盘即时赔率解析结束");
    }
}