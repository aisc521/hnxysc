package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.mapper.TbSclassMapper;
import com.zhcdata.db.mapper.TbScoreMapper;
import com.zhcdata.db.mapper.TbSubSclassMapper;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.db.model.SclassInfo;
import com.zhcdata.db.model.ScoreInfo;
import com.zhcdata.db.model.SubSclassInfo;
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

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        try {
            String s = getSE().split(",")[0];
            String e = getSE().split(",")[1];
            String url = "http://interface.win007.com/zq/jifen.aspx";   //"?ID=1&subID=418";

            String SclassID = "";

            List<SclassInfo> sclassInfos=tbSclassMapper.querySClassList(df.format(new Date()));

            //List<Schedule> schedules = scheduleMapper.selectPastAndFutureNoEnd(s, e, "-1");
            if (sclassInfos != null && sclassInfos.size() > 0) {
                for (int i = 0; i < sclassInfos.size(); i++) {
                    System.out.println("共"+sclassInfos.size()+"第"+i);
                    //联赛ID
                    SclassID = sclassInfos.get(i).getSclassid().toString();
                    List<SubSclassInfo> subList = tbSubSclassMapper.querySubSclassID(SclassID,df.format(new Date()));
                    if (subList != null && subList.size() > 0) {
                        for (int k = 0; k < subList.size(); k++) {
                            analysis(url + "?ID=" + SclassID + "&subID=" + subList.get(k).getSubsclassid().toString(), SclassID, subList.get(k).getSubsclassid().toString());
                        }
                    } else {
                        //没有子联,就是杯赛
                        analysis(url + "?ID=" + SclassID, SclassID, null);
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.info("[球队积分排名]异常" + ex);
        }

        String sd = "";
    }

    private void analysis(String url, String SclassID, String SubSclassID) {
        try {
            String re = HttpUtils.getHtmlResult(url);
            System.out.println(url);
            if (re.contains("tips")) {
                analysisTips(re,SclassID,SubSclassID);
                return;
            }

            //int pp=re.split(";").length;
            if (re.contains(";")) {
                if (re.contains("arrTeam") && re.contains("totalScore") && re.contains("homeScore") && re.contains("guestScore")) {
                    List<ScoreInfo> list = new ArrayList<>();

                    String arrTeam = re.split(";")[0];              //球队信息
                    arrTeam = arrTeam.split("=")[1];
                    for (int k = 0; k < arrTeam.split("],").length; k++) {
                        String arrTeamInfo = arrTeam.split("],")[k].replaceAll("\\[", "");

                        ScoreInfo info = new ScoreInfo();
                        info.setTeamid(Integer.valueOf(arrTeamInfo.split(",")[0].replaceAll(" ", "")));
                        info.setSclassid(Integer.valueOf(SclassID));
                        if (SubSclassID != null && SubSclassID != "") {
                            info.setSubsclassid(Integer.valueOf(SubSclassID));
                        }
                        list.add(info);
                    }

                    //String scoreColor = re.split(";")[1];                 //升降级

                    List<ScoreInfo> totalScoreList = new ArrayList<>();
                    String totalScore = re.split(";")[2];            //总积分
                    totalScore = totalScore.split("=")[1];
                    for (int k = 0; k < totalScore.split("],").length; k++) {
                        String scoreColorInfo = totalScore.split("],")[k].replaceAll("\\[", "");
                        ScoreInfo info = list.get(k);
                        //info.setWinScore(Integer.valueOf(scoreColorInfo.split(",")[5]));
                        //info.setFlatScore(Integer.valueOf(scoreColorInfo.split(",")[6]));
                        //info.setFailScore(Integer.valueOf(scoreColorInfo.split(",")[7]));
                        //info.setTotalHomescore(Integer.valueOf(scoreColorInfo.split(",")[8]));
                        //info.setTotalGuestscore(Integer.valueOf(scoreColorInfo.split(",")[9]));
                        info.setRedcard(Integer.valueOf(scoreColorInfo.split(",")[3]));
                        info.setDeduct(Integer.valueOf(scoreColorInfo.split(",")[17]));
                        info.setCause(scoreColorInfo.split(",")[18]);
                        totalScoreList.add(info);
                    }

                    List<ScoreInfo> homeScoreList = new ArrayList<>();
                    String homeScore = re.split(";")[3];            //主场积分
                    homeScore = homeScore.split("=")[1];
                    for (int k = 0; k < homeScore.split("],").length; k++) {
                        String homeScoreInfo = homeScore.split("],")[k].replaceAll("\\[", "");
                        ScoreInfo info = list.get(k);
                        info.setWinScore(Integer.valueOf(homeScoreInfo.split(",")[2]));
                        info.setFlatScore(Integer.valueOf(homeScoreInfo.split(",")[3]));
                        info.setFailScore(Integer.valueOf(homeScoreInfo.split(",")[4]));
                        info.setTotalHomescore(Integer.valueOf(homeScoreInfo.split(",")[5]));
                        info.setTotalGuestscore(Integer.valueOf(homeScoreInfo.split(",")[6]));
                        info.setHomeorguest(1);    //主
                        homeScoreList.add(info);
                    }

                    //List<ScoreInfo> guestScoreList = new ArrayList<>();
                    String guestScore = re.split(";")[4];           //客场积分
                    guestScore = guestScore.split("=")[1];
                    for (int k = 0; k < guestScore.split("],").length; k++) {
                        String guestScoreInfo = guestScore.split("],")[k].replaceAll("\\[", "");
                        ScoreInfo info = list.get(k);
                        info.setWinScore(Integer.valueOf(guestScoreInfo.split(",")[2]));
                        info.setFlatScore(Integer.valueOf(guestScoreInfo.split(",")[3]));
                        info.setFailScore(Integer.valueOf(guestScoreInfo.split(",")[4]));
                        info.setTotalHomescore(Integer.valueOf(guestScoreInfo.split(",")[5]));
                        info.setTotalGuestscore(Integer.valueOf(guestScoreInfo.split(",")[6]));
                        info.setHomeorguest(0);     //客
                        homeScoreList.add(info);
                    }

                    //String halfScore = re.split(";")[5];                      //半场积分
                    //String homeHalfScore = re.split(";")[6];                  //主场半场积分
                    //String guestHalfScore = re.split(";")[7];                 //客场半场积分

                    //List<ScoreInfo> SeasonList = new ArrayList<>();
                    String Season = re.split(";")[8];                     //赛季
                    Season = Season.split("=")[1];
                    Season = Season.replaceAll("'", "");
                    for (int k = 0; k < homeScoreList.size(); k++) {
                        //System.out.println(Season);
                        //System.out.println(Season.length());
                        ScoreInfo info = homeScoreList.get(k);
                        info.setMatchseason(Season);
                        dealTable(info);
                    }
                    String end = "";
                }
            }
        } catch (Exception ex) {
            LOGGER.info("[球队积分排名]异常" + ex);
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
                    for (int i = 0; i < strings.length; i++) {
                        //简单的判断下，当前串是不是球队积分串
                        if (strings[i].split("\\^").length > 8) {
                            String[] stringsScore = strings[i].split("\\^");
                            ScoreInfo info = new ScoreInfo();
                            info.setTeamid(Integer.parseInt(stringsScore[1]));
                            info.setSclassid(Integer.parseInt(SclassID));
                            info.setWinScore(Integer.parseInt(stringsScore[7]));
                            info.setFlatScore(Integer.parseInt(stringsScore[8]));
                            info.setFailScore(Integer.parseInt(stringsScore[9]));
                            info.setTotalHomescore(Integer.parseInt(stringsScore[10]));
                            info.setTotalGuestscore(Integer.parseInt(stringsScore[11]));
                            if(subSclassID!=null) {
                                info.setSubsclassid(Integer.parseInt(subSclassID));
                            }
                            //info.setHomeorguest(Integer.parseInt(stringsScore[1]));
                            info.setMatchseason(Season);
                            dealTable(info);                              //入库
                        }
                    }
                }
            }

            //排名0^球队ID1^颜色值2^球队名繁3^英4^简5^赛6^胜7^平8^负9^得分10^失分11^净胜分12^积分13^14
            //Season = Season.split("=")[1].replaceAll("'", "");

            //String tips = re.split(";")[1];                 //字段说明
            //String GroupName = re.split(";")[3];            //分组说明
        } catch (Exception ex) {
            LOGGER.info("[球队积分排名]异常" + ex);
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
                //if (!info.equals(existingTeam.get(0))) {
                info.setId(existingTeam.get(0).getId());
                if (tbScoreMapper.updateByPrimaryKeySelective(info) > 0) {
                    LOGGER.info("[球队积分排名]修改成功" + info.getTeamid());
                } else {
                    LOGGER.info("[球队积分排名]修改失败" + info.getTeamid());
                }
            }
        } catch (Exception ex) {
            LOGGER.info("[球队积分排名]异常" + ex);
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
