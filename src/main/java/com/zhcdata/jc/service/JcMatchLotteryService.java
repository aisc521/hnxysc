package com.zhcdata.jc.service;

import com.zhcdata.db.model.JcMatchLottery;

import java.util.List;

public interface JcMatchLotteryService {
    /**
     * 根据期号和NoId查询
     * @param issueNum
     * @param noId
     * @return
     */
    JcMatchLottery queryJcMatchLotteryByIssueNumAndNoId(String issueNum, String noId);

    List<JcMatchLottery> queryJcMatchLotteryByMatchIdAndType(Integer matchId);
}
