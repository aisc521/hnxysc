package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.TbJcMatchLineupMapper;
import com.zhcdata.db.model.JcMatchLineupInfo;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.LineupRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class LineupJob {
    @Resource
    TbJcMatchLineupMapper tbJcMatchLineupMapper;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Async
    @Scheduled(cron = "31 48 13 ? * *")
    public void work() {
        String url = "http://interface.win007.com/zq/lineup.aspx?cmd=new";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try{
            List<LineupRsp> result_list = new QiuTanXmlComm().handleMothodList(url, LineupRsp.class);
            for(int i=0;i<result_list.size();i++) {
                List<JcMatchLineupInfo> list = tbJcMatchLineupMapper.queryLineup(result_list.get(i).getID());
                if (list != null && list.size() > 0) {
                    //暂不确定，是否有变化，如果有，则修改
                    LOGGER.info("阵容信息已存在，暂时不保存");
                } else {
                    JcMatchLineupInfo info = new JcMatchLineupInfo();
                    info.setId(Long.parseLong(result_list.get(i).getID()));
                    info.setHomeArray(result_list.get(i).getHomeArray());
                    info.setAwayArray(result_list.get(i).getAwayArray());
                    info.setHomeLineup(result_list.get(i).getHomeLineup());
                    info.setAwayLineup(result_list.get(i).getAwayLineup());
                    info.setHomeBackup(result_list.get(i).getHomeBackup());
                    info.setAwayBackup(result_list.get(i).getAwayBackup());
                    info.setCreateTime(df.format(new Date()));
                    if (tbJcMatchLineupMapper.insertSelective(info) > 0) {
                        LOGGER.info("阵容信息保存成功");
                    } else {
                        LOGGER.info("阵容信息保存失败");
                    }
                }
            }

            String sds="";
        }catch (Exception ex) {
            LOGGER.info("阵容定时任务异常：" + ex);
        }
    }
}
