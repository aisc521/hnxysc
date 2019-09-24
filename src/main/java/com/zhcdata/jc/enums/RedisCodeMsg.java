package com.zhcdata.jc.enums;

/**
 * CopyRight (c)1999-2017 : zhcdata.com
 * Project : win-the-goods
 * Comments : Redis Key和过期时长
 * JDK version : JDK1.8
 * Create Date : 2019-06-19 13:43
 *
 * @author : Yore
 * @version : 1.0
 * @since : 1.0
 */
public enum RedisCodeMsg {

    SOCCER_COMM_MSG_LIST("SOCCER_COMM_MSG_LIST", "操作消息", -1),
    SOCCER_HSET_ODDS_QT("SOCCER_HSET_ODDS_QT", "比赛赔率列表(球探)", 1 * 60 * 60 * 24 * 35),
    SOCCER_ODDS_DETAIL("SOCCER_ODDS_DETAIL", "比赛赔率详情", 1 * 60 * 60 * 24 * 35),
    SOCCER_LINEUP_DATA("SOCCER_LINEUP_DATA","阵容数据",1 * 60 * 60 * 24 * 30),
    SOCCER_ANALYSIS("SOCCER_ANALYSIS","分析(积分+近期+交锋)",1 * 60 * 60 * 24 * 30),
    PMS_TEXT_LIVE("PMS_TEXT_LIVE:","比赛文字直播缓存,按比赛存",1*60*60*24*25),
    SAME_ODDS("PMS_PEIlVDETAIL:HASH:","比赛赔率详情",1*60*60*24*35),

    SOCCER_SAME_ODDS_MATCH("SOCCER_SAME_ODDS_MATCH","同赔比赛列表",1 * 60 * 60 * 24 * 31),
    ;
    private String name;//缓存名

    private String message;//缓存备注

    private long seconds;//缓存失效时间

    RedisCodeMsg(String name, String message, long seconds) {
        this.name = name;
        this.message = message;
        this.seconds = seconds;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public long getSeconds() {
        return seconds;
    }

}
