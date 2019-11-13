package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.WorldRankingMapper;
import com.zhcdata.db.model.WorldRankInfo;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.clubRsp;
import com.zhcdata.jc.xml.rsp.rankRsp;
import lombok.extern.slf4j.Slf4j;
import org.omg.PortableInterceptor.INACTIVE;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Configuration
@EnableScheduling
public class WorldRankJob implements Job {
    @Resource
    WorldRankingMapper worldRankingMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        String url = "http://interface.win007.com/zq/fifa.aspx";
        try {
            List<rankRsp> result_list = new QiuTanXmlComm().handleMothodList(url, rankRsp.class);
            for (rankRsp a : result_list) {
                WorldRankInfo info = new WorldRankInfo();
                info.setType(a.getType());
                info.setName(a.getName().replaceAll(" ", ""));
                info.setTeamid(Integer.parseInt(a.getTeamID()));
                info.setArea(a.getArea());
                info.setRank(Integer.parseInt(a.getRank()));
                info.setChgrank(Integer.parseInt(a.getChgRank()));
                info.setScore(Integer.parseInt(a.getScore()));
                info.setChgscore(Integer.parseInt(a.getChgScore()));
                info.setUpdate1(a.getUpdate());

                List<WorldRankInfo> list = worldRankingMapper.selectByTeamID(info.getTeamid(), info.getType());
                if (list != null && list.size() > 0) {
                    if (worldRankingMapper.updateByTeamID(info) > 0) {
                        log.error("[世界排名]修改成功" + a.getTeamID() + "," + a.getType());
                    } else {
                        log.error("[世界排名]无需修改" + a.getTeamID() + "," + a.getType());
                    }
                } else {
                    if (worldRankingMapper.insertSelective(info) > 0) {
                        log.error("[世界排名]插入成功" + a.getTeamID() + "," + a.getType());
                    } else {
                        log.error("[世界排名]插入失败" + a.getTeamID() + "," + a.getType());
                    }
                }
            }
        } catch (Exception ex) {
            log.error("[世界排名]" + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
