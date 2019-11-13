package com.zhcdata.jc.service;

import com.zhcdata.db.model.SeasonPlayerStatistics;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.PlayerStaticsRsp.PlayerStaticRsp;

public interface SeasonPlayerStatisticsService {
    SeasonPlayerStatistics queryByPlayIdLenIdSeason(String id, String leagueID, String matchSeason);

    void updateSeasonPlayerStatistics(SeasonPlayerStatistics seasonPlayerStatistics, PlayerStaticRsp playerStaticRsp) throws BaseException;

    void insertSeasonPlayerStatistics(PlayerStaticRsp playerStaticRsp) throws BaseException;
}
