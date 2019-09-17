package com.zhcdata.db.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

public class Playerinteam implements Serializable {
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PlayerID")
    private Integer playerid;

    @Column(name = "PlayerName")
    private String playername;

    @Column(name = "TeamID")
    private Integer teamid;

    @Column(name = "TeamName")
    private String teamname;

    @Column(name = "Place")
    private String place;

    @Column(name = "Number")
    private String number;

    @Column(name = "Score")
    private Short score;

    @Column(name = "ModifyTime")
    private Date modifytime;

    @Column(name = "coachTypeID")
    private Integer coachtypeid;

    @Column(name = "captainTypeID")
    private Integer captaintypeid;

    @Column(name = "PlayerPositionID")
    private Integer playerpositionid;

    @Column(name = "startTime")
    private Date starttime;

    @Column(name = "endTime")
    private Date endtime;

    private static final long serialVersionUID = 1L;

    /**
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return PlayerID
     */
    public Integer getPlayerid() {
        return playerid;
    }

    /**
     * @param playerid
     */
    public void setPlayerid(Integer playerid) {
        this.playerid = playerid;
    }

    /**
     * @return PlayerName
     */
    public String getPlayername() {
        return playername;
    }

    /**
     * @param playername
     */
    public void setPlayername(String playername) {
        this.playername = playername;
    }

    /**
     * @return TeamID
     */
    public Integer getTeamid() {
        return teamid;
    }

    /**
     * @param teamid
     */
    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
    }

    /**
     * @return TeamName
     */
    public String getTeamname() {
        return teamname;
    }

    /**
     * @param teamname
     */
    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    /**
     * @return Place
     */
    public String getPlace() {
        return place;
    }

    /**
     * @param place
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * @return Number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return Score
     */
    public Short getScore() {
        return score;
    }

    /**
     * @param score
     */
    public void setScore(Short score) {
        this.score = score;
    }

    /**
     * @return ModifyTime
     */
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * @param modifytime
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * @return coachTypeID
     */
    public Integer getCoachtypeid() {
        return coachtypeid;
    }

    /**
     * @param coachtypeid
     */
    public void setCoachtypeid(Integer coachtypeid) {
        this.coachtypeid = coachtypeid;
    }

    /**
     * @return captainTypeID
     */
    public Integer getCaptaintypeid() {
        return captaintypeid;
    }

    /**
     * @param captaintypeid
     */
    public void setCaptaintypeid(Integer captaintypeid) {
        this.captaintypeid = captaintypeid;
    }

    /**
     * @return PlayerPositionID
     */
    public Integer getPlayerpositionid() {
        return playerpositionid;
    }

    /**
     * @param playerpositionid
     */
    public void setPlayerpositionid(Integer playerpositionid) {
        this.playerpositionid = playerpositionid;
    }

    /**
     * @return startTime
     */
    public Date getStarttime() {
        return starttime;
    }

    /**
     * @param starttime
     */
    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    /**
     * @return endTime
     */
    public Date getEndtime() {
        return endtime;
    }

    /**
     * @param endtime
     */
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }
}