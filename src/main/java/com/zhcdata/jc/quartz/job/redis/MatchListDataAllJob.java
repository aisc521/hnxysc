package com.zhcdata.jc.quartz.job.redis;

import com.zhcdata.jc.dto.MatchResult1;
import com.zhcdata.jc.service.ScheduleService;
import com.zhcdata.jc.tools.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springside.modules.utils.mapper.JsonMapper;
import org.springside.modules.utils.time.ClockUtil;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/19 19:23
 */
@Configuration
@EnableScheduling
@Slf4j
@Component
public class MatchListDataAllJob  implements Job {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private ScheduleService scheduleService;

    @Resource
    private RedisUtils redisUtils;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String startDate = "";
        String endDate = "";
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, -25);
            startDate=df.format(calendar.getTime()).substring(0, 10) + " 11:00:00";

            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(new Date());
            calendar1.add(Calendar.DAY_OF_MONTH, -24);
            endDate=df.format(calendar1.getTime()).substring(0, 10) + " 11:00:00";

            String time="";
            for(int i=0;i<24;i++) {
                long start = ClockUtil.currentTimeMillis();
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                startDate = df.format(calendar.getTime()).substring(0, 10) + " 11:00:00";

                calendar1.add(Calendar.DAY_OF_MONTH, 1);
                endDate = df.format(calendar1.getTime()).substring(0, 10) + " 11:00:00";

                time = startDate.substring(0, 10);

                List<MatchResult1> list1=new ArrayList<>();
                List<MatchResult1> list1_1 = scheduleService.queryMacthListForJob(startDate, endDate, "1", "","1"); //竞彩 正在进行
                for(int a=0;a<list1_1.size();a++){
                    list1.add(list1_1.get(a));
                }
                List<MatchResult1> list1_2 = scheduleService.queryMacthListForJob(startDate, endDate, "1","","2"); //竞彩 未开始
                for(int b=0;b<list1_2.size();b++){
                    list1.add(list1_2.get(b));
                }
                List<MatchResult1> list1_3 = scheduleService.queryMacthListForJob(startDate, endDate, "1","","3"); //竞彩 已经结束
                for(int c=0;c<list1_3.size();c++) {
                    list1.add(list1_3.get(c));
                }
                deal(list1, time, "1");

                List<MatchResult1> list5 = new ArrayList<>();
                List<MatchResult1> list5_1 = scheduleService.queryMacthListForJob(startDate, endDate, "4","","1");//全部 正在进行
                for(int a=0;a<list5_1.size();a++){
                    list5.add(list5_1.get(a));
                }
                List<MatchResult1> list5_2 = scheduleService.queryMacthListForJob(startDate, endDate, "4","","2");//全部 未开始
                for(int b=0;b<list5_2.size();b++){
                    list5.add(list5_2.get(b));
                }
                List<MatchResult1> list5_3 = scheduleService.queryMacthListForJob(startDate, endDate, "4","","3");//全部 已经结束
                for(int c=0;c<list5_3.size();c++){
                    list5.add(list5_3.get(c));
                }

                deal(list5, time, "5");

                long end = ClockUtil.currentTimeMillis();
                System.out.println(time);
                LOGGER.info("更新" + time + "赛事列表成功 竞彩:" + list1.size() + "场 耗时:" + (end - start));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void deal(List<MatchResult1> result1s_1,String time,String type){
        List<MatchResult1> result1s=new ArrayList<>();
        for(int v=0;v<result1s_1.size();v++){
            MatchResult1 r1=result1s_1.get(v);
            if(r1.getMatchState().equals("1")){
                if(!r1.getMatchTime2().contains("0000-00-00 00:00:00")) {
                    Timestamp ts = Timestamp.valueOf(r1.getMatchTime2());
                    String len = getMinute(df.format(ts), df.format(new Date()));
                    r1.setMatchState(len);
                }else {
                    r1.setMatchState("'完'");
                }
            }else if(r1.getMatchState().equals("3")){
                if(!r1.getMatchTime2().contains("0000-00-00 00:00:00")) {
                    Timestamp ts = Timestamp.valueOf(r1.getMatchTime2());
                    String len = getMinute(df.format(ts), df.format(new Date()));
                    r1.setMatchState((45 + Integer.valueOf(len)) > 90 ? "90+" : String.valueOf(45 + Integer.valueOf(len)));
                }else {
                    r1.setMatchState("'完'");
                }
            }

            if(r1.getMatchState().equals("未")) {
                r1.setStatusDescFK("1");
            }
            result1s.add(r1);
        }
        List<MatchResult1> resultNew = new ArrayList<>();
        int p = 1;
        int pc=result1s.size()/20;
        if(result1s.size()%20>0){
            pc+=1;
        }
        for (int j = 0; j < result1s.size(); j++) {
            if (result1s.size() < 20) {
                //小于20，直接存
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("busiCode","20010201");
                map.put("resCode","000000");
                map.put("message","成功");
                map.put("pageNo","1");
                map.put("pageTotal","1");
                map.put("list",result1s);
                redisUtils.hset("SOCCER:HSET:AGAINSTLIST"+time + type,  String.valueOf(1), JsonMapper.defaultMapper().toJson(map));
                redisUtils.expire("SOCCER:HSET:AGAINSTLIST"+time + type,1*60*60*24*35);
                break;
            } else {
                if ((j + 1) % 20 > 0) {
                    resultNew.add(result1s.get(j));

                    //最后一页，不满20条也要存
                    if (j == result1s.size() - 1) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("busiCode", "20010201");
                        map.put("resCode", "000000");
                        map.put("message", "成功");
                        map.put("pageNo", p);
                        map.put("pageTotal", pc);
                        map.put("list", resultNew);
                        redisUtils.hset("SOCCER:HSET:AGAINSTLIST" + time + type, String.valueOf(p), JsonMapper.defaultMapper().toJson(map));
                        redisUtils.expire("SOCCER:HSET:AGAINSTLIST" + time + type, 1 * 60 * 60 * 24 * 35);
                    }
                } else {
                    //20条
                    resultNew.add(result1s.get(j));
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("busiCode", "20010201");
                    map.put("resCode", "000000");
                    map.put("message", "成功");
                    map.put("pageNo", p);
                    map.put("pageTotal", pc);
                    map.put("list", resultNew);
                    redisUtils.hset("SOCCER:HSET:AGAINSTLIST"+time + type,  String.valueOf(p), JsonMapper.defaultMapper().toJson(map));
                    redisUtils.expire("SOCCER:HSET:AGAINSTLIST" + time + type, 1 * 60 * 60 * 24 * 35);
                    p++;
                    resultNew = new ArrayList<>();
                }
            }
        }
    }

    private String getMinute(String s, String e) {
        String str = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1 = sdf.parse(s);     //开始时间
            Date d2 = sdf.parse(e);     //结束时间

            long diff = d2.getTime() - d1.getTime();
            long nd = 1000 * 24 * 60 * 60;
            long nh = 1000 * 60 * 60;
            long nm = 1000 * 60;

            long day = diff / nd;
            long hour1 = diff % nd / nh;
            long min = diff % nd % nh / nm;
            str = String.valueOf(min);
        } catch (Exception ex) {
            LOGGER.error("计算比赛时间异常" + "s:" + s + "e:" + e);
        }
        return str;
    }

}
