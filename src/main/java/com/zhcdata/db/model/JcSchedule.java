package com.zhcdata.db.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "jc_schedule")
public class JcSchedule implements Serializable {
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ScheduleID")
    private Integer scheduleid;

    @Column(name = "MatchID")
    private String matchid;

    @Column(name = "MatchTime")
    private Date matchtime;

    @Column(name = "SelloutTime")
    private Date sellouttime;

    @Column(name = "Sclass")
    private String sclass;

    @Column(name = "HomeTeam")
    private String hometeam;

    @Column(name = "HomeTeamID")
    private Integer hometeamid;

    @Column(name = "GuestTeam")
    private String guestteam;

    @Column(name = "GuestTeamID")
    private Integer guestteamid;

    @Column(name = "MatchState")
    private Short matchstate;

    @Column(name = "HomeScore")
    private Short homescore;

    @Column(name = "GuestScore")
    private Short guestscore;

    @Column(name = "HomeHalfScore")
    private Short homehalfscore;

    @Column(name = "GuestHalfScore")
    private Short guesthalfscore;

    @Column(name = "PolyGoal")
    private Float polygoal;

    @Column(name = "IsEnd")
    private boolean isend;

    @Column(name = "IsTurned")
    private boolean isturned;

    private Short state;

    @Column(name = "HomeTeamF")
    private String hometeamf;

    @Column(name = "GuestTeamF")
    private String guestteamf;

    @Column(name = "IsAudit")
    private Byte isaudit;

    @Column(name = "MatchTime2")
    private Date matchtime2;

    @Column(name = "HomeTeamGov")
    private String hometeamgov;

    @Column(name = "GuestTeamGov")
    private String guestteamgov;

    @Column(name = "Single101")
    private Integer single101;

    @Column(name = "Single102")
    private Integer single102;

    @Column(name = "Single103")
    private Integer single103;

    @Column(name = "Single104")
    private Integer single104;

    @Column(name = "Single105")
    private Integer single105;

    @Column(name = "AddTime")
    private Date addtime;

    @Column(name = "SelloutTimeGov")
    private Date sellouttimegov;

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
     * @return MatchID
     */
    public String getMatchid() {
        return matchid;
    }

    /**
     * @param matchid
     */
    public void setMatchid(String matchid) {
        this.matchid = matchid;
    }

    /**
     * @return MatchTime
     */
    public Date getMatchtime() {
        return matchtime;
    }

    /**
     * @param matchtime
     */
    public void setMatchtime(Date matchtime) {
        this.matchtime = matchtime;
    }

    /**
     * @return SelloutTime
     */
    public Date getSellouttime() {
        return sellouttime;
    }

    /**
     * @param sellouttime
     */
    public void setSellouttime(Date sellouttime) {
        this.sellouttime = sellouttime;
    }

    /**
     * @return Sclass
     */
    public String getSclass() {
        return sclass;
    }

    /**
     * @param sclass
     */
    public void setSclass(String sclass) {
        this.sclass = sclass;
    }

    /**
     * @return HomeTeam
     */
    public String getHometeam() {
        return hometeam;
    }

    /**
     * @param hometeam
     */
    public void setHometeam(String hometeam) {
        this.hometeam = hometeam;
    }

    /**
     * @return HomeTeamID
     */
    public Integer getHometeamid() {
        return hometeamid;
    }

    /**
     * @param hometeamid
     */
    public void setHometeamid(Integer hometeamid) {
        this.hometeamid = hometeamid;
    }

    /**
     * @return GuestTeam
     */
    public String getGuestteam() {
        return guestteam;
    }

    /**
     * @param guestteam
     */
    public void setGuestteam(String guestteam) {
        this.guestteam = guestteam;
    }

    /**
     * @return GuestTeamID
     */
    public Integer getGuestteamid() {
        return guestteamid;
    }

    /**
     * @param guestteamid
     */
    public void setGuestteamid(Integer guestteamid) {
        this.guestteamid = guestteamid;
    }

    /**
     * @return MatchState
     */
    public Short getMatchstate() {
        return matchstate;
    }

    /**
     * @param matchstate
     */
    public void setMatchstate(Short matchstate) {
        this.matchstate = matchstate;
    }

    /**
     * @return HomeScore
     */
    public Short getHomescore() {
        return homescore;
    }

    /**
     * @param homescore
     */
    public void setHomescore(Short homescore) {
        this.homescore = homescore;
    }

    /**
     * @return GuestScore
     */
    public Short getGuestscore() {
        return guestscore;
    }

    /**
     * @param guestscore
     */
    public void setGuestscore(Short guestscore) {
        this.guestscore = guestscore;
    }

    /**
     * @return HomeHalfScore
     */
    public Short getHomehalfscore() {
        return homehalfscore;
    }

    /**
     * @param homehalfscore
     */
    public void setHomehalfscore(Short homehalfscore) {
        this.homehalfscore = homehalfscore;
    }

    /**
     * @return GuestHalfScore
     */
    public Short getGuesthalfscore() {
        return guesthalfscore;
    }

    /**
     * @param guesthalfscore
     */
    public void setGuesthalfscore(Short guesthalfscore) {
        this.guesthalfscore = guesthalfscore;
    }

    /**
     * @return PolyGoal
     */
    public Float getPolygoal() {
        return polygoal;
    }

    /**
     * @param polygoal
     */
    public void setPolygoal(Float polygoal) {
        this.polygoal = polygoal;
    }

    public boolean isIsend() {
        return isend;
    }

    public void setIsend(boolean isend) {
        this.isend = isend;
    }

    public boolean isIsturned() {
        return isturned;
    }

    public void setIsturned(boolean isturned) {
        this.isturned = isturned;
    }

    /**
     * @return state
     */
    public Short getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(Short state) {
        this.state = state;
    }

    /**
     * @return HomeTeamF
     */
    public String getHometeamf() {
        return hometeamf;
    }

    /**
     * @param hometeamf
     */
    public void setHometeamf(String hometeamf) {
        this.hometeamf = hometeamf;
    }

    /**
     * @return GuestTeamF
     */
    public String getGuestteamf() {
        return guestteamf;
    }

    /**
     * @param guestteamf
     */
    public void setGuestteamf(String guestteamf) {
        this.guestteamf = guestteamf;
    }

    /**
     * @return IsAudit
     */
    public Byte getIsaudit() {
        return isaudit;
    }

    /**
     * @param isaudit
     */
    public void setIsaudit(Byte isaudit) {
        this.isaudit = isaudit;
    }

    /**
     * @return MatchTime2
     */
    public Date getMatchtime2() {
        return matchtime2;
    }

    /**
     * @param matchtime2
     */
    public void setMatchtime2(Date matchtime2) {
        this.matchtime2 = matchtime2;
    }

    /**
     * @return HomeTeamGov
     */
    public String getHometeamgov() {
        return hometeamgov;
    }

    /**
     * @param hometeamgov
     */
    public void setHometeamgov(String hometeamgov) {
        this.hometeamgov = hometeamgov;
    }

    /**
     * @return GuestTeamGov
     */
    public String getGuestteamgov() {
        return guestteamgov;
    }

    /**
     * @param guestteamgov
     */
    public void setGuestteamgov(String guestteamgov) {
        this.guestteamgov = guestteamgov;
    }

    /*public boolean isSingle101() {
        return single101;
    }

    public void setSingle101(boolean single101) {
        this.single101 = single101;
    }

    public boolean isSingle102() {
        return single102;
    }

    public void setSingle102(boolean single102) {
        this.single102 = single102;
    }

    public boolean isSingle103() {
        return single103;
    }

    public void setSingle103(boolean single103) {
        this.single103 = single103;
    }

    public boolean isSingle104() {
        return single104;
    }

    public void setSingle104(boolean single104) {
        this.single104 = single104;
    }

    public boolean isSingle105() {
        return single105;
    }

    public void setSingle105(boolean single105) {
        this.single105 = single105;
    }*/


    public Integer getSingle101() {
        return single101;
    }

    public void setSingle101(Integer single101) {
        this.single101 = single101;
    }

    public Integer getSingle102() {
        return single102;
    }

    public void setSingle102(Integer single102) {
        this.single102 = single102;
    }

    public Integer getSingle103() {
        return single103;
    }

    public void setSingle103(Integer single103) {
        this.single103 = single103;
    }

    public Integer getSingle104() {
        return single104;
    }

    public void setSingle104(Integer single104) {
        this.single104 = single104;
    }

    public Integer getSingle105() {
        return single105;
    }

    public void setSingle105(Integer single105) {
        this.single105 = single105;
    }

    /**
     * @return AddTime
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * @param addtime
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * @return SelloutTimeGov
     */
    public Date getSellouttimegov() {
        return sellouttimegov;
    }

    /**
     * @param sellouttimegov
     */
    public void setSellouttimegov(Date sellouttimegov) {
        this.sellouttimegov = sellouttimegov;
    }
}