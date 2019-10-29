package com.zhcdata.jc.quartz.job.InstantLottery;

import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.db.model.JcSchedulesp;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.service.*;
import com.zhcdata.jc.xml.QiuTanXmlComm;
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
 * @Description 对应接口 32.竞彩足球赔率
 * @Author cuishuai
 * @Date 2019/9/11 10:13
 */
@Configuration
@EnableScheduling
@Slf4j
@Component
public class JcFootBallOddsJob implements Job {
    @Resource
    private LotteryTypeMatchJobService lotteryTypeMatchJobService;
    @Value("${custom.qiutan.url.jcZqOddsUrl}")
    String requestUrl;


    @Resource
    private JcScheduleService jcScheduleService;

    @Resource
    private JcSchedulespService jcSchedulespService;

    @Resource
    private JcSchedulespvaryService jcSchedulespvaryService;

    @Resource
    private ScheduleService scheduleService;
    /**
     * 两分钟
     */
   /* @Scheduled(cron = "0 0/2 * * * ?")*/
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.error("竞彩足球赔率定时任务启动");
        long s = System.currentTimeMillis();
        try {
            JcFootBallOddsFirstRsp object  = (JcFootBallOddsFirstRsp) new QiuTanXmlComm().handleMothod(requestUrl,JcFootBallOddsFirstRsp.class,JcFootBallOddsRsp.class,JcFootBallOddsRqRsp.class,
                    JcFootBallOddsBfRsp.class,JcFootBallOddsJqRsp.class,JcFootBallOddsBqcRsp.class,JcFootBallOddsSfRsp.class);
            //查询竞彩足球彩种库  查询比赛id 入库
            List<JcFootBallOddsRsp> jcFootBallOddsRspList = object.getList();
            if(jcFootBallOddsRspList != null && jcFootBallOddsRspList.size() > 0){
                for(int i = 0; i < jcFootBallOddsRspList.size(); i++){
                    JcFootBallOddsRsp jcFootBallOddsRsp = jcFootBallOddsRspList.get(i);
                    String [] timeArr =  jcFootBallOddsRsp.getMatchTime().split("\\s+");
                    String time = timeArr[0];
                    JcSchedule jcSchedule = jcScheduleService.queryJcScheduleByMatchID(jcFootBallOddsRsp.getID(),time);
                    if(jcSchedule != null){
                        //查询是否有此条信息
                        //查询赛程表
                        Schedule schedule = scheduleService.queryScheduleById(Long.valueOf(jcSchedule.getScheduleid()));
                        if(schedule != null){
                            JcSchedulesp jcSchedulesp = jcSchedulespService.queryJcSchedulespById(jcSchedule.getScheduleid());
                            if(jcSchedulesp != null){//更新
                                jcSchedulespService.updateJcSchedulesp(jcSchedulesp,jcSchedule,jcFootBallOddsRsp,schedule);
                                //插入竞彩sp变化表数据
                                jcSchedulespvaryService.insertJcSchedulespvary(jcSchedule,jcFootBallOddsRsp,jcSchedulesp.getSpid(),jcSchedulesp,schedule);
                            }else{//新增
                                Integer spId = jcSchedulespService.insertJcSchedulesp(jcSchedule,jcFootBallOddsRsp,schedule);
                                //插入竞彩sp变化表数据
                                jcSchedulespvaryService.insertJcSchedulespvary(jcSchedule,jcFootBallOddsRsp,spId,jcSchedulesp,schedule);
                            }
                            //更新竞彩对阵表
                            jcSchedule.setHometeam(jcFootBallOddsRspList.get(i).getHome());
                            jcSchedule.setGuestteam(jcFootBallOddsRspList.get(i).getAway());
                            jcScheduleService.updateJcSchedule(jcSchedule,jcFootBallOddsRsp);
                        }else{
                            log.error("竞彩足球赔率定时任务查询赛程为空，Scheduleid：" + jcSchedule.getScheduleid());
                        }

                    }else{
                        log.error("竞彩足球赔率定时任务根据MacthId查询结果为空，MactchId：" + jcFootBallOddsRsp.getID());
                    }
                }
            }else{
                log.error("竞彩足球赔率定时任务返回数据为空");
            }
        } catch (Exception e) {
            log.error("竞彩足球赔率定时任务启动异常",e);
            e.printStackTrace();
        }
        log.error("竞彩足球赔率定时任务启动,耗时："+(System.currentTimeMillis()-s)+"毫秒");
    }
}
