package com.zhcdata.jc.xml.rsp;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * CopyRight (c)1999-2019 : zhcw.com
 * Project : jc-new-server
 * Create Date : 2019/9/10 15:02
 * JDK version : JDK1.8
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 *
 * @author : 高阳
 * @version : 0.0.1
 */
@Getter
@Setter
@XStreamAlias("match")
public class ToDayMatchRsp {
  String ID;//比赛Id
  String color;//颜色值
  String leagueID;//联赛id
  String kind;//类型 1:联赛，2:杯赛
  String level;//是否是重要比赛; 【是否是重要比赛】0:一般赛事，1:重要赛事
  String league;//简体名,繁体名,英文名
  String subLeague;//子联赛名称
  String subLeagueID;//子联赛ID
  String time;//比赛时间
  String time2;//开场时间;
  String home;//主队信息简体名,繁体名,英文名,主队ID
  String away;//客队信息:简体名,繁体名,英文名,客队ID
  String state;
  String homeScore;
  String awayScore;
  String bc1;
  String bc2;
  String red1;
  String red2;
  String yellow1;
  String yellow2;
  String order1;
  String order2;
  String explain;
  String zl;
  String tv;
  String lineup;
  String explain2;
  String corner1;
  String corner2;

}
