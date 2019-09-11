package com.zhcdata.jc.xml.rsp.InstantLotteryRsp.BdrealTimeSp;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/11 11:25
 */
@Getter
@Setter
@XStreamAlias("list")
public class BdrealimeSpFirstRsp {
    @XStreamImplicit
    List<BdrealimeSpRsp> list;
}
