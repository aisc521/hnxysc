package com.zhcdata.db.model;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Table(name = "tb_jc_plan")
public class TbJcPlan implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 所属专家id
     */
    @Column(name = "ascription_expert")
    private Long ascriptionExpert;

    /**
     * 标题显示类型（1标题 2简介）
     */
    @Column(name = "title_show_type")
    private Long titleShowType;

    /**
     * 方案标题
     */
    private String title;

    /**
     * 简介显示类型（1标题 2 简介）
     */
    @Column(name = "introduc_show_type")
    private Long introducShowType;

    /**
     * 方案简介
     */
    private String introduction;

    /**
     * 选号详情
     */
    @Column(name = "num_info")
    private String numInfo;

    /**
     * 方案编号
     */
    private String number;

    /**
     * 方案价格
     */
    private Long price;

    /**
     * 方案类型（1 正常付费 2 不中全退 3 免费）
     */
    private Long type;

    /**
     * 是否中奖（0 未中 1 已中 2 未开始）
     */
    @Column(name = "is_right")
    private Long isRight;

    /**
     * 比赛结束时间
     */
    @Column(name = "end_Time")
    private Date endTime;

    /**
     * 方案创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 方案更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 1 进行中 0 已结束
     */
    private Integer status;

    /**
     * 命中情况
     */
    @Column(name = "plan_Hit")
    private String planHit;

    /**
     * 无付费阅读人数
     */
    @Column(name = "red_Count")
    private Integer redCount;

    /**
     * 付费阅读人数
     */
    @Column(name = "red_Pay_Count")
    private Integer redPayCount;


    /**
     * 方案人气
      */
    @Column(name = "plan_popularity")
    private Integer planPopularity;


    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }
    @Transient
    private Integer cnt ;//传递参数需要用

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
     * 获取所属专家id
     *
     * @return ascription_expert - 所属专家id
     */
    public Long getAscriptionExpert() {
        return ascriptionExpert;
    }

    /**
     * 设置所属专家id
     *
     * @param ascriptionExpert 所属专家id
     */
    public void setAscriptionExpert(Long ascriptionExpert) {
        this.ascriptionExpert = ascriptionExpert;
    }

    /**
     * 获取标题显示类型（1标题 2简介）
     *
     * @return title_show_type - 标题显示类型（1标题 2简介）
     */
    public Long getTitleShowType() {
        return titleShowType;
    }

    /**
     * 设置标题显示类型（1标题 2简介）
     *
     * @param titleShowType 标题显示类型（1标题 2简介）
     */
    public void setTitleShowType(Long titleShowType) {
        this.titleShowType = titleShowType;
    }

    /**
     * 获取方案标题
     *
     * @return title - 方案标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置方案标题
     *
     * @param title 方案标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取简介显示类型（1标题 2 简介）
     *
     * @return introduc_show_type - 简介显示类型（1标题 2 简介）
     */
    public Long getIntroducShowType() {
        return introducShowType;
    }

    /**
     * 设置简介显示类型（1标题 2 简介）
     *
     * @param introducShowType 简介显示类型（1标题 2 简介）
     */
    public void setIntroducShowType(Long introducShowType) {
        this.introducShowType = introducShowType;
    }

    /**
     * 获取方案简介
     *
     * @return introduction - 方案简介
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * 设置方案简介
     *
     * @param introduction 方案简介
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * 获取选号详情
     *
     * @return num_info - 选号详情
     */
    public String getNumInfo() {
        return numInfo;
    }

    /**
     * 设置选号详情
     *
     * @param numInfo 选号详情
     */
    public void setNumInfo(String numInfo) {
        this.numInfo = numInfo;
    }

    /**
     * 获取方案编号
     *
     * @return number - 方案编号
     */
    public String getNumber() {
        return number;
    }

    /**
     * 设置方案编号
     *
     * @param number 方案编号
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * 获取方案价格
     *
     * @return price - 方案价格
     */
    public Long getPrice() {
        return price;
    }

    /**
     * 设置方案价格
     *
     * @param price 方案价格
     */
    public void setPrice(Long price) {
        this.price = price;
    }

    /**
     * 获取方案类型（1 正常付费 2 不中全退 3 免费）
     *
     * @return type - 方案类型（1 正常付费 2 不中全退 3 免费）
     */
    public Long getType() {
        return type;
    }

    /**
     * 设置方案类型（1 正常付费 2 不中全退 3 免费）
     *
     * @param type 方案类型（1 正常付费 2 不中全退 3 免费）
     */
    public void setType(Long type) {
        this.type = type;
    }

    /**
     * 获取是否中奖（0 未中 1 已中 2 未开始）
     *
     * @return is_right - 是否中奖（0 未中 1 已中 2 未开始）
     */
    public Long getIsRight() {
        return isRight;
    }

    /**
     * 设置是否中奖（0 未中 1 已中 2 未开始）
     *
     * @param isRight 是否中奖（0 未中 1 已中 2 未开始）
     */
    public void setIsRight(Long isRight) {
        this.isRight = isRight;
    }

    /**
     * 获取比赛结束时间
     *
     * @return end_Time - 比赛结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置比赛结束时间
     *
     * @param endTime 比赛结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取方案创建时间
     *
     * @return create_time - 方案创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置方案创建时间
     *
     * @param createTime 方案创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取方案更新时间
     *
     * @return update_time - 方案更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置方案更新时间
     *
     * @param updateTime 方案更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取1 进行中 0 已结束
     *
     * @return status - 1 进行中 0 已结束
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置1 进行中 0 已结束
     *
     * @param status 1 进行中 0 已结束
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取命中情况
     *
     * @return plan_Hit - 命中情况
     */
    public String getPlanHit() {
        return planHit;
    }

    /**
     * 设置命中情况
     *
     * @param planHit 命中情况
     */
    public void setPlanHit(String planHit) {
        this.planHit = planHit;
    }

    /**
     * 获取无付费阅读人数
     *
     * @return red_Count - 无付费阅读人数
     */
    public Integer getRedCount() {
        return redCount;
    }

    /**
     * 设置无付费阅读人数
     *
     * @param redCount 无付费阅读人数
     */
    public void setRedCount(Integer redCount) {
        this.redCount = redCount;
    }

    /**
     * 获取付费阅读人数
     *
     * @return red_Pay_Count - 付费阅读人数
     */
    public Integer getRedPayCount() {
        return redPayCount;
    }

    /**
     * 设置付费阅读人数
     *
     * @param redPayCount 付费阅读人数
     */
    public void setRedPayCount(Integer redPayCount) {
        this.redPayCount = redPayCount;
    }

    public Integer getPlanPopularity() {
        return planPopularity;
    }

    public void setPlanPopularity(Integer planPopularity) {
        this.planPopularity = planPopularity;
    }
}