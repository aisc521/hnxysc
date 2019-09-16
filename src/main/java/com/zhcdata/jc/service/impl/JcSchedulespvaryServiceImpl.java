package com.zhcdata.jc.service.impl;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.zhcdata.db.mapper.JcSchedulespvaryMapper;
import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.db.model.JcSchedulesp;
import com.zhcdata.db.model.JcSchedulespvary;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.JcSchedulespvaryService;
import com.zhcdata.jc.tools.JcLotteryUtils;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.Odds.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/12 13:52
 */
@Service
public class JcSchedulespvaryServiceImpl implements JcSchedulespvaryService {
    @Resource
    private JcSchedulespvaryMapper jcSchedulespvaryMapper;
    @Override
    public void insertJcSchedulespvary(JcSchedule jcSchedule, JcFootBallOddsRsp jcFootBallOddsRsp,Integer spId,JcSchedulesp jcSchedulesp1,Schedule schedule) throws BaseException {
        JcSchedulesp jcSchedulesp = new JcSchedulesp();
        //让球胜平负
        JcSchedulespvary rqsp = new JcSchedulespvary();
        rqsp.setSpid(spId);
        rqsp.setTypeid(Short.parseShort("101"));
        rqsp.setSp(JcLotteryUtils.jcSpChange(jcFootBallOddsRsp,jcSchedulesp,"101",schedule));
        rqsp.setChangetime(new Date());
        int i = jcSchedulespvaryMapper.insertSelective(rqsp);
        if(i <= 0){
            throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                    ProtocolCodeMsg.INSERT_FAILE.getMsg());
        }
        //比分
        JcSchedulespvary bfsp = new JcSchedulespvary();
        bfsp.setSpid(spId);
        bfsp.setTypeid(Short.parseShort("101"));
        bfsp.setSp(JcLotteryUtils.jcSpChange(jcFootBallOddsRsp,jcSchedulesp,"102",schedule));
        bfsp.setChangetime(new Date());
        int j = jcSchedulespvaryMapper.insertSelective(bfsp);
        if(j <= 0){
            throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                    ProtocolCodeMsg.INSERT_FAILE.getMsg());
        }
        //总进球
        JcSchedulespvary jqsp = new JcSchedulespvary();
        jqsp.setSpid(spId);
        jqsp.setTypeid(Short.parseShort("101"));
        jqsp.setSp(JcLotteryUtils.jcSpChange(jcFootBallOddsRsp,jcSchedulesp,"103",schedule));
        jqsp.setChangetime(new Date());
        int k = jcSchedulespvaryMapper.insertSelective(jqsp);
        if(k <= 0){
            throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                    ProtocolCodeMsg.INSERT_FAILE.getMsg());
        }
        //半全场
        JcSchedulespvary bqcsp = new JcSchedulespvary();
        bqcsp.setSpid(spId);
        bqcsp.setTypeid(Short.parseShort("101"));
        bqcsp.setSp(JcLotteryUtils.jcSpChange(jcFootBallOddsRsp,jcSchedulesp,"104",schedule));
        bqcsp.setChangetime(new Date());
        int l = jcSchedulespvaryMapper.insertSelective(bqcsp);
        if(l <= 0){
            throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                    ProtocolCodeMsg.INSERT_FAILE.getMsg());
        }
        //胜平负
        JcSchedulespvary spfcsp = new JcSchedulespvary();
        spfcsp.setSpid(spId);
        spfcsp.setTypeid(Short.parseShort("101"));
        spfcsp.setSp(JcLotteryUtils.jcSpChange(jcFootBallOddsRsp,jcSchedulesp,"105",schedule));
        spfcsp.setChangetime(new Date());
        int m = jcSchedulespvaryMapper.insertSelective(spfcsp);
        if(m <= 0){
            throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                    ProtocolCodeMsg.INSERT_FAILE.getMsg());
        }

    }
}
