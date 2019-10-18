package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.JcScheduleMapper;
import com.zhcdata.db.model.JcMatchLottery;
import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.db.model.JcSchedulesp;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.JcScheduleService;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.Odds.JcFootBallOddsRqRsp;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.Odds.JcFootBallOddsRsp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/12 13:50
 */
@Service
public class JcScheduleServiceImpl implements JcScheduleService {
    @Resource
    private JcScheduleMapper jcScheduleMapper;
    @Override
    public JcSchedule queryJcScheduleByMatchID(String id,String time) {
        String startTime = time + " 00:00:00";
        String endTime = time + " 23:59:59";
        return jcScheduleMapper.queryJcScheduleByMatchID(id,startTime,endTime);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJcSchedule(JcSchedule jcSchedule, JcFootBallOddsRsp jcFootBallOddsRsp) throws BaseException {
        List<JcFootBallOddsRqRsp> rq = jcFootBallOddsRsp.getRq();
        JcFootBallOddsRqRsp jcFootBallOddsRqRsp = rq.get(0);
        jcSchedule.setPolygoal(Float.valueOf(jcFootBallOddsRqRsp.getGoal()));//单场让球
        //更新 竞彩赛程表 是否单关标识
        String danRq = jcFootBallOddsRsp.getDan_rq();//让球单关标识
        String danBf = jcFootBallOddsRsp.getDan_bf();//比分单关标识
        String danJq = jcFootBallOddsRsp.getDan_jq();//进球单关标识
        String danBqc = jcFootBallOddsRsp.getDan_bqc();//半全场单关标识
        String danSf = jcFootBallOddsRsp.getDan_sf();//胜平负单关标识

        if("True".equals(danRq)){
            jcSchedule.setSingle101(1);//让球
        }else{
            jcSchedule.setSingle101(0);//让球
        }

        if("True".equals(danBf)){
            jcSchedule.setSingle102(1);//比分单关标识
        }else{
            jcSchedule.setSingle102(0);//比分单关标识
        }

        if("True".equals(danJq)){
            jcSchedule.setSingle103(1);//进球单关标识
        }else{
            jcSchedule.setSingle103(0);//进球单关标识
        }

        if("True".equals(danBqc)){
            jcSchedule.setSingle104(1);//半全场单关标识
        }else{
            jcSchedule.setSingle104(0);//半全场单关标识
        }

        if("True".equals(danSf)){
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
    public JcSchedule queryJcScheduleByBet007(Integer matchId) {
        return jcScheduleMapper.queryJcScheduleByBet007(matchId);
    }
}
