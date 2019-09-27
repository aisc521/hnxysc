package com.zhcdata.jc.quartz.job.Match;

import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.tools.BeanUtils;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.MatchDelOrEditRsp;
import com.zhcdata.jc.xml.rsp.MatchListRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 获取24小时内的赛程删除，比赛时间修改记录
 */
@Configuration
@EnableScheduling
public class MatchListDelOrEditJob {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static List<String> cache = new ArrayList<>();


    @Resource
    ScheduleMapper scheduleMapper;

    //@Scheduled(cron = "1 0/2 * * * ?")
    public void execute() throws Exception {
        LOGGER.info("比赛删除&修改时间记录定时任务启动");
        long s = System.currentTimeMillis();
        int update = 0;
        int delete = 0;
        int repeat = 0;
        List<MatchDelOrEditRsp> list = new QiuTanXmlComm().handleMothodList("http://interface.win007.com/zq/ModifyRecord.aspx", MatchDelOrEditRsp.class);
        for (int i = 0; i < list.size(); i++) {
            if (!cache.contains(list.get(i).getID())) {//如果缓存中没有这个id
                if (list.get(i).getType().equals("modify")) {//修改
                    Schedule schedule = new Schedule();
                    schedule.setScheduleid(Integer.parseInt(list.get(i).getID()));
                    schedule.setMatchtime(sdf.parse(BeanUtils.parseToFormat(list.get(i).getMatchtime())));
                    int i1 = scheduleMapper.updateByPrimaryKeySelective(schedule);
                    if (i1 > 0) update++;else repeat++;
                } else if (list.get(i).getType().equals("delete")) {//删除
                    int i1 = scheduleMapper.deleteByPrimaryKey(Integer.parseInt(list.get(i).getID()));
                    if (i1 > 0) delete++;else repeat++;
                }
                cache.add(list.get(i).getID());
                if (cache.size() >= 500) {
                    System.out.println("移除");
                    cache.remove(cache.get(0));
                }
            }
        }
        LOGGER.info("比赛删除&修改时间记录定时任务结束,共：" + list.size() + ",更新:" + update + "条,删除:" + delete + ",重复:"+repeat+",list缓存:"+cache.size()+"耗时：" + (System.currentTimeMillis() - s) + "毫秒");
    }
}