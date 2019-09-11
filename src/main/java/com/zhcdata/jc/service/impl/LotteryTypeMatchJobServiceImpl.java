package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.JcMatchBjdcMapper;
import com.zhcdata.db.mapper.JcMatchJczqMapper;
import com.zhcdata.db.mapper.JcMatchZcMapper;
import com.zhcdata.db.model.JcMatchBjdc;
import com.zhcdata.db.model.JcMatchJczq;
import com.zhcdata.db.model.JcMatchZc;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.LotteryTypeMatchJobService;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.LotterType.LotteryTypeMatchRsp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @Description @Description 对应接口： 8.彩票赛程与球探网ID关联表
 *              功   能： 区分赛事彩种信息
 * @Author cuishuai
 * @Date 2019/9/10 16:27
 */
@Service
public class LotteryTypeMatchJobServiceImpl implements LotteryTypeMatchJobService {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    @Resource
    private JcMatchZcMapper jcLotterTypeZcMapper;
    @Resource
    private JcMatchJczqMapper jcLotterTypeJcMapper;
    @Resource
    private JcMatchBjdcMapper jcLotterTypeBdMapper;

    @Override
    public JcMatchZc queryJcLotterTypeZcByIDBet007(long idBet007) {
        return jcLotterTypeZcMapper.queryJcLotterTypeZcByIDBet007(idBet007);
    }

    @Override
    public JcMatchJczq queryJcLotterTypeJcByIDBet007(long idBet007) {
        return jcLotterTypeJcMapper.queryJcLotterTypeJcByIDBet007(idBet007);
    }

    @Override
    public JcMatchBjdc queryJcLotterTypeBdByIDBet007(long idBet007) {
        return jcLotterTypeBdMapper.queryJcLotterTypeBdByIDBet007(idBet007);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJcLotterTypeZc(JcMatchZc jcLotterTypeZc, LotteryTypeMatchRsp lotteryTypeMatchRsp) throws ParseException, BaseException {
        jcLotterTypeZc.setAway(lotteryTypeMatchRsp.getAway());
        jcLotterTypeZc.setLotteryName(lotteryTypeMatchRsp.getLotteryName());
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getIssueNum())){
            jcLotterTypeZc.setIssueNum(lotteryTypeMatchRsp.getIssueNum());
        }
        jcLotterTypeZc.setNoId(lotteryTypeMatchRsp.getID());
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getID_bet007())){
            jcLotterTypeZc.setIdBet007(Long.valueOf(lotteryTypeMatchRsp.getID_bet007()));
        }
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getTime())){
            jcLotterTypeZc.setStartTime(sdf.parse(lotteryTypeMatchRsp.getTime()));
        }
        jcLotterTypeZc.setSport(lotteryTypeMatchRsp.getSport());
        jcLotterTypeZc.setHome(lotteryTypeMatchRsp.getHome());
        jcLotterTypeZc.setAway(lotteryTypeMatchRsp.getAway());
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getHomeID())){
            jcLotterTypeZc.setHomeId(Long.valueOf(lotteryTypeMatchRsp.getHomeID()));
        }
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getAwayID())){
            jcLotterTypeZc.setAwayId(Long.valueOf(lotteryTypeMatchRsp.getAwayID()));
        }
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getRecordID())){
            jcLotterTypeZc.setRecordId(Long.valueOf(lotteryTypeMatchRsp.getRecordID()));
        }
        jcLotterTypeZc.setTurn(lotteryTypeMatchRsp.getTurn());
        jcLotterTypeZc.setLeague(lotteryTypeMatchRsp.getLeague());
        Example example = new Example(JcMatchZc.class);
        example.createCriteria().andEqualTo("id",jcLotterTypeZc.getId());
        int i = jcLotterTypeZcMapper.updateByExampleSelective(jcLotterTypeZc,example);
        if(i <= 0){
            throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                    ProtocolCodeMsg.UPDATE_FAILE.getMsg());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertJcLotterTypeZc(LotteryTypeMatchRsp lotteryTypeMatchRsp) throws ParseException, BaseException {
        JcMatchZc jcLotterTypeZc = new JcMatchZc();
        jcLotterTypeZc.setAway(lotteryTypeMatchRsp.getAway());
        jcLotterTypeZc.setLotteryName(lotteryTypeMatchRsp.getLotteryName());
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getIssueNum())){
            jcLotterTypeZc.setIssueNum(lotteryTypeMatchRsp.getIssueNum());
        }
        jcLotterTypeZc.setNoId(lotteryTypeMatchRsp.getID());
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getID_bet007())){
            jcLotterTypeZc.setIdBet007(Long.valueOf(lotteryTypeMatchRsp.getID_bet007()));
        }
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getTime())){
            jcLotterTypeZc.setStartTime(sdf.parse(lotteryTypeMatchRsp.getTime()));
        }
        jcLotterTypeZc.setSport(lotteryTypeMatchRsp.getSport());
        jcLotterTypeZc.setHome(lotteryTypeMatchRsp.getHome());
        jcLotterTypeZc.setAway(lotteryTypeMatchRsp.getAway());
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getHomeID())){
            jcLotterTypeZc.setHomeId(Long.valueOf(lotteryTypeMatchRsp.getHomeID()));
        }
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getAwayID())){
            jcLotterTypeZc.setAwayId(Long.valueOf(lotteryTypeMatchRsp.getAwayID()));
        }
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getRecordID())){
            jcLotterTypeZc.setRecordId(Long.valueOf(lotteryTypeMatchRsp.getRecordID()));
        }
        jcLotterTypeZc.setTurn(lotteryTypeMatchRsp.getTurn());
        jcLotterTypeZc.setLeague(lotteryTypeMatchRsp.getLeague());
        int i = jcLotterTypeZcMapper.insertSelective(jcLotterTypeZc);
        if(i <= 0){
            throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                    ProtocolCodeMsg.INSERT_FAILE.getMsg());
        }
    }

    @Override
    public void updateJcLotterTypeJc(JcMatchJczq jcLotterTypeJc, LotteryTypeMatchRsp lotteryTypeMatchRsp) throws ParseException, BaseException {
        jcLotterTypeJc.setAway(lotteryTypeMatchRsp.getAway());
        jcLotterTypeJc.setLotteryName(lotteryTypeMatchRsp.getLotteryName());
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getIssueNum())){
            jcLotterTypeJc.setIssueNum(lotteryTypeMatchRsp.getIssueNum());
        }
        jcLotterTypeJc.setNoId(lotteryTypeMatchRsp.getID());
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getID_bet007())){
            jcLotterTypeJc.setIdBet007(Long.valueOf(lotteryTypeMatchRsp.getID_bet007()));
        }
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getTime())){
            jcLotterTypeJc.setStartTime(sdf.parse(lotteryTypeMatchRsp.getTime()));
        }
        jcLotterTypeJc.setSport(lotteryTypeMatchRsp.getSport());
        jcLotterTypeJc.setHome(lotteryTypeMatchRsp.getHome());
        jcLotterTypeJc.setAway(lotteryTypeMatchRsp.getAway());
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getHomeID())){
            jcLotterTypeJc.setHomeId(Long.valueOf(lotteryTypeMatchRsp.getHomeID()));
        }
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getAwayID())){
            jcLotterTypeJc.setAwayId(Long.valueOf(lotteryTypeMatchRsp.getAwayID()));
        }
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getRecordID())){
            jcLotterTypeJc.setRecordId(Long.valueOf(lotteryTypeMatchRsp.getRecordID()));
        }
        jcLotterTypeJc.setTurn(lotteryTypeMatchRsp.getTurn());
        jcLotterTypeJc.setLeague(lotteryTypeMatchRsp.getLeague());
        Example example = new Example(JcMatchJczq.class);
        example.createCriteria().andEqualTo("id",jcLotterTypeJc.getId());
        int i = jcLotterTypeJcMapper.updateByExampleSelective(jcLotterTypeJc,example);
        if(i <= 0){
            throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                    ProtocolCodeMsg.INSERT_FAILE.getMsg());
        }
    }

    @Override
    public void insertJcLotterTypeJc(LotteryTypeMatchRsp lotteryTypeMatchRsp) throws ParseException, BaseException {
        JcMatchJczq jcLotterTypeJc = new JcMatchJczq();
        jcLotterTypeJc.setAway(lotteryTypeMatchRsp.getAway());
        jcLotterTypeJc.setLotteryName(lotteryTypeMatchRsp.getLotteryName());
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getIssueNum())){
            jcLotterTypeJc.setIssueNum(lotteryTypeMatchRsp.getIssueNum());
        }
        jcLotterTypeJc.setNoId(lotteryTypeMatchRsp.getID());
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getID_bet007())){
            jcLotterTypeJc.setIdBet007(Long.valueOf(lotteryTypeMatchRsp.getID_bet007()));
        }
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getTime())){
            jcLotterTypeJc.setStartTime(sdf.parse(lotteryTypeMatchRsp.getTime()));
        }
        jcLotterTypeJc.setSport(lotteryTypeMatchRsp.getSport());
        jcLotterTypeJc.setHome(lotteryTypeMatchRsp.getHome());
        jcLotterTypeJc.setAway(lotteryTypeMatchRsp.getAway());
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getHomeID())){
            jcLotterTypeJc.setHomeId(Long.valueOf(lotteryTypeMatchRsp.getHomeID()));
        }
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getAwayID())){
            jcLotterTypeJc.setAwayId(Long.valueOf(lotteryTypeMatchRsp.getAwayID()));
        }
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getRecordID())){
            jcLotterTypeJc.setRecordId(Long.valueOf(lotteryTypeMatchRsp.getRecordID()));
        }
        jcLotterTypeJc.setTurn(lotteryTypeMatchRsp.getTurn());
        jcLotterTypeJc.setLeague(lotteryTypeMatchRsp.getLeague());
        int i = jcLotterTypeJcMapper.insertSelective(jcLotterTypeJc);
        if(i <= 0){
            throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                    ProtocolCodeMsg.INSERT_FAILE.getMsg());
        }
    }

    @Override
    public void updateJcLotterTypeBd(JcMatchBjdc jcLotterTypeBd, LotteryTypeMatchRsp lotteryTypeMatchRsp) throws BaseException, ParseException {
        jcLotterTypeBd.setAway(lotteryTypeMatchRsp.getAway());
        jcLotterTypeBd.setLotteryName(lotteryTypeMatchRsp.getLotteryName());
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getIssueNum())){
            jcLotterTypeBd.setIssueNum(lotteryTypeMatchRsp.getIssueNum());
        }
        jcLotterTypeBd.setNoId(lotteryTypeMatchRsp.getID());
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getID_bet007())){
            jcLotterTypeBd.setIdBet007(Long.valueOf(lotteryTypeMatchRsp.getID_bet007()));
        }
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getTime())){
            jcLotterTypeBd.setStartTime(sdf.parse(lotteryTypeMatchRsp.getTime()));
        }
        jcLotterTypeBd.setSport(lotteryTypeMatchRsp.getSport());
        jcLotterTypeBd.setHome(lotteryTypeMatchRsp.getHome());
        jcLotterTypeBd.setAway(lotteryTypeMatchRsp.getAway());
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getHomeID())){
            jcLotterTypeBd.setHomeId(Long.valueOf(lotteryTypeMatchRsp.getHomeID()));
        }
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getAwayID())){
            jcLotterTypeBd.setAwayId(Long.valueOf(lotteryTypeMatchRsp.getAwayID()));
        }
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getRecordID())){
            jcLotterTypeBd.setRecordId(Long.valueOf(lotteryTypeMatchRsp.getRecordID()));
        }
        jcLotterTypeBd.setTurn(lotteryTypeMatchRsp.getTurn());
        jcLotterTypeBd.setLeague(lotteryTypeMatchRsp.getLeague());
        Example example = new Example(JcMatchBjdc.class);
        example.createCriteria().andEqualTo("id",jcLotterTypeBd.getId());
        int i = jcLotterTypeBdMapper.updateByExampleSelective(jcLotterTypeBd,example);
        if(i <= 0){
            throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                    ProtocolCodeMsg.INSERT_FAILE.getMsg());
        }
    }

    @Override
    public void insertJcLotterTypeBd(LotteryTypeMatchRsp lotteryTypeMatchRsp) throws ParseException, BaseException {
        JcMatchBjdc jcLotterTypeBd = new JcMatchBjdc();
        jcLotterTypeBd.setAway(lotteryTypeMatchRsp.getAway());
        jcLotterTypeBd.setLotteryName(lotteryTypeMatchRsp.getLotteryName());
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getIssueNum())){
            jcLotterTypeBd.setIssueNum(lotteryTypeMatchRsp.getIssueNum());
        }
        jcLotterTypeBd.setNoId(lotteryTypeMatchRsp.getID());
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getID_bet007())){
            jcLotterTypeBd.setIdBet007(Long.valueOf(lotteryTypeMatchRsp.getID_bet007()));
        }
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getTime())){
            jcLotterTypeBd.setStartTime(sdf.parse(lotteryTypeMatchRsp.getTime()));
        }
        jcLotterTypeBd.setSport(lotteryTypeMatchRsp.getSport());
        jcLotterTypeBd.setHome(lotteryTypeMatchRsp.getHome());
        jcLotterTypeBd.setAway(lotteryTypeMatchRsp.getAway());
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getHomeID())){
            jcLotterTypeBd.setHomeId(Long.valueOf(lotteryTypeMatchRsp.getHomeID()));
        }
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getAwayID())){
            jcLotterTypeBd.setAwayId(Long.valueOf(lotteryTypeMatchRsp.getAwayID()));
        }
        if(StringUtils.isNotBlank(lotteryTypeMatchRsp.getRecordID())){
            jcLotterTypeBd.setRecordId(Long.valueOf(lotteryTypeMatchRsp.getRecordID()));
        }
        jcLotterTypeBd.setTurn(lotteryTypeMatchRsp.getTurn());
        jcLotterTypeBd.setLeague(lotteryTypeMatchRsp.getLeague());
        int i = jcLotterTypeBdMapper.insertSelective(jcLotterTypeBd);
        if(i <= 0){
            throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                    ProtocolCodeMsg.INSERT_FAILE.getMsg());
        }
    }

    @Override
    public JcMatchJczq queryJcLotterTypeJcByNoId(String id) {
        return jcLotterTypeJcMapper.queryJcLotterTypeJcByNoId(id);
    }
}
