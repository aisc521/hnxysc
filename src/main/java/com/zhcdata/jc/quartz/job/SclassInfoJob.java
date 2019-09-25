package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.TbSclassInfoMapper;
import com.zhcdata.db.model.TbSclassInfo;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class SclassInfoJob implements Job {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    TbSclassInfoMapper tbSclassInfoMapper;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            String url = "http://interface.win007.com/zq/League_XML.aspx";
            List<SclassInfoRsp> result_list = new QiuTanXmlComm().handleMothodList(url, SclassInfoRsp.class);
            for (SclassInfoRsp a : result_list) {
                TbSclassInfo info = new TbSclassInfo();
                info.setNamecn(a.getCountry());                     //国家球队简体名
                //info.setNameen("");                               //国家球队繁体名
                info.setNamefn(a.getCountryEn());                   //国家球队英文名
                info.setFlagpic(a.getCountryLogo());                //国家队图标
                //info.setInfoorder("");                            //页面显示时作排序用，每个州内部排序，如资料库首页
                info.setInfoType(Integer.valueOf(a.getAreaID()));   //1 欧洲 2 美洲 3 亚洲 4 大洋洲 5 非洲
                info.setModifytime(df.format(new Date()));          //修改时间
                //info.setAllorder("");                             //所有国家的排序
                List<TbSclassInfo> list = tbSclassInfoMapper.querySclassInfo(a.getCountry());
                if (list == null || list.size() < 1) {
                    if (tbSclassInfoMapper.insertSelective(info) > 0) {
                        LOGGER.info(a.getCountry() + "[杯赛联赛资料]入库成功!");
                    } else {
                        LOGGER.info("[杯赛联赛资料]入库失败!");
                    }
                } else {
                    info.setInfoid(list.get(0).getInfoid());
                    if (tbSclassInfoMapper.updateByPrimaryKeySelective(info) > 0) {
                        LOGGER.info(a.getCountry() + "[杯赛联赛资料]修改成功!");
                    } else {
                        LOGGER.info("[杯赛联赛资料]修改失败!");
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error(ex.toString());
        }
    }
}
