package com.zhcdata.jc.service;

import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.Odds.JcFootBallOddsRsp;

public interface JcScheduleService {
    /**
     * 根据MatchId查询
     * @param id
     * @return
     */
    JcSchedule queryJcScheduleByMatchID(String id,String time);

    /**
     * 更新竞彩对阵表
     * @param jcSchedule
     * @param jcFootBallOddsRsp
     */
    void updateJcSchedule(JcSchedule jcSchedule, JcFootBallOddsRsp jcFootBallOddsRsp) throws BaseException;

    JcSchedule queryJcScheduleByBet007(Integer matchId);
}
