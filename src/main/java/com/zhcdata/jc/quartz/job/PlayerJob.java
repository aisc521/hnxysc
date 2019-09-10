package com.zhcdata.jc.quartz.job;

import com.zhcdata.jc.tools.HttpUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * 球员资料
 */
@Configuration
@EnableScheduling
public class PlayerJob {

    @Async
    @Scheduled(cron = "21 47 16 ? * *")
    public void work(){
        String url="http://interface.win007.com/zq/Player_XML.aspx?day=1";
        String sds=HttpUtils.getHtmlResult(url);


        String sdssd="";
    }
}
