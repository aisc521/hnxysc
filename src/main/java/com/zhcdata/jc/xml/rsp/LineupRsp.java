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
    String HomeLineup;
    String AwayLineup;
    String HomeBackup;
    String AwayBackup;
}
