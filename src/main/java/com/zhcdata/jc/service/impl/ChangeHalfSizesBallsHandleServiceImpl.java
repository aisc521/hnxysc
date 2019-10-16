package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.*;
import com.zhcdata.db.model.MultiTotalScorehalfDetail;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.db.model.TotalScorehalfDetail;
import com.zhcdata.jc.service.ManyHandicapOddsChangeService;
import com.zhcdata.jc.tools.BeanUtils;
import com.zhcdata.jc.xml.rsp.MoreHandicapOddsLisAlltRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * CopyRight (c)1999-2019 : zhcw.com
 * Project : jc-new-server
 * Create Date : 2019/9/26 10:16
 * JDK version : JDK1.8
 * Comments :  21.多盘口赔率：30秒内变化赔率接口 <半场大小球变化数据:>
 *
 * @author : 高阳
 * @version : 0.0.1
 */
@Slf4j
@Service("changeHalfSizesBallsHandleServiceImpl")
public class ChangeHalfSizesBallsHandleServiceImpl implements ManyHandicapOddsChangeService {

    @Resource
    TotalScorehalfDetailMapper totalScorehalfDetailMapper;

    @Resource
    MultiTotalScorehalfDetailMapper multiTotalScorehalfDetailMapper;

    @Resource
    TotalScorehalfMapper totalScorehalfMapper;

    @Resource
    MultiTotalScorehalfMapper multiTotalScorehalfMapper;

    @Resource
    ScheduleMapper scheduleMapper;

    @Override
    public void changeHandle(MoreHandicapOddsLisAlltRsp rsp) {

        List<String> cah = rsp.getDh().getH();
        if (cah == null || cah.size() < 1) {
            log.error("21多盘口赔率变化: 半场大小球 变化数据总条数:{}", " 没有可更新的数据");
            return;
        }
        log.error("21多盘口赔率变化: 半场大小球 变化数据总条数:{}", cah.size());
        for (int i = 0; i < cah.size(); i++) {
            try {
                String[] item = cah.get(i).split(",");
                log.error("21多盘口赔率变化: 半场大小球 接口数据:{}", item);
                //if (item.length != 8) {
                //    log.error("21多盘口赔率变化: 半场大小球  数据格式不合法 接口数据:{}", item);
                //    continue;
                //}
                Schedule schedule = scheduleMapper.selectByPrimaryKey(Integer.parseInt(item[0]));
                if(schedule ==null || schedule.getMatchtime().getTime()<System.currentTimeMillis()){
                    log.error("21多盘口赔率变化: 半场大小球  暂无此赛程或比赛已经开始，比赛ID:{}", item[0]);
                    continue;
                }

                if ("1".equals(item[5])) {//单盘口
                    singleHandicap(item);
                } else {//存到多盘口
                    manyHandicap(item);
                }
            } catch (Exception e) {
                log.error("亚盘赔率异常:", e);
            }
        }
    }

    //单盘口操作
    public void singleHandicap(String item[]) {
        //存到单盘口
        TotalScorehalfDetail xml = BeanUtils.parseTotalScorehalfDetail(item);
        //比赛id，公司ID获取数据库最新一条
        TotalScorehalfDetail totalScorehalfDetail = totalScorehalfDetailMapper.selectByMidAndCpy(item[0], item[1]);
        if (totalScorehalfDetail == null || totalScorehalfDetail.getOddsid()==null) {
            return;
        }
        if (totalScorehalfDetail.getId()==null || !totalScorehalfDetail.oddsEquals(xml) && xml.getModifytime().getTime() > totalScorehalfDetail.getModifytime().getTime()) {
            //入数据库\
            xml.setOddsid(totalScorehalfDetail.getOddsid());
            int inch = totalScorehalfDetailMapper.insertSelective(xml);
            totalScorehalfMapper.updateOddsByOddsId(xml.getOddsid(),xml.getUpodds(),xml.getDownodds(),xml.getModifytime(),xml.getGoal());
            if (inch > 0) {
                log.error("21多盘口赔率变化: 半场大小球  单盘口 接口数据:{} 入库成功", item);
            }
        }
    }

    //多盘口操作
    public void manyHandicap(String item[]) {
        MultiTotalScorehalfDetail xml = BeanUtils.parseMultiTotalScorehalfDetail(item);
        MultiTotalScorehalfDetail multiTotalScorehalfDetail = multiTotalScorehalfDetailMapper.selectByMidAndCpyAndNum(item[0], item[1], item[5]);
        if (multiTotalScorehalfDetail == null) { // 需要odds 没有就不入库
            return;
        }
        if (!multiTotalScorehalfDetail.oddsEquals(xml)) {
            xml.setOddsid(multiTotalScorehalfDetail.getOddsid());
            int inch = multiTotalScorehalfDetailMapper.insertSelective(xml);
            multiTotalScorehalfMapper.updateOddsByOddsId(xml.getOddsid(),xml.getGoal(),xml.getUpodds(),xml.getDownodds(),xml.getAddtime());
            if (inch > 0) {
                log.error("21多盘口赔率变化: 半场大小球  多盘口 接口数据:{} 入库成功", item);
            }
        }
    }
}



