package com.zhcdata.jc.xml.rsp.InstantLotteryRsp.BjDcLotteryQuery;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/11 13:25
 */
@Getter
@Setter
@XStreamAlias("match")
public class BjDcLotteryQueryRsp {
    private String IssueNum;
    private String ID;
    private String Home;
    private String Away;
    private String rf;
    private String jq;
    private String ds;
    private String bf;
    private String bqc;

}
