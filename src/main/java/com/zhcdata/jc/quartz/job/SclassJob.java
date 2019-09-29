package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.TbSclassMapper;
import com.zhcdata.db.model.SclassInfo;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.SclassInfoRsp;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@EnableScheduling
public class SclassJob implements Job {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    TbSclassMapper tbSclassMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String url = "http://interface.win007.com/zq/League_XML.aspx";
        try {
            List<SclassInfoRsp> result_list = new QiuTanXmlComm().handleMothodList(url, SclassInfoRsp.class);
            int i = 0;
            for (SclassInfoRsp a : result_list) {
                i++;
                List<SclassInfo> list = tbSclassMapper.querySClass(a.getId());
                SclassInfo info = new SclassInfo();
                System.out.println("共" + result_list.size() + "第" + i + "ID" + a.getId());
//                if(a.getId().contains("807")){
//                    String sdf="";
//                }
                info.setSclassid(Integer.valueOf(a.getId()));       //联赛ID
                info.setColor(a.getColor());                        //颜色
                info.setNameJ(a.getGb());                           //简体
                info.setNameF(a.getBig());                          //繁体
                info.setNameE(a.getEn());                           //英文
                info.setNameJs(a.getGb_short());                    //简体短
                info.setNameFs(a.getBig_short());                   //繁体短
                info.setNameEs(a.getEn_short());                    //英文短
                info.setKind(Short.valueOf(a.getType()));           //联赛、杯赛
                info.setMode(Short.valueOf(a.getType()));           //联赛按轮 杯赛按组
                if (a.getSum_round().length() > 0) {
                    info.setCountRound(Short.valueOf(a.getSum_round()));//仅对分轮的赛程有效
                }
                if (a.getCurr_round().length() > 0) {
                    info.setCurrRound(Short.valueOf(a.getCurr_round()));//正在进行的轮次
                }
                info.setCurrMatchseason(a.getCurr_matchSeason());   //当前赛季，如 2004-2005、2005
                info.setSclassPic(a.getLogo());                     //联赛的标志
                //info.setIfstop();                               //1：非休赛 2：休赛
                //info.setSclassSequence();                       //赛事排名，后台手动排序
                //info.setBfIfdisp();                             //1：显示 0：不显示
                //info.setBfSimplyDisp();                         //1：一级 0：普通
                //info.setIfhavesub();                            //1：有 0：没有
                //info.setBeginseason();                          //
                //info.setCountGroup();                           //找不到相关使用
                //info.setSclassRule();                           //赛制，所订的规则
                //info.setInfoid();                               //联赛信息(SclassInfo)表 ID
                if (list != null && list.size() > 0) {
                    if (!info.equals(info)) {
                        if (tbSclassMapper.updateByPrimaryKeySelective(info) > 0) {
                            LOGGER.info("联赛信息修改成功");
                        } else {
                            LOGGER.info("联赛信息修改失败");
                        }
                    }
                } else {
                    if (tbSclassMapper.insertSelective(info) > 0) {
                        LOGGER.info("联赛信息保存成功");
                    } else {
                        LOGGER.info("联赛信息保存失败");
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }
}
