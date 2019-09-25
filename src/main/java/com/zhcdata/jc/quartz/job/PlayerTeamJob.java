package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.TbPlayerInTeamMapper;
import com.zhcdata.db.mapper.TbTeamMapper;
import com.zhcdata.db.model.PlayerInTeamInfo;
import com.zhcdata.db.model.TeamInfo;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.PlayerRsp;
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
public class PlayerTeamJob implements Job {

    @Resource
    TbTeamMapper tbTeamMapper;

    @Resource
    TbPlayerInTeamMapper tbPlayerInTeamMapper;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String url = "http://interface.win007.com/zq/Player_XML.aspx?day=1";
        try {
            List<PlayerRsp> result_list = new QiuTanXmlComm().handleMothodList(url, PlayerRsp.class);
            for (PlayerRsp a : result_list) {
                PlayerInTeamInfo info=new PlayerInTeamInfo();
                info.setId(Integer.valueOf(a.getId()));                 //记录 ID
                info.setPlayerid(Integer.valueOf(a.getPlayerID()));     //球员 ID
                info.setPlayername(a.getName_J());                      //球员姓名
                info.setTeamid(Integer.valueOf(a.getTeamID()));         //效力球队
                List<TeamInfo> teamInfos=tbTeamMapper.queryTeam(info.getTeamid().toString());
                if(teamInfos!=null&&teamInfos.size()>0) {
                    info.setTeamname(teamInfos.get(0).getNameJ());      //球队名
                }
                info.setNumber(a.getNumber());                          //球衣号
                info.setPlace(a.getPlace());                            //位置
                //info.setScore();                                      //入球数
                //info.setStarttime();                                  //开始效力时间
                //info.setEndtime();                                    //结束效力时间
                info.setModifytime(df.format(new Date()));              //修改时间
                List<PlayerInTeamInfo> list=tbPlayerInTeamMapper.queryPlayerInTeam(info.getId().toString());
                if(list==null||list.size()==0){
                    if(tbPlayerInTeamMapper.insertSelective(info)>0){
                        log.info("[球员所属球队]插入成功");
                    }else {
                        log.info("[球员所属球队]插入失败");
                    }
                }else {
                    if (tbPlayerInTeamMapper.updateByPrimaryKeySelective(info) > 0) {
                        log.info("[球员所属球队]修改成功");
                    } else {
                        log.info("[球员所属球队]修改失败");
                    }
                }
            }
        }catch (Exception ex){
            System.out.println("球员所属球队异常：" + ex.getMessage());
        }
    }
}
