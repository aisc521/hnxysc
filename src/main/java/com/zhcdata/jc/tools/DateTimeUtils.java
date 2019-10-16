package com.zhcdata.jc.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Title:
 * Description: 时间工具类
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author cuishuai
 * @version 1.0
 * @Date 2019/6/25 09:37
 */
public class DateTimeUtils {

  public static String getIs(Date date) {
    String week = "";
    Map<String, String> r = new HashMap();
    r.put("date", new SimpleDateFormat("MM-dd").format(new Date()));
    r.put("time", new SimpleDateFormat("HH:mm").format(new Date()));

    //获取当天是周几,周末不会执行
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    switch (calendar.get(Calendar.DAY_OF_WEEK)) {
      case Calendar.MONDAY:
        week = "周一";
        break;
      case Calendar.TUESDAY:
        week = "周二";
        break;
      case Calendar.WEDNESDAY:
        week = "周三";
        break;
      case Calendar.THURSDAY:
        week = "周四";
        break;
      case Calendar.FRIDAY:
        week = "周五";
        break;
      case Calendar.SATURDAY:
        week = "周六";
        break;
      case Calendar.SUNDAY:
        week = "周日";
        break;

    }
    return week;
  }

}
