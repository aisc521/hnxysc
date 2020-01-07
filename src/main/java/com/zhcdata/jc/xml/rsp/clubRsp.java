package com.zhcdata.jc.xml.rsp;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XStreamAlias("Item")
public class clubRsp {
    String ID;
    String PlayerID;
    String transferTime;
    String endTime;
    String Team;
    String TeamNow;
    String TeamID;
    String TeamNowID;
    String Money;
    String Place;
    String ZH_Season;
    String type;
}
