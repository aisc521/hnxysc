package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.JcChampionRunnerOddsMapper;
import com.zhcdata.db.model.JcChampionRunnerOdds;
import com.zhcdata.jc.service.JcChampionRunnerOddsService;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.JcChampionRunnerOddsRsp.JcChampionRunnerOddsRsp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/11/13 16:57
 */
@Service
public class JcChampionRunnerOddsServiceImpl implements JcChampionRunnerOddsService {
    @Resource
    private JcChampionRunnerOddsMapper jcChampionRunnerOddsMapper;


    @Override
    public JcChampionRunnerOdds queryJcChampionRunnerOddsByTypeAndMatchIdAndTeams(String typeRsp, String matchID, String teams) {
        return jcChampionRunnerOddsMapper.queryJcChampionRunnerOddsByTypeAndMatchIdAndTeams(typeRsp,matchID,teams);
    }

    @Override
    public void updateJcChampionRunnerOdds(JcChampionRunnerOdds jcChampionRunnerOdds, JcChampionRunnerOddsRsp jcChampionRunnerOddsRsp,int j) {
        jcChampionRunnerOdds.setOddsType(Long.valueOf(j));
        String typeRsp = jcChampionRunnerOddsRsp.getType();
        /*if(typeRsp.indexOf("冠亚军") != -1){//包含冠亚军  是 冠亚军玩法
            jcChampionRunnerOdds.setGameType(Long.valueOf(2));
        }else{//冠军玩法
            jcChampionRunnerOdds.setGameType(Long.valueOf(1));
        }*/
    }

    @Override
    public void insertJcChampionRunnerOdds(JcChampionRunnerOddsRsp jcChampionRunnerOddsRsp,int j) {

    }
}
