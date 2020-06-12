package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.JcScheduleMapper;
import com.zhcdata.db.mapper.LetGoalDetailMapper;
import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.service.GetMatchInfoByIdListService;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.MatchListRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class GetMatchInfoByIdListServiceImpl implements GetMatchInfoByIdListService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    ScheduleMapper scheduleMapper;

    @Resource
    JcScheduleMapper jcScheduleMapper;

    @Resource
    LetGoalDetailMapper letGoalDetailMapper;

    @Override
    public void dealMatch(String startDate,String endDate){
        LOGGER.info("id查询比赛信息定时任务启动");
        try {
            long s = System.currentTimeMillis();
            int update = 0;
            QiuTanXmlComm parse = new QiuTanXmlComm();

            List<Schedule> models = scheduleMapper.selectStatusChangedToday(startDate + " 11:00:00", endDate + " 11:00:00",sdf.format(new Date()));
            //List<Schedule> models = scheduleMapper.selectStatusChangedToday("2019-10-21 00:00:00", "2019-10-21 23:59:59",sdf.format(new Date()));

            StringBuilder sb = new StringBuilder();
            int p=1;
            for (Schedule model : models) {
                sb.append(model.getScheduleid()).append(",");
                if(p%250==0){
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
                        model.setScheduleid(Integer.valueOf(rsp.getA()));                                        //赛事ID
                        model.setMatchstate((short) rsp.getF());                                                 //状态
                        model.setMatchtime(sdf.parse(rsp.getD().replaceAll("/", "-")));       //时间
                        model.setHomescore((short)rsp.getJ());
                        model.setGuestscore((short)rsp.getK());
                        if(!rsp.getL().equals("")) {
                            model.setHomehalfscore(Short.valueOf(rsp.getL()));
                        }
                        if(!rsp.getM().equals("")) {
                            model.setGuesthalfscore(Short.valueOf(rsp.getM()));
                        }
                        model.setHomeRed(Short.valueOf(rsp.getN()));
                        model.setGuestRed(Short.valueOf(rsp.getO()));

                        if(rsp.getYellow().contains("-")) {
                            model.setHomeYellow(Short.valueOf(rsp.getYellow().split("-")[0]));
                            model.setGuestYellow(Short.valueOf(rsp.getYellow().split("-")[1]));
                        }
                        model.setBfshow(rsp.getHidden().equals("True"));
                        if (scheduleMapper.updateByPrimaryKeySelective(model) > 0) {
                            LOGGER.info("更新成功" + model.getScheduleid());
                        }else {
                            LOGGER.info("更新失败(不需要)" + model.getScheduleid());
                        }

                        JcSchedule jcSchedule=new JcSchedule();
                        jcSchedule.setScheduleid(Integer.valueOf(rsp.getA()));
                        jcSchedule.setIsturned(Boolean.valueOf(rsp.getZ()));
                        if(jcScheduleMapper.updateByPrimaryKeySelective(jcSchedule)>0){
                            LOGGER.info("更新成功(竞彩表)" + model.getScheduleid());
                        }else {
                            LOGGER.info("更新失败(竞彩表,不需要)" + model.getScheduleid());
                        }

                        //如果状态是取消 推迟 则删除赔率 盘口
                        try {
                            if ("-10".equals(rsp.getF()) || "-14".equals(rsp.getF())) {
                                //盘口删除
                                int re = letGoalDetailMapper.deleteletgoaldetailByOddsid(rsp.getA());
                                if (re > 0) {
                                    LOGGER.info("删除赔率盘口大小球成功(比赛推迟或取消)" + rsp.getA());
                                } else {
                                    LOGGER.info("删除赔率盘口大小球失败(比赛推迟或取消)" + rsp.getA());
                                }
                            }
                        }catch (Exception ex){
                            LOGGER.error("删除赔率盘口大小球异常");
                            ex.printStackTrace();
                        }


                        update++;
                    }
                }
                LOGGER.info("即时变化的比分数据(5分钟),共有比赛" + models.size() + ",更新:" + update + "条,耗时：" + (System.currentTimeMillis() - s) + "毫秒");
                Thread.sleep(60000);
            }
        }catch (Exception ex){
            LOGGER.error("[按赛程ID查比赛的数据]异常",ex);
        }
    }
}
