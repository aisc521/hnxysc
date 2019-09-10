package com.zhcdata.jc.dto;

import com.zhcdata.jc.tools.Const;
import lombok.Data;

/**
 * CopyRight (c)1999-2017 : zhcw.com
 * Project : zhcw-framework
 * Comments : 响应数据统一封装
 * JDK version : JDK1.8
 * Create Date : 2017/7/7 11:16
 *
 * @author : Watson W
 * @version : 1.0
 * @since : 1.0
 */
@Data
public class JsonResult {

  private String msg;
  private boolean success;
  private Object data;

  public JsonResult() {
  }

  public JsonResult(String msg, boolean success, Object data) {
    this.msg = msg;
    this.success = success;
    this.data = data;
  }

  /**
   * 成功消息
   *
   * @param data 查询数据
   * @return 响应数据
   */
  public static JsonResult success(Object data) {
    return success(data, Const.SUCCESS_STR);
  }

  /**
   * 成功消息
   *
   * @param msg 成功消息
   * @return 响应数据
   */
  public static JsonResult successMsg(String msg) {
    return success(null, msg);
  }

  /**
   * 成功消息
   *
   * @param data 查询数据
   * @param msg  成功消息
   * @return 响应数据
   */
  public static JsonResult success(Object data, String msg) {
    return new JsonResult(msg, true, data);
  }

  /**
   * 失败消息
   *
   * @param msg 失败消息
   * @return 响应数据
   */
  public static JsonResult failure(String msg) {
    return new JsonResult(msg, false, null);
  }

  /**
   * 失败消息
   *
   * @param msg 失败消息
   * @param ex  异常信息
   * @return 响应数据
   */
  public static JsonResult failure(String msg, Object ex) {
    return new JsonResult(msg, false, ex);
  }

  @Override
  public String toString() {
    return "JsonResult{" +
      "msg='" + msg + '\'' +
      ", success=" + success +
      ", data=" + data +
      '}';
  }

}
