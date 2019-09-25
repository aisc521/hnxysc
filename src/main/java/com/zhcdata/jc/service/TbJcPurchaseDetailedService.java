package com.zhcdata.jc.service;

import com.zhcdata.jc.dto.PurchasedPlanDto;

import java.util.List;

public interface TbJcPurchaseDetailedService {
    List<PurchasedPlanDto> queryPurchasedPlanDtoByUserId(long l);
}
