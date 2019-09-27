package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.MultiTotalScorehalfMapper;
import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.mapper.StandardMapper;
import com.zhcdata.db.mapper.TotalScorehalfMapper;
import com.zhcdata.db.model.MultiTotalScorehalf;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.db.model.Standard;
import com.zhcdata.db.model.TotalScorehalf;
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
 * Comments : 20.多盘口赔率：即时赔率接口 <半场大小球即时数据:>
 *
 * @author : 高阳
 * @version : 0.0.1
 */
@Slf4j
@Service("multHalfSizesBallsHandleServiceImpl")
public class MultHalfSizesBallsHandleServiceImpl implements MultHandicapOddsService {

    @Resource
    private TotalScorehalfMapper totalScorehalfMapper;

    @Resource
    private MultiTotalScorehalfMapper multiTotalScorehalfMapper;

    @Resource
    private ScheduleMapper scheduleMapper;

    @Override
    @Async
    public void changeHandle(String[] items) {
        //比赛ID,公司ID,初盘盘口,初盘大球赔率,初盘小球赔率,即时盘盘口,即时盘大球赔率,即时盘小球赔率,盘口序号,变盘时间
        synchronized (this) {
            if (items.length > 0) {
                for (String item : items) {
                    if (StringUtils.isNotEmpty(item) && item.split(",").length == 10 && item.split(",")[8].equals("1"))
                        singleHandicap(item);
                    else if (StringUtils.isNotEmpty(item) && item.split(",").length == 10)
                        manyHandicap(item);
                }
            }
            log.error("半场大小球即时数据解析完成");
        }
    }

    //单盘口操作
    private void singleHandicap(String item) {
        String[] info = item.split(",");
        //当前转化为对象
        TotalScorehalf xml = BeanUtils.parseTotalScorehalf(item);
        //查询比赛信息
        Schedule sc = scheduleMapper.selectByPrimaryKey(xml.getScheduleid());
        //查询最新一条数据
        TotalScorehalf db = totalScorehalfMapper.selectByMidAndCpy(info[0], info[1]);
        try {
            if (db == null){
                if (totalScorehalfMapper.insertSelective(xml)>0)
                    log.info("20多盘口赔率: 单盘即时半场大小球 接口数据:{} 入库成功", item);
            }else if (!db.oddsEquals(xml) && xml.getModifytime().getTime() > db.getModifytime().getTime()) {
                if (sc==null || sc.getMatchtime().getTime()>xml.getModifytime().getTime()) {
                    //入数据库
                    xml.setOddsid(db.getOddsid());
                    int inch = totalScorehalfMapper.updateByPrimaryKeySelective(xml);
                    if (inch > 0) {
                        log.error("20多盘口赔率: 单盘即时半场大小球 接口数据:{} 更新成功", item);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    //单盘口操作
    private void manyHandicap(String item) {
        String[] info = item.split(",");
        //当前转化为对象
        MultiTotalScorehalf xml = BeanUtils.parseMultiTotalScorehalf(item);
        //查询比赛信息
        Schedule sc = scheduleMapper.selectByPrimaryKey(xml.getScheduleid());
        //查询最新一条数据
        MultiTotalScorehalf db = multiTotalScorehalfMapper.selectByMidAndCpyAndNum(info[0], info[1],info[8]);
        if (db == null){
            if (multiTotalScorehalfMapper.insertSelective(xml)>0)
                log.info("20多盘口赔率: 即时多盘半场大小球 接口数据:{} 入库成功", item);
        }else if (!db.oddsEquals(xml) && xml.getModifytime().getTime() > db.getModifytime().getTime()) {
            if (sc==null || sc.getMatchtime().getTime()>xml.getModifytime().getTime()) {
                //入数据库
                xml.setOddsid(db.getOddsid());
                int inch = multiTotalScorehalfMapper.updateByPrimaryKeySelective(xml);
                if (inch > 0) {
                    log.info("20多盘口赔率: 即时多盘半场大小球 接口数据:{} 更新成功", item);
                }
            }
        }
    }
}