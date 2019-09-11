package com.zhcdata.jc.xml.rsp.InstantLotteryRsp.Odds;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/11 10:39
 */
@Getter
@Setter
@XStreamAlias("bqc")
public class JcFootBallOddsBqcRsp {
    private String ht33;
    private String ht31;
    private String ht30;
    private String ht13;
    private String ht11;
    private String ht10;
    private String ht03;
    private String ht01;
    private String ht00;
    private String stop;

}
