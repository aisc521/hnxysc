package com.zhcdata.jc.tools;

import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Strings;
import com.zhcdata.db.model.JcMatchBjdcPl;
import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.db.model.JcSchedulesp;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.BdrealTimeSp.*;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.BjDcLotteryQuery.BjDcLotteryQueryRsp;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.Odds.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/12 9:50
 */
@Slf4j
public class JcLotteryUtils {
    //北单竞彩
    private static final String matchNameStr = "英冠,英甲,英乙,英足总杯,英联赛杯,英锦标赛,社区盾杯,意乙,意大利杯,意超杯,西乙,国王杯,西超杯,德乙,德国杯,德超杯,法乙,法联赛杯,法国杯,法超杯,葡超,葡甲,葡萄牙杯,葡联赛杯,葡超杯,荷甲,荷乙,荷兰杯,荷超杯,俄超,俄甲,俄罗斯杯,俄超杯,苏超,苏冠,苏联赛杯,苏足总杯,挪超,挪甲,挪威杯,挪超杯,瑞超,瑞甲,瑞典杯,瑞超杯,比甲,比乙,比利时杯,比超杯,捷甲,捷克杯,捷超杯,土超,土超杯,希超,希腊杯,瑞士超,瑞士甲,瑞士杯,以超,以色列杯,丹超,丹甲,丹麦杯,奥甲,奥乙,澳地利杯,波甲,波兰杯,波超杯,爱超,爱甲,爱联赛杯,爱足总杯,匈甲,匈牙利杯,匈联赛杯,匈超杯,芬超,芬甲,芬联赛杯,芬兰杯,罗甲,罗杯,罗超杯,克甲,克杯,乌超,乌克兰杯,乌超杯,冰超,冰甲,冰岛杯,冰超杯,巴甲,巴乙,巴保罗锦,巴西杯,阿甲,阿乙,阿根延杯,阿超杯,智利甲,智利杯,智超杯,美职足,公开赛杯,墨超,墨西哥杯,墨超杯,墨冠杯,中足杯,中超杯,日职,日乙,天皇杯,日联赛杯,日超杯,韩职,韩挑战联,韩足总杯,澳超,澳杯,欧洲杯,欧冠,世欧预,欧预赛,欧罗巴,欧国联,欧超杯,欧青赛,女欧洲杯,美洲杯,解放者杯,世南美预,俱乐部杯,优胜者杯,世亚预,中美洲杯,中北美冠,金杯赛,亚洲杯,亚冠,世亚预,亚预赛,四强赛,亚青赛,亚运男足,亚运女足,西亚锦,东南亚锦,女亚洲杯,女四强赛,非洲杯,世非预,非预赛,非国锦,世界杯,女世界杯,奥运男足,奥运女足,联合会杯,世预附加,世青赛,世俱杯,国冠杯,中国杯,世预赛";
    //五大联赛
    private static final String fiveLsStr = "英超,意甲,德甲,西甲,法甲";
    /**
     * 彩种标识
     * 1、14场胜负彩    足彩   JC_ZC
     * 2、六场半全场          JC_6_HAF_ALL
     * 3、四场进球彩          JC_14
     * 4、单场让球胜平负 北单  JC_BD
     * 5、竞彩足球      竞彩  JC_JC
     * 6、竞彩篮球           JC_LQ
     * 7、北京单场胜负过关    JC_BD_SF
     */
    public static String JcLotterZh(String lottery){
        String lotteryName = "";
        if("14场胜负彩".equals(lottery)){
            lotteryName = "JC_ZC";
        }
        if("六场半全场".equals(lottery)){
            lotteryName = "JC_6_HAF_ALL";
        }
        if("四场进球彩".equals(lottery)){
            lotteryName = "JC_14";
        }
        if("单场让球胜平负".equals(lottery)){
            lotteryName = "JC_BD";
        }
        if("竞彩足球".equals(lottery)){
            lotteryName = "JC_JC";
        }
        if("竞彩篮球".equals(lottery)){
            lotteryName = "JC_LQ";
        }
        if("北京单场胜负过关".equals(lottery)){
            lotteryName = "JC_BD_SF";
        }
        return lotteryName;
    }

    /**
     *
     * @return
     */
    public static String matchZh(String macthName){
        String[] matchNameArr = matchNameStr.split(",");
        String type = "4";
        for(int i = 0; i< matchNameArr.length; i++){
            String macth = matchNameArr[i];
            if(macthName.equals(macth)){
                type = "3";
            }
        }
        String[] fiveLsArr = fiveLsStr.split(",");
        for(int j = 0; j < fiveLsArr.length; j++){
            String fiveLs = fiveLsArr[j];
            if(macthName.equals(fiveLs)){
                type = "2";
            }
        }
        return type;
    }

    /**
     * 北京单场赔率信息字符串拼接
     * 玩法类型
             12-比分
             13-总进球
             14-半全场胜平负
             15-让球胜平负
     */
    public static String bJDcPlInfo(BdrealimeSpRsp bdrealimeSpRsp,String type){
        String resultInfo = "";
        if("12".equals(type)){//比分
            List<BdrealimeSpBfRsp> bfList =  bdrealimeSpRsp.getBf();
            BdrealimeSpBfRsp bdrealimeSpBfRsp = bfList.get(0);
            resultInfo = "平:" + "00:" + bdrealimeSpBfRsp.getSd00() + "|" +
                         "主负:" + "01:" + bdrealimeSpBfRsp.getSl01() + "|" +
                         "主负:" + "02:" + bdrealimeSpBfRsp.getSl02() + "|" +
                         "主负:" + "03:" + bdrealimeSpBfRsp.getSl03() + "|" +
                         "平:" + "11:" + bdrealimeSpBfRsp.getSd11() + "|" +
                         "主负:" + "12:" + bdrealimeSpBfRsp.getSl12() + "|" +
                         "主负:" + "13:" + bdrealimeSpBfRsp.getSl13() + "|" +
                         "主胜:" + "20:" + bdrealimeSpBfRsp.getSw20() + "|" +
                         "主胜:" + "21:" + bdrealimeSpBfRsp.getSw21() + "|" +
                         "平:" + "22:" + bdrealimeSpBfRsp.getSd22() + "|" +
                         "主负:" + "23:" + bdrealimeSpBfRsp.getSl23() + "|" +
                         "主胜:" + "30:" + bdrealimeSpBfRsp.getSw30() + "|" +
                         "主胜:" + "31:" + bdrealimeSpBfRsp.getSw31() + "|" +
                         "主胜:" + "32:" + bdrealimeSpBfRsp.getSw32() + "|" +
                         "平：" + "33:" + bdrealimeSpBfRsp.getSd33() + "|" +
                         "主胜:" + "40:" + bdrealimeSpBfRsp.getSw40() + "|" +
                         "主胜:" + "41:" + bdrealimeSpBfRsp.getSw41() + "|" +
                         "主胜:" + "42:" + bdrealimeSpBfRsp.getSw42() + "|" +
                         "主负:" + "04:" + bdrealimeSpBfRsp.getSl04() + "|" +
                         "主负:" + "14:" + bdrealimeSpBfRsp.getSl14() + "|" +
                         "主负:" + "24:" + bdrealimeSpBfRsp.getSl24();



        }
        if("13".equals(type)){//总进球
            List<BdrealimeSpJqRsp> jqList =  bdrealimeSpRsp.getJq();
            BdrealimeSpJqRsp bdrealimeSpJqRsp = jqList.get(0);
            resultInfo = "进球:" + "0:" + bdrealimeSpJqRsp.getT0() + "|" +
                         "进球:" + "1:" + bdrealimeSpJqRsp.getT0() + "|" +
                         "进球:" + "2:" + bdrealimeSpJqRsp.getT0() + "|" +
                         "进球:" + "3:" + bdrealimeSpJqRsp.getT0() + "|" +
                         "进球:" + "4:" + bdrealimeSpJqRsp.getT0() + "|" +
                         "进球:" + "5:" + bdrealimeSpJqRsp.getT0() + "|" +
                         "进球:" + "6:" + bdrealimeSpJqRsp.getT0() + "|" +
                         "进球:" + "7:" + bdrealimeSpJqRsp.getT0();
        }
        if("14".equals(type)){//半全场 3表示胜利，1表示平，0表示负，前面的数字表示全场，后面的数字表示半场。例：<33>表示全场胜半场胜赔率。
            List<BdrealimeSpBqcRsp> bqcList =  bdrealimeSpRsp.getBqc();
            BdrealimeSpBqcRsp bdrealimeSpBqcRsp = bqcList.get(0);
            resultInfo = "半全场:" + "33:" + bdrealimeSpBqcRsp.getHt33() + "|" +
                         "半全场:" + "31:" + bdrealimeSpBqcRsp.getHt31() + "|" +
                         "半全场:" + "30:" + bdrealimeSpBqcRsp.getHt30() + "|" +
                         "半全场:" + "13:" + bdrealimeSpBqcRsp.getHt13() + "|" +
                         "半全场:" + "11:" + bdrealimeSpBqcRsp.getHt11() + "|" +
                         "半全场:" + "10:" + bdrealimeSpBqcRsp.getHt10() + "|" +
                         "半全场:" + "03:" + bdrealimeSpBqcRsp.getHt03() + "|" +
                         "半全场:" + "01:" + bdrealimeSpBqcRsp.getHt01() + "|" +
                         "半全场:" + "00:" + bdrealimeSpBqcRsp.getHt00();
        }
        if("15".equals(type)){//让球胜平负 3表示主胜，1表示平，0表示主负
            List<BdrealimeSpSpfRsp> spfList =  bdrealimeSpRsp.getSpf();
            BdrealimeSpSpfRsp bdrealimeSpBqcRsp = spfList.get(0);
            List<BdrealimeSpBfRsp> bfList =  bdrealimeSpRsp.getBf();
            BdrealimeSpBfRsp bdrealimeSpBfRsp = bfList.get(0);
            resultInfo = "让球胜平负:" + "3:" + bdrealimeSpBqcRsp.getSf3() + "|" +
                         "让球胜平负:" + "1:" + bdrealimeSpBqcRsp.getSf1() + "|" +
                         "让球胜平负:" + "0:" + bdrealimeSpBqcRsp.getSf0() + "" +
                         "胜其他:" + "4:" + bdrealimeSpBfRsp.getSw4() + "|" +
                         "平其他:" + "4:" + bdrealimeSpBfRsp.getSd4() + "|" +
                         "负其他:" + "4:" + bdrealimeSpBfRsp.getSl4();
        }
        if("16".equals(type)){//上下单双  11 上单  10 上双 01 下单 00 下双
            List<BdrealimeSpSxdsRsp> sxds = bdrealimeSpRsp.getSxds();
            BdrealimeSpSxdsRsp bdrealimeSpSxdsRsp = sxds.get(0);
            resultInfo = "上下单双:" + "11:" + bdrealimeSpSxdsRsp.getDs11() + "|" +
                         "上下单双:" + "10:" + bdrealimeSpSxdsRsp.getDs10() + "|" +
                         "上下单双:" + "01:" + bdrealimeSpSxdsRsp.getDs01() + "|" +
                         "上下单双:" + "00:" + bdrealimeSpSxdsRsp.getDs00();
        }
        return resultInfo;
    }
    /**
     * 赛果生成
     */
    public static String gameResult(Schedule schedule,List<JcMatchBjdcPl> jcMatchBjdcPls,BjDcLotteryQueryRsp bjDcLotteryQueryRsp,String type){
        String resultInfo = "";
        //判断是否是完场 完场的数据计算赛果信息
        if(schedule.getMatchstate() == -1){//完场
            //胜平负
            String spf = spf(schedule,jcMatchBjdcPls,bjDcLotteryQueryRsp,type);
            //半全场
            String bqc = bqc(schedule,jcMatchBjdcPls,bjDcLotteryQueryRsp,type);
            //比分
            String bf = bf(schedule,jcMatchBjdcPls,bjDcLotteryQueryRsp,type);
            //进球
            String jq = jq(schedule,jcMatchBjdcPls,bjDcLotteryQueryRsp,type);
            //上下单双
            String sxds = sxds(schedule,jcMatchBjdcPls,bjDcLotteryQueryRsp,type);
            resultInfo = spf + "|" + bqc + "|" + bf + "|" + jq + "|" + sxds;
            return resultInfo;
        }else{
            log.error("赛程Id为:" + schedule.getScheduleid() + "的比赛还没有完场，无法计算赛果等信息");
        }
        return resultInfo;
    }

    public static String spf(Schedule schedule,List<JcMatchBjdcPl> jcMatchBjdcPls,BjDcLotteryQueryRsp bjDcLotteryQueryRsp,String type){
        String conCedNum = "";
        String spf = "";
        for(int i = 0; i < jcMatchBjdcPls.size(); i++){
            JcMatchBjdcPl jcMatchBjdcPl1 = jcMatchBjdcPls.get(i);
            if("15".equals(jcMatchBjdcPl1.getLotteryPlay())){
                conCedNum = jcMatchBjdcPl1.getConCedNum();
            }
        }
        Integer result = Integer.parseInt(String.valueOf(schedule.getHomescore())) + Integer.parseInt(conCedNum);
        Integer game = result - Integer.parseInt(String.valueOf(schedule.getGuestscore()));
        if(game > 0){
            if("1".equals(type)){
                spf = "11@" + "胜_" + bjDcLotteryQueryRsp.getRf();
            }
            if("2".equals(type)){
                spf = "胜_" + bjDcLotteryQueryRsp.getRf();
            }

        }
        if(game == 0){
            if("1".equals(type)){
                spf = "11@" + "平_" + bjDcLotteryQueryRsp.getRf();
            }
            if("2".equals(type)){
                spf = "平_" + bjDcLotteryQueryRsp.getRf();
            }

        }
        if(game < 0){
            if("1".equals(type)){
                spf = "11@" + "负_" + bjDcLotteryQueryRsp.getRf();
            }
            if("2".equals(type)){
                spf = "负_" + bjDcLotteryQueryRsp.getRf();
            }

        }
        return spf;
    }

    public static String bqc(Schedule schedule,List<JcMatchBjdcPl> jcMatchBjdcPls,BjDcLotteryQueryRsp bjDcLotteryQueryRsp,String type){
        Short homeScore = schedule.getHomescore();
        Short gestScore = schedule.getGuestscore();
        Short homeHalfScore = schedule.getHomehalfscore();
        Short guestHalfScore = schedule.getGuesthalfscore();
        String bqcGame = "";
        String bqc = "";
        if(Integer.parseInt(String.valueOf(homeHalfScore)) > Integer.parseInt(String.valueOf(guestHalfScore))){//半场胜
            //全场比分
            if(Integer.parseInt(String.valueOf(homeScore)) > Integer.parseInt(String.valueOf(gestScore))){//全场胜
                bqcGame = "胜胜_";
            }
            if(Integer.parseInt(String.valueOf(homeScore)) == Integer.parseInt(String.valueOf(gestScore))){
                bqcGame = "胜平_";
            }
            if(Integer.parseInt(String.valueOf(homeScore)) < Integer.parseInt(String.valueOf(gestScore))){
                bqcGame = "胜负_";
            }
        }
        if(Integer.parseInt(String.valueOf(homeHalfScore)) == Integer.parseInt(String.valueOf(guestHalfScore))){//半场平
            //全场比分
            if(Integer.parseInt(String.valueOf(homeScore)) > Integer.parseInt(String.valueOf(gestScore))){//全场胜
                bqcGame = "平胜_";
            }
            if(Integer.parseInt(String.valueOf(homeScore)) == Integer.parseInt(String.valueOf(gestScore))){
                bqcGame = "平平_";
            }
            if(Integer.parseInt(String.valueOf(homeScore)) < Integer.parseInt(String.valueOf(gestScore))){
                bqcGame = "平负_";
            }
        }
        if(Integer.parseInt(String.valueOf(homeHalfScore)) < Integer.parseInt(String.valueOf(guestHalfScore))){//半场负
            if(Integer.parseInt(String.valueOf(homeScore)) > Integer.parseInt(String.valueOf(gestScore))){//全场胜
                bqcGame = "负胜_";
            }
            if(Integer.parseInt(String.valueOf(homeScore)) == Integer.parseInt(String.valueOf(gestScore))){
                bqcGame = "负平_";
            }
            if(Integer.parseInt(String.valueOf(homeScore)) < Integer.parseInt(String.valueOf(gestScore))){
                bqcGame = "负负_";
            }
        }
        if("1".equals(type)){
            bqc = "14@" + bqcGame + bjDcLotteryQueryRsp.getBqc();
        }
        if("2".equals(type)){
            bqc = bqcGame + bjDcLotteryQueryRsp.getBqc();
        }

        return bqc;
    }

    public static String bf(Schedule schedule,List<JcMatchBjdcPl> jcMatchBjdcPls,BjDcLotteryQueryRsp bjDcLotteryQueryRsp,String type){
        Short homeScore = schedule.getHomescore();
        Short gestScore = schedule.getGuestscore();
        String bfGame = homeScore + ":" + gestScore + "_";
        String bf = "";
        if("1".equals(type)){
             bf = "12@" + bfGame + bjDcLotteryQueryRsp.getBf();
        }
        if("2".equals(type)){
             bf = bfGame + bjDcLotteryQueryRsp.getBf();
        }

        return bf;
    }
    public static String jq(Schedule schedule,List<JcMatchBjdcPl> jcMatchBjdcPls,BjDcLotteryQueryRsp bjDcLotteryQueryRsp,String type){
        Short homeScore = schedule.getHomescore();
        Short gestScore = schedule.getGuestscore();
        Integer jqGame = Integer.parseInt(String.valueOf(homeScore) + Integer.parseInt(String.valueOf(gestScore)));
        String jq = "";
        if("1".equals(type)){
             jq = "13@" + jqGame + "_" + bjDcLotteryQueryRsp.getJq();
        }
        if("2".equals(type)){
             jq = jqGame + "_" + bjDcLotteryQueryRsp.getJq();
        }
        return jq;
    }

    public static String sxds (Schedule schedule,List<JcMatchBjdcPl> jcMatchBjdcPls,BjDcLotteryQueryRsp bjDcLotteryQueryRsp,String type){
        Short homeScore = schedule.getHomescore();
        Short gestScore = schedule.getGuestscore();
        Integer A = homeScore + gestScore;
        String sxds = "";
        if(A >= 3){//上
            if((A & 1) == 1){//奇数
                if("1".equals(type)){
                    sxds = "16@" + "上单_" + bjDcLotteryQueryRsp.getDs();
                }
                if("2".equals(type)){
                    sxds = "上单_" + bjDcLotteryQueryRsp.getDs();
                }
            }else{//偶数
                if("1".equals(type)){
                    sxds = "16@" + "上双_" + bjDcLotteryQueryRsp.getDs();
                }
                if("2".equals(type)){
                    sxds = "上双_" + bjDcLotteryQueryRsp.getDs();
                }
            }
        }
        if(A < 3){//下
            if((A & 1) == 1){//奇数
                if("1".equals(type)){
                    sxds = "16@" + "下单_" + bjDcLotteryQueryRsp.getDs();
                }
                if("2".equals(type)){
                    sxds = "下单_" + bjDcLotteryQueryRsp.getDs();
                }
            }else{//偶数
                if("1".equals(type)){
                    sxds = "16@" + "下双_" + bjDcLotteryQueryRsp.getDs();
                }
                if("2".equals(type)){
                    sxds = "下双_" + bjDcLotteryQueryRsp.getDs();
                }
            }
        }
        return sxds;

    }

    /**
     * 计算开奖sp
     */
    public static String JcKjSp(JcFootBallOddsRsp jcFootBallOddsRsp, Schedule schedule, JcSchedule jcSchedule, String type){
        Short homeScore = schedule.getHomescore();
        Short gestScore = schedule.getGuestscore();
        Short homeHalfScore = schedule.getHomehalfscore();
        Short guestHalfScore = schedule.getGuesthalfscore();
        //"11"));//让球胜平负开奖sp
        //"12"));//进球数开奖sp
        //"13"));//比分开奖sp
        //"14"));//半全场开奖sp
        //"15"));//胜平负开奖sp
        String sp = "";
        if("11".equals(type)){
            List<JcFootBallOddsRqRsp> jcFootBallOddsRqRsps = jcFootBallOddsRsp.getRq();
            JcFootBallOddsRqRsp jcFootBallOddsRqRsp = jcFootBallOddsRqRsps.get(0);
            Float rqNum = jcSchedule.getPolygoal();
            Float bf = Float.parseFloat(String.valueOf(homeScore)) + rqNum;
            if(bf > Float.parseFloat(String.valueOf(gestScore))){//胜
                sp = jcFootBallOddsRqRsp.getRq3();
            }
            if(bf == Float.parseFloat(String.valueOf(gestScore))){//平
                sp = jcFootBallOddsRqRsp.getRq1();
            }
            if(bf < Float.parseFloat(String.valueOf(gestScore))){//负
                sp = jcFootBallOddsRqRsp.getRq0();
            }
        }
        if("12".equals(type)){
            List<JcFootBallOddsJqRsp> jcFootBallOddsRqRsps = jcFootBallOddsRsp.getJq();
            JcFootBallOddsJqRsp jcFootBallOddsJqRsp = jcFootBallOddsRqRsps.get(0);
            Integer jiNum =  homeScore + gestScore;
            if(jiNum == 0){
                sp = jcFootBallOddsJqRsp.getT0();
            }
            if(jiNum == 1){
                sp = jcFootBallOddsJqRsp.getT1();
            }
            if(jiNum == 2){
                sp = jcFootBallOddsJqRsp.getT2();
            }
            if(jiNum == 3){
                sp = jcFootBallOddsJqRsp.getT3();
            }
            if(jiNum == 4){
                sp = jcFootBallOddsJqRsp.getT4();
            }
            if(jiNum == 5){
                sp = jcFootBallOddsJqRsp.getT5();
            }
            if(jiNum == 6){
                sp = jcFootBallOddsJqRsp.getT6();
            }
            if(jiNum == 7){
                sp = jcFootBallOddsJqRsp.getT7();
            }
        }
        if("13".equals(type)){
            List<JcFootBallOddsBfRsp> bfList = jcFootBallOddsRsp.getBf();
            JcFootBallOddsBfRsp jcFootBallOddsBfRsp = bfList.get(0);
            String bf = String.valueOf(homeScore) + String.valueOf(gestScore);
            switch (bf){
                case  "00" :
                    sp = jcFootBallOddsBfRsp.getSd00();
                    break;
                case  "01" :
                    sp = jcFootBallOddsBfRsp.getSl01();
                    break;
                case  "02" :
                    sp = jcFootBallOddsBfRsp.getSl02();
                    break;
                case  "03" :
                    sp = jcFootBallOddsBfRsp.getSl03();
                    break;
                case  "10" :
                    sp = jcFootBallOddsBfRsp.getSw10();
                    break;
                case  "11" :
                    sp = jcFootBallOddsBfRsp.getSd11();
                    break;
                case  "12" :
                    sp = jcFootBallOddsBfRsp.getSl12();
                    break;
                case  "13" :
                    sp = jcFootBallOddsBfRsp.getSl13();
                    break;
                case  "20" :
                    sp = jcFootBallOddsBfRsp.getSw20();
                    break;
                case  "21" :
                    sp = jcFootBallOddsBfRsp.getSw21();
                    break;
                case  "22" :
                    sp = jcFootBallOddsBfRsp.getSd22();
                    break;
                case  "23" :
                    sp = jcFootBallOddsBfRsp.getSl23();
                    break;
                case  "30" :
                    sp = jcFootBallOddsBfRsp.getSw30();
                    break;
                case  "31" :
                    sp = jcFootBallOddsBfRsp.getSw31();
                    break;
                case  "32" :
                    sp = jcFootBallOddsBfRsp.getSw32();
                    break;
                case  "33" :
                    sp = jcFootBallOddsBfRsp.getSd33();
                    break;
                case  "40" :
                    sp = jcFootBallOddsBfRsp.getSw40();
                    break;
                case  "41" :
                    sp = jcFootBallOddsBfRsp.getSw41();
                    break;
                case  "42" :
                    sp = jcFootBallOddsBfRsp.getSw42();
                    break;
                case  "04" :
                    sp = jcFootBallOddsBfRsp.getSl04();
                    break;
                case  "14" :
                    sp = jcFootBallOddsBfRsp.getSl14();
                    break;
                case  "24" :
                    sp = jcFootBallOddsBfRsp.getSl24();
                    break;
                case  "50" :
                    sp = jcFootBallOddsBfRsp.getSw50();
                    break;
                case  "51" :
                    sp = jcFootBallOddsBfRsp.getSw51();
                    break;
                case  "52" :
                    sp = jcFootBallOddsBfRsp.getSw52();
                    break;
                case  "05" :
                    sp = jcFootBallOddsBfRsp.getSl05();
                    break;
                case  "15" :
                    sp = jcFootBallOddsBfRsp.getSl15();
                    break;
                case  "25" :
                    sp = jcFootBallOddsBfRsp.getSl25();
                    break;
                default:
                    if(homeScore > gestScore){//胜其他
                        sp = jcFootBallOddsBfRsp.getSw5();
                    }
                    if(homeScore == gestScore){//平其他
                        sp = jcFootBallOddsBfRsp.getSd4();
                    }
                    if(homeScore < gestScore){//负其他
                        sp = jcFootBallOddsBfRsp.getSl4();
                    }
                break;
            }
        }
        if("14".equals(type)){
            List<JcFootBallOddsBqcRsp> bqcRspList = jcFootBallOddsRsp.getBqc();
            JcFootBallOddsBqcRsp jcFootBallOddsBqcRsp = bqcRspList.get(0);
            //3 胜 1 平 0 负
            String bqc = "";
            if(homeHalfScore > guestHalfScore){
                if(homeScore > gestScore){
                    bqc = "33";
                }
                if(homeScore == gestScore){
                    bqc = "31";
                }
                if(homeScore < gestScore){
                    bqc = "30";
                }
            }
            if(homeHalfScore == guestHalfScore){
                if(homeScore > gestScore){
                    bqc = "13";
                }
                if(homeScore == gestScore){
                    bqc = "11";
                }
                if(homeScore < gestScore){
                    bqc = "10";
                }
            }
            if(homeHalfScore < guestHalfScore){
                if(homeScore > gestScore){
                    bqc = "03";
                }
                if(homeScore == gestScore){
                    bqc = "01";
                }
                if(homeScore < gestScore){
                    bqc = "00";
                }
            }
            switch (bqc){
                case "33" :
                    sp = jcFootBallOddsBqcRsp.getHt33();
                    break;
                case "31" :
                    sp = jcFootBallOddsBqcRsp.getHt31();
                    break;
                case "30" :
                    sp = jcFootBallOddsBqcRsp.getHt30();
                    break;
                case "13" :
                    sp = jcFootBallOddsBqcRsp.getHt13();
                    break;
                case "11" :
                    sp = jcFootBallOddsBqcRsp.getHt11();
                    break;
                case "10" :
                    sp = jcFootBallOddsBqcRsp.getHt10();
                    break;
                case "03" :
                    sp = jcFootBallOddsBqcRsp.getHt03();
                    break;
                case "01" :
                    sp = jcFootBallOddsBqcRsp.getHt01();
                    break;
                case "00" :
                    sp = jcFootBallOddsBqcRsp.getHt00();
                    break;
            }
        }
        if("15".equals(type)){
            List<JcFootBallOddsSfRsp> sf = jcFootBallOddsRsp.getSf();
            JcFootBallOddsSfRsp jcFootBallOddsSfRsp = sf.get(0);
            if(homeScore > gestScore){//胜
                sp = jcFootBallOddsSfRsp.getSf3();
            }
            if(homeScore == gestScore){//平
                sp = jcFootBallOddsSfRsp.getSf1();
            }
            if(homeScore < gestScore){//负
                sp = jcFootBallOddsSfRsp.getSf0();
            }
        }
        return sp;
    }

    /**
     * 计算竞彩变化
     * @return
     */
    public static String jcSpChange(JcFootBallOddsRsp jcFootBallOddsRsp, JcSchedulesp jcSchedulesp,String type,Schedule schedule){
        String result = "";
        switch (type){
            case "101"://让球胜平负
                List<JcFootBallOddsRqRsp> rq = jcFootBallOddsRsp.getRq();
                JcFootBallOddsRqRsp jcFootBallOddsRqRsp = rq.get(0);
                if(jcSchedulesp == null){
                    JSONArray rs = new JSONArray();
                    rs.add(jcFootBallOddsRqRsp.getRq3());
                    rs.add(0.00);


                    JSONArray rp = new JSONArray();
                    rp.add(jcFootBallOddsRqRsp.getRq1());
                    rp.add(0.00);


                    JSONArray rf = new JSONArray();
                    rf.add(jcFootBallOddsRqRsp.getRq0());
                    rf.add(0.00);
                    result = rs.toString() + "," + rp.toString() + "," + rf.toString();
                }else{
                    JSONArray rs = new JSONArray();
                    rs.add(jcFootBallOddsRqRsp.getRq3());
                    Double rsChange = Double.parseDouble(jcFootBallOddsRqRsp.getRq3()) - Double.parseDouble(jcSchedulesp.getWl3());
                    BigDecimal b = new BigDecimal(rsChange);
                    rs.add(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());


                    JSONArray rp = new JSONArray();
                    rp.add(jcFootBallOddsRqRsp.getRq1());
                    Double rpChange = Double.parseDouble(jcFootBallOddsRqRsp.getRq1()) - Double.parseDouble(jcSchedulesp.getWl1());
                    BigDecimal b1 = new BigDecimal(rpChange);
                    rp.add(b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());


                    JSONArray rf = new JSONArray();
                    rf.add(jcFootBallOddsRqRsp.getRq0());
                    Double rfChange = Double.parseDouble(jcFootBallOddsRqRsp.getRq0()) - Double.parseDouble(jcSchedulesp.getWl0());
                    BigDecimal b2 = new BigDecimal(rfChange);
                    rf.add(b2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    result = rs.toString() + "," + rp.toString() + "," + rf.toString();
                }
                break;
            case "102":
                List<JcFootBallOddsBfRsp> bf = jcFootBallOddsRsp.getBf();
                JcFootBallOddsBfRsp jcFootBallOddsBfRsp = bf.get(0);
                JSONArray oo = new JSONArray();
                oo.add(jcFootBallOddsBfRsp.getSd00());
                JSONArray o1 = new JSONArray();
                o1.add(jcFootBallOddsBfRsp.getSl01());
                JSONArray o2 = new JSONArray();
                o2.add(jcFootBallOddsBfRsp.getSl02());
                JSONArray o3 = new JSONArray();
                o3.add(jcFootBallOddsBfRsp.getSl03());
                JSONArray oneo = new JSONArray();
                oneo.add(jcFootBallOddsBfRsp.getSw10());
                JSONArray one1 = new JSONArray();
                one1.add(jcFootBallOddsBfRsp.getSd11());
                JSONArray one2 = new JSONArray();
                one2.add(jcFootBallOddsBfRsp.getSl12());
                JSONArray one3 = new JSONArray();
                one3.add(jcFootBallOddsBfRsp.getSl13());
                JSONArray twoo = new JSONArray();
                twoo.add(jcFootBallOddsBfRsp.getSw20());
                JSONArray two1 = new JSONArray();
                two1.add(jcFootBallOddsBfRsp.getSw21());
                JSONArray two2 = new JSONArray();
                two2.add(jcFootBallOddsBfRsp.getSd22());
                JSONArray two3 = new JSONArray();
                two3.add(jcFootBallOddsBfRsp.getSl23());
                JSONArray threeo = new JSONArray();
                threeo.add(jcFootBallOddsBfRsp.getSw30());
                JSONArray three1 = new JSONArray();
                three1.add(jcFootBallOddsBfRsp.getSw31());
                JSONArray three2 = new JSONArray();
                three2.add(jcFootBallOddsBfRsp.getSw32());
                JSONArray three3 = new JSONArray();
                three3.add(jcFootBallOddsBfRsp.getSd33());
                JSONArray fouro = new JSONArray();
                fouro.add(jcFootBallOddsBfRsp.getSw40());
                JSONArray four1 = new JSONArray();
                four1.add(jcFootBallOddsBfRsp.getSw41());
                JSONArray four2 = new JSONArray();
                four2.add(jcFootBallOddsBfRsp.getSw42());
                JSONArray o4 = new JSONArray();
                o4.add(jcFootBallOddsBfRsp.getSl04());
                JSONArray one4 = new JSONArray();
                one4.add(jcFootBallOddsBfRsp.getSl14());
                JSONArray two4 = new JSONArray();
                two4.add(jcFootBallOddsBfRsp.getSl24());
                JSONArray w = new JSONArray();
                w.add(jcFootBallOddsBfRsp.getSw5());
                JSONArray d = new JSONArray();
                d.add(jcFootBallOddsBfRsp.getSd4());
                JSONArray l = new JSONArray();
                l.add(jcFootBallOddsBfRsp.getSl4());
                if(jcSchedulesp == null){//比分
                    oo.add(0.00);
                    o1.add(0.00);
                    o2.add(0.00);
                    o3.add(0.00);
                    oneo.add(0.00);
                    one1.add(0.00);
                    one2.add(0.00);
                    one3.add(0.00);
                    twoo.add(0.00);
                    two1.add(0.00);
                    two2.add(0.00);
                    two3.add(0.00);
                    threeo.add(0.00);
                    three1.add(0.00);
                    three2.add(0.00);
                    three3.add(0.00);
                    fouro.add(0.00);
                    four1.add(0.00);
                    four2.add(0.00);
                    o4.add(0.00);
                    one4.add(0.00);
                    two4.add(0.00);
                    w.add(0.00);
                    d.add(0.00);
                    l.add(0.00);
                }else{
                    Double ooChange = Double.parseDouble(jcFootBallOddsBfRsp.getSd00()) - Double.parseDouble(jcSchedulesp.getSd00());
                    BigDecimal b1 = new BigDecimal(ooChange);
                    oo.add(b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double o1Change = Double.parseDouble(jcFootBallOddsBfRsp.getSl01()) - Double.parseDouble(jcSchedulesp.getSl01());
                    BigDecimal b2 = new BigDecimal(o1Change);
                    o1.add(b2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double o2Change = Double.parseDouble(jcFootBallOddsBfRsp.getSl02()) - Double.parseDouble(jcSchedulesp.getSl02());
                    BigDecimal b3 = new BigDecimal(o2Change);
                    o2.add(b3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double o3Change = Double.parseDouble(jcFootBallOddsBfRsp.getSl03()) - Double.parseDouble(jcSchedulesp.getSl03());
                    BigDecimal b4 = new BigDecimal(o3Change);
                    o3.add(b4.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double one0Change = Double.parseDouble(jcFootBallOddsBfRsp.getSw10()) - Double.parseDouble(jcSchedulesp.getSw10());
                    BigDecimal b5 = new BigDecimal(one0Change);
                    oneo.add(b5.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double one1Change = Double.parseDouble(jcFootBallOddsBfRsp.getSd11()) - Double.parseDouble(jcSchedulesp.getSd11());
                    BigDecimal b6 = new BigDecimal(one1Change);
                    one1.add(b6.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double one2Change = Double.parseDouble(jcFootBallOddsBfRsp.getSl12()) - Double.parseDouble(jcSchedulesp.getSl12());
                    BigDecimal b7 = new BigDecimal(one2Change);
                    one2.add(b7.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double one3Change = Double.parseDouble(jcFootBallOddsBfRsp.getSl13()) - Double.parseDouble(jcSchedulesp.getSl13());
                    BigDecimal b8 = new BigDecimal(one3Change);
                    one3.add(b8.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double two0Change = Double.parseDouble(jcFootBallOddsBfRsp.getSw20()) - Double.parseDouble(jcSchedulesp.getSw20());
                    BigDecimal b9 = new BigDecimal(two0Change);
                    twoo.add(b9.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double two1Change = Double.parseDouble(jcFootBallOddsBfRsp.getSw21()) - Double.parseDouble(jcSchedulesp.getSw21());
                    BigDecimal b10 = new BigDecimal(two1Change);
                    two1.add(b10.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double two2Change = Double.parseDouble(jcFootBallOddsBfRsp.getSd22()) - Double.parseDouble(jcSchedulesp.getSd22());
                    BigDecimal b11 = new BigDecimal(two2Change);
                    two2.add(b11.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double two3Change = Double.parseDouble(jcFootBallOddsBfRsp.getSl23()) - Double.parseDouble(jcSchedulesp.getSl23());
                    BigDecimal b12 = new BigDecimal(two3Change);
                    two3.add(b12.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double three0Change = Double.parseDouble(jcFootBallOddsBfRsp.getSw30()) - Double.parseDouble(jcSchedulesp.getSw30());
                    BigDecimal b13 = new BigDecimal(three0Change);
                    threeo.add(b13.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());


                    Double three1Change = Double.parseDouble(jcFootBallOddsBfRsp.getSw31()) - Double.parseDouble(jcSchedulesp.getSw31());
                    BigDecimal b14 = new BigDecimal(three1Change);
                    three1.add(b14.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double three2Change = Double.parseDouble(jcFootBallOddsBfRsp.getSw32()) - Double.parseDouble(jcSchedulesp.getSw32());
                    BigDecimal b15 = new BigDecimal(three2Change);
                    three2.add(b15.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double three3Change = Double.parseDouble(jcFootBallOddsBfRsp.getSd33()) - Double.parseDouble(jcSchedulesp.getSd33());
                    BigDecimal b16 = new BigDecimal(three3Change);
                    three3.add(b16.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double four0Change = Double.parseDouble(jcFootBallOddsBfRsp.getSw40()) - Double.parseDouble(jcSchedulesp.getSw40());
                    BigDecimal b17 = new BigDecimal(four0Change);
                    fouro.add(b17.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double four1Change = Double.parseDouble(jcFootBallOddsBfRsp.getSw41()) - Double.parseDouble(jcSchedulesp.getSw41());
                    BigDecimal b18 = new BigDecimal(four1Change);
                    four1.add(b18.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double four2Change = Double.parseDouble(jcFootBallOddsBfRsp.getSw42()) - Double.parseDouble(jcSchedulesp.getSw42());
                    BigDecimal b19 = new BigDecimal(four2Change);
                    four2.add(b19.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double o4Change = Double.parseDouble(jcFootBallOddsBfRsp.getSl04()) - Double.parseDouble(jcSchedulesp.getSl04());
                    BigDecimal b20 = new BigDecimal(o4Change);
                    o4.add(b20.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double one4Change = Double.parseDouble(jcFootBallOddsBfRsp.getSl14()) - Double.parseDouble(jcSchedulesp.getSl14());
                    BigDecimal b21 = new BigDecimal(one4Change);
                    one4.add(b21.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double two4Change = Double.parseDouble(jcFootBallOddsBfRsp.getSl24()) - Double.parseDouble(jcSchedulesp.getSl24());
                    BigDecimal b22 = new BigDecimal(two4Change);
                    two4.add(b22.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double wChange = Double.parseDouble(jcFootBallOddsBfRsp.getSw5()) - Double.parseDouble(jcSchedulesp.getSw5());
                    BigDecimal b23 = new BigDecimal(wChange);
                    w.add(b23.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double dChange = Double.parseDouble(jcFootBallOddsBfRsp.getSd4()) - Double.parseDouble(jcSchedulesp.getSd4());
                    BigDecimal b24 = new BigDecimal(dChange);
                    d.add(b24.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double lChange = Double.parseDouble(jcFootBallOddsBfRsp.getSl4()) - Double.parseDouble(jcSchedulesp.getSl5());
                    BigDecimal b25 = new BigDecimal(lChange);
                    l.add(b25.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
                result = oo.toString() + "," + o1.toString() + "," + o2.toString()+ "," + o3.toString()+ "," + oneo.toString() + "," + one1.toString() + "," + one2.toString() + "," + one3.toString()
                         + "," + twoo.toString() + "," + two1.toString() + "," + two2.toString() + "," + two3.toString()
                         + "," + threeo.toString()+ "," + three1.toString()+ "," + three2.toString()+ "," + three3.toString()
                         + "," + fouro.toString()+ "," + four1.toString()+ "," + four2.toString()
                         + "," + o4.toString()+ "," + one4.toString()+ "," + two4.toString()
                         + "," + w.toString()+ "," + d.toString()+ "," + l.toString();
                break;
            case "103":
                List<JcFootBallOddsJqRsp> jq = jcFootBallOddsRsp.getJq();
                JcFootBallOddsJqRsp jcFootBallOddsJqRsp = jq.get(0);
                JSONArray zore = new JSONArray();
                zore.add(jcFootBallOddsJqRsp.getT0());
                JSONArray one = new JSONArray();
                one.add(jcFootBallOddsJqRsp.getT1());
                JSONArray two = new JSONArray();
                two.add(jcFootBallOddsJqRsp.getT2());
                JSONArray three = new JSONArray();
                three.add(jcFootBallOddsJqRsp.getT3());
                JSONArray four = new JSONArray();
                four.add(jcFootBallOddsJqRsp.getT4());
                JSONArray five = new JSONArray();
                five.add(jcFootBallOddsJqRsp.getT5());
                JSONArray six = new JSONArray();
                six.add(jcFootBallOddsJqRsp.getT6());
                JSONArray seven = new JSONArray();
                seven.add(jcFootBallOddsJqRsp.getT7());
                if(jcSchedulesp == null){
                    zore.add(0.00);
                    one.add(0.00);
                    two.add(0.00);
                    three.add(0.00);
                    four.add(0.00);
                    five.add(0.00);
                    six.add(0.00);
                    seven.add(0.00);
                }else{
                    Double zoreChange = Double.parseDouble(jcFootBallOddsJqRsp.getT0()) - Double.parseDouble(jcSchedulesp.getT0());
                    BigDecimal b1 = new BigDecimal(zoreChange);
                    zore.add(b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double oneChange = Double.parseDouble(jcFootBallOddsJqRsp.getT1()) - Double.parseDouble(jcSchedulesp.getT1());
                    BigDecimal b2 = new BigDecimal(oneChange);
                    one.add(b2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double twoChange = Double.parseDouble(jcFootBallOddsJqRsp.getT2()) - Double.parseDouble(jcSchedulesp.getT2());
                    BigDecimal b3 = new BigDecimal(twoChange);
                    two.add(b3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double threeChange = Double.parseDouble(jcFootBallOddsJqRsp.getT3()) - Double.parseDouble(jcSchedulesp.getT3());
                    BigDecimal b4 = new BigDecimal(threeChange);
                    three.add(b4.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double fourChange = Double.parseDouble(jcFootBallOddsJqRsp.getT4()) - Double.parseDouble(jcSchedulesp.getT4());
                    BigDecimal b5 = new BigDecimal(fourChange);
                    four.add(b5.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double fiveChange = Double.parseDouble(jcFootBallOddsJqRsp.getT5()) - Double.parseDouble(jcSchedulesp.getT5());
                    BigDecimal b6 = new BigDecimal(fiveChange);
                    five.add(b6.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double sixChange = Double.parseDouble(jcFootBallOddsJqRsp.getT6()) - Double.parseDouble(jcSchedulesp.getT6());
                    BigDecimal b7 = new BigDecimal(sixChange);
                    six.add(b7.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

                    Double sevenChange = Double.parseDouble(jcFootBallOddsJqRsp.getT7()) - Double.parseDouble(jcSchedulesp.getT7());
                    BigDecimal b8 = new BigDecimal(sevenChange);
                    seven.add(b8.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                }
                zore.add(0.00);
                one.add(0.00);
                two.add(0.00);
                three.add(0.00);
                four.add(0.00);
                five.add(0.00);
                six.add(0.00);
                seven.add(0.00);
                result = zore.toString() + "," + one.toString()+ "," + two.toString()+ "," + three.toString()+ "," + four.toString()+ "," + five.toString()+ "," + six.toString()+ "," + seven.toString();
                break;
            case "104":
                List<JcFootBallOddsBqcRsp> bqc = jcFootBallOddsRsp.getBqc();
                JcFootBallOddsBqcRsp jcFootBallOddsBqcRsp = bqc.get(0);
                Short homeScore = schedule.getHomescore();
                Short gestScore = schedule.getGuestscore();
                Short homeHalfScore = schedule.getHomehalfscore();
                Short guestHalfScore = schedule.getGuesthalfscore();
                //3 胜 1 平 0 负
                JSONArray ss = new JSONArray();
                JSONArray sp = new JSONArray();
                JSONArray sf = new JSONArray();
                JSONArray ps = new JSONArray();
                JSONArray pp = new JSONArray();
                JSONArray pf = new JSONArray();
                JSONArray fs = new JSONArray();
                JSONArray fp = new JSONArray();
                JSONArray ff = new JSONArray();
                if(homeHalfScore > guestHalfScore){
                    if(homeScore > gestScore){//33
                        ss.add(jcFootBallOddsBqcRsp.getHt33());
                        if(jcSchedulesp == null){
                            ss.add(0.00);
                        }else{
                            Double ssChange = Double.parseDouble(jcFootBallOddsBqcRsp.getHt33()) - Double.parseDouble(jcSchedulesp.getHt33());
                            BigDecimal b1 = new BigDecimal(ssChange);
                            ss.add(b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        }
                    }
                    if(homeScore == gestScore){//31
                        sp.add(jcFootBallOddsBqcRsp.getHt31());
                        if(jcSchedulesp == null){
                            sp.add(0.00);
                        }else{
                            Double spChange = Double.parseDouble(jcFootBallOddsBqcRsp.getHt31()) - Double.parseDouble(jcSchedulesp.getHt31());
                            BigDecimal b2 = new BigDecimal(spChange);
                            sp.add(b2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        }
                    }
                    if(homeScore < gestScore){//30
                        sf.add(jcFootBallOddsBqcRsp.getHt30());
                        if(jcSchedulesp == null){
                            sf.add(0.00);
                        }else{
                            Double sfChange = Double.parseDouble(jcFootBallOddsBqcRsp.getHt30()) - Double.parseDouble(jcSchedulesp.getHt30());
                            BigDecimal b3 = new BigDecimal(sfChange);
                            sf.add(b3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        }
                    }
                }
                if(homeHalfScore == guestHalfScore){
                    if(homeScore > gestScore){//13
                        ps.add(jcFootBallOddsBqcRsp.getHt13());
                        if(jcSchedulesp == null){
                            ps.add(0.00);
                        }else{
                            Double psChange = Double.parseDouble(jcFootBallOddsBqcRsp.getHt13()) - Double.parseDouble(jcSchedulesp.getHt13());
                            BigDecimal b4 = new BigDecimal(psChange);
                            ps.add(b4.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        }
                    }
                    if(homeScore == gestScore){//11
                        pp.add(jcFootBallOddsBqcRsp.getHt11());
                        if(jcSchedulesp == null){
                            pp.add(0.00);
                        }else{
                            Double ppChange = Double.parseDouble(jcFootBallOddsBqcRsp.getHt11()) - Double.parseDouble(jcSchedulesp.getHt11());
                            BigDecimal b5 = new BigDecimal(ppChange);
                            pp.add(b5.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        }
                    }
                    if(homeScore < gestScore){//10
                        pf.add(jcFootBallOddsBqcRsp.getHt10());
                        if(jcSchedulesp == null){
                            pf.add(0.00);
                        }else{
                            Double pfChange = Double.parseDouble(jcFootBallOddsBqcRsp.getHt10()) - Double.parseDouble(jcSchedulesp.getHt10());
                            BigDecimal b6 = new BigDecimal(pfChange);
                            pf.add(b6.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        }
                    }
                }
                if(homeHalfScore < guestHalfScore){
                    if(homeScore > gestScore){//03
                        fs.add(jcFootBallOddsBqcRsp.getHt03());
                        if(jcSchedulesp == null){
                            fs.add(0.00);
                        }else{
                            Double fsChange = Double.parseDouble(jcFootBallOddsBqcRsp.getHt03()) - Double.parseDouble(jcSchedulesp.getHt03());
                            BigDecimal b7 = new BigDecimal(fsChange);
                            fs.add(b7.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        }
                    }
                    if(homeScore == gestScore){//01
                        fp.add(jcFootBallOddsBqcRsp.getHt01());
                        if(jcSchedulesp == null){
                            fp.add(0.00);
                        }else{
                            Double fpChange = Double.parseDouble(jcFootBallOddsBqcRsp.getHt01()) - Double.parseDouble(jcSchedulesp.getHt01());
                            BigDecimal b8 = new BigDecimal(fpChange);
                            fp.add(b8.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        }
                    }
                    if(homeScore < gestScore){//00
                        ff.add(jcFootBallOddsBqcRsp.getHt00());
                        if(jcSchedulesp == null){
                            ff.add(0.00);
                        }else{
                            Double ffChange = Double.parseDouble(jcFootBallOddsBqcRsp.getHt00()) - Double.parseDouble(jcSchedulesp.getHt00());
                            BigDecimal b9 = new BigDecimal(ffChange);
                            ff.add(b9.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        }
                    }
                }
                result = ss.toString() + "," + sp.toString() + "," + sf.toString() + ","
                        + ps.toString() + "," + pp.toString() + "," + pf.toString() + ","
                        + fs.toString() + "," + fp.toString() + "," + ff.toString();
                break;
            case "105":
                List<JcFootBallOddsSfRsp> sfList = jcFootBallOddsRsp.getSf();
                JcFootBallOddsSfRsp jcFootBallOddsSfRsp = sfList.get(0);
                Short homeScore1 = schedule.getHomescore();
                Short gestScore1 = schedule.getGuestscore();
                JSONArray s = new JSONArray();
                JSONArray p = new JSONArray();
                JSONArray f = new JSONArray();
                if(homeScore1 > gestScore1){//胜
                    s.add(jcFootBallOddsSfRsp.getSf3());
                    if(jcSchedulesp == null){
                        s.add(0.00);
                    }else{
                        Double sChange = Double.parseDouble(jcFootBallOddsSfRsp.getSf3()) - Double.parseDouble(jcSchedulesp.getSf3());
                        BigDecimal b1 = new BigDecimal(sChange);
                        s.add(b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    }
                }
                if(homeScore1 == gestScore1){//平
                    p.add(jcFootBallOddsSfRsp.getSf1());
                    if(jcSchedulesp == null){
                        p.add(0.00);
                    }else{
                        Double pChange = Double.parseDouble(jcFootBallOddsSfRsp.getSf1()) - Double.parseDouble(jcSchedulesp.getSf1());
                        BigDecimal b2 = new BigDecimal(pChange);
                        p.add(b2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    }
                }
                if(homeScore1 < gestScore1){//负
                    f.add(jcFootBallOddsSfRsp.getSf0());
                    if(jcSchedulesp == null){
                        f.add(0.00);
                    }else{
                        Double fChange = Double.parseDouble(jcFootBallOddsSfRsp.getSf0()) - Double.parseDouble(jcSchedulesp.getSf0());
                        BigDecimal b3 = new BigDecimal(fChange);
                        f.add(b3.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    }
                }
                result = s.toString() + "," + p.toString() + "," + f.toString();
                break;
        }
        return result;
    }

}
