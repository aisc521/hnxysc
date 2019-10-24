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
 * 　　　　　　　　　┃　　　┃　　　　create by xuan on 2019/10/9
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
package com.zhcdata.jc.quartz.job.Odds;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FlagInfo {

    public static boolean europe_odds_flag = false;

    public static List<String> europe_odds_mc = new ArrayList<>();//存的是 比赛id：公司id

    public static List<String> multi_yp = new ArrayList<>();//存的是 比赛id:公司id:盘口号 亚赔（让球盘）

    public static List<String> multi_ou = new ArrayList<>();//存的是 比赛id:公司id:盘口号 欧赔

    public static List<String> multi_dx = new ArrayList<>();//存的是 比赛id:公司id:盘口号 大小球

    public static List<String> multi_hyp = new ArrayList<>();//存的是 比赛id:公司id:盘口号 半场雅培

    public static List<String> multi_hdx = new ArrayList<>();//存的是 比赛id:公司id:盘口号 半场大小球

    //public static Map<String,Long> MATCH_START_TIME = new LinkedHashMap<>();//存的是 比赛id，开始时间毫秒数

}