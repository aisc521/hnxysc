package com.zhcdata.db.model;

import java.util.Date;

public class Letgoal_goal {
    private Long oddsid;

    private Integer goalCount;

    private Date updateTime;

    private Date createTime;

    public Long getOddsid() {
        return oddsid;
    }

    public void setOddsid(Long oddsid) {
        this.oddsid = oddsid;
    }

    public Integer getGoalCount() {
        return goalCount;
    }

    public void setGoalCount(Integer goalCount) {
        this.goalCount = goalCount;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Letgoal_goal() {
    }

    public Letgoal_goal(Long oddsid, Integer goalCount, Date updateTime, Date createTime) {
        this.oddsid = oddsid;
        this.goalCount = goalCount;
        this.updateTime = updateTime;
        this.createTime = createTime;
    }
}