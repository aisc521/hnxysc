package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.JcSchedulespMapper;
import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.db.model.JcSchedulesp;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.JcSchedulespService;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.Odds.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/12 13:51
 */
@Service
public class JcSchedulespServiceImpl implements JcSchedulespService {

    @Resource
    private JcSchedulespMapper jcSchedulespMapper;
    @Override
    public JcSchedulesp queryJcSchedulespById(Integer scheduleId) {
        return jcSchedulespMapper.queryJcSchedulespByScId(scheduleId);
    }

    @Override
    public void updateJcSchedulesp(JcSchedulesp jcSchedulesp, JcSchedule jcSchedule, JcFootBallOddsRsp jcFootBallOddsRsp) {
       /* jcSchedulesp.setId(jcSchedule.getScheduleid());*/

    }

    @Override
    public Integer insertJcSchedulesp(JcSchedule jcSchedule, JcFootBallOddsRsp jcFootBallOddsRsp) throws BaseException {
        /*JcSchedulesp jcSchedulesp = new JcSchedulesp();
        jcSchedulesp.setId(jcSchedule.getScheduleid());
        //jcSchedulesp.setKind();//过关类型
        //让球胜平负sp
        List<JcFootBallOddsRqRsp> rq = jcFootBallOddsRsp.getRq();
        JcFootBallOddsRqRsp jcFootBallOddsRqRsp = rq.get(0);
        jcSchedulesp.setWl3(Double.valueOf(jcFootBallOddsRqRsp.getRq3()));//让球胜平负sp
        jcSchedulesp.setWl1(Double.valueOf(jcFootBallOddsRqRsp.getRq1()));//让球胜平负sp
        jcSchedulesp.setWl0(Double.valueOf(jcFootBallOddsRqRsp.getRq0()));//让球胜平负sp
        //jcSchedulesp.setWlend();//让球胜平负开奖sp
        //进球数即时sp
        List<JcFootBallOddsJqRsp> jq = jcFootBallOddsRsp.getJq();
        JcFootBallOddsJqRsp jcFootBallOddsJqRsp = jq.get(0);
        jcSchedulesp.setT0(Double.valueOf(jcFootBallOddsJqRsp.getT0()));
        jcSchedulesp.setT1(Double.valueOf(jcFootBallOddsJqRsp.getT1()));
        jcSchedulesp.setT2(Double.valueOf(jcFootBallOddsJqRsp.getT2()));
        jcSchedulesp.setT3(Double.valueOf(jcFootBallOddsJqRsp.getT3()));
        jcSchedulesp.setT4(Double.valueOf(jcFootBallOddsJqRsp.getT4()));
        jcSchedulesp.setT5(Double.valueOf(jcFootBallOddsJqRsp.getT5()));
        jcSchedulesp.setT6(Double.valueOf(jcFootBallOddsJqRsp.getT6()));
        jcSchedulesp.setT7(Double.valueOf(jcFootBallOddsJqRsp.getT7()));
        //jcSchedulesp.setTend();//开奖sp
        //比分即时sp
        List<JcFootBallOddsBfRsp> bf = jcFootBallOddsRsp.getBf();
        JcFootBallOddsBfRsp jcFootBallOddsBfRsp = bf.get(0);
        jcSchedulesp.setSw10(Double.valueOf(jcFootBallOddsBfRsp.getSw10()));
        jcSchedulesp.setSw20(Double.valueOf(jcFootBallOddsBfRsp.getSw20()));
        jcSchedulesp.setSw21(Double.valueOf(jcFootBallOddsBfRsp.getSw21()));
        jcSchedulesp.setSw30(Double.valueOf(jcFootBallOddsBfRsp.getSw30()));
        jcSchedulesp.setSw31(Double.valueOf(jcFootBallOddsBfRsp.getSw31()));
        jcSchedulesp.setSw32(Double.valueOf(jcFootBallOddsBfRsp.getSw32()));
        jcSchedulesp.setSw40(Double.valueOf(jcFootBallOddsBfRsp.getSw40()));
        jcSchedulesp.setSw41(Double.valueOf(jcFootBallOddsBfRsp.getSw41()));
        jcSchedulesp.setSw42(Double.valueOf(jcFootBallOddsBfRsp.getSw42()));
        jcSchedulesp.setSw50(Double.valueOf(jcFootBallOddsBfRsp.getSw50()));
        jcSchedulesp.setSw51(Double.valueOf(jcFootBallOddsBfRsp.getSw51()));
        jcSchedulesp.setSw52(Double.valueOf(jcFootBallOddsBfRsp.getSw52()));
        jcSchedulesp.setSw5(Double.valueOf(jcFootBallOddsBfRsp.getSw5()));
        jcSchedulesp.setSd00(Double.valueOf(jcFootBallOddsBfRsp.getSd00()));
        jcSchedulesp.setSd11(Double.valueOf(jcFootBallOddsBfRsp.getSd11()));
        jcSchedulesp.setSd22(Double.valueOf(jcFootBallOddsBfRsp.getSd22()));
        jcSchedulesp.setSd33(Double.valueOf(jcFootBallOddsBfRsp.getSd33()));
        jcSchedulesp.setSd4(Double.valueOf(jcFootBallOddsBfRsp.getSd4()));
        jcSchedulesp.setSl01(Double.valueOf(jcFootBallOddsBfRsp.getSl01()));
        jcSchedulesp.setSl02(Double.valueOf(jcFootBallOddsBfRsp.getSl02()));
        jcSchedulesp.setSl12(Double.valueOf(jcFootBallOddsBfRsp.getSl12()));
        jcSchedulesp.setSl03(Double.valueOf(jcFootBallOddsBfRsp.getSl03()));
        jcSchedulesp.setSl13(Double.valueOf(jcFootBallOddsBfRsp.getSl13()));
        jcSchedulesp.setSl23(Double.valueOf(jcFootBallOddsBfRsp.getSl23()));
        jcSchedulesp.setSl04(Double.valueOf(jcFootBallOddsBfRsp.getSl04()));
        jcSchedulesp.setSl14(Double.valueOf(jcFootBallOddsBfRsp.getSl14()));
        jcSchedulesp.setSl24(Double.valueOf(jcFootBallOddsBfRsp.getSl24()));
        jcSchedulesp.setSl05(Double.valueOf(jcFootBallOddsBfRsp.getSl05()));
        jcSchedulesp.setSl15(Double.valueOf(jcFootBallOddsBfRsp.getSl15()));
        jcSchedulesp.setSl25(Double.valueOf(jcFootBallOddsBfRsp.getSl25()));
        jcSchedulesp.setSl5(Double.valueOf(jcFootBallOddsBfRsp.getSl5()));
        //jcSchedulesp.setSend();//开奖sp
        //半全场即时sp
        List<JcFootBallOddsBqcRsp> bqc = jcFootBallOddsRsp.getBqc();
        JcFootBallOddsBqcRsp jcFootBallOddsBqcRsp = bqc.get(0);
        jcSchedulesp.setHt33(Double.valueOf(jcFootBallOddsBqcRsp.getHt33()));
        jcSchedulesp.setHt31(Double.valueOf(jcFootBallOddsBqcRsp.getHt31()));
        jcSchedulesp.setHt13(Double.valueOf(jcFootBallOddsBqcRsp.getHt13()));
        jcSchedulesp.setHt11(Double.valueOf(jcFootBallOddsBqcRsp.getHt11()));
        jcSchedulesp.setHt10(Double.valueOf(jcFootBallOddsBqcRsp.getHt10()));
        jcSchedulesp.setHt03(Double.valueOf(jcFootBallOddsBqcRsp.getHt03()));
        jcSchedulesp.setHt01(Double.valueOf(jcFootBallOddsBqcRsp.getHt01()));
        jcSchedulesp.setHt00(Double.valueOf(jcFootBallOddsBqcRsp.getHt00()));
        jcSchedulesp.setHt30(Double.valueOf(jcFootBallOddsBqcRsp.getHt30()));
        //jcSchedulesp.setHtend();
        //更新时间
        jcSchedulesp.setUpdatetime(new Date());
        //让球胜平负是否停售
        if("true".equals(jcFootBallOddsRqRsp.getStop())){
            jcSchedulesp.setWlstop(true);
        }else{
            jcSchedulesp.setWlstop(false);
        }
        //进球数是否停售
        if("true".equals(jcFootBallOddsJqRsp.getStop())){
            jcSchedulesp.setTstop(true);
        }else{
            jcSchedulesp.setTstop(false);
        }
        //比分是否停售
        if("true".equals(jcFootBallOddsBfRsp.getStop())){
            jcSchedulesp.setSstop(true);
        }else{
            jcSchedulesp.setSstop(false);
        }
        //半全场是否停售
        if("true".equals(jcFootBallOddsBqcRsp.getStop())){
            jcSchedulesp.setHtstop(true);
        }else{
            jcSchedulesp.setHtstop(false);
        }
        //胜平负即时sp
        List<JcFootBallOddsSfRsp> sf = jcFootBallOddsRsp.getSf();
        JcFootBallOddsSfRsp jcFootBallOddsSfRsp = sf.get(0);
        jcSchedulesp.setSf3(Double.valueOf(jcFootBallOddsSfRsp.getSf3()));
        jcSchedulesp.setSf1(Double.valueOf(jcFootBallOddsSfRsp.getSf1()));
        jcSchedulesp.setSf0(Double.valueOf(jcFootBallOddsSfRsp.getSf0()));
        //jcSchedulesp.setSfend();
        //胜平负是否停售
        if("true".equals(jcFootBallOddsSfRsp.getStop())){
            jcSchedulesp.setSfstop(true);
        }else{
            jcSchedulesp.setSfstop(false);
        }
        int i = jcSchedulespMapper.insertSelective(jcSchedulesp);
        //获取返回的主键
        if(i <= 0){
            throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                    ProtocolCodeMsg.INSERT_FAILE.getMsg());
        }
        return jcSchedulesp.getSpid();*/
        return 1;
    }
}
