package com.zhcdata.jc.service.impl;

import com.google.common.base.Strings;

import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.service.PayService;
import com.zhcdata.jc.tools.CommonUtils;
import com.zhcdata.jc.tools.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.mapper.JsonMapper;

import javax.annotation.Resource;
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
@Service
public class PayServiceImpl implements PayService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PayServiceImpl.class);
    @Resource
    private CommonUtils commonUtils;

    @Value("${custom.url.acc}")
    private String accUrl;

    @Override
    public Map<String, Object> wechatPay(String userId, String payMoney, String productName,String description, String payType, String orderId, String src, String ip) {
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
            paramsMap.put("tradeType", commonUtils.getPayTradType(Integer.parseInt(payType),"O"));
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
            LOGGER.error("请求支付系统异常", e);
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
            paramsMap.put("productName", "商品购买");
            paramsMap.put("description", description);
            paramsMap.put("spbillCreateIp",ip);
            paramsMap.put("tradeType", commonUtils.getPayTradType(Integer.parseInt(payType), "O"));
            String md5 = commonUtils.bodyMd5(paramsMap);
            String returnStrJson = HttpUtils.PayHttpPost(accUrl, paramsMap, "10100117", "UTF-8", src, md5);
            returnMap = handlePayJosn(returnStrJson);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("请求支付系统异常", e);
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
            String returnStrJson = HttpUtils.PayHttpPost(accUrl, paramsMap, "10100103", "UTF-8", src,md5);
            Map<String, Object> bodyMap = handlePayJosn(returnStrJson);
            if (bodyMap != null) {
                returnMap.putAll(bodyMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("请求支付系统异常", e);
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
            LOGGER.error("请求支付系统异常", e);
        }
        return null;
    }

    @Override
    public Map<String, Object> discountRecommendUse(String userId,String orderId,String description,String src) {
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
            LOGGER.error("请求支付系统异常", e);
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
