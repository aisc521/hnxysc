package com.zhcdata.jc.quartz.job.InstantLottery;

import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.db.model.JcSchedulesp;
import com.zhcdata.jc.service.JcScheduleService;
import com.zhcdata.jc.service.JcSchedulespService;
import com.zhcdata.jc.service.JcSchedulespvaryService;
import com.zhcdata.jc.service.LotteryTypeMatchJobService;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.Odds.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

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
public class JcFootBallOddsJob {
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

    /**
     * 两分钟
     */
    //@Scheduled(cron = "0 0/2 * * * ?")
    public void execute(){
        log.error("竞彩足球赔率定时任务启动");
        long s = System.currentTimeMillis();
        try {
            JcFootBallOddsFirstRsp object  = (JcFootBallOddsFirstRsp) new QiuTanXmlComm().handleMothod(requestUrl,JcFootBallOddsFirstRsp.class,JcFootBallOddsRsp.class,JcFootBallOddsRqRsp.class,
                    JcFootBallOddsBfRsp.class,JcFootBallOddsJqRsp.class,JcFootBallOddsBqcRsp.class,JcFootBallOddsSfRsp.class);
            //查询竞彩足球彩种库  查询比赛id 入库
            List<JcFootBallOddsRsp> jcFootBallOddsRspList = object.getList();
            for(int i = 0; i < jcFootBallOddsRspList.size(); i++){
                JcFootBallOddsRsp jcFootBallOddsRsp = jcFootBallOddsRspList.get(i);
                JcSchedule jcSchedule = jcScheduleService.queryJcScheduleByMatchID(jcFootBallOddsRsp.getID());
                if(jcSchedule != null){
                    //每一次sp变化都insert入库
                    /**
                     * 问题
                     * 1、每个玩法的开奖sp
                     * 2、过关类型
                     */
                    //查询是否有此条信息
                    JcSchedulesp jcSchedulesp = jcSchedulespService.queryJcSchedulespById(jcSchedule.getScheduleid());
                    if(jcSchedulesp != null){//更新
                        jcSchedulespService.updateJcSchedulesp(jcSchedulesp,jcSchedule,jcFootBallOddsRsp);
                    }else{//新增

                    }
                    Integer spId = jcSchedulespService.insertJcSchedulesp(jcSchedule,jcFootBallOddsRsp);




                    //插入竞彩sp变化表数据
                    /**
                     * 问题
                     * 1、彩种id是哪个id
                     * 2、sp变化的记录格式
                     * 3、变化时间是每次的创建时间？
                     */
                    jcSchedulespvaryService.insertJcSchedulespvary(jcSchedule,jcFootBallOddsRsp,spId);
                    //更新竞彩对阵表
                    jcScheduleService.updateJcSchedule(jcSchedule,jcFootBallOddsRsp);
                }else{
                    log.error("竞彩足球赔率定时任务根据MacthId查询结果为空，MactchId：" + jcFootBallOddsRsp.getID());
                }
            }

        } catch (Exception e) {
            log.error("竞彩足球赔率定时任务启动异常",e);
            e.printStackTrace();
        }
        log.error("竞彩足球赔率定时任务启动,耗时："+(System.currentTimeMillis()-s)+"毫秒");
    }
}
