package com.zhcdata.jc.quartz.job.InstantLottery;
import com.zhcdata.db.model.JcMatchLottery;
import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.db.model.JcSchedulesp;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.service.LotteryTypeMatchJobService;
import com.zhcdata.jc.tools.Const;
import com.zhcdata.jc.tools.JcLotteryUtils;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.LotterType.LotteryTypeMatchFristRsp;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.LotterType.LotteryTypeMatchRsp;
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
@Component
public class LotteryTypeMatchJob implements Job {
    @Resource
    private LotteryTypeMatchJobService lotteryTypeMatchJobService;
    @Value("${custom.qiutan.url.lotterTypeMacthUrl}")
    String requestUrl;

    /**
     * 每十分钟执行一次 定时任务
     */

   /* @Scheduled(cron = "0 0/10 * * * ?")*/
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.error("彩票赛程与球探网ID关联表定时任务启动");
        long s = System.currentTimeMillis();
        try {
            LotteryTypeMatchFristRsp object  = (LotteryTypeMatchFristRsp) new QiuTanXmlComm().handleMothod(requestUrl,LotteryTypeMatchFristRsp.class,LotteryTypeMatchRsp.class);
            List<LotteryTypeMatchRsp> lotteryTypeMatchRspList = object.getList();
            if(lotteryTypeMatchRspList != null && lotteryTypeMatchRspList.size() > 0){
                for(int i = 0; i < lotteryTypeMatchRspList.size(); i++){
                    try{
                        LotteryTypeMatchRsp lotteryTypeMatchRsp = lotteryTypeMatchRspList.get(i);
                        //全部入lottery表数据
                        //判断是否是足球玩法
                        if(!"0".equals(lotteryTypeMatchRsp.getID_bet007())){
                            //根据bet007 和玩法查询是否有对应数据
                            String gameType = JcLotteryUtils.JcLotterZh(lotteryTypeMatchRsp.getLotteryName().trim());
                            JcMatchLottery jcMatchLottery=null;
                            if(gameType.equals("SF14")||gameType.equals("BJDC")) {
                                jcMatchLottery = lotteryTypeMatchJobService.queryJcMatchLotteryByBet007(Long.parseLong(lotteryTypeMatchRsp.getID_bet007()),gameType);
                                if(jcMatchLottery != null){//
                                    //jcMatchLottery.setLottery(jcMatchLottery.getLottery() + "_");
                                    lotteryTypeMatchJobService.deleteMatchLotteryById(jcMatchLottery);
                                }
                                jcMatchLottery = lotteryTypeMatchJobService.queryJcMatchLotteryByBet007_1(Long.parseLong(lotteryTypeMatchRsp.getIssueNum()),gameType, lotteryTypeMatchRsp.getID());
                            }else {
                                jcMatchLottery = lotteryTypeMatchJobService.queryJcMatchLotteryByBet007(Long.parseLong(lotteryTypeMatchRsp.getID_bet007()),gameType);
                            }
                            if(jcMatchLottery != null){//更新
                                lotteryTypeMatchJobService.updateJcMatchLottery(jcMatchLottery,lotteryTypeMatchRsp);
                            }else{//新增
                                lotteryTypeMatchJobService.insertJcMatchLottery(lotteryTypeMatchRsp);
                            }

                            //判断是否是竞彩的玩法
                            if("JCZQ".equals(gameType)){
                                //单独记录竞彩数据
                                //根据bet007查询竞彩表是否有对应数据
                                JcSchedule jcSchedule = lotteryTypeMatchJobService.queryJcScheduleByBet007(Long.parseLong(lotteryTypeMatchRsp.getID_bet007()));
                                //根据bet007查询赛程表
                                Schedule schedule = lotteryTypeMatchJobService.queryScheduleByBet007(Integer.parseInt(lotteryTypeMatchRsp.getID_bet007()));
                                //根据id查询竞彩足球赔率表
                                //JcSchedulesp jcSchedulesp = lotteryTypeMatchJobService.queryJcSchedulespByScId(jcSchedule.getId());
                                if(jcSchedule != null){//更新
                                    lotteryTypeMatchJobService.updateJcSchedule(jcSchedule,schedule,lotteryTypeMatchRsp);
                                }else{//新增
                                    lotteryTypeMatchJobService.insertJcSchedule(schedule,lotteryTypeMatchRsp);
                                }
                            }else{
                                log.error("彩票赛程与球探网ID关联表定时任务返回数据不是竞彩玩法不需入库");
                            }

                        }else{
                            log.error("彩票赛程与球探网ID关联表定时任务返回数据不是足球玩法：玩法名称:" + lotteryTypeMatchRsp.getLotteryName());
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }else{
                log.error("彩票赛程与球探网ID关联表定时任务返回数据为空");
            }
        } catch (Exception e) {
            log.error("彩票赛程与球探网ID关联表定时任务启动异常",e);
            e.printStackTrace();
        }
        log.error("彩票赛程与球探网ID关联表定时任务启动,耗时："+(System.currentTimeMillis()-s)+"毫秒");
    }
}
