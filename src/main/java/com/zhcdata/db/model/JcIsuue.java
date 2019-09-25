package com.zhcdata.db.model;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "jc_isuue")
public class JcIsuue implements Serializable {
    /**
     * 主键
     */
    @Column(name = "ID")
    private Long id;

    /**
     * 期号
     */
    @Column(name = "ISSUE")
    private String issue;

    /**
     * 开奖号码
     */
    @Column(name = "WIN_NUM")
    private String winNum;

    /**
     * 彩种
     */
    @Column(name = "LOTTERY")
    private String lottery;

    /**
     * 彩种名称
     */
    @Column(name = "LOTTERY_NAME")
    private String lotteryName;

    /**
     * 状态 0 未开奖  2 已开奖  3异常数据  1处理中
     */
    @Column(name = "STATUS")
    private String status;

    /**
     * 获取开奖号码开始时间
     */
    @Column(name = "SALE_TIME_START")
    private Date saleTimeStart;

    /**
     * 获取开奖号码结束时间
     */
    @Column(name = "SALE_TIME_END")
    private Date saleTimeEnd;

    /**
     * 销售时间
     */
    @Column(name = "REAL_SALE_START_TIME")
    private Date realSaleStartTime;

    /**
     * 销售时间
     */
    @Column(name = "REAL_SALE_END_TIME")
    private Date realSaleEndTime;

    /**
     * 开奖时间
     */
    @Column(name = "AWARD_TIME")
    private Date awardTime;

    /**
     * 更新时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 年
     */
    @Column(name = "YEAR")
    private String year;

    /**
     * 月
     */
    @Column(name = "MONTH")
    private String month;

    /**
     * 日
     */
    @Column(name = "DAY")
    private String day;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键
     *
     * @return ID - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取期号
     *
     * @return ISSUE - 期号
     */
    public String getIssue() {
        return issue;
    }

    /**
     * 设置期号
     *
     * @param issue 期号
     */
    public void setIssue(String issue) {
        this.issue = issue;
    }

    /**
     * 获取开奖号码
     *
     * @return WIN_NUM - 开奖号码
     */
    public String getWinNum() {
        return winNum;
    }

    /**
     * 设置开奖号码
     *
     * @param winNum 开奖号码
     */
    public void setWinNum(String winNum) {
        this.winNum = winNum;
    }

    /**
     * 获取彩种
     *
     * @return LOTTERY - 彩种
     */
    public String getLottery() {
        return lottery;
    }

    /**
     * 设置彩种
     *
     * @param lottery 彩种
     */
    public void setLottery(String lottery) {
        this.lottery = lottery;
    }

    /**
     * 获取彩种名称
     *
     * @return LOTTERY_NAME - 彩种名称
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
     * 获取状态 0 未开奖  2 已开奖  3异常数据  1处理中
     *
     * @return STATUS - 状态 0 未开奖  2 已开奖  3异常数据  1处理中
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态 0 未开奖  2 已开奖  3异常数据  1处理中
     *
     * @param status 状态 0 未开奖  2 已开奖  3异常数据  1处理中
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取获取开奖号码开始时间
     *
     * @return SALE_TIME_START - 获取开奖号码开始时间
     */
    public Date getSaleTimeStart() {
        return saleTimeStart;
    }

    /**
     * 设置获取开奖号码开始时间
     *
     * @param saleTimeStart 获取开奖号码开始时间
     */
    public void setSaleTimeStart(Date saleTimeStart) {
        this.saleTimeStart = saleTimeStart;
    }

    /**
     * 获取获取开奖号码结束时间
     *
     * @return SALE_TIME_END - 获取开奖号码结束时间
     */
    public Date getSaleTimeEnd() {
        return saleTimeEnd;
    }

    /**
     * 设置获取开奖号码结束时间
     *
     * @param saleTimeEnd 获取开奖号码结束时间
     */
    public void setSaleTimeEnd(Date saleTimeEnd) {
        this.saleTimeEnd = saleTimeEnd;
    }

    /**
     * 获取销售时间
     *
     * @return REAL_SALE_START_TIME - 销售时间
     */
    public Date getRealSaleStartTime() {
        return realSaleStartTime;
    }

    /**
     * 设置销售时间
     *
     * @param realSaleStartTime 销售时间
     */
    public void setRealSaleStartTime(Date realSaleStartTime) {
        this.realSaleStartTime = realSaleStartTime;
    }

    /**
     * 获取销售时间
     *
     * @return REAL_SALE_END_TIME - 销售时间
     */
    public Date getRealSaleEndTime() {
        return realSaleEndTime;
    }

    /**
     * 设置销售时间
     *
     * @param realSaleEndTime 销售时间
     */
    public void setRealSaleEndTime(Date realSaleEndTime) {
        this.realSaleEndTime = realSaleEndTime;
    }

    /**
     * 获取开奖时间
     *
     * @return AWARD_TIME - 开奖时间
     */
    public Date getAwardTime() {
        return awardTime;
    }

    /**
     * 设置开奖时间
     *
     * @param awardTime 开奖时间
     */
    public void setAwardTime(Date awardTime) {
        this.awardTime = awardTime;
    }

    /**
     * 获取更新时间
     *
     * @return UPDATE_TIME - 更新时间
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
     * @return YEAR - 年
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
     * @return MONTH - 月
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
     * @return DAY - 日
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
     * 获取创建时间
     *
     * @return CREATE_TIME - 创建时间
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
}