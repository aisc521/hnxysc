package com.zhcdata.jc.service;

import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.db.model.JcSchedulesp;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.Odds.JcFootBallOddsRsp;

public interface JcSchedulespvaryService {
    /**
     * 竞彩sp变化表新增
     * @param jcSchedule
     * @param jcFootBallOddsRsp
     */
    void insertJcSchedulespvary(JcSchedule jcSchedule, JcFootBallOddsRsp jcFootBallOddsRsp,Integer spId,JcSchedulesp jcSchedulesp,Schedule schedule) throws BaseException;
}
