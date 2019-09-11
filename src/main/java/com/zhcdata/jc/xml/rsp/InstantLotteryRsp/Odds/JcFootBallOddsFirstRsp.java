package com.zhcdata.jc.xml.rsp.InstantLotteryRsp.Odds;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/11 10:42
 */
@Getter
@Setter
@XStreamAlias("list")
public class JcFootBallOddsFirstRsp {
    @XStreamImplicit
    List<JcFootBallOddsRsp> list;
}
