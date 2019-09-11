package com.zhcdata.jc.xml.rsp.InstantLotteryRsp.LotteryScore;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/11 13:33
 */
@Getter
@Setter
@XStreamAlias("match")
public class LotteryScoreRsp {
    private String ID;
    private String color;
    private String leagueID;
    private String kind;
    private String level;
    private String league;
    private String subLeague;
    private String subLeagueID;
    private String time;
    private String time2;
    private String home;
    private String away;
    private String state;
    private String homeScore;
    private String awayScore;
    private String bc1;
    private String bc2;
    private String red1;
    private String red2;
    private String yellow1;
    private String yellow2;
    private String order1;
    private String order2;
    private String explain;
    private String zl;
    private String tv;
    private String lineup;
    private String explain2;
    private String corner1;
    private String corner2;
}
