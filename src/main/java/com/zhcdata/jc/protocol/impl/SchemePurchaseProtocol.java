package com.zhcdata.jc.protocol.impl;

import com.google.common.base.Strings;
import com.zhcdata.db.model.TbJcMatch;
import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.db.model.TbJcPurchaseDetailed;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.*;
import com.zhcdata.jc.tools.CommonUtils;
import com.zhcdata.jc.tools.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.mapper.JsonMapper;
import org.springside.modules.utils.number.NumberUtil;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @Value("${custom.url.acc}")
    String accUrl;

    @Resource
    private CommonUtils commonUtils;

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

        String flag = paramMap.get("flag");
        if (Strings.isNullOrEmpty(flag) || !NumberUtil.isNumber(flag)) {
            LOGGER.info("[" + ProtocolCodeMsg.PAY_FLAG.getMsg() + "]:flag---" + payType);
            map.put("resCode", ProtocolCodeMsg.PAY_FLAG.getCode());
            map.put("message", ProtocolCodeMsg.PAY_FLAG.getMsg());
            return map;
        }
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();

        //判断是否包含优惠券购买
        String couponId=paramMap.get("couponId");
        if(!Strings.isNullOrEmpty(couponId)) {
            paramMap.put(couponId,couponId);
            try {
                //验证优惠券有效性
                Map<String, Object> paramsMap_acc = new HashMap<>(10);
                paramsMap_acc.put("userId", paramMap.get("userId")); //登录用户id
                paramsMap_acc.put("oprSys", "O");                    //暂时传o
                paramsMap_acc.put("couponId", couponId);             //优惠券ID
                String transactionType = "10100407";                 //协议号
                String md5 = commonUtils.bodyMd5(paramsMap_acc);     //加密参数
                String returnJson = HttpUtils.PayHttpPost(accUrl, paramsMap_acc, transactionType, "UTF-8", headBean.getSrc(), md5);

                //验证优惠券状态
                //验证优惠券有效期
                //判断优惠券类型(打折券、代金券、通用券)

                Map<String, Object> returnMap_acc = new HashMap<>(2);
                returnMap_acc = handlePayJosn(returnJson);
                paramMap.put("type",returnMap_acc.get("type").toString());          //优惠券类型 0通用 1代金 2折扣
                paramMap.put("access",returnMap_acc.get("accesss").toString());     //0付费 1免费
                paramMap.put("couponPrice",returnMap_acc.get("price").toString());  //价格

                String status=returnMap_acc.get("status").toString();               //获取状态
                if(status.equals("-1")){
                    //是否验证，有效日期(待定)

                    //未使用，锁定 优惠券可以使用，锁定优惠券
//                    Map<String, Object> paramsMap1_acc = new HashMap<>(10);
//                    paramsMap1_acc.put("userId", paramMap.get("userId"));  //登录用户id
//                    paramsMap1_acc.put("oprSys", "O");                     //暂时传o
//                    paramsMap1_acc.put("couponId", couponId);              //优惠券ID
//                    paramsMap1_acc.put("orderId","");                      //子系统订单号
//                    paramsMap1_acc.put("oprStatus","2");                   //锁定
//                    String returnJson1 = HttpUtils.PayHttpPost(accUrl, paramsMap1_acc, "10100405", "UTF-8", headBean.getSrc(), commonUtils.bodyMd5(paramsMap1_acc));
//                    Map<String, Object> returnMap1_acc = new HashMap<>(2);
//                    returnMap1_acc = handlePayJosn(returnJson1);
//                    if(1==2){
//                        //锁定失败
//                        resultMap.put("resCode", ProtocolCodeMsg.COUPON_LOCKING_FAIL.getCode());
//                        resultMap.put("message", ProtocolCodeMsg.COUPON_LOCKING_FAIL.getMsg());
//                        return resultMap;
//                    }
                }else if(status.equals("1")){
                    //已使用
                    resultMap.put("resCode", ProtocolCodeMsg.COUPON_ALREADY_USED.getCode());
                    resultMap.put("message", ProtocolCodeMsg.COUPON_ALREADY_USED.getMsg());
                    return resultMap;
                }else if(status.equals("2")){
                    //已锁定，继续下一步
                }else if(status.equals("9")){
                    //已过期
                    resultMap.put("resCode", ProtocolCodeMsg.COUPON_OVERDUE.getCode());
                    resultMap.put("message", ProtocolCodeMsg.COUPON_OVERDUE.getMsg());
                    return resultMap;
                }
            }catch (Exception ex){
                ex.printStackTrace();
                resultMap.put("resCode", ProtocolCodeMsg.USER_BUY_FAIL.getCode());
                resultMap.put("message", ProtocolCodeMsg.USER_BUY_FAIL.getMsg());
            }
        }

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
       /* if("21".equals(payType)){
            resultMap.put("resCode", ProtocolCodeMsg.ZFB_ERROR.getCode());
            resultMap.put("message", ProtocolCodeMsg.ZFB_ERROR.getMsg());
            return resultMap;
        }*/
        Calendar instance = Calendar.getInstance();
        int i = instance.get(Calendar.HOUR_OF_DAY);
        if ("21".equals(payType) && i < 7) {
            resultMap.put("resCode", ProtocolCodeMsg.USERMENUBUY_MENU_MONEY_ERROR.getCode());
            resultMap.put("message", "支付宝支持的交易时间为7:00-24:00");
            return resultMap;
        }

        //判断是否是首次购买
        Integer list = tbJcPurchaseDetailedService.queryIsFirstBuy(Long.valueOf(String.valueOf(paramMap.get("userId"))));

        String flag = paramMap.get("flag");
        if(list==0&&!"1".equals(flag)){
            resultMap.put("resCode", ProtocolCodeMsg.PAY_FLAG_EXIT.getCode());
            resultMap.put("message", ProtocolCodeMsg.PAY_FLAG_EXIT.getMsg());
            return resultMap;
        }
        if("21".equals(payType) && list <= 0){
            resultMap.put("resCode", ProtocolCodeMsg.MONEY_ERROR.getCode());
            resultMap.put("message", "支付宝支持的交易金额为大于10元");
            return resultMap;
        }
        //判断首单 是都是点播卡支付
        if(list <= 0 && "99".equals(payType)){
            resultMap.put("resCode", ProtocolCodeMsg.FIRST_BUY_ERROR.getCode());
            resultMap.put("message", "首单不支持点播卡支付");
            return resultMap;
        }

        //判断优惠券是否符合限额
        if(paramMap.get("type").equals("1")) {
            if (tbJcPlan.getPrice() < Long.valueOf(paramMap.get("useNumber"))) {
                resultMap.put("resCode", ProtocolCodeMsg.COUPON_NO_USE.getCode());
                resultMap.put("message", ProtocolCodeMsg.COUPON_NO_USE.getMsg());
                return resultMap;
            }
        }


        //生成订单信息 并且调用支付
        resultMap = tbJcPurchaseDetailedService.schemePurchase(tbJcPlan,paramMap.get("userId"),paramMap,payService,list,headBean,paramMap.get("cell"));
        return resultMap;
    }

    private Map<String, Object> handlePayJosn(String payJson) {

        ProtocolParamDto jsonMap = JsonMapper.defaultMapper().fromJson(payJson, ProtocolParamDto.class);
        if (jsonMap != null && jsonMap.getMessage() != null) {
            Map<String, Object> bodyMap = jsonMap.getMessage().getBody();
            if (bodyMap != null) {
                return bodyMap;
            }
        }
        return null;
    }
}
