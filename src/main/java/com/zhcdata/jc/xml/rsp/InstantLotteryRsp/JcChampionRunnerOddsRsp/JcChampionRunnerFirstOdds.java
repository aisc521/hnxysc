package com.zhcdata.jc.xml.rsp.InstantLotteryRsp.JcChampionRunnerOddsRsp;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/11/13 17:04
 */
@Getter
@Setter
@XStreamAlias("list")
public class JcChampionRunnerFirstOdds {
    @XStreamImplicit
    List<JcChampionRunnerOddsRsp> list;
}
