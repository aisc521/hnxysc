package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.SeasonPlayerStatisticsMapper;
import com.zhcdata.db.model.SeasonPlayerStatistics;
import com.zhcdata.db.model.TbJcPurchaseDetailed;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.SeasonPlayerStatisticsService;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.PlayerStaticsRsp.PlayerStaticRsp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/11/12 16:38
 */
@Service
public class SeasonPlayerStatisticsServiceImpl implements SeasonPlayerStatisticsService {
    @Resource
    private SeasonPlayerStatisticsMapper seasonPlayerStatisticsMapper;
    @Override
    public SeasonPlayerStatistics queryByPlayIdLenIdSeason(String id, String leagueID, String matchSeason) {
        return seasonPlayerStatisticsMapper.queryByPlayIdLenIdSeason(id,leagueID,matchSeason);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSeasonPlayerStatistics(SeasonPlayerStatistics seasonPlayerStatistics, PlayerStaticRsp playerStaticRsp) throws BaseException {
        seasonPlayerStatistics = generateSeasonPlayerStatistics(seasonPlayerStatistics,playerStaticRsp,"0");

        Example example = new Example(SeasonPlayerStatistics.class);
        example.createCriteria().andEqualTo("id",seasonPlayerStatistics.getId());
        int i = seasonPlayerStatisticsMapper.updateByExample(seasonPlayerStatistics,example);
        if(i <= 0){
            throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                    ProtocolCodeMsg.UPDATE_FAILE.getMsg());
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertSeasonPlayerStatistics(PlayerStaticRsp playerStaticRsp) throws BaseException {
        SeasonPlayerStatistics seasonPlayerStatistics = new SeasonPlayerStatistics();
        seasonPlayerStatistics = generateSeasonPlayerStatistics(seasonPlayerStatistics,playerStaticRsp,"1");
        int i = seasonPlayerStatisticsMapper.insert(seasonPlayerStatistics);
        if(i <= 0){
            throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                    ProtocolCodeMsg.INSERT_FAILE.getMsg());
        }
    }

    public SeasonPlayerStatistics generateSeasonPlayerStatistics(SeasonPlayerStatistics seasonPlayerStatistics, PlayerStaticRsp playerStaticRsp,String flag){
        seasonPlayerStatistics.setPlayerId(Long.valueOf(playerStaticRsp.getID()));
        seasonPlayerStatistics.setTeamId(Long.valueOf(playerStaticRsp.getTeamID()));
        if("False".equals(playerStaticRsp.getIfHome())){
            seasonPlayerStatistics.setIfHome(Long.valueOf(1));
        }
        if("True".equals(playerStaticRsp.getIfHome())){
            seasonPlayerStatistics.setIfHome(Long.valueOf(0));
        }
        seasonPlayerStatistics.setLeagueId(Long.valueOf(playerStaticRsp.getLeagueID()));
        seasonPlayerStatistics.setMatchSeason(playerStaticRsp.getMatchSeason());
        seasonPlayerStatistics.setSchSum(Long.valueOf(playerStaticRsp.getSchSum()));
        seasonPlayerStatistics.setBackSum(Long.valueOf(playerStaticRsp.getBackSum()));
        seasonPlayerStatistics.setPlayTime(playerStaticRsp.getPlayingTime());
        seasonPlayerStatistics.setGoals(Long.valueOf(playerStaticRsp.getGoals()));
        seasonPlayerStatistics.setPenaltyGoals(Long.valueOf(playerStaticRsp.getPenaltyGoals()));
        seasonPlayerStatistics.setShots(Long.valueOf(playerStaticRsp.getShots()));
        seasonPlayerStatistics.setShotsTarget(Long.valueOf(playerStaticRsp.getShotsTarget()));
        seasonPlayerStatistics.setWasFouled(Long.valueOf(playerStaticRsp.getWasFouled()));
        seasonPlayerStatistics.setOffside(Long.valueOf(playerStaticRsp.getOffside()));
        seasonPlayerStatistics.setBestSum(Long.valueOf(playerStaticRsp.getBestSum()));
        seasonPlayerStatistics.setRating(Float.valueOf(playerStaticRsp.getRating()));
        seasonPlayerStatistics.setTotalPass(Long.valueOf(playerStaticRsp.getTotalPass()));
        seasonPlayerStatistics.setPassSuc(Long.valueOf(playerStaticRsp.getPassSuc()));
        seasonPlayerStatistics.setKeyPass(Long.valueOf(playerStaticRsp.getKeyPass()));
        seasonPlayerStatistics.setAssist(Long.valueOf(playerStaticRsp.getAssist()));
        seasonPlayerStatistics.setLongBall(Long.valueOf(playerStaticRsp.getLongBall()));
        seasonPlayerStatistics.setLongBallSuc(Long.valueOf(playerStaticRsp.getLongBallsSuc()));
        seasonPlayerStatistics.setThroughBall(Long.valueOf(playerStaticRsp.getThroughBall()));
        seasonPlayerStatistics.setThroughBallSuc(Long.valueOf(playerStaticRsp.getThroughBallSuc()));
        seasonPlayerStatistics.setDribblesSuc(Long.valueOf(playerStaticRsp.getDribblesSuc()));
        seasonPlayerStatistics.setCrossNum(Long.valueOf(playerStaticRsp.getCrossNum()));
        seasonPlayerStatistics.setCrossSuc(Long.valueOf(playerStaticRsp.getCrossSuc()));
        seasonPlayerStatistics.setTackle(Long.valueOf(playerStaticRsp.getTackle()));
        seasonPlayerStatistics.setInterception(Long.valueOf(playerStaticRsp.getInterception()));
        seasonPlayerStatistics.setClearance(Long.valueOf(playerStaticRsp.getClearance()));
        seasonPlayerStatistics.setDispossessed(Long.valueOf(playerStaticRsp.getDispossessed()));
        seasonPlayerStatistics.setShotsBlocked(Long.valueOf(playerStaticRsp.getShotsBlocked()));
        seasonPlayerStatistics.setAertalSuc(Long.valueOf(playerStaticRsp.getAerialSuc()));
        seasonPlayerStatistics.setFouls(Long.valueOf(playerStaticRsp.getFouls()));
        seasonPlayerStatistics.setRed(Long.valueOf(playerStaticRsp.getRed()));
        seasonPlayerStatistics.setYellow(Long.valueOf(playerStaticRsp.getYellow()));
        seasonPlayerStatistics.setTurnOver(Long.valueOf(playerStaticRsp.getTurnOver()));
        if("1".equals(flag)){//新增
            seasonPlayerStatistics.setCreateTime(new Date());
            seasonPlayerStatistics.setUpdateTime(new Date());
        }else{//更新
            seasonPlayerStatistics.setUpdateTime(new Date());
        }
        return seasonPlayerStatistics;
    }


}
