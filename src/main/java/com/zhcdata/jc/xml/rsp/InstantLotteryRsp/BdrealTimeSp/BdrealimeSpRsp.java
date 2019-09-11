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
@XStreamAlias("match")
public class BdrealimeSpRsp {
    private String IssueNum;
    private String ID;
    private String MatchTime;
    private String Home;
    private String Away;
    @XStreamImplicit
    private List<BdrealimeSpSpfRsp> spf;
    @XStreamImplicit
    private List<BdrealimeSpBfRsp> bf;
    @XStreamImplicit
    private List<BdrealimeSpJqRsp> jq;
    @XStreamImplicit
    private List<BdrealimeSpBqcRsp> bqc;
    @XStreamImplicit
    private List<BdrealimeSpSxdsRsp> sxds;
}
