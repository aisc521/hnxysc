package com.zhcdata.db.model;

import lombok.ToString;

import java.util.Date;

@ToString
public class TotalScoreDetail {
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

    public boolean oddsEquals(TotalScoreDetail xml) {
        try {
            if (this.getGoal()==null && xml.getGoal()!=null)
                return false;
            if (this.getDownodds()==null && xml.getDownodds()!=null)
                return false;
            if (this.getGoal()!=null && xml.getGoal()==null)
                return false;
            if (this.getDownodds()!=null && xml.getDownodds()==null)
                return false;
            return xml.goal-goal==0 && xml.getUpodds()-upodds==0 && xml.downodds-downodds==0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}