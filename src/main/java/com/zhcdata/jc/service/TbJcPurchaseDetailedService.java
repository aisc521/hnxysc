package com.zhcdata.jc.service;

import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.db.model.TbJcPurchaseDetailed;
import com.zhcdata.db.model.TbJcUser;
import com.zhcdata.jc.dto.PurchasedPlanDto;
import com.zhcdata.jc.exception.BaseException;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

public interface TbJcPurchaseDetailedService {
    List<PurchasedPlanDto> queryPurchasedPlanDtoByUserId(long l);

    Map<String, Object> schemePurchase(TbJcPlan tbJcPlan, TbJcUser tbJcUser, Map<String, String> paramMap,PayService payService) throws BaseException;

    List<TbJcPurchaseDetailed> queryOrder();

    int updateByExampleSelective(TbJcPurchaseDetailed tbJcPurchaseDetailed, Example example);

    TbJcPurchaseDetailed queryTbJcPurchaseDetailedByUserAndPlanId(Long userId, Long schemeId);
}
