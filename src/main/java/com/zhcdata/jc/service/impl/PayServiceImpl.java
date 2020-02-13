package com.zhcdata.jc.service.impl;

import com.google.common.base.Strings;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.PayService;
import com.zhcdata.jc.tools.CommonUtils;
import com.zhcdata.jc.tools.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.mapper.JsonMapper;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Title: 支付相关service
 * Description: 支付相关service，调用账户中心
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/8/22 16:32
 */
@Slf4j
@Service
public class PayServiceImpl implements PayService {
    @Resource
    private CommonUtils commonUtils;

    @Value("${custom.url.acc}")
    private String accUrl;

    @Override
    public Map<String, Object> wechatPay(String userId, String payMoney, String productName, String description, String payType, String orderId, String src, String ip) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> paramsMap = new HashMap<>(10);
        try {
            paramsMap.put("userId", userId);
            paramsMap.put("orderId", orderId);
            paramsMap.put("oprSys", "O");
            paramsMap.put("tatalAmount", payMoney);
            paramsMap.put("thirdMoney", payMoney);
            paramsMap.put("amount", "0");
            paramsMap.put("productName", description);
            paramsMap.put("description", description);
            if (!Strings.isNullOrEmpty(description)) {
                paramsMap.put("description", productName);
            }
            paramsMap.put("spbillCreateIp", ip);
            String transactionType = "10100116";
            paramsMap.put("tradeType", commonUtils.getPayTradType(Integer.parseInt(payType), "O"));
            if ("20".equals(payType)) {
                paramsMap.put("type", "NATIVE");
            } else if ("22".equals(payType)) {
                transactionType = "10100119";
            }

            String md5 = commonUtils.bodyMd5(paramsMap);
            String returnStrJson = HttpUtils.PayHttpPost(accUrl, paramsMap, transactionType, "UTF-8", src, md5);
            returnMap = handlePayJosn(returnStrJson);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("请求支付系统异常", e);
            returnMap.put("resCode", ProtocolCodeMsg.USER_BUY_FAIL.getCode());
            returnMap.put("message", ProtocolCodeMsg.USER_BUY_FAIL.getMsg());
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> aliPay(String userId, String payMoney, String description, String payType, String orderId, String src, String ip) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> paramsMap = new HashMap<>(10);
        try {
            paramsMap.put("userId", userId);
            paramsMap.put("orderId", orderId);
            paramsMap.put("oprSys", "O");
            paramsMap.put("amount", payMoney);
            paramsMap.put("productName", description);
            paramsMap.put("description", description);
            paramsMap.put("spbillCreateIp", ip);
            paramsMap.put("tradeType", commonUtils.getPayTradType(Integer.parseInt(payType), "O"));
            String md5 = commonUtils.bodyMd5(paramsMap);
            String returnStrJson = HttpUtils.PayHttpPost(accUrl, paramsMap, "10100117", "UTF-8", src, md5);
            returnMap = handlePayJosn(returnStrJson);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("请求支付系统异常", e);
            returnMap.put("resCode", ProtocolCodeMsg.USER_BUY_FAIL.getCode());
            returnMap.put("message", ProtocolCodeMsg.USER_BUY_FAIL.getMsg());
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> moneyPay(String payMoney, String payType, String userId, String orderId, String src, String productName) {
        Map<String, Object> returnMap = new HashMap<>(2);
        Map<String, Object> paramsMap = new HashMap<>(10);
        String oprSys = "O";
        try {
            paramsMap.put("userId", userId);
            paramsMap.put("orderId", orderId);
            paramsMap.put("tradeType", commonUtils.getPayTradType(Integer.parseInt(payType), oprSys));
            paramsMap.put("oprSys", oprSys);
            paramsMap.put("amount", payMoney);
            paramsMap.put("productName", productName);
            String md5 = commonUtils.bodyMd5(paramsMap);
            String returnStrJson = HttpUtils.PayHttpPost(accUrl, paramsMap, "10100110", "UTF-8", src, md5);
            Map<String, Object> bodyMap = handlePayJosn(returnStrJson);
            if (bodyMap != null) {
                returnMap.putAll(bodyMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("请求支付系统异常", e);
            returnMap.put("resCode", ProtocolCodeMsg.USER_BUY_FAIL.getCode());
            returnMap.put("message", ProtocolCodeMsg.USER_BUY_FAIL.getMsg());
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> queryOrderStatus(String payMoney, String payType, String userId, String orderId, String src) {
        try {
            //拼接参数
            Map<String, Object> paramsMap = new HashMap<>(5);
            paramsMap.put("userId", userId);
            paramsMap.put("orderId", orderId);
            paramsMap.put("oprSys", "O");
            paramsMap.put("totalAmount", payMoney);
            paramsMap.put("oprType", "DEPOPAY");
            paramsMap.put("tradeType", commonUtils.getPayTradType(Integer.parseInt(payType), "O"));
            //调用账户中心接口
            String returnStrJson = HttpUtils.PayHttpPost(accUrl, paramsMap, "10100204", "UTF-8", src, "");
            return handlePayJosn(returnStrJson);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("请求支付系统异常", e);
        }
        return null;
    }

    @Override
    public Map<String, Object> discountRecommendUse(String userId, String orderId, String description, String src) {
        Map<String, Object> returnMap = new HashMap<String, Object>(2);
        Map<String, Object> paramsMap = new HashMap<>(6);
        try {
            paramsMap.put("userId", userId);
            paramsMap.put("orderId", orderId);
            paramsMap.put("oprSys", "O");
            //type=2为使用竞彩专家点播
            paramsMap.put("type", "2");
            paramsMap.put("lotteryName", "JCZ");
            paramsMap.put("productName", description);
            String md5 = commonUtils.bodyMd5(paramsMap);
            String returnStrJson = HttpUtils.PayHttpPost(accUrl, paramsMap, "10100114", "UTF-8", src, md5);
            return handlePayJosn(returnStrJson);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("请求支付系统异常", e);
            returnMap.put("resCode", ProtocolCodeMsg.USER_BUY_FAIL.getCode());
            returnMap.put("message", ProtocolCodeMsg.USER_BUY_FAIL.getMsg());
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> refundFrozenToMoney(Long userId, String orderId, BigDecimal amount, String remark, String src) {
        Map<String, Object> bodyMap = new HashMap<>();
        try {
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("userId", userId);
            paramsMap.put("orderId", orderId);
            paramsMap.put("oprSys", "O");
            paramsMap.put("tradeType", "PAY_O_TK_XJ");
            paramsMap.put("amount", amount.toString());
            paramsMap.put("remark", remark);
            String returnStr = HttpUtils.httpPost(accUrl, paramsMap, "10100112", "UTF-8", src, null, "A");
            bodyMap = handlePayJosn(returnStr);
        } catch (BaseException e) {
            log.error("账户中心退冻结款失败，订单号为" + orderId, e);
        } catch (Exception e) {
            log.error("账户中心退冻结款接口", e);
        }
        log.error("账户中心退冻结款成功，订单号为" + orderId);
        return bodyMap;
    }

    @Override
    public Map<String, Object> deductFrozen(Long userId, String orderId, BigDecimal amount, String remark, String src) {
        //调用账户中心接口进行扣款处理
        Map<String, Object> bodyMap = new HashMap<>();
        try {
            //根据支付id调用账户中心接口，判断订单是否支付完成
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("userId", userId);
            paramsMap.put("orderId", orderId);
            paramsMap.put("oprSys", "O");
            paramsMap.put("tradeType", "PAY_O_KDJ_XJ");
            paramsMap.put("amount", amount.toString());
            paramsMap.put("remark", remark);
            String returnStr = HttpUtils.httpPost(accUrl, paramsMap, "10100111", "UTF-8", src, null, "A");
            bodyMap = handlePayJosn(returnStr);
        } catch (BaseException e) {
            log.error("账户中心增加优惠次数更新或插入失败，订单号为" + orderId, e);
        } catch (Exception e) {
            log.error("账户中心增加优惠次数接口", e);
        }
        log.error("账户中心增加优惠次数，订单号为" + orderId);
        return bodyMap;
    }

    @Override
    public Map<String, Object> refundDiscount(Long userId, String orderId, String type, String productName, String src) {
        Map<String, Object> bodyMap = new HashMap<>();
        try {
            //根据支付id调用账户中心接口，判断订单是否支付完成
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("userId", userId);
            paramsMap.put("orderId", orderId);
            paramsMap.put("type", Strings.isNullOrEmpty(type) ? "1" : type);//1.无期限 5.周卡
            paramsMap.put("flag", "5");//标识：0 支出标识 1充值赠送 2 系统赠送 3大礼包 4购买 5 退回
            paramsMap.put("lotteryName", "JCZ");
            paramsMap.put("addTimes", 1);
            paramsMap.put("validityTime", "0");
            paramsMap.put("productName", productName);
            paramsMap.put("buyType", "2");
            String md5 = commonUtils.bodyMd5(paramsMap);
            String returnStr = HttpUtils.httpPost(accUrl, paramsMap, "10100115", "UTF-8", src, md5, "A");
            bodyMap = handlePayJosn(returnStr);
        } catch (BaseException e) {
            log.error("账户中心增加优惠次数更新或插入失败，订单号为" + orderId, e);
        } catch (Exception e) {
            log.error("账户中心增加优惠次数接口", e);
        }
        log.error("账户中心增加优惠次数，订单号为" + orderId);
        return bodyMap;
    }

    @Override
    public Map<String, Object> currencyCouponPay(String userId,String couponId, String orderId, String description, String src) {
        Map<String, Object> returnMap = new HashMap<String, Object>(2);
        Map<String, Object> paramsMap = new HashMap<>(6);
        try {
            paramsMap.put("userId",userId);                   //登录用户id
            paramsMap.put("oprSys", "O");                     //暂时传o
            paramsMap.put("couponId", couponId);              //优惠券ID
            paramsMap.put("orderId",orderId);                 //子系统订单号
            paramsMap.put("oprStatus","1");                   //使用
            String returnJson = HttpUtils.PayHttpPost(accUrl, paramsMap, "10100405", "UTF-8", src, commonUtils.bodyMd5(paramsMap));
            returnMap = handlePayJosn(returnJson);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("请求支付系统异常", e);
            returnMap.put("resCode", ProtocolCodeMsg.USER_BUY_FAIL.getCode());
            returnMap.put("message", ProtocolCodeMsg.USER_BUY_FAIL.getMsg());
        }
        return returnMap;
    }

    /**
     * 解析有关支付系统返回的字符串
     * 如果成功返回boyMap
     */
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
