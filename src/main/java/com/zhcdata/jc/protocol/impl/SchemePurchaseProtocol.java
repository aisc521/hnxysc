package com.zhcdata.jc.protocol.impl;

import com.google.common.base.Strings;
import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.db.model.TbJcPurchaseDetailed;
import com.zhcdata.db.model.TbJcUser;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.PayService;
import com.zhcdata.jc.service.TbJcPurchaseDetailedService;
import com.zhcdata.jc.service.TbJcUserService;
import com.zhcdata.jc.service.TbPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.number.NumberUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 方案购买
 * @Author cuishuai
 * @Date 2019/10/10 11:06
 */
@Service("")
public class SchemePurchaseProtocol implements BaseProtocol {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private TbPlanService tbPlanService;

    @Resource
    private TbJcUserService tbJcUserService;

    @Resource
    private PayService payService;
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

        String planId = paramMap.get("schemeId");
        if (Strings.isNullOrEmpty(planId) || !NumberUtil.isNumber(planId)) {
            LOGGER.info("[" + ProtocolCodeMsg.PLANID_NULL.getMsg() + "]:schemeId---" + planId);
            map.put("resCode", ProtocolCodeMsg.PLANID_NULL.getCode());
            map.put("message", ProtocolCodeMsg.PLANID_NULL.getMsg());
            return map;
        }
        String payType = paramMap.get("payType");
        if (Strings.isNullOrEmpty(payType) || !NumberUtil.isNumber(payType)) {
            LOGGER.info("[" + ProtocolCodeMsg.PAY_TYPE.getMsg() + "]:payType---" + payType);
            map.put("resCode", ProtocolCodeMsg.PAY_TYPE.getCode());
            map.put("message", ProtocolCodeMsg.PAY_TYPE.getMsg());
            return map;
        }
        String buyMoney = paramMap.get("buyMoney");
        if (Strings.isNullOrEmpty(buyMoney) || !NumberUtil.isNumber(buyMoney)) {
            LOGGER.info("[" + ProtocolCodeMsg.MONEY_IS_NULL.getMsg() + "]:buyMoney---" + buyMoney);
            map.put("resCode", ProtocolCodeMsg.MONEY_IS_NULL.getCode());
            map.put("message", ProtocolCodeMsg.MONEY_IS_NULL.getMsg());
            return map;
        }
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        //查询方案信息
        TbJcPlan tbJcPlan = tbPlanService.queryPlanByPlanId(Long.valueOf(paramMap.get("schemeId")));
        if(tbJcPlan == null){
            resultMap.put("resCode", ProtocolCodeMsg.PLAN_IS_NULL.getCode());
            resultMap.put("message", ProtocolCodeMsg.PLAN_IS_NULL.getMsg());
            return resultMap;
        }
        if("0".equals(tbJcPlan.getStatus())){
            resultMap.put("resCode", ProtocolCodeMsg.PLAN_IS_END.getCode());
            resultMap.put("message", ProtocolCodeMsg.PLAN_IS_END.getMsg());
            return resultMap;
        }
        //查询用户信息
        TbJcUser tbJcUser = tbJcUserService.queryTbJcUserById(Long.valueOf(paramMap.get("userId")));
        if(tbJcUser == null){
            resultMap.put("resCode", ProtocolCodeMsg.USER_IS_NULL.getCode());
            resultMap.put("message", ProtocolCodeMsg.USER_IS_NULL.getMsg());
            return resultMap;
        }
        //生成订单信息 并且调用支付
        resultMap = tbJcPurchaseDetailedService.schemePurchase(tbJcPlan,tbJcUser,paramMap,payService);
        return resultMap;
    }
}
