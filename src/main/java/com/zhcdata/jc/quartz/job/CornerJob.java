package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.CornerRsp;
import com.zhcdata.jc.xml.rsp.LiveRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@Configuration
@EnableScheduling
public class CornerJob {

    @Resource
    ScheduleMapper scheduleMapper;

    @Async
    @Scheduled(cron = "51 8 11 * * *")
    public void work() {
        String url = "http://interface.win007.com/zq/Corner.aspx?type=new";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<CornerRsp> result_list = new QiuTanXmlComm().handleMothodList(url, CornerRsp.class);
            for (CornerRsp a : result_list) {
                Schedule schedule = scheduleMapper.selectByPrimaryKey(Integer.valueOf(a.getId()));
                if (schedule != null) {
                    String[] strings = a.getCorner().split(",");
                    if (strings.length == 4) {
                        //此判断,避免数组越界
                        if (scheduleMapper.updateByScheduleID(strings[0], strings[1], strings[2], strings[3], a.getId()) > 0) {
                            log.info("[角球事件接口]：角球数更新成功" + a.getId());
                        } else {
                            log.info("[角球事件接口]：角球数更新失败" + a.getId());
                        }
                    } else {
                        log.info("[角球事件接口]：数据数据不够、不进行处理");
                    }
                } else {
                    log.info("[角球事件接口]：查询赛程表赛事信息不存在" + a.getId());
                }
            }
            String asdf = "";
        } catch (Exception ex) {
            log.error("[角球事件接口异常]" + ex.getMessage());
        }
    }
}
