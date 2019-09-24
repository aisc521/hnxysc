package com.zhcdata.db.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_jc_expert")
public class TbJcExpert implements Serializable {
    /**
     * 专家id
     */
    private Long id;

    /**
     * 专家昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 专家头像
     */
    private String img;

    /**
     * 专家标签
     */
    private String lable;

    /**
     * 专家介绍
     */
    private String introduction;

    /**
     * 分成比例
     */
    @Column(name = "royalty_ratio")
    private String royaltyRatio;

    /**
     * 专家所属负责人
     */
    private String ascription;

    /**
     * 真实粉丝数
     */
    private Long fans;

    /**
     * 真实经验数
     */
    private Long experience;

    private Integer popularity;

    /**
     * 专家手机号
     */
    private String mobile;

    /**
     * 专家真实姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 专家身份证号
     */
    @Column(name = "card_id")
    private String cardId;

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
     * 专家起始粉丝数
     */
    @Column(name = "start_fans")
    private Long startFans;

    /**
     * 专家起始经验数
     */
    @Column(name = "start_experience")
    private Long startExperience;

    /**
     * 是否上架1 上架 2 下架
     */
    @Column(name = "is_upshelf")
    private Long isUpshelf;

    /**
     * 专家等级
     */
    private Long grade;

    private static final long serialVersionUID = 1L;

    /**
     * 获取专家id
     *
     * @return id - 专家id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置专家id
     *
     * @param id 专家id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取专家昵称
     *
     * @return nick_name - 专家昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置专家昵称
     *
     * @param nickName 专家昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取专家头像
     *
     * @return img - 专家头像
     */
    public String getImg() {
        return img;
    }

    /**
     * 设置专家头像
     *
     * @param img 专家头像
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * 获取专家标签
     *
     * @return lable - 专家标签
     */
    public String getLable() {
        return lable;
    }

    /**
     * 设置专家标签
     *
     * @param lable 专家标签
     */
    public void setLable(String lable) {
        this.lable = lable;
    }

    /**
     * 获取专家介绍
     *
     * @return introduction - 专家介绍
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * 设置专家介绍
     *
     * @param introduction 专家介绍
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * 获取分成比例
     *
     * @return royalty_ratio - 分成比例
     */
    public String getRoyaltyRatio() {
        return royaltyRatio;
    }

    /**
     * 设置分成比例
     *
     * @param royaltyRatio 分成比例
     */
    public void setRoyaltyRatio(String royaltyRatio) {
        this.royaltyRatio = royaltyRatio;
    }

    /**
     * 获取专家所属负责人
     *
     * @return ascription - 专家所属负责人
     */
    public String getAscription() {
        return ascription;
    }

    /**
     * 设置专家所属负责人
     *
     * @param ascription 专家所属负责人
     */
    public void setAscription(String ascription) {
        this.ascription = ascription;
    }

    /**
     * 获取真实粉丝数
     *
     * @return fans - 真实粉丝数
     */
    public Long getFans() {
        return fans;
    }

    /**
     * 设置真实粉丝数
     *
     * @param fans 真实粉丝数
     */
    public void setFans(Long fans) {
        this.fans = fans;
    }

    /**
     * 获取真实经验数
     *
     * @return experience - 真实经验数
     */
    public Long getExperience() {
        return experience;
    }

    /**
     * 设置真实经验数
     *
     * @param experience 真实经验数
     */
    public void setExperience(Long experience) {
        this.experience = experience;
    }

    /**
     * @return popularity
     */
    public Integer getPopularity() {
        return popularity;
    }

    /**
     * @param popularity
     */
    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
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
     * 获取专家真实姓名
     *
     * @return real_name - 专家真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置专家真实姓名
     *
     * @param realName 专家真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 获取专家身份证号
     *
     * @return card_id - 专家身份证号
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * 设置专家身份证号
     *
     * @param cardId 专家身份证号
     */
    public void setCardId(String cardId) {
        this.cardId = cardId;
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
     * 获取专家起始粉丝数
     *
     * @return start_fans - 专家起始粉丝数
     */
    public Long getStartFans() {
        return startFans;
    }

    /**
     * 设置专家起始粉丝数
     *
     * @param startFans 专家起始粉丝数
     */
    public void setStartFans(Long startFans) {
        this.startFans = startFans;
    }

    /**
     * 获取专家起始经验数
     *
     * @return start_experience - 专家起始经验数
     */
    public Long getStartExperience() {
        return startExperience;
    }

    /**
     * 设置专家起始经验数
     *
     * @param startExperience 专家起始经验数
     */
    public void setStartExperience(Long startExperience) {
        this.startExperience = startExperience;
    }

    /**
     * 获取是否上架1 上架 2 下架
     *
     * @return is_upshelf - 是否上架1 上架 2 下架
     */
    public Long getIsUpshelf() {
        return isUpshelf;
    }

    /**
     * 设置是否上架1 上架 2 下架
     *
     * @param isUpshelf 是否上架1 上架 2 下架
     */
    public void setIsUpshelf(Long isUpshelf) {
        this.isUpshelf = isUpshelf;
    }

    /**
     * 获取专家等级
     *
     * @return grade - 专家等级
     */
    public Long getGrade() {
        return grade;
    }

    /**
     * 设置专家等级
     *
     * @param grade 专家等级
     */
    public void setGrade(Long grade) {
        this.grade = grade;
    }
}