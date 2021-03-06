package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.*;
import com.zhcdata.db.model.*;
import com.zhcdata.jc.service.ManyHandicapOddsChangeService;
import com.zhcdata.jc.tools.BeanUtils;
import com.zhcdata.jc.xml.rsp.MoreHandicapOddsLisAlltRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

//import static com.zhcdata.jc.quartz.job.Odds.FlagInfo.MATCH_START_TIME;

/**
 * CopyRight (c)1999-2019 : zhcw.com
 * Project : jc-new-server
 * Create Date : 2019/9/25 10:49
 * JDK version : JDK1.8
 * Comments : 21.多盘口赔率：30秒内变化赔率接口 <亚赔（让球盘）变化数据:>
 *
 * @author : 高阳
 * @version : 0.0.1
 */
@Slf4j
@Service("changeHandicapHandleServiceImpl")
public class ChangeHandicapHandleServiceImpl implements ManyHandicapOddsChangeService {

    @Resource
    private LetGoalDetailMapper letGoalDetailMapper;//让球盘赔率变化

    @Resource
    private LetgoalMapper letgoalMapper;//让球赔率表

    @Resource
    private MultiLetGoalDetailMapper multiLetGoalDetailMapper;//让球多盘赔率变化

    @Resource
    private MultiLetgoalMapper multiLetgoalMapper;

    @Resource
    private ScheduleMapper scheduleMapper;

    @Resource
    private Letgoal_goalMapper letgoal_goalMapper;

    @Override
    public void changeHandle(MoreHandicapOddsLisAlltRsp rsp) {
        //亚赔（让球盘）变化数据:比赛ID,公司ID,即时盘口,主队即时赔率,客队即时赔率,是否封盘1,是否走地,盘口序号,变盘时间,是否封盘2
        if (rsp == null || rsp.getA() == null || rsp.getA().getH() == null || rsp.getA().getH().size() == 0)
            return;
        List<String> cah = rsp.getA().getH();
        if (cah == null || cah.size() < 1) {
            log.error("21多盘口赔率变化: 亚赔（让球盘）变化数据总条数:{}", " 没有可更新的数据");
            return;
        }
        log.error("21多盘口赔率变化: 亚赔（让球盘）变化数据总条数:{}", cah.size());
        for (int i = 0; i < cah.size(); i++) {
            try {
                String[] item = cah.get(i).split(",");
                if (!item[10].equals("3")) {
                    if ("1".equals(item[7])) {//单盘口
                        singleHandicap(item);
                    } else {//存到多盘口
                        manyHandicap(item);
                    }
                }
            } catch (Exception e) {
                log.error("ChangeHandicapHandleServiceImpl亚盘赔率异常:"+cah.get(i), e);
                e.printStackTrace();
            }
        }
    }

    //单盘口操作
    public void singleHandicap(String item[]) {
        //if (true)return;
        //存到单盘口
        LetGoalDetail xml = BeanUtils.parseLetGoalDetail(item);
        //查询此比赛最新的一条赔率
        LetGoalDetail letGoalDetail = letGoalDetailMapper.selectByMatchAndCpyOrderByTimeDescLimit1(Integer.parseInt(item[0]), Integer.parseInt(item[1]));
        if (letGoalDetail == null || letGoalDetail.getOddsid() == null) {
            return;
        }
        if (!letGoalDetail.oddsEquals(xml)) {
            //入数据库
            xml.setOddsid(letGoalDetail.getOddsid());
            int inch = letGoalDetailMapper.insertSelective(xml);
            letgoalMapper.updateOddsByOddsId(xml.getOddsid(), xml.getUpodds(), xml.getGoal(), xml.getDownodds(), xml.getModifytime());
            if (!xml.getGoal().equals(letGoalDetail.getGoal())) {
                //更盘口变化表，查询有没有此oddsid的变盘次数
                int changed = letgoal_goalMapper.updateCountAddOne(letGoalDetail.getOddsid(), new Date());
                if (changed == 0) {//没有更新 插入
                    Letgoal_goal goal = new Letgoal_goal();
                    goal.setOddsid(Long.parseLong(letGoalDetail.getOddsid().toString()));
                    goal.setCreateTime(new Date());
                    goal.setGoalCount(0);
                    letgoal_goalMapper.insertSelective(goal);
                }
            }

            if (inch > 0)
                log.error("21多盘口赔率变化: 亚赔（让球盘）单盘口 接口数据:{} 入库成功", item);
        }

    }

    //多盘口操作
    public void manyHandicap(String item[]) {
        MultiLetGoalDetail xml = BeanUtils.parseMultiLetGoalDetail(item);
        MultiLetGoalDetail multiLetGoalDetail = multiLetGoalDetailMapper.selectByMidAndCpyAndNum(item[0], item[1], item[7]);
        if (multiLetGoalDetail == null) { // 需要odds 没有就不入库
            return;
        }
        if (!multiLetGoalDetail.oddsEquals(xml)) {
            xml.setOddsid(multiLetGoalDetail.getOddsid());
            int inch = multiLetGoalDetailMapper.insertSelective(xml);
            multiLetgoalMapper.updateOddsByOddsId(xml.getOddsid(), xml.getUpodds(), xml.getGoal(), xml.getDownodds(), xml.getAddtime());
            if (inch > 0) {
                log.error("21多盘口赔率变化: 亚赔（让球盘）多盘口 接口数据:{} 入库成功", item);
            }
        }
    }
}
