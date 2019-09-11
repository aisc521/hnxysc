package com.zhcdata.jc.xml.rsp.InstantLotteryRsp.LotterType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/10 19:09
 */
@Getter
@Setter
@XStreamAlias("match")
public class LotteryTypeMatchFristRsp {
    @XStreamImplicit
    List<LotteryTypeMatchRsp> list;
}
