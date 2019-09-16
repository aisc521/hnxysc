package com.zhcdata.jc.quartz.job.InstantLottery;

import com.zhcdata.db.model.JcMatchJczq;
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
                JcMatchJczq jcLotterTypeJc = lotteryTypeMatchJobService.queryJcLotterTypeJcByNoId(jcFootBallOddsRsp.getID());
                if(jcLotterTypeJc != null){
                    Long matchId = jcLotterTypeJc.getIdBet007();
                    System.out.println(matchId);
                }else{
                    log.error("查询竞彩彩种信息为空，ID：" + jcFootBallOddsRsp.getID());
                }
            }

        } catch (Exception e) {
            log.error("竞彩足球赔率定时任务启动异常",e);
            e.printStackTrace();
        }
        log.error("竞彩足球赔率定时任务启动,耗时："+(System.currentTimeMillis()-s)+"毫秒");
    }
}
