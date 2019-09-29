package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.*;
import com.zhcdata.db.model.*;
import com.zhcdata.jc.service.ManyHandicapOddsChangeService;
import com.zhcdata.jc.service.MultHandicapOddsService;
import com.zhcdata.jc.tools.BeanUtils;
import com.zhcdata.jc.xml.rsp.MoreHandicapOddsLisAlltRsp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * CopyRight (c)1999-2019 : zhcw.com
 * Project : jc-new-server
 * Create Date : 2019/9/25 10:49
 * JDK version : JDK1.8
 * Comments : 20.多盘口赔率：即时赔率接口 <亚赔（让球盘）即时数据:>
 *
 * @author : 高阳
 * @version : 0.0.1
 */
@Slf4j
@Service("multHandicapHandleServiceImpl")
public class MultHandicapHandleServiceImpl implements MultHandicapOddsService {

    @Resource
    private LetgoalMapper letgoalMapper;

    @Resource
    private MultiLetgoalMapper multiLetgoalMapper;

    @Resource
    private ScheduleMapper scheduleMapper;

    private boolean run = false;

    @Async
    @Override
    public void changeHandle(String[] items) {
        synchronized (this) {
            if (items.length > 0) {
                for (String item : items) {
                    if (StringUtils.isNotEmpty(item) && item.split(",").length == 14 && item.split(",")[10].equals("1"))
                        singleHandicap(item);
                    else if (StringUtils.isNotEmpty(item) && item.split(",").length == 14)
                        manyHandicap(item);
                    else {
                        boolean notEmpty = StringUtils.isNotEmpty(item);
                        boolean b = item.split(",").length == 14;
                        boolean b1 = item.split(",")[10].equals("1");
                        System.out.println(notEmpty + " " + b + " " + b1);
                    }
                }
            }
            log.error("亚赔（让球盘）即时数据解析完成");
        }
    }


    //单盘口操作
    public void singleHandicap(String item) {
        String[] info = item.split(",");
        Schedule sc = scheduleMapper.selectByPrimaryKey(Integer.parseInt(info[0]));
        //当前转化为对象
        Letgoal xml = BeanUtils.parseLetgoal(item);
        //查询最新一条数据
        Letgoal db = letgoalMapper.selectByMatchIdAndCompany(info[0], info[1]);
        if (db == null) {
            try {
                if (letgoalMapper.insertSelective(xml) > 0)
                    log.info("20多盘口赔率: 亚赔（让球盘）单盘口 接口数据:{} 入库成功", item);
            } catch (Exception e) {
                log.error("20多盘口赔率: 亚赔（让球盘）单盘口 接口数据:{} 入库异常", item);
            }

        } else if (!db.nowOddsSame(xml) && xml.getModifytime().getTime() > db.getModifytime().getTime()) {
            try {
                if ((sc == null) || (sc.getMatchtime().getTime() > xml.getModifytime().getTime())) {
                    //入数据库
                    xml.setOddsid(db.getOddsid());
                    if (letgoalMapper.updateByPrimaryKeySelective(xml) > 0) {
                        log.info("20多盘口赔率: 亚赔（让球盘）单盘口 接口数据:{} 更新成功", item);
                    }
                }
            } catch (Exception e) {
                log.error("20多盘口赔率: 亚赔（让球盘）单盘口 接口数据:{} 更新异常", item);
            }
        }
    }


    //多盘口操作
    public void manyHandicap(String item) {
        String[] info = item.split(",");
        Schedule sc = scheduleMapper.selectByPrimaryKey(Integer.parseInt(info[0]));
        //当前转化为对象
        MultiLetgoal xml = BeanUtils.parseMultiLetgoall(item);
        //查询最新一条数据
        MultiLetgoal db = multiLetgoalMapper.selectByMatchIdAndCompanyAndHandicapNum(info[0], info[1], Short.parseShort(info[10]));
        if (db == null) {
            try {
                if (multiLetgoalMapper.insertSelective(xml) > 0)
                    log.info("20多盘口赔率: 亚赔（让球盘）多盘口 接口数据:{} 入库成功", item);
            } catch (Exception e) {
                log.error("20多盘口赔率: 亚赔（让球盘）多盘口 接口数据:{} 入库异常", item);
            }

        } else if (!db.nowOddsSame(xml) && xml.getModifytime().getTime() > db.getModifytime().getTime()) {
            if (sc == null || sc.getMatchtime().getTime() > xml.getModifytime().getTime()) {
                //入数据库
                xml.setOddsid(db.getOddsid());
                try {
                    if (multiLetgoalMapper.updateByPrimaryKeySelective(xml) > 0) {
                        log.info("20多盘口赔率: 亚赔（让球盘）多盘口 接口数据:{} 更新成功", item);
                    }
                } catch (Exception e) {
                    log.error("20多盘口赔率: 亚赔（让球盘）多盘口 接口数据:{} 更新异常", item);
                }
            }
        }
    }
}
