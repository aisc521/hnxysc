package com.zhcdata.jc.service;

import com.github.pagehelper.PageInfo;
import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.db.model.TbJcPurchaseDetailed;
import com.zhcdata.db.model.TbJcUser;
import com.zhcdata.jc.dto.PurchasedPlanDto;
import com.zhcdata.jc.exception.BaseException;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

public interface TbJcPurchaseDetailedService {
    PageInfo<PurchasedPlanDto> queryPurchasedPlanDtoByUserId(int pageNo, int pageAmount, long l);

    Map<String, Object> schemePurchase(TbJcPlan tbJcPlan, String userId, Map<String, String> paramMap,PayService payService,List<TbJcPurchaseDetailed> list) throws BaseException;

    List<TbJcPurchaseDetailed> queryOrder();

    int updateByExampleSelective(TbJcPurchaseDetailed tbJcPurchaseDetailed, Example example);

    TbJcPurchaseDetailed queryTbJcPurchaseDetailedByUserAndPlanId(Long userId, Long schemeId);

    TbJcPurchaseDetailed queryOrderByUserAndOrderId(Long userId, String orderId);

    List<TbJcPurchaseDetailed> queryIsFirstBuy(Long userId);

    List<TbJcPurchaseDetailed> queryTbJcPurchaseDetailedByPlanId(Long id);
}
