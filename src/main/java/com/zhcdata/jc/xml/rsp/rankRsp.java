package com.zhcdata.jc.xml.rsp;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XStreamAlias("i")
public class rankRsp {
    String type;
    String Name;
    String TeamID;
    String Area;
    String Rank;
    String ChgRank;
    String Score;
    String ChgScore;
    String update;
}
