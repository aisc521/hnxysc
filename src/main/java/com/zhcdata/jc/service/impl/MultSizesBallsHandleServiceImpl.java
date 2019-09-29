package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.MultiTotalScoreMapper;
import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.mapper.StandardMapper;
import com.zhcdata.db.mapper.TotalScoreMapper;
import com.zhcdata.db.model.MultiTotalScore;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.db.model.Standard;
import com.zhcdata.db.model.TotalScore;
import com.zhcdata.jc.service.MultHandicapOddsService;
import com.zhcdata.jc.tools.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * CopyRight (c)1999-2019 : zhcw.com
 * Project : jc-new-server
 * Create Date : 2019/9/25 10:49
 * JDK version : JDK1.8
 * Comments : 20.多盘口赔率：即时赔率接口 <大小球即时数据:>
 *
 * @author : 高阳
 * @version : 0.0.1
 */
@Slf4j
@Service("multSizesBallsHandleServiceImpl")
public class MultSizesBallsHandleServiceImpl implements MultHandicapOddsService {

    @Resource
    private TotalScoreMapper totalScoreMapper;

    @Resource
    private MultiTotalScoreMapper multiTotalScoreMapper;

    @Resource
    private ScheduleMapper scheduleMapper;

    @Override
    @Async
    public void changeHandle(String[] items) {
        synchronized (this) {
            //比赛ID,公司ID,初盘盘口,初盘大球赔率,初盘小球赔率,即时盘盘口,即时盘大球赔率,即时盘小球赔率,盘口序号,变盘时间,是否封盘2
            if (items.length > 0) {
                for (String item : items) {
                    if (StringUtils.isNotEmpty(item) && item.split(",").length == 11 && item.split(",")[8].equals("1"))
                        singleHandicap(item);
                    else if (StringUtils.isNotEmpty(item) && item.split(",").length == 11)
                        manyHandicap(item);
                }
            }
            log.error("大小球即时数据解析完成");
        }
    }

    //单盘口操作
    private void singleHandicap(String item) {
        String[] info = item.split(",");
        //当前转化为对象
        TotalScore xml = BeanUtils.parseTotalScore(item);
        //查询比赛信息
        Schedule sc = scheduleMapper.selectByPrimaryKey(xml.getScheduleid());
        //查询最新一条数据
        TotalScore db = totalScoreMapper.selectTotalScoreByMatchAndCpy(Integer.parseInt(info[0]), Integer.parseInt(info[1]));
        if (db == null) {
            if (totalScoreMapper.insertSelective(xml)>0)
                log.info("20多盘口赔率: 大小球即时数据 单盘口 接口数据:{} 入库成功", item);
        } else if (!db.oddsEquals(xml) && xml.getModifytime().getTime() > db.getModifytime().getTime()) {
            if (sc==null || sc.getMatchtime().getTime() > xml.getModifytime().getTime()) {
                //入数据库
                xml.setOddsid(db.getOddsid());
                if (totalScoreMapper.updateByPrimaryKeySelective(xml) > 0) {
                    log.info("20多盘口赔率: 大小球即时数据 单盘口 接口数据:{} 更新成功", item);
                }
            }
        }
    }


    //多盘口操作
    private void manyHandicap(String item) {
        String[] info = item.split(",");
        //当前转化为对象
        MultiTotalScore xml = BeanUtils.parseMultiTotalScore(item);
        //查询比赛信息
        Schedule sc = scheduleMapper.selectByPrimaryKey(xml.getScheduleid());
        //查询最新一条数据
        MultiTotalScore db = null;
        try {
             db = multiTotalScoreMapper.selectTotalScoreByMatchAndCpyAndNum(Integer.parseInt(info[0]), Integer.parseInt(info[1]), Integer.parseInt(info[8]));
        }catch (TooManyResultsException e){
            System.out.println(Integer.parseInt(info[0])+" ---- "+ Integer.parseInt(info[1]));
            e.printStackTrace();
        }
        if (db == null){
            //直接插入
            if (multiTotalScoreMapper.insertSelective(xml)>0)
                log.info("20多盘口赔率: 大小球即时数据 多盘口 接口数据:{} 入库成功", item);
        }else if (!db.oddsEquals(xml) && xml.getModifytime().getTime() > db.getModifytime().getTime()) {
            if (sc==null || sc.getMatchtime().getTime() > xml.getModifytime().getTime()) {
                //入数据库
                xml.setOddsid(db.getOddsid());
                int inch = multiTotalScoreMapper.updateByPrimaryKeySelective(xml);
                if (inch > 0) {
                    log.info("20多盘口赔率: 大小球即时数据 多盘口 接口数据:{} 更新成功", item);
                }
            }
        }
    }
}