package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.mapper.StandardDetailMapper;
import com.zhcdata.db.mapper.StandardMapper;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.db.model.StandardDetail;
import com.zhcdata.jc.service.ManyHandicapOddsChangeService;
import com.zhcdata.jc.tools.BeanUtils;
import com.zhcdata.jc.xml.rsp.MoreHandicapOddsLisAlltRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

//import static com.zhcdata.jc.quartz.job.Odds.FlagInfo.MATCH_START_TIME;

/**
 * CopyRight (c)1999-2019 : zhcw.com
 * Project : jc-new-server
 * Create Date : 2019/9/26 10:16
 * JDK version : JDK1.8
 * Comments :  21.多盘口赔率：30秒内变化赔率接口 <欧赔 胜平负变化数据:>
 *
 * @author : 高阳
 * @version : 0.0.1
 */
@Slf4j
@Service("changeOddsHandleServiceImpl")
public class ChangeOddsHandleServiceImpl implements ManyHandicapOddsChangeService {

    @Resource
    private StandardDetailMapper standardDetailMapper;

    @Resource
    private StandardMapper standardMapper;

    @Resource
    private ScheduleMapper scheduleMapper;

    @Override
    public void changeHandle(MoreHandicapOddsLisAlltRsp rsp) {
        if (rsp==null || rsp.getO()==null || rsp.getO().getH()==null || rsp.getO().getH().size()==0)
            return;
        List<String> cah = rsp.getO().getH();
        if (cah == null || cah.size() < 1) {
            log.error("21多盘口赔率变化: 欧赔（让球盘）变化数据总条数:{}", " 没有可更新的数据");
            return;
        }
        log.error("21多盘口赔率变化: 欧赔（让球盘）变化数据总条数:{}", cah.size());
        for (int i = 0; i < cah.size(); i++) {
            try {
                String[] item = cah.get(i).split(",");
                //Long time = MATCH_START_TIME.get(item[0]);
                //if (time==null || time<1){
                //    Schedule schedule = scheduleMapper.selectByPrimaryKey(Integer.parseInt(item[0]));
                //    if(schedule!=null){
                //        time = schedule.getMatchtime().getTime();
                //        MATCH_START_TIME.put(schedule.getScheduleid().toString(), schedule.getMatchtime().getTime());
                //        if (MATCH_START_TIME.size()>500)
                //            MATCH_START_TIME.remove(MATCH_START_TIME.entrySet().iterator().next().getKey());
                //    }
                //}
                //if (time!=null && time < System.currentTimeMillis()){
                //    log.error("21多盘口赔率变化: 欧赔（让球盘）比赛已经开始，比赛ID:{}", item[0]);
                //    continue;
                //}
                if (!item[8].equals("3")){
                    singleHandicap(item);
                }
            } catch (Exception e) {
                log.error("亚盘赔率异常:", e);
            }
        }
    }

    //单盘口操作
    public void singleHandicap(String item[]) {
        //存到单盘口
        StandardDetail xml = BeanUtils.parseStandardDetail(item);

        //查询此比赛最新的一条赔率
        StandardDetail standardDetail = standardDetailMapper.selectByMidAndCpy(item[0], item[1]);
        if (standardDetail == null || standardDetail.getOddsid() == null) {
            return;
        }
        if (standardDetail.getId()==null || !standardDetail.oddsEquals(xml) && xml.getModifytime().getTime() > standardDetail.getModifytime().getTime()) {
            //入数据库\
            xml.setOddsid(standardDetail.getOddsid());
            int inch = standardDetailMapper.insertSelective(xml);
            standardMapper.updateOddsByOddsId(xml.getOddsid(),xml.getHomewin(),xml.getStandoff(),xml.getGuestwin(),xml.getModifytime());
            if (inch > 0) {
                log.error("21多盘口赔率变化: 欧赔（让球盘）单盘口 接口数据:{} 入库成功", item);
            }
        }
    }
}
