package com.zhcdata.db.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_jc_purchase_detailed")
public class TbJcPurchaseDetailed implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 支付id
     */
    @Column(name = "pay_id")
    private String payId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 方案id
     */
    @Column(name = "scheme_id")
    private Long schemeId;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 手机号
     */
    private String cell;

    /**
     * 彩种名称
     */
    @Column(name = "lottery_name")
    private String lotteryName;

    /**
     * 支付状态（0_未支付 2_支付成功 8_支付失败 1_冻结状态 3_退款成功）
     */
    @Column(name = "pay_status")
    private Long payStatus;

    /**
     * 中奖状态（0 未中 1 已中）
     */
    @Column(name = "award_status")
    private Long awardStatus;

    /**
     * 三方支付标识
     */
    @Column(name = "trade_type")
    private String tradeType;

    /**
     * 三方金额
     */
    @Column(name = "third_money")
    private BigDecimal thirdMoney;

    /**
     * 购买金额
     */
    @Column(name = "buy_money")
    private Long buyMoney;

    /**
     * 红包金额
     */
    @Column(name = "red_money")
    private Long redMoney;

    /**
     * 折扣金额
     */
    @Column(name = "rebeat_money")
    private Long rebeatMoney;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 年
     */
    private String year;

    /**
     * 月
     */
    private String month;

    /**
     * 日
     */
    private String day;

    /**
     * 支付方式（微信Native支付_20,支付宝支付_21,微信H5支付_22,余额支付_0, AD1点播卡支付）
     */
    @Column(name = "pay_type")
    private Long payType;

    /**
     * 微信支付  支付宝支付
     */
    @Column(name = "pay_info")
    private String payInfo;

    /**
     * 支付
     */
    private String src;

    @Column(name = "apple_receipt")
    private String appleReceipt;

    @Column(name = "apple_retuen")
    private String appleRetuen;

    /**
     * 期次
     */
    private String issue;

    /**
     * 开始期次
     */
    @Column(name = "start_issue")
    private String startIssue;

    /**
     * 结束期次
     */
    @Column(name = "end_issue")
    private String endIssue;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 专家id
     */
    @Column(name = "expert_id")
    private Long expertId;

    /**
     * 方案类型1 正常付费 2不中全退
     */
    @Column(name = "plan_type")
    private String planType;

    /**
     * 支付类型1 点播支付 2 现金支付
     */
    @Column(name = "plan_pay_type")
    private String planPayType;
    /**
     * 是否首次 1 是首次  0 不是首次
     */
    @Column(name = "first")
    private String first;

    /**
     * 优惠券获取方式
     */
    @Column(name = "access")
    private String access;

    /**
     * 优惠券金额
     */
    @Column(name = "coupon_pay_money")
    private String couponPayMoney;

    /**
     * 优惠券ID
     */
    @Column(name = "coupon_id")
    private String couponId;

    @Column(name = "coupon_type")
    private String couponType;


    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    private static final long serialVersionUID = 1L;

    /**
     * 获取id
     *
     * @return id - id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取支付id
     *
     * @return pay_id - 支付id
     */
    public String getPayId() {
        return payId;
    }

    /**
     * 设置支付id
     *
     * @param payId 支付id
     */
    public void setPayId(String payId) {
        this.payId = payId;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取方案id
     *
     * @return scheme_id - 方案id
     */
    public Long getSchemeId() {
        return schemeId;
    }

    /**
     * 设置方案id
     *
     * @param schemeId 方案id
     */
    public void setSchemeId(Long schemeId) {
        this.schemeId = schemeId;
    }

    /**
     * 获取用户名
     *
     * @return user_name - 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     *
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取手机号
     *
     * @return cell - 手机号
     */
    public String getCell() {
        return cell;
    }

    /**
     * 设置手机号
     *
     * @param cell 手机号
     */
    public void setCell(String cell) {
        this.cell = cell;
    }

    /**
     * 获取彩种名称
     *
     * @return lottery_name - 彩种名称
     */
    public String getLotteryName() {
        return lotteryName;
    }

    /**
     * 设置彩种名称
     *
     * @param lotteryName 彩种名称
     */
    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    /**
     * 获取支付状态（0_未支付 2_支付成功 8_支付失败 1_冻结状态 3_退款成功）
     *
     * @return pay_status - 支付状态（0_未支付 2_支付成功 8_支付失败 1_冻结状态 3_退款成功）
     */
    public Long getPayStatus() {
        return payStatus;
    }

    /**
     * 设置支付状态（0_未支付 2_支付成功 8_支付失败 1_冻结状态 3_退款成功）
     *
     * @param payStatus 支付状态（0_未支付 2_支付成功 8_支付失败 1_冻结状态 3_退款成功）
     */
    public void setPayStatus(Long payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * 获取中奖状态（0 未中 1 已中）
     *
     * @return award_status - 中奖状态（0 未中 1 已中）
     */
    public Long getAwardStatus() {
        return awardStatus;
    }

    /**
     * 设置中奖状态（0 未中 1 已中）
     *
     * @param awardStatus 中奖状态（0 未中 1 已中）
     */
    public void setAwardStatus(Long awardStatus) {
        this.awardStatus = awardStatus;
    }

    /**
     * 获取三方支付标识
     *
     * @return trade_type - 三方支付标识
     */
    public String getTradeType() {
        return tradeType;
    }

    /**
     * 设置三方支付标识
     *
     * @param tradeType 三方支付标识
     */
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * 获取三方金额
     *
     * @return third_money - 三方金额
     */
    public BigDecimal getThirdMoney() {
        return thirdMoney;
    }

    /**
     * 设置三方金额
     *
     * @param thirdMoney 三方金额
     */
    public void setThirdMoney(BigDecimal thirdMoney) {
        this.thirdMoney = thirdMoney;
    }

    /**
     * 获取购买金额
     *
     * @return buy_money - 购买金额
     */
    public Long getBuyMoney() {
        return buyMoney;
    }

    /**
     * 设置购买金额
     *
     * @param buyMoney 购买金额
     */
    public void setBuyMoney(Long buyMoney) {
        this.buyMoney = buyMoney;
    }

    /**
     * 获取红包金额
     *
     * @return red_money - 红包金额
     */
    public Long getRedMoney() {
        return redMoney;
    }

    /**
     * 设置红包金额
     *
     * @param redMoney 红包金额
     */
    public void setRedMoney(Long redMoney) {
        this.redMoney = redMoney;
    }

    /**
     * 获取折扣金额
     *
     * @return rebeat_money - 折扣金额
     */
    public Long getRebeatMoney() {
        return rebeatMoney;
    }

    /**
     * 设置折扣金额
     *
     * @param rebeatMoney 折扣金额
     */
    public void setRebeatMoney(Long rebeatMoney) {
        this.rebeatMoney = rebeatMoney;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取年
     *
     * @return year - 年
     */
    public String getYear() {
        return year;
    }

    /**
     * 设置年
     *
     * @param year 年
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * 获取月
     *
     * @return month - 月
     */
    public String getMonth() {
        return month;
    }

    /**
     * 设置月
     *
     * @param month 月
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * 获取日
     *
     * @return day - 日
     */
    public String getDay() {
        return day;
    }

    /**
     * 设置日
     *
     * @param day 日
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * 获取支付方式（微信Native支付_20,支付宝支付_21,微信H5支付_22,余额支付_0, AD1点播卡支付）
     *
     * @return pay_type - 支付方式（微信Native支付_20,支付宝支付_21,微信H5支付_22,余额支付_0, AD1点播卡支付）
     */
    public Long getPayType() {
        return payType;
    }

    /**
     * 设置支付方式（微信Native支付_20,支付宝支付_21,微信H5支付_22,余额支付_0, AD1点播卡支付）
     *
     * @param payType 支付方式（微信Native支付_20,支付宝支付_21,微信H5支付_22,余额支付_0, AD1点播卡支付）
     */
    public void setPayType(Long payType) {
        this.payType = payType;
    }

    /**
     * 获取微信支付  支付宝支付
     *
     * @return pay_info - 微信支付  支付宝支付
     */
    public String getPayInfo() {
        return payInfo;
    }

    /**
     * 设置微信支付  支付宝支付
     *
     * @param payInfo 微信支付  支付宝支付
     */
    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }

    /**
     * 获取支付
     *
     * @return src - 支付
     */
    public String getSrc() {
        return src;
    }

    /**
     * 设置支付
     *
     * @param src 支付
     */
    public void setSrc(String src) {
        this.src = src;
    }

    /**
     * @return apple_receipt
     */
    public String getAppleReceipt() {
        return appleReceipt;
    }

    /**
     * @param appleReceipt
     */
    public void setAppleReceipt(String appleReceipt) {
        this.appleReceipt = appleReceipt;
    }

    /**
     * @return apple_retuen
     */
    public String getAppleRetuen() {
        return appleRetuen;
    }

    /**
     * @param appleRetuen
     */
    public void setAppleRetuen(String appleRetuen) {
        this.appleRetuen = appleRetuen;
    }

    /**
     * 获取期次
     *
     * @return issue - 期次
     */
    public String getIssue() {
        return issue;
    }

    /**
     * 设置期次
     *
     * @param issue 期次
     */
    public void setIssue(String issue) {
        this.issue = issue;
    }

    /**
     * 获取开始期次
     *
     * @return start_issue - 开始期次
     */
    public String getStartIssue() {
        return startIssue;
    }

    /**
     * 设置开始期次
     *
     * @param startIssue 开始期次
     */
    public void setStartIssue(String startIssue) {
        this.startIssue = startIssue;
    }

    /**
     * 获取结束期次
     *
     * @return end_issue - 结束期次
     */
    public String getEndIssue() {
        return endIssue;
    }

    /**
     * 设置结束期次
     *
     * @param endIssue 结束期次
     */
    public void setEndIssue(String endIssue) {
        this.endIssue = endIssue;
    }

    /**
     * 获取订单id
     *
     * @return order_id - 订单id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单id
     *
     * @param orderId 订单id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取专家id
     *
     * @return expert_id - 专家id
     */
    public Long getExpertId() {
        return expertId;
    }

    /**
     * 设置专家id
     *
     * @param expertId 专家id
     */
    public void setExpertId(Long expertId) {
        this.expertId = expertId;
    }

    /**
     * 获取方案类型1 正常付费 2不中全退
     *
     * @return plan_type - 方案类型1 正常付费 2不中全退
     */
    public String getPlanType() {
        return planType;
    }

    /**
     * 设置方案类型1 正常付费 2不中全退
     *
     * @param planType 方案类型1 正常付费 2不中全退
     */
    public void setPlanType(String planType) {
        this.planType = planType;
    }

    /**
     * 获取支付类型1 点播支付 2 现金支付
     *
     * @return plan_pay_type - 支付类型1 点播支付 2 现金支付
     */
    public String getPlanPayType() {
        return planPayType;
    }

    /**
     * 设置支付类型1 点播支付 2 现金支付
     *
     * @param planPayType 支付类型1 点播支付 2 现金支付
     */
    public void setPlanPayType(String planPayType) {
        this.planPayType = planPayType;
    }

    public String getCouponPayMoney() {
        return couponPayMoney;
    }

    public void setCouponPayMoney(String couponPayMoney) {
        this.couponPayMoney = couponPayMoney;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }
}