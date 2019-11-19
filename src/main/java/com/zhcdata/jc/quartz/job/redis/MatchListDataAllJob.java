package com.zhcdata.jc.quartz.job.redis;

import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.model.JcMatchLottery;
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
    private ScheduleMapper scheduleMapper;

    @Resource
    private ScheduleService scheduleService;

    @Resource
    private RedisUtils redisUtils;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String jc=""; //竞彩ID串
        String bd=""; //北单ID串
        String zc=""; //足彩ID串
        try {
            //处理足彩
            String issueNum = null;
            List<JcMatchLottery> nextNum = scheduleMapper.selectNextIssueNum();           //后N
            if (nextNum != null && nextNum.size() > 0) {
                issueNum = nextNum.get(0).getIssueNum();
            }

            List<JcMatchLottery> lastNum = scheduleMapper.selectLastIssueNum(issueNum);   //前10
            lastNum.addAll(nextNum);


            for (int a = 0; a < lastNum.size(); a++) {

                long start = ClockUtil.currentTimeMillis();
                List<MatchResult1> list1 = new ArrayList<>();
                List<MatchResult1> list1_1 = scheduleService.queryMacthListForJob(null, null, "3", "", "1", lastNum.get(a).getIssueNum(),null,null); //竞彩 正在进行
                list1.addAll(list1_1);

                List<MatchResult1> list1_2 = scheduleService.queryMacthListForJob(null, null, "3", "", "2", lastNum.get(a).getIssueNum(),null,null); //竞彩 未开始
                list1.addAll(list1_2);

                List<MatchResult1> list1_3 = scheduleService.queryMacthListForJob(null, null, "3", "", "3", lastNum.get(a).getIssueNum(),null,null); //竞彩 已经结束
                list1.addAll(list1_3);

                for (int b = 0; b < list1.size(); b++) {
                    zc += list1.get(b).getMatchId() + ",";
                }

                deal(list1, lastNum.get(a).getIssueNum(), "6"); //目前存6
                long end = ClockUtil.currentTimeMillis();
                LOGGER.info("更新足彩赛事列表成功 期号:" + lastNum.get(a).getIssueNum() + " 耗时:" + (end - start));
            }
        }catch (Exception ex){
            LOGGER.error("异常信息");
            ex.printStackTrace();
        }

        String startDate = "";
        String startDateBd = "";
        String endDate = "";
        String endDateBd = "";
        String startDateAll="";
        String endDateAll="";

        try{
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, -25);
//            startDate=df1.format(calendar.getTime()) + " 11:00:00";

            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(new Date());
            calendar1.add(Calendar.DAY_OF_MONTH, -24);
//            endDate=df1.format(calendar1.getTime()) + " 11:00:00";

            String time="";
            for(int i=0;i<30;i++) {
                long start = ClockUtil.currentTimeMillis();
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                String format = df1.format(calendar.getTime());
                startDate = format + " 11:00:00";
                startDateBd= format + " 09:59:59";
                startDateAll=format + " 10:59:59";

                calendar1.add(Calendar.DAY_OF_MONTH, 1);
                String format1 = df1.format(calendar1.getTime());
                endDate = format1 + " 11:00:00";
                endDateBd= format1 + " 09:59:59";
                endDateAll=format + " 10:59:59";

                time = startDate.substring(0, 10);

                //竞彩
                List<MatchResult1> list1=new ArrayList<>();
                List<MatchResult1> list1_1 = scheduleService.queryMacthListForJob(startDate, endDate, "1", "","1",null,null,null); //竞彩 正在进行
                list1.addAll(list1_1);

                List<MatchResult1> list1_2 = scheduleService.queryMacthListForJob(startDate, endDate, "1","","2",null,null,null); //竞彩 未开始
                list1.addAll(list1_2);

                List<MatchResult1> list1_3 = scheduleService.queryMacthListForJob(startDate, endDate, "1","","3",null,null,null); //竞彩 已经结束
                list1.addAll(list1_3);
                for(int c=0;c<list1.size();c++) {
                    jc += list1.get(c).getMatchId() + ",";
                }
                deal(list1, time, "1");

                //北单
                List<MatchResult1> list2=new ArrayList<>();
                List<MatchResult1> list2_1 = scheduleService.queryMacthListForJob(startDateBd, endDateBd, "2", "","1",null,null,null); //北单 正在进行
                list2.addAll(list2_1);

                List<MatchResult1> list2_2 = scheduleService.queryMacthListForJob(startDateBd, endDateBd, "2","","2",null,null,null); //北单 未开始
                list2.addAll(list2_2);

                List<MatchResult1> list2_3 = scheduleService.queryMacthListForJob(startDateBd, endDateBd, "2","","3",null,null,null); //北单 已经结束
                list2.addAll(list2_3);

                //北单赛事ID串
                for(int a=0;a<list2.size();a++){
                    bd+=list2.get(a).getMatchId()+",";
                }
                deal(list2, time, "2");
                System.out.println(time);
                LOGGER.info("更新" + time + "赛事列表成功 北单:" + list2.size() + "场");

                String str="";
                List<MatchResult1> list5 = new ArrayList<>();
                List<MatchResult1> list5_1 = scheduleService.queryMacthListForJob(startDateAll, endDateAll, "4","","1",null,null,null);//全部 正在进行
                for(int a=0;a<list5_1.size();a++){
                    if(!str.contains(list5_1.get(a).getMatchId())) {
                        MatchResult1 r1=list5_1.get(a);
                        if(jc.contains(r1.getMatchId()+",")) {
                            r1.setMatchType("1");
                        }else if(bd.contains(r1.getMatchId()+",")){
                            r1.setMatchType("2");
                        }else if(zc.contains(r1.getMatchId()+",")){
                            r1.setMatchType("3");
                        }else {
                            r1.setMatchType("5");
                        }
                        list5.add(r1);
                        str+=list5_1.get(a).getMatchId()+",";
                    }
                }

                List<MatchResult1> list5_2 = scheduleService.queryMacthListForJob(startDateAll, endDateAll, "4","","2",null,null,null);//全部 未开始
                for(int b=0;b<list5_2.size();b++){
                    if(!str.contains(list5_2.get(b).getMatchId())) {
                        MatchResult1 r2=list5_2.get(b);
                        if(jc.contains(r2.getMatchId()+",")) {
                            r2.setMatchType("1");
                        }else if(bd.contains(r2.getMatchId()+",")){
                            r2.setMatchType("2");
                        }else if(zc.contains(r2.getMatchId()+",")){
                            r2.setMatchType("3");
                        }else {
                            r2.setMatchType("5");
                        }
                        list5.add(r2);
                        str+=list5_2.get(b).getMatchId()+",";
                    }
                }

                List<MatchResult1> list5_3 = scheduleService.queryMacthListForJob(startDateAll, endDateAll, "4","","3",null,null,null);//全部 已经结束
                for(int c=0;c<list5_3.size();c++) {
                    if (!str.contains(list5_3.get(c).getMatchId())) {
                        MatchResult1 r3 = list5_3.get(c);
                        if (jc.contains(r3.getMatchId() + ",")) {
                            r3.setMatchType("1");
                        } else if (bd.contains(r3.getMatchId() + ",")) {
                            r3.setMatchType("2");
                        } else if (zc.contains(r3.getMatchId() + ",")) {
                            r3.setMatchType("3");
                        } else {
                            r3.setMatchType("5");
                        }
                        list5.add(r3);
                        str += list5_3.get(c).getMatchId() + ",";
                    }
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
                if(r1.getMatchTime2()!=null&&!r1.getMatchTime2().contains("0000-00-00 00:00:00")) {
                    Timestamp ts = Timestamp.valueOf(r1.getMatchTime2());
                    String len = getMinute(df.format(ts), df.format(new Date()));
                    r1.setMatchState(len+"'");
                }else {
                    r1.setMatchState("'完'");
                }
            }else if(r1.getMatchState().equals("3")){
                if(r1.getMatchTime2()!=null&&!r1.getMatchTime2().contains("0000-00-00 00:00:00")) {
                    Timestamp ts = Timestamp.valueOf(r1.getMatchTime2());
                    String len = getMinute(df.format(ts), df.format(new Date()));
                    r1.setMatchState((45 + Integer.valueOf(len)) > 90 ? "90+'" : String.valueOf(45 + Integer.valueOf(len))+"'");
                }else {
                    r1.setMatchState("'完'");
                }
            }else if(r1.getMatchState().equals("中")){
                r1.setStatusDescFK("2");
            }else if(r1.getMatchState().equals("(完)")){
                r1.setStatusDescFK("-1");
            }else if(r1.getMatchState().equals("未")) {
                r1.setStatusDescFK("0");
            }else if(r1.getMatchState().equals("完")) {
                r1.setStatusDescFK("-1");
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
            LOGGER.error("计算比赛时间异常" + "s:" + s + "e:" , ex);
        }
        return str;
    }

}
