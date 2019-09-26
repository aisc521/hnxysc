package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.TbPlayerMapper;
import com.zhcdata.db.model.PlayerInfo;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.PlayerRsp;
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

/**
 * 球员资料
 */
@Configuration
@EnableScheduling
public class PlayerJob implements Job {

    @Resource
    TbPlayerMapper tbPlayerMapper;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String url = "http://interface.win007.com/zq/Player_XML.aspx?day=1";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            List<PlayerRsp> result_list = new QiuTanXmlComm().handleMothodList(url, PlayerRsp.class);
            for (PlayerRsp a : result_list) {
                PlayerInfo info = new PlayerInfo();
                //球员ID
                info.setPlayerid(Integer.parseInt(a.getPlayerID()));
                //球员类型
                if (a.getPlace().contains("主教练")) {
                    info.setKind(Short.valueOf("2"));
                } else if (a.getPlace().contains("教练")) {
                    info.setKind(Short.valueOf("3"));
                } else {
                    info.setKind(Short.valueOf("1"));
                }
                //球员简体短名称
                info.setNameShort(a.getName_J());
                //球员繁体
                info.setNameF(a.getName_F());
                //球员简体名称
                info.setNameJ(a.getName_J());
                //球员英文名
                info.setNameE(a.getName_E());
                //球员英文短名称
                info.setNameEs(a.getName_E());
                //球员生日
                if (!a.getBirthday().equals("")) {
                    info.setBirthday(a.getBirthday());
                }
                //身高
                if (!a.getTallness().equals("")) {
                    info.setTallness(Short.valueOf(a.getTallness()));
                }
                //体重
                if (!a.getWeight().equals("")) {
                    info.setWeight(Short.valueOf(a.getWeight()));
                }
                //国家
                info.setCountry(a.getCountry());
                //图片
                info.setPhoto(a.getPhoto());
                //介绍
                info.setIntroduce(a.getIntroduce());
                //身体状态
                info.setHealth(a.getHealth());
                //修改时间
                info.setModifytime(df.format(new Date()));
                //国家ID
                //info.setCountryid();
                //预计身价
                info.setExpectedvalue(a.getValue());
                //荣誉
                //info.setHonorInfo();
                //合同期限
                if (!a.getEndDateContract().equals("")) {
                    if (a.getEndDateContract().length() < 12) {
                        info.setEnddatecontract(a.getEndDateContract() + " 00:00:00");
                    }
                }
                //惯用脚
                if (a.getFeet().contains("左脚")) {
                    info.setIdiomaticfeet(Integer.valueOf("0"));
                } else if (a.getFeet().contains("右脚")) {
                    info.setIdiomaticfeet(Integer.valueOf("1"));
                } else if (a.getFeet().contains("双脚")) {
                    info.setIdiomaticfeet(Integer.valueOf("2"));
                }

                List<PlayerInfo> list = tbPlayerMapper.queryPlayer(a.getPlayerID());
                if (list != null && list.size() > 0) {
                    //此接口不做数据是否改变的判断,直接修改(该接口一天一次，没关系)
                    if (tbPlayerMapper.updateByPrimaryKeySelective(info) > 0) {
                        LOGGER.info("[球员资料]信息修改成功" + df.format(new Date()));
                    } else {
                        LOGGER.info("[球员资料]信息修改失败" + df.format(new Date()));
                    }
                } else {
                    if (tbPlayerMapper.insertSelective(info) > 0) {
                        LOGGER.info("[球员资料]信息保存成功" + df.format(new Date()));
                    } else {
                        LOGGER.info("[球员资料]信息保存失败" + df.format(new Date()));
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("[球员资料]异常信息：" + ex.getMessage());
        }
    }
}
