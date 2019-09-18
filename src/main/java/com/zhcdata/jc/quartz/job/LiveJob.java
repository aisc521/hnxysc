package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.mapper.TbLiveMapper;
import com.zhcdata.db.model.LiveInfo;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.LiveRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Configuration
@EnableScheduling
public class LiveJob {

    @Resource
    TbLiveMapper tbLiveMapper;

    @Resource
    ScoreJob scoreJob;

    @Resource
    ScheduleMapper scheduleMapper;

    @Async
    @Scheduled(cron = "1 41 20 ? * *")
    public void work() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String url = "http://interface.win007.com/zq/TextLive.aspx";
        try {
            String s = scoreJob.getSE().split(",")[0];
            String e = scoreJob.getSE().split(",")[1];
            List<Schedule> schedules = scheduleMapper.selectPastAndFutureNoEnd(s, e, "-1");
            for (int i = 0; i < schedules.size(); i++) {
                System.out.println(i);
                List<LiveRsp> result_list = new QiuTanXmlComm().handleMothodList(url + "?id=" + "1552518", LiveRsp.class);
                for (LiveRsp a : result_list) {
                    List<LiveInfo> list = tbLiveMapper.queryLive(a.getID());
                    if (list == null || list.size() == 0) {
                        LiveInfo info = new LiveInfo();
                        info.setId(Long.valueOf(a.getID()));
                        info.setContent(a.getContent());
                        info.setTime(a.getTime());
                        info.setCreateTime(df.format(new Date()));
                        info.setScheduleid(Long.valueOf(schedules.get(i).getScheduleid()));
                        if (tbLiveMapper.insertSelective(info) > 0) {
                            log.info("直播数据保存成功");
                        } else {
                            log.info("直播数据保存失败");
                        }
                    }
                }
            }
        } catch (Exception ex) {
            log.error("直播接口处理异常：" + ex.getMessage());
        }
    }
}
