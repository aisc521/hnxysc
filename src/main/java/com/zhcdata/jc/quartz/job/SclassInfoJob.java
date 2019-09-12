package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.TbSclassInfoMapper;
import com.zhcdata.db.model.TbSclassInfo;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.SclassInfoRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class SclassInfoJob {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    TbSclassInfoMapper tbSclassMapper;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Async
    @Scheduled(cron = "21 2 18 ? * *")
    public void work() {
        try {
            String url = "http://interface.win007.com/zq/League_XML.aspx";
            List<SclassInfoRsp> result_list = new QiuTanXmlComm().handleMothodList(url, SclassInfoRsp.class);
            for (SclassInfoRsp a : result_list) {
                List<TbSclassInfo> list = tbSclassMapper.querySclassInfo(a.getCountry());
                if (list == null || list.size() < 1) {
                    TbSclassInfo info = new TbSclassInfo();
                    info.setNamecn(a.getCountry());                     //国家球队简体名
                    //info.setNameen("");                               //国家球队繁体名
                    info.setNamefn(a.getCountryEn());                   //国家球队英文名
                    info.setFlagpic(a.getCountryLogo());                //国家队图标
                    //info.setInfoorder("");                            //页面显示时作排序用，每个州内部排序，如资料库首页
                    info.setInfoType(Integer.valueOf(a.getAreaID()));   //1 欧洲 2 美洲 3 亚洲 4 大洋洲 5 非洲
                    info.setModifytime(df.format(new Date()));          //修改时间
                    //info.setAllorder("");                             //所有国家的排序
                    if (tbSclassMapper.insertSelective(info) > 0) {
                        LOGGER.info(a.getCountry() + "入库成功!");
                    } else {
                        LOGGER.info("入库失败!");
                    }
                } else {
                    LOGGER.info(a.getCountry() + "已经存在，不再入库");
                }
            }
        } catch (Exception ex) {
            LOGGER.error(ex.toString());
        }
    }
}
