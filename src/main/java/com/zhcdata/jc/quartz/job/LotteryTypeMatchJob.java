package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.model.JcLotterTypeZc;
import com.zhcdata.jc.service.LotteryTypeMatchJobService;
import com.zhcdata.jc.tools.Const;
import com.zhcdata.jc.tools.HttpUtils;
import com.zhcdata.jc.tools.xml.XmlUtils;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.LotteryTypeMatchRsp;
import com.zhcdata.jc.xml.rsp.ToDayMatchRsp;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
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

    @Scheduled(cron = "0/2 * * * * ?")
    public void execute(){
        log.error("彩票赛程与球探网ID关联表定时任务启动");
        long s = System.currentTimeMillis();
        try {
            List<LotteryTypeMatchRsp> list  = new QiuTanXmlComm().handleMothod(requestUrl,List.class,LotteryTypeMatchRsp.class);
            System.out.println(list);
        } catch (Exception e) {
            log.error("彩票赛程与球探网ID关联表定时任务启动异常",e);
            e.printStackTrace();
        }
        log.error("彩票赛程与球探网ID关联表定时任务启动,耗时："+(System.currentTimeMillis()-s)+"毫秒");
    }


}
