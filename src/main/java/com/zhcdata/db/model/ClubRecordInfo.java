package com.zhcdata.db.model;

import java.util.Date;

public class ClubRecordInfo {
    private Integer id;

    private Integer playerid;

    private Date transfertime;

    private Date endtime;

    private String team;

    private String teamnow;

    private Integer teamid;

    private Integer teamnowid;

    private Long money;

    private String place;

    private String zhSeason;

    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlayerid() {
        return playerid;
    }

    public void setPlayerid(Integer playerid) {
        this.playerid = playerid;
    }

    public Date getTransfertime() {
        return transfertime;
    }

    public void setTransfertime(Date transfertime) {
        this.transfertime = transfertime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team == null ? null : team.trim();
    }

    public String getTeamnow() {
        return teamnow;
    }

    public void setTeamnow(String teamnow) {
        this.teamnow = teamnow == null ? null : teamnow.trim();
    }

    public Integer getTeamid() {
        return teamid;
    }

    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
    }

    public Integer getTeamnowid() {
        return teamnowid;
    }

    public void setTeamnowid(Integer teamnowid) {
        this.teamnowid = teamnowid;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public String getZhSeason() {
        return zhSeason;
    }

    public void setZhSeason(String zhSeason) {
        this.zhSeason = zhSeason == null ? null : zhSeason.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}
