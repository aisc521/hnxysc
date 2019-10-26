package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.TbSclassMapper;
import com.zhcdata.db.model.SclassInfo;
import com.zhcdata.db.model.SclassType;
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

                //处理联赛
                SclassType sclassType=new SclassType();
                sclassType.setSclassid(info.getSclassid());
                String fiveMatch="'英超','意甲','德甲','西甲','法甲'";
                String BdJc="'英冠','英甲','英乙','英足总杯','英联杯','英锦赛','英社盾','意乙','意杯','意超杯','西乙','西杯','西超杯','德乙','德国杯','德超杯','法乙','法联杯','法国杯','法超杯','葡超','葡甲','葡杯','葡联杯','葡超杯','荷甲','荷乙','荷兰杯','荷超杯','俄超','俄甲','俄杯','俄超杯','苏超','苏冠','苏联杯','苏总杯','挪超','挪甲','挪杯','挪超杯','瑞典超','瑞典甲','瑞典杯','瑞超杯','比甲','比乙','比利时杯','比超杯','捷甲','捷克杯','捷超杯','土超','土超杯','希腊超','希腊杯','瑞士超','瑞士甲','瑞士杯','以超','以杯','丹麦超','丹麦甲','丹麦杯','奥甲','奥乙','奥地利杯','波兰甲','波兰杯','波超杯','爱超','爱甲','爱联杯','爱足杯','匈甲','匈杯','匈联杯','匈超杯','芬超','芬甲','芬联杯','芬兰杯','罗甲','罗杯','罗超杯','克亚甲','克亚杯','乌超','乌克兰杯','乌超杯','冰岛超','冰岛甲','冰岛杯','冰超杯','巴西甲','巴西乙','巴圣锦标','巴西杯','阿甲','阿乙','阿根廷杯','阿超杯','智利甲','智利杯','智超杯','美职业','美公开赛','墨西联','墨西哥杯','墨超杯','墨冠杯','中协杯','中超杯','日职联','日职乙','日皇杯','日联杯','日超杯','韩K联','韩挑K联','韩足总','澳洲甲','澳足总','欧洲杯','欧冠杯','欧洲预选','欧洲杯','欧罗巴杯','欧国联','超霸杯','欧青U21外','女欧国杯','美洲杯','自由杯','南美预选','南球杯','南美超杯','北美预选','中美洲杯','美冠杯','美金杯','亚洲杯','亚冠杯','亚洲预选','亚预赛','东亚杯','亚青U23','亚运男足','亚运女足','西亚锦','东南锦','女亚杯','东亚女足','非洲杯','非洲预选','非预赛','非国锦标','世界杯','女世界杯','奥运男足','奥运女足','洲际杯','世界杯附','世青杯','世俱杯','国际冠军杯','中国杯','世预赛'";
                if(fiveMatch.contains(info.getNameJ())){
                    sclassType.setType(2);
                }else if(BdJc.contains(info.getNameJ())){
                    sclassType.setType(3);
                }else {
                    sclassType.setType(4);
                }

                List<SclassType> re=tbSclassMapper.querySclassTypeList(String.valueOf(info.getSclassid()));
                if(re!=null&&re.size()>0){
//                    if(tbSclassMapper.updateSclassTypeByPrimaryKeySelective(sclassType)>0){
//                        LOGGER.info("联赛所属类型修改成功");
//                    }else {
//                        LOGGER.info("联赛所属类型修改失败");
//                    }
                }else {
                    if(tbSclassMapper.insertSclassTypeSelective(sclassType)>0){
                        LOGGER.info("联赛所属类型保存成功");
                    }else {
                        LOGGER.info("联赛所属类型保存失败");
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
