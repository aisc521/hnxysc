package com.zhcdata.jc.quartz.job.redis;

import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.db.model.TbJcVictory;
import com.zhcdata.jc.dto.ExpertInfo;
import com.zhcdata.jc.dto.MatchPlanResult1;
import com.zhcdata.jc.dto.VictoryInfo;
import com.zhcdata.jc.dto.VictoryResult;
import com.zhcdata.jc.service.TbJcExpertService;
import com.zhcdata.jc.service.TbJcMatchService;
import com.zhcdata.jc.service.TbJcVictoryService;
import com.zhcdata.jc.service.TbPlanService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description 处理专家战绩
 * @Author cuishuai
 * @Date 2019/9/20 14:44
 */
@Slf4j
@Component
public class HandleExpertRecordJob implements Job {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private TbJcExpertService tbJcExpertService;

    @Resource
    private TbPlanService tbPlanService;
    @Resource
    private TbJcMatchService tbJcMatchService;
    @Resource
    private TbJcVictoryService tbJcVictoryService;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("[处理专家战绩开启]" + df.format(new Date()));

        try {
            DecimalFormat dfLv = new DecimalFormat("0.00");//格式化小数

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());

            calendar.add(Calendar.DAY_OF_MONTH, -2);
            String timeThree = df.format(calendar.getTime());    //三天

            calendar.add(Calendar.DAY_OF_MONTH, -4);    //五天
            String timeFive = df.format(calendar.getTime());

            calendar.add(Calendar.DAY_OF_MONTH, -6);    //七天
            String timeSeven = df.format(calendar.getTime());

            List<ExpertInfo> expertResults = tbJcExpertService.queryExperts(); //专家列表
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

                    int three = 0;      //三天总数
                    int three_z = 0;    //三天命中

                    int five = 0;       //五天总数
                    int five_z = 0;     //五天命中

                    int seven = 0;      //七天总数
                    int seven_z = 0;    //七天命中

                    String trend = "";  //趋势

                    int z_count = 0;    //命中总数

                    int lh_history = 0; //历史最高连红

                    int jin10z = 0;       //近10中几
                    int jin9z = 0;        //近 9中几
                    int jin8z = 0;        //近 8中几
                    int jin7z = 0;        //近 7中几
                    int jin6z = 0;        //近 6中几
                    int jin5z = 0;        //近 5中几
                    int jin4z = 0;        //近 4中几
                    int jin3z = 0;        //近 3中几

                    int price=0;          //投入金额
                    int dou=0;            //记录双选个数
                    BigDecimal money=new BigDecimal(0); //奖金

                    BigDecimal lastSevenDayPayMoney=new BigDecimal(0);      //近七天投入
                    BigDecimal lastSevenDayReturnMoney=new BigDecimal(0);   //近七天回报

                    BigDecimal lastDayPayMoney=new BigDecimal(0);           //历史投入
                    BigDecimal lastDayReturnMoney=new BigDecimal(0);        //历史回报

                    List<TbJcPlan> planResults = tbPlanService.queryPlanList(String.valueOf(expertResults.get(p).getId()), "0"); //已结束方案
                    if (planResults != null && planResults.size() > 0) {
                        for (int k = 0; k < planResults.size(); k++) {
                            List<MatchPlanResult1> matchlist = tbJcMatchService.queryList1(planResults.get(k).getId());   //当前方案的比赛
                            if (matchlist != null && matchlist.size() > 0) {
                                for (int m = 0; m < matchlist.size(); m++) {
                                    if (matchlist.get(m).getStatus().equals("1")) {
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
                                    if (matchlist.get(m).getDateOfMatch().compareTo(timeThree) > 0) {
                                        three += 1;
                                        if (matchlist.get(m).getStatus().equals("1")) {
                                            three_z += 1;
                                        }
                                    }

                                    //五天
                                    if (matchlist.get(m).getDateOfMatch().compareTo(timeFive) > 0) {
                                        five += 1;
                                        if (planResults.get(k).getStatus().equals("1")) {
                                            five_z += 1;
                                        }
                                    }


                                    //七天
                                    if (matchlist.get(m).getDateOfMatch().compareTo(timeSeven) > 0) {
                                        seven += 1;
                                        if (planResults.get(k).getStatus().equals("1")) {
                                            seven_z += 1;
                                        }
                                    }

                                    //中红 不中黑
                                    if (planResults.get(k).getStatus().equals("1")) {
                                        trend += "红";
                                    } else {
                                        trend += "黑";
                                    }

                                    //全部命中数(率)
                                    if (planResults.get(k).getStatus().equals("1")) {
                                        z_count += 1;
                                    }

                                    //回报率
                                    if (matchlist.get(m).getDateOfMatch().compareTo(timeSeven) > 0) {
                                        String planInfo = matchlist.get(m).getPlanInfo();
                                        if (planInfo.contains("|")) {
                                            String spf = planInfo.split("\\|")[0];
                                            String rqspf = planInfo.split("\\|")[1];

                                            String[] spfs = spf.split(",");
                                            String[] rqspfs = rqspf.split(",");

                                            int a = 0;
                                            for (int p1 = 0; p1 < spfs.length; p1++) {
                                                if (Double.valueOf(spfs[p1]) > 0) {
                                                    a += 1;
                                                }
                                            }

                                            int b = 0;
                                            for (int p2 = 0; p2 < rqspfs.length; p2++) {
                                                if (Double.valueOf(rqspfs[p2]) > 0) {
                                                    b += 1;
                                                }
                                            }

                                            if (a > 1 || b > 1) {
                                                dou += 1;
                                            }
                                        }
                                    }


                                }
                            }
                        }

                        //处理投入金额
                        price=2*2*dou;

                        //处理奖金
                        //处理七日回报率

                        int c = 1;
                        //处理近3 4 5 6 7 8 9 10场 命中场次
                        for (int b = trend.length() - 1; b >= 0; b--) {
                            String v = trend.substring(b, b + 1);
                            if (v.equals("红")) {
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
                        info.setLzNow(String.valueOf(lz));                                              //当前连中
                        info.setF(String.valueOf(zs));                                                  //已发方案总数
                        info.setZ(String.valueOf(z_count));                                             //方案命中数
                        info.setzThreeDays(three==0?"0":dfLv.format((float) three_z / three)); //三天命中率
                        info.setzFiveDays(three==0?"0":dfLv.format((float) five_z / five));    //五天命中率
                        info.setzSevenDays(three==0?"0":dfLv.format((float) seven_z / seven)); //七天命中率
                        info.setTrend(trend);                                                           //趋势
                        info.setzAll(dfLv.format((float) z_count / zs));                       //全部命中率
                        info.setLzBig(String.valueOf(lh_history));                                      //历史最高连红
                        info.setTen_z(String.valueOf(jin10z));                                          //近10中几
                        info.setNine_z(String.valueOf(jin9z));                                          //近9中几
                        info.setEight_z(String.valueOf(jin8z));                                         //近8中几
                        info.setSeven_z(String.valueOf(jin7z));                                         //近7中几
                        info.setNine_z(String.valueOf(jin6z));                                          //近6中几
                        info.setFive_z(String.valueOf(jin5z));                                          //近5中几
                        info.setFour_z(String.valueOf(jin4z));                                          //近4中几
                        info.setThree_z(String.valueOf(jin3z));                                         //近3中几
                        info.setReturnSevenDays("0");                                                   //七天回报率
                        info.setYlSevenDays("0");                                                       //七天盈利率
                        info.setReturnAll("0");                                                         //全部回报率
                        info.setExpertId(String.valueOf(expertResults.get(p).getId()));

                        //这里修改入库
                        VictoryResult Victory = tbJcVictoryService.queryVictory(String.valueOf(expertResults.get(p).getId()));
                        if (Victory != null) {
                            //已存在，则修改
                            int r = tbJcVictoryService.updateById(info);
                            if (r > 0) {
                                log.info("专家趋势计算成功");
                            }
                        } else {
                            //不存在，则添加
                            int r = tbJcVictoryService.insert(info);
                            if (r > 0) {
                                log.info("新专家趋势插入成功");
                            }
                        }
                    }else{
                        VictoryResult Victory = tbJcVictoryService.queryVictory(String.valueOf(expertResults.get(p).getId()));

                        if (Victory == null) {
                            VictoryInfo info = new VictoryInfo();
                            info.setLzNow(String.valueOf(0));                                  //当前连中
                            info.setF(String.valueOf(0));                                      //已发方案总数
                            info.setZ(String.valueOf(0));                                       //方案命中数
                            info.setzThreeDays("0");                                            //三天命中率
                            info.setzFiveDays("0");                                             //五天命中率
                            info.setzSevenDays("0");                                            //七天命中率
                            info.setTrend("0");                                                 //趋势
                            info.setzAll("0");                                                  //全部命中率
                            info.setLzBig(String.valueOf(0));                                   //历史最高连红
                            info.setTen_z(String.valueOf(0));                                   //近10中几
                            info.setNine_z(String.valueOf(0));                                  //近9中几
                            info.setEight_z(String.valueOf(0));                                 //近8中几
                            info.setSeven_z(String.valueOf(0));                                 //近7中几
                            info.setNine_z(String.valueOf(0));                                  //近6中几
                            info.setFive_z(String.valueOf(0));                                  //近5中几
                            info.setFour_z(String.valueOf(0));                                  //近4中几
                            info.setThree_z(String.valueOf(0));                                 //近3中几
                            info.setReturnSevenDays("0");                                       //七天回报率/七天盈利率
                            info.setReturnAll("0");                                             //全部回报率
                            info.setExpertId(String.valueOf(expertResults.get(p).getId()));
                            info.setYlSevenDays("0");
                            info.setSix_z("0");

                            //不存在，则添加
                            int r = tbJcVictoryService.insert(info);
                            if (r > 0) {
                                log.info("新专家趋势插入成功");
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            log.error("[处理专家战绩异常]" + ex + ex.getMessage() + ex.getStackTrace());
            ex.printStackTrace();
        }
        log.info("[处理专家战绩结束]" + df.format(new Date()));
    }
}
