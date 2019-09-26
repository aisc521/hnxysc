package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.mapper.TbDetailResultMapper;
import com.zhcdata.db.mapper.TbTeamTechStatisticsMapper;
import com.zhcdata.db.model.DetailResultInfo;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.db.model.TTSInfo;
import com.zhcdata.jc.tools.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Configuration
@EnableScheduling
public class DRTSJob implements Job {

    @Resource
    ScheduleMapper scheduleMapper;

    @Resource
    TbDetailResultMapper tbDetailResultMapper;

    @Resource
    TbTeamTechStatisticsMapper tbTeamTechStatisticsMapper;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //String a="1716564^1^1^90^^^^6894074^^";
        //String b1="1681920^0^3^74^罗伊博阿腾^172289^罗伊博阿腾^6894043^^Roy Boateng";
        //String b2="1673766^0^1^54^^^^6894378^^";
        //int j=a.split("\\^").length;
        //int j1=b1.split("\\^").length;
        //int j2=b2.split("\\^").length;

        String url = "http://interface.win007.com/zq/detail.aspx?type=new";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            String xml = HttpUtils.getHtmlResult(url);
            SAXReader saxReader = new SAXReader();
            org.dom4j.Document docDom4j = saxReader.read(new ByteArrayInputStream(xml.getBytes("utf-8")));
            org.dom4j.Element root = docDom4j.getRootElement();
            List<Element> childElements = root.elements();
            for (org.dom4j.Element one : childElements) {
                if (one.getName().equals("event")) {
                    //比赛的详细事件
                    int j=0;
                    List<Element> twos = one.elements();
                    for (org.dom4j.Element two : twos) {
                        j++;
                        System.out.println(twos.size());
                        System.out.println(j);
                        System.out.println(two.getName());
                        System.out.println(two.getText());
                        String[] values = two.getText().split("\\^");

                        if (values[0].contains("1673766")) {
                            String sdfa = "";
                        }

                        //赛程ID^主客队标志^事件类型^时间^球员名字^球名ID^简体球员名^记录ID(不重复）^助攻球员ID
                        //     0         1        2    3       4      5         6              7          8
                        DetailResultInfo info = new DetailResultInfo();
                        info.setId(Integer.valueOf(values[7]));                 //详细结果 ID
                        info.setScheduleid(Integer.parseInt(values[0]));        //赛程 ID
                        info.setHappentime(Short.parseShort(values[3]));        //发生时间

                        //if(info.getId().toString().contains("6920165")){
                        //   String sdsd="";
                        //}

                        Schedule scheduleInfo = scheduleMapper.selectByPrimaryKey(info.getScheduleid());
                        if (scheduleInfo != null) {                             //球队 ID
                            if (values[1].equals("1")) {
                                info.setTeamid(scheduleInfo.getHometeamid());
                            } else {
                                info.setTeamid(scheduleInfo.getGuestteamid());
                            }
                        }
                        info.setPlayername(values[4]);                          //球员名称
                        if (values[5].contains(",")) {
                            info.setPlayerid(Integer.parseInt(values[5].split(",")[0]));      //球员 ID
                            info.setPlayeridIn(Integer.parseInt(values[5].split(",")[1]));
                        } else {
                            if (values[5].length() > 0) {
                                info.setPlayerid(Integer.parseInt(values[5]));      //球员 ID
                            }
                        }

                        info.setKind(Short.valueOf(values[2]));                 //类型
                        info.setModifytime(dfs.format(new Date()));              //修改时间
                        info.setPlayernameJ(values[6]);                         //球员简体名
                        if (values.length > 8) {
                            if (values[8].length() > 0) {
                                info.setPlayeridIn(Integer.valueOf(values[8]));      //球员
                            }
                        }
                        List<DetailResultInfo> queryDetailResult = tbDetailResultMapper.queryDetailResult(info.getScheduleid().toString(), info.getId().toString());
                        if (queryDetailResult == null || queryDetailResult.size() == 0) {
                            if (tbDetailResultMapper.insertSelective(info) > 0) {
                                log.info("比赛详细事件接口插入成功");
                            } else {
                                log.info("比赛详细事件接口插入失败");
                            }
                        } else {
                            if (tbDetailResultMapper.updateByPrimaryKeySelective(info) > 0) {
                                log.info("比赛详细事件接口修改成功");
                            } else {
                                log.info("比赛详细事件接口修改失败");
                            }
                        }
                    }

                } else if (one.getName().equals("technic")) {
                    //技术统计
                    List<Element> twos = one.elements();
                    for (org.dom4j.Element two : twos) {
                        System.out.println(two.getName());
                        System.out.println(two.getText());

                        Schedule schedule = scheduleMapper.selectByPrimaryKey(Integer.parseInt(two.getText().split("\\^")[0]));
                        if (schedule != null) {
                            for (int k = 1; k < 3; k++) {
                                TTSInfo info = new TTSInfo();
                                if (k == 1) {
                                    info.setTeamid(schedule.getHometeamid());
                                } else if (k == 2) {
                                    info.setTeamid(schedule.getGuestteamid());
                                }
                                info.setScheduleid(Integer.parseInt(two.getText().split("\\^")[0]));

                                //6,4,4;45,2,3;11,1,3;3,17,10;4,4,1;34,13,9;8,21,15;14,55%,45%;46,54%,46%;
                                // 9,1,1;16,1,3;43,146,83;44,60,27;
                                String[] strings = two.getText().split("\\^")[1].split(";");
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
                                    log.info("球队技术统计已存在，进行修改");
                                    info.setId(ttsInfos.get(0).getId());
                                    if (tbTeamTechStatisticsMapper.updateByPrimaryKeySelective(info) > 0) {
                                        log.info("球队技术统计修改成功");
                                    } else {
                                        log.info("球队技术统计修改失败");
                                    }
                                } else {
                                    log.info("球队技术统计已存在，进行插入");
                                    if (tbTeamTechStatisticsMapper.insertSelective(info) > 0) {
                                        log.info("球队技术统计保存成功");
                                    } else {
                                        log.info("球队技术统计保存失败");
                                    }
                                }
                            }
                        } else {
                            log.info("未查到球队信息,比赛ScheduleID：" + two.getText().split("\\^")[0]);
                        }

                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("当天比赛的详细事件&技术统计异常" + ex.getMessage());
        }
    }
}
