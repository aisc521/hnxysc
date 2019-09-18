package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.mapper.TbScoreMapper;
import com.zhcdata.db.mapper.TbSubSclassMapper;
import com.zhcdata.db.model.Schedule;
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

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            String s = getSE().split(",")[0];
            String e = getSE().split(",")[1];

            String url = "http://interface.win007.com/zq/jifen.aspx";   //"?ID=1&subID=418";

            //查询当天的比赛信息
            List<Schedule> schedules = scheduleMapper.selectPastAndFutureNoEnd(s, e, -1);
            if (schedules != null && schedules.size() > 0) {
                for (int i = 0; i < schedules.size(); i++) {
                    //联赛ID
                    String SclassID = schedules.get(i).getSclassid().toString();
                    List<SubSclassInfo> subList = tbSubSclassMapper.querySubSclassID(SclassID);
                    if (subList != null && subList.size() > 0) {
                        //子联赛ID
                        String SubSclassID = subList.get(0).getSubsclassid().toString();

                        String re = HttpUtils.getHtmlResult(url + "?ID=" + SclassID + "&subID=" + SubSclassID);
                        int pp=re.split(";").length;
                        if(re.contains(";")) {
                            List<ScoreInfo> list = new ArrayList<>();

                            String arrTeam = re.split(";")[0];              //球队信息
                            arrTeam = arrTeam.split("=")[1];
                            for (int k = 0; k < arrTeam.split("],").length; k++) {
                                String arrTeamInfo = arrTeam.split("],")[k].replaceAll("\\[", "");

                                ScoreInfo info = new ScoreInfo();
                                info.setTeamid(Integer.valueOf(arrTeamInfo.split(",")[0].replaceAll(" ", "")));
                                info.setSclassid(Integer.valueOf(SclassID));
                                info.setSubsclassid(Integer.valueOf(SubSclassID));
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
                            for (int k = 0; k < homeScoreList.size(); k++) {
                                ScoreInfo info = homeScoreList.get(k);
                                info.setMatchseason(Season);
                                //SeasonList.add(info);
                                List<ScoreInfo> existingTeam = new ArrayList<>();
                                existingTeam = tbScoreMapper.queryScore(String.valueOf(info.getSclassid()), String.valueOf(info.getSubsclassid()), String.valueOf(info.getHomeorguest()), String.valueOf(info.getTeamid()), Season);
                                if (existingTeam == null || existingTeam.size() == 0) {
                                    if (tbScoreMapper.insertSelective(info) > 0) {
                                        LOGGER.info("球队积分排名入库成功" + info.getTeamid());
                                    } else {
                                        LOGGER.info("球队积分排名入库失败" + info.getTeamid());
                                    }
                                } else {
                                    LOGGER.info("处理球队积分已存在" + info.getTeamid());
                                    ScoreInfo updateInfo = new ScoreInfo();

                                    boolean remark = false;
                                    if (existingTeam.get(0).getWinScore() != info.getWinScore()) {
                                        updateInfo.setWinScore(info.getWinScore());
                                        remark = true;
                                    }

                                    if (existingTeam.get(0).getFlatScore() != info.getFlatScore()) {
                                        updateInfo.setFlatScore(info.getFlatScore());
                                        remark = true;
                                    }

                                    if (existingTeam.get(0).getFailScore() != info.getFailScore()) {
                                        updateInfo.setFailScore(info.getFailScore());
                                        remark = true;
                                    }

                                    if (existingTeam.get(0).getTotalHomescore() != info.getTotalHomescore()) {
                                        updateInfo.setTotalHomescore(info.getTotalHomescore());
                                        remark = true;
                                    }

                                    if (existingTeam.get(0).getTotalGuestscore() != info.getTotalGuestscore()) {
                                        updateInfo.setTotalGuestscore(info.getTotalGuestscore());
                                        remark = true;
                                    }

                                    if (existingTeam.get(0).getDeduct() != info.getDeduct()) {
                                        updateInfo.setDeduct(info.getDeduct());
                                        remark = true;
                                    }

                                    if (existingTeam.get(0).getCause() != info.getCause()) {
                                        updateInfo.setCause(info.getCause());
                                        remark = true;
                                    }
                                    updateInfo.setId(existingTeam.get(0).getId());

                                    if(remark) {
                                        if (tbScoreMapper.updateByPrimaryKeySelective(updateInfo) > 0) {
                                            LOGGER.info("球队积分排名修改成功" + updateInfo.getTeamid());
                                        } else {
                                            LOGGER.info("球队积分排名修改失败" + updateInfo.getTeamid());
                                        }
                                    }

                                }
                            }
                            String end="";

                        }
                    }

                }
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        String sd = "";
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
