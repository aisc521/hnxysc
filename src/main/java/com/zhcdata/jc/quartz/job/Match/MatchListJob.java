package com.zhcdata.jc.quartz.job.Match;
import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.tools.BeanUtils;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.MatchListRsp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 4. 赛程赛果 半天一次 每次之间需要间隔60秒
 */
@Slf4j
@Component
public class MatchListJob implements Job{
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    ScheduleMapper scheduleMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("start赛程赛果定时任务启动......");
        long s = System.currentTimeMillis();
        QiuTanXmlComm parse = new QiuTanXmlComm();
        for(int i = 0 ;i<6;i++){ //目前查往后六天的比赛
            String str = DateFormatUtils.format(DateUtils.addDays(new Date(),i),"yyyy-MM-dd");
            String url = "http://interface.win007.com/zq/BF_XML.aspx?date="+str+"";
            List<MatchListRsp> list = parse.handleMothodList(url, MatchListRsp.class);
            log.error("赛程赛果定时任务地址： "+url);
            try {
                executeHandle(list);
                //接口限制60秒
                Thread.sleep(1000*60);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("start赛程赛果定时任务结束....消耗总时间="+(System.currentTimeMillis() - s) );
    }

    public void executeHandle(List<MatchListRsp> list) throws Exception {

        long s = System.currentTimeMillis();
        int insert = 0;
        int hasIn = 0;
        int update = 0;
        for (MatchListRsp model : list) {
            Schedule xml = BeanUtils.parseSchedule(model);
            try {
                Schedule inDb = scheduleMapper.selectByPrimaryKey(Integer.parseInt(model.getA()));
                if (inDb != null && inDb.getScheduleid() != null)
                    if (inDb.matchEquals(xml))
                        hasIn++;
                    else {
                        //更新
                        Schedule toUpdate = new Schedule();
                        toUpdate.setScheduleid(xml.getScheduleid());
                        toUpdate.setMatchstate(xml.getMatchstate());
                        toUpdate.setHomescore(xml.getHomescore());
                        toUpdate.setGuestscore(xml.getGuestscore());
                        toUpdate.setHomehalfscore(xml.getHomehalfscore());
                        toUpdate.setGuesthalfscore(xml.getGuesthalfscore());
                        toUpdate.setMatchtime(xml.getMatchtime());
                        toUpdate.setHomeRed(xml.getHomeRed());
                        toUpdate.setHomeYellow(xml.getHomeYellow());
                        toUpdate.setGuestRed(xml.getGuestRed());
                        toUpdate.setGuestYellow(xml.getGuestYellow());
                        toUpdate.setBfChangetime(xml.getBfChangetime());
                        if (scheduleMapper.updateByPrimaryKeySelective(toUpdate)>0) update++;
                    }
                else {
                    //主客队半场全场角球数
                    xml.setHomecorner(0);
                    xml.setHomecornerhalf(0);
                    xml.setGuestcorner(0);
                    xml.setGuestcornerhalf(0);
                    if (scheduleMapper.insertSelective(xml) > 0)
                        insert++;
                }
            } catch (Exception e) {
                System.err.println("N2JKN3K2N3N4K5"+e.getMessage());
                e.printStackTrace();
            }
        }
        log.info("赛程赛果定时任务结束,共：" + list.size() + ",插入:" + insert + "条,跳过:" + hasIn + ",更新"+update+"，耗时：" + (System.currentTimeMillis() - s) + "毫秒");
    }

    public String dateReq(){
        Date date = DateUtils.addDays(new Date(),1);
        return null;
    }
}