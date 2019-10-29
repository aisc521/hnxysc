package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.*;
import com.zhcdata.db.model.LetGoalhalfDetail;
import com.zhcdata.db.model.MultiLetGoalhalfDetail;
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
 * Create Date : 2019/9/25 10:49
 * JDK version : JDK1.8
 * Comments : 21.多盘口赔率：30秒内变化赔率接口 <半场亚赔（让球盘）变化数据:>
 *
 * @author : 高阳
 * @version : 0.0.1
 */
@Slf4j
@Service("changeHalfHandicapHandleServiceImpl")
public class ChangeHalfHandicapHandleServiceImpl implements ManyHandicapOddsChangeService {

    @Resource
    private LetGoalhalfDetailMapper letGoalhalfDetailMapper;

    @Resource
    private LetGoalhalfMapper letGoalhalfMapper;

    @Resource
    private MultiLetGoalhalfMapper multiLetGoalhalfMapper;

    @Resource
    private MultiLetGoalhalfDetailMapper multiLetGoalhalfDetailMapper;

    @Resource
    private ScheduleMapper scheduleMapper;

    @Override
    public void changeHandle(MoreHandicapOddsLisAlltRsp rsp) {
        //比赛ID,公司ID,即时盘口,主队即时赔率,客队即时赔率,是否封盘1,是否走地,盘口序号,变盘时间,是否封盘2
        //比赛ID,公司ID,即时盘口,主队即时赔率,客队即时赔率,是否走地,盘口序号,变盘时间
        if (rsp==null || rsp.getAh()==null || rsp.getAh().getH()==null || rsp.getAh().getH().size()==0)
            return;
        List<String> cah = rsp.getAh().getH();
        if (cah == null || cah.size() < 1) {
            log.error("21多盘口赔率变化: 半场亚赔（让球盘）变化数据总条数:{}", " 没有可更新的数据");
            return;
        }
        log.error("21多盘口赔率变化: 半场亚赔（让球盘）变化数据总条数:{}", cah.size());
        for (int i = 0; i < cah.size(); i++) {
            try {
                String[] item = cah.get(i).split(",");
                log.error("21多盘口赔率变化: 半场亚赔（让球盘）接口数据:{}", cah.get(i));
                if (!item[8].equals("3")){
                    if ("1".equals(item[6])) {//单盘口
                        singleHandicap(item);
                    } else {//存到多盘口
                        manyHandicap(item);
                    }
                }

            } catch (Exception e) {
                log.error("ChangeHalfHandicapHandleServiceImpl半场亚赔赔率异常:"+cah.get(i), e);
            }
        }
    }

    //单盘口操作
    public void singleHandicap(String item[]) {
        LetGoalhalfDetail xml = BeanUtils.parseLetGoalhalfDetail(item);
        //查询最新一条数据
        LetGoalhalfDetail letGoalhalfDetail = letGoalhalfDetailMapper.selectByMidAndCpy(item[0], item[1]);
        if (letGoalhalfDetail == null || letGoalhalfDetail.getOddsid()==null) {
            return;
        }
        try {
            if (!letGoalhalfDetail.oddsEquals(xml)) {
                //入数据库\
                xml.setOddsid(letGoalhalfDetail.getOddsid());
                int inch = letGoalhalfDetailMapper.insertSelective(xml);
                letGoalhalfMapper.updateOddsByOddsId(xml.getOddsid(),xml.getUpodds(),xml.getGoal(),xml.getDownodds(),xml.getModifytime());
                if (inch > 0) {
                    log.error("21多盘口赔率变化: 半场亚赔（让球盘）单盘口 接口数据:{} 入库成功", item);
                }
            }
        }catch (Exception e){
            log.error("21多盘口赔率变化: 半场亚赔（让球盘）单盘口 接口数据:{} 入库异常");
            System.out.println(xml.toString());
            System.out.println(letGoalhalfDetail.toString());
            e.printStackTrace();
        }

    }

    //多盘口操作
    public void manyHandicap(String item[]) {
        MultiLetGoalhalfDetail xml = BeanUtils.parseMultiLetGoalhalfDetail(item);
        MultiLetGoalhalfDetail multiLetGoalhalfDetail = multiLetGoalhalfDetailMapper.selectByMidAndCpyAndNum(item[0], item[1], item[6]);
        if (multiLetGoalhalfDetail == null || multiLetGoalhalfDetail.getOddsid()==null) { // 需要odds 没有就不入库
            return;
        }
        if (!multiLetGoalhalfDetail.oddsEquals(xml)) {
            xml.setOddsid(multiLetGoalhalfDetail.getOddsid());
            int inch = multiLetGoalhalfDetailMapper.insertSelective(xml);
            multiLetGoalhalfMapper.updateOddsByOddsId(xml.getOddsid(),xml.getUpodds(),xml.getGoal(),xml.getDownodds(),xml.getAddtime());
            if (inch > 0) {
                log.error("21多盘口赔率变化: 半场亚赔（让球盘）多盘口 接口数据:{} 入库成功", item);
            }
        }
    }
}