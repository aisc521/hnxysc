package com.zhcdata.jc.quartz.job.redis;

import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.db.model.TbJcVictory;
import com.zhcdata.jc.dto.*;
import com.zhcdata.jc.service.TbJcExpertService;
import com.zhcdata.jc.service.TbJcMatchService;
import com.zhcdata.jc.service.TbJcVictoryService;
import com.zhcdata.jc.service.TbPlanService;
import com.zhcdata.jc.tools.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 处理专家战绩
 * @Author cuishuai
 * @Date 2019/9/20 14:44
 */
@Slf4j
@Component
public class HandleExpertRecordJob implements Job {
    SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private TbJcExpertService tbJcExpertService;

    @Resource
    private TbPlanService tbPlanService;

    @Resource
    private TbJcMatchService tbJcMatchService;

    @Resource
    private TbJcVictoryService tbJcVictoryService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    MatchListDataJob matchListDataJob;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("[处理专家战绩开启]" + df2.format(new Date()));

        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String type = dataMap.getString("type");

        try {
            DecimalFormat dfLv = new DecimalFormat("0.00");//格式化小数

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());

            calendar.add(Calendar.DAY_OF_MONTH, -2);
            String timeThree = df.format(calendar.getTime());                   //三天

            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, -4);                    //五天
            String timeFive = df.format(calendar.getTime());

            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, -6);                    //七天
            String timeSeven = df.format(calendar.getTime());

            String time = (String) redisUtils.hget("SOCCER:HSET:HandleExpertRecordJob", "TIME");

            //更新时间(任务开启先记录时间,以免漏掉开启到结束这段时间的方案)
            redisUtils.hset("SOCCER:HSET:HandleExpertRecordJob", "TIME", df2.format(new Date()));
            List<ExpertInfo> expertResults = new ArrayList<>();
            if (type != null && type.equals("1")) {
                expertResults = tbJcExpertService.queryExpertsAll();   //所有专家列表
            } else {
                expertResults = tbJcExpertService.queryExperts(time);   //所有专家列表(有新结束方案)
            }

            if (expertResults != null && expertResults.size() > 0) {
                for (int p = 0; p < expertResults.size(); p++) {
                    //当前连中
                    //已发方案总数
                    //方案命中数
                    //三天命中率
                    //五天命中率
                    //七天命中率
                    //七天回报率/七天盈利率
                    //趋势
                    //全部回报率
                    //全部命中率
                    //历史最高连红
                    //近10中几
                    //近9中几
                    //近8中几
                    //近7中几
                    //近6中几
                    //近5中几
                    //近4中几
                    //近3中几
                    int lz = 0;         //当前连中
                    int zs = 0;         //已发方案总数
                    int z = 0;          //已中

                    int three = 0;      //三天总数
                    int three_z = 0;    //三天命中

                    int five = 0;       //五天总数
                    int five_z = 0;     //五天命中

                    int seven = 0;      //七天总数
                    int seven_z = 0;    //七天命中

                    String trend = "";  //趋势

                    BigDecimal lastSevenDayPayMoney = new BigDecimal(0);      //近七天投入
                    BigDecimal lastSevenDayReturnMoney = new BigDecimal(0);   //近七天回报

                    BigDecimal lastDayPayMoney = new BigDecimal(0);           //全部投入
                    BigDecimal lastDayReturnMoney = new BigDecimal(0);        //全部回报

                    int z_count = 0;    //全部命中总数

                    int lh_history = 0; //历史最高连红

                    int jin10z = 0;       //近10中几
                    int jin9z = 0;        //近 9中几
                    int jin8z = 0;        //近 8中几
                    int jin7z = 0;        //近 7中几
                    int jin6z = 0;        //近 6中几
                    int jin5z = 0;        //近 5中几
                    int jin4z = 0;        //近 4中几
                    int jin3z = 0;        //近 3中几
                    if (String.valueOf(expertResults.get(p).getId()).equals("124")) {
                        String sd = "";
                    }

                    try {
                        List<TbJcPlan> planResults = tbPlanService.queryPlanList(String.valueOf(expertResults.get(p).getId()), "0"); //已结束方案
                        if (planResults != null && planResults.size() > 0) {
                            for (int k = 0; k < planResults.size(); k++) {
                                if(planResults.get(k).getId()==1103){
                                    String sfsd="";
                                }

                                //新增胜负玩法逻辑 2020-04-02 yyc

                                int xz1 = 0;                                          //记录选择个数(投入金额)
                                int xz2 = 0;
                                BigDecimal return_money = new BigDecimal(0);     //当前方案奖金
                                BigDecimal pay_money = new BigDecimal(0);        //当前方案投入

                                int isServerDay = 0; //是否近七天方案(1是 0否)

                                List<MatchPlanResult1> matchlist = tbJcMatchService.queryList1(planResults.get(k).getId());   //当前方案的比赛
                                if (matchlist != null && matchlist.size() > 0) {
                                    for (int m = 0; m < matchlist.size(); m++) {
                                        if (matchlist.get(m).getStatus().equals("1")||matchlist.get(m).getStatus().equals("2")) {
                                            lz += 1;    //中,则+1
                                        } else {
                                            //历史最高连红 如果当前连中大于历史连中则替换
                                            if (lz > lh_history) {
                                                lh_history = lz;
                                            }
                                            lz = 0;     //不中,则重置
                                        }
                                        zs += 1;        //已发方案总数(按已发赛事数量算的)

                                        //三天
                                        if (matchlist.get(m).getDateOfMatch().compareTo(timeThree) >= 0) {
                                            three += 1;          //投入+1
                                            if (matchlist.get(m).getStatus().equals("1")||matchlist.get(m).getStatus().equals("2")) {
                                                three_z += 1;    //中奖+1
                                            }
                                        }

                                        //五天
                                        if (matchlist.get(m).getDateOfMatch().compareTo(timeFive) >= 0) {
                                            five += 1;
                                            if (matchlist.get(m).getStatus().equals("1")||matchlist.get(m).getStatus().equals("2")) {
                                                five_z += 1;
                                            }
                                        }


                                        //七天
                                        if (matchlist.get(m).getDateOfMatch().compareTo(timeSeven) >= 0) {
                                            isServerDay = 1;
                                            seven += 1;
                                            if (matchlist.get(m).getStatus().equals("1")||matchlist.get(m).getStatus().equals("2")) {
                                                seven_z += 1;
                                            }
                                        }

                                        //中红 不中黑
                                        if(planResults.get(k).getMatchPlanType()==null||planResults.get(k).getMatchPlanType().equals("1")) {
                                            //竞彩在此处理，胜负玩法不在这处理
                                            if (matchlist.get(m).getStatus().equals("1")) {
                                                trend += "红";
                                            } else {
                                                trend += "黑";
                                            }
                                        }

                                        //全部命中数(率)
                                        if (matchlist.get(m).getStatus().equals("1")||matchlist.get(m).getStatus().equals("2")) {
                                            z_count += 1;
                                        }

                                        //赛果
                                        if (matchlist.get(m).getScore() == null) {
                                            log.info("赛事Id:" + matchlist.get(m).getMatchId() + "无赛果,因无法计算，被迫跳过");
                                            continue;
                                        }
                                        String score = matchlist.get(m).getScore();
                                        if (score.equals("vs")) {
                                            score = "0:0";
                                        }
                                        String hScore = score.split(":")[0];
                                        if (hScore.contains(".")) {
                                            hScore = hScore.split("\\.")[0];
                                        }

                                        String gScore = score.split(":")[1];
                                        if (gScore.contains(".")) {
                                            gScore = gScore.split("\\.")[0];
                                        }

                                        score = hScore + ":" + gScore;
                                        String[] scores = score.split(":");

                                        //已购方案信息
                                        String planInfo = matchlist.get(m).getPlanInfo();
                                        if (planResults.get(k).getMatchPlanType()==null||planResults.get(k).getMatchPlanType().equals("1")) {
                                            if (planInfo.contains("|")) {
                                                String spf = planInfo.split("\\|")[0];
                                                String rqspf = planInfo.split("\\|")[1];

                                                String[] spfs = spf.split(",");             //胜平负
                                                String[] rqspfs = rqspf.split(",");         //让球胜平负

                                                BigDecimal money_match = new BigDecimal(0);     //当前赛事奖金

                                                if (!spf.equals("0,0,0")) {
                                                    //计算胜平负
                                                    if (spfs.length == 3) {
                                                        if (Double.valueOf(spfs[0]) > 0) {
                                                            if (m == 0) {
                                                                xz1 += 1;
                                                            } else if (m == 1) {
                                                                xz2 += 1;
                                                            }

                                                            if (Integer.valueOf(scores[0]) > Integer.valueOf(scores[1])) {
                                                                money_match = new BigDecimal(spfs[0]);  //胜出 计奖金
                                                            }
                                                        }
                                                        if (Double.valueOf(spfs[1]) > 0) {
                                                            if (m == 0) {
                                                                xz1 += 1;
                                                            } else if (m == 1) {
                                                                xz2 += 1;
                                                            }
                                                            if (Integer.valueOf(scores[0]) == Integer.valueOf(scores[1])) {
                                                                money_match = new BigDecimal(spfs[1]);  //平 计奖金
                                                            }
                                                        }
                                                        if (Double.valueOf(spfs[2]) > 0) {
                                                            if (m == 0) {
                                                                xz1 += 1;
                                                            } else if (m == 1) {
                                                                xz2 += 1;
                                                            }
                                                            if (Integer.valueOf(scores[0]) < Integer.valueOf(scores[1])) {
                                                                money_match = new BigDecimal(spfs[2]);  //负 计奖金
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    //计算让球胜平负
                                                    if (rqspfs.length == 3) {
                                                        BigDecimal rb = new BigDecimal(0);           //让球数
                                                        SPFListDto rballs = tbPlanService.querySPFList((matchlist.get(m).getMatchId()));
                                                        if (rballs != null) {
                                                            rb = new BigDecimal(rballs.getAwayTeamRangballs());
                                                        }

                                                        if (Double.valueOf(rqspfs[0]) > 0) {
                                                            if (m == 0) {
                                                                xz1 += 1;
                                                            } else if (m == 1) {
                                                                xz2 += 1;
                                                            }

                                                            if (new BigDecimal(scores[0]).add(rb).compareTo(new BigDecimal(scores[1])) > 0) {
                                                                money_match = new BigDecimal(rqspfs[0]);  //胜出 计奖金
                                                            }
                                                        }

                                                        if (Double.valueOf(rqspfs[1]) > 0) {
                                                            if (m == 0) {
                                                                xz1 += 1;
                                                            } else if (m == 1) {
                                                                xz2 += 1;
                                                            }
                                                            if (new BigDecimal(scores[0]).add(rb).compareTo(new BigDecimal(scores[1])) == 0) {
                                                                money_match = new BigDecimal(rqspfs[1]);  //平 计奖金
                                                            }
                                                        }
                                                        if (Double.valueOf(rqspfs[2]) > 0) {
                                                            if (m == 0) {
                                                                xz1 += 1;
                                                            } else if (m == 1) {
                                                                xz2 += 1;
                                                            }
                                                            if (new BigDecimal(scores[0]).add(rb).compareTo(new BigDecimal(scores[1])) < 0) {
                                                                money_match = new BigDecimal(rqspfs[2]);  //负 计奖金
                                                            }
                                                        }
                                                    }
                                                }

                                                //比赛推迟36小时算取消,比赛算中,方案算结束。如果两场，需要等到推迟36小时，再结束
                                                //这判断为1，没有问题
                                                if (matchlist.get(m).getMatchState().equals("-10") || matchlist.get(m).getMatchState().equals("-12") || matchlist.get(m).getMatchState().equals("-14")) {
                                                    //腰斩、取消、推迟的比赛，sp值为1
                                                    money_match = new BigDecimal(1);
                                                    if (xz1 > 0) {
                                                        money_match = money_match.multiply(new BigDecimal(xz1));
                                                    }
                                                    if (xz2 > 0) {
                                                        money_match = money_match.multiply(new BigDecimal(xz2));
                                                    }
                                                }

                                                if (m == 0) {
                                                    //第一场比赛
                                                    return_money = money_match;
                                                } else {
                                                    //第二场比赛，目前仅支持最多两场
                                                    return_money = return_money.multiply(money_match);
                                                }
                                            }
                                        } else if(planResults.get(k).getMatchPlanType()!=null&&planResults.get(k).getMatchPlanType().equals("2")){
                                            //胜负玩法
                                            xz1+=1; //推比赛数量
                                            String[] odds = matchlist.get(m).getOdds().split("/");
                                            String[] rqspf = planInfo.split("\\|")[1].split(",");   //专家选择的赔率信息
                                            String panKou = odds[2];
                                            Float value = Math.abs(Float.valueOf(panKou)) % Float.valueOf("0.5");
                                            if (value != 0) {
                                                int remark=2;

                                                String pankou1=matchListDataJob.getPanKou(panKou);
                                                if(pankou1.contains("-")){
                                                    pankou1=pankou1.replace("/","/-");     //把-0.5/1 处理成-0.5/-1
                                                }
                                                String[] panKou2 = pankou1.split("/");
                                                for (int j = 0; j < panKou2.length; j++) {

                                                    if (new BigDecimal(scores[0]).subtract(new BigDecimal(panKou2[j])).compareTo(new BigDecimal(scores[1])) == 0) {
                                                        //左盘走或者右盘走 走盘需要返回本金 这里赋值0.5
                                                        return_money=return_money.add(new BigDecimal(0.5));
                                                        if(remark==2) {
                                                            //第一次循环，是走盘，则显示走。如果第二次循环是走盘，不处理
                                                            remark = 0;
                                                        }
                                                    }else {
                                                        if(Double.valueOf(rqspf[0]) > 0) {
                                                            if (new BigDecimal(scores[0]).subtract(new BigDecimal(panKou2[j])).compareTo(new BigDecimal(scores[1])) > 0) {
                                                                remark=1;
                                                                return_money = return_money.add(new BigDecimal(rqspf[0]).divide(new BigDecimal(2)).add(new BigDecimal(0.5)));
                                                                //此类盘口分两份，
                                                            }else {
                                                                remark=-1;
                                                            }
                                                        }else if(Double.valueOf(rqspf[2]) > 0){
                                                            if (new BigDecimal(scores[0]).subtract(new BigDecimal(panKou)).compareTo(new BigDecimal(scores[1])) < 0) {
                                                                return_money = return_money.add(new BigDecimal(rqspf[2]).divide(new BigDecimal(2)).add(new BigDecimal(0.5)));
                                                                remark=1;
                                                            }else {
                                                                remark=-1;
                                                            }
                                                        }
                                                    }
                                                }
                                                if(remark==1){
                                                    trend+="红";
                                                }else if(remark==-1){
                                                    trend+="黑";
                                                }else if(remark==0){
                                                    trend+="走";
                                                }

                                            } else {
                                                //如果走盘
                                                if (new BigDecimal(scores[0]).subtract(new BigDecimal(panKou)).compareTo(new BigDecimal(scores[1])) == 0) {
                                                    trend+="走";
                                                    return_money = new BigDecimal(1); //走盘按1处理(相当于原路返回)
                                                }else {
                                                    if(Double.valueOf(rqspf[0]) > 0) {
                                                        if (new BigDecimal(scores[0]).subtract(new BigDecimal(panKou)).compareTo(new BigDecimal(scores[1])) > 0) {
                                                            trend+="红";
                                                            return_money = new BigDecimal(rqspf[0]).add(new BigDecimal(1)); //确认球探的盘口是否需要+1(目前需要加)
                                                            //todo
                                                        }else {
                                                            trend+="黑";
                                                        }
                                                    }else if(Double.valueOf(rqspf[2]) > 0){
                                                        if (new BigDecimal(scores[0]).subtract(new BigDecimal(panKou)).compareTo(new BigDecimal(scores[1])) < 0) {
                                                            trend+="红";
                                                            return_money = new BigDecimal(rqspf[2]).add(new BigDecimal(1));
                                                        }else {
                                                            trend+="黑";
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if (matchlist.size() == 1) {
                                        //推一场
                                        pay_money = new BigDecimal(xz1).multiply(new BigDecimal(2));
                                    } else if (matchlist.size() == 2) {
                                        //推两场
                                        pay_money = new BigDecimal(xz1).multiply(new BigDecimal(2)).multiply(new BigDecimal(xz2));
                                    }
                                } else {
                                    log.info("方案(id:" + planResults.get(k).getId() + ")无赛事信息");
                                }

                                //当前专家方案
                                lastDayPayMoney = lastDayPayMoney.add(pay_money);                                           //支出
                                lastDayReturnMoney = lastDayReturnMoney.add(return_money.multiply(new BigDecimal(2)));  //回报

                                if (isServerDay == 1) {
                                    System.out.println("方案号" + planResults.get(k).getId() + "奖金:" + return_money.multiply(new BigDecimal(2)) + "," + "投注金额:" + pay_money);
                                    //近七天
                                    lastSevenDayPayMoney = lastSevenDayPayMoney.add(pay_money);
                                    lastSevenDayReturnMoney = lastSevenDayReturnMoney.add(return_money.multiply(new BigDecimal(2)));
                                }
                            }

                            int c = 1;
                            //处理近3 4 5 6 7 8 9 10场 命中场次
                            for (int b = trend.length() - 1; b >= 0; b--) {
                                String v = trend.substring(b, b + 1);
                                if (v.equals("红")||v.equals("走")) {
                                    if (c < 4) {
                                        jin3z += 1;
                                        jin4z += 1;
                                        jin5z += 1;
                                        jin6z += 1;
                                        jin7z += 1;
                                        jin8z += 1;
                                        jin9z += 1;
                                        jin10z += 1;
                                    } else if (c < 5) {
                                        jin4z += 1;
                                        jin5z += 1;
                                        jin6z += 1;
                                        jin7z += 1;
                                        jin8z += 1;
                                        jin9z += 1;
                                        jin10z += 1;
                                    } else if (c < 6) {
                                        jin5z += 1;
                                        jin6z += 1;
                                        jin7z += 1;
                                        jin8z += 1;
                                        jin9z += 1;
                                        jin10z += 1;
                                    } else if (c < 7) {
                                        jin6z += 1;
                                        jin7z += 1;
                                        jin8z += 1;
                                        jin9z += 1;
                                        jin10z += 1;
                                    } else if (c < 8) {
                                        jin7z += 1;
                                        jin8z += 1;
                                        jin9z += 1;
                                        jin10z += 1;
                                    } else if (c < 9) {
                                        jin8z += 1;
                                        jin9z += 1;
                                        jin10z += 1;
                                    } else if (c < 10) {
                                        jin9z += 1;
                                        jin10z += 1;
                                    } else if (c < 11) {
                                        jin10z += 1;
                                    }
                                }
                                c++;
                            }
                            VictoryInfo info = new VictoryInfo();
                            info.setLzNow(String.valueOf(lz));                                                            //当前连中
                            info.setF(String.valueOf(zs));                                                                //已发方案总数
                            info.setZ(String.valueOf(z_count));                                                           //方案命中数
                            info.setzThreeDays(three == 0 ? new Double(0) : Double.valueOf(Math.floor((float) three_z * 100 / three)));          //三天命中率
                            info.setzFiveDays(five_z == 0 ? new Double(0) : Double.valueOf(Math.floor((float) five_z * 100 / five)));            //五天命中率
                            info.setzSevenDays(seven_z == 0 ? new Double(0) : Double.valueOf(Math.floor((float) seven_z * 100 / seven)));        //七天命中率
                            info.setTrend(trend);                                                                                                       //趋势
                            if(trend.length()>10){
                                info.setTrend(trend.substring(trend.length()-10,trend.length()));
                            }
                            info.setzAll(Double.valueOf(Math.floor((float) z_count * 100 / zs)));                                                       //全部命中率
                            info.setLzBig(String.valueOf(lh_history));                                                   //历史最高连红
                            info.setTen_z(String.valueOf(jin10z));                                                       //近10中几
                            info.setNine_z(String.valueOf(jin9z));                                                       //近9中几
                            info.setEight_z(String.valueOf(jin8z));                                                      //近8中几
                            info.setSeven_z(String.valueOf(jin7z));                                                      //近7中几
                            info.setSix_z(String.valueOf(jin6z));                                                       //近6中几
                            info.setFive_z(String.valueOf(jin5z));                                                       //近5中几
                            info.setFour_z(String.valueOf(jin4z));                                                       //近4中几
                            info.setThree_z(String.valueOf(jin3z));                                                      //近3中几
                            if (lastSevenDayPayMoney.compareTo(new BigDecimal(0)) > 0) {
                                info.setReturnSevenDays(Double.valueOf(Math.floor(lastSevenDayReturnMoney.multiply(new BigDecimal(100)).divide(lastSevenDayPayMoney, 2).doubleValue())));  //七天回报率
                            } else {
                                info.setReturnSevenDays(new Double(0));
                            }
                            //info.setYlSevenDays("0");                                                                                                                           //七天盈利率
                            if (lastDayPayMoney.compareTo(new BigDecimal(0)) > 0) {
                                info.setReturnAll(Double.valueOf(Math.floor(lastDayReturnMoney.multiply(new BigDecimal(100)).divide(lastDayPayMoney, 2).doubleValue())));                  //全部回报率
                            } else {
                                info.setReturnAll(new Double(0));
                            }
                            info.setExpertId(String.valueOf(expertResults.get(p).getId()));
                            info.setSevenDaysHit(seven + "中" + seven_z);
                            //if(info.getSevenDaysHit().equals("0中0")){
                            //    String s="";
                            //}

                            if (Integer.parseInt(info.getLzNow()) >= 5) {
                                //1类(倒序)
                                info.setOrder_By(getOrderBy(Integer.parseInt(info.getLzNow())) + 100);
                            } else {
                                //2类(倒序数大的在前面、数小的在后边) 9 8 7 6 5 4
                                if (Integer.parseInt(info.getTen_z()) == 9) {
                                    info.setOrder_By(204);
                                } else if (Integer.parseInt(info.getNine_z()) == 8) {
                                    info.setOrder_By(205);
                                } else if (Integer.parseInt(info.getEight_z()) == 7) {
                                    info.setOrder_By(206);
                                } else if (Integer.parseInt(info.getSeven_z()) == 6) {
                                    info.setOrder_By(207);
                                } else if (Integer.parseInt(info.getSix_z()) == 5) {
                                    info.setOrder_By(208);
                                } else
                                    //if (Integer.parseInt(info.getFive_z()) == 4) {
                                    //info.setOrder_By(209);        //5中4
                                    //} else
                                    {
                                    //3类(倒序)
                                    if (new BigDecimal(info.getReturnSevenDays()).compareTo(new BigDecimal(100)) > 0) {
                                        info.setOrder_By(300);
                                    } else {
                                        //4类(倒序)
                                        if (Integer.parseInt(info.getSeven_z()) == 5) {
                                            info.setOrder_By(408);
                                        } else if (Integer.parseInt(info.getEight_z()) == 6) {
                                            info.setOrder_By(407);
                                        } else if (Integer.parseInt(info.getNine_z()) == 7) {
                                            info.setOrder_By(406);
                                        } else if (Integer.parseInt(info.getTen_z()) == 8) {
                                            info.setOrder_By(405);
                                        } else {
                                            //5类(倒序)
                                            if (Integer.parseInt(info.getTen_z()) == 5) {
                                                info.setOrder_By(507);
                                            } else if (Integer.parseInt(info.getTen_z()) == 6) {
                                                info.setOrder_By(506);
                                            } else if (Integer.parseInt(info.getTen_z()) == 7) {
                                                info.setOrder_By(505);
                                            } else if(Integer.parseInt(info.getLzNow()) == 4){
                                                info.setOrder_By(551); //新增4连红
                                            }else {
                                                //6类
                                                if (Integer.parseInt(info.getLzNow()) == 2) {
                                                    info.setOrder_By(603);
                                                } else if (Integer.parseInt(info.getLzNow()) == 3) {
                                                    info.setOrder_By(602);
                                                } else {
                                                    //7类
                                                    if (Integer.parseInt(info.getThree_z()) == 2) {
                                                        info.setOrder_By(703);
                                                    } else if (Integer.parseInt(info.getFour_z()) == 3) {
                                                        info.setOrder_By(702);
                                                    } else if(Integer.parseInt(info.getFive_z()) == 4){
                                                        info.setOrder_By(751); //新增5中4
                                                    }else {
                                                        //8类
                                                        if (Integer.parseInt(info.getLzBig()) >= 8) {
                                                            info.setOrder_By(getOrderBy(Integer.parseInt(info.getLzNow())) + 800);
                                                        } else {
                                                            info.setOrder_By(900);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }


                            //这里修改入库
                            VictoryResult Victory = tbJcVictoryService.queryVictory(String.valueOf(expertResults.get(p).getId()));
                            if (Victory != null) {
                                //已存在，则修改
                                int r = tbJcVictoryService.updateById(info);
                                if (r > 0) {
                                    log.info("专家(id:" + expertResults.get(p).getId() + ",nickName:" + expertResults.get(p).getNickName() + ")趋势计算成功");
                                }
                            } else {
                                //不存在，则添加
                                int r = tbJcVictoryService.insert(info);
                                if (r > 0) {
                                    log.info("新专(id:" + expertResults.get(p).getId() + ",nickName:" + expertResults.get(p).getNickName() + ")家趋势插入成功");
                                }
                            }
                        } else {
                            log.info("专家(id:" + expertResults.get(p).getId() + ",nickName:" + expertResults.get(p).getNickName() + ")无发布方案");
                        }
                        VictoryResult Victory = tbJcVictoryService.queryVictory(String.valueOf(expertResults.get(p).getId()));
                        if (Victory == null) {
                            VictoryInfo info = new VictoryInfo();
                            info.setLzNow(String.valueOf(0));                                  //当前连中
                            info.setF(String.valueOf(0));                                      //已发方案总数
                            info.setZ(String.valueOf(0));                                      //方案命中数
                            info.setzThreeDays(new Double(0));                           //三天命中率
                            info.setzFiveDays(new Double(0));                            //五天命中率
                            info.setzSevenDays(new Double(0));                           //七天命中率
                            info.setTrend("");                                                  //趋势
                            info.setzAll(new Double(0));                                  //全部命中率
                            info.setLzBig(String.valueOf(0));                                   //历史最高连红
                            info.setTen_z(String.valueOf(0));                                   //近10中几
                            info.setNine_z(String.valueOf(0));                                  //近9中几
                            info.setEight_z(String.valueOf(0));                                 //近8中几
                            info.setSeven_z(String.valueOf(0));                                 //近7中几
                            info.setNine_z(String.valueOf(0));                                  //近6中几
                            info.setFive_z(String.valueOf(0));                                  //近5中几
                            info.setFour_z(String.valueOf(0));                                  //近4中几
                            info.setThree_z(String.valueOf(0));                                 //近3中几
                            info.setReturnSevenDays(new Double(0));
                            //七天回报率/七天盈利率
                            info.setReturnAll(new Double(0));                             //全部回报率
                            info.setExpertId(String.valueOf(expertResults.get(p).getId()));
                            info.setYlSevenDays(new Double(0));
                            info.setSix_z("0");
                            info.setOrder_By(1000);

                            //不存在，则添加
                            int r = tbJcVictoryService.insert(info);
                            if (r > 0) {
                                log.info("新专家趋势插入成功");
                            }
                        }
                    } catch (Exception ex) {
                        log.error("处理专家(id:" + expertResults.get(p).getId() + ",nickName:" + expertResults.get(p).getNickName() + ")战绩异常：");
                        ex.printStackTrace();
                    }
                }
            } else {
                log.info("因为没有新结束的方案,所以不需要处理战绩。上次计算战绩时间" + time);
            }
        } catch (Exception ex) {
            log.error("[处理专家战绩异常]");
            ex.printStackTrace();
        }
        log.info("[处理专家战绩结束]" + df2.format(new Date()));
    }

    //转换排序顺序
    private int getOrderBy(Integer value) {
        return 100 - value;
    }
}
