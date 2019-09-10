package com.zhcdata.jc.xml.rsp;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XStreamAlias("i")
public class PlayerRsp {
    String id;
    String PlayerID;
    String Name_J;
    String Name_F;
    String Name_E;
    String Birthday;
    String Tallness;
    String Weight;
    String Country;
    String Photo;
    String Health;
    String value;
    String feet;
    String Introduce;
    String TeamID;
    String Place;
    String Number;
    String EndDateContract;
}
