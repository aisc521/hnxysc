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

import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.tools.BeanUtils;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.MatchListRsp;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

//接口4
@Configuration
@EnableScheduling
public class GetMatchInfoByIdListJob {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Resource
    ScheduleMapper scheduleMapper;

    //@Scheduled(cron = "1 0/5 * * * ?")
    public void execute() throws Exception {
        LOGGER.info("id查询比赛信息定时任务启动");
        long s = System.currentTimeMillis();
        int update = 0;int hasIn = 0;
        QiuTanXmlComm parse = new QiuTanXmlComm();
        Date past = new Date(System.currentTimeMillis()-(8*60*60*1000));//8小时前
        Date future = new Date(System.currentTimeMillis()+(72*60*60*1000));//72小时后
        List<Schedule> models = scheduleMapper.selectPastAndFutureNoEnd(sdf.format(past),sdf.format(future),"-1");
        Map<String, Schedule> moMap = new HashMap<>();
        StringBuilder sb = new StringBuilder().append("?id=");
        for (Schedule model : models) {
            moMap.put(String.valueOf(model.getScheduleid()), model);
            sb.append(model.getScheduleid()).append(",");
        }
        sb = sb.deleteCharAt(sb.length()-1);
        System.out.println(sb);
        List<MatchListRsp> xml = parse.handleMothodList("http://interface.win007.com/zq/BF_XMLByID.aspx"+sb, MatchListRsp.class);
        for (int i = 0; i < xml.size(); i++) {
            Schedule wb = BeanUtils.parseSchedule(xml.get(i));
            Schedule db = moMap.get(String.valueOf(wb.getScheduleid()));
            if (!BeanUtils.ScheduleNoChange(wb,db)){
                //监测到变化，更新
                if (scheduleMapper.updateByPrimaryKeySelective(wb)>0){
                    update++;
                }
            } else hasIn++;
        }

        System.out.println(models.size());
        LOGGER.info("id查询比赛信息定时任务结束,共："+models.size()+",更新:" + update + "条,跳过:"+hasIn+",耗时：" + (System.currentTimeMillis() - s) + "毫秒");
    }
}