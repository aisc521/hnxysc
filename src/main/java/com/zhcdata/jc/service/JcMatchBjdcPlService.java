package com.zhcdata.jc.service;

import com.zhcdata.db.model.JcMatchBjdcPl;
import com.zhcdata.db.model.JcMatchLottery;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.BdrealTimeSp.BdrealimeSpRsp;

import java.util.List;

public interface JcMatchBjdcPlService {
    List<JcMatchBjdcPl> queryJcMatchBjdcPlByIssuemAndNoId(String issueNum, String noId);

    void updateJcMatchBjdcPl(List<JcMatchBjdcPl>  jcMatchBjdcPl, JcMatchLottery jcMatchLottery, BdrealimeSpRsp bdrealimeSpRsp) throws BaseException;

    void insertJcMatchBjdcPl(JcMatchLottery jcMatchLottery, BdrealimeSpRsp bdrealimeSpRsp) throws BaseException;
}
