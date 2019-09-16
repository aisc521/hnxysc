package com.zhcdata.jc.xml.rsp;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XStreamAlias("i")
public class TeamRsp {
    String id;
    String lsID;
    String g;
    String b;
    String e;
    String Found;
    String Area;
    String gym;
    String Capacity;
    String Flag;
    String addr;
    String URL;
    String master;
}
