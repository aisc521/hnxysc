package com.zhcdata.db.mapper;

import com.zhcdata.db.model.TbJcPurchaseDetailed;
import com.zhcdata.jc.dto.PurchasedPlanDto;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TbJcPurchaseDetailedMapper extends Mapper<TbJcPurchaseDetailed> {
    List<PurchasedPlanDto> queryPurchasedPlanDtoByUserId(@Param("userId") long userId);

    TbJcPurchaseDetailed queryPurchasedPlanByOrderId(@Param("orderId")String orderId);

    List<TbJcPurchaseDetailed> queryOrder();
}