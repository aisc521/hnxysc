package com.zhcdata.jc.tools;

import com.google.common.base.Strings;
import com.zhcdata.db.model.JcMatchBjdcPl;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.BdrealTimeSp.*;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.BjDcLotteryQuery.BjDcLotteryQueryRsp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

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
            resultInfo = spf + "|" + bqc + "|" + bf + "|" + jq;
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
}
