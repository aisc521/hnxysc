package com.zhcdata.jc.xml.rsp.InstantLotteryRsp.Odds;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/11 10:40
 */
@Getter
@Setter
@XStreamAlias("sf")
public class JcFootBallOddsSfRsp {
    private String sf3;
    private String sf1;
    private String sf0;
    private String stop;
}
