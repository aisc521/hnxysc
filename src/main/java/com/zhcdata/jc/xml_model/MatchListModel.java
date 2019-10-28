/*create by xuan on 2019/9/10

 */
package com.zhcdata.jc.xml_model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MatchListModel {
    private String a;//比赛id
    private String b;//颜色值
    private String c;//联赛/杯赛信息:简体名,繁体名,英文名,联赛/杯赛ID,是否是精简版
    private String d;//比赛时间
    private String e;//联赛子类型
    private int f;//比赛状态
    //private String g;//
    private String h;//主队信息:简体名,繁体名,英文名,主队ID
    private String i;//客队信息:简体名,繁体名,英文名,客队ID
    private int j;//主队比分
    private int k;//客队比分
    private String l;//主队半场比分
    private String m;//客队半场比分
    private String n;//主队红牌
    private String o;//客队红牌
    private String p;//主队排名
    private String q;//客队排名
    private String r;//赛事说明
    private String s;//轮次/分组名
    private String t;//比赛地点
    private String u;//天气图标
    private String v;//天气
    private String w;//温度
    private String x;//赛季
    private String y;//小组分组
    private String z;//是否中立场
    private String subID;//子联赛id
    private String yellow;//黄牌数
    private String explain2;//比赛说明2
    private String hidden;//是否隐藏
}