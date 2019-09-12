package com.zhcdata.jc.xml.rsp;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XStreamAlias("Item")
public class SubSclassRsp {
    String id;
    String subID;
    String Name;
    String Num;
    String sum_round;
    String curr_round;
    String IsHaveScore;
    String IncludeSeason;
    String IsCurrentSclass;
    String CurrentSeason;
    String IsZu;
    String Name_big5;
    String Name_en;
}
