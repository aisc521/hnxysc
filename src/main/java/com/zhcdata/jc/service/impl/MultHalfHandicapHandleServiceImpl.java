package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.*;
import com.zhcdata.db.model.*;
import com.zhcdata.jc.service.MultHandicapOddsService;
import com.zhcdata.jc.tools.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * CopyRight (c)1999-2019 : zhcw.com
 * Project : jc-new-server
 * Create Date : 2019/9/25 10:49
 * JDK version : JDK1.8
 * Comments : 20.多盘口赔率：即时赔率接口 <半场亚赔（让球盘）即时数据:>
 *
 * @author : 高阳
 * @version : 0.0.1
 */
@Slf4j
@Service("multHalfHandicapHandleServiceImpl")
public class MultHalfHandicapHandleServiceImpl implements MultHandicapOddsService {

    @Resource
    private LetGoalhalfMapper letGoalhalfMapper;

    @Resource
    private MultiLetGoalhalfMapper multiLetGoalhalfMapper;

    @Resource
    private ScheduleMapper scheduleMapper;

    @Async
    @Override
    public void changeHandle(String[] items) {
        synchronized (this) {
            //比赛ID,公司ID,初盘盘口,主队初盘赔率,客队初盘赔率,即时盘口,主队即时赔率,客队即时赔率,是否走地,盘口序号,变盘时间
            if (items.length > 0) {
                for (String item : items) {
                    if (StringUtils.isNotEmpty(item) && item.split(",").length == 11 && item.split(",")[9].equals("1"))
                        singleHandicap(item);
                    else if (StringUtils.isNotEmpty(item) && item.split(",").length == 11)
                        manyHandicap(item);
                }
            }
            log.error("半场亚赔（让球盘）即时数据解析完成");
        }
    }

    //单盘口操作
    private void singleHandicap(String item) {
        String[] info = item.split(",");
        //当前转化为对象
        LetGoalhalf xml = BeanUtils.parseHalfLetGoal(item);
        //查询比赛信息
        Schedule sc = scheduleMapper.selectByPrimaryKey(xml.getScheduleid());
        //查询最新一条数据
        LetGoalhalf db = letGoalhalfMapper.selectByMatchIdAndCmp(Integer.parseInt(info[0]), Integer.parseInt(info[1]));
        if (db == null) {
            //插入
            if (letGoalhalfMapper.insertSelective(xml) > 0)
                log.info("20多盘口赔率: 亚赔（让球盘）单盘口 接口数据:{} 入库成功", item);
        } else if (!db.oddsEquals(xml) && xml.getModifytime().getTime() > db.getModifytime().getTime()) {
            if (sc == null || sc.getMatchtime().getTime() > xml.getModifytime().getTime()) {
                //入数据库
                xml.setOddsid(db.getOddsid());
                try {
                    if (letGoalhalfMapper.updateByPrimaryKeySelective(xml) > 0) {
                        log.info("20多盘口赔率: 亚赔（让球盘）单盘口 接口数据:{} 更新成功", item);
                    }
                } catch (Exception e) {
                    log.error("20多盘口赔率: 亚赔（让球盘）单盘口 接口数据:{} 更新异常", item);
                }

            }
        }
    }

    //多盘口操作
    private void manyHandicap(String item) {
        String[] info = item.split(",");
        //当前转化为对象
        MultiLetGoalhalf xml = BeanUtils.parseMultiLetGoalhalf(item);
        //查询比赛信息
        Schedule sc = scheduleMapper.selectByPrimaryKey(xml.getScheduleid());
        //查询最新一条数据
        MultiLetGoalhalf db = multiLetGoalhalfMapper.selectByMatchIdAndCmpAndNum(info[0], info[1], info[9]);
        if (db == null) {
            if (multiLetGoalhalfMapper.insertSelective(xml) > 0)
                log.error("20多盘口赔率: 亚赔（让球盘）单盘口 接口数据:{} 入库成功", item);
        } else if (!db.oddsEquals(xml) && xml.getModifytime().getTime() > db.getModifytime().getTime()) {
            if (sc == null || sc.getMatchtime().getTime() > xml.getModifytime().getTime()) {
                //入数据库
                xml.setOddsid(db.getOddsid());
                int inch = multiLetGoalhalfMapper.updateByPrimaryKeySelective(xml);
                if (inch > 0) {
                    log.error("20多盘口赔率: 亚赔（让球盘）单盘口 接口数据:{} 更新成功", item);
                }
            }
        }
    }
}
