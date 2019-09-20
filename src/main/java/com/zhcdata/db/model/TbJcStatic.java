package com.zhcdata.db.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_jc_static")
public class TbJcStatic implements Serializable {
    private Integer id;

    /**
     * 注册用户数

     */
    @Column(name = "reg_user_num")
    private Integer regUserNum;

    /**
     * 充值人数
     */
    @Column(name = "recharge_user_num")
    private Integer rechargeUserNum;

    /**
     * 消费人数
     */
    @Column(name = "consumption_user_num")
    private Integer consumptionUserNum;

    /**
     * 充值金额
     */
    @Column(name = "recharge_amount")
    private Double rechargeAmount;

    /**
     * 消费金额
     */
    @Column(name = "consumption_amount")
    private Double consumptionAmount;

    /**
     * 点播消费金额金额
     */
    @Column(name = "db_amount")
    private Integer dbAmount;

    /**
     * 不中退次数
     */
    @Column(name = "no_hit_return_count")
    private Integer noHitReturnCount;

    /**
     * 不中退金额
     */
    @Column(name = "no_hit_return_amount")
    private Double noHitReturnAmount;

    /**
     * 同赔金额
     */
    @Column(name = "tp_amount")
    private Integer tpAmount;

    /**
     * 方案消费
     */
    @Column(name = "plan_consumption")
    private Double planConsumption;

    /**
     * 点播消费
     */
    @Column(name = "db_consumption")
    private Double dbConsumption;

    /**
     * 赠送天数
     */
    @Column(name = "give_days")
    private Integer giveDays;

    /**
     * 赠送次数
     */
    @Column(name = "give_count")
    private Integer giveCount;

    /**
     * 微信支付
     */
    @Column(name = "wechat_pay")
    private Integer wechatPay;

    /**
     * 余额付款
     */
    @Column(name = "balance_pay")
    private Integer balancePay;

    /**
     * 支付宝付款
     */
    @Column(name = "ali_pay")
    private Integer aliPay;

    /**
     * 优惠金额
     */
    @Column(name = "discount_amount")
    private Double discountAmount;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 统计时间
     */
    @Column(name = "statistic_date")
    private Date statisticDate;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取注册用户数

     *
     * @return reg_user_num - 注册用户数

     */
    public Integer getRegUserNum() {
        return regUserNum;
    }

    /**
     * 设置注册用户数

     *
     * @param regUserNum 注册用户数

     */
    public void setRegUserNum(Integer regUserNum) {
        this.regUserNum = regUserNum;
    }

    /**
     * 获取充值人数
     *
     * @return recharge_user_num - 充值人数
     */
    public Integer getRechargeUserNum() {
        return rechargeUserNum;
    }

    /**
     * 设置充值人数
     *
     * @param rechargeUserNum 充值人数
     */
    public void setRechargeUserNum(Integer rechargeUserNum) {
        this.rechargeUserNum = rechargeUserNum;
    }

    /**
     * 获取消费人数
     *
     * @return consumption_user_num - 消费人数
     */
    public Integer getConsumptionUserNum() {
        return consumptionUserNum;
    }

    /**
     * 设置消费人数
     *
     * @param consumptionUserNum 消费人数
     */
    public void setConsumptionUserNum(Integer consumptionUserNum) {
        this.consumptionUserNum = consumptionUserNum;
    }

    /**
     * 获取充值金额
     *
     * @return recharge_amount - 充值金额
     */
    public Double getRechargeAmount() {
        return rechargeAmount;
    }

    /**
     * 设置充值金额
     *
     * @param rechargeAmount 充值金额
     */
    public void setRechargeAmount(Double rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    /**
     * 获取消费金额
     *
     * @return consumption_amount - 消费金额
     */
    public Double getConsumptionAmount() {
        return consumptionAmount;
    }

    /**
     * 设置消费金额
     *
     * @param consumptionAmount 消费金额
     */
    public void setConsumptionAmount(Double consumptionAmount) {
        this.consumptionAmount = consumptionAmount;
    }

    /**
     * 获取点播消费金额金额
     *
     * @return db_amount - 点播消费金额金额
     */
    public Integer getDbAmount() {
        return dbAmount;
    }

    /**
     * 设置点播消费金额金额
     *
     * @param dbAmount 点播消费金额金额
     */
    public void setDbAmount(Integer dbAmount) {
        this.dbAmount = dbAmount;
    }

    /**
     * 获取不中退次数
     *
     * @return no_hit_return_count - 不中退次数
     */
    public Integer getNoHitReturnCount() {
        return noHitReturnCount;
    }

    /**
     * 设置不中退次数
     *
     * @param noHitReturnCount 不中退次数
     */
    public void setNoHitReturnCount(Integer noHitReturnCount) {
        this.noHitReturnCount = noHitReturnCount;
    }

    /**
     * 获取不中退金额
     *
     * @return no_hit_return_amount - 不中退金额
     */
    public Double getNoHitReturnAmount() {
        return noHitReturnAmount;
    }

    /**
     * 设置不中退金额
     *
     * @param noHitReturnAmount 不中退金额
     */
    public void setNoHitReturnAmount(Double noHitReturnAmount) {
        this.noHitReturnAmount = noHitReturnAmount;
    }

    /**
     * 获取同赔金额
     *
     * @return tp_amount - 同赔金额
     */
    public Integer getTpAmount() {
        return tpAmount;
    }

    /**
     * 设置同赔金额
     *
     * @param tpAmount 同赔金额
     */
    public void setTpAmount(Integer tpAmount) {
        this.tpAmount = tpAmount;
    }

    /**
     * 获取方案消费
     *
     * @return plan_consumption - 方案消费
     */
    public Double getPlanConsumption() {
        return planConsumption;
    }

    /**
     * 设置方案消费
     *
     * @param planConsumption 方案消费
     */
    public void setPlanConsumption(Double planConsumption) {
        this.planConsumption = planConsumption;
    }

    /**
     * 获取点播消费
     *
     * @return db_consumption - 点播消费
     */
    public Double getDbConsumption() {
        return dbConsumption;
    }

    /**
     * 设置点播消费
     *
     * @param dbConsumption 点播消费
     */
    public void setDbConsumption(Double dbConsumption) {
        this.dbConsumption = dbConsumption;
    }

    /**
     * 获取赠送天数
     *
     * @return give_days - 赠送天数
     */
    public Integer getGiveDays() {
        return giveDays;
    }

    /**
     * 设置赠送天数
     *
     * @param giveDays 赠送天数
     */
    public void setGiveDays(Integer giveDays) {
        this.giveDays = giveDays;
    }

    /**
     * 获取赠送次数
     *
     * @return give_count - 赠送次数
     */
    public Integer getGiveCount() {
        return giveCount;
    }

    /**
     * 设置赠送次数
     *
     * @param giveCount 赠送次数
     */
    public void setGiveCount(Integer giveCount) {
        this.giveCount = giveCount;
    }

    /**
     * 获取微信支付
     *
     * @return wechat_pay - 微信支付
     */
    public Integer getWechatPay() {
        return wechatPay;
    }

    /**
     * 设置微信支付
     *
     * @param wechatPay 微信支付
     */
    public void setWechatPay(Integer wechatPay) {
        this.wechatPay = wechatPay;
    }

    /**
     * 获取余额付款
     *
     * @return balance_pay - 余额付款
     */
    public Integer getBalancePay() {
        return balancePay;
    }

    /**
     * 设置余额付款
     *
     * @param balancePay 余额付款
     */
    public void setBalancePay(Integer balancePay) {
        this.balancePay = balancePay;
    }

    /**
     * 获取支付宝付款
     *
     * @return ali_pay - 支付宝付款
     */
    public Integer getAliPay() {
        return aliPay;
    }

    /**
     * 设置支付宝付款
     *
     * @param aliPay 支付宝付款
     */
    public void setAliPay(Integer aliPay) {
        this.aliPay = aliPay;
    }

    /**
     * 获取优惠金额
     *
     * @return discount_amount - 优惠金额
     */
    public Double getDiscountAmount() {
        return discountAmount;
    }

    /**
     * 设置优惠金额
     *
     * @param discountAmount 优惠金额
     */
    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
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
     * 获取统计时间
     *
     * @return statistic_date - 统计时间
     */
    public Date getStatisticDate() {
        return statisticDate;
    }

    /**
     * 设置统计时间
     *
     * @param statisticDate 统计时间
     */
    public void setStatisticDate(Date statisticDate) {
        this.statisticDate = statisticDate;
    }
}