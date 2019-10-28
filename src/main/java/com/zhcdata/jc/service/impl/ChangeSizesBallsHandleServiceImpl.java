package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.*;
import com.zhcdata.db.model.MultiTotalScoreDetail;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.db.model.TotalScoreDetail;
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
 * Comments :  21.多盘口赔率：30秒内变化赔率接口 <大小球变化数据:>
 *
 * @author : 高阳
 * @version : 0.0.1
 */
@Slf4j
@Service("changeSizesBallsHandleServiceImpl")
public class ChangeSizesBallsHandleServiceImpl implements ManyHandicapOddsChangeService {

    @Resource
    TotalScoreDetailMapper totalScoreDetailMapper;

    @Resource
    MultiTotalScoreDetailMapper multiTotalScoreDetailMapper;

    @Resource
    TotalScoreMapper totalScoreMapper;

    @Resource
    MultiTotalScoreMapper multiTotalScoreMapper;

    @Resource
    private ScheduleMapper scheduleMapper;

    @Override
    public void changeHandle(MoreHandicapOddsLisAlltRsp rsp) {
        if (rsp==null || rsp.getD()==null || rsp.getD().getH()==null || rsp.getD().getH().size()==0)
            return;
        List<String> cah = rsp.getD().getH();
        if (cah == null || cah.size() < 1) {
            log.error("21多盘口赔率变化: 大小球 变化数据总条数:{}", " 没有可更新的数据");
            return;
        }
        log.error("21多盘口赔率变化: 大小球 变化数据总条数:{}", cah.size());
        for (int i = 0; i < cah.size(); i++) {
            try {
                String[] item = cah.get(i).split(",");
                if (!"3".equals(item[8])) {
                    if ("1".equals(item[5])) {//单盘口
                        singleHandicap(item);
                    } else {//存到多盘口
                        manyHandicap(item);
                    }
                }

            } catch (Exception e) {
                log.error("亚盘赔率异常:", e);
            }
        }
    }

    //单盘口操作
    public void singleHandicap(String item[]) {
        //存到单盘口
        TotalScoreDetail xml = BeanUtils.parseTotalScoreDetail(item);
        //查询此比赛最新的一条赔率
        TotalScoreDetail totalScoreDetail = totalScoreDetailMapper.selectByMidAndCpy(item[0], item[1]);
        if (totalScoreDetail == null || totalScoreDetail.getOddsid() == null)
            return;
        if (!totalScoreDetail.oddsEquals(xml) && xml.getModifytime().getTime() > totalScoreDetail.getModifytime().getTime()) {
            //入数据库\
            xml.setOddsid(totalScoreDetail.getOddsid());
            int inch = totalScoreDetailMapper.insertSelective(xml);
            try {
                totalScoreMapper.updateOddsByOddsId(xml.getOddsid(), xml.getModifytime(), xml.getUpodds(), xml.getGoal(), xml.getDownodds());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (inch > 0) {
                String msg = "比赛id:" + item[0] + ",公司ID:" + item[1];
                log.error("21多盘口赔率变化: 大小球 单盘口 接口数据:{} 入库成功", msg);
            }
        }
    }

    //多盘口操作
    public void manyHandicap(String item[]) {
        MultiTotalScoreDetail xml = BeanUtils.parseparseMultiTotalScoreDetail(item);
        MultiTotalScoreDetail multiTotalScoreDetail = multiTotalScoreDetailMapper.selectByMidAndCpyAndNum(item[0], item[1], item[5]);
        if (multiTotalScoreDetail == null || multiTotalScoreDetail.getOddsid() == null) { // 需要odds 没有就不入库
            return;
        }
        if (!multiTotalScoreDetail.oddsEquals(xml)) {
            xml.setOddsid(multiTotalScoreDetail.getOddsid());
            int inch = multiTotalScoreDetailMapper.insertSelective(xml);
            multiTotalScoreMapper.updateOddsByOddsId(xml.getOddsid(), xml.getAddtime(), xml.getUpodds(), xml.getGoal(), xml.getDownodds());
            if (inch > 0) {
                log.error("21多盘口赔率变化: 大小球 多盘口 接口数据:{} 入库成功", item);
            }
        }
    }
}



