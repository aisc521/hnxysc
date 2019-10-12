package com.zhcdata.jc.protocol.impl;

import com.google.common.base.Strings;
import com.zhcdata.db.model.TbJcPurchaseDetailed;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.TbJcPurchaseDetailedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.number.NumberUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 方案订单查询
 * @Author cuishuai
 * @Date 2019/10/11 17:24
 */
@Service("20200229")
public class QueryOrderProtocol  implements BaseProtocol {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private TbJcPurchaseDetailedService tbJcPurchaseDetailedService;

    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        Map<String, Object> map = new HashMap<>();
        String userId = paramMap.get("userId");
        if (Strings.isNullOrEmpty(userId) || !NumberUtil.isNumber(userId)) {
            LOGGER.info("[" + ProtocolCodeMsg.USER_ID_NOT_EXIST.getMsg() + "]:userId---" + userId);
            map.put("resCode", ProtocolCodeMsg.USER_ID_NOT_EXIST.getCode());
            map.put("message", ProtocolCodeMsg.USER_ID_NOT_EXIST.getMsg());
            return map;
        }

        String orderId = paramMap.get("orderId");
        if (Strings.isNullOrEmpty(orderId)) {
            LOGGER.info("[" + ProtocolCodeMsg.ORDER_ID_NOT_EXIST.getMsg() + "]:orderId---" + orderId);
            map.put("resCode", ProtocolCodeMsg.ORDER_ID_NOT_EXIST.getCode());
            map.put("message", ProtocolCodeMsg.ORDER_ID_NOT_EXIST.getMsg());
            return map;
        }
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        TbJcPurchaseDetailed tbJcPurchaseDetailed = tbJcPurchaseDetailedService.queryOrderByUserAndOrderId(Long.valueOf(String.valueOf(paramMap.get("userId"))),String.valueOf(paramMap.get("orderId")));
        if(tbJcPurchaseDetailed == null){
            resultMap.put("resCode", ProtocolCodeMsg.ORDER_IS_NULL.getCode());
            resultMap.put("message", ProtocolCodeMsg.ORDER_IS_NULL.getMsg());
            return resultMap;
        }
        if(0 == tbJcPurchaseDetailed.getPayStatus()){
            resultMap.put("status", "0");
        }
        if(2 == tbJcPurchaseDetailed.getPayStatus()){
            resultMap.put("status", "2");
        }
        if(8 == tbJcPurchaseDetailed.getPayStatus()){
            resultMap.put("status", "8");
        }
        if(1 == tbJcPurchaseDetailed.getPayStatus()){
            resultMap.put("status", "2");
        }
        resultMap.put("orderId", tbJcPurchaseDetailed.getOrderId());
        resultMap.put("thirdAmount", tbJcPurchaseDetailed.getThirdMoney());
        return resultMap;
    }
}
