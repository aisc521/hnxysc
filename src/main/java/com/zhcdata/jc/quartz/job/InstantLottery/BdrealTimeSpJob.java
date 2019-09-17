package com.zhcdata.jc.quartz.job.InstantLottery;

import com.zhcdata.db.model.JcMatchBjdcPl;
import com.zhcdata.db.model.JcMatchLottery;
import com.zhcdata.jc.service.JcMatchBjdcPlService;
import com.zhcdata.jc.service.JcMatchLotteryService;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.BdrealTimeSp.*;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.Odds.*;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 对应接口 34.1北京单场实时SP值
 * @Author cuishuai
 * @Date 2019/9/11 11:52
 */
@Configuration
@EnableScheduling
@Slf4j
@Component
public class BdrealTimeSpJob implements Job {
    @Value("${custom.qiutan.url.bdRealTimeSpUrl}")
    String requestUrl;


    @Resource
    private JcMatchLotteryService JcMatchLotteryService;

    @Resource
    private JcMatchBjdcPlService jcMatchBjdcPlService;

    /**
     * 四分钟
     */
    /*@Scheduled(cron = "0 0/4 * * * ?")*/
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException{
        log.error("北京单场实时SP值定时任务启动");
        long s = System.currentTimeMillis();
        try {
            BdrealimeSpFirstRsp object  = (BdrealimeSpFirstRsp) new QiuTanXmlComm().handleMothod(requestUrl,BdrealimeSpFirstRsp.class,BdrealimeSpRsp.class,BdrealimeSpSpfRsp.class,
                    BdrealimeSpBfRsp.class,BdrealimeSpBqcRsp.class,BdrealimeSpSxdsRsp.class);

            List<BdrealimeSpRsp> list = object.getList();
            if(list.size() > 0){
                for(int i = 0; i < list.size(); i++){
                    BdrealimeSpRsp bdrealimeSpRsp = list.get(i);
                    String issueNum = bdrealimeSpRsp.getIssueNum();
                    String noId = bdrealimeSpRsp.getID();
                    //根据期号和场次查询lottery表查询 bet007 字段信息
                    JcMatchLottery jcMatchLottery = JcMatchLotteryService.queryJcMatchLotteryByIssueNumAndNoId(issueNum,noId);
                    if(jcMatchLottery != null){
                        List<JcMatchBjdcPl> jcMatchBjdcPl = jcMatchBjdcPlService.queryJcMatchBjdcPlByIssuemAndNoId(issueNum,noId);
                        if(jcMatchBjdcPl.size() > 0){//更新
                            jcMatchBjdcPlService.updateJcMatchBjdcPl(jcMatchBjdcPl,jcMatchLottery,bdrealimeSpRsp);
                        }else{//新增
                            jcMatchBjdcPlService.insertJcMatchBjdcPl(jcMatchLottery,bdrealimeSpRsp);
                        }
                    }else{
                        log.error("北京单场实时SP值定时任务查询jcMatchLottery为空");
                    }
                }
            }else{
                log.error("北京单场实时SP值定时任务返回数据为空");
            }

        } catch (Exception e) {
            log.error("北京单场实时SP值定时任务启动异常",e);
            e.printStackTrace();
        }
        log.error("北京单场实时SP值定时任务启动,耗时："+(System.currentTimeMillis()-s)+"毫秒");
    }
}
