package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.*;
import com.zhcdata.db.model.*;
import com.zhcdata.jc.tools.HttpUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;
import javax.sql.rowset.spi.SyncResolver;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class ScoreJob implements Job {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    ScheduleMapper scheduleMapper;

    @Resource
    TbSubSclassMapper tbSubSclassMapper;

    @Resource
    TbScoreMapper tbScoreMapper;

    @Resource
    TbSclassMapper tbSclassMapper;

    @Resource
    CupMatchMapper cupMatchMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        try {
            String s = getSE().split(",")[0];
            String e = getSE().split(",")[1];
            String url = "http://interface.win007.com/zq/jifen.aspx";   //"?ID=1&subID=418";

            String SclassID = "";

            //List<SclassInfo> sclassInfos=tbSclassMapper.querySClassList(df.format(new Date()));
            //List<Schedule> schedules = scheduleMapper.selectPastAndFutureNoEnd(s, e, "-1");

            List<SclassInfo> sclassInfos=tbSclassMapper.querySClassIDList(s,e);
            if (sclassInfos != null && sclassInfos.size() > 0) {
                for (int i = 0; i < sclassInfos.size(); i++) {
                    Thread.sleep(5000);
                    System.out.println("共"+sclassInfos.size()+"第"+i);
                    //联赛ID
                    SclassID = sclassInfos.get(i).getSclassid().toString();
                    List<SubSclassInfo> subList = tbSubSclassMapper.querySubSclassID(SclassID,df.format(new Date()));
                    if (subList != null && subList.size() > 0) {
                        for (int k = 0; k < subList.size(); k++) {
                            Thread.sleep(5000);
                            analysis(url + "?ID=" + SclassID + "&subID=" + subList.get(k).getSubsclassid().toString(), SclassID, subList.get(k).getSubsclassid().toString());
                        }
                    } else {
                        //没有子联,就是杯赛
                        analysis(url + "?ID=" + SclassID, SclassID, null);
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("[球队积分排名]异常" + ex);
            ex.printStackTrace();
        }

        String sd = "";
    }

    private void analysis(String url, String SclassID, String SubSclassID) {
        try {
            String re = HttpUtils.getHtmlResult(url);
            System.out.println(url);
            System.out.println(re);
            if (re.contains("tips")) {
                analysisTips(re,SclassID,SubSclassID);
                return;
            }

            //int pp=re.split(";").length;
            if (re.contains(";")) {
                if (re.contains("arrTeam") && re.contains("totalScore") && re.contains("homeScore") && re.contains("guestScore")) {
                    List<ScoreInfo> list = new ArrayList<>();

//                    String arrTeam = re.split(";")[0];              //球队信息
//                    arrTeam = arrTeam.split("=")[1];
//                    for (int k = 0; k < arrTeam.split("],").length; k++) {
//                        String arrTeamInfo = arrTeam.split("],")[k].replaceAll("\\[", "");
//
//                        ScoreInfo info = new ScoreInfo();
//                        info.setTeamid(Integer.valueOf(arrTeamInfo.split(",")[0].replaceAll(" ", "")));
//                        info.setSclassid(Integer.valueOf(SclassID));
//                        if (SubSclassID != null && SubSclassID != "") {
//                            info.setSubsclassid(Integer.valueOf(SubSclassID));
//                        }
//                        list.add(info);
//                    }

                    //String scoreColor = re.split(";")[1];                 //升降级

                    List<ScoreInfo> totalScoreList = new ArrayList<>();
                    String totalScore = re.split(";")[2];            //总积分
                    totalScore = totalScore.split("=")[1];
                    for (int k = 0; k < totalScore.split("],").length; k++) {
                        String scoreColorInfo = totalScore.split("],")[k].replaceAll("\\[", "");
                        ScoreInfo info = new ScoreInfo();
                        info.setTeamid(Integer.valueOf(scoreColorInfo.split(",")[2]));      //球队ID
                        info.setTotalOrder(Integer.valueOf(scoreColorInfo.split(",")[1]));  //总场排名
                        totalScoreList.add(info);
                    }

                    List<ScoreInfo> homeScoreList = new ArrayList<>();
                    String homeScore = re.split(";")[3];            //主场积分
                    homeScore = homeScore.split("=")[1];
                    for (int k = 0; k < homeScore.split("],").length; k++) {
                        String homeScoreInfo = homeScore.split("],")[k].replaceAll("\\[", "");
                        String[] homeScoreInfos=homeScoreInfo.split(",");
                        ScoreInfo info = new ScoreInfo();
                        info.setTeamid(Integer.valueOf(homeScoreInfos[1]));                                      //球队
                        info.setWinScore(Integer.valueOf(homeScoreInfos[3]));                                    //胜
                        info.setFlatScore(Integer.valueOf(homeScoreInfos[4]));                                   //平
                        info.setFailScore(Integer.valueOf(homeScoreInfos[5]));                                   //负
                        info.setTotalHomescore(Integer.valueOf(homeScoreInfos[6]));                              //得
                        info.setTotalGuestscore(Integer.valueOf(homeScoreInfos[7]));                             //失
                        info.setHomeorguest(1);                                                                  //主
                        info.setOrderby(Integer.valueOf(homeScoreInfos[0].replace(" ",""))); //排名
                        info.setScore(Integer.valueOf(homeScoreInfos[14].replace("]]",""))); //积分
                        homeScoreList.add(info);
                    }

                    List<ScoreInfo> guestScoreList = new ArrayList<>();
                    String guestScore = re.split(";")[4];           //客场积分
                    guestScore = guestScore.split("=")[1];
                    for (int k = 0; k < guestScore.split("],").length; k++) {
                        String guestScoreInfo = guestScore.split("],")[k].replaceAll("\\[", "");
                        String[] guestScoreInfos=guestScoreInfo.split(",");
                        ScoreInfo info = new ScoreInfo();
                        info.setTeamid(Integer.valueOf(guestScoreInfos[1]));                                     //球队
                        info.setWinScore(Integer.valueOf(guestScoreInfos[3]));                                   //胜
                        info.setFlatScore(Integer.valueOf(guestScoreInfos[4]));                                  //平
                        info.setFailScore(Integer.valueOf(guestScoreInfos[5]));                                  //负
                        info.setTotalHomescore(Integer.valueOf(guestScoreInfos[6]));                             //得
                        info.setTotalGuestscore(Integer.valueOf(guestScoreInfos[7]));                            //失
                        info.setHomeorguest(0);                                                                  //客
                        info.setOrderby(Integer.valueOf(guestScoreInfos[0].replace(" ","")));//排名
                        info.setScore(Integer.valueOf(guestScoreInfos[14].replace("]]","")));//积分
                        guestScoreList.add(info);
                    }

                    //String halfScore = re.split(";")[5];                      //半场积分
                    //String homeHalfScore = re.split(";")[6];                  //主场半场积分
                    //String guestHalfScore = re.split(";")[7];                 //客场半场积分
                    String Season = re.split(";")[8];                     //赛季
                    Season = Season.split("=")[1];
                    Season = Season.replaceAll("'", "");


                    if(totalScoreList.size()==homeScoreList.size()&&homeScoreList.size()==guestScoreList.size()){
                        //主队
                        for(int m=0;m<homeScoreList.size();m++){
                            ScoreInfo info = homeScoreList.get(m);
                            for(int j=0;j<totalScoreList.size();j++){
                                if(info.getTeamid().equals(totalScoreList.get(j).getTeamid())){
                                    info.setTotalOrder(totalScoreList.get(j).getTotalOrder());
                                    continue;
                                }
                            }
                            list.add(info);
                        }

                        //客队
                        for(int n=0;n<guestScoreList.size();n++){
                            ScoreInfo info = guestScoreList.get(n);
                            for(int k=0;k<totalScoreList.size();k++){
                                if(info.getTeamid().equals(totalScoreList.get(k).getTeamid())){
                                    info.setTotalOrder(totalScoreList.get(k).getTotalOrder());
                                    continue;
                                }
                            }
                            list.add(info);
                        }
                    }

                    for (int p = 0; p < list.size(); p++) {
                        //System.out.println(Season);
                        //System.out.println(Season.length());
                        ScoreInfo info = list.get(p);
                        info.setMatchseason(Season);
                        dealTable(info);
                    }
                    String end = "";
                }
            }
        } catch (Exception ex) {
            LOGGER.error("[球队积分排名]异常" + ex);
            ex.printStackTrace();
        }
    }

    private void analysisTips(String re,String SclassID,String subSclassID) {
        try {
            String Season ="";

            String[] htmlList=re.split(";");
            for(int t=0;t<htmlList.length;t++){
                if(htmlList[t].contains("Season")){
                    Season = htmlList[t].split("=")[1].replaceAll("'", "");
                }else if(htmlList[t].contains("Score")){
                    String Score = htmlList[t];                                     //积分信息
                    Score = Score.split("=")[1].replaceAll("'", "");
                    String[] strings = Score.split("\\|");

                    CupMatch cupMatch=new CupMatch();
                    cupMatch.setSclassid(Integer.valueOf(SclassID));                //联赛ID
                    cupMatch.setMatchseason(Season);                                //赛季
                    String strConten="";
                    for (int i = 0; i < strings.length; i++) {
                        if(strings[i].length()>0) {
                            if (strings[i].contains("积分")) {
                                strConten += strings[i] + "|";
                                cupMatch.setGrouping(strings[i].substring(0, 1));        //分组
                            } else if (strings[i].contains("^")) {
                                strConten += strings[i] + "|";
                            }

                            //下一项是否结束，并且下一项数组不能越界

                            if (i== strings.length-1 || strings[i + 1].contains("积分")) {
                                cupMatch.setStrcontent(strConten);
                                List<CupMatch> selectList = cupMatchMapper.selectList(String.valueOf(cupMatch.getSclassid()), cupMatch.getGrouping(), cupMatch.getMatchseason());
                                if (selectList != null && selectList.size() > 0) {
                                    CupMatch cupMatch1=selectList.get(0);
                                    cupMatch1.setStrcontent(strConten);
                                    if (cupMatchMapper.updateByPrimaryKeySelective(cupMatch1) > 0) {
                                        LOGGER.info("[球队积分排名CupMatch]修改成功");
                                    } else {
                                        LOGGER.info("[球队积分排名CupMatch]修改失败");
                                    }
                                } else {
                                    if (cupMatchMapper.insertSelective(cupMatch) > 0) {
                                        LOGGER.info("[球队积分排名CupMatch]添加成功");
                                    } else {
                                        LOGGER.info("[球队积分排名CupMatch]添加失败");
                                    }
                                }

                                strConten=""; //重置
                            }
                        }

                        //简单的判断下，当前串是不是球队积分串
//                        if (strings[i].split("\\^").length > 8) {
//                            String[] stringsScore = strings[i].split("\\^");
//                            ScoreInfo info = new ScoreInfo();
//                            info.setTeamid(Integer.parseInt(stringsScore[1]));
//                            info.setSclassid(Integer.parseInt(SclassID));
//                            info.setWinScore(Integer.parseInt(stringsScore[7]));
//                            info.setFlatScore(Integer.parseInt(stringsScore[8]));
//                            info.setFailScore(Integer.parseInt(stringsScore[9]));
//                            info.setTotalHomescore(Integer.parseInt(stringsScore[10]));
//                            info.setTotalGuestscore(Integer.parseInt(stringsScore[11]));
//                            if(subSclassID!=null) {
//                                info.setSubsclassid(Integer.parseInt(subSclassID));
//                            }
//                            //info.setHomeorguest(Integer.parseInt(stringsScore[1]));
//                            info.setMatchseason(Season);
//                            dealTable(info);                              //入库
//                        }
                    }
                }
            }

            //排名0^球队ID1^颜色值2^球队名繁3^英4^简5^赛6^胜7^平8^负9^得分10^失分11^净胜分12^积分13^14
            //Season = Season.split("=")[1].replaceAll("'", "");

            //String tips = re.split(";")[1];                 //字段说明
            //String GroupName = re.split(";")[3];            //分组说明
        } catch (Exception ex) {
            LOGGER.error("[球队积分排名]异常" + ex);
            ex.printStackTrace();
        }
    }

    public void dealTable(ScoreInfo info) {
        try {
            List<ScoreInfo> existingTeam = tbScoreMapper.queryScore(info);
            if (existingTeam == null || existingTeam.size() == 0) {
                if (tbScoreMapper.insertSelective(info) > 0) {
                    LOGGER.info("[球队积分排名]入库成功" + info.getTeamid());
                } else {
                    LOGGER.info("[球队积分排名]入库失败" + info.getTeamid());
                }
            } else {
                info.setId(existingTeam.get(0).getId());
                if (tbScoreMapper.updateByPrimaryKeySelective(info) > 0) {
                    LOGGER.info("[球队积分排名]修改成功" + info.getTeamid());
                } else {
                    LOGGER.info("[球队积分排名]修改失败" + info.getTeamid());
                }
            }
        } catch (Exception ex) {
            LOGGER.error("[球队积分排名]异常" + ex);
            ex.printStackTrace();
        }
    }

    public String getSE() {
        String startDate = "";
        String endDate = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dt = df.format(new Date());
        String hour = dt.substring(11, 13);
        if (Integer.parseInt(hour) > 10) {
            Date date = new Date();//获取当前时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            startDate = df.format(calendar.getTime()).substring(0, 10) + " 11:00:00";

            calendar.add(Calendar.DAY_OF_MONTH, 1);
            endDate = df.format(calendar.getTime()).substring(0, 10) + " 11:00:00";

            //过11点,过了11:00点则不显示昨天的赛果只显示今天的对阵
        } else {
            Date date = new Date();//获取当前时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            endDate = df.format(calendar.getTime()).substring(0, 10) + " 11:00:00";

            calendar.add(Calendar.DAY_OF_MONTH, -1);
            startDate = df.format(calendar.getTime()).substring(0, 10) + " 11:00:00";
            //未过11点,每天中午11:00前都显示昨日比赛结果
        }
        return startDate + "," + endDate;
    }
}
