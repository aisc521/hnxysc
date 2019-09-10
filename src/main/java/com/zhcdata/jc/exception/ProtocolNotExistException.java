package com.zhcdata.jc.exception;

/**
 * CopyRight (c)1999-2017 : zhcw.com
 * Project : zhcw-framework
 * Comments : 协议未找到异常
 * JDK version : JDK1.8
 * Create Date : 2017/7/7 11:19
 *
 * @author : Watson W
 * @version : 1.0
 * @since : 1.0
 */
public class ProtocolNotExistException extends RuntimeException {

  private static final long serialVersionUID = 2436570927617844019L;

  private String retCd;  //异常对应的返回码
  private String msgDes;  //异常对应的描述信息

  public ProtocolNotExistException() {
    super();
  }

  public ProtocolNotExistException(String message) {
    super(message);
    msgDes = message;
  }

  public ProtocolNotExistException(String retCd, String msgDes) {
    super();
    this.retCd = retCd;
    this.msgDes = msgDes;
  }

  public String getRetCd() {
    return retCd;
  }

  public String getMsgDes() {
    return msgDes;
  }

}
