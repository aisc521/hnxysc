package com.zhcdata.jc.quartz.job.InstantLottery;
import com.zhcdata.db.model.JcMatchBjdc;
import com.zhcdata.db.model.JcMatchJczq;
import com.zhcdata.db.model.JcMatchZc;
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

                if(lotteryTypeMatchRsp.getLotteryName().trim().equals(Const.ONE_FOUR_SFC)){//足彩
                    JcMatchZc jcLotterTypeZc = lotteryTypeMatchJobService.queryJcLotterTypeZcByIDBet007(Long.parseLong(lotteryTypeMatchRsp.getID_bet007()));
                    if(jcLotterTypeZc != null){
                        lotteryTypeMatchJobService.updateJcLotterTypeZc(jcLotterTypeZc,lotteryTypeMatchRsp);
                    }else{
                        lotteryTypeMatchJobService.insertJcLotterTypeZc(lotteryTypeMatchRsp);
                    }
                }
                if(lotteryTypeMatchRsp.getLotteryName().trim().equals(Const.JC_FOOTBALL)){//竞彩
                    JcMatchJczq jcLotterTypeJc = lotteryTypeMatchJobService.queryJcLotterTypeJcByIDBet007(Long.parseLong(lotteryTypeMatchRsp.getID_bet007()));
                    if(jcLotterTypeJc != null){
                        lotteryTypeMatchJobService.updateJcLotterTypeJc(jcLotterTypeJc,lotteryTypeMatchRsp);
                    }else{
                        lotteryTypeMatchJobService.insertJcLotterTypeJc(lotteryTypeMatchRsp);
                    }
                }
                if(lotteryTypeMatchRsp.getLotteryName().trim().equals(Const.SINGLE_R_SPF)){//北单
                    JcMatchBjdc jcLotterTypeBd = lotteryTypeMatchJobService.queryJcLotterTypeBdByIDBet007(Long.parseLong(lotteryTypeMatchRsp.getID_bet007()));
                    if(jcLotterTypeBd != null){
                        lotteryTypeMatchJobService.updateJcLotterTypeBd(jcLotterTypeBd,lotteryTypeMatchRsp);
                    }else{
                        lotteryTypeMatchJobService.insertJcLotterTypeBd(lotteryTypeMatchRsp);
                    }
                }
            }
        } catch (Exception e) {
            log.error("彩票赛程与球探网ID关联表定时任务启动异常",e);
            e.printStackTrace();
        }
        log.error("彩票赛程与球探网ID关联表定时任务启动,耗时："+(System.currentTimeMillis()-s)+"毫秒");
    }
}
