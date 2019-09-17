package com.zhcdata.jc.xml.rsp;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XStreamAlias("match")
public class LineupMatchRsp {
    String ScheduleID;
    String league;
    String matchtime;
    String home;
    String away;
}
