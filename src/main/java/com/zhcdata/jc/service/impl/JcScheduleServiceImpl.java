package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.JcScheduleMapper;
import com.zhcdata.db.model.JcMatchLottery;
import com.zhcdata.db.model.JcSchedule;
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
    public JcSchedule queryJcScheduleByMatchID(String id) {
        return jcScheduleMapper.queryJcScheduleByMatchID(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJcSchedule(JcSchedule jcSchedule, JcFootBallOddsRsp jcFootBallOddsRsp) throws BaseException {
        List<JcFootBallOddsRqRsp> rq = jcFootBallOddsRsp.getRq();
        JcFootBallOddsRqRsp jcFootBallOddsRqRsp = rq.get(0);
        jcSchedule.setPolygoal(Float.valueOf(jcFootBallOddsRqRsp.getGoal()));//单场让球
        jcSchedule.setSingle101(Boolean.valueOf(jcFootBallOddsRsp.getDan_rq()));//是否开售让球胜平负单关
        jcSchedule.setSingle102(Boolean.valueOf(jcFootBallOddsRsp.getDan_bf()));//是否开售比分单关
        jcSchedule.setSingle103(Boolean.valueOf(jcFootBallOddsRsp.getDan_jq()));//是否开售进球数单关
        jcSchedule.setSingle104(Boolean.valueOf(jcFootBallOddsRsp.getDan_bqc()));//是否开售半全场单关
        jcSchedule.setSingle105(Boolean.valueOf(jcFootBallOddsRsp.getDan_sf()));//是否开售胜平负单关
        Example example = new Example(JcMatchLottery.class);
        example.createCriteria().andEqualTo("id",jcSchedule.getId());
        int i = jcScheduleMapper.updateByExampleSelective(jcSchedule,example);
        if(i <= 0){
            throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                    ProtocolCodeMsg.UPDATE_FAILE.getMsg());
        }
    }
}
