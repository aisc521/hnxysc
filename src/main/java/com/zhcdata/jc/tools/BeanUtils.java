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
 * 　　　　　　　　　┃　　　┃　　　　create by xuan on 2019/9/11
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

import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.xml.rsp.MatchListRsp;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.zhcdata.jc.quartz.job.Match.MatchListJob.parseToFormat;

public class BeanUtils {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Schedule parseSchedule(MatchListRsp model) throws ParseException {
        Schedule inDb = new Schedule();
        inDb.setScheduleid(Integer.parseInt(model.getA()));//比赛id
        if (model.getC() != null && model.getC().split(",").length == 4)//联赛id
            inDb.setSclassid(Integer.parseInt(model.getC().split(",")[3]));
        if (model.getX() != null)//赛季
            inDb.setMatchseason(model.getX());
        //if (StringUtils.isNotEmpty(model.getS()))//轮次
        //    inDb.setRound(Short.valueOf(model.getS()));
        // TODO: 2019/9/11 轮次
        if (model.getY() != null)//组
            inDb.setGrouping(model.getY());
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
        if (model.getD() != null)
            inDb.setMatchtime(sdf.parse(parseToFormat(model.getD())));//开始时间
        // TODO: 2019/9/10 下半场开始时间
        if (model.getT() != null)
            inDb.setLocation(model.getT());//比赛地点
        if (model.getP() != null)
            inDb.setHomeOrder(model.getP());//主队排名
        if (model.getQ() != null)
            inDb.setGuestOrder(model.getQ());//客队排名
        if (StringUtils.isNotEmpty(model.getF()+""))
            inDb.setMatchstate(Short.parseShort(("" + model.getF())));//比赛状态
        if (StringUtils.isNotEmpty(model.getU()))
            inDb.setWeathericon(Short.parseShort(model.getU()));//天气图标
        if (model.getV() != null)
            inDb.setWeather(model.getV());//天气
        if (model.getW() != null)
            inDb.setTemperature(model.getW());//温度
        if (model.getExplain2() != null)
            inDb.setTemperature(model.getExplain2().split(";")[0]);//电视台信息
        // TODO: 2019/9/10 裁判信息 需要用 裁判数据接口
        // TODO: 2019/9/10 阵容需要用出场阵容接口
        if (model.getJ() >= 0)
            inDb.setHomescore(Short.parseShort("" + model.getJ()));//主队得分
        if (model.getK() >= 0)
            inDb.setGuestscore(Short.parseShort("" + model.getK()));//客队得分
        if (StringUtils.isNotEmpty(model.getL()))
            inDb.setHomehalfscore(Short.parseShort(model.getL()));//主队半场
        if (StringUtils.isNotEmpty(model.getM()))
            inDb.setGuesthalfscore(Short.parseShort(model.getM()));//客队半场
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


    public static boolean ScheduleNoChange(Schedule s1,Schedule s2){
        return s1.equals(s2);
    }

}