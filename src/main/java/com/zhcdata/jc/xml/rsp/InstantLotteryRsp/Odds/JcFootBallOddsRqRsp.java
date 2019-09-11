package com.zhcdata.jc.xml.rsp.InstantLotteryRsp.Odds;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/11 10:18
 */
@Getter
@Setter
@XStreamAlias("rq")
public class JcFootBallOddsRqRsp {
    private String goal;
    private String rq3;
    private String rq1;
    private String rq0;
    private String stop;

}
