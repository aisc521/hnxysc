package com.zhcdata.jc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhcdata.db.mapper.JcMatchBjdcPlMapper;
import com.zhcdata.db.model.JcMatchBjdcPl;
import com.zhcdata.db.model.JcMatchLottery;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.JcMatchBjdcPlService;
import com.zhcdata.jc.tools.JcLotteryUtils;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.BdrealTimeSp.BdrealimeSpRsp;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.BdrealTimeSp.BdrealimeSpSpfRsp;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/12 13:48
 */
@Service
public class JcMatchBjdcPlServiceImpl implements JcMatchBjdcPlService {
    @Resource
    private JcMatchBjdcPlMapper jcMatchBjdcPlMapper;
    @Override
    public List<JcMatchBjdcPl>  queryJcMatchBjdcPlByIssuemAndNoId(String issueNum, String noId) {
        return jcMatchBjdcPlMapper.queryJcMatchBjdcPlByIssuemAndNoId(issueNum,noId);
    }

    @Override
    public void updateJcMatchBjdcPl(List<JcMatchBjdcPl>  jcMatchBjdcPl, JcMatchLottery jcMatchLottery, BdrealimeSpRsp bdrealimeSpRsp) throws BaseException {
        for(int i = 0; i < jcMatchBjdcPl.size(); i++){
            JcMatchBjdcPl BjdcPl1 = jcMatchBjdcPl.get(i);
            //获取玩法信息分个更新
            if("12".equals(BjdcPl1.getLotteryPlay())){//比分
                BjdcPl1.setIssueNum(bdrealimeSpRsp.getIssueNum());
                BjdcPl1.setNoId(bdrealimeSpRsp.getID());
                BjdcPl1.setLotteryName("比分");
                BjdcPl1.setLotteryPlay("12");
                if(jcMatchLottery != null){
                    BjdcPl1.setIdBet007(jcMatchLottery.getIdBet007());
                }
                BjdcPl1.setPlayType(1);//玩法类型 默认单关
                String bfSp = JcLotteryUtils.bJDcPlInfo(bdrealimeSpRsp,"12");
                BjdcPl1.setRateResult(bfSp);//赔率
                BjdcPl1.setStatus(0);
                BjdcPl1.setUpdateTime(new Date());

            }
            if("13".equals(BjdcPl1.getLotteryPlay())){//总进球
                BjdcPl1.setIssueNum(bdrealimeSpRsp.getIssueNum());
                BjdcPl1.setNoId(bdrealimeSpRsp.getID());
                BjdcPl1.setLotteryName("总进球");
                BjdcPl1.setLotteryPlay("13");
                if(jcMatchLottery != null){
                    BjdcPl1.setIdBet007(jcMatchLottery.getIdBet007());
                }
                BjdcPl1.setPlayType(1);//玩法类型 默认单关
                String bfSp = JcLotteryUtils.bJDcPlInfo(bdrealimeSpRsp,"13");
                BjdcPl1.setRateResult(bfSp);//赔率
                BjdcPl1.setStatus(0);
                BjdcPl1.setUpdateTime(new Date());
            }
            if("14".equals(BjdcPl1.getLotteryPlay())){//半全场胜平负
                BjdcPl1.setIssueNum(bdrealimeSpRsp.getIssueNum());
                BjdcPl1.setNoId(bdrealimeSpRsp.getID());
                BjdcPl1.setLotteryName("半全场胜平负");
                BjdcPl1.setLotteryPlay("14");
                if(jcMatchLottery != null){
                    BjdcPl1.setIdBet007(jcMatchLottery.getIdBet007());
                }
                BjdcPl1.setPlayType(1);//玩法类型 默认单关
                String bfSp = JcLotteryUtils.bJDcPlInfo(bdrealimeSpRsp,"14");
                BjdcPl1.setRateResult(bfSp);//赔率
                BjdcPl1.setStatus(0);
                BjdcPl1.setUpdateTime(new Date());
            }
            if("15".equals(BjdcPl1.getLotteryPlay())){//让球胜平负
                BjdcPl1.setIssueNum(bdrealimeSpRsp.getIssueNum());
                BjdcPl1.setNoId(bdrealimeSpRsp.getID());
                BjdcPl1.setLotteryName("让球胜平负");
                BjdcPl1.setLotteryPlay("15");
                if(jcMatchLottery != null){
                    BjdcPl1.setIdBet007(jcMatchLottery.getIdBet007());
                }
                BjdcPl1.setPlayType(1);//玩法类型 默认单关
                String bfSp = JcLotteryUtils.bJDcPlInfo(bdrealimeSpRsp,"15");
                BjdcPl1.setRateResult(bfSp);//赔率
                BjdcPl1.setStatus(0);
                BjdcPl1.setUpdateTime(new Date());
                List<BdrealimeSpSpfRsp> spf = bdrealimeSpRsp.getSpf();
                BdrealimeSpSpfRsp bdrealimeSpSpfRsp = spf.get(0);
                BjdcPl1.setConCedNum(bdrealimeSpSpfRsp.getGoal());
            }

            /**
             * 上下单双
             */
            if("16".equals(BjdcPl1.getLotteryPlay())){
                BjdcPl1.setIssueNum(bdrealimeSpRsp.getIssueNum());
                BjdcPl1.setNoId(bdrealimeSpRsp.getID());
                BjdcPl1.setLotteryName("上下单双");
                BjdcPl1.setLotteryPlay("16");
                if(jcMatchLottery != null){
                    BjdcPl1.setIdBet007(jcMatchLottery.getIdBet007());
                }
                BjdcPl1.setPlayType(1);//玩法类型 默认单关
                String sxdsSp = JcLotteryUtils.bJDcPlInfo(bdrealimeSpRsp,"16");
                BjdcPl1.setRateResult(sxdsSp);//赔率
                BjdcPl1.setStatus(0);
                BjdcPl1.setUpdateTime(new Date());
                BjdcPl1.setCreateTime(new Date());
            }
            Example example = new Example(JcMatchBjdcPl.class);
            example.createCriteria().andEqualTo("id",BjdcPl1.getId());
            int j = jcMatchBjdcPlMapper.updateByExampleSelective(BjdcPl1,example);
            if(j <= 0){
                throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                        ProtocolCodeMsg.UPDATE_FAILE.getMsg());
            }
        }
    }

    @Override
    public void insertJcMatchBjdcPl(JcMatchLottery jcMatchLottery, BdrealimeSpRsp bdrealimeSpRsp) throws BaseException {
            /**
             * 让球胜平负
             */
            JcMatchBjdcPl spfPl = new JcMatchBjdcPl();
            spfPl.setIssueNum(bdrealimeSpRsp.getIssueNum());
            spfPl.setNoId(bdrealimeSpRsp.getID());
            spfPl.setLotteryName("让球胜平负");
            spfPl.setLotteryPlay("15");
                if(jcMatchLottery != null){
                    spfPl.setIdBet007(jcMatchLottery.getIdBet007());
                }
            spfPl.setPlayType(1);//玩法类型 默认单关
            String spfSp = JcLotteryUtils.bJDcPlInfo(bdrealimeSpRsp,"15");
            spfPl.setRateResult(spfSp);//赔率
            spfPl.setStatus(0);
            spfPl.setUpdateTime(new Date());
            spfPl.setCreateTime(new Date());
            List<BdrealimeSpSpfRsp> spf = bdrealimeSpRsp.getSpf();
            BdrealimeSpSpfRsp bdrealimeSpSpfRsp = spf.get(0);
            spfPl.setConCedNum(bdrealimeSpSpfRsp.getGoal());
            int i = jcMatchBjdcPlMapper.insertSelective(spfPl);
            if(i <= 0){
                throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                        ProtocolCodeMsg.INSERT_FAILE.getMsg());
            }
            /**
             * 比分
             */
            JcMatchBjdcPl bfPl = new JcMatchBjdcPl();
            bfPl.setIssueNum(bdrealimeSpRsp.getIssueNum());
            bfPl.setNoId(bdrealimeSpRsp.getID());
            bfPl.setLotteryName("比分");
            bfPl.setLotteryPlay("12");
            if(jcMatchLottery != null){
                bfPl.setIdBet007(jcMatchLottery.getIdBet007());
            }
            bfPl.setPlayType(1);//玩法类型 默认单关
            String bfSp = JcLotteryUtils.bJDcPlInfo(bdrealimeSpRsp,"12");
            bfPl.setRateResult(bfSp);//赔率
            bfPl.setStatus(0);
            bfPl.setUpdateTime(new Date());
            bfPl.setCreateTime(new Date());
            int j = jcMatchBjdcPlMapper.insertSelective(bfPl);
            if(j <= 0){
                throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                        ProtocolCodeMsg.INSERT_FAILE.getMsg());
            }
            /**
             * 进球
             */
            JcMatchBjdcPl jqPl = new JcMatchBjdcPl();
            jqPl.setIssueNum(bdrealimeSpRsp.getIssueNum());
            jqPl.setNoId(bdrealimeSpRsp.getID());
            jqPl.setLotteryName("总进球");
            jqPl.setLotteryPlay("13");
                if(jcMatchLottery != null){
                    jqPl.setIdBet007(jcMatchLottery.getIdBet007());
                }
            jqPl.setPlayType(1);//玩法类型 默认单关
                String jqSp = JcLotteryUtils.bJDcPlInfo(bdrealimeSpRsp,"13");
            jqPl.setRateResult(jqSp);//赔率
            jqPl.setStatus(0);
            jqPl.setUpdateTime(new Date());
            jqPl.setCreateTime(new Date());
            int k = jcMatchBjdcPlMapper.insertSelective(jqPl);
            if(k <= 0){
                throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                        ProtocolCodeMsg.INSERT_FAILE.getMsg());
            }

            /**
             * 半全场
             */
            JcMatchBjdcPl bqcPl = new JcMatchBjdcPl();
            bqcPl.setIssueNum(bdrealimeSpRsp.getIssueNum());
            bqcPl.setNoId(bdrealimeSpRsp.getID());
            bqcPl.setLotteryName("半全场胜平负");
            bqcPl.setLotteryPlay("14");
            if(jcMatchLottery != null){
                bqcPl.setIdBet007(jcMatchLottery.getIdBet007());
            }
            bqcPl.setPlayType(1);//玩法类型 默认单关
            String bqcSp = JcLotteryUtils.bJDcPlInfo(bdrealimeSpRsp,"14");
            bqcPl.setRateResult(bqcSp);//赔率
            bqcPl.setStatus(0);
            bqcPl.setUpdateTime(new Date());
            bqcPl.setCreateTime(new Date());
            int l = jcMatchBjdcPlMapper.insertSelective(bqcPl);
            if(l <= 0){
                throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                        ProtocolCodeMsg.INSERT_FAILE.getMsg());
            }

            /**
             * 上下单双
             */
            JcMatchBjdcPl sxds = new JcMatchBjdcPl();
            sxds.setIssueNum(bdrealimeSpRsp.getIssueNum());
            sxds.setNoId(bdrealimeSpRsp.getID());
            sxds.setLotteryName("上下单双");
            sxds.setLotteryPlay("16");
            if(jcMatchLottery != null){
                sxds.setIdBet007(jcMatchLottery.getIdBet007());
            }
            sxds.setPlayType(1);//玩法类型 默认单关
            String sxdsSp = JcLotteryUtils.bJDcPlInfo(bdrealimeSpRsp,"16");
            sxds.setRateResult(sxdsSp);//赔率
            sxds.setStatus(0);
            sxds.setUpdateTime(new Date());
            sxds.setCreateTime(new Date());
            int m = jcMatchBjdcPlMapper.insertSelective(sxds);
            if(m <= 0){
                throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                        ProtocolCodeMsg.INSERT_FAILE.getMsg());
            }
    }

  @Override
  public PageInfo<Map<String, String>> queryBjdcListReuslt(int pageNo, int pageAmount, String date) {
    PageHelper.startPage(pageNo, pageAmount);
    String startDate = date+" 00:00:01";
    String endvDate = date+" 23:59:59";
    List<Map<String, String>> list = jcMatchBjdcPlMapper.queryBjdcListReuslt(startDate,endvDate);
    return new PageInfo<>(list);
  }

  @Override
  public int queryTodayMatchCount(String date) {
    String startDate = date+" 00:00:01";
    String endvDate = date+" 23:59:59";
    return jcMatchBjdcPlMapper.queryTodayMatchCount(startDate,endvDate);
  }
}
