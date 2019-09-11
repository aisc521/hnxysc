package com.zhcdata.jc.xml.rsp.InstantLotteryRsp.LotterMatchStatistics;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/11 13:42
 */
@Getter
@Setter
@XStreamAlias("list")
public class LotterMatchStatFirstRsp {
    @XStreamImplicit
    List<LotterMatchStatEventRsp> event;
    @XStreamImplicit
    List<LotterMatchStatTechnicRsp> technic;

}
