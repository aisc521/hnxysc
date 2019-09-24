package com.zhcdata.db.model;

import java.util.Date;

public class MultiTotalScorehalfDetail {
    private Integer id;

    private Integer oddsid;

    private Float upodds;

    private Float goal;

    private Float downodds;

    private Integer type;

    private Date addtime;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }


    public boolean oddsEquals(MultiTotalScorehalfDetail db) {
        boolean time = true;
        if (db.addtime!=null && addtime!=null)
            if (db.getAddtime().getTime()!=addtime.getTime())
                time = false;
        return time && db.upodds.equals(upodds) && downodds.equals(db.downodds) && goal.equals(db.getGoal());
    }
}