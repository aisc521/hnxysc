package com.zhcdata.jc.quartz.job.InstantLottery;
import com.zhcdata.db.model.JcMatchLottery;
import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.db.model.JcSchedulesp;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.service.LotteryTypeMatchJobService;
import com.zhcdata.jc.tools.Const;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.LotterType.LotteryTypeMatchFristRsp;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.LotterType.LotteryTypeMatchRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import javax.annotation.Resource;

import java.util.List;


/**
 * @Description 对应接口： 8.彩票赛程与球探网ID关联表
 *              功   能:  区分赛事彩种信息
 *              14场胜负彩     足彩
 *              单场让球胜平负  北单
 *              竞彩足球       竞彩
 * @Author cuishuai
 * @Date 2019/9/10 15:24
 */
@Configuration
@EnableScheduling
@Slf4j
public class LotteryTypeMatchJob{
    @Resource
    private LotteryTypeMatchJobService lotteryTypeMatchJobService;
    @Value("${custom.qiutan.url.lotterTypeMacthUrl}")
    String requestUrl;

    /**
     * 每十分钟执行一次 定时任务
     */

    @Scheduled(cron = "0 0/10 * * * ?")
    public void execute(){
        log.error("彩票赛程与球探网ID关联表定时任务启动");
        long s = System.currentTimeMillis();
        try {
            LotteryTypeMatchFristRsp object  = (LotteryTypeMatchFristRsp) new QiuTanXmlComm().handleMothod(requestUrl,LotteryTypeMatchFristRsp.class,LotteryTypeMatchRsp.class);
            List<LotteryTypeMatchRsp> lotteryTypeMatchRspList = object.getList();
            for(int i = 0; i < lotteryTypeMatchRspList.size(); i++){
                LotteryTypeMatchRsp lotteryTypeMatchRsp = lotteryTypeMatchRspList.get(i);
                //全部入lottery表数据
                //根据bet007查询是否有对应数据
                JcMatchLottery jcMatchLottery = lotteryTypeMatchJobService.queryJcMatchLotteryByBet007(Long.parseLong(lotteryTypeMatchRsp.getID_bet007()));
                if(jcMatchLottery != null){//更新
                    lotteryTypeMatchJobService.updateJcMatchLottery(jcMatchLottery,lotteryTypeMatchRsp);
                }else{//新增
                    lotteryTypeMatchJobService.insertJcMatchLottery(lotteryTypeMatchRsp);
                }
                //单独记录竞彩数据
                //根据bet007查询竞彩表是否有对应数据
                JcSchedule jcSchedule = lotteryTypeMatchJobService.queryJcScheduleByBet007(Long.parseLong(lotteryTypeMatchRsp.getID_bet007()));
                //根据bet007查询赛程表
                Schedule schedule = lotteryTypeMatchJobService.queryScheduleByBet007(Integer.parseInt(lotteryTypeMatchRsp.getID_bet007()));
                //根据id查询竞彩足球赔率表
                JcSchedulesp jcSchedulesp = lotteryTypeMatchJobService.queryJcSchedulespByScId(Integer.parseInt(lotteryTypeMatchRsp.getID_bet007()));
                if(jcSchedule != null){//更新
                    lotteryTypeMatchJobService.updateJcSchedule(jcSchedule,schedule,jcSchedulesp,lotteryTypeMatchRsp);
                }else{//新增
                    lotteryTypeMatchJobService.insertJcSchedule(schedule,jcSchedulesp,lotteryTypeMatchRsp);
                }
            }
        } catch (Exception e) {
            log.error("彩票赛程与球探网ID关联表定时任务启动异常",e);
            e.printStackTrace();
        }
        log.error("彩票赛程与球探网ID关联表定时任务启动,耗时："+(System.currentTimeMillis()-s)+"毫秒");
    }
}
