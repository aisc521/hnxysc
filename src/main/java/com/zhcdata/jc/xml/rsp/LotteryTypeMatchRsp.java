package com.zhcdata.jc.xml.rsp;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/10 19:09
 */
@Getter
@Setter
@XStreamAlias("i")
public class LotteryTypeMatchRsp {
    private String LotteryName;
    private String IssueNum;
    private String ID;
    private String ID_bet007;
    private String time;
    private String sport;
    private String Home;
    private String Away;
    private String HomeID;
    private String AwayID;
    private String Turn;
    private String league;
    private String RecordID;

}
