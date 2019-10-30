package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.TbPlayerTechStatisticsMapper;
import com.zhcdata.db.model.PTSInfo;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.PTSMatchRsp;
import com.zhcdata.jc.xml.rsp.PTSRsp;
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
import java.util.List;

@Configuration
@EnableScheduling
public class PTSJob implements Job {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    TbPlayerTechStatisticsMapper tbPlayerTechStatisticsMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String url = "http://interface.win007.com/zq/PlayDetail.aspx";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            List<PTSMatchRsp> match_list = new QiuTanXmlComm().handleMothodList(url, PTSMatchRsp.class);
            if (match_list != null && match_list.size() > 0) {
                for (int i = 0; i < match_list.size(); i++) {
                    Thread.sleep(15000);
                    List<PTSRsp> result_list = new QiuTanXmlComm().handleMothodList(url + "?ID=" + match_list.get(i).getScheduleID(), PTSRsp.class);
                    if (result_list != null && result_list.size() > 0) {
                        for (int p = 0; p < result_list.size(); p++) {
                            PTSInfo ptsInfo = new PTSInfo();
                            ptsInfo.setScheduleid(Integer.parseInt(match_list.get(i).getScheduleID()));     //赛程
                            ptsInfo.setTeamid(Integer.parseInt(result_list.get(p).getTeamID()));            //球队 ID
                            ptsInfo.setPlayerid(Integer.parseInt(result_list.get(p).getID()));              //球员表(Player)的 ID
                            ptsInfo.setPlayername(result_list.get(p).getPlayerName());                      //球员名
                            ptsInfo.setRating(Float.parseFloat(result_list.get(p).getRating()));            //评分
                            ptsInfo.setShots(Integer.parseInt(result_list.get(p).getShots()));              //射门次数
                            ptsInfo.setShotstarget(Integer.parseInt(result_list.get(p).getShotsTarget()));  //射正次数
                            ptsInfo.setKeypass(Integer.parseInt(result_list.get(p).getKeyPass()));          //关键传球次数
                            ptsInfo.setTotalpass(Integer.parseInt(result_list.get(p).getTotalPass()));      //总传球次数
                            ptsInfo.setAccuratepass(Integer.parseInt(result_list.get(p).getAccuratePass()));//传球成功次数
                            ptsInfo.setAerialwon(Integer.parseInt(result_list.get(p).getAerialWon()));      //争顶成功次数
                            ptsInfo.setTouches(Integer.parseInt(result_list.get(p).getTouches()));          //身体接触次数
                            ptsInfo.setTackles(Integer.parseInt(result_list.get(p).getTackles()));          //铲断次数
                            ptsInfo.setInterception(Integer.parseInt(result_list.get(p).getInterception()));//拦截
                            ptsInfo.setClearances(Integer.parseInt(result_list.get(p).getClearances()));    //解围
                            ptsInfo.setClearancewon(Integer.parseInt(result_list.get(p).getLongBallWon())); //有效解围
                            ptsInfo.setShotsblocked(Integer.parseInt(result_list.get(p).getShotsBlocked())); //封堵
                            ptsInfo.setOffsideprovoked(Integer.parseInt(result_list.get(p).getOffsideProvoked()));   //制造越位
                            ptsInfo.setFouls(Integer.parseInt(result_list.get(p).getFouls()));                       //犯规
                            ptsInfo.setDribbleswon(Integer.parseInt(result_list.get(p).getDribblesWon()));           //带球摆脱
                            ptsInfo.setWasfouled(Integer.parseInt(result_list.get(p).getWasFouled()));               //被犯规
                            ptsInfo.setDispossessed(Integer.parseInt(result_list.get(p).getDispossessed()));         //偷球
                            ptsInfo.setTurnover(Integer.parseInt(result_list.get(p).getTurnOver()));                 //失误
                            ptsInfo.setOffsides(Integer.parseInt(result_list.get(p).getOffsides()));                 //越位次数
                            ptsInfo.setCrossnum(Integer.parseInt(result_list.get(p).getCrossNum()));                 //横传次数
                            ptsInfo.setCrosswon(Integer.parseInt(result_list.get(p).getCrossWon()));                 //精准横传
                            ptsInfo.setLongballs(Integer.parseInt(result_list.get(p).getLongBall()));                //长传
                            ptsInfo.setLongballswon(Integer.parseInt(result_list.get(p).getLongBallWon()));          //精准长传
                            ptsInfo.setThroughball(Integer.parseInt(result_list.get(p).getThroughBall()));           //直塞
                            ptsInfo.setThroughballwon(Integer.parseInt(result_list.get(p).getThroughBallWon()));     //精准直塞
                            //ptsInfo.setPenaltyprovoked(Integer.parseInt(result_list.get(p).get));                  //制造点球
                            //ptsInfo.setPenaltytotal(Integer.parseInt(result_list.get(p).get));                                                            //点球
                            ptsInfo.setPenaltygoals(Integer.parseInt(result_list.get(p).getPenaltyGoals()));         //点球进球
                            //ptsInfo.setNotpenaltygoals(Integer.parseInt(result_list.get(p).get));                  //射门进球
                            ptsInfo.setAssist(Integer.parseInt(result_list.get(p).getAssist()));                     //助攻
                            //ptsInfo.setSecondYellow(Integer.parseInt(result_list.get(p).get));                     //第二张黄牌
                            ptsInfo.setYellow(Integer.parseInt(result_list.get(p).getYellow()));                     //黄牌
                            ptsInfo.setRed(Integer.parseInt(result_list.get(p).getRed()));                           //红牌
                            //ptsInfo.setShotonpost(Integer.parseInt(result_list.get(p).get));                       //击中门框
                            //ptsInfo.setClearanceoffline(Integer.parseInt(result_list.get(p).get));                 //解围出界
                            //ptsInfo.setPenaltysave(Integer.parseInt(result_list.get(p).get);                       //扑出点球
                            //ptsInfo.setErrorleadtogoal(Integer.parseInt(result_list.get(p).get));                  //失误导致失球
                            //ptsInfo.setLastmantackle(Integer.parseInt(result_list.get(p).get));                    //最后防守球员
                            //ptsInfo.setLastmancontest(Integer.parseInt(result_list.get(p).get));                   //最后运球
                            //ptsInfo.setOwngoals(Integer.parseInt(result_list.get(p).get));                         //乌龙球
                            //ptsInfo.setIsbest(Integer.parseInt(result_list.get(p).get));                           //本场最佳球员

                            List<PTSInfo> list = new ArrayList<>();
                            list = tbPlayerTechStatisticsMapper.queryPTS(String.valueOf(ptsInfo.getScheduleid()), String.valueOf(ptsInfo.getTeamid()), String.valueOf(ptsInfo.getPlayerid()));
                            if (list == null || list.size() == 0) {
                                if (tbPlayerTechStatisticsMapper.insertSelective(ptsInfo) > 0) {
                                    LOGGER.info("[球员技术统计表]保存成功!");
                                } else {
                                    LOGGER.info("[球员技术统计表]保存失败");
                                }
                            } else {
                                ptsInfo.setId(list.get(0).getId());
                                if (tbPlayerTechStatisticsMapper.updateByPrimaryKeySelective(ptsInfo) > 0) {
                                    LOGGER.info("[球员技术统计表]修改成功!");
                                } else {
                                    LOGGER.info("[球员技术统计表]修改失败");
                                }
                            }
                        }

                    } else {
                        LOGGER.info("赛事ID:" + match_list.get(i).getScheduleID() + "无球员统计信息");
                    }

                }
            }
        } catch (Exception ex) {
            LOGGER.error("球员技术统计表处理异常" + ex);
            ex.printStackTrace();
        }
    }
}
