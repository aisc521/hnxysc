package com.zhcdata.jc.xml.rsp;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XStreamAlias("match")
public class LineupRsp {
    String ID;
    String homeArray;
    String awayArray;
    String HomeLineup_cn;
    String AwayLineup_cn;
    String HomeBackup_cn;
    String AwayBackup_cn;
    String HomeLineup_big5;
    String AwayLineup_big5;
    String HomeBackup_big5;
    String AwayBackup_big5;
}
