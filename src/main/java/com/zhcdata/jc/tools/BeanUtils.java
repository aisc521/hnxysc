/*create by xuan on 2019/9/11

 */
package com.zhcdata.jc.tools;

import com.zhcdata.db.model.*;
import com.zhcdata.jc.xml.rsp.MatchListRsp;
import com.zhcdata.jc.xml.rsp.StandardHalfRsps.H;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BeanUtils {

    //private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //private static final SimpleDateFormat sdf_X = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


    public static Schedule parseSchedule(MatchListRsp model) {
        Schedule inDb = new Schedule();
        inDb.setScheduleid(Integer.parseInt(model.getA()));//比赛id
        if (model.getC() != null && model.getC().split(",").length == 5)//联赛id
            inDb.setSclassid(Integer.parseInt(model.getC().split(",")[3]));
        if (model.getX() != null)//赛季
            inDb.setMatchseason(model.getX());
        if (StringUtils.isNotEmpty(model.getS()))//轮次
            inDb.setGrouping(model.getS());
        // TODO: 2019/9/11 轮次
        if (model.getY() != null)//组
            inDb.setGrouping2(model.getY());
        if (model.getH() != null) {
            inDb.setHometeamid(Integer.parseInt(model.getH().split(",")[3]));//主队id
            inDb.setHometeam(model.getH().split(",")[1]);//主队名
        }
        if (model.getI() != null) {
            inDb.setGuestteamid(Integer.parseInt(model.getI().split(",")[3]));//客队id
            inDb.setGuestteam(model.getI().split(",")[1]);//客队名
        }
        if (model.getZ() != null)
            inDb.setNeutrality(model.getZ().startsWith("T"));//是否中立场'
        if (model.getD() != null) {
            try {
                inDb.setMatchtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new TimeFormatUtils().parseToFormat(model.getD())));//开始时间
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // TODO: 2019/9/10 下半场开始时间
        if (model.getT() != null)
            inDb.setLocation(model.getT());//比赛地点
        if (model.getP() != null)
            inDb.setHomeOrder(model.getP());//主队排名
        if (model.getQ() != null)
            inDb.setGuestOrder(model.getQ());//客队排名
        if (StringUtils.isNotEmpty(model.getF() + ""))
            inDb.setMatchstate(Short.parseShort(("" + model.getF())));//比赛状态
        if (StringUtils.isNotEmpty(model.getU()))
            inDb.setWeathericon(Short.parseShort(model.getU()));//天气图标
        if (model.getV() != null)
            inDb.setWeather(model.getV());//天气
        if (model.getW() != null)
            inDb.setTemperature(model.getW());//温度
        if (model.getExplain2() != null) {
            inDb.setTv(model.getExplain2().split(";")[0]);    //电视台信息
            //inDb.setTemperature(model.getExplain2().split(";")[0]);//电视台信息
            inDb.setExplainlist(model.getExplain2());
        }
        // TODO: 2019/9/10 裁判信息 需要用 裁判数据接口
        // TODO: 2019/9/10 阵容需要用出场阵容接口
        if (model.getJ() >= 0)
            inDb.setHomescore(Short.parseShort("" + model.getJ()));//主队得分
        if (model.getK() >= 0)
            inDb.setGuestscore(Short.parseShort("" + model.getK()));//客队得分
        if (StringUtils.isNotEmpty(model.getL()))
            inDb.setHomehalfscore(Short.parseShort(model.getL()));//主队半场
        else inDb.setHomehalfscore(Short.parseShort("0"));//主队半场

        if (StringUtils.isNotEmpty(model.getM()))
            inDb.setGuesthalfscore(Short.parseShort(model.getM()));//客队半场
        else inDb.setGuesthalfscore(Short.parseShort("0"));//客队半场
        // TODO: 2019/9/10 比分说明或者其他说明
        // TODO: 2019/9/10 比分说明2
        if (model.getN() != null)
            inDb.setHomeRed(Short.parseShort(model.getN()));//主队红牌
        if (model.getO() != null)
            inDb.setGuestRed(Short.parseShort(model.getO()));//主队红牌
        if (model.getYellow() != null) {//主客队黄牌
            inDb.setHomeYellow(Short.parseShort(model.getYellow().split("-")[0]));
            inDb.setGuestYellow(Short.parseShort(model.getYellow().split("-")[1]));
        }
        // TODO: 2019/9/10 比分变化时间
        // TODO: 2019/9/10 上盘球队
        // TODO: 2019/9/10 下级分组
        if (StringUtils.isNotEmpty(model.getSubID()))
            inDb.setSubsclassid(Integer.parseInt(model.getSubID()));//自联赛ID
        if (StringUtils.isNotEmpty(model.getHidden()))
            inDb.setBfshow(model.getHidden().startsWith("F"));//是否显示
        return inDb;
    }


    public static boolean ScheduleNoChange(Schedule s1, Schedule s2) {
        return s1.equals(s2);
    }


    public static Letgoal parseLetgoal(String str) {
        String[] info = str.split(",");
        Letgoal mo = new Letgoal();
        mo.setScheduleid(Integer.parseInt(info[0]));//比赛id
        mo.setCompanyid(Integer.parseInt(info[1]));//公司id

        mo.setFirstgoal(Float.parseFloat(info[2]));//初盘及赔率
        mo.setFirstupodds(Float.parseFloat(info[3]));//初盘及赔率
        mo.setFirstdownodds(Float.parseFloat(info[4]));//初盘及赔率

        mo.setGoal(Float.parseFloat(info[5]));//即时盘及赔率
        mo.setUpodds(Float.parseFloat(info[6]));//即时盘及赔率
        mo.setDownodds(Float.parseFloat(info[7]));//即时盘及赔率

        mo.setGoalReal(Float.parseFloat(info[5]));//终
        mo.setUpoddsReal(Float.parseFloat(info[6]));//终
        mo.setDownoddsReal(Float.parseFloat(info[7]));//终
        //mo.setRunning(info[0]);//是否正在走地
        //mo.setResult(info[0]);//盘路结果 1主队赢 2走 3输

        try {
            mo.setModifytime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(info[12]));//变化时间
        } catch (Exception e) {
            System.err.println("parseToLetgoal,dateFormatException,变化时间转换失败" + info[12]);
            e.printStackTrace();
        }
        mo.setClosepan(info[8].startsWith("T"));//球探网手动封盘
        mo.setZoudi(info[9].startsWith("T"));//主网是否开走地
        mo.setIsstoplive(info[13].startsWith("T"));//主网是否封盘
        return mo;
    }

    public static Letgoal parseLetgoal(String[] info) {
        //String[] info = str.split(",");
        Letgoal mo = new Letgoal();
        mo.setScheduleid(Integer.parseInt(info[0]));//比赛id
        mo.setCompanyid(Integer.parseInt(info[1]));//公司id

        mo.setFirstgoal(Float.parseFloat(info[2]));//初盘及赔率
        mo.setFirstupodds(Float.parseFloat(info[3]));//初盘及赔率
        mo.setFirstdownodds(Float.parseFloat(info[4]));//初盘及赔率

        mo.setGoal(Float.parseFloat(info[5]));//即时盘及赔率
        mo.setUpodds(Float.parseFloat(info[6]));//即时盘及赔率
        mo.setDownodds(Float.parseFloat(info[7]));//即时盘及赔率

        //mo.setGoalReal(info[]);//终盘及赔率
        //mo.setUpoddsReal(info[]);//终盘及赔率
        //mo.setDownoddsReal(info[]);//终盘及赔率
        //mo.setRunning(info[0]);//是否正在走地
        //mo.setResult(info[0]);//盘路结果 1主队赢 2走 3输

        try {
            mo.setModifytime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(info[12]));//变化时间
        } catch (Exception e) {
            System.err.println("parseToLetgoal,dateFormatException,变化时间转换失败");
            e.printStackTrace();
        }
        mo.setClosepan(info[8].startsWith("T"));//球探网手动封盘
        mo.setZoudi(info[9].startsWith("T"));//主网是否开走地
        mo.setIsstoplive(info[13].startsWith("T"));//主网是否封盘
        return mo;
    }

    public static MultiLetgoal parseMultiLetgoall(String s) {
        String[] info = s.split(",");
        MultiLetgoal mo = new MultiLetgoal();
        mo.setScheduleid(Integer.parseInt(info[0]));//比赛id
        mo.setCompanyid(Integer.parseInt(info[1]));//公司id

        mo.setFirstgoal(Float.parseFloat(info[2]));//初盘及赔率
        mo.setFirstupodds(Float.parseFloat(info[3]));//初盘及赔率
        mo.setFirstdownodds(Float.parseFloat(info[4]));//初盘及赔率

        mo.setGoal(Float.parseFloat(info[5]));//即时盘及赔率
        mo.setUpodds(Float.parseFloat(info[6]));//即时盘及赔率
        mo.setDownodds(Float.parseFloat(info[7]));//即时盘及赔率

        mo.setGoalReal(Float.parseFloat(info[5]));//终
        mo.setUpoddsReal(Float.parseFloat(info[6]));//终
        mo.setDownoddsReal(Float.parseFloat(info[7]));//终
        //mo.setGoalReal(info[]);//终盘及赔率
        //mo.setUpoddsReal(info[]);//终盘及赔率
        //mo.setDownoddsReal(info[]);//终盘及赔率

        try {
            mo.setModifytime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(info[12]));//变化时间
        } catch (Exception e) {
            System.err.println("parseMultiLetgoall,dateFormatException,变化时间转换失败");
            e.printStackTrace();
        }
        mo.setZoudi(info[9].startsWith("T"));//主网是否开走地
        //mo.setClosepan(info[8].startsWith("T"));//球探网手动封盘
        mo.setIsstoplive(info[13].startsWith("T"));//主网是否封盘
        mo.setNum(Short.parseShort(info[10]));//盘口号
        return mo;
    }

    public static Standard parseStandard(String s) {
        Standard mo = new Standard();
        //比赛ID,公司ID,初盘主胜赔率,初盘平局赔率,初盘客胜赔率,即时主胜赔率,即时平局赔率,即时客胜赔率,盘口序号,变盘时间,是否封盘2
        String[] info = s.split(",");
        mo.setScheduleid(Integer.parseInt(info[0]));//比赛id
        mo.setCompanyid(Integer.parseInt(info[1]));//公司ID

        mo.setFirsthomewin(info[2]);//初赔
        mo.setFirststandoff(info[3]);//
        mo.setFirstguestwin(info[4]);//

        //mo.setHomewin(info[]);//终盘
        //mo.setStandoff(info[]);//
        //mo.setGuestwin(info[]);//
        //mo.setResult(info[]);//盘路结果

        mo.setHomewinR(info[5]);//即时
        mo.setStandoffR(info[6]);//
        mo.setGuestwinR(info[7]);//

        mo.setClosepan(info[10].startsWith("T"));//封盘
        try {
            mo.setModifytime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(info[9]));//变化时间
        } catch (Exception e) {
            System.err.println("parseStandard,dateFormatException,变化时间转换失败" + info[9]);
            e.printStackTrace();
        }
        return mo;
    }

    public static Standard parseStandard(String[] info) {
        Standard mo = new Standard();
        //比赛ID,公司ID,初盘主胜赔率,初盘平局赔率,初盘客胜赔率,即时主胜赔率,即时平局赔率,即时客胜赔率,盘口序号,变盘时间,是否封盘2
        //String[] info = s.split(",");
        mo.setScheduleid(Integer.parseInt(info[0]));//比赛id
        mo.setCompanyid(Integer.parseInt(info[1]));//公司ID

        mo.setFirsthomewin(info[2]);//初赔
        mo.setFirststandoff(info[3]);//
        mo.setFirstguestwin(info[4]);//

        mo.setHomewin(info[5]);//终盘
        mo.setStandoff(info[6]);//
        mo.setGuestwin(info[7]);//
        //mo.setResult(info[]);//盘路结果

        mo.setHomewinR(info[5]);//即时
        mo.setStandoffR(info[6]);//
        mo.setGuestwinR(info[7]);//

        mo.setClosepan(info[10].startsWith("T"));//封盘
        try {
            mo.setModifytime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(info[9]));//变化时间
        } catch (Exception e) {
            System.err.println("parseStandards,dateFormatException,变化时间转换失败" + info[9]);
            e.printStackTrace();
        }
        return mo;
    }

    public static TotalScore parseTotalScore(String s) {
        TotalScore mo = new TotalScore();
        //比赛ID,公司ID,初盘盘口,初盘大球赔率,初盘小球赔率,即时盘盘口,即时盘大球赔率,即时盘小球赔率,盘口序号,变盘时间,是否封盘2
        String[] info = s.split(",");
        //mo.setOddsid();
        mo.setScheduleid(Integer.parseInt(info[0]));
        mo.setCompanyid(Integer.parseInt(info[1]));

        mo.setFirstgoal(Float.parseFloat(info[2]));
        mo.setFirstupodds(Float.parseFloat(info[3]));
        mo.setFirstdownodds(Float.parseFloat(info[4]));

        mo.setGoal(Float.parseFloat(info[5]));
        mo.setUpodds(Float.parseFloat(info[6]));
        mo.setDownodds(Float.parseFloat(info[7]));

        mo.setGoalReal(Float.parseFloat(info[5]));
        mo.setUpoddsReal(Float.parseFloat(info[6]));
        mo.setDownoddsReal(Float.parseFloat(info[7]));


        //mo.setGoalReal();
        //mo.setUpoddsReal();
        //mo.setDownoddsReal();
        //mo.setResult();
        //mo.setZoudi();

        //mo.setClosepan();//球探封盘
        try {
            mo.setModifytime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(info[9]));//变化时间
        } catch (Exception e) {
            System.err.println("parseTotalScore,dateFormatException,变化时间转换失败" + info[9]);
            e.printStackTrace();
        }
        mo.setIsstoplive(info[10].startsWith("T"));//主网封盘
        return mo;
    }

    public static MultiTotalScore parseMultiTotalScore(String s) {
        //比赛ID,公司ID,初盘盘口,初盘大球赔率,初盘小球赔率,即时盘盘口,即时盘大球赔率,即时盘小球赔率,盘口序号,变盘时间,是否封盘2
        MultiTotalScore mo = new MultiTotalScore();
        String[] info = s.split(",");
        mo.setScheduleid(Integer.parseInt(info[0]));
        mo.setCompanyid(Integer.parseInt(info[1]));

        mo.setFirstgoal(Float.parseFloat(info[2]));
        mo.setFirstupodds(Float.parseFloat(info[3]));
        mo.setFirstdownodds(Float.parseFloat(info[4]));

        mo.setGoal(Float.parseFloat(info[5]));
        mo.setUpodds(Float.parseFloat(info[6]));
        mo.setDownodds(Float.parseFloat(info[7]));

        mo.setGoalReal(Float.parseFloat(info[5]));
        mo.setUpoddsReal(Float.parseFloat(info[6]));
        mo.setDownoddsReal(Float.parseFloat(info[7]));
        mo.setNum(Short.parseShort(info[8]));
        try {
            mo.setModifytime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(info[9]));//变化时间
        } catch (Exception e) {
            System.err.println("parseMultiTotalScore,dateFormatException,变化时间转换失败" + info[9]);
            e.printStackTrace();
        }
        mo.setIsstoplive(info[10].startsWith("T"));//主网封盘
        // TODO: 2019/9/16 主网是否开走地 终盘信息
        return mo;
    }

    public static LetGoalhalf parseHalfLetGoal(String s) {
        String[] info = s.split(",");
        //比赛ID,公司ID,初盘盘口,主队初盘赔率,客队初盘赔率  即时盘口,主队即时赔率,客队即时赔率,是否走地,盘口序号,变盘时间
        LetGoalhalf mo = new LetGoalhalf();
        mo.setScheduleid(Integer.parseInt(info[0]));//比赛ID
        mo.setCompanyid(Integer.parseInt(info[1]));//开盘公司
        mo.setFirstgoal(Float.parseFloat(info[2]));//初
        mo.setFirstupodds(Float.parseFloat(info[3]));
        mo.setFirstdownodds(Float.parseFloat(info[4]));

        mo.setGoal(Float.parseFloat(info[5]));//即时
        mo.setUpodds(Float.parseFloat(info[6]));
        mo.setDownodds(Float.parseFloat(info[7]));

        mo.setGoalReal(Float.parseFloat(info[5]));//终盘
        mo.setUpoddsReal(Float.parseFloat(info[6]));
        mo.setDownoddsReal(Float.parseFloat(info[7]));

        // TODO: 2019/9/16 终盘 盘路结果
        try {
            mo.setModifytime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(info[10]));//修改时间
        } catch (Exception e) {
            System.err.println("parseHalfLetGoal,dateFormatException,变化时间转换失败" + info[10]);
            e.printStackTrace();
        }
        mo.setZoudi(info[8].startsWith("T"));
        return mo;
    }


    public static MultiLetGoalhalf parseMultiLetGoalhalf(String s) {
        String[] info = s.split(",");
        //比赛ID,公司ID,初盘盘口,主队初盘赔率,客队初盘赔率  即时盘口,主队即时赔率,客队即时赔率,是否走地,盘口序号,变盘时间
        MultiLetGoalhalf mo = new MultiLetGoalhalf();
        mo.setScheduleid(Integer.parseInt(info[0]));//比赛ID
        mo.setCompanyid(Integer.parseInt(info[1]));//开盘公司
        mo.setFirstgoal(Float.parseFloat(info[2]));//初
        mo.setFirstupodds(Float.parseFloat(info[3]));
        mo.setFirstdownodds(Float.parseFloat(info[4]));

        mo.setGoal(Float.parseFloat(info[5]));//即时
        mo.setUpodds(Float.parseFloat(info[6]));
        mo.setDownodds(Float.parseFloat(info[7]));

        mo.setGoalReal(Float.parseFloat(info[5]));//终盘
        mo.setUpoddsReal(Float.parseFloat(info[6]));
        mo.setDownoddsReal(Float.parseFloat(info[7]));

        // TODO: 2019/9/16 终盘 盘路结果
        try {
            mo.setModifytime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(info[10]));//修改时间
        } catch (Exception e) {
            System.err.println("parseMultiLetGoalhalf,dateFormatException,变化时间转换失败" + info[10]);
            e.printStackTrace();
        }

        mo.setZoudi(info[8].startsWith("T"));
        mo.setNum(Short.parseShort(info[9]));

        return mo;
    }

    public static TotalScorehalf parseTotalScorehalf(String s) {
        String[] info = s.split(",");
        TotalScorehalf mo = new TotalScorehalf();
        //比赛ID,公司ID,初盘盘口,初盘大球赔率,初盘小球赔率,即时盘盘口,即时盘大球赔率,即时盘小球赔率,盘口序号,变盘时间
        mo.setScheduleid(Integer.parseInt(info[0]));
        mo.setCompanyid(Integer.parseInt(info[1]));

        mo.setFirstgoal(Float.parseFloat(info[2]));//初
        mo.setFirstupodds(Float.parseFloat(info[3]));
        mo.setFirstdownodds(Float.parseFloat(info[4]));

        mo.setGoal(Float.parseFloat(info[5]));//即时
        mo.setUpodds(Float.parseFloat(info[6]));
        mo.setDownodds(Float.parseFloat(info[7]));

        mo.setGoalReal(Float.parseFloat(info[5]));//终盘
        mo.setUpoddsReal(Float.parseFloat(info[6]));
        mo.setDownoddsReal(Float.parseFloat(info[7]));

        try {
            mo.setModifytime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(info[9]));//修改时间
        } catch (Exception e) {
            System.err.println("parseTotalScorehalf,dateFormatException,变化时间转换失败" + info[9]);
            e.printStackTrace();
        }
        return mo;
    }


    public static MultiTotalScorehalf parseMultiTotalScorehalf(String s) {
        String[] info = s.split(",");
        MultiTotalScorehalf mo = new MultiTotalScorehalf();
        //比赛ID,公司ID,初盘盘口,初盘大球赔率,初盘小球赔率,即时盘盘口,即时盘大球赔率,即时盘小球赔率,盘口序号,变盘时间
        mo.setScheduleid(Integer.parseInt(info[0]));
        mo.setCompanyid(Integer.parseInt(info[1]));

        mo.setFirstgoal(Float.parseFloat(info[2]));//初
        mo.setFirstupodds(Float.parseFloat(info[3]));
        mo.setFirstdownodds(Float.parseFloat(info[4]));

        mo.setGoal(Float.parseFloat(info[5]));//即时
        mo.setUpodds(Float.parseFloat(info[6]));
        mo.setDownodds(Float.parseFloat(info[7]));

        mo.setGoalReal(Float.parseFloat(info[5]));//终盘
        mo.setUpoddsReal(Float.parseFloat(info[6]));
        mo.setDownoddsReal(Float.parseFloat(info[7]));

        try {
            mo.setModifytime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(info[9]));//修改时间
        } catch (Exception e) {
            System.err.println("parseMultiTotalScorehalf,dateFormatException,变化时间转换失败" + info[9]);
            e.printStackTrace();
        }
        mo.setNum(Short.parseShort(info[8]));
        return mo;
    }

    public static LetGoalDetail parseLetGoalDetail(String[] item) {
        //亚赔（让球盘）变化数据:比赛ID,公司ID,即时盘口,主队即时赔率,客队即时赔率,是否封盘1,是否走地,盘口序号,变盘时间,是否封盘2
        LetGoalDetail mo = new LetGoalDetail();
        mo.setUpodds(Float.parseFloat(item[3]));
        mo.setGoal(Float.parseFloat(item[2]));
        mo.setDownodds(Float.parseFloat(item[4]));
        try {
            mo.setModifytime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(item[8]));//修改时间
        } catch (Exception e) {
            System.err.println("parseLetGoalDetail,dateFormatException,变化时间转换失败");
            e.printStackTrace();
        }
        return mo;
    }

    public static MultiLetGoalDetail parseMultiLetGoalDetail(String[] item) {
        //亚赔（让球盘）变化数据:比赛ID,公司ID,即时盘口,主队即时赔率,客队即时赔率,是否封盘1,是否走地,盘口序号,变盘时间,是否封盘2
        MultiLetGoalDetail mo = new MultiLetGoalDetail();
        mo.setUpodds(Float.parseFloat(item[3]));
        mo.setGoal(Float.parseFloat(item[2]));
        mo.setDownodds(Float.parseFloat(item[4]));
        try {
            mo.setAddtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(item[8]));//修改时间
        } catch (Exception e) {
            System.err.println("parseMultiLetGoalDetail,dateFormatException,变化时间转换失败");
            e.printStackTrace();
        }
        return mo;
    }

    public static StandardDetail parseStandardDetail(String[] s) {
        //比赛ID,公司ID,即时盘主胜赔率,即时盘平局赔率,即时盘客胜赔率,盘口序号,变盘时间,是否封盘2
        StandardDetail mo = new StandardDetail();
        mo.setHomewin(Float.valueOf(s[2]));
        mo.setStandoff(Float.valueOf(s[3]));
        mo.setGuestwin(Float.valueOf(s[4]));
        try {
            mo.setModifytime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s[6]));//修改时间
        } catch (Exception e) {
            System.err.println("parseMultiLetGoalDetail,dateFormatException,变化时间转换失败");
            e.printStackTrace();
        }
        return mo;
    }

    public static TotalScoreDetail parseTotalScoreDetail(String[] info) {
        /*大小球变化数据 比赛ID,公司ID,时盘盘口,即时盘大球赔率,即时盘小球赔率,盘口序号,变盘时间,是否封盘2*/
        TotalScoreDetail mo = new TotalScoreDetail();
        mo.setUpodds(Float.parseFloat(info[3]));
        mo.setDownodds(Float.parseFloat(info[4]));
        mo.setGoal(Float.parseFloat(info[2]));
        try {
            mo.setModifytime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(info[6]));//修改时间
        } catch (Exception e) {
            System.err.println("parseMultiLetGoalDetail,dateFormatException,变化时间转换失败");
            e.printStackTrace();
        }
        return mo;
    }

    public static MultiTotalScoreDetail parseparseMultiTotalScoreDetail(String[] info) {
        /*大小球变化数据 比赛ID,公司ID,时盘盘口,即时盘大球赔率,即时盘小球赔率,盘口序号,变盘时间,是否封盘2*/
        MultiTotalScoreDetail mo = new MultiTotalScoreDetail();
        mo.setUpodds(Float.parseFloat(info[3]));
        mo.setDownodds(Float.parseFloat(info[4]));
        mo.setGoal(Float.parseFloat(info[2]));
        try {
            mo.setAddtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(info[6]));//修改时间
        } catch (Exception e) {
            System.err.println("parseMultiLetGoalDetail,dateFormatException,变化时间转换失败");
            e.printStackTrace();
        }
        return mo;
    }

    public static LetGoalhalfDetail parseLetGoalhalfDetail(String[] info) {
        //比赛ID,公司ID,即时盘口,主队即时赔率,客队即时赔率,是否走地,盘口序号,变盘时间
        LetGoalhalfDetail mo = new LetGoalhalfDetail();
        mo.setUpodds(Float.parseFloat(info[3]));
        mo.setGoal(Float.parseFloat(info[2]));
        mo.setDownodds(Float.parseFloat(info[4]));
        try {
            mo.setModifytime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(info[7]));//修改时间
        } catch (Exception e) {
            System.err.println("parseLetGoalhalfDetail,dateFormatException,变化时间转换失败");
            e.printStackTrace();
        }
        return mo;
    }

    public static MultiLetGoalhalfDetail parseMultiLetGoalhalfDetail(String[] info) {
        MultiLetGoalhalfDetail mo = new MultiLetGoalhalfDetail();
        mo.setUpodds(Float.parseFloat(info[3]));
        mo.setGoal(Float.parseFloat(info[2]));
        mo.setDownodds(Float.parseFloat(info[4]));
        try {
            mo.setAddtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(info[7]));//修改时间
        } catch (Exception e) {
            System.err.println("parseMultiLetGoalhalfDetail,dateFormatException,变化时间转换失败");
            e.printStackTrace();
        }
        return mo;
    }

    public static TotalScorehalfDetail parseTotalScorehalfDetail(String[] info) {
        //比赛ID,公司ID,时盘盘口,即时盘大球赔率,即时盘小球赔率,盘口序号,变盘时间
        TotalScorehalfDetail mo = new TotalScorehalfDetail();
        mo.setGoal(Float.parseFloat(info[2]));
        mo.setUpodds(Float.parseFloat(info[3]));
        mo.setDownodds(Float.parseFloat(info[4]));
        try {
            mo.setModifytime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(info[6]));//修改时间
        } catch (Exception e) {
            System.err.println("parseLetGoalhalfDetail,dateFormatException,变化时间转换失败");
            e.printStackTrace();
        }
        return mo;
    }

    public static MultiTotalScorehalfDetail parseMultiTotalScorehalfDetail(String[] info) {
        MultiTotalScorehalfDetail mo = new MultiTotalScorehalfDetail();
        //比赛ID,公司ID,时盘盘口,即时盘大球赔率,即时盘小球赔率,盘口序号,变盘时间
        mo.setGoal(Float.parseFloat(info[2]));
        mo.setUpodds(Float.parseFloat(info[3]));
        mo.setDownodds(Float.parseFloat(info[4]));
        try {
            mo.setAddtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(info[6]));//修改时间
        } catch (Exception e) {
            System.err.println("parseLetGoalhalfDetail,dateFormatException,变化时间转换失败");
            e.printStackTrace();
        }
        return mo;
    }

    public static List<StandardHalf> parseStandardHalf(H h) {
        //半场欧赔    博彩公司ID,博彩公司名,初盘主胜,初盘平局,初盘客胜,主胜,平局,客胜,变化时间
        List<StandardHalf> mos = new ArrayList<>();
        List<String> odds = h.getOdds().getO();
        for (int i = 0; i < odds.size(); i++) {
            String[] str = odds.get(i).split(",");
            StandardHalf mo = new StandardHalf();
            mo.setScheduleid(Integer.parseInt(h.getId()));
            mo.setCompanyid(Integer.parseInt(str[0]));

            mo.setFirsthomewin(Float.parseFloat(str[2]));//初盘主胜水位
            mo.setFirststandoff(Float.parseFloat(str[3]));//初盘和局水位
            mo.setFirstguestwin(Float.parseFloat(str[4]));//初盘客胜水位

            /*终盘水位就是即时水位，但是到了比赛开始时间就不更新了*/
            mo.setHomewin(Float.parseFloat(str[5]));//终盘
            mo.setStandoff(Float.parseFloat(str[6]));//终盘
            mo.setGuestwin(Float.parseFloat(str[7]));//终盘

            mo.setHomewinR(Float.parseFloat(str[5]));//即时主胜
            mo.setStandoffR(Float.parseFloat(str[6]));//即时和局
            mo.setGuestwinR(Float.parseFloat(str[7]));//即时主胜

            try {
                mo.setModifytime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new TimeFormatUtils().parseToFormat(str[8])));//修改时间
            } catch (Exception e) {
                System.err.println("parseStandardHalf,dateFormatException,变化时间转换失败 : " + str[8]);
                e.printStackTrace();
            }
            mos.add(mo);
        }
        return mos;
    }

    public static StandardHalfDetail parseStandardHalfDetail(StandardHalf half) {
        StandardHalfDetail mo = new StandardHalfDetail();
        mo.setHomewin(half.getHomewinR());
        mo.setGuestwin(half.getGuestwinR());
        mo.setStandoff(half.getStandoffR());
        mo.setModifytime(half.getModifytime());
        return mo;
    }

    public static Date parseTime(String time) {
        try {
            return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(time);
        } catch (Exception e) {
            System.err.println("BeanUtils.parseTime exception,time:" + time);
            e.printStackTrace();
        }
        return new Date();
    }

    public EuropeOdds parseEuropeOdds(String id, String[] mos) {
        //博彩公司ID,博彩公司英文名,初盘主胜,初盘平局,初盘客胜,主胜,平局,客胜,变化时间,博彩公司简体名
        EuropeOdds mo = new EuropeOdds();
        mo.setScheduleid(Integer.parseInt(id));
        mo.setCompanyid(Integer.parseInt(mos[0]));
        //初
        mo.setFirsthomewin(Float.parseFloat(mos[2]));
        mo.setFirststandoff(Float.parseFloat(mos[3]));
        mo.setFirstguestwin(Float.parseFloat(mos[4]));
        //终（即时）
        if (StringUtils.isNotEmpty(mos[5]))
            mo.setRealhomewin(Float.parseFloat(mos[5]));
        if (StringUtils.isNotEmpty(mos[6]))
            mo.setRealstandoff(Float.parseFloat(mos[6]));
        if (StringUtils.isNotEmpty(mos[7]))
            mo.setRealguestwin(Float.parseFloat(mos[7]));

        try {
            mo.setModifytime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(mos[8]));//修改时间
        } catch (Exception e) {
            System.err.println("parseEuropeOdds,dateFormatException,变化时间转换失败 : " + mos[8]);
            e.printStackTrace();
        }
        return mo;
    }

    public static EuropeOddsDetail parseEuropeOddsDetail(String[] mos) {
        ////博彩公司ID,博彩公司英文名,初盘主胜,初盘平局,初盘客胜,主胜,平局,客胜,变化时间,博彩公司简体名
        EuropeOddsDetail mo = new EuropeOddsDetail();
        if (StringUtils.isNotEmpty(mos[5]) && StringUtils.isNotEmpty(mos[6]) && StringUtils.isNotEmpty(mos[7])) {
            mo.setHomewin(Float.parseFloat(mos[5]));
            mo.setStandoff(Float.parseFloat(mos[6]));
            mo.setGuestwin(Float.parseFloat(mos[7]));
        } else {//初
            mo.setFirst(true);
            mo.setHomewin(Float.parseFloat(mos[2]));
            mo.setStandoff(Float.parseFloat(mos[3]));
            mo.setGuestwin(Float.parseFloat(mos[4]));
        }

        try {
            if (mos[8]==null || mos[8].equals("")){
                System.err.println("parseEuropeOddsDetail,mos[8]是nulllllllll" + mos[8]);
            }
            mo.setModifytime(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(mos[8]));//修改时间
        } catch (Exception e) {
            System.err.println("parseEuropeOddsDetail,dateFormatException,变化时间转换失败 : " + mos[8]);
            e.printStackTrace();
        }
        return mo;
    }

    public static LetGoalDetail parseSingleLetGoalDetail(String[] item) {
        //1647028,17,-0.25,0.81,1.12,False,False,2019-10-15 15:31:04,False,2
        //比赛ID,公司ID,初盘盘口,主队初盘赔率,客队初盘赔率,即时盘口,主队即时赔率,客队即时赔率,是否封盘1,是否走地,变盘时间,是否封盘2
        //比赛ID,公司ID,即时盘口,主队即时赔率,客队即时赔率,是否封盘1,是否走地,变盘时间,是否封盘2
        LetGoalDetail mo = new LetGoalDetail();

        try {
            if (StringUtils.isNotEmpty(item[5]) && StringUtils.isNotEmpty(item[6]) && StringUtils.isNotEmpty(item[7])) {
                mo.setUpodds(Float.parseFloat(item[3]));
                mo.setGoal(Float.parseFloat(item[2]));
                mo.setDownodds(Float.parseFloat(item[4]));
            }
            mo.setModifytime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(item[8]));//修改时间
        } catch (Exception e) {
            System.err.println("parseSingleLetGoalDetail,dateFormatException,变化时间转换失败");
            e.printStackTrace();
        }
        return mo;
    }

    public static Letgoal parseSingleLetGoal(String[] info) {
        //比赛ID,公司ID,初盘盘口,主队初盘赔率,客队初盘赔率,即时盘口,主队即时赔率,客队即时赔率,是否封盘1,是否走地,变盘时间,是否封盘2
        Letgoal mo = new Letgoal();
        mo.setScheduleid(Integer.parseInt(info[0]));//比赛id
        mo.setCompanyid(Integer.parseInt(info[1]));//公司id

        if (StringUtils.isNotEmpty(info[2]) && StringUtils.isNotEmpty(info[3]) && StringUtils.isNotEmpty(info[4])) {
            mo.setFirstgoal(Float.parseFloat(info[2]));//初盘及赔率
            mo.setFirstupodds(Float.parseFloat(info[3]));//初盘及赔率
            mo.setFirstdownodds(Float.parseFloat(info[4]));//初盘及赔率
        }

        if (StringUtils.isNotEmpty(info[5]) && StringUtils.isNotEmpty(info[6]) && StringUtils.isNotEmpty(info[7])) {
            mo.setGoal(Float.parseFloat(info[5]));//即时盘及赔率
            mo.setUpodds(Float.parseFloat(info[6]));//即时盘及赔率
            mo.setDownodds(Float.parseFloat(info[7]));//即时盘及赔率

            mo.setGoalReal(Float.parseFloat(info[5]));//终
            mo.setUpoddsReal(Float.parseFloat(info[6]));//终
            mo.setDownoddsReal(Float.parseFloat(info[7]));//终
        }

        //mo.setRunning(info[0]);//是否正在走地
        //mo.setResult(info[0]);//盘路结果 1主队赢 2走 3输

        try {
            mo.setModifytime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(info[10]));//变化时间
        } catch (Exception e) {
            System.err.println("parseSingleLetGoal,dateFormatException,变化时间转换失败" + info[10]);
            e.printStackTrace();
        }
        mo.setClosepan(info[8].startsWith("T"));//球探网手动封盘
        mo.setZoudi(info[9].startsWith("T"));//主网是否开走地
        mo.setIsstoplive(info[11].startsWith("T"));//主网是否封盘
        return mo;
    }

    public static StandardDetail parseSingleStandardDetail(String[] item) {
        //比赛ID,公司ID,即时盘口,主队即时赔率,客队即时赔率,是否封盘1,是否走地,变盘时间,是否封盘2
        StandardDetail mo = new StandardDetail();
        if (StringUtils.isNotEmpty(item[2])&&StringUtils.isNotEmpty(item[3])&&StringUtils.isNotEmpty(item[4])){
            mo.setHomewin(Float.parseFloat(item[2]));
            mo.setStandoff(Float.parseFloat(item[3]));
            mo.setGuestwin(Float.parseFloat(item[4]));
        }
        return mo;
    }

    public static Standard parseSingleStandard(String[] info) {
        //比赛ID,公司ID,即时盘口,主队即时赔率,客队即时赔率,是否封盘1,是否走地,变盘时间,是否封盘2
        Standard mo = new Standard();
        mo.setScheduleid(Integer.parseInt(info[0]));//比赛id
        mo.setCompanyid(Integer.parseInt(info[1]));//公司ID

        mo.setHomewin(info[2]);//终盘
        mo.setStandoff(info[3]);//
        mo.setGuestwin(info[4]);//
        //mo.setResult(info[]);//盘路结果

        mo.setHomewinR(info[2]);//即时
        mo.setStandoffR(info[3]);//
        mo.setGuestwinR(info[4]);//

        mo.setClosepan(info[9].startsWith("T"));//封盘
        try {
            mo.setModifytime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(info[8]));//变化时间
        } catch (Exception e) {
            System.err.println("parseStandard,dateFormatException,变化时间转换失败" + info[8]);
            e.printStackTrace();
        }
        return mo;
    }

    public static TotalScoreDetail parseSingleTotalScoreDetail(String[] info) {
        //比赛ID,公司ID,时盘盘口,即时盘大球赔率,即时盘小球赔率,变盘时间,是否封盘2
        TotalScoreDetail mo = new TotalScoreDetail();
        mo.setUpodds(Float.parseFloat(info[3]));
        mo.setDownodds(Float.parseFloat(info[4]));
        mo.setGoal(Float.parseFloat(info[2]));
        try {
            mo.setModifytime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(info[5]));//修改时间
        } catch (Exception e) {
            System.err.println("parseSingleTotalScoreDetail,dateFormatException,变化时间转换失败");
            e.printStackTrace();
        }
        return mo;
    }

    public static TotalScore parseSingleTotalScore(String[] info) {
        TotalScore mo = new TotalScore();
        //比赛ID,公司ID,时盘盘口,即时盘大球赔率,即时盘小球赔率,变盘时间,是否封盘2
        mo.setScheduleid(Integer.parseInt(info[0]));
        mo.setCompanyid(Integer.parseInt(info[1]));

        mo.setGoal(Float.parseFloat(info[2]));
        mo.setUpodds(Float.parseFloat(info[3]));
        mo.setDownodds(Float.parseFloat(info[4]));

        mo.setGoalReal(Float.parseFloat(info[2]));
        mo.setUpoddsReal(Float.parseFloat(info[3]));
        mo.setDownoddsReal(Float.parseFloat(info[4]));


        //mo.setGoalReal();
        //mo.setUpoddsReal();
        //mo.setDownoddsReal();
        //mo.setResult();
        //mo.setZoudi();

        //mo.setClosepan();//球探封盘
        try {
            mo.setModifytime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(info[5]));//变化时间
        } catch (Exception e) {
            System.err.println("parseTotalScore,dateFormatException,变化时间转换失败" + info[5]);
            e.printStackTrace();
        }
        mo.setIsstoplive(info[6].startsWith("T"));//主网封盘
        return mo;
    }
}