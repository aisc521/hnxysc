package com.zhcdata.db.mapper;

import com.zhcdata.db.model.TbJcExpertSettlement;
import com.zhcdata.jc.dto.SettlementDto;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TbJcExpertSettlementMapper extends Mapper<TbJcExpertSettlement> {
    List<SettlementDto> queryTbJcExpertSettlement();
}