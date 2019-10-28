package com.zhcdata.db.model;

import lombok.ToString;

import java.util.Date;

@ToString
public class TotalScorehalfDetail {
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


    public boolean oddsEquals(TotalScorehalfDetail db) {
        try {
            boolean time = true;
            if (db.modifytime!=null && modifytime!=null)
                if (db.getModifytime().getTime()!=modifytime.getTime())
                    time = false;
            return time && db.upodds.equals(upodds) && downodds.equals(db.downodds) && goal.equals(db.getGoal());
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(db.toString());
            System.out.println(this.toString());
            return false;
        }
    }
}