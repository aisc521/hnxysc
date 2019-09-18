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
                List<PlayerInfo> list = tbPlayerMapper.queryPlayer(a.getPlayerID());
                if (list != null && list.size() > 0) {
                    LOGGER.info("球员信息已经存在,判断是否需要修改");
                    PlayerInfo info=new PlayerInfo();
                    if(!a.getPlayerID().equals(list.get(0).getPlayerid())){
                        info.setPlayerid(Integer.parseInt(a.getPlayerID()));
                    }
                    //球员类型
                    short type=-1;
                    if (a.getPlace().contains("主教练")) {
                        type=2;
                    } else if (a.getPlace().contains("教练")) {
                        type=3;
                    } else {
                        type=1;
                    }
                    if(type!=list.get(0).getKind()){
                        info.setKind(type);
                    }
                    //if(!a.getName_J().equals(list.get(0).getNameShort())){
                    //    info.setNameShort(a.getName_J());
                    //}
                    if(!a.getName_F().equals(list.get(0).getNameF())){
                        info.setNameF(a.getName_F());
                    }
                    if(!a.getName_J().equals(list.get(0).getNameJ())){
                        info.setNameJ(a.getName_J());
                    }
                    if(!a.getName_E().equals(list.get(0).getNameE())){
                        info.setNameE(a.getName_E());
                    }
                    //if(!a.getName_E().equals(list.get(0).getNameEs())){
                    //    info.setNameEs(a.getName_E());
                    //}

                    String newBirthday="";
                    if(a.getBirthday()!=""&&a.getBirthday().contains(" ")&&a.getBirthday().contains("/")) {
                        newBirthday+=a.getBirthday().split(" ")[0].split("/")[0];
                        if(a.getBirthday().split(" ")[0].split("/")[1].length()==1) {
                            newBirthday += "0" + a.getBirthday().split(" ")[0].split("/")[1];
                        }else {
                            newBirthday += a.getBirthday().split(" ")[0].split("/")[1];
                        }

                        if(a.getBirthday().split(" ")[0].split("/")[2].length()==1){
                            newBirthday+="0"+a.getBirthday().split(" ")[0].split("/")[2];
                        }else {
                            newBirthday += a.getBirthday().split(" ")[0].split("/")[2];
                        }
                    }

                    String oldBirthday="";
                    if(list.get(0).getBirthday()!=""&&list.get(0).getBirthday().contains(" ")&&list.get(0).getBirthday().contains("-")) {
                        oldBirthday += list.get(0).getBirthday().split(" ")[0].split("-")[0];
                        if (list.get(0).getBirthday().split(" ")[0].split("-")[1].length() == 1) {
                            oldBirthday += "0" + list.get(0).getBirthday().split(" ")[0].split("-")[1];
                        } else {
                            oldBirthday += list.get(0).getBirthday().split(" ")[0].split("-")[1];
                        }

                        if (list.get(0).getBirthday().split(" ")[0].split("-")[2].length() == 1) {
                            oldBirthday += "0" + list.get(0).getBirthday().split(" ")[0].split("-")[2];
                        } else {
                            oldBirthday += list.get(0).getBirthday().split(" ")[0].split("-")[2];
                        }
                    }

                    if(!newBirthday.equals(oldBirthday)){
                        info.setBirthday(a.getBirthday());
                    }
                    if(!a.getTallness().equals(list.get(0).getTallness().toString())){
                        info.setTallness(Short.valueOf(a.getTallness()));
                    }
                    if(!a.getWeight().equals(list.get(0).getWeight().toString())){
                        info.setWeight(Short.valueOf(a.getWeight()));
                    }
                    if(!a.getCountry().replace(" ","").equals(list.get(0).getCountry())){
                        info.setCountry(a.getCountry());
                    }
                    if(!a.getPhoto().equals(list.get(0).getPhoto())){
                        info.setPhoto(a.getPhoto());
                    }
                    if(!a.getIntroduce().equals(list.get(0).getIntroduce())){
                        info.setIntroduce(a.getIntroduce());
                    }
                    if(!a.getHealth().equals(list.get(0).getHealth())){
                        info.setHealth(a.getHealth());
                    }
                    if(!a.getValue().equals(list.get(0).getExpectedvalue())){
                        info.setExpectedvalue(a.getValue());
                    }
                    if(a.getEndDateContract().length()>0) {
                        if (!list.get(0).getEnddatecontract().contains(a.getEndDateContract())) {
                            info.setEnddatecontract(a.getEndDateContract());
                        }
                    }

                    int feetType=-1;
                    if(a.getFeet().contains("左")){
                        feetType=0;
                    }else if(a.getFeet().contains("右脚")){
                        feetType=1;
                    }if(a.getFeet().contains("双脚")){
                        feetType=2;
                    }

                    if(feetType!=-1){
                        if(list.get(0).getIdiomaticfeet()==null){
                            info.setIdiomaticfeet(feetType);
                        }else {
                            if (feetType != list.get(0).getIdiomaticfeet()) {
                                info.setIdiomaticfeet(feetType);
                            }
                        }
                    }

                    if(info.getBirthday()!=null||
                            info.getIdiomaticfeet()!=null|| info.getCountry()!=null||
                            info.getNameShort()!=null||info.getEnddatecontract()!=null||
                            info.getExpectedvalue()!=null|| info.getHealth()!=null||
                            info.getIntroduce()!=null||info.getKind()!=null||
                            info.getNameE()!=null||info.getNameEs()!=null||
                            info.getNameF()!=null|| info.getNameJ()!=null||
                            info.getPhoto()!=null||info.getTallness()!=null||
                            info.getWeight()!=null
                            ){
                        if (tbPlayerMapper.updateByPrimaryKeySelective(info) > 0) {
                            LOGGER.info("球员信息保存成功");
                        }else {
                            LOGGER.info("球员信息保存失败");
                        }
                    }
                } else {
                    LOGGER.info("球员信息不存在："+a.getPlayerID()+","+a.getName_E());
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
                    if(!a.getBirthday().equals("")) {
                        info.setBirthday(a.getBirthday());
                    }
                    //身高
                    if(!a.getTallness().equals("")) {
                        info.setTallness(Short.valueOf(a.getTallness()));
                    }
                    //体重
                    if(!a.getWeight().equals("")) {
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
                    if(!a.getEndDateContract().contains("")){
                        info.setEnddatecontract(a.getEndDateContract());
                    }
                    //惯用脚
                    if(a.getFeet().contains("左脚")){
                        info.setIdiomaticfeet(Integer.valueOf("0"));
                    }else if(a.getFeet().contains("右脚")){
                        info.setIdiomaticfeet(Integer.valueOf("1"));
                    }if(a.getFeet().contains("双脚")){
                        info.setIdiomaticfeet(Integer.valueOf("2"));
                    }
                    //国家ID
                    //info.setCountry2id();
                    //?
                    //info.setHotsortnumber();
                    if (tbPlayerMapper.insertSelective(info) > 0) {
                        LOGGER.info("球员信息保存成功");
                    }else {
                        LOGGER.info("球员信息保存失败");
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("异常信息：" + ex.getMessage());
        }

    }
}
