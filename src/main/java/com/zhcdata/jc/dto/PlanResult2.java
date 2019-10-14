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
