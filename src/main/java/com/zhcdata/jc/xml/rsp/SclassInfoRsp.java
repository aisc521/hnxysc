package com.zhcdata.jc.xml.rsp;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XStreamAlias("match")
public class SclassInfoRsp {
    String id;
    String color;
    String gb_short;
    String big_short;
    String en_short;
    String gb;
    String big;
    String en;
    String type;
    String subSclass;
    String sum_round;
    String curr_round;
    String Curr_matchSeason;
    String countryID;
    String country;
    String areaID;
    String countryEn;
    String logo;
    String countryLogo;
}
