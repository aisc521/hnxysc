/**
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　　　 ████━████ ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　create by xuan on 2019/10/25
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 */
package com.zhcdata.jc.tools;

public class TimeFormatUtils {
    public String parseToFormat(String str) {
        //e:2019/9/9 0:0:0
        String temp = "0";
        String[] date = str.split(" ")[0].split("/");
        String[] time = str.split(" ")[1].split(":");

        String y = date[0];
        if (y.length() == 1) y = temp + y;
        String m = date[1];
        if (m.length() == 1) m = temp + m;
        String d = date[2];
        if (d.length() == 1) d = temp + d;

        String h = time[0];
        if (h.length() == 1) h = temp + h;
        String f = time[1];
        if (f.length() == 1) f = temp + f;
        String s = time[2];
        if (s.length() == 1) s = temp + s;

        return y + "-" + m + "-" + d + " " + h + ":" + f + ":" + s;
    }
}