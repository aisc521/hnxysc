package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.TbJcPurchaseDetailedMapper;
import com.zhcdata.jc.dto.PurchasedPlanDto;
import com.zhcdata.jc.service.TbJcPurchaseDetailedService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/20 16:51
 */
@Service
public class TbJcPurchaseDetailedServiceImpl implements TbJcPurchaseDetailedService {
    @Resource
    private TbJcPurchaseDetailedMapper tbJcPurchaseDetailedMapper;
    @Override
    public List<PurchasedPlanDto> queryPurchasedPlanDtoByUserId(long userId) {
        return tbJcPurchaseDetailedMapper.queryPurchasedPlanDtoByUserId(userId);
    }
}
