package com.zhcdata.jc.service;

import com.github.pagehelper.PageInfo;
import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.db.model.TbJcPurchaseDetailed;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.dto.PurchasedPlanDto;
import com.zhcdata.jc.exception.BaseException;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

public interface TbJcPurchaseDetailedService {
    PageInfo<PurchasedPlanDto> queryPurchasedPlanDtoByUserId(int pageNo, int pageAmount, long l);

    Map<String, Object> schemePurchase(TbJcPlan tbJcPlan, String userId, Map<String, String> paramMap,PayService payService,Integer list,ProtocolParamDto.HeadBean headBean,String cell) throws BaseException;

    List<TbJcPurchaseDetailed> queryOrder();

    int updateByExampleSelective(TbJcPurchaseDetailed tbJcPurchaseDetailed, Example example);

    TbJcPurchaseDetailed queryTbJcPurchaseDetailedByUserAndPlanId(Long userId, Long schemeId);

    TbJcPurchaseDetailed queryOrderByUserAndOrderId(Long userId, String orderId);

    Integer queryIsFirstBuy(Long userId);

    List<TbJcPurchaseDetailed> queryTbJcPurchaseDetailedByPlanId(Long id);

    Integer queryIfHaveSuccessOeder(Long userId);

    void updateTbJcPurchaseDetailed(TbJcPurchaseDetailed tbJcPurchaseDetailed,TbJcPurchaseDetailedService tbJcPurchaseDetailedService,TbPlanService tbPlanService) throws BaseException;

    void refundFrozenToMoney(TbJcPlan tbJcPlan,TbJcPurchaseDetailedService tbJcPurchaseDetailedService,PayService payService) throws BaseException;

    String refundFrozenToMoney(String type,TbJcPurchaseDetailed tbJcPurchaseDetailed,PayService payService) throws BaseException;

    /**
     * 增加方案人气
     * @param tbJcPlan
     * @throws BaseException
     */
    void addPlanPopularity(TbJcPlan tbJcPlan) throws BaseException;
}
