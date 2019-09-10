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

  GOODS_COMM_MSG_LIST("GOODS_COMM_MSG_LIST", "操作消息", -1),
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
