package com.zhcdata.jc.xml.rsp.InstantLotteryRsp.PlayerStaticsRsp;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/11/12 16:44
 */
@Getter
@Setter
@XStreamAlias("list")
public class PlayerStaticFirstRsp {
    @XStreamImplicit
    List<PlayerStaticRsp> list;
}
