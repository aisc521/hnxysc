package com.zhcdata.jc.service;

import com.zhcdata.db.model.JcMatchBjdcPl;
import com.zhcdata.db.model.JcMatchBjdcreslut;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.BjDcLotteryQuery.BjDcLotteryQueryRsp;

import java.util.List;

public interface JcMatchBjdcreslutService {
    JcMatchBjdcreslut queryJcMatchBjdcreslutByBet007(int i);


    void updateJcMatchBjdcreslut(Schedule schedule, JcMatchBjdcreslut jcMatchBjdcreslut, List<JcMatchBjdcPl> jcMatchBjdcPls, BjDcLotteryQueryRsp bjDcLotteryQueryRsp) throws BaseException;

    void insertJcMatchBjdcreslut(Schedule schedule, List<JcMatchBjdcPl> jcMatchBjdcPls, BjDcLotteryQueryRsp bjDcLotteryQueryRsp) throws BaseException;
}
