package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.mapper.TbTeamTechStatisticsMapper;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.db.model.TTSInfo;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.TTSRsp;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Configuration
@EnableScheduling
public class TTSJob implements Job {

    @Resource
    ScheduleMapper scheduleMapper;

    @Resource
    TbTeamTechStatisticsMapper tbTeamTechStatisticsMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String url = "http://interface.win007.com/zq/Technic_XML.aspx";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            List<TTSRsp> TTS_list = new QiuTanXmlComm().handleMothodList(url, TTSRsp.class);
            if (TTS_list != null && TTS_list.size() > 0) {
                for (TTSRsp a : TTS_list) {
                    Schedule schedule = scheduleMapper.selectByPrimaryKey(Integer.parseInt(a.getId()));
                    if (schedule != null) {
                        for (int k = 1; k < 3; k++) {
                            TTSInfo info = new TTSInfo();
                            if (k == 1) {
                                info.setTeamid(schedule.getHometeamid());
                            } else if (k == 2) {
                                info.setTeamid(schedule.getGuestteamid());
                            }
                            info.setScheduleid(Integer.parseInt(a.getId()));

                            //6,4,4;45,2,3;11,1,3;3,17,10;4,4,1;34,13,9;8,21,15;14,55%,45%;46,54%,46%;
                            // 9,1,1;16,1,3;43,146,83;44,60,27;
                            String[] strings = a.getTechnicCount().split(";");
                            for (String b : strings) {
                                if (b.split(",")[0].equals(0)) {
                                    info.setKickofffirst(Boolean.valueOf(b.split(",")[k]));     //0.先开球
                                } else if (b.split(",")[0].equals("1")) {
                                    info.setFirstcorner(Boolean.valueOf(b.split(",")[k]));      //1.第一个角球
                                } else if (b.split(",")[0].equals("2")) {
                                    info.setFirstyellow(Boolean.valueOf(b.split(",")[k]));      //2.第一张黄牌
                                } else if (b.split(",")[0].equals("3")) {
                                    info.setShots(Integer.parseInt(b.split(",")[k]));           //3.射门次数
                                } else if (b.split(",")[0].equals("4")) {
                                    info.setTarget(Integer.parseInt(b.split(",")[k]));          //4.射正次数
                                } else if (b.split(",")[0].equals("5")) {
                                    info.setFouls(Integer.parseInt(b.split(",")[k]));           //5.犯规次数
                                } else if (b.split(",")[0].equals("6")) {
                                    info.setCorner(Integer.parseInt(b.split(",")[k]));          //6.角球次数
                                } else if (b.split(",")[0].equals("7")) {
                                    info.setCornerover(Integer.parseInt(b.split(",")[k]));      //7.角球次数(加时)
                                } else if (b.split(",")[0].equals("8")) {
                                    info.setFreekick(Integer.parseInt(b.split(",")[k]));        //8.任意球次数
                                } else if (b.split(",")[0].equals("9")) {
                                    info.setOffside(Integer.parseInt(b.split(",")[k]));         //9.越位次数
                                } else if (b.split(",")[0].equals("10")) {
                                    info.setOwngoal(Integer.parseInt(b.split(",")[k]));         //10.乌龙球数
                                } else if (b.split(",")[0].equals("11")) {
                                    info.setYellow(Integer.parseInt(b.split(",")[k]));          //11.黄牌数
                                } else if (b.split(",")[0].equals("12")) {
                                    info.setYellowover(Integer.parseInt(b.split(",")[k]));      //12.黄牌数(加时)
                                } else if (b.split(",")[0].equals("13")) {
                                    info.setRed(Integer.parseInt(b.split(",")[k]));             //13.红牌数
                                } else if (b.split(",")[0].equals("14")) {
                                    info.setControlpercent(Integer.parseInt(b.split(",")[k].replace("%", "")));  //14.控球时间(控球率)
                                } else if (b.split(",")[0].equals("15")) {
                                    info.setHeader(Integer.parseInt(b.split(",")[k]));          //15.头球
                                } else if (b.split(",")[0].equals("16")) {
                                    info.setSave(Integer.parseInt(b.split(",")[k]));            //16.救球
                                } else if (b.split(",")[0].equals("17")) {
                                    info.setGkpounced(Integer.parseInt(b.split(",")[k]));       //17.守门员出击
                                } else if (b.split(",")[0].equals("18")) {
                                    info.setLostball(Integer.parseInt(b.split(",")[k]));        //18.丟球
                                } else if (b.split(",")[0].equals("19")) {
                                    info.setStealsuc(Integer.parseInt(b.split(",")[k]));        //19.成功抢断
                                } else if (b.split(",")[0].equals("20")) {
                                    info.setHoldup(Integer.parseInt(b.split(",")[k]));          //20.阻截
                                } else if (b.split(",")[0].equals("21")) {
                                    info.setLongpass(Integer.parseInt(b.split(",")[k]));        //21.长传
                                } else if (b.split(",")[0].equals("22")) {
                                    info.setShortpass(Integer.parseInt(b.split(",")[k]));       //22.短传
                                } else if (b.split(",")[0].equals("23")) {
                                    info.setAssists(Integer.parseInt(b.split(",")[k]));         //23.助攻
                                } else if (b.split(",")[0].equals("24")) {
                                    info.setSuccesscross(Integer.parseInt(b.split(",")[k]));    //24.成功传中
                                } else if (b.split(",")[0].equals("25")) {
                                    info.setFirstsubst(Boolean.valueOf(b.split(",")[k]));       //25.第一个换人
                                } else if (b.split(",")[0].equals("26")) {
                                    info.setLastsubst(Boolean.valueOf(b.split(",")[k]));        //26.最后换人
                                } else if (b.split(",")[0].equals("27")) {
                                    info.setFirstoffside(Boolean.valueOf(b.split(",")[k]));     //27.第一个越位
                                } else if (b.split(",")[0].equals("28")) {
                                    info.setLastoffside(Boolean.valueOf(b.split(",")[k]));      //28.最后越位
                                } else if (b.split(",")[0].equals("29")) {
                                    info.setSubst(Integer.parseInt(b.split(",")[k]));           //29.换人数
                                } else if (b.split(",")[0].equals("30")) {
                                    info.setLastcorner(Boolean.valueOf(b.split(",")[k]));       //30.最后角球
                                } else if (b.split(",")[0].equals("31")) {
                                    info.setLastyellow(Boolean.valueOf(b.split(",")[k]));       //31.最后黄牌
                                } else if (b.split(",")[0].equals("32")) {
                                    info.setSubstover(Integer.parseInt(b.split(",")[k]));       //32.换人数(加时)
                                } else if (b.split(",")[0].equals("33")) {
                                    info.setOffsideover(Integer.parseInt(b.split(",")[k]));     //33.越位次数(加时)
                                } else if (b.split(",")[0].equals("34")) {
                                    info.setOfftarget(Integer.parseInt(b.split(",")[k]));       //34.射门不中
                                } else if (b.split(",")[0].equals("35")) {
                                    info.setHitwoodwork(Integer.parseInt(b.split(",")[k]));     //35.中柱
                                } else if (b.split(",")[0].equals("36")) {
                                    info.setHeadersuc(Integer.parseInt(b.split(",")[k]));       //36.头球成功次数
                                } else if (b.split(",")[0].equals("37")) {
                                    info.setBlocked(Integer.parseInt(b.split(",")[k]));         //37.射门被挡
                                } else if (b.split(",")[0].equals("38")) {
                                    info.setTackle(Integer.parseInt(b.split(",")[k]));          //38.铲球
                                } else if (b.split(",")[0].equals("39")) {
                                    info.setDribbles(Integer.parseInt(b.split(",")[k]));        //39.过人次数
                                } else if (b.split(",")[0].equals("40")) {
                                    info.setThrowins(Integer.parseInt(b.split(",")[k]));        //40.界外球
                                } else if (b.split(",")[0].equals("41")) {
                                    info.setPassball(Integer.parseInt(b.split(",")[k]));        //41.传球次数
                                } else if (b.split(",")[0].equals("42")) {
                                    //42.传球成功率
                                } else if (b.split(",")[0].equals("43")) {
                                    //43.进攻次数
                                } else if (b.split(",")[0].equals("44")) {
                                    info.setDangerousatt(Short.parseShort(b.split(",")[k]));    //44.危险进攻次数
                                } else if (b.split(",")[0].equals("45")) {
                                    info.setCornerhalf(Integer.parseInt(b.split(",")[k]));      //45.半场角球
                                } else if (b.split(",")[0].equals("46")) {
                                    info.setControlpercenthalf(Integer.parseInt(b.split(",")[k].replace("%", "")));//46.半场控球
                                }
                            }
                            info.setModifytime(df.format(new Date()));

                            List<TTSInfo> ttsInfos = tbTeamTechStatisticsMapper.queryTTS(info.getScheduleid().toString(), info.getTeamid().toString());
                            if (ttsInfos != null && ttsInfos.size() > 0) {
                                //判断先注销
                                //if(!info.equals(ttsInfos.get(0))) {
                                    info.setId(ttsInfos.get(0).getId());
                                    if (tbTeamTechStatisticsMapper.updateByPrimaryKeySelective(info) > 0) {
                                        log.info("[球队技术统计]修改成功");
                                    } else {
                                        log.info("[球队技术统计]修改失败");
                                    }
                                //}
                            } else {
                                if (tbTeamTechStatisticsMapper.insertSelective(info) > 0) {
                                    log.info("[球队技术统计]保存成功");
                                } else {
                                    log.info("[球队技术统计]保存失败");
                                }
                            }
                        }
                    } else {
                        log.info("未查到球队信息,比赛ScheduleID：" + a.getId());
                    }
                }
            }
        } catch (Exception ex) {
            log.info("球队技术统计处理异常" + ex.getMessage());
        }
    }
}
