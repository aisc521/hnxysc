package com.zhcdata.jc.protocol.impl;

import com.google.common.base.Strings;
import com.zhcdata.db.model.TbJcMatch;
import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.db.model.TbJcPurchaseDetailed;
import com.zhcdata.db.model.TbJcUser;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.number.NumberUtil;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 方案购买
 * @Author cuishuai
 * @Date 2019/10/10 11:06
 */
@Service("20200228")
public class SchemePurchaseProtocol implements BaseProtocol {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private TbPlanService tbPlanService;

    @Resource
    private TbJcUserService tbJcUserService;

    @Resource
    private TbJcMatchService tbJcMatchService;
    @Resource
    private PayService payService;
    @Resource
    private TbJcPurchaseDetailedService tbJcPurchaseDetailedService;
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        //查询方案信息
        TbJcPlan tbJcPlan = tbPlanService.queryPlanByPlanId(Long.valueOf(String.valueOf(paramMap.get("schemeId"))));
        if(tbJcPlan == null){
            resultMap.put("resCode", ProtocolCodeMsg.PLAN_IS_NULL.getCode());
            resultMap.put("message", ProtocolCodeMsg.PLAN_IS_NULL.getMsg());
            return resultMap;
        }
        //判断方案如果是已经结束的不能购买
        if("0".equals(tbJcPlan.getStatus())){
            resultMap.put("resCode", ProtocolCodeMsg.PLAN_IS_END.getCode());
            resultMap.put("message", ProtocolCodeMsg.PLAN_IS_END.getMsg());
            return resultMap;
        }
        //判断最近一场比赛的的开始时间如果大于当前时间  此方案不能购买
        TbJcMatch tbJcMatch = tbJcMatchService.queryJcMatchByPlanId(tbJcPlan.getId());
        if(tbJcMatch == null){
            resultMap.put("resCode", ProtocolCodeMsg.MATCH_IS_NULL.getCode());
            resultMap.put("message", ProtocolCodeMsg.MATCH_IS_NULL.getMsg());
            return resultMap;
        }

        String dateOfMatch = tbJcMatch.getDateOfMatch();
        Date date = format.parse(dateOfMatch);
        Date nowDate = new Date();
        if(nowDate.compareTo(date) > 0){//当前时间大于比赛时间
            resultMap.put("resCode", ProtocolCodeMsg.PLAN_IS_END.getCode());
            resultMap.put("message", ProtocolCodeMsg.PLAN_IS_END.getMsg());
            return resultMap;
        }

        //查询用户是否已经购买过此方案
        TbJcPurchaseDetailed tbJcPurchaseDetailed = tbJcPurchaseDetailedService.queryTbJcPurchaseDetailedByUserAndPlanId(Long.valueOf(String.valueOf(paramMap.get("userId"))),Long.valueOf(String.valueOf(paramMap.get("schemeId"))));
        if(tbJcPurchaseDetailed != null){
            resultMap.put("resCode", ProtocolCodeMsg.IS_BUY_PLAN.getCode());
            resultMap.put("message", ProtocolCodeMsg.IS_BUY_PLAN.getMsg());
            return resultMap;
        }

        //判断是否是支付宝支付  判断是否在支付时间范围内
        String payType = paramMap.get("payType");
        Calendar instance = Calendar.getInstance();
        int i = instance.get(Calendar.HOUR_OF_DAY);
        if ("21".equals(payType) && i < 7) {
            resultMap.put("resCode", ProtocolCodeMsg.USERMENUBUY_MENU_MONEY_ERROR.getCode());
            resultMap.put("message", "支付宝支持的交易时间为7:00-24:00");
            return resultMap;
        }

        //判断是否是首次购买
        List<TbJcPurchaseDetailed> list = tbJcPurchaseDetailedService.queryIsFirstBuy(Long.valueOf(String.valueOf(paramMap.get("userId"))));
        if("21".equals(payType) && list.size() <= 0){
            resultMap.put("resCode", ProtocolCodeMsg.MONEY_ERROR.getCode());
            resultMap.put("message", "支付宝支持的交易金额为大于10元");
            return resultMap;
        }
        //判断首单 是都是点播卡支付
        if(list.size() <= 0 && "99".equals(payType)){
            resultMap.put("resCode", ProtocolCodeMsg.FIRST_BUY_ERROR.getCode());
            resultMap.put("message", "首单不支持点播卡支付");
            return resultMap;
        }

        //生成订单信息 并且调用支付
        resultMap = tbJcPurchaseDetailedService.schemePurchase(tbJcPlan,String.valueOf(paramMap.get("userId")),paramMap,payService,list,headBean);
        return resultMap;
    }
}
