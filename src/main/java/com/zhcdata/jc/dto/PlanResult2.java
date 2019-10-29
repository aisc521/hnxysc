package com.zhcdata.jc.dto;

import java.util.ArrayList;
import java.util.List;

public class PlanResult2 {
    private String id;
    private String nickName;
    private String img;
    private String lable;
    private String fans;
    private String introduction;
    private String numInfo;
    private String title;
    private String status;

    private String planHit;

    private String popularity;

    private String lz;
    private String zThreeDays;
    private String zFiveDays;
    private String zSevenDays;
    private String price;
    private String createTime;
    private String grade;
    private String planStatus;
    private String pintroduction;

    public String getPintroduction() {
        return pintroduction;
    }

    public void setPintroduction(String pintroduction) {
        this.pintroduction = pintroduction;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
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

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPlanHit() {
        return planHit;
    }

    public void setPlanHit(String planHit) {
        this.planHit = planHit;
    }

    List<MatchPlanResult1> list=new ArrayList<>();

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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getNumInfo() {
        return numInfo;
    }

    public void setNumInfo(String numInfo) {
        this.numInfo = numInfo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MatchPlanResult1> getList() {
        return list;
    }

    public void setList(List<MatchPlanResult1> list) {
        this.list = list;
    }
}
