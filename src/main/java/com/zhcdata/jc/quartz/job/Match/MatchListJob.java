/**
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　　　 ████━████ ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　create by xuan on 2019/9/10
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 */
package com.zhcdata.jc.quartz.job.Match;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.javafx.collections.MappingChange;
import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.tools.BeanUtils;
import com.zhcdata.jc.tools.HttpUtils;
import com.zhcdata.jc.tools.xml.XmlUtils;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.MatchListRsp;
import com.zhcdata.jc.xml_model.MatchList;
import com.zhcdata.jc.xml_model.MatchListModel;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

//接口4
@Configuration
@EnableScheduling
public class MatchListJob{

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Resource
    ScheduleMapper scheduleMapper;

    @Scheduled(cron = "1 * * * * ?")
    public void execute() throws Exception {
        LOGGER.info("赛程赛果定时任务启动");
        long s = System.currentTimeMillis();
        int insert = 0;
        int hasIn = 0;
        QiuTanXmlComm parse = new QiuTanXmlComm();
        List<MatchListRsp> models = parse.handleMothodList("http://interface.win007.com/zq/BF_XML.aspx?date=2019-09-10", MatchListRsp.class);
        for (MatchListRsp model : models) {
            try {
                Schedule inDb = scheduleMapper.selectByPrimaryKey(Integer.parseInt(model.getA()));
                if (inDb != null && inDb.getScheduleid() != null)
                    hasIn++;
                else {
                    inDb = BeanUtils.parseSchedule(model);
                    //主客队半场全场角球数
                    inDb.setHomecorner(0);
                    inDb.setHomecornerhalf(0);
                    inDb.setGuestcorner(0);
                    inDb.setGuestcornerhalf(0);
                    if (scheduleMapper.insertSelective(inDb) > 0)
                        insert++;
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
        LOGGER.info("赛程赛果定时任务结束,共：" + models.size() + ",插入:" + insert + "条,跳过:" + hasIn + ",耗时：" + (System.currentTimeMillis() - s) + "毫秒");
    }


    public static String parseToFormat(String str) {
        //e:2019/9/9 0:0:0
        String temp = "0";
        String[] date = str.split(" ")[0].split("/");
        String[] time = str.split(" ")[1].split(":");

        String y = date[0];
        if (y.length() == 1) y = temp + y;
        String m = date[1];
        if (m.length() == 1) m = temp + m;
        String d = date[2];
        if (d.length() == 1) d = temp + d;

        String h = time[0];
        if (h.length() == 1) h = temp + h;
        String f = time[1];
        if (f.length() == 1) f = temp + f;
        String s = time[2];
        if (s.length() == 1) s = temp + s;

        return y + "-" + m + "-" + d + " " + h + ":" + f + ":" + s;
    }

    public static void main(String[] args) {
        //String s = parseToFormat("2019/9/9 1:2:3");
        //String d = parseToFormat("2019/9/9 01:2:3");
        //String f = parseToFormat("2019/9/9 1:20:3");
        //String g = parseToFormat("2019/9/9 1:2:03");
        //System.out.println(s);
        //System.out.println(d);
        //System.out.println(f);
        //System.out.println(g);

        short str = Short.parseShort("九与");
        //short str = Short.parseShort("九月");
        //short str = Short.valueOf("九");
        System.out.println(str);

    }
}