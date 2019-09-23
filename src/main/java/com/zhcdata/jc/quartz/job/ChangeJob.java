package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.tools.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@Configuration
@EnableScheduling
public class ChangeJob {
    @Resource
    ScheduleMapper scheduleMapper;

    @Async
    @Scheduled(cron = "21 33 14 * * *")
    public void work() {
        String url = "http://interface.win007.com/zq/change.xml";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //比赛ID0^比赛状态1^主队比分2^客队比分3^主队上半场比分4^
        // 客队上半场比分5^主队红牌6^客队红牌7^比赛时间8^开场时间9^
        // 赛程说明10^是否有阵容11^主队黄牌12^客队黄牌13^比赛日期14^
        // 比赛说明215^主队角球16^客队角球17^无效字段，可忽略18
        try {
            String xml = HttpUtils.getHtmlResult(url);
            SAXReader saxReader = new SAXReader();
            org.dom4j.Document docDom4j = saxReader.read(new ByteArrayInputStream(xml.getBytes("utf-8")));
            org.dom4j.Element root = docDom4j.getRootElement();
            List<Element> childElements = root.elements();
            for (org.dom4j.Element one : childElements) {
                String[] strings = one.getText().split("\\^");
                Schedule info = new Schedule();

                info.setScheduleid(Integer.valueOf(strings[0]));    //比赛ID0
                if (strings.length > 0 && strings[1].length() > 0) {
                    info.setMatchstate(Short.valueOf(strings[1]));      //比赛状态1
                }
                if (strings.length > 1 && strings[1].length() > 0) {
                    info.setHomescore(Short.valueOf(strings[2]));       //主队比分2
                }
                if (strings.length > 2 && strings[1].length() > 0) {
                    info.setGuestscore(Short.valueOf(strings[3]));      //客队比分3
                }
                if (strings.length > 3 && strings[1].length() > 0) {
                    info.setHomehalfscore(Short.valueOf(strings[4]));   //主队上半场比分4
                }
                if (strings.length > 4 && strings[1].length() > 0) {
                    info.setGuesthalfscore(Short.valueOf(strings[5]));  //客队上半场比分5
                }
                if (strings.length > 5 && strings[1].length() > 0) {
                    info.setHomeRed(Short.valueOf(strings[6]));         //主队红牌6
                }
                if (strings.length > 6 && strings[1].length() > 0) {
                    info.setGuestRed(Short.valueOf(strings[7]));        //客队红牌7
                }
                if (strings.length > 8 && strings[1].length() > 0) {
                    info.setMatchtime2(df.parse(strings[9]));           //开场时间9
                }
                if (strings.length > 10 && strings[1].length() > 0) {
                    info.setVisitor(Integer.valueOf(strings[11]));      //是否有阵容11
                }
                if (strings.length > 11 && strings[1].length() > 0) {
                    info.setHomeYellow(Short.valueOf(strings[12]));     //主队黄牌12
                }
                if (strings.length > 12 && strings[1].length() > 0) {
                    info.setGuestYellow(Short.valueOf(strings[13]));    //客队黄牌13
                }
                if (strings.length > 15 && strings[1].length() > 0) {
                    info.setHomecorner(Integer.valueOf(strings[16]));   //主队角球16
                }
                if (strings.length > 16 && strings[1].length() > 0) {
                    info.setGuestcorner(Integer.valueOf(strings[17]));  //客队角球17
                }

                if (scheduleMapper.updateByPrimaryKeySelective(info) > 0) {
                    log.error("[即时变化的比分数据（20秒）]：更新成功");
                } else {
                    log.error("[即时变化的比分数据（20秒）]：更新失败");
                }
            }
        } catch (Exception ex) {
            log.error("[即时变化的比分数据（20秒）]" + ex.getMessage());
        }
    }
}