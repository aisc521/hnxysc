/*create by xuan on 2019/9/21

 */
package com.zhcdata.jc.xml.rsp.EuropeHundredOddsRsp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class H {
    private String id;

    private String time;

    private String league;

    private String home;

    private String away;

    private Odds odds;

}