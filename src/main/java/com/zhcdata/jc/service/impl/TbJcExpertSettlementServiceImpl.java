package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.TbJcExpertSettlementMapper;
import com.zhcdata.db.model.TbJcExpertSettlement;
import com.zhcdata.jc.dto.SettlementDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.TbJcExpertSettlementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/11/15 15:53
 */
@Service
public class TbJcExpertSettlementServiceImpl implements TbJcExpertSettlementService {
    @Resource
    private TbJcExpertSettlementMapper tbJcExpertSettlementMapper;
    @Override
    public List<SettlementDto> queryTbJcExpertSettlement() {
        return tbJcExpertSettlementMapper.queryTbJcExpertSettlement();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertTbJcExpertSettlement(TbJcExpertSettlement tbJcExpertSettlement) throws BaseException {
        int i = tbJcExpertSettlementMapper.insert(tbJcExpertSettlement);
        if(i < 0){
            throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                    ProtocolCodeMsg.INSERT_FAILE.getMsg());
        }
    }
}
