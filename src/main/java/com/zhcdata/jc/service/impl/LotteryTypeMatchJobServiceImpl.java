package com.zhcdata.jc.service.impl;


import com.zhcdata.db.mapper.JcMatchLotteryMapper;
import com.zhcdata.db.mapper.JcScheduleMapper;
import com.zhcdata.db.mapper.JcSchedulespMapper;
import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.model.JcMatchLottery;
import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.db.model.JcSchedulesp;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.LotteryTypeMatchJobService;
import com.zhcdata.jc.tools.JcLotteryUtils;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.LotterType.LotteryTypeMatchRsp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private JcMatchLotteryMapper jcMatchLotteryMapper;

    @Resource
    private JcScheduleMapper jcScheduleMapper;

    @Resource
    private ScheduleMapper scheduleMapper;

    @Resource
    private JcSchedulespMapper jcSchedulespMapper;

    @Override
    public JcMatchLottery queryJcMatchLotteryByBet007(long Betoo7,String gameType) {
        return jcMatchLotteryMapper.queryJcMatchLotteryByBet007(Betoo7,gameType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJcMatchLottery(JcMatchLottery jcMatchLottery, LotteryTypeMatchRsp lotteryTypeMatchRsp) throws BaseException {
        jcMatchLottery.setUpdateTime(new Date());
        jcMatchLottery.setLotteryName(lotteryTypeMatchRsp.getLotteryName());
        /**
         * 彩种标识
         * 1、14场胜负彩    足彩   JC_ZC
         * 2、六场半全场          JC_6_HAF_ALL
         * 3、四场进球彩          JC_14
         * 4、单场让球胜平负 北单  JC_BD
         * 5、竞彩足球      竞彩  JC_JC
         * 6、竞彩篮球           JC_LQ
         * 7、北京单场胜负过关    JC_BD_SF
         */
        jcMatchLottery.setLottery(JcLotteryUtils.JcLotterZh(lotteryTypeMatchRsp.getLotteryName().trim()));
        jcMatchLottery.setIssueNum(lotteryTypeMatchRsp.getIssueNum());
        jcMatchLottery.setNoId(lotteryTypeMatchRsp.getID());
        jcMatchLottery.setTurn(lotteryTypeMatchRsp.getTurn());
        jcMatchLottery.setRecordId(Long.valueOf(lotteryTypeMatchRsp.getRecordID()));
        jcMatchLottery.setIdBet007(Long.valueOf(lotteryTypeMatchRsp.getID_bet007()));

        /**
         * 2 五大联赛
         * 3 北单竞彩
         * 4 其他
         */
        jcMatchLottery.setType(JcLotteryUtils.matchZh(lotteryTypeMatchRsp.getLeague()));
        Example example = new Example(JcMatchLottery.class);
        example.createCriteria().andEqualTo("id",jcMatchLottery.getId());
        int i = jcMatchLotteryMapper.updateByExampleSelective(jcMatchLottery,example);
        if(i <= 0){
            throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                    ProtocolCodeMsg.UPDATE_FAILE.getMsg());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertJcMatchLottery(LotteryTypeMatchRsp lotteryTypeMatchRsp) throws BaseException {
        JcMatchLottery jcMatchLottery = new JcMatchLottery();
        Date date = new Date();
        jcMatchLottery.setUpdateTime(date);
        jcMatchLottery.setCreateTime(date);
        jcMatchLottery.setLotteryName(lotteryTypeMatchRsp.getLotteryName());
        /**
         * 彩种标识
         * 1、14场胜负彩    足彩   JC_ZC
         * 2、六场半全场          JC_6_HAF_ALL
         * 3、四场进球彩          JC_14
         * 4、单场让球胜平负 北单  JC_BD
         * 5、竞彩足球      竞彩  JC_JC
         * 6、竞彩篮球           JC_LQ
         * 7、北京单场胜负过关    JC_BD_SF
         */
        jcMatchLottery.setLottery(JcLotteryUtils.JcLotterZh(lotteryTypeMatchRsp.getLotteryName().trim()));
        jcMatchLottery.setIssueNum(lotteryTypeMatchRsp.getIssueNum());
        jcMatchLottery.setNoId(lotteryTypeMatchRsp.getID());
        jcMatchLottery.setTurn(lotteryTypeMatchRsp.getTurn());
        jcMatchLottery.setRecordId(Long.valueOf(lotteryTypeMatchRsp.getRecordID()));
        jcMatchLottery.setIdBet007(Long.valueOf(lotteryTypeMatchRsp.getID_bet007()));

        /**
         * 2 五大联赛
         * 3 北单竞彩
         * 4 其他
         */
        jcMatchLottery.setType(JcLotteryUtils.matchZh(lotteryTypeMatchRsp.getLeague()));
        int i = jcMatchLotteryMapper.insertSelective(jcMatchLottery);
        if(i <= 0){
            throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                    ProtocolCodeMsg.INSERT_FAILE.getMsg());
        }
    }

    @Override
    public JcSchedule queryJcScheduleByBet007(long Bet007) {
        return jcScheduleMapper.queryJcScheduleByBet007(Bet007);
    }

    @Override
    public Schedule queryScheduleByBet007(int Bet007) {
        return scheduleMapper.selectByPrimaryKey(Bet007);
    }

    @Override
    public JcSchedulesp queryJcSchedulespByScId(int Bet007) {
        return jcSchedulespMapper.queryJcSchedulespByScId(Bet007);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateJcSchedule(JcSchedule jcSchedule, Schedule schedule, JcSchedulesp jcSchedulesp, LotteryTypeMatchRsp lotteryTypeMatchRsp) throws ParseException, BaseException {
        Example example = new Example(JcMatchLottery.class);
        example.createCriteria().andEqualTo("id",jcSchedule.getId());
        int i = jcScheduleMapper.updateByExampleSelective(generJcSchedule(jcSchedule,schedule,jcSchedulesp,lotteryTypeMatchRsp),example);
        if(i <= 0){
            throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                    ProtocolCodeMsg.UPDATE_FAILE.getMsg());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertJcSchedule(Schedule schedule, JcSchedulesp jcSchedulesp, LotteryTypeMatchRsp lotteryTypeMatchRsp) throws ParseException, BaseException {
        JcSchedule jcSchedule = new JcSchedule();
        int i = jcScheduleMapper.insertSelective(generJcSchedule(jcSchedule,schedule,jcSchedulesp,lotteryTypeMatchRsp));
        if(i <= 0){
            throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                    ProtocolCodeMsg.INSERT_FAILE.getMsg());
        }
    }

    private JcSchedule generJcSchedule(JcSchedule jcSchedule,Schedule schedule, JcSchedulesp jcSchedulesp, LotteryTypeMatchRsp lotteryTypeMatchRsp) throws ParseException {
        jcSchedule.setAddtime(new Date());
        jcSchedule.setScheduleid(Integer.valueOf(lotteryTypeMatchRsp.getID_bet007()));
        jcSchedule.setMatchid(lotteryTypeMatchRsp.getID());
        jcSchedule.setMatchtime(sdf.parse(lotteryTypeMatchRsp.getTime()));
        jcSchedule.setSclass(lotteryTypeMatchRsp.getLeague());
        jcSchedule.setHometeam(lotteryTypeMatchRsp.getHome());
        jcSchedule.setHometeamid(Integer.valueOf(lotteryTypeMatchRsp.getHomeID()));
        jcSchedule.setGuestteam(lotteryTypeMatchRsp.getAway());
        jcSchedule.setGuestteamid(Integer.valueOf(lotteryTypeMatchRsp.getAwayID()));
        jcSchedule.setMatchstate((short) 0);//未开始
        if(schedule != null){
            jcSchedule.setHomescore(schedule.getHomescore());//主队入球 --- 赛程表
            jcSchedule.setGuestscore(schedule.getGuestscore());//客队入球 --- 赛程表
            jcSchedule.setHomehalfscore(schedule.getHomehalfscore());//主队半场入球 --赛程表
            jcSchedule.setGuesthalfscore(schedule.getGuesthalfscore());//客队半场入球 --赛程表

            if(schedule.getMatchstate().equals("-1")){//结束
                jcSchedule.setIsend(true);//是否已结束 --赛程表
            }else{
                jcSchedule.setIsend(false);//是否已结束 --赛程表
            }
            jcSchedule.setMatchstate(schedule.getMatchstate());//比赛状态

        }

        if("true".equals(lotteryTypeMatchRsp.getTurn())){
            jcSchedule.setIsturned(true);
        }else {
            jcSchedule.setIsturned(false);
        }
        /*if(jcSchedulesp != null){
            jcSchedule.setPolygoal();//单场让球 ---竞彩赔率表
            jcSchedule.setSingle101();//是否开售让球胜平负单关--赔率表
            jcSchedule.setSingle102();//是否开售比分单关  --赔率表
            jcSchedule.setSingle103();//是否开售进球数单关
            jcSchedule.setSingle104();//是否开售半全场单关
            jcSchedule.setSingle105();//是否开售胜平负单关
        }*/

        return jcSchedule;
    }

}
