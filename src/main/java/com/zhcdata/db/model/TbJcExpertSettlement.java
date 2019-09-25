package com.zhcdata.db.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_jc_expert_settlement")
public class TbJcExpertSettlement implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 统计日期
     */
    @Column(name = "statistics_date")
    private String statisticsDate;

    /**
     * 专家昵称
     */
    @Column(name = "nike_name")
    private String nikeName;

    /**
     * 专家手机号
     */
    private String mobile;

    /**
     * 专家所属负责人
     */
    @Column(name = "expert_person")
    private String expertPerson;

    /**
     * 专家方案数量
     */
    @Column(name = "expert_plan_num")
    private Long expertPlanNum;

    /**
     * 销售额
     */
    @Column(name = "sale_price")
    private BigDecimal salePrice;

    /**
     * 付费点播额
     */
    @Column(name = "pay_ondemand_price")
    private BigDecimal payOndemandPrice;

    /**
     * 点播均价
     */
    @Column(name = "ondemand_average")
    private BigDecimal ondemandAverage;

    /**
     * 免费点播额
     */
    @Column(name = "free_ondemand_price")
    private BigDecimal freeOndemandPrice;

    /**
     * 结算金额
     */
    @Column(name = "settlement_amount")
    private BigDecimal settlementAmount;

    /**
     * 分成比例
     */
    @Column(name = "divide_proport")
    private String divideProport;

    /**
     * 实际收入
     */
    @Column(name = "real_income")
    private BigDecimal realIncome;

    /**
     * 结算状态(0 未结算 1 已结算)
     */
    @Column(name = "settlemen_status")
    private Long settlemenStatus;

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
     * 专家所属负责人姓名
     */
    @Column(name = "expert_person_name")
    private String expertPersonName;

    /**
     * 专家id
     */
    @Column(name = "expert_id")
    private Long expertId;

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
     * 获取统计日期
     *
     * @return statistics_date - 统计日期
     */
    public String getStatisticsDate() {
        return statisticsDate;
    }

    /**
     * 设置统计日期
     *
     * @param statisticsDate 统计日期
     */
    public void setStatisticsDate(String statisticsDate) {
        this.statisticsDate = statisticsDate;
    }

    /**
     * 获取专家昵称
     *
     * @return nike_name - 专家昵称
     */
    public String getNikeName() {
        return nikeName;
    }

    /**
     * 设置专家昵称
     *
     * @param nikeName 专家昵称
     */
    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    /**
     * 获取专家手机号
     *
     * @return mobile - 专家手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置专家手机号
     *
     * @param mobile 专家手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取专家所属负责人
     *
     * @return expert_person - 专家所属负责人
     */
    public String getExpertPerson() {
        return expertPerson;
    }

    /**
     * 设置专家所属负责人
     *
     * @param expertPerson 专家所属负责人
     */
    public void setExpertPerson(String expertPerson) {
        this.expertPerson = expertPerson;
    }

    /**
     * 获取专家方案数量
     *
     * @return expert_plan_num - 专家方案数量
     */
    public Long getExpertPlanNum() {
        return expertPlanNum;
    }

    /**
     * 设置专家方案数量
     *
     * @param expertPlanNum 专家方案数量
     */
    public void setExpertPlanNum(Long expertPlanNum) {
        this.expertPlanNum = expertPlanNum;
    }

    /**
     * 获取销售额
     *
     * @return sale_price - 销售额
     */
    public BigDecimal getSalePrice() {
        return salePrice;
    }

    /**
     * 设置销售额
     *
     * @param salePrice 销售额
     */
    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    /**
     * 获取付费点播额
     *
     * @return pay_ondemand_price - 付费点播额
     */
    public BigDecimal getPayOndemandPrice() {
        return payOndemandPrice;
    }

    /**
     * 设置付费点播额
     *
     * @param payOndemandPrice 付费点播额
     */
    public void setPayOndemandPrice(BigDecimal payOndemandPrice) {
        this.payOndemandPrice = payOndemandPrice;
    }

    /**
     * 获取点播均价
     *
     * @return ondemand_average - 点播均价
     */
    public BigDecimal getOndemandAverage() {
        return ondemandAverage;
    }

    /**
     * 设置点播均价
     *
     * @param ondemandAverage 点播均价
     */
    public void setOndemandAverage(BigDecimal ondemandAverage) {
        this.ondemandAverage = ondemandAverage;
    }

    /**
     * 获取免费点播额
     *
     * @return free_ondemand_price - 免费点播额
     */
    public BigDecimal getFreeOndemandPrice() {
        return freeOndemandPrice;
    }

    /**
     * 设置免费点播额
     *
     * @param freeOndemandPrice 免费点播额
     */
    public void setFreeOndemandPrice(BigDecimal freeOndemandPrice) {
        this.freeOndemandPrice = freeOndemandPrice;
    }

    /**
     * 获取结算金额
     *
     * @return settlement_amount - 结算金额
     */
    public BigDecimal getSettlementAmount() {
        return settlementAmount;
    }

    /**
     * 设置结算金额
     *
     * @param settlementAmount 结算金额
     */
    public void setSettlementAmount(BigDecimal settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    /**
     * 获取分成比例
     *
     * @return divide_proport - 分成比例
     */
    public String getDivideProport() {
        return divideProport;
    }

    /**
     * 设置分成比例
     *
     * @param divideProport 分成比例
     */
    public void setDivideProport(String divideProport) {
        this.divideProport = divideProport;
    }

    /**
     * 获取实际收入
     *
     * @return real_income - 实际收入
     */
    public BigDecimal getRealIncome() {
        return realIncome;
    }

    /**
     * 设置实际收入
     *
     * @param realIncome 实际收入
     */
    public void setRealIncome(BigDecimal realIncome) {
        this.realIncome = realIncome;
    }

    /**
     * 获取结算状态(0 未结算 1 已结算)
     *
     * @return settlemen_status - 结算状态(0 未结算 1 已结算)
     */
    public Long getSettlemenStatus() {
        return settlemenStatus;
    }

    /**
     * 设置结算状态(0 未结算 1 已结算)
     *
     * @param settlemenStatus 结算状态(0 未结算 1 已结算)
     */
    public void setSettlemenStatus(Long settlemenStatus) {
        this.settlemenStatus = settlemenStatus;
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
     * 获取专家所属负责人姓名
     *
     * @return expert_person_name - 专家所属负责人姓名
     */
    public String getExpertPersonName() {
        return expertPersonName;
    }

    /**
     * 设置专家所属负责人姓名
     *
     * @param expertPersonName 专家所属负责人姓名
     */
    public void setExpertPersonName(String expertPersonName) {
        this.expertPersonName = expertPersonName;
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
}