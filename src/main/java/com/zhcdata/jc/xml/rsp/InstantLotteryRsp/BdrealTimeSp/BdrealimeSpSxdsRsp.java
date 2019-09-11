package com.zhcdata.jc.xml.rsp.InstantLotteryRsp.BdrealTimeSp;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/11 11:32
 */
@Getter
@Setter
@XStreamAlias("sxds")
public class BdrealimeSpSxdsRsp {
    private String ds11;
    private String ds10;
    private String ds01;
    private String ds00;
}
