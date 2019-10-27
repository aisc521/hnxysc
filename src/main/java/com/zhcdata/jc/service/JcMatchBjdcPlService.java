package com.zhcdata.jc.service;

import com.github.pagehelper.PageInfo;
import com.zhcdata.db.model.JcMatchBjdcPl;
import com.zhcdata.db.model.JcMatchLottery;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.BdrealTimeSp.BdrealimeSpRsp;

import java.util.List;
import java.util.Map;

public interface JcMatchBjdcPlService {
    List<JcMatchBjdcPl> queryJcMatchBjdcPlByIssuemAndNoId(String issueNum, String noId);

    void updateJcMatchBjdcPl(List<JcMatchBjdcPl>  jcMatchBjdcPl, JcMatchLottery jcMatchLottery, BdrealimeSpRsp bdrealimeSpRsp) throws BaseException;

    void insertJcMatchBjdcPl(JcMatchLottery jcMatchLottery, BdrealimeSpRsp bdrealimeSpRsp) throws BaseException;

    PageInfo<Map<String, String>> queryBjdcListReuslt(int pageNo, int pageAmount, String date);

    int queryTodayMatchCount(String date);

    String queryTOdayMatchIssue(String date);

    List<JcMatchBjdcPl> queryBjdcByMatchId(Integer matchId);

    List<JcMatchBjdcPl> queryJcMatchBdPlByLottery();


    int upMatchBdRate(JcMatchBjdcPl jcMatchBjdcPl);
}
