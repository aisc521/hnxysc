package com.zhcdata.jc.service;

import com.zhcdata.db.model.JcChampionRunnerOdds;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.JcChampionRunnerOddsRsp.JcChampionRunnerOddsRsp;

public interface JcChampionRunnerOddsService {

    void updateJcChampionRunnerOdds(JcChampionRunnerOdds jcChampionRunnerOdds, JcChampionRunnerOddsRsp jcChampionRunnerOddsRsp,int j);

    void insertJcChampionRunnerOdds(JcChampionRunnerOddsRsp jcChampionRunnerOddsRsp,int j);
}
