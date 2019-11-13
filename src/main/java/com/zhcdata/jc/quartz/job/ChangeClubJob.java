package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.ExchangeClubRecordMapper;
import com.zhcdata.db.model.ClubRecordInfo;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.clubRsp;
import lombok.extern.slf4j.Slf4j;
import org.omg.PortableInterceptor.INACTIVE;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Configuration
@EnableScheduling
public class ChangeClubJob implements Job {
    @Resource
    ExchangeClubRecordMapper exchangeClubRecordMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String url = "http://interface.win007.com/zq/Transfer.aspx?day=10";
        try{
            List<clubRsp> result_list = new QiuTanXmlComm().handleMothodList(url, clubRsp.class);
            for (clubRsp a : result_list) {
                ClubRecordInfo info=new ClubRecordInfo();
                info.setId(Integer.valueOf(a.getID()));
                info.setPlayerid(Integer.valueOf(a.getPlayerID()));
                info.setTransfertime(dateFormat.parse(a.getTransferTime()));
                if(!a.getEndTime().equals("")) {
                    info.setEndtime(dateFormat.parse(a.getEndTime().replaceAll("/","-")));
                }
                info.setTeam(a.getTeam());
                info.setTeamid(Integer.parseInt(a.getTeamID()));
                info.setTeamnow(a.getTeamNow());
                info.setTeamnowid(Integer.parseInt(a.getTeamNowID()));
                if(!a.getMoney().replaceAll(" ","").equals("")) {
                    info.setMoney(Long.valueOf(a.getMoney().replaceAll(" ","")));
                }
                if(!a.getPlace().equals("")) {
                    info.setPlace(a.getPlace());
                }
                info.setZhSeason(a.getZH_Season());
                info.setType(a.getType());

                List<ClubRecordInfo> list=exchangeClubRecordMapper.selectByPrimaryKey(info.getId());
                if(list!=null&&list.size()>0){
                    if(exchangeClubRecordMapper.updateByPrimaryKeySelective(info)>0){
                        log.error("[五大联赛转会记录]修改成功"+info.getId());
                    }else {
                        log.error("[五大联赛转会记录]无需修改");
                    }
                }else {
                    if(exchangeClubRecordMapper.insertSelective(info)>0){
                        log.error("[五大联赛转会记录]插入成功"+info.getId());
                    }else {
                        log.error("[五大联赛转会记录]插入失败");
                    }
                }
            }
        }catch (Exception ex){
            log.error("[五大联赛转会记录]" + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
