package com.zhcdata.db.model;

import lombok.ToString;

import java.util.Date;

@ToString
public class LetGoalhalfDetail {
    private Integer id;

    private Integer oddsid;

    private Float upodds;

    private Float goal;

    private Float downodds;

    private Date modifytime;

    private Boolean isearly;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOddsid() {
        return oddsid;
    }

    public void setOddsid(Integer oddsid) {
        this.oddsid = oddsid;
    }

    public Float getUpodds() {
        return upodds;
    }

    public void setUpodds(Float upodds) {
        this.upodds = upodds;
    }

    public Float getGoal() {
        return goal;
    }

    public void setGoal(Float goal) {
        this.goal = goal;
    }

    public Float getDownodds() {
        return downodds;
    }

    public void setDownodds(Float downodds) {
        this.downodds = downodds;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public Boolean getIsearly() {
        return isearly;
    }

    public void setIsearly(Boolean isearly) {
        this.isearly = isearly;
    }

    public boolean oddsEquals(LetGoalhalfDetail xml) {
        if (this.getGoal()==null && xml.getGoal()!=null)
            return false;
        if (this.getUpodds()==null && xml.getUpodds()!=null)
            return false;
        if (this.getGoal()!=null && xml.getGoal()==null)
            return false;
        if (this.getUpodds()!=null && xml.getUpodds()==null)
            return false;
        if (!this.getModifytime().equals(xml.getModifytime()))
            return false;
        return xml.upodds.equals(upodds) && xml.getGoal().equals(goal) && xml.getDownodds().equals(downodds);
    }
}