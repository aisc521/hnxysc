package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.JcSchedulespMapper;
import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.db.model.JcSchedulesp;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.JcSchedulespService;
import com.zhcdata.jc.tools.JcLotteryUtils;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.Odds.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
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
    @Override
    public JcSchedulesp queryJcSchedulespById(Integer scheduleId) {
        return jcSchedulespMapper.queryJcSchedulespByScId(scheduleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJcSchedulesp(JcSchedulesp jcSchedulesp, JcSchedule jcSchedule, JcFootBallOddsRsp jcFootBallOddsRsp,Schedule schedule) throws BaseException {
        jcSchedulesp.setId(String.valueOf(jcSchedule.getScheduleid()));
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
            jcSchedulesp.setWlstop("true");
        }else{
            jcSchedulesp.setWlstop("false");
        }
        //进球数是否停售
        if("true".equals(jcFootBallOddsJqRsp.getStop())){
            jcSchedulesp.setTstop("true");
        }else{
            jcSchedulesp.setTstop("false");
        }
        //比分是否停售
        if("true".equals(jcFootBallOddsBfRsp.getStop())){
            jcSchedulesp.setSstop("true");
        }else{
            jcSchedulesp.setSstop("false");
        }
        //半全场是否停售
        if("true".equals(jcFootBallOddsBqcRsp.getStop())){
            jcSchedulesp.setHtstop("true");
        }else{
            jcSchedulesp.setHtstop("false");
        }
        //胜平负即时sp
        List<JcFootBallOddsSfRsp> sf = jcFootBallOddsRsp.getSf();
        JcFootBallOddsSfRsp jcFootBallOddsSfRsp = sf.get(0);
        jcSchedulesp.setSf3(jcFootBallOddsSfRsp.getSf3());
        jcSchedulesp.setSf1(jcFootBallOddsSfRsp.getSf1());
        jcSchedulesp.setSf0(jcFootBallOddsSfRsp.getSf0());
        //胜平负是否停售
        if("true".equals(jcFootBallOddsSfRsp.getStop())){
            jcSchedulesp.setSfstop("true");
        }else{
            jcSchedulesp.setSfstop("false");
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
        example.createCriteria().andEqualTo("id",jcSchedulesp.getId());
        int i = jcSchedulespMapper.updateByExampleSelective(jcSchedulesp,example);
        if(i <= 0){
            throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                    ProtocolCodeMsg.UPDATE_FAILE.getMsg());
        }
    }

    @Override
    public Integer insertJcSchedulesp(JcSchedule jcSchedule, JcFootBallOddsRsp jcFootBallOddsRsp,Schedule schedule) throws BaseException {
        JcSchedulesp jcSchedulesp = new JcSchedulesp();
        jcSchedulesp.setId(String.valueOf(jcSchedule.getScheduleid()));
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
            jcSchedulesp.setWlstop("true");
        }else{
            jcSchedulesp.setWlstop("false");
        }
        //进球数是否停售
        if("true".equals(jcFootBallOddsJqRsp.getStop())){
            jcSchedulesp.setTstop("true");
        }else{
            jcSchedulesp.setTstop("false");
        }
        //比分是否停售
        if("true".equals(jcFootBallOddsBfRsp.getStop())){
            jcSchedulesp.setSstop("true");
        }else{
            jcSchedulesp.setSstop("false");
        }
        //半全场是否停售
        if("true".equals(jcFootBallOddsBqcRsp.getStop())){
            jcSchedulesp.setHtstop("true");
        }else{
            jcSchedulesp.setHtstop("false");
        }
        //胜平负即时sp
        List<JcFootBallOddsSfRsp> sf = jcFootBallOddsRsp.getSf();
        JcFootBallOddsSfRsp jcFootBallOddsSfRsp = sf.get(0);
        jcSchedulesp.setSf3(jcFootBallOddsSfRsp.getSf3());
        jcSchedulesp.setSf1(jcFootBallOddsSfRsp.getSf1());
        jcSchedulesp.setSf0(jcFootBallOddsSfRsp.getSf0());
        //胜平负是否停售
        if("true".equals(jcFootBallOddsSfRsp.getStop())){
            jcSchedulesp.setSfstop("true");
        }else{
            jcSchedulesp.setSfstop("false");
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
        return jcSchedulesp.getSpid();
    }

    @Override
    public List<Map<String, String>> queryJczqListReuslt(String date) throws BaseException {
        String startDate = date+" 00:00:01";
        String endvDate = date+" 23:59:59";
        return jcSchedulespMapper.queryJczqListReuslt(startDate,endvDate);
    }
}
