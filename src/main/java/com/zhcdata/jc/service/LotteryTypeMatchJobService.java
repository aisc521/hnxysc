package com.zhcdata.jc.service;

import com.zhcdata.db.model.JcMatchLottery;
import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.db.model.JcSchedulesp;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.LotterType.LotteryTypeMatchRsp;

import java.text.ParseException;

/**
 * @Description 对应接口： 8.彩票赛程与球探网ID关联表
 *              功   能： 区分赛事彩种信息
 */
public interface LotteryTypeMatchJobService {

    /**
     * 根据bet007查询JcMatchLottery数据
     * @param Betoo7
     * @return
     */
    JcMatchLottery queryJcMatchLotteryByBet007(long Betoo7,String gameType);

    /**
     * 更新JcMatchLottery数据
     * @param jcMatchLottery
     * @param lotteryTypeMatchRsp
     */
    void updateJcMatchLottery(JcMatchLottery jcMatchLottery, LotteryTypeMatchRsp lotteryTypeMatchRsp) throws BaseException;
    /**
     * 新增JcMatchLottery数据
     * @param lotteryTypeMatchRsp
     * @param lotteryTypeMatchRsp
     */
    void insertJcMatchLottery(LotteryTypeMatchRsp lotteryTypeMatchRsp) throws BaseException;

    /**
     * 根据bet007查询JcSchedule数据
     * @param l
     * @return
     */
    JcSchedule queryJcScheduleByBet007(long Bet007);

    /**
     * 根据id查询赛程表信息
     * @param l
     * @return
     */
    Schedule queryScheduleByBet007(int l);

    /**
     * 查询竞彩赔率表
     * @param i
     * @return
     */
    JcSchedulesp queryJcSchedulespByScId(int i);

    /**
     * 更新竞彩表
     * @param jcSchedule
     * @param schedule
     * @param jcSchedulesp
     * @param lotteryTypeMatchRsp
     */
    void updateJcSchedule(JcSchedule jcSchedule, Schedule schedule, LotteryTypeMatchRsp lotteryTypeMatchRsp) throws ParseException, BaseException;

    /**
     * 新增竞彩表
     * @param schedule
     * @param jcSchedulesp
     * @param lotteryTypeMatchRsp
     */
    void insertJcSchedule(Schedule schedule, LotteryTypeMatchRsp lotteryTypeMatchRsp) throws ParseException, BaseException;
}
