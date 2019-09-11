package com.zhcdata.jc.xml.rsp.InstantLotteryRsp.LotterMatchStatistics;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/11 13:43
 */
@Getter
@Setter
@XStreamAlias("event")
public class LotterMatchStatEventRsp {
    private String item;
}
