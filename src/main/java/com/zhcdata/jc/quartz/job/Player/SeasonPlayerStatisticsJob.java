package com.zhcdata.jc.quartz.job.Player;

import com.zhcdata.db.model.Sclass;
import com.zhcdata.db.model.SeasonPlayerStatistics;
import com.zhcdata.jc.service.SclassService;
import com.zhcdata.jc.service.SeasonPlayerStatisticsService;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.PlayerStaticsRsp.PlayerStaticFirstRsp;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.PlayerStaticsRsp.PlayerStaticRsp;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 球员赛季  数据统计
 * @Author cuishuai
 * @Date 2019/11/12 16:34
 */

@Configuration
@EnableScheduling
@Slf4j
@Component
public class SeasonPlayerStatisticsJob implements Job{

    @Value("${custom.qiutan.url.seasonUrl}")
    String requestUrl;

    @Resource
    private SeasonPlayerStatisticsService seasonPlayerStatisticsService;
    @Resource
    private SclassService sclassService;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.error("赛季球员数据统计定时任务启动");
        long s = System.currentTimeMillis();
        try {
            //查询所有联赛id
            List<Sclass> sclassList = sclassService.querySclassInfo();
            if(sclassList == null && sclassList.size() <= 0){
                return;
            }
            for(int i = 0; i < sclassList.size(); i++){
                Sclass sclass = sclassList.get(i);
                ArrayList arrayList = (ArrayList)new QiuTanXmlComm().handleMothod(requestUrl + sclass.getSclassid(),PlayerStaticRsp.class);
                if(arrayList.size() <= 0){
                    continue;
                }
                PlayerStaticFirstRsp object  = (PlayerStaticFirstRsp) new QiuTanXmlComm().handleMothod(requestUrl + sclass.getSclassid(),PlayerStaticFirstRsp.class,PlayerStaticRsp.class);
                List<PlayerStaticRsp> list = object.getList();
                for(int j = 0; j < list.size(); j++){
                    PlayerStaticRsp playerStaticRsp = list.get(j);
                   //根据球员id  联赛id  赛季 查询 是否有此数据 如果有 更新  没有新增
                    SeasonPlayerStatistics seasonPlayerStatistics = seasonPlayerStatisticsService.queryByPlayIdLenIdSeason(playerStaticRsp.getID(),playerStaticRsp.getLeagueID(),playerStaticRsp.getMatchSeason());
                    if(seasonPlayerStatistics != null){//更新
                        seasonPlayerStatisticsService.updateSeasonPlayerStatistics(seasonPlayerStatistics,playerStaticRsp);
                    }else{//新增
                        seasonPlayerStatisticsService.insertSeasonPlayerStatistics(playerStaticRsp);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("赛季球员数据统计定时任务异常:" + e.getCause());
        }
        log.error("赛季球员数据统计定时任务启动,耗时："+(System.currentTimeMillis()-s)+"毫秒");
    }
}
