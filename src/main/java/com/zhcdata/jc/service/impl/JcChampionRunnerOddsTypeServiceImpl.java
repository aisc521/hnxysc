package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.JcChampionRunnerOddsMapper;
import com.zhcdata.db.mapper.JcChampionRunnerOddsTypeMapper;
import com.zhcdata.db.model.JcChampionRunnerOdds;
import com.zhcdata.db.model.JcChampionRunnerOddsType;
import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.JcChampionRunnerOddsTypeService;
import com.zhcdata.jc.tools.CommonUtils;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.JcChampionRunnerOddsRsp.JcChampionRunnerOddsRsp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/11/13 16:58
 */
@Service
public class JcChampionRunnerOddsTypeServiceImpl implements JcChampionRunnerOddsTypeService {
    @Resource
    private JcChampionRunnerOddsTypeMapper jcChampionRunnerOddsTypeMapper;
    @Resource
    private JcChampionRunnerOddsMapper jcChampionRunnerOddsMapper;

    @Resource
    private CommonUtils commonUtils;

    @Override
    public JcChampionRunnerOddsType queryJcChampionRunnerOddsTypeByPlayTypeNameAndGameType(String typeRsp, String type) {
        return jcChampionRunnerOddsTypeMapper.queryJcChampionRunnerOddsTypeByPlayTypeNameAndGameType(typeRsp,type);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updataJcChampionRunnerOddsType(JcChampionRunnerOddsType jcChampionRunnerOddsType, JcChampionRunnerOddsRsp jcChampionRunnerOddsRsp) throws BaseException {
        String typeRsp = jcChampionRunnerOddsRsp.getType();
        if(typeRsp.indexOf("冠亚军") != -1){//包含冠亚军  是 冠亚军玩法
            jcChampionRunnerOddsType.setGameType(Long.valueOf(2));
        }else{//冠军玩法
            jcChampionRunnerOddsType.setGameType(Long.valueOf(1));
        }
        //查询 是否有此类型的玩法
        List<JcChampionRunnerOddsType> countGame = jcChampionRunnerOddsTypeMapper.queryJcChampionRunnerOddsTypeByGameType(typeRsp);
        if(countGame.size() > 0){
             jcChampionRunnerOddsType.setPlayCode(countGame.get(0).getPlayCode());//2019亚洲杯冠军  对应的编码
        }else{
            jcChampionRunnerOddsType.setPlayCode(commonUtils.createOrderId("CHRU"));
        }
        jcChampionRunnerOddsType.setPlayTypeName(typeRsp);//2019亚洲杯冠军
        //jcChampionRunnerOddsType.setStartTime();//开始时间
        //jcChampionRunnerOddsType.setEndTime();//结束时间
        //jcChampionRunnerOddsType.setStatus();//状态  是否过期
        jcChampionRunnerOddsType.setUpdateTime(new Date());
        Example example1 = new Example(JcChampionRunnerOddsType.class);
        example1.createCriteria().andEqualTo("id",jcChampionRunnerOddsType.getId());
        int j = jcChampionRunnerOddsTypeMapper.updateByExampleSelective(jcChampionRunnerOddsType,example1);
        if(j < 0) {
            throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                    ProtocolCodeMsg.UPDATE_FAILE.getMsg());
        }

       //详情表
        insertJcChampionRunnerOdds(jcChampionRunnerOddsType,jcChampionRunnerOddsRsp);
        return j;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertJcChampionRunnerOddsType(JcChampionRunnerOddsRsp jcChampionRunnerOddsRsp) throws BaseException {
        JcChampionRunnerOddsType jcChampionRunnerOddsType = new JcChampionRunnerOddsType();
        String typeRsp = jcChampionRunnerOddsRsp.getType();
        if(typeRsp.indexOf("冠亚军") != -1){//包含冠亚军  是 冠亚军玩法
            jcChampionRunnerOddsType.setGameType(Long.valueOf(2));
        }else{//冠军玩法
            jcChampionRunnerOddsType.setGameType(Long.valueOf(1));
        }

        //查询 是否有此类型的玩法
        List<JcChampionRunnerOddsType> countGame = jcChampionRunnerOddsTypeMapper.queryJcChampionRunnerOddsTypeByGameType(typeRsp);
        if(countGame.size() > 0){
            jcChampionRunnerOddsType.setPlayCode(countGame.get(0).getPlayCode());//2019亚洲杯冠军  对应的编码
        }else{
            jcChampionRunnerOddsType.setPlayCode(commonUtils.createOrderId("CHRU"));
        }
        jcChampionRunnerOddsType.setPlayTypeName(typeRsp);
        //jcChampionRunnerOddsType.setStartTime();
        //jcChampionRunnerOddsType.setEndTime();
        //jcChampionRunnerOddsType.setStatus();
        jcChampionRunnerOddsType.setCreateTime(new Date());
        int i = jcChampionRunnerOddsTypeMapper.insert(jcChampionRunnerOddsType);
        if(i < 0){
            throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                    ProtocolCodeMsg.INSERT_FAILE.getMsg());
        }
        insertJcChampionRunnerOdds(jcChampionRunnerOddsType,jcChampionRunnerOddsRsp);
        return i;
    }


    public void insertJcChampionRunnerOdds(JcChampionRunnerOddsType jcChampionRunnerOddsType, JcChampionRunnerOddsRsp jcChampionRunnerOddsRsp) throws BaseException {
        //详情信息查询是否存在
        JcChampionRunnerOdds jcChampionRunnerOdds = jcChampionRunnerOddsMapper.queryJcChampionRunnerOddsByPlayCodeAndMatchIdAndTeams(jcChampionRunnerOddsType.getPlayCode(),jcChampionRunnerOddsRsp.getMatchID(),jcChampionRunnerOddsRsp.getTeams());
        jcChampionRunnerOdds.setOddsType(jcChampionRunnerOddsType.getId());
        jcChampionRunnerOdds.setGameType(jcChampionRunnerOddsType.getGameType());
        jcChampionRunnerOdds.setPlayCode(jcChampionRunnerOddsType.getPlayCode());
        jcChampionRunnerOdds.setMatchId(jcChampionRunnerOddsRsp.getMatchID());
        jcChampionRunnerOdds.setOdds(jcChampionRunnerOddsRsp.getOdds());
        if("True".equals(jcChampionRunnerOddsRsp.getIsEnd())){
            jcChampionRunnerOdds.setIsEnd(Long.valueOf(1));
        }else{
            jcChampionRunnerOdds.setIsEnd(Long.valueOf(0));
        }
        //jcChampionRunnerOdds.setStatus();
        if(jcChampionRunnerOdds != null){//更新
            Example example2 = new Example(JcChampionRunnerOdds.class);
            example2.createCriteria().andEqualTo("id",jcChampionRunnerOdds.getId());
            jcChampionRunnerOdds.setUpdateTime(new Date());
            int m = jcChampionRunnerOddsMapper.updateByExampleSelective(jcChampionRunnerOdds,example2);
            if(m < 0) {
                throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                        ProtocolCodeMsg.UPDATE_FAILE.getMsg());
            }
        }else{//新增
            jcChampionRunnerOdds.setUpdateTime(new Date());
            jcChampionRunnerOdds.setCreateTime(new Date());
            int h = jcChampionRunnerOddsMapper.insert(jcChampionRunnerOdds);
            if(h < 0){
                throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                        ProtocolCodeMsg.INSERT_FAILE.getMsg());
            }
        }

    }
}
