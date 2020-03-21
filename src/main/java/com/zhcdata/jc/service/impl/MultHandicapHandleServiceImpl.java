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

import static com.zhcdata.jc.quartz.job.Odds.FlagInfo.multi_yp;

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
    private MultiLetGoalDetailMapper multiLetGoalDetailMapper;

    @Resource
    private ScheduleMapper scheduleMapper;

    @Resource
    private LetGoalDetailMapper letGoalDetailMapper;

    @Async
    @Override
    public void changeHandle(String[] items) {
        synchronized (this) {
            if (items.length > 0) {
                for (int i = 0; i < items.length; i++) {
                    try {
                        if (StringUtils.isNotEmpty(items[i])) {
                            if (StringUtils.isNotEmpty(items[i]) && items[i].split(",")[10].equals("1"))
                                singleHandicap(items[i], "共" + items.length + ",当前" + i);
                            else
                                manyHandicap(items[i], "共" + items.length + ",当前" + i);
                        }
                    } catch (Exception e) {
                        log.error("亚赔（让球盘）即时数据解析错误" + items[i]);
                        e.printStackTrace();
                    }
                }
            }
            log.error("亚赔（让球盘）即时数据解析完成");
        }
    }


    //单盘口操作
    public void singleHandicap(String item, String msg) {
        String[] info = item.split(",");
        //当前转化为对象
        Letgoal xml = BeanUtils.parseLetgoal(item);
        String flag = xml.getScheduleid() + ":" + xml.getCompanyid() + ":" + item.split(",")[10];
        if (multi_yp.contains(flag))
            //如果有此 比赛id:公司id:盘口id，则return，更新让变化表来，如果没有，添加
            return;
        Schedule sc = scheduleMapper.selectByPrimaryKey(Integer.parseInt(info[0]));
        //查询最新一条数据
        Letgoal db = letgoalMapper.selectByMatchIdAndCompany(info[0], info[1]);
        if (db == null) {
            try {
                int insert_id = letgoalMapper.insertSelective(xml);
                try {
                    if (insert_id>0)
                        log.warn("亚盘主表新增记录完成，数据体{}",xml.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
                if (insert_id > 0) {
                    multi_yp_add(flag);
                    Letgoal dbAfterInsert = letgoalMapper.selectByMatchIdAndCompany(info[0], info[1]);
                    LetGoalDetail first = new LetGoalDetail();
                    first.setOddsid(dbAfterInsert.getOddsid());
                    first.setUpodds(xml.getFirstupodds());
                    first.setGoal(xml.getFirstgoal());
                    first.setDownodds(xml.getFirstdownodds());
                    first.setModifytime(xml.getModifytime());
                    letGoalDetailMapper.insertSelective(first);
                    log.error("亚赔单盘主表更新子表初赔:" + first.toString());
                    //log.info("20多盘口赔率: 亚赔（让球盘）单盘口 接口数据:{} 入库成功", item);
                    //letgoal_goalMapper.selectByPrimaryKey()
                    //往详情表插入一条
                    //LetGoalDetail detail = letGoalDetailMapper.selectByMatchAndCpyOrderByTimeAscLimit1(xml.getScheduleid(), xml.getCompanyid());
                    //if ((!detail.getUpodds().equals(xml.getFirstupodds()))||(!detail.getGoal().equals(xml.getFirstgoal()))||(!detail.getDownodds().equals(xml.getFirstdownodds()))){
                    //证明这不是初赔
                    //}
                }
            } catch (Exception e) {
                log.error("20多盘口赔率: 亚赔（让球盘）单盘口 接口数据:{} 入库异常", item,e);
                //e.printStackTrace();
            }
        } else if (!db.nowOddsSame(xml) && (xml.getModifytime().getTime() > db.getModifytime().getTime())) {
            try {
                if ((sc == null) || (sc.getMatchtime().getTime() > xml.getModifytime().getTime())) {
                    //入数据库
                    xml.setOddsid(db.getOddsid());
                    if (letgoalMapper.updateByPrimaryKeySelective(xml) > 0) {
                        //log.info("20多盘口赔率: 亚赔（让球盘）单盘口 接口数据:{} 更新成功"+msg, item);
                        multi_yp_add(flag);
                    }
                }
            } catch (Exception e) {
                log.error("20多盘口赔率: 亚赔（让球盘）单盘口 接口数据:{} 更新异常", item,e);
            }
        }
    }


    //多盘口操作
    public void manyHandicap(String item, String msg) {
        String[] info = item.split(",");
        MultiLetgoal xml = BeanUtils.parseMultiLetgoall(item);
        String flag = xml.getScheduleid() + ":" + xml.getCompanyid() + ":" + item.split(",")[10];
        if (multi_yp.contains(flag))
            return;
        Schedule sc = scheduleMapper.selectByPrimaryKey(Integer.parseInt(info[0]));
        //当前转化为对象
        //查询最新一条数据
        MultiLetgoal db = multiLetgoalMapper.selectByMatchIdAndCompanyAndHandicapNum(info[0], info[1], Short.parseShort(info[10]));
        if (db == null) {
            try {
                if (multiLetgoalMapper.insertSelective(xml) > 0) {
                    multi_yp_add(flag);
                    //log.info("20多盘口赔率: 亚赔（让球盘）多盘口 接口数据:{} 入库成功"+msg, item);
                    MultiLetgoal afterInsert = multiLetgoalMapper.selectByMatchIdAndCompanyAndHandicapNum(info[0], info[1], Short.parseShort(info[10]));
                    MultiLetGoalDetail detail = new MultiLetGoalDetail();
                    detail.setOddsid(afterInsert.getOddsid());
                    detail.setDownodds(xml.getFirstdownodds());
                    detail.setGoal(xml.getFirstgoal());
                    detail.setUpodds(xml.getFirstupodds());
                    detail.setAddtime(xml.getModifytime());
                    multiLetGoalDetailMapper.insertSelective(detail);
                }
            } catch (Exception e) {
                log.error("20多盘口赔率: 亚赔（让球盘）多盘口 接口数据:{} 入库异常", item,e);
                e.printStackTrace();
            }

        } else if (!db.nowOddsSame(xml) && (xml.getModifytime().getTime() > db.getModifytime().getTime())) {
            if (sc == null || sc.getMatchtime().getTime() > xml.getModifytime().getTime()) {
                //入数据库
                xml.setOddsid(db.getOddsid());
                try {
                    if (multiLetgoalMapper.updateByPrimaryKeySelective(xml) > 0) {
                        multi_yp_add(flag);
                        //log.info("20多盘口赔率: 亚赔（让球盘）多盘口 接口数据:{} 更新成功", item);
                    }
                } catch (Exception e) {
                    log.error("20多盘口赔率: 亚赔（让球盘）多盘口 接口数据:{} 更新异常", item,e);
                }
            }
        }
    }

    private void multi_yp_add(String str) {
        if (multi_yp.size() > 30000)
            multi_yp.remove(0);
        multi_yp.add(str);
    }
}
