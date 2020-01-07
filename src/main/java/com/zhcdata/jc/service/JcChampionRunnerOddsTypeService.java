package com.zhcdata.jc.service;

import com.zhcdata.db.model.JcChampionRunnerOddsType;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.JcChampionRunnerOddsRsp.JcChampionRunnerOddsRsp;

public interface JcChampionRunnerOddsTypeService {
    JcChampionRunnerOddsType queryJcChampionRunnerOddsTypeByPlayTypeNameAndGameType(String typeRsp, String type);

    int updataJcChampionRunnerOddsType(JcChampionRunnerOddsType jcChampionRunnerOddsType, JcChampionRunnerOddsRsp jcChampionRunnerOddsRsp) throws BaseException;

    int insertJcChampionRunnerOddsType(JcChampionRunnerOddsRsp jcChampionRunnerOddsRsp) throws BaseException;
}
