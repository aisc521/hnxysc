package com.zhcdata.jc.exception;

import java.io.Serializable;
import java.util.Map;

/**
 * CopyRight (c)1999-2017 : zhcw.com
 * Project : zhcw-framework
 * Comments : BaseException
 * JDK version : JDK1.8
 * Create Date : 2017/7/7 11:18
 *
 * @author : Watson W
 * @version : 1.0
 * @since : 1.0
 */
public class BaseException extends Exception implements Serializable {

  /**
   * the serialVersionUID
   */
  private static final long serialVersionUID = 3476277440732294172L;

  private Map<String, String> paramMap;

  /**
   * message key
   */
  private String code;

  /**
   * message params
   */
  private Object[] values;

  /**
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * @param code the code to set
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * @return the values
   */
  public Object[] getValues() {
    return values;
  }

  /**
   * @param values the values to set
   */
  public void setValues(Object[] values) {
    this.values = values;
  }

  public BaseException(String message, Throwable cause, String code, Object[] values) {
    super(message, cause);
    this.code = code;
    this.values = values;
  }

  public BaseException(String code, String message) {
    super(message);
    this.code = code;
  }

  public BaseException(String code, String message, Map<String, String> map) {
    super(message);
    this.code = code;
    this.paramMap = map;
  }

  public BaseException(String code) {
    this.code = code;
  }

  public Map<String, String> getParamMap() {
    return paramMap;
  }

  public void setParamMap(Map<String, String> paramMap) {
    this.paramMap = paramMap;
  }

}
