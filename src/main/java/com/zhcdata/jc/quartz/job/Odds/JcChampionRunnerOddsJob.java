package com.zhcdata.jc.quartz.job.Odds;

import com.zhcdata.db.model.JcChampionRunnerOdds;
import com.zhcdata.db.model.JcChampionRunnerOddsType;
import com.zhcdata.jc.service.JcChampionRunnerOddsService;
import com.zhcdata.jc.service.JcChampionRunnerOddsTypeService;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.JcChampionRunnerOddsRsp.JcChampionRunnerFirstOdds;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.JcChampionRunnerOddsRsp.JcChampionRunnerOddsRsp;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.PlayerStaticsRsp.PlayerStaticRsp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/11/13 16:59
 */
@Configuration
@EnableScheduling
@Slf4j
@Component
public class JcChampionRunnerOddsJob implements Job {

    @Value("${custom.qiutan.url.championRunnerOddsUrl}")
    String requestUrl;

    @Resource
    private JcChampionRunnerOddsService jcChampionRunnerOddsService;
    @Resource
    private JcChampionRunnerOddsTypeService jcChampionRunnerOddsTypeService;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.error("冠亚军赔率定时任务启动");
        long s = System.currentTimeMillis();
        try {
            JcChampionRunnerFirstOdds object  = (JcChampionRunnerFirstOdds) new QiuTanXmlComm().handleMothod(requestUrl,JcChampionRunnerFirstOdds.class,JcChampionRunnerOddsRsp.class);
            ArrayList arrayList = (ArrayList)new QiuTanXmlComm().handleMothod(requestUrl,JcChampionRunnerFirstOdds.class,JcChampionRunnerOddsRsp.class);
            if(arrayList.size() <= 0){
                return;
            }
            List<JcChampionRunnerOddsRsp> list = object.getList();
            if(list != null && list.size() > 0){
                for(int i = 0; i < list.size(); i++){
                    JcChampionRunnerOddsRsp jcChampionRunnerOddsRsp = list.get(i);
                    //jc_champion_runner_odds_type
                    String typeRsp = jcChampionRunnerOddsRsp.getType();
                    String type = "";
                    if(typeRsp.indexOf("冠亚军") != -1){//包含冠亚军  是 冠亚军玩法
                        type = "2";
                    }else{//冠军玩法
                        type = "1";
                    }
                    JcChampionRunnerOddsType jcChampionRunnerOddsType = jcChampionRunnerOddsTypeService.queryJcChampionRunnerOddsTypeByPlayTypeNameAndGameType(typeRsp,type);
                    if(jcChampionRunnerOddsType != null){//更新
                        int j = jcChampionRunnerOddsTypeService.updataJcChampionRunnerOddsType(jcChampionRunnerOddsType,jcChampionRunnerOddsRsp);
                        if(j > 0){//更新成功===查询冠亚军赔率表
                            JcChampionRunnerOdds jcChampionRunnerOdds = jcChampionRunnerOddsService.queryJcChampionRunnerOddsByTypeAndMatchIdAndTeams(typeRsp,jcChampionRunnerOddsRsp.getMatchID(),jcChampionRunnerOddsRsp.getTeams());
                            if(jcChampionRunnerOdds != null){//更新
                                jcChampionRunnerOddsService.updateJcChampionRunnerOdds(jcChampionRunnerOdds,jcChampionRunnerOddsRsp,j);
                            }else{//新增
                                jcChampionRunnerOddsService.insertJcChampionRunnerOdds(jcChampionRunnerOddsRsp,j);
                            }
                        }
                    }else{//新增
                        int j = jcChampionRunnerOddsTypeService.insertJcChampionRunnerOddsType(jcChampionRunnerOddsRsp);
                        if(j > 0){//新增成功==查询冠亚军赔率表
                            JcChampionRunnerOdds jcChampionRunnerOdds = jcChampionRunnerOddsService.queryJcChampionRunnerOddsByTypeAndMatchIdAndTeams(typeRsp,jcChampionRunnerOddsRsp.getMatchID(),jcChampionRunnerOddsRsp.getTeams());
                            if(jcChampionRunnerOdds != null){//更新
                                jcChampionRunnerOddsService.updateJcChampionRunnerOdds(jcChampionRunnerOdds,jcChampionRunnerOddsRsp,j);
                            }else{//新增
                                jcChampionRunnerOddsService.insertJcChampionRunnerOdds(jcChampionRunnerOddsRsp,j);
                            }
                        }
                    }
                }
            }else{
                log.error("冠亚军赔率定时任务返回数据为空");
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error("冠亚军赔率定时任务异常:" + e.getCause());
        }
        log.error("冠亚军赔率定时任务启动,耗时："+(System.currentTimeMillis()-s)+"毫秒");
    }
}
