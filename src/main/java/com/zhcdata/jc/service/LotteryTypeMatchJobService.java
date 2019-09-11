package com.zhcdata.jc.service;
import com.zhcdata.db.model.JcMatchBjdc;
import com.zhcdata.db.model.JcMatchJczq;
import com.zhcdata.db.model.JcMatchZc;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.LotterType.LotteryTypeMatchRsp;

import java.text.ParseException;

/**
 * @Description 对应接口： 8.彩票赛程与球探网ID关联表
 *              功   能： 区分赛事彩种信息
 */
public interface LotteryTypeMatchJobService {
    /**
     * 根据IDBet007 查询是否有对应数据信息
     * @param l
     * @return
     */
    JcMatchZc queryJcLotterTypeZcByIDBet007(long l);
    /**
     * 根据IDBet007 查询是否有对应数据信息
     * @param l
     * @return
     */
    JcMatchJczq queryJcLotterTypeJcByIDBet007(long l);

    JcMatchBjdc queryJcLotterTypeBdByIDBet007(long l);

    void updateJcLotterTypeZc(JcMatchZc jcLotterTypeZc, LotteryTypeMatchRsp lotteryTypeMatchRsp) throws ParseException, BaseException;

    void insertJcLotterTypeZc(LotteryTypeMatchRsp lotteryTypeMatchRsp) throws ParseException, BaseException;

    void updateJcLotterTypeJc(JcMatchJczq jcLotterTypeJc, LotteryTypeMatchRsp lotteryTypeMatchRsp) throws ParseException, BaseException;

    void insertJcLotterTypeJc(LotteryTypeMatchRsp lotteryTypeMatchRsp) throws ParseException, BaseException;

    void updateJcLotterTypeBd(JcMatchBjdc jcLotterTypeBd, LotteryTypeMatchRsp lotteryTypeMatchRsp) throws BaseException, ParseException;

    void insertJcLotterTypeBd(LotteryTypeMatchRsp lotteryTypeMatchRsp) throws ParseException, BaseException;

    /**
     * 根据NOID 插叙
     * @param id
     * @return
     */
    JcMatchJczq queryJcLotterTypeJcByNoId(String id);
}
