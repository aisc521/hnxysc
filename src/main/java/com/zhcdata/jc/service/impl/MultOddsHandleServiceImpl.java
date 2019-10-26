package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.mapper.StandardDetailMapper;
import com.zhcdata.db.mapper.StandardMapper;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.db.model.Standard;
import com.zhcdata.db.model.StandardDetail;
import com.zhcdata.jc.service.MultHandicapOddsService;
import com.zhcdata.jc.tools.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.zhcdata.jc.quartz.job.Odds.FlagInfo.multi_ou;
import static com.zhcdata.jc.quartz.job.Odds.FlagInfo.multi_yp;

/**
 * CopyRight (c)1999-2019 : zhcw.com
 * Project : jc-new-server
 * Create Date : 2019/9/25 10:49
 * JDK version : JDK1.8
 * Comments : 20.多盘口赔率：即时赔率接口 <欧赔（胜平负）即时数据:>
 *
 * @author : 高阳
 * @version : 0.0.1
 */
@Slf4j
@Service("multOddsHandleServiceImpl")
public class MultOddsHandleServiceImpl implements MultHandicapOddsService {

    @Resource
    private StandardMapper standardMapper;

    @Resource
    private ScheduleMapper scheduleMapper;

    @Resource
    private StandardDetailMapper standardDetailMapper;

    @Override
    @Async
    public void changeHandle(String[] items) {
        synchronized (this) {
            if (items.length > 0) {
                for (String item : items) {
                    try {
                        if (StringUtils.isNotEmpty(item) && item.split(",")[8].equals("1"))
                            singleHandicap(item.split(","));
                    } catch (Exception e) {
                        log.error("欧赔（胜平负）即时数据解析错误" + item);
                        e.printStackTrace();
                    }
                }
            }
            log.error("欧赔（胜平负）即时数据解析完成");
        }
    }

    //单盘口操作
    public void singleHandicap(String[] item) {
        //当前转化为对象
        Standard xml = BeanUtils.parseStandard(item);
        String flag = xml.getScheduleid() + ":" + xml.getCompanyid() + ":" + item[1];
        if (multi_ou.contains(flag))
            return;
        //查询比赛信息
        Schedule sc = scheduleMapper.selectByPrimaryKey(xml.getScheduleid());
        //查询最新一条数据
        Standard db = standardMapper.selectByMidAndCpy(item[0], item[1]);
        try {
            if (db == null) {
                //数据库没有 要插入
                if (standardMapper.insertSelective(xml) > 0) {
                    //log.info("20多盘口赔率: 欧赔（胜平负） 接口数据:{} 入库成功", item);
                    //插入一条详情表
                    Standard afterInsert = standardMapper.selectByMidAndCpy(item[0], item[1]);
                    StandardDetail detail = new StandardDetail();
                    detail.setOddsid(afterInsert.getOddsid());
                    detail.setHomewin(Float.parseFloat(afterInsert.getFirsthomewin()));
                    detail.setGuestwin(Float.parseFloat(afterInsert.getFirstguestwin()));
                    detail.setStandoff(Float.parseFloat(afterInsert.getFirststandoff()));
                    detail.setModifytime(xml.getModifytime());
                    standardDetailMapper.insertSelective(detail);
                    log.error("欧赔单盘主表更新子表初赔:" + detail.toString());
                }
            } else if (!db.same(xml) && (xml.getModifytime().getTime() > db.getModifytime().getTime())) {
                if (sc == null || sc.getMatchtime().getTime() > xml.getModifytime().getTime()) {
                    //入数据库
                    xml.setOddsid(db.getOddsid());
                    if (standardMapper.updateByPrimaryKeySelective(xml) > 0) {
                        //multi_ou_add(flag);
                        //log.info("20多盘口赔率: 欧赔（胜平负） 接口数据:{} 更新成功", item);
                    }
                }
            }
            multi_ou_add(flag);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void multi_ou_add(String str) {
        if (multi_ou.size() > 30000)
            multi_ou.remove(0);
        multi_ou.add(str);
    }
}
