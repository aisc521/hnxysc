package com.zhcdata.db.model;

import java.util.Date;
import java.util.Objects;

public class Letgoal {
    private Integer oddsid;

    private Integer scheduleid;

    private Integer companyid;

    private Float firstgoal;

    private Float firstupodds;

    private Float firstdownodds;

    private Float goal;

    private Float upodds;

    private Float downodds;

    private Date modifytime;

    private Integer result;

    private Boolean closepan;

    private Boolean zoudi;

    private Boolean running;

    private Float goalReal;

    private Float upoddsReal;

    private Float downoddsReal;

    private Boolean isstoplive;

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

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Boolean getClosepan() {
        return closepan;
    }

    public void setClosepan(Boolean closepan) {
        this.closepan = closepan;
    }

    public Boolean getZoudi() {
        return zoudi;
    }

    public void setZoudi(Boolean zoudi) {
        this.zoudi = zoudi;
    }

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
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

    public Boolean getIsstoplive() {
        return isstoplive;
    }

    public void setIsstoplive(Boolean isstoplive) {
        this.isstoplive = isstoplive;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public boolean nowOddsSame(Letgoal db) {
        return db.goal.equals(goal)&&db.upodds.equals(upodds)&&db.downodds.equals(downodds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Letgoal letgoal = (Letgoal) o;
        return //oddsid.equals(letgoal.oddsid) &&
                scheduleid.equals(letgoal.scheduleid) &&
                companyid.equals(letgoal.companyid) &&
                firstgoal.equals(letgoal.firstgoal) &&
                firstupodds.equals(letgoal.firstupodds) &&
                firstdownodds.equals(letgoal.firstdownodds) &&
                Objects.equals(goal, letgoal.goal) &&
                Objects.equals(upodds, letgoal.upodds) &&
                Objects.equals(downodds, letgoal.downodds) &&
                Objects.equals(modifytime, letgoal.modifytime) &&
                Objects.equals(goalReal, letgoal.goalReal) &&
                Objects.equals(upoddsReal, letgoal.upoddsReal) &&
                Objects.equals(downoddsReal, letgoal.downoddsReal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oddsid, scheduleid, companyid, firstgoal, firstupodds, firstdownodds, goal, upodds, downodds, modifytime, goalReal, upoddsReal, downoddsReal);
    }


    @Override
    public String toString() {
        return "Letgoal{" +
                "oddsid=" + oddsid +
                ", scheduleid=" + scheduleid +
                ", companyid=" + companyid +
                ", firstgoal=" + firstgoal +
                ", firstupodds=" + firstupodds +
                ", firstdownodds=" + firstdownodds +
                ", goal=" + goal +
                ", upodds=" + upodds +
                ", downodds=" + downodds +
                ", modifytime=" + modifytime +
                ", result=" + result +
                ", goalReal=" + goalReal +
                ", upoddsReal=" + upoddsReal +
                ", downoddsReal=" + downoddsReal +
                ", isstoplive=" + isstoplive +
                ", starttime=" + starttime +
                '}';
    }
}