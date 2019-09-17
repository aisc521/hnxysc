package com.zhcdata.jc.service;

import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.db.model.JcSchedulesp;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.Odds.JcFootBallOddsRsp;

public interface JcSchedulespService {
    /**
     * 根据赛事id查询
     * @param scheduleId
     * @return
     */
    JcSchedulesp queryJcSchedulespById(Integer scheduleId);

    /**
     * 更新JcSchedulesp
     * @param jcSchedulesp
     * @param jcSchedule
     * @param jcFootBallOddsRsp
     */
    void updateJcSchedulesp(JcSchedulesp jcSchedulesp, JcSchedule jcSchedule, JcFootBallOddsRsp jcFootBallOddsRsp,Schedule schedule) throws BaseException;

    /**
     * 新增JcSchedulesp
     * @param jcSchedule
     * @param jcFootBallOddsRsp
     */
    Integer insertJcSchedulesp(JcSchedule jcSchedule, JcFootBallOddsRsp jcFootBallOddsRsp,Schedule schedule) throws BaseException;
}