package com.zhcdata.db.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

public class Detailresult implements Serializable {
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ScheduleID")
    private Integer scheduleid;

    @Column(name = "HappenTime")
    private Short happentime;

    @Column(name = "TeamID")
    private Integer teamid;

    private String playername;

    @Column(name = "PlayerID")
    private Integer playerid;

    @Column(name = "Kind")
    private Short kind;

    @Column(name = "modifyTime")
    private Date modifytime;

    @Column(name = "playername_e")
    private String playernameE;

    @Column(name = "playername_j")
    private String playernameJ;

    @Column(name = "PlayerID_in")
    private Integer playeridIn;

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
     * @return ScheduleID
     */
    public Integer getScheduleid() {
        return scheduleid;
    }

    /**
     * @param scheduleid
     */
    public void setScheduleid(Integer scheduleid) {
        this.scheduleid = scheduleid;
    }

    /**
     * @return HappenTime
     */
    public Short getHappentime() {
        return happentime;
    }

    /**
     * @param happentime
     */
    public void setHappentime(Short happentime) {
        this.happentime = happentime;
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
     * @return playername
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
     * @return Kind
     */
    public Short getKind() {
        return kind;
    }

    /**
     * @param kind
     */
    public void setKind(Short kind) {
        this.kind = kind;
    }

    /**
     * @return modifyTime
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
     * @return playername_e
     */
    public String getPlayernameE() {
        return playernameE;
    }

    /**
     * @param playernameE
     */
    public void setPlayernameE(String playernameE) {
        this.playernameE = playernameE;
    }

    /**
     * @return playername_j
     */
    public String getPlayernameJ() {
        return playernameJ;
    }

    /**
     * @param playernameJ
     */
    public void setPlayernameJ(String playernameJ) {
        this.playernameJ = playernameJ;
    }

    /**
     * @return PlayerID_in
     */
    public Integer getPlayeridIn() {
        return playeridIn;
    }

    /**
     * @param playeridIn
     */
    public void setPlayeridIn(Integer playeridIn) {
        this.playeridIn = playeridIn;
    }
}