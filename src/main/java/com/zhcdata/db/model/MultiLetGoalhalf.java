package com.zhcdata.db.model;

import java.util.Date;

public class MultiLetGoalhalf {
    private Integer oddsid;

    private Integer scheduleid;

    private Integer companyid;

    private Float firstgoal;

    private Float firstupodds;

    private Float firstdownodds;

    private Float goal;

    private Float upodds;

    private Float downodds;

    private Boolean zoudi;

    private Short num;

    private Date modifytime;

    private Float goalReal;

    private Float upoddsReal;

    private Float downoddsReal;

    private Date starttime;

    public Integer getOddsid() {
        return oddsid;
    }

    public void setOddsid(Integer oddsid) {
        this.oddsid = oddsid;
    }

    public Integer getScheduleid() {
        return scheduleid;
    }

    public void setScheduleid(Integer scheduleid) {
        this.scheduleid = scheduleid;
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public Float getFirstgoal() {
        return firstgoal;
    }

    public void setFirstgoal(Float firstgoal) {
        this.firstgoal = firstgoal;
    }

    public Float getFirstupodds() {
        return firstupodds;
    }

    public void setFirstupodds(Float firstupodds) {
        this.firstupodds = firstupodds;
    }

    public Float getFirstdownodds() {
        return firstdownodds;
    }

    public void setFirstdownodds(Float firstdownodds) {
        this.firstdownodds = firstdownodds;
    }

    public Float getGoal() {
        return goal;
    }

    public void setGoal(Float goal) {
        this.goal = goal;
    }

    public Float getUpodds() {
        return upodds;
    }

    public void setUpodds(Float upodds) {
        this.upodds = upodds;
    }

    public Float getDownodds() {
        return downodds;
    }

    public void setDownodds(Float downodds) {
        this.downodds = downodds;
    }

    public Boolean getZoudi() {
        return zoudi;
    }

    public void setZoudi(Boolean zoudi) {
        this.zoudi = zoudi;
    }

    public Short getNum() {
        return num;
    }

    public void setNum(Short num) {
        this.num = num;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public Float getGoalReal() {
        return goalReal;
    }

    public void setGoalReal(Float goalReal) {
        this.goalReal = goalReal;
    }

    public Float getUpoddsReal() {
        return upoddsReal;
    }

    public void setUpoddsReal(Float upoddsReal) {
        this.upoddsReal = upoddsReal;
    }

    public Float getDownoddsReal() {
        return downoddsReal;
    }

    public void setDownoddsReal(Float downoddsReal) {
        this.downoddsReal = downoddsReal;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public boolean oddsEquals(MultiLetGoalhalf db) {
        return db.getGoal().equals(goal) && db.getUpodds().equals(upodds) && db.getDownodds().equals(downodds);
    }
}