package com.zhcdata.jc.xml.rsp.InstantLotteryRsp.PlayerStaticsRsp;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/11/12 16:45
 */
@Getter
@Setter
@XStreamAlias("play")
public class PlayerStaticRsp {
    private String TeamID;
    private String ifHome;
    private String ID;
    private String leagueID;
    private String matchSeason;
    private String SchSum;
    private String BackSum;
    private String PlayingTime;
    private String goals;
    private String penaltyGoals;
    private String shots;
    private String shotsTarget;
    private String wasFouled;
    private String offside;
    private String bestSum;
    private String rating;
    private String totalPass;
    private String passSuc;
    private String keyPass;
    private String assist;
    private String longBall;
    private String longBallsSuc;
    private String throughBall;
    private String throughBallSuc;
    private String dribblesSuc;
    private String CrossNum;
    private String CrossSuc;
    private String tackle;
    private String interception;
    private String clearance;
    private String dispossessed;
    private String shotsBlocked;
    private String aerialSuc;
    private String fouls;
    private String red;
    private String yellow;
    private String turnOver;
    private String modifyTime;
}
