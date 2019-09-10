package com.zhcdata.jc.converter;

import com.google.common.base.Strings;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * CopyRight (c)1999-2017 : zhcw.com
 * Project : zhcw-framework
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 * JDK version : JDK1.8
 * Create Date : 2017/7/7 17:14
 *
 * @author : Watson W
 * @version : 1.0
 * @since : 1.0
 */
public class DateConverter implements Converter<String, Date> {

  private static final DateTimeFormatter DATE = DateTimeFormat.forPattern("yyyy-MM-dd");
  private static final DateTimeFormatter DATE_TIME = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
  private static final DateTimeFormatter TIMESTAMP = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");

  @Override
  public Date convert(String s) {
    if (Strings.isNullOrEmpty(s)) {
      return null;
    } else if (10 == s.length()) {
      return DateTime.parse(s, DATE).toDate();
    } else if (19 == s.length()) {
      return DateTime.parse(s, DATE_TIME).toDate();
    } else if (23 == s.length()) {
      return DateTime.parse(s, TIMESTAMP).toDate();
    } else {
      return null;
    }
  }

}
