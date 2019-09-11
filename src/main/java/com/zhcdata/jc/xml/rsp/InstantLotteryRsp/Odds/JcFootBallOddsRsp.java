package com.zhcdata.jc.xml.rsp.InstantLotteryRsp.Odds;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/11 10:16
 */
@Getter
@Setter
@XStreamAlias("match")
public class JcFootBallOddsRsp {
    private String ID;
    private String MatchTime;
    private String league;
    private String Home;
    private String Away;
    private String dan_rq;
    private String dan_bf;
    private String dan_jq;
    private String dan_bqc;
    private String dan_sf;
    @XStreamImplicit
    private List<JcFootBallOddsRqRsp> rq;
    @XStreamImplicit
    private List<JcFootBallOddsBfRsp> bf;
    @XStreamImplicit
    private List<JcFootBallOddsJqRsp> jq;
    @XStreamImplicit
    private List<JcFootBallOddsBqcRsp> bqc;
    @XStreamImplicit
    private List<JcFootBallOddsSfRsp> sf;

}
