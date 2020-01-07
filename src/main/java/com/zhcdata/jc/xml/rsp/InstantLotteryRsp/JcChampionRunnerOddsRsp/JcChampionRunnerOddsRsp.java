package com.zhcdata.jc.xml.rsp.InstantLotteryRsp.JcChampionRunnerOddsRsp;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/11/13 17:04
 */
@Getter
@Setter
@XStreamAlias("match")
public class JcChampionRunnerOddsRsp {
    private String type;
    private String MatchID;
    private String Teams;
    private String Odds;
    private String IsEnd;
}
