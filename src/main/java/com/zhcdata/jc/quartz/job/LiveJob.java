package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.TbLiveMapper;
import com.zhcdata.db.model.LiveInfo;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.LiveDetailRsp;
import com.zhcdata.jc.xml.rsp.LiveRsp;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Configuration
@EnableScheduling
public class LiveJob implements Job {

    @Resource
    TbLiveMapper tbLiveMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
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
                            log.info("[文字直播]保存成功");
                        } else {
                            log.info("[文字直播]保存失败");
                        }
                    }
                }
            }
        } catch (Exception ex) {
            log.error("文字直播接口处理异常：" + ex.getMessage());
        }
    }
}
