package com.zhcdata.jc.xml.rsp;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XStreamAlias("play")
public class PTSRsp {
    String ID;
    String TeamID;
    String number;
    String playerName;
    String positionName;
    String shots;
    String shotsTarget;
    String keyPass;
    String PassRate;
    String aerialWon;
    String touches;
    String dribblesWon;
    String wasFouled;
    String dispossessed;
    String turnOver;
    String Offsides;
    String tackles;
    String interception;
    String clearances;
    String clearanceWon;
    String shotsBlocked;
    String OffsideProvoked;
    String fouls;
    String totalPass;
    String accuratePass;
    String CrossNum;
    String CrossWon;
    String longBall;
    String longBallWon;
    String throughBall;
    String throughBallWon;
    String rating;
    String red;
    String yellow;
    String assist;
    String isFirstTeam;
    String PlayingTime;
    String goals;
    String penaltyGoals;
}
