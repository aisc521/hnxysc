package com.zhcdata.jc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhcdata.db.mapper.JcScheduleMapper;
import com.zhcdata.db.mapper.JcSchedulespMapper;
import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.db.model.JcSchedulesp;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.dto.QueryFiveGameDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.JcSchedulespService;
import com.zhcdata.jc.tools.DateTimeUtils;
import com.zhcdata.jc.tools.JcLotteryUtils;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.Odds.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/12 13:51
 */
@Service
public class JcSchedulespServiceImpl implements JcSchedulespService {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    @Resource
    private JcSchedulespMapper jcSchedulespMapper;

    @Resource
    private JcScheduleMapper jcScheduleMapper;

    @Override
    public JcSchedulesp queryJcSchedulespById(Integer scheduleId) {
        return jcSchedulespMapper.queryJcSchedulespByScId(scheduleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJcSchedulesp(JcSchedulesp jcSchedulesp, JcSchedule jcSchedule, JcFootBallOddsRsp jcFootBallOddsRsp,Schedule schedule) throws BaseException {
        jcSchedulesp.setId(String.valueOf(jcSchedule.getId()));
        jcSchedulesp.setKind("2");//单关过关
        //让球胜平负sp
        List<JcFootBallOddsRqRsp> rq = jcFootBallOddsRsp.getRq();
        JcFootBallOddsRqRsp jcFootBallOddsRqRsp = rq.get(0);
        jcSchedulesp.setWl3(jcFootBallOddsRqRsp.getRq3());//让球胜平负sp
        jcSchedulesp.setWl1(jcFootBallOddsRqRsp.getRq1());//让球胜平负sp
        jcSchedulesp.setWl0(jcFootBallOddsRqRsp.getRq0());//让球胜平负sp

        //进球数即时sp
        List<JcFootBallOddsJqRsp> jq = jcFootBallOddsRsp.getJq();
        JcFootBallOddsJqRsp jcFootBallOddsJqRsp = jq.get(0);
        jcSchedulesp.setT0(jcFootBallOddsJqRsp.getT0());
        jcSchedulesp.setT1(jcFootBallOddsJqRsp.getT1());
        jcSchedulesp.setT2(jcFootBallOddsJqRsp.getT2());
        jcSchedulesp.setT3(jcFootBallOddsJqRsp.getT3());
        jcSchedulesp.setT4(jcFootBallOddsJqRsp.getT4());
        jcSchedulesp.setT5(jcFootBallOddsJqRsp.getT5());
        jcSchedulesp.setT6(jcFootBallOddsJqRsp.getT6());
        jcSchedulesp.setT7(jcFootBallOddsJqRsp.getT7());

        //比分即时sp
        List<JcFootBallOddsBfRsp> bf = jcFootBallOddsRsp.getBf();
        JcFootBallOddsBfRsp jcFootBallOddsBfRsp = bf.get(0);
        jcSchedulesp.setSw10(jcFootBallOddsBfRsp.getSw10());
        jcSchedulesp.setSw20(jcFootBallOddsBfRsp.getSw20());
        jcSchedulesp.setSw21(jcFootBallOddsBfRsp.getSw21());
        jcSchedulesp.setSw30(jcFootBallOddsBfRsp.getSw30());
        jcSchedulesp.setSw31(jcFootBallOddsBfRsp.getSw31());
        jcSchedulesp.setSw32(jcFootBallOddsBfRsp.getSw32());
        jcSchedulesp.setSw40(jcFootBallOddsBfRsp.getSw40());
        jcSchedulesp.setSw41(jcFootBallOddsBfRsp.getSw41());
        jcSchedulesp.setSw42(jcFootBallOddsBfRsp.getSw42());
        jcSchedulesp.setSw50(jcFootBallOddsBfRsp.getSw50());
        jcSchedulesp.setSw51(jcFootBallOddsBfRsp.getSw51());
        jcSchedulesp.setSw52(jcFootBallOddsBfRsp.getSw52());
        jcSchedulesp.setSw5(jcFootBallOddsBfRsp.getSw5());
        jcSchedulesp.setSd00(jcFootBallOddsBfRsp.getSd00());
        jcSchedulesp.setSd11(jcFootBallOddsBfRsp.getSd11());
        jcSchedulesp.setSd22(jcFootBallOddsBfRsp.getSd22());
        jcSchedulesp.setSd33(jcFootBallOddsBfRsp.getSd33());
        jcSchedulesp.setSd4(jcFootBallOddsBfRsp.getSd4());
        jcSchedulesp.setSl01(jcFootBallOddsBfRsp.getSl01());
        jcSchedulesp.setSl02(jcFootBallOddsBfRsp.getSl02());
        jcSchedulesp.setSl12(jcFootBallOddsBfRsp.getSl12());
        jcSchedulesp.setSl03(jcFootBallOddsBfRsp.getSl03());
        jcSchedulesp.setSl13(jcFootBallOddsBfRsp.getSl13());
        jcSchedulesp.setSl23(jcFootBallOddsBfRsp.getSl23());
        jcSchedulesp.setSl04(jcFootBallOddsBfRsp.getSl04());
        jcSchedulesp.setSl14(jcFootBallOddsBfRsp.getSl14());
        jcSchedulesp.setSl24(jcFootBallOddsBfRsp.getSl24());
        jcSchedulesp.setSl05(jcFootBallOddsBfRsp.getSl05());
        jcSchedulesp.setSl15(jcFootBallOddsBfRsp.getSl15());
        jcSchedulesp.setSl25(jcFootBallOddsBfRsp.getSl25());
        jcSchedulesp.setSl5(jcFootBallOddsBfRsp.getSl5());

        //半全场即时sp
        List<JcFootBallOddsBqcRsp> bqc = jcFootBallOddsRsp.getBqc();
        JcFootBallOddsBqcRsp jcFootBallOddsBqcRsp = bqc.get(0);
        jcSchedulesp.setHt33(jcFootBallOddsBqcRsp.getHt33());
        jcSchedulesp.setHt31(jcFootBallOddsBqcRsp.getHt31());
        jcSchedulesp.setHt13(jcFootBallOddsBqcRsp.getHt13());
        jcSchedulesp.setHt11(jcFootBallOddsBqcRsp.getHt11());
        jcSchedulesp.setHt10(jcFootBallOddsBqcRsp.getHt10());
        jcSchedulesp.setHt03(jcFootBallOddsBqcRsp.getHt03());
        jcSchedulesp.setHt01(jcFootBallOddsBqcRsp.getHt01());
        jcSchedulesp.setHt00(jcFootBallOddsBqcRsp.getHt00());
        jcSchedulesp.setHt30(jcFootBallOddsBqcRsp.getHt30());


        //更新时间
        jcSchedulesp.setUpdatetime(sdf.format(new Date()));
        //让球胜平负是否停售
        if("true".equals(jcFootBallOddsRqRsp.getStop())){
            jcSchedulesp.setWlstop("1");
        }else{
            jcSchedulesp.setWlstop("0");
        }
        //进球数是否停售
        if("true".equals(jcFootBallOddsJqRsp.getStop())){
            jcSchedulesp.setTstop("1");
        }else{
            jcSchedulesp.setTstop("0");
        }
        //比分是否停售
        if("true".equals(jcFootBallOddsBfRsp.getStop())){
            jcSchedulesp.setSstop("1");
        }else{
            jcSchedulesp.setSstop("0");
        }
        //半全场是否停售
        if("true".equals(jcFootBallOddsBqcRsp.getStop())){
            jcSchedulesp.setHtstop("1");
        }else{
            jcSchedulesp.setHtstop("0");
        }
        //胜平负即时sp
        List<JcFootBallOddsSfRsp> sf = jcFootBallOddsRsp.getSf();
        JcFootBallOddsSfRsp jcFootBallOddsSfRsp = sf.get(0);
        jcSchedulesp.setSf3(jcFootBallOddsSfRsp.getSf3());
        jcSchedulesp.setSf1(jcFootBallOddsSfRsp.getSf1());
        jcSchedulesp.setSf0(jcFootBallOddsSfRsp.getSf0());
        //胜平负是否停售
        if("true".equals(jcFootBallOddsSfRsp.getStop())){
            jcSchedulesp.setSfstop("1");
        }else{
            jcSchedulesp.setSfstop("0");
        }

        //判断是否完场
        Short matchState = jcSchedule.getMatchstate();
        if(matchState == -1){//完场
            jcSchedulesp.setWlend(JcLotteryUtils.JcKjSp(jcFootBallOddsRsp,schedule,jcSchedule,"11"));//让球胜平负开奖sp
            jcSchedulesp.setTend(JcLotteryUtils.JcKjSp(jcFootBallOddsRsp,schedule,jcSchedule,"12"));//进球数开奖sp
            jcSchedulesp.setSend(JcLotteryUtils.JcKjSp(jcFootBallOddsRsp,schedule,jcSchedule,"13"));//比分开奖sp
            jcSchedulesp.setHtend(JcLotteryUtils.JcKjSp(jcFootBallOddsRsp,schedule,jcSchedule,"14"));//半全场开奖sp
            jcSchedulesp.setSfend(JcLotteryUtils.JcKjSp(jcFootBallOddsRsp,schedule,jcSchedule,"15"));//胜平负开奖sp
        }
        Example example = new Example(JcSchedulesp.class);
        example.createCriteria()
                .andEqualTo("id",jcSchedulesp.getId())
                .andEqualTo("kind",jcSchedulesp.getKind())
        ;
        int i = jcSchedulespMapper.updateByExampleSelective(jcSchedulesp,example);
        if(i <= 0){
            throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                    ProtocolCodeMsg.UPDATE_FAILE.getMsg());
        }


        //更新 竞彩赛程表 是否单关标识
        String danRq = jcFootBallOddsRsp.getDan_rq();//让球单关标识
        String danBf = jcFootBallOddsRsp.getDan_bf();//比分单关标识
        String danJq = jcFootBallOddsRsp.getDan_jq();//进球单关标识
        String danBqc = jcFootBallOddsRsp.getDan_bqc();//半全场单关标识
        String danSf = jcFootBallOddsRsp.getDan_sf();//胜平负单关标识

        if("true".equals(danRq)){
            jcSchedule.setSingle101(1);//让球
        }else{
            jcSchedule.setSingle101(0);//让球
        }

        if("true".equals(danBf)){
            jcSchedule.setSingle102(1);//比分单关标识
        }else{
            jcSchedule.setSingle102(0);//比分单关标识
        }

        if("true".equals(danJq)){
            jcSchedule.setSingle103(1);//进球单关标识
        }else{
            jcSchedule.setSingle103(0);//进球单关标识
        }

        if("true".equals(danBqc)){
            jcSchedule.setSingle104(1);//半全场单关标识
        }else{
            jcSchedule.setSingle104(0);//半全场单关标识
        }

        if("true".equals(danSf)){
            jcSchedule.setSingle105(1);//胜平负单关标识
        }else{
            jcSchedule.setSingle105(0);//胜平负单关标识
        }

        Example example1 = new Example(JcSchedule.class);
        example1.createCriteria().andEqualTo("id",jcSchedule.getId());
        int j = jcScheduleMapper.updateByExampleSelective(jcSchedule,example1);
        if(j <= 0){
            throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                    ProtocolCodeMsg.UPDATE_FAILE.getMsg());
        }

    }

    @Override
    public Integer insertJcSchedulesp(JcSchedule jcSchedule, JcFootBallOddsRsp jcFootBallOddsRsp,Schedule schedule) throws BaseException {
        JcSchedulesp jcSchedulesp = new JcSchedulesp();
        jcSchedulesp.setId(String.valueOf(jcSchedule.getId()));
        jcSchedulesp.setKind("2");//单关过关
        //让球胜平负sp
        List<JcFootBallOddsRqRsp> rq = jcFootBallOddsRsp.getRq();
        JcFootBallOddsRqRsp jcFootBallOddsRqRsp = rq.get(0);
        jcSchedulesp.setWl3(jcFootBallOddsRqRsp.getRq3());//让球胜平负sp
        jcSchedulesp.setWl1(jcFootBallOddsRqRsp.getRq1());//让球胜平负sp
        jcSchedulesp.setWl0(jcFootBallOddsRqRsp.getRq0());//让球胜平负sp

        //进球数即时sp
        List<JcFootBallOddsJqRsp> jq = jcFootBallOddsRsp.getJq();
        JcFootBallOddsJqRsp jcFootBallOddsJqRsp = jq.get(0);
        jcSchedulesp.setT0(jcFootBallOddsJqRsp.getT0());
        jcSchedulesp.setT1(jcFootBallOddsJqRsp.getT1());
        jcSchedulesp.setT2(jcFootBallOddsJqRsp.getT2());
        jcSchedulesp.setT3(jcFootBallOddsJqRsp.getT3());
        jcSchedulesp.setT4(jcFootBallOddsJqRsp.getT4());
        jcSchedulesp.setT5(jcFootBallOddsJqRsp.getT5());
        jcSchedulesp.setT6(jcFootBallOddsJqRsp.getT6());
        jcSchedulesp.setT7(jcFootBallOddsJqRsp.getT7());

        //比分即时sp
        List<JcFootBallOddsBfRsp> bf = jcFootBallOddsRsp.getBf();
        JcFootBallOddsBfRsp jcFootBallOddsBfRsp = bf.get(0);
        jcSchedulesp.setSw10(jcFootBallOddsBfRsp.getSw10());
        jcSchedulesp.setSw20(jcFootBallOddsBfRsp.getSw20());
        jcSchedulesp.setSw21(jcFootBallOddsBfRsp.getSw21());
        jcSchedulesp.setSw30(jcFootBallOddsBfRsp.getSw30());
        jcSchedulesp.setSw31(jcFootBallOddsBfRsp.getSw31());
        jcSchedulesp.setSw32(jcFootBallOddsBfRsp.getSw32());
        jcSchedulesp.setSw40(jcFootBallOddsBfRsp.getSw40());
        jcSchedulesp.setSw41(jcFootBallOddsBfRsp.getSw41());
        jcSchedulesp.setSw42(jcFootBallOddsBfRsp.getSw42());
        jcSchedulesp.setSw50(jcFootBallOddsBfRsp.getSw50());
        jcSchedulesp.setSw51(jcFootBallOddsBfRsp.getSw51());
        jcSchedulesp.setSw52(jcFootBallOddsBfRsp.getSw52());
        jcSchedulesp.setSw5(jcFootBallOddsBfRsp.getSw5());
        jcSchedulesp.setSd00(jcFootBallOddsBfRsp.getSd00());
        jcSchedulesp.setSd11(jcFootBallOddsBfRsp.getSd11());
        jcSchedulesp.setSd22(jcFootBallOddsBfRsp.getSd22());
        jcSchedulesp.setSd33(jcFootBallOddsBfRsp.getSd33());
        jcSchedulesp.setSd4(jcFootBallOddsBfRsp.getSd4());
        jcSchedulesp.setSl01(jcFootBallOddsBfRsp.getSl01());
        jcSchedulesp.setSl02(jcFootBallOddsBfRsp.getSl02());
        jcSchedulesp.setSl12(jcFootBallOddsBfRsp.getSl12());
        jcSchedulesp.setSl03(jcFootBallOddsBfRsp.getSl03());
        jcSchedulesp.setSl13(jcFootBallOddsBfRsp.getSl13());
        jcSchedulesp.setSl23(jcFootBallOddsBfRsp.getSl23());
        jcSchedulesp.setSl04(jcFootBallOddsBfRsp.getSl04());
        jcSchedulesp.setSl14(jcFootBallOddsBfRsp.getSl14());
        jcSchedulesp.setSl24(jcFootBallOddsBfRsp.getSl24());
        jcSchedulesp.setSl05(jcFootBallOddsBfRsp.getSl05());
        jcSchedulesp.setSl15(jcFootBallOddsBfRsp.getSl15());
        jcSchedulesp.setSl25(jcFootBallOddsBfRsp.getSl25());
        jcSchedulesp.setSl5(jcFootBallOddsBfRsp.getSl5());

        //半全场即时sp
        List<JcFootBallOddsBqcRsp> bqc = jcFootBallOddsRsp.getBqc();
        JcFootBallOddsBqcRsp jcFootBallOddsBqcRsp = bqc.get(0);
        jcSchedulesp.setHt33(jcFootBallOddsBqcRsp.getHt33());
        jcSchedulesp.setHt31(jcFootBallOddsBqcRsp.getHt31());
        jcSchedulesp.setHt13(jcFootBallOddsBqcRsp.getHt13());
        jcSchedulesp.setHt11(jcFootBallOddsBqcRsp.getHt11());
        jcSchedulesp.setHt10(jcFootBallOddsBqcRsp.getHt10());
        jcSchedulesp.setHt03(jcFootBallOddsBqcRsp.getHt03());
        jcSchedulesp.setHt01(jcFootBallOddsBqcRsp.getHt01());
        jcSchedulesp.setHt00(jcFootBallOddsBqcRsp.getHt00());
        jcSchedulesp.setHt30(jcFootBallOddsBqcRsp.getHt30());


        //更新时间
        jcSchedulesp.setUpdatetime(sdf.format(new Date()));
        //让球胜平负是否停售
        if("true".equals(jcFootBallOddsRqRsp.getStop())){
            jcSchedulesp.setWlstop("1");
        }else{
            jcSchedulesp.setWlstop("0");
        }
        //进球数是否停售
        if("true".equals(jcFootBallOddsJqRsp.getStop())){
            jcSchedulesp.setTstop("1");
        }else{
            jcSchedulesp.setTstop("0");
        }
        //比分是否停售
        if("true".equals(jcFootBallOddsBfRsp.getStop())){
            jcSchedulesp.setSstop("1");
        }else{
            jcSchedulesp.setSstop("0");
        }
        //半全场是否停售
        if("true".equals(jcFootBallOddsBqcRsp.getStop())){
            jcSchedulesp.setHtstop("1");
        }else{
            jcSchedulesp.setHtstop("0");
        }
        //胜平负即时sp
        List<JcFootBallOddsSfRsp> sf = jcFootBallOddsRsp.getSf();
        JcFootBallOddsSfRsp jcFootBallOddsSfRsp = sf.get(0);
        jcSchedulesp.setSf3(jcFootBallOddsSfRsp.getSf3());
        jcSchedulesp.setSf1(jcFootBallOddsSfRsp.getSf1());
        jcSchedulesp.setSf0(jcFootBallOddsSfRsp.getSf0());
        //胜平负是否停售
        if("true".equals(jcFootBallOddsSfRsp.getStop())){
            jcSchedulesp.setSfstop("1");
        }else{
            jcSchedulesp.setSfstop("0");
        }

        //判断是否完场
        Short matchState = jcSchedule.getMatchstate();
        if(matchState == -1){//完场
            jcSchedulesp.setWlend(JcLotteryUtils.JcKjSp(jcFootBallOddsRsp,schedule,jcSchedule,"11"));//让球胜平负开奖sp
            jcSchedulesp.setTend(JcLotteryUtils.JcKjSp(jcFootBallOddsRsp,schedule,jcSchedule,"12"));//进球数开奖sp
            jcSchedulesp.setSend(JcLotteryUtils.JcKjSp(jcFootBallOddsRsp,schedule,jcSchedule,"13"));//比分开奖sp
            jcSchedulesp.setHtend(JcLotteryUtils.JcKjSp(jcFootBallOddsRsp,schedule,jcSchedule,"14"));//半全场开奖sp
            jcSchedulesp.setSfend(JcLotteryUtils.JcKjSp(jcFootBallOddsRsp,schedule,jcSchedule,"15"));//胜平负开奖sp
        }
        //初赔sp
        jcSchedulesp.setFirstSf0(jcFootBallOddsSfRsp.getSf0());
        jcSchedulesp.setFirstSf1(jcFootBallOddsSfRsp.getSf1());
        jcSchedulesp.setFirstSf3(jcFootBallOddsSfRsp.getSf3());

        jcSchedulesp.setFirstWl0(jcFootBallOddsRqRsp.getRq0());
        jcSchedulesp.setFirstWl1(jcFootBallOddsRqRsp.getRq1());
        jcSchedulesp.setFirstWl3(jcFootBallOddsRqRsp.getRq3());
        int i = jcSchedulespMapper.insertSelective(jcSchedulesp);
        //获取返回的主键
        if(i <= 0){
            throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                    ProtocolCodeMsg.INSERT_FAILE.getMsg());
        }

        //更新 竞彩赛程表 是否单关标识
        String danRq = jcFootBallOddsRsp.getDan_rq();//让球单关标识
        String danBf = jcFootBallOddsRsp.getDan_bf();//比分单关标识
        String danJq = jcFootBallOddsRsp.getDan_jq();//进球单关标识
        String danBqc = jcFootBallOddsRsp.getDan_bqc();//半全场单关标识
        String danSf = jcFootBallOddsRsp.getDan_sf();//胜平负单关标识

        if("true".equals(danRq)){
            jcSchedule.setSingle101(1);//让球
        }else{
            jcSchedule.setSingle101(0);//让球
        }

        if("true".equals(danBf)){
            jcSchedule.setSingle102(1);//比分单关标识
        }else{
            jcSchedule.setSingle102(0);//比分单关标识
        }

        if("true".equals(danJq)){
            jcSchedule.setSingle103(1);//进球单关标识
        }else{
            jcSchedule.setSingle103(0);//进球单关标识
        }

        if("true".equals(danBqc)){
            jcSchedule.setSingle104(1);//半全场单关标识
        }else{
            jcSchedule.setSingle104(0);//半全场单关标识
        }

        if("true".equals(danSf)){
            jcSchedule.setSingle105(1);//胜平负单关标识
        }else{
            jcSchedule.setSingle105(0);//胜平负单关标识
        }

        Example example1 = new Example(JcSchedule.class);
        example1.createCriteria().andEqualTo("id",jcSchedule.getId());
        int j = jcScheduleMapper.updateByExampleSelective(jcSchedule,example1);
        if(j <= 0){
            throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                    ProtocolCodeMsg.UPDATE_FAILE.getMsg());
        }



        return jcSchedulesp.getSpid();
    }

    @Override
    public PageInfo<Map<String, String>>  queryJczqListReuslt(int pageNo, int pageAmount, String date) throws BaseException {

        Date d = null;
        try {
            d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date satDate = new java.sql.Date(d.getTime() - (86400000 * 2));
        Date endDate = new java.sql.Date(d.getTime() + (86400000 * 2));
        String sat = new SimpleDateFormat("yyyy-MM-dd").format(satDate)+" 00:00:01";;
        String end = new SimpleDateFormat("yyyy-MM-dd").format(endDate)+" 23:59:59";
        String week = DateTimeUtils.getIs(d) ;
        PageHelper.startPage(pageNo, pageAmount);
        List<Map<String,String>> list = jcSchedulespMapper.queryJczqListReuslt(sat,end,week);
        return new PageInfo<>(list);
    }

    @Override
    public int queryTodayMatchCount(String date) throws BaseException {
        Date d = null;
        try {
            d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date satDate = new java.sql.Date(d.getTime() - (86400000 * 2));
        Date endDate = new java.sql.Date(d.getTime() + (86400000 * 2));
        String sat = new SimpleDateFormat("yyyy-MM-dd").format(satDate)+" 00:00:01";;
        String end = new SimpleDateFormat("yyyy-MM-dd").format(endDate)+" 23:59:59";
        String week = DateTimeUtils.getIs(d) ;

        return jcSchedulespMapper.queryTodayMatchCount(sat,end,week);
    }

    @Override
    public QueryFiveGameDto queryJcSchedulespByIdFive(Integer matchId) {
        return jcSchedulespMapper.queryJcSchedulespByIdFive(matchId);
    }


}
