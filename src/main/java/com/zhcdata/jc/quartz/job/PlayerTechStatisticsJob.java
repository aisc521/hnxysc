package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.mapper.TbScoreMapper;
import com.zhcdata.db.mapper.TbSubSclassMapper;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.LineupMatchRsp;
import com.zhcdata.jc.xml.rsp.PTSRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

@Configuration
@EnableScheduling
public class PlayerTechStatisticsJob {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    ScheduleMapper scheduleMapper;

    @Resource
    TbSubSclassMapper tbSubSclassMapper;

    @Resource
    TbScoreMapper tbScoreMapper;

    @Resource
    ScoreJob scoreJob;

    @Async
    @Scheduled(cron = "41 32 9 ? * *")
    public void work() {
        String url = "http://interface.win007.com/zq/PlayDetail.aspx";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String s = scoreJob.getSE().split(",")[0];
        String e = scoreJob.getSE().split(",")[1];

        try {
            List<LineupMatchRsp> match_list = new QiuTanXmlComm().handleMothodList(url, LineupMatchRsp.class);
            if (match_list != null && match_list.size() > 0) {
                for (int i = 0; i < match_list.size(); i++) {
                    url = url + "?ID=" + match_list.get(i).getScheduleID();
                    List<PTSRsp> result_list = new QiuTanXmlComm().handleMothodList(url, PTSRsp.class);

                    if(result_list!=null&&result_list.size()>0){
                        String sdssd="";
                    }else {
                        System.out.println("第" + i + 1 + "个无信息");
                    }
                    String sdfd = "";
                }
            }
        } catch (Exception ex) {
            String sdf = "";
        }
    }
}
