package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.JcScheduleMapper;
import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.tools.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springside.modules.utils.time.DateFormatUtil;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Configuration
@EnableScheduling
public class Change2Job implements Job {
    @Resource
    ScheduleMapper scheduleMapper;

    @Resource
    JcScheduleMapper jcScheduleMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String url = "http://interface.win007.com/zq/change2.xml";
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
                JcSchedule jcInfo=new JcSchedule();  //竞彩赛程

                info.setScheduleid(Integer.valueOf(strings[0]));    //比赛ID0
                if (strings.length > 0 && strings[1].length() > 0) {
                    info.setMatchstate(Short.valueOf(strings[1]));      //比赛状态1
                    jcInfo.setMatchstate(Short.valueOf(strings[1]));      //比赛状态1
                }
                if (strings.length > 1 && strings[2].length() > 0) {
                    info.setHomescore(Short.valueOf(strings[2]));       //主队比分2
                    jcInfo.setHomescore(Short.valueOf(strings[2]));       //主队比分2
                }
                if (strings.length > 2 && strings[3].length() > 0) {
                    info.setGuestscore(Short.valueOf(strings[3]));      //客队比分3
                    jcInfo.setGuestscore(Short.valueOf(strings[3]));      //客队比分3
                }
                if (strings.length > 3 && strings[4].length() > 0) {
                    info.setHomehalfscore(Short.valueOf(strings[4]));   //主队上半场比分4
                    jcInfo.setHomehalfscore(Short.valueOf(strings[4]));   //主队上半场比分4
                }
                if (strings.length > 4 && strings[5].length() > 0) {
                    info.setGuesthalfscore(Short.valueOf(strings[5]));  //客队上半场比分5
                    jcInfo.setGuesthalfscore(Short.valueOf(strings[5]));  //客队上半场比分5
                }
                if (strings.length > 5 && strings[6].length() > 0) {
                    info.setHomeRed(Short.valueOf(strings[6]));         //主队红牌6
                }
                if (strings.length > 6 && strings[7].length() > 0) {
                    info.setGuestRed(Short.valueOf(strings[7]));        //客队红牌7
                }
                String string = strings[9];
                if (strings.length > 8 && string.length() > 0) {
                    if (!"0,0,0,0,0,0".equals(string)) {
                        Date date = DateFormatUtil.pareDate("yyyy,MM,dd,HH,mm,ss", string);
                        Calendar instance = Calendar.getInstance();
                        instance.setTime(date);
                        instance.add(Calendar.MONTH, 1);
                        string = DateFormatUtil.formatDate("yyyy,M,d,HH,mm,ss", instance.getTime());
                    }
                    info.setMatchtime2(string);           //开场时间9
                    jcInfo.setMatchtime2(string);                     //开场时间9
                }
                if (strings.length > 10 && strings[11].length() > 0) {
                    info.setVisitor(Integer.valueOf(strings[11]));      //是否有阵容11
                }
                if (strings.length > 11 && strings[12].length() > 0) {
                    info.setHomeYellow(Short.valueOf(strings[12]));     //主队黄牌12
                }
                if (strings.length > 12 && strings[13].length() > 0) {
                    info.setGuestYellow(Short.valueOf(strings[13]));    //客队黄牌13
                }
                if (strings.length > 15 && strings[16].length() > 0) {
                    info.setHomecorner(Integer.valueOf(strings[16]));   //主队角球16
                }
                if (strings.length > 16 && strings[17].length() > 0) {
                    info.setGuestcorner(Integer.valueOf(strings[17]));  //客队角球17
                }

                if(jcScheduleMapper.updateByPrimaryKeySelective(jcInfo)>0){
                    log.info("[即时变化的比分数据（150秒）]：(竞彩)更新成功");
                }else {
                    log.info("[即时变化的比分数据（150秒）]：(竞彩)不需要更新");
                }

                if (scheduleMapper.updateByPrimaryKeySelective(info) > 0) {
                    log.error("[即时变化的比分数据（150秒）]：更新成功");
                } else {
                    log.error("[即时变化的比分数据（150秒）]：不需要更新");
                }
            }
        } catch (Exception ex) {
            log.error("[即时变化的比分数据（120秒）]" + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
