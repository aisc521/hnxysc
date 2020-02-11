package com.zhcdata.jc.service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Title: 支付相关service
 * Description: 支付相关service，调用账户中心
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/8/22 16:09
 */
public interface PayService {

    /**
     * 微信支付
     * @param userId 用户id
     * @param payMoney 支付金额
     * @param description 购买说明文字
     * @param productName 购买商品（如果为空，则微信弹出的名称显示为description）
     * @param payType 支付方式 20微信native 22微信h5
     * @param orderId 订单号
     * @param src   用户src
     * @param ip    用户ip，从body获取
     * @return
     */
    Map<String, Object> wechatPay(String userId, String payMoney, String productName, String description, String payType, String orderId, String src, String ip);

    /**
     * 好店啊支付宝支付
     * @param userId 用户id
     * @param payMoney 支付金额
     * @param description 购买说明文字
     * @param payType 支付方式 21好店啊支付宝
     * @param orderId   订单号
     * @param src   用户src
     * @param ip    用户ip，从body获取
     * @return
     */
    Map<String, Object> aliPay(String userId, String payMoney, String description, String payType, String orderId, String src, String ip);

    /**
     * 余额支付(冻结余额)
     * @param payMoney 支付金额
     * @param payType   支付方式 0，3 余额支付
     * @param userId 用户id
     * @param orderId   订单号
     * @param src   用户src
     * @param productName   购买说明文字
     * @return
     */
    Map<String, Object> moneyPay(String payMoney, String payType, String userId, String orderId, String src, String productName);

    /**
     * 订单查询
     * @param payMoney  支付金额
     * @param payType   支付方式 0，3 余额支付  20微信native  21好店啊支付宝  22微信h5
     * @param userId    用户id
     * @param orderId   订单号
     * @param src       用户src
     * @return
     */
    Map<String, Object> queryOrderStatus(String payMoney, String payType, String userId, String orderId, String src);

    /**
     * 使用点播卡
     * @param userId 用户id
     * @param orderId   订单号
     * @param description 购买说明文字
     * @param src   src
     * @return type 1普通次卡  5周卡   7免费赠送限时点播卡
     */
    Map<String,Object> discountRecommendUse(String userId, String orderId, String description, String src);

    /**
     * 退冻结款到账户
     * @param userId    用户id
     * @param orderId   支付点播订单号
     * @param amount    实际退款金额
     * @param remark    说明文字 如点播方案不中退
     * @param src       用户src  使用购买时的SRC即可
     * @return
     */
    Map<String,Object> refundFrozenToMoney(Long userId, String orderId, BigDecimal amount, String remark, String src);

    /**
     * 扣除冻结金额
     * @param userId    用户id
     * @param orderId   支付点播订单号
     * @param amount    实际退款金额
     * @param remark    说明文字 如点播方案扣款
     * @param src       用户src  使用购买时的SRC即可
     * @return
     */
    Map<String,Object> deductFrozen(Long userId, String orderId, BigDecimal amount, String remark, String src);



    /**
     * 退回已使用的未中点播卡次数
     * @param userId        用户id
     * @param orderId       支付点播订单号
     * @param type          1.普通次卡   5.周卡
     * @param productName   说明文字 如点播方案不中退
     * @param src           用户src  使用购买时的SRC即可
     * @return
     */
    Map<String,Object> refundDiscount(Long userId,String orderId,String type,String productName,String src);


    /**
     * 通用券使用
     * @param userId
     * @param orderId
     * @param description
     * @param src
     * @return
     */
    Map<String,Object> currencyCouponPay(String userId,String couponId, String orderId, String description, String src);
}
