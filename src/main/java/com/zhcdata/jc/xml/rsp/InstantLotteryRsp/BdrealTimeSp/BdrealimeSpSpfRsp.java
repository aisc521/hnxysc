package com.zhcdata.jc.xml.rsp.InstantLotteryRsp.BdrealTimeSp;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/11 11:26
 */
@Getter
@Setter
@XStreamAlias("spf")
public class BdrealimeSpSpfRsp {
    private String goal;
    private String sf3;
    private String sf1;
    private String sf0;
}
