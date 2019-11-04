/*create by xuan on 2019/9/10

 */
package com.zhcdata.jc.quartz.job.Match;

import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.MatchListRsp;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//接口4
@SuppressWarnings("SpringJavaAutowiringInspection")
//@Configuration
//@EnableScheduling
@Component
@Slf4j
public class GetMatchInfoByIdListJob implements Job {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");


    @Resource
    ScheduleMapper scheduleMapper;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOGGER.info("id查询比赛信息定时任务启动");
        try {
            long s = System.currentTimeMillis();
            int update = 0;
            QiuTanXmlComm parse = new QiuTanXmlComm();
            String today = day.format(new Date());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            String startDate=sdf.format(calendar.getTime()).substring(0, 10);
            List<Schedule> models = scheduleMapper.selectStatusChangedToday(startDate + " 00:00:00", today + " 23:59:59",sdf.format(new Date()));
            //List<Schedule> models = scheduleMapper.selectStatusChangedToday("2019-10-21 00:00:00", "2019-10-21 23:59:59",sdf.format(new Date()));
            StringBuilder sb = new StringBuilder();
            int p=1;
            for (Schedule model : models) {
                sb.append(model.getScheduleid()).append(",");
                if(p%200==0){
                    //每组200
                    sb.append("|");
                }
                p++;
            }
            String[] ids= sb.toString().split("\\|");
            for(int m=0;m<ids.length;m++) {
                List<MatchListRsp> xml = parse.handleMothodList("http://interface.win007.com/zq/BF_XMLByID.aspx?id=" + ids[m].substring(0,ids[m].length()-1), MatchListRsp.class);
                if (xml != null) {
                    for (MatchListRsp rsp : xml) {
                        Schedule model = new Schedule();
                        model.setScheduleid(Integer.valueOf(rsp.getA()));                                       //赛事ID
                        model.setMatchstate((short) rsp.getF());                                                 //状态
                        model.setMatchtime(sdf.parse(rsp.getD().replaceAll("/", "-")));       //时间
                        model.setBfshow(rsp.getHidden().equals("True"));
                        if (scheduleMapper.updateByPrimaryKeySelective(model) > 0) {
                            LOGGER.error("更新成功" + model.getScheduleid());
                        }
                        update++;
                    }
                }
                LOGGER.info("即时变化的比分数据(5分钟),共有比赛" + models.size() + ",更新:" + update + "条,耗时：" + (System.currentTimeMillis() - s) + "毫秒");
            }
        }catch (Exception ex){
            LOGGER.error("[按赛程ID查比赛的数据]异常",ex);
        }
    }
}

//public void execute() throws Exception {
//    LOGGER.info("id查询比赛信息定时任务启动");
//    long s = System.currentTimeMillis();
//    int update = 0;//int hasIn = 0;
//    QiuTanXmlComm parse = new QiuTanXmlComm();
//    //Date past = new Date(System.currentTimeMillis()-(8*60*60*1000));//8小时前
//    //Date future = new Date(System.currentTimeMillis()+(72*60*60*1000));//72小时后
//    //List<Schedule> models = scheduleMapper.selectPastAndFutureNoEnd(sdf.format(past),sdf.format(future),"-1");
//    String today = day.format(new Date());
//    List<Schedule> models = scheduleMapper.selectStatusChangedToday(today+" 00:00:00",today+" 23:59:59");
//
//
//    //Map<String, Schedule> moMap = new HashMap<>();
//    StringBuilder sb = new StringBuilder().append("?id=");
//    for (Schedule model : models) {
//        //moMap.put(String.valueOf(model.getScheduleid()), model);
//        sb.append(model.getScheduleid()).append(",");
//    }
//    sb.deleteCharAt(sb.length() - 1);
//    //System.out.println(sb);
//    List<MatchListRsp> xml = parse.handleMothodList("http://interface.win007.com/zq/BF_XMLByID.aspx"+sb, MatchListRsp.class);
//    //for (int i = 0; i < xml.size(); i++) {
//    //    Schedule wb = BeanUtils.parseSchedule(xml.get(i));
//    //    Schedule db = moMap.get(String.valueOf(wb.getScheduleid()));
//    //    if (!BeanUtils.ScheduleNoChange(wb,db)){
//    //        //监测到变化，更新
//    //        if (scheduleMapper.updateByPrimaryKeySelective(wb)>0){
//    //            update++;
//    //        }
//    //    } else hasIn++;
//    //}
//    if (xml!=null){
//        for (Schedule model : models) {
//            if (scheduleMapper.updateByPrimaryKeySelective(model) > 0)
//                update++;
//        }
//    }
//
//    //System.out.println(models.size());
//    LOGGER.info("即时变化的比分数据(5分钟),共有比赛"+ models.size() +",更新:" + update + "条,耗时：" + (System.currentTimeMillis() - s) + "毫秒");
//}