package com.zhcdata.jc.dto;

import java.util.List;

public class PlanResult1 {
    //某个专家的所有方案
    private String id;
    private String nickName;
    private String img;
    private String lable;
    private String fans;
    private String popularity;
    private String lz;
    private String zThreeDays;
    private String zFiveDays;
    private String zSevenDays;
    private String titleShowType;
    private String price;
    private String type;
    private String createTime;
    private String planHit;
    private String planId;
    private String planType;
    private String planStatus;
    private String buStatus;

    public String getBuStatus() {
        return buStatus;
    }

    public void setBuStatus(String buStatus) {
        this.buStatus = buStatus;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanHit() {
        return planHit;
    }

    public void setPlanHit(String planHit) {
        this.planHit = planHit;
    }

    private List<MatchPlanResult> list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getLz() {
        return lz;
    }

    public void setLz(String lz) {
        this.lz = lz;
    }

    public String getzThreeDays() {
        return zThreeDays;
    }

    public void setzThreeDays(String zThreeDays) {
        this.zThreeDays = zThreeDays;
    }

    public String getzFiveDays() {
        return zFiveDays;
    }

    public void setzFiveDays(String zFiveDays) {
        this.zFiveDays = zFiveDays;
    }

    public String getzSevenDays() {
        return zSevenDays;
    }

    public void setzSevenDays(String zSevenDays) {
        this.zSevenDays = zSevenDays;
    }

    public String getTitleShowType() {
        return titleShowType;
    }

    public void setTitleShowType(String titleShowType) {
        this.titleShowType = titleShowType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<MatchPlanResult> getList() {
        return list;
    }

    public void setList(List<MatchPlanResult> list) {
        this.list = list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
