package com.zhcdata.jc.service;

import com.zhcdata.db.model.TbJcExpertSettlement;
import com.zhcdata.jc.dto.SettlementDto;
import com.zhcdata.jc.exception.BaseException;

import java.util.List;

public interface TbJcExpertSettlementService {
    List<SettlementDto> queryTbJcExpertSettlement();

    void insertTbJcExpertSettlement(TbJcExpertSettlement tbJcExpertSettlement) throws BaseException;
}
