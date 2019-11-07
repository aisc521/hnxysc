package com.zhcdata.jc.quartz.job.redis;

import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.jc.dto.MatchResult1;
import com.zhcdata.jc.service.ScheduleService;
import com.zhcdata.jc.tools.CommonUtils;
import com.zhcdata.jc.tools.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.statement.execute.Execute;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
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
 * @Date 2019/9/19 19:24
 */
@Configuration
@EnableScheduling
@Slf4j
@Component
public class MatchListDataJob implements Job {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private ScheduleMapper scheduleMapper;
    @Resource
    private ScheduleService scheduleService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private CommonUtils commonUtils;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //按期次刷新足彩(分钟) 如果多期次同事开比赛，时间会有问题，再观察
        try {
            String issueNum = "";
            List<MatchResult1> nowIssueNum = scheduleMapper.selectNowIssueNum();
            if (nowIssueNum != null && nowIssueNum.size() > 0) {
                issueNum = nowIssueNum.get(0).getNum();
            }

            List<MatchResult1> zcList = scheduleMapper.selectNoFinishMatch();
            if (zcList == null || zcList.size() == 0) {
                //当前期有未结束的比赛
                issueNum = String.valueOf(Integer.parseInt(issueNum) + 1);
            }
            List<MatchResult1> list6 = new ArrayList<>();
            List<MatchResult1> list6_1 = scheduleService.queryMacthListForJob(null, null, "3", "", "1", issueNum,null,null); //竞彩 正在进行
            list6.addAll(list6_1);

            List<MatchResult1> list6_2 = scheduleService.queryMacthListForJob(null, null, "3", "", "2", issueNum,null,null); //竞彩 未开始
            list6.addAll(list6_2);

            List<MatchResult1> list6_3 = scheduleService.queryMacthListForJob(null, null, "3", "", "3", issueNum,null,null); //竞彩 已经结束
            list6.addAll(list6_3);

            deal(list6, issueNum, "6"); //目前存6
            LOGGER.info("按期次更新足彩赛事列表成功");
        }catch (Exception ex){
            LOGGER.error("按期次刷新足彩(分钟) 异常");
            ex.printStackTrace();
        }

        String s=commonUtils.getSE().split(",")[0];
        String e=commonUtils.getSE().split(",")[1];

        String zc=""; //足彩ID串(按日期验证足彩是否，和按期次验证足彩是否重复效果一样)
        //足彩按大期次刷新(分钟) 原1.0
        long zc_s = ClockUtil.currentTimeMillis();
        try {
            List<MatchResult1> list3 = new ArrayList<>();

            List<MatchResult1> list3_1 = scheduleService.queryMacthListForJob(s, e, "3", "", "1", null,null,null);
            list3.addAll(list3_1);

            List<MatchResult1> list3_2 = scheduleService.queryMacthListForJob(s, e, "3", "", "2", null,null,null);
            list3.addAll(list3_2);

            List<MatchResult1> list3_3 = scheduleService.queryMacthListForJob(s, e, "3", "", "3", null,null,null);
            list3.addAll(list3_3);

            //足彩赛事ID串
            for (int b = 0; b < list3.size(); b++) {
                zc += list3.get(b).getMatchId() + ",";
            }

            if (list3 != null && list3.size() > 0) {
                String time = scheduleService.queryZcNum(commonUtils.getSE().split(",")[0], commonUtils.getSE().split(",")[1]);
                if (StringUtils.isNotBlank(time)) {
                    deal(list3, time, "3");
                    long zc_e = ClockUtil.currentTimeMillis();
                    LOGGER.info("更新足彩赛事列表成功 期号:" + list3.get(0).getNum1() + "共" + list3.size() + "条(包含最新2期,不等同于场次) 耗时:" + (zc_e - zc_s));
                } else {
                    LOGGER.info("更新足彩赛事列表失败,time查询为空");
                }
            } else {
                LOGGER.info("更新足彩赛事列表失败 无赛事信息");
            }
        }catch (Exception ex){
            LOGGER.error("按日期刷新足彩(分钟) 异常");
            ex.printStackTrace();
        }

        String jc=""; //竞彩ID串
        String bd=""; //北单ID串


        try {
            String startDate = "";
            String endDate = "";

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, -2);
            startDate=df.format(calendar.getTime()).substring(0, 10) + " 11:00:00";

            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(new Date());
            calendar1.add(Calendar.DAY_OF_MONTH, -1);
            endDate=df.format(calendar1.getTime()).substring(0, 10) + " 11:00:00";

            String time="";

            for(int i=0;i<5;i++){
                long start = ClockUtil.currentTimeMillis();
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                startDate=df.format(calendar.getTime()).substring(0, 10) + " 11:00:00";

                calendar1.add(Calendar.DAY_OF_MONTH, 1);
                endDate=df.format(calendar1.getTime()).substring(0, 10) + " 11:00:00";

                time=startDate.substring(0, 10);

                //北单
                String sBd=df.format(calendar.getTime()).substring(0, 10) + " 10:00:00";
                String eBd=df.format(calendar1.getTime()).substring(0, 10) + " 10:00:00";

                List<MatchResult1> list2 = new ArrayList<>();

                List<MatchResult1> list2_1 = scheduleService.queryMacthListForJob(sBd, eBd, "2", "", "1", null,null,null);//北单 正在进行
                list2.addAll(list2_1);

                List<MatchResult1> list2_2 = scheduleService.queryMacthListForJob(sBd, eBd, "2", "", "2", null,null,null);//北单 未开始
                list2.addAll(list2_2);

                List<MatchResult1> list2_3 = scheduleService.queryMacthListForJob(sBd, eBd, "2", "", "3", null,null,null);//北单 已结束
                list2.addAll(list2_3);

                //北单赛事ID串
                for (int a = 0; a < list2.size(); a++) {
                    bd += list2.get(a).getMatchId() + ",";
                }
                deal(list2, sBd.split(" ")[0], "2");

                //竞彩
                List<MatchResult1> list1=new ArrayList<>();
                List<MatchResult1> list1_1 = scheduleService.queryMacthListForJob(startDate, endDate, "1","","1",null,null,null); //竞彩
                list1.addAll(list1_1);
                List<MatchResult1> list1_2 = scheduleService.queryMacthListForJob(startDate, endDate, "1","","2",null,null,null); //竞彩
                list1.addAll(list1_2);
                List<MatchResult1> list1_3 = scheduleService.queryMacthListForJob(startDate, endDate, "1","","3",null,null,null); //竞彩
                list1.addAll(list1_3);

                //竞彩赛事ID串
                for(int c=0;c<list1.size();c++) {
                    jc += list1.get(c).getMatchId() + ",";
                }
                deal(list1,time,"1");


                //全部
                String str = "";
                List<MatchResult1> list5 = new ArrayList<>();
                List<MatchResult1> list5_1 = scheduleService.queryMacthListForJob(startDate, endDate, "4", "", "1", null,null,null);
                for (int a = 0; a < list5_1.size(); a++) {
                    if (!str.contains(list5_1.get(a).getMatchId())) {
                        MatchResult1 r1 = list5_1.get(a);
                        if (jc.contains(r1.getMatchId() + ",")) {
                            r1.setMatchType("1");
                        } else if (bd.contains(r1.getMatchId() + ",")) {
                            r1.setMatchType("2");
                        } else if (zc.contains(r1.getMatchId() + ",")) {
                            r1.setMatchType("3");
                        } else {
                            r1.setMatchType("5");
                        }
                        list5.add(r1);
                        str += list5_1.get(a).getMatchId() + ",";
                    }
                }

                List<MatchResult1> list5_2 = scheduleService.queryMacthListForJob(startDate, endDate, "4", "", "2", null,null,null);
                for (int b = 0; b < list5_2.size(); b++) {
                    if (!str.contains(list5_2.get(b).getMatchId())) {
                        MatchResult1 r2 = list5_2.get(b);
                        if (jc.contains(r2.getMatchId() + ",")) {
                            r2.setMatchType("1");
                        } else if (bd.contains(r2.getMatchId() + ",")) {
                            r2.setMatchType("2");
                        } else if (zc.contains(r2.getMatchId() + ",")) {
                            r2.setMatchType("3");
                        } else {
                            r2.setMatchType("5");
                        }
                        list5.add(r2);
                        str += list5_2.get(b).getMatchId() + ",";
                    }
                }

                List<MatchResult1> list5_3 = scheduleService.queryMacthListForJob(startDate, endDate, "4", "", "3", null,null,null);
                for (int c = 0; c < list5_3.size(); c++) {
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
                LOGGER.info("更新" + time + "赛事列表成功 竞彩:" + list1.size() + "场,北单:" + list2.size() + "场 耗时:" + (end - start));
            }
        } catch (Exception ex) {
            LOGGER.error("定时任务后5天故障");
            ex.printStackTrace();
        }
    }

    /**
     * 数据存缓存
     * @param result1s_1
     * @param time
     * @param type
     */
    public void deal(List<MatchResult1> result1s_1,String time,String type){
        List<MatchResult1> result1s=new ArrayList<>();
        for(int v=0;v<result1s_1.size();v++){
            MatchResult1 r1=result1s_1.get(v);
            //处理盘口
            r1.setMatchPankou(getPanKou1(r1.getMatchPankou()));
            if(r1.getMatchState().equals("1")){
                r1.setStatusDescFK("2");
                r1.setStatusescFK("2");
                if(r1.getMatchTime2()!=null&&!r1.getMatchTime2().contains("0000-00-00 00:00:00")) {
                    Timestamp ts = Timestamp.valueOf(r1.getMatchTime2());
                    String len = getMinute(df.format(ts), df.format(new Date()));
                    r1.setMatchState(len+"'");
                }
            }else if(r1.getMatchState().equals("3")){
                r1.setStatusDescFK("3");
                r1.setStatusescFK("3");
                if(r1.getMatchTime2()!=null&&!r1.getMatchTime2().contains("0000-00-00 00:00:00")) {
                    Timestamp ts = Timestamp.valueOf(r1.getMatchTime2());
                    String len = getMinute(df.format(ts), df.format(new Date()));
                    r1.setMatchState((45 + Integer.valueOf(len)) > 90 ? "90+'" : String.valueOf(45 + Integer.valueOf(len))+"'");
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
            LOGGER.error("计算比赛时间异常" + "s:" + s + "e:" , ex);
        }
        return str;
    }
    public String getPanKou1(String value){
        if(value!=null) {
            if (value.equals("0.0")) {
                value = "0";
            } else if (value.equals("1.0")) {
                value = "1";
            } else if (value.equals("2.0")) {
                value = "2";
            } else if (value.equals("3.0")) {
                value = "3";
            } else if (value.equals("4.0")) {
                value = "4";
            } else if (value.equals("5.0")) {
                value = "5";
            } else if (value.equals("-1.0")) {
                value = "-1";
            } else if (value.equals("-2.0")) {
                value = "-2";
            } else if (value.equals("-3.0")) {
                value = "-3";
            } else if (value.equals("-4.0")) {
                value = "-4";
            } else if (value.equals("-5.0")) {
                value = "-5";
            }
        }
        return value;
    }
    public String getPanKou(String value){
        if(value!=null) {
            if (value.equals("0.0")) {
                return "0";
            } else if (value.equals("1.0")) {
                return "1";
            } else if (value.equals("2.0")) {
                return "2";
            } else if (value.equals("3.0")) {
                return "3";
            } else if (value.equals("4.0")) {
                return "4";
            } else if (value.equals("5.0")) {
                return "5";
            } else if (value.equals("-1.0")) {
                return "-1";
            } else if (value.equals("-2.0")) {
                return "-2";
            } else if (value.equals("-3.0")) {
               return  "-3";
            } else if (value.equals("-4.0")) {
               return  "-4";
            } else if (value.equals("-5.0")) {
               return  "-5";
            }
            String oneArray[] = {"-5", "-4.75", "-4.5", "-4.25", "-4", "-3.75", "-3.5", "-3.25", "-3", "-2.75", "-2.5", "-2.25", "-2", "-1.75", "-1.5", "-1.25", "-1", "-0.75", "-0.5", "-0.25", "0", "0.25", "0.5", "0.75", "1", "1.25", "1.5", "1.75", "2", "2.25", "2.5", "2.75", "3", "3.25", "3.5", "3.75", "4", "4.25", "4.5", "4.75", "5"};
            String twoArray[] = {"-5/5", "-4.5/5", "-4.5/4.5", "-4/4.5", "-4/4.5", "-3.5/4", "-3.5/3.5", "-3/3.5", "-3/3", "-2.5/3", "-2.5/2.5", "-2/2.5", "-2/2", "-1.5/2", "-1.5/1.5", "-1/1.5", "-1/1", "-0.5/1", "-0.5/0.5", "-0/0.5", "0/0", "0/0.5", "0.5/0.5", "0.5/1", "1/1", "1/1.5", "1.5/1.5", "1.5/2", "2/2", "2/2.5", "2.5/2.5", "2.5/3", "3/3", "3/3.5", "3.5/3.5", "3.5/4", "4/4", "4/4.5", "4.5/4.5", "4.5/5", "5/5"};
            int index = ArrayUtils.indexOf(oneArray, value);
            if (index == -1) {
                return value;
            }
            return twoArray[index];
        }else {
            return "";
        }
    }
}
