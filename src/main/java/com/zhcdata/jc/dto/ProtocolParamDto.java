package com.zhcdata.jc.dto;

import java.util.Map;

/**
 * CopyRight (c)1999-2017 : zhcw.com
 * Project : ai-center
 * Comments : 协议参数传输对象
 * JDK version : JDK1.8
 * Create Date : 2017/7/7 17:27
 *
 * @author : Watson W
 * @version : 1.0
 * @since : 1.0
 */
public class ProtocolParamDto {

  private Message message;

  public Message getMessage() {
    return message;
  }

  public void setMessage(Message message) {
    this.message = message;
  }

  public static class Message {

    private HeadBean head;

    private Map<String, Object> body;

    public HeadBean getHead() {
      return head;
    }

    public void setHead(HeadBean head) {
      this.head = head;
    }

    public Map getBody() {
      return body;
    }

    public void setBody(Map body) {
      //noinspection unchecked
      this.body = body;
    }

  }

  @SuppressWarnings("unused")
  public static class HeadBean {

    /**
     * 消息编号
     * messengerID+14位时间戳YYYYMMDDHHmmss+2位随机号。
     * 请求与应答的消息序列号必须相同。
     */
    private String messageID;

    /**
     * 时间戳
     * 请求方时间戳，消息发出时系统的当前时间。
     * 格式：yyyyMMddHHmmss
     */
    private String timeStamp;

    /**
     * 标志渠道信息
     */
    private String src;

    /**
     * 系统类型（M- 马上奖，F-疯抢，A-账户）
     */
    private String sysType;

    /**
     * 消息发送方编号 000101
     */
    private String messengerID;

    /**
     * 交易效验类型。
     * 交易类型。每个交易包括请求和响应，当前支持以下交易类型：
     */
    private String transactionType;

    /**
     * 手机信息
     */
    private String ua;

    /**
     * 移动识别码
     */
    private String imei;

    /**
     * 标识有设备唯一标识
     */
    private String deviceId;

    /**
     * 对消息包的摘要, 摘要算法为MD5+base64，摘要的内容为
     * 先对内容进行base64编码,再对内容进行MD5加密
     * （messageID+timeStamp+messengerID+platform+transactionType+密码+消息体） 密码=weichat@$^000101
     */
    private String digest;

    /**
     * 处理状态
     */
    private String resCode;

    /**
     * 处理消息
     */
    private String message;

    public String getMessageID() {
      return messageID;
    }

    public HeadBean setMessageID(String messageID) {
      this.messageID = messageID;
      return this;
    }

    public String getTimeStamp() {
      return timeStamp;
    }

    public HeadBean setTimeStamp(String timeStamp) {
      this.timeStamp = timeStamp;
      return this;
    }

    public String getSrc() {
      return src;
    }

    public HeadBean setSrc(String src) {
      this.src = src;
      return this;
    }

    public String getSysType() {
      return sysType;
    }

    public HeadBean setSysType(String sysType) {
      this.sysType = sysType;
      return this;
    }

    public String getMessengerID() {
      return messengerID;
    }

    public HeadBean setMessengerID(String messengerID) {
      this.messengerID = messengerID;
      return this;
    }

    public String getTransactionType() {
      return transactionType;
    }

    public HeadBean setTransactionType(String transactionType) {
      this.transactionType = transactionType;
      return this;
    }

    public String getUa() {
      return ua;
    }

    public HeadBean setUa(String ua) {
      this.ua = ua;
      return this;
    }

    public String getImei() {
      return imei;
    }

    public HeadBean setImei(String imei) {
      this.imei = imei;
      return this;
    }

    public String getDeviceId() {
      return deviceId;
    }

    public HeadBean setDeviceId(String deviceId) {
      this.deviceId = deviceId;
      return this;
    }

    public String getDigest() {
      return digest;
    }

    public HeadBean setDigest(String digest) {
      this.digest = digest;
      return this;
    }

    public String getResCode() {
      return resCode;
    }

    public HeadBean setResCode(String resCode) {
      this.resCode = resCode;
      return this;
    }

    public String getMessage() {
      return message;
    }

    public HeadBean setMessage(String message) {
      this.message = message;
      return this;
    }
  }

}
