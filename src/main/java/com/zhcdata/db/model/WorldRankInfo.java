package com.zhcdata.db.model;

public class WorldRankInfo {
    private String type;

    private String name;

    private Integer teamid;

    private String area;

    private Integer rank;

    private Integer chgrank;

    private Integer score;

    private Integer chgscore;

    private String update1;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getTeamid() {
        return teamid;
    }

    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getChgrank() {
        return chgrank;
    }

    public void setChgrank(Integer chgrank) {
        this.chgrank = chgrank;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getChgscore() {
        return chgscore;
    }

    public void setChgscore(Integer chgscore) {
        this.chgscore = chgscore;
    }

    public String getUpdate1() {
        return update1;
    }

    public void setUpdate1(String update) {
        this.update1 = update == null ? null : update.trim();
    }
}
