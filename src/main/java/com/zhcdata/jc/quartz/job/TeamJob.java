package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.TbTeamMapper;
import com.zhcdata.db.model.TeamInfo;
import com.zhcdata.jc.tools.HttpUtils;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.PlayerRsp;
import com.zhcdata.jc.xml.rsp.TeamRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 球队资料
 */
@Configuration
@EnableScheduling
public class TeamJob {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    TbTeamMapper tbTeamMapper;

    @Async
    /*@Scheduled(cron = "51 24 16 ? * *")*/
    public void work() {
        String url = "http://interface.win007.com/zq/Team_XML.aspx";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



        try {
            List<TeamRsp> list = new QiuTanXmlComm().handleMothodList(url, TeamRsp.class);

            //String re = HttpUtils.getHtmlResult(url);

            String sdsd="";
        } catch (Exception ex) {
            String sds = "";
        }

        List<TeamInfo> list = tbTeamMapper.queryTeam("7");

        String sd = "";
    }
}
