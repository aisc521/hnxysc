package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.TbLiveMapper;
import com.zhcdata.db.model.LiveInfo;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.LiveDetailRsp;
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

    @Async
    @Scheduled(cron = "31 39 9 ? * *")
    public void work() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String url = "http://interface.win007.com/zq/TextLive.aspx";
        try {
            List<LiveRsp> result_list = new QiuTanXmlComm().handleMothodList(url, LiveRsp.class);
            for (LiveRsp a : result_list) {
                List<LiveDetailRsp> result_Detail = new QiuTanXmlComm().handleMothodList(url + "?id=" + a.getID(), LiveDetailRsp.class);
                for (LiveDetailRsp b : result_Detail) {
                    List<LiveInfo> list = tbLiveMapper.queryLive(a.getID());
                    if (list == null || list.size() == 0) {
                        LiveInfo info = new LiveInfo();
                        info.setId(Long.valueOf(b.getID()));
                        info.setContent(b.getContent());
                        info.setTime(b.getTime());
                        info.setCreateTime(df.format(new Date()));
                        info.setScheduleid(Long.valueOf(a.getID()));
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
