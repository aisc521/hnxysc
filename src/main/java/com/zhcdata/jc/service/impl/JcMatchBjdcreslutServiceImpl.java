package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.JcMatchBjdcPlMapper;
import com.zhcdata.db.mapper.JcMatchBjdcreslutMapper;
import com.zhcdata.db.model.JcMatchBjdcPl;
import com.zhcdata.db.model.JcMatchBjdcreslut;
import com.zhcdata.db.model.JcMatchLottery;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.JcMatchBjdcreslutService;
import com.zhcdata.jc.tools.JcLotteryUtils;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.BjDcLotteryQuery.BjDcLotteryQueryRsp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/12 13:49
 */
@Service
public class JcMatchBjdcreslutServiceImpl implements JcMatchBjdcreslutService {
    @Resource
    private JcMatchBjdcreslutMapper jcMatchBjdcreslutMapper;
    @Resource
    private JcMatchBjdcPlMapper jcMatchBjdcPlMapper;
    @Override
    public JcMatchBjdcreslut queryJcMatchBjdcreslutByBet007(int i) {
        return jcMatchBjdcreslutMapper.queryJcMatchBjdcreslutByBet007(i);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJcMatchBjdcreslut(Schedule schedule, JcMatchBjdcreslut jcMatchBjdcreslut, List<JcMatchBjdcPl> jcMatchBjdcPls, BjDcLotteryQueryRsp bjDcLotteryQueryRsp) throws BaseException {
        jcMatchBjdcreslut.setAwardTime(new Date());
        jcMatchBjdcreslut.setIdBet007(Long.valueOf(schedule.getScheduleid()));
        jcMatchBjdcreslut.setUpdateTime(new Date());
        //计算赛果
        jcMatchBjdcreslut.setMatchResult(JcLotteryUtils.gameResult(schedule, jcMatchBjdcPls,bjDcLotteryQueryRsp,"1"));
        //更新赛果表的让球数信息
        for(int i = 0; i < jcMatchBjdcPls.size(); i++){
            JcMatchBjdcPl jcMatchBjdcPl = jcMatchBjdcPls.get(i);
            if("15".equals(jcMatchBjdcPl.getLotteryPlay())){//让球胜平负
                jcMatchBjdcreslut.setConCedNum(jcMatchBjdcPl.getConCedNum());
            }
        }
        Example example = new Example(JcMatchBjdcreslut.class);
        example.createCriteria().andEqualTo("id",jcMatchBjdcreslut.getId());
        int i = jcMatchBjdcreslutMapper.updateByExampleSelective(jcMatchBjdcreslut,example);
        if(i <= 0){
            throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                    ProtocolCodeMsg.UPDATE_FAILE.getMsg());
        }
        //更新赔率表的赛果和赔率
        for(int j = 0 ; j < jcMatchBjdcPls.size(); j++){
            JcMatchBjdcPl jcMatchBjdcPl = jcMatchBjdcPls.get(j);
            jcMatchBjdcPl.setMatchResult(JcLotteryUtils.gameResult(schedule, jcMatchBjdcPls,bjDcLotteryQueryRsp,"2"));
            Example example1 = new Example(JcMatchBjdcPl.class);
            example1.createCriteria().andEqualTo("id",jcMatchBjdcPl.getId());
            int k = jcMatchBjdcPlMapper.updateByExampleSelective(jcMatchBjdcPl,example1);
            if(k <= 0){
                throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                        ProtocolCodeMsg.UPDATE_FAILE.getMsg());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertJcMatchBjdcreslut(Schedule schedule, List<JcMatchBjdcPl> jcMatchBjdcPls, BjDcLotteryQueryRsp bjDcLotteryQueryRsp) throws BaseException {
        JcMatchBjdcreslut jcMatchBjdcreslut = new JcMatchBjdcreslut();
        jcMatchBjdcreslut.setAwardTime(new Date());
        jcMatchBjdcreslut.setIdBet007(Long.valueOf(schedule.getScheduleid()));
        jcMatchBjdcreslut.setUpdateTime(new Date());
        //计算赛果
        jcMatchBjdcreslut.setMatchResult(JcLotteryUtils.gameResult(schedule, jcMatchBjdcPls,bjDcLotteryQueryRsp,"1"));
        jcMatchBjdcreslut.setCreateTime(new Date());
        //更新赛果表的让球数信息
        for(int i = 0; i < jcMatchBjdcPls.size(); i++){
            JcMatchBjdcPl jcMatchBjdcPl = jcMatchBjdcPls.get(i);
            if("15".equals(jcMatchBjdcPl.getLotteryPlay())){//让球胜平负
                jcMatchBjdcreslut.setConCedNum(jcMatchBjdcPl.getConCedNum());
            }
        }
        int i = jcMatchBjdcreslutMapper.insertSelective(jcMatchBjdcreslut);
        if(i <= 0){
            throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                    ProtocolCodeMsg.INSERT_FAILE.getMsg());
        }
        //更新赔率表的赛果和赔率
        for(int j = 0 ; j < jcMatchBjdcPls.size(); j++){
            JcMatchBjdcPl jcMatchBjdcPl = jcMatchBjdcPls.get(j);
            jcMatchBjdcPl.setMatchResult(JcLotteryUtils.gameResult(schedule, jcMatchBjdcPls,bjDcLotteryQueryRsp,"2"));
            Example example1 = new Example(JcMatchBjdcPl.class);
            example1.createCriteria().andEqualTo("id",jcMatchBjdcPl.getId());
            int k = jcMatchBjdcPlMapper.updateByExampleSelective(jcMatchBjdcPl,example1);
            if(k <= 0){
                throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                        ProtocolCodeMsg.UPDATE_FAILE.getMsg());
            }
        }
    }

}
