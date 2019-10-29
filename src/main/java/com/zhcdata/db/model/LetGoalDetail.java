package com.zhcdata.db.model;

import lombok.ToString;

import java.util.Date;

@ToString
public class LetGoalDetail {
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

    public boolean oddsEquals(LetGoalDetail db) {
        try {
            if (this.getGoal()==null && db.getGoal()!=null)
                return false;
            if (this.getUpodds()==null && db.getUpodds()!=null)
                return false;
            if (this.getGoal()!=null && db.getGoal()==null)
                return false;
            if (this.getUpodds()!=null && db.getUpodds()==null)
                return false;

            return db.goal.equals(goal) && db.upodds.equals(upodds) && db.getDownodds().equals(downodds);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}