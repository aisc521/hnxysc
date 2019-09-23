package com.zhcdata.db.model;

import java.util.Objects;

public class PlayerInTeamInfo {
    private Integer id;

    private Integer playerid;

    private String playername;

    private Integer teamid;

    private String teamname;

    private String place;

    private String number;

    private Short score;

    private String modifytime;

    private Integer coachtypeid;

    private Integer captaintypeid;

    private Integer playerpositionid;

    private String starttime;

    private String endtime;

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

    public String getPlayername() {
        return playername;
    }

    public void setPlayername(String playername) {
        this.playername = playername == null ? null : playername.trim();
    }

    public Integer getTeamid() {
        return teamid;
    }

    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname == null ? null : teamname.trim();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public Short getScore() {
        return score;
    }

    public void setScore(Short score) {
        this.score = score;
    }

    public String getModifytime() {
        return modifytime;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
    }

    public Integer getCoachtypeid() {
        return coachtypeid;
    }

    public void setCoachtypeid(Integer coachtypeid) {
        this.coachtypeid = coachtypeid;
    }

    public Integer getCaptaintypeid() {
        return captaintypeid;
    }

    public void setCaptaintypeid(Integer captaintypeid) {
        this.captaintypeid = captaintypeid;
    }

    public Integer getPlayerpositionid() {
        return playerpositionid;
    }

    public void setPlayerpositionid(Integer playerpositionid) {
        this.playerpositionid = playerpositionid;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerInTeamInfo that = (PlayerInTeamInfo) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(playerid, that.playerid) &&
                Objects.equals(playername, that.playername) &&
                Objects.equals(teamid, that.teamid) &&
                Objects.equals(place, that.place) &&
                Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, playerid, playername, teamid, place, number);
    }
}
