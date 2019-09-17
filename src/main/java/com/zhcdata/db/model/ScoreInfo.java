package com.zhcdata.db.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "score")
public class ScoreInfo implements Serializable {
    private static final long serialVersionUID = 7000500109417718416L;
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TeamID")
    private Integer teamid;

    @Column(name = "SclassID")
    private Integer sclassid;

    @Column(name = "Win_Score")
    private Integer winScore;

    @Column(name = "Flat_Score")
    private Integer flatScore;

    @Column(name = "Fail_Score")
    private Integer failScore;

    @Column(name = "Total_Homescore")
    private Integer totalHomescore;

    @Column(name = "Total_Guestscore")
    private Integer totalGuestscore;

    private Integer homeorguest;

    @Column(name = "Matchseason")
    private String matchseason;

    private Integer deduct;

    private String cause;

    @Column(name = "Goal")
    private Integer goal;

    @Column(name = "subSclassID")
    private Integer subsclassid;

    @Column(name = "RedCard")
    private Integer redcard;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeamid() {
        return teamid;
    }

    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
    }

    public Integer getSclassid() {
        return sclassid;
    }

    public void setSclassid(Integer sclassid) {
        this.sclassid = sclassid;
    }

    public Integer getWinScore() {
        return winScore;
    }

    public void setWinScore(Integer winScore) {
        this.winScore = winScore;
    }

    public Integer getFlatScore() {
        return flatScore;
    }

    public void setFlatScore(Integer flatScore) {
        this.flatScore = flatScore;
    }

    public Integer getFailScore() {
        return failScore;
    }

    public void setFailScore(Integer failScore) {
        this.failScore = failScore;
    }

    public Integer getTotalHomescore() {
        return totalHomescore;
    }

    public void setTotalHomescore(Integer totalHomescore) {
        this.totalHomescore = totalHomescore;
    }

    public Integer getTotalGuestscore() {
        return totalGuestscore;
    }

    public void setTotalGuestscore(Integer totalGuestscore) {
        this.totalGuestscore = totalGuestscore;
    }

    public Integer getHomeorguest() {
        return homeorguest;
    }

    public void setHomeorguest(Integer homeorguest) {
        this.homeorguest = homeorguest;
    }

    public String getMatchseason() {
        return matchseason;
    }

    public void setMatchseason(String matchseason) {
        this.matchseason = matchseason == null ? null : matchseason.trim();
    }

    public Integer getDeduct() {
        return deduct;
    }

    public void setDeduct(Integer deduct) {
        this.deduct = deduct;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause == null ? null : cause.trim();
    }

    public Integer getGoal() {
        return goal;
    }

    public void setGoal(Integer goal) {
        this.goal = goal;
    }

    public Integer getSubsclassid() {
        return subsclassid;
    }

    public void setSubsclassid(Integer subsclassid) {
        this.subsclassid = subsclassid;
    }

    public Integer getRedcard() {
        return redcard;
    }

    public void setRedcard(Integer redcard) {
        this.redcard = redcard;
    }
}
