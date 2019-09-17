package com.zhcdata.jc.quartz.job.InstantLottery;

import com.zhcdata.db.model.JcMatchBjdcPl;
import com.zhcdata.db.model.JcMatchBjdcreslut;
import com.zhcdata.db.model.JcMatchLottery;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.service.JcMatchBjdcPlService;
import com.zhcdata.jc.service.JcMatchBjdcreslutService;
import com.zhcdata.jc.service.JcMatchLotteryService;
import com.zhcdata.jc.service.ScheduleService;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.BdrealTimeSp.*;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.BjDcLotteryQuery.BjDcLotteryQueryFirstRsp;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.BjDcLotteryQuery.BjDcLotteryQueryRsp;
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
 * @Description 对应接口 34.2北京单场开奖查询
 * @Author cuishuai
 * @Date 2019/9/11 13:24
 */
@Configuration
@EnableScheduling
@Slf4j
@Component
public class BjDcLotteryQueryJob implements Job {
    @Value("${custom.qiutan.url.bdLotterQueryUrl}")
    String requestUrl;
    @Resource
    private com.zhcdata.jc.service.JcMatchLotteryService JcMatchLotteryService;

    @Resource
    private JcMatchBjdcreslutService jcMatchBjdcreslutService;
    @Resource
    private ScheduleService scheduleService;
    @Resource
    private JcMatchBjdcPlService jcMatchBjdcPlService;
    /**
     * 四分钟
     */
    /*@Scheduled(cron = "0 0/4 * * * ?")*/
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.error("北京单场开奖SP值定时任务启动");
        long s = System.currentTimeMillis();
        try {
            BjDcLotteryQueryFirstRsp object  = (BjDcLotteryQueryFirstRsp) new QiuTanXmlComm().handleMothod(requestUrl,BjDcLotteryQueryFirstRsp.class,BjDcLotteryQueryRsp.class);
            List<BjDcLotteryQueryRsp> match = object.getMatch();
            if(match.size() > 0){
                for(int i = 0; i < match.size(); i++){
                    BjDcLotteryQueryRsp bjDcLotteryQueryRsp = match.get(i);
                    //根据期号和场次查询lottery表查询 bet007 字段信息
                    JcMatchLottery jcMatchLottery = JcMatchLotteryService.queryJcMatchLotteryByIssueNumAndNoId(bjDcLotteryQueryRsp.getIssueNum(),bjDcLotteryQueryRsp.getID());
                    if(jcMatchLottery != null){
                        //根据bet007查询赛程表 查询对应的赛果
                        Schedule schedule = scheduleService.queryScheduleById(jcMatchLottery.getIdBet007());
                        if(schedule != null){
                            //查询赔率信息
                            List<JcMatchBjdcPl> jcMatchBjdcPls = jcMatchBjdcPlService.queryJcMatchBjdcPlByIssuemAndNoId(bjDcLotteryQueryRsp.getIssueNum(),bjDcLotteryQueryRsp.getID());
                            if(jcMatchBjdcPls.size() > 0){
                                //查询赛果表 有的话更新  没有新增
                                JcMatchBjdcreslut jcMatchBjdcreslut = jcMatchBjdcreslutService.queryJcMatchBjdcreslutByBet007(Integer.parseInt(String.valueOf(jcMatchLottery.getIdBet007())));
                                if(jcMatchBjdcreslut != null){//更新
                                    jcMatchBjdcreslutService.updateJcMatchBjdcreslut(schedule,jcMatchBjdcreslut,jcMatchBjdcPls,bjDcLotteryQueryRsp);
                                }else{//新增
                                    jcMatchBjdcreslutService.insertJcMatchBjdcreslut(schedule,jcMatchBjdcPls,bjDcLotteryQueryRsp);
                                }
                            }else{
                                log.error("北京单场开奖SP值根据期号和Id查询无对应赔率数据,期号+Id = " +  bjDcLotteryQueryRsp.getIssueNum() + "@" + bjDcLotteryQueryRsp.getID());
                            }

                        }else{
                            log.error("北京单场开奖SP值根据期号和Id查询无对应赛程信息,赛程Id = " +  jcMatchLottery.getIdBet007());
                        }
                    }else {
                        log.error("北京单场开奖SP值根据期号和Id查询无对应数据,期号+Id = " +  bjDcLotteryQueryRsp.getIssueNum() + "@" + bjDcLotteryQueryRsp.getID());
                    }
                }

            }else{
                log.error("北京单场开奖SP值定时任务返回数据为空");
            }

        } catch (Exception e) {
            log.error("北京单场开奖SP值定时任务启动异常",e);
            e.printStackTrace();
        }
        log.error("北京单场开奖SP值定时任务启动,耗时："+(System.currentTimeMillis()-s)+"毫秒");
    }
}
