package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.TbPlayerMapper;
import com.zhcdata.db.model.PlayerInfo;
import com.zhcdata.jc.tools.HttpUtils;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.PlayerRsp;
import com.zhcdata.jc.xml.rsp.ToDayMatchRsp;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

/**
 * 球员资料
 */
@Configuration
@EnableScheduling
public class PlayerJob {

    @Resource
    TbPlayerMapper tbPlayerMapper;

    @Async
    @Scheduled(cron = "21 10 19 ? * *")
    public void work() {
        String url = "http://interface.win007.com/zq/Player_XML.aspx?day=1";

        List<PlayerRsp> result_list  = new QiuTanXmlComm().handleMothod(url,PlayerRsp.class);
        for (PlayerRsp a : result_list) {
            List<PlayerInfo> list = tbPlayerMapper.queryPlayer(a.getPlayerID());
            //if(list!=null&&)
        }


        try {

        } catch (Exception ex) {
            System.out.println("异常信息：" + ex.getMessage());
        }

    }
}
