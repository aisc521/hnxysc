package com.zhcdata.jc.service.impl;

import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.db.model.JcSchedulespvary;
import com.zhcdata.jc.service.JcSchedulespvaryService;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.Odds.JcFootBallOddsRsp;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/12 13:52
 */
@Service
public class JcSchedulespvaryServiceImpl implements JcSchedulespvaryService {
    @Override
    public void insertJcSchedulespvary(JcSchedule jcSchedule, JcFootBallOddsRsp jcFootBallOddsRsp,Integer spId) {
        JcSchedulespvary jcSchedulespvary = new JcSchedulespvary();

    }
}
