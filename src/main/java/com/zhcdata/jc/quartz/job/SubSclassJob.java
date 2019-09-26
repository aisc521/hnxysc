package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.TbSubSclassMapper;
import com.zhcdata.db.model.SubSclassInfo;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.SubSclassRsp;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

@Configuration
@EnableScheduling
public class SubSclassJob implements Job {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    TbSubSclassMapper tbSubSclassMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String url = "http://interface.win007.com/zq/SubLeague_XML.aspx";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            List<SubSclassRsp> result_list = new QiuTanXmlComm().handleMothodList(url, SubSclassRsp.class);
            for (SubSclassRsp a : result_list) {

                SubSclassInfo info = new SubSclassInfo();
                info.setSubsclassid(Integer.valueOf(a.getId()));                    //ID 号
                info.setSclassid(Integer.valueOf(a.getSubID()));                    //联赛 ID
                info.setIshavescore(Boolean.valueOf(a.getIsHaveScore()));           //1 有，0 无。在统计积分中，只统计有积分的子联赛
                if(a.getCurr_round().length()>0) {
                    info.setCurrRound(Integer.valueOf(a.getCurr_round()));          //当前轮数
                }
                if(a.getSum_round().length()>0) {
                    info.setCountRound(Integer.valueOf(a.getSum_round()));          //联赛的总轮数
                }
                info.setIscurrentsclass(Boolean.valueOf(a.getIsCurrentSclass()));   //是否当前子联赛（估计是用来作标识用）
                info.setSubsclassname(a.getName());                                 //子联赛名
                info.setSubnameJs(a.getName_big5());                                //子联赛缩略名
                info.setIncludeseason(a.getIncludeSeason());                        //包含的了赛季
                info.setIszu(Boolean.valueOf(a.getIsZu()));                         //是否分局数显示

                List<SubSclassInfo> list = tbSubSclassMapper.querySubSclass(a.getId());
                if (list != null && list.size() > 0) {
                    if(tbSubSclassMapper.updateByPrimaryKeySelective(info)>0){
                        LOGGER.info(a.getSubID() + "[子联赛]修改数据库成功");
                    }else {
                        LOGGER.info(a.getSubID() + "[子联赛]修改数据库失败");
                    }
                } else {
                    if (tbSubSclassMapper.insertSelective(info) > 0) {
                        LOGGER.info(a.getSubID() + "[子联赛]记录数据库成功");
                    } else {
                        LOGGER.info(a.getSubID() + "[子联赛]记录数据库失败");
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("[子联赛]资料异常" + ex);
        }

    }
}
