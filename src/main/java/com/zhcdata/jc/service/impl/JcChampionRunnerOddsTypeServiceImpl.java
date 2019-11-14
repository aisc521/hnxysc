package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.JcChampionRunnerOddsTypeMapper;
import com.zhcdata.db.model.JcChampionRunnerOddsType;
import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.JcChampionRunnerOddsTypeService;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.JcChampionRunnerOddsRsp.JcChampionRunnerOddsRsp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/11/13 16:58
 */
@Service
public class JcChampionRunnerOddsTypeServiceImpl implements JcChampionRunnerOddsTypeService {
    @Resource
    private JcChampionRunnerOddsTypeMapper jcChampionRunnerOddsTypeMapper;
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

        //jcChampionRunnerOddsType.setPlayCode();
        jcChampionRunnerOddsType.setPlayTypeName(typeRsp);
        //jcChampionRunnerOddsType.setStartTime();
        //jcChampionRunnerOddsType.setEndTime();
        //jcChampionRunnerOddsType.setStatus();
        jcChampionRunnerOddsType.setUpdateTime(new Date());
        Example example1 = new Example(JcChampionRunnerOddsType.class);
        example1.createCriteria().andEqualTo("id",jcChampionRunnerOddsType.getId());
        int j = jcChampionRunnerOddsTypeMapper.updateByExampleSelective(jcChampionRunnerOddsType,example1);
        if(j < 0){
            throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                    ProtocolCodeMsg.UPDATE_FAILE.getMsg());
        }
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

        //jcChampionRunnerOddsType.setPlayCode();
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
        return i;
    }
}
