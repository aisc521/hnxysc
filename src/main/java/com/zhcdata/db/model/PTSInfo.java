package com.zhcdata.db.model;

public class PTSInfo {
    private Integer id;

    private Integer scheduleid;

    private Integer teamid;

    private Integer playerid;

    private String playername;

    private Float rating;

    private Integer shots;

    private Integer shotstarget;

    private Integer keypass;

    private Integer totalpass;

    private Integer accuratepass;

    private Integer aerialwon;

    private Integer touches;

    private Integer tackles;

    private Integer interception;

    private Integer clearances;

    private Integer clearancewon;

    private Integer shotsblocked;

    private Integer offsideprovoked;

    private Integer fouls;

    private Integer dribbleswon;

    private Integer wasfouled;

    private Integer dispossessed;

    private Integer turnover;

    private Integer offsides;

    private Integer crossnum;

    private Integer crosswon;

    private Integer longballs;

    private Integer longballswon;

    private Integer throughball;

    private Integer throughballwon;

    private Integer penaltyprovoked;

    private Integer penaltytotal;

    private Integer penaltygoals;

    private Integer notpenaltygoals;

    private Integer assist;

    private Integer secondYellow;

    private Integer yellow;

    private Integer red;

    private Integer shotonpost;

    private Integer clearanceoffline;

    private Integer penaltysave;

    private Integer errorleadtogoal;

    private Integer lastmantackle;

    private Integer lastmancontest;

    private Integer owngoals;

    private Boolean isbest;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScheduleid() {
        return scheduleid;
    }

    public void setScheduleid(Integer scheduleid) {
        this.scheduleid = scheduleid;
    }

    public Integer getTeamid() {
        return teamid;
    }

    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
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

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Integer getShots() {
        return shots;
    }

    public void setShots(Integer shots) {
        this.shots = shots;
    }

    public Integer getShotstarget() {
        return shotstarget;
    }

    public void setShotstarget(Integer shotstarget) {
        this.shotstarget = shotstarget;
    }

    public Integer getKeypass() {
        return keypass;
    }

    public void setKeypass(Integer keypass) {
        this.keypass = keypass;
    }

    public Integer getTotalpass() {
        return totalpass;
    }

    public void setTotalpass(Integer totalpass) {
        this.totalpass = totalpass;
    }

    public Integer getAccuratepass() {
        return accuratepass;
    }

    public void setAccuratepass(Integer accuratepass) {
        this.accuratepass = accuratepass;
    }

    public Integer getAerialwon() {
        return aerialwon;
    }

    public void setAerialwon(Integer aerialwon) {
        this.aerialwon = aerialwon;
    }

    public Integer getTouches() {
        return touches;
    }

    public void setTouches(Integer touches) {
        this.touches = touches;
    }

    public Integer getTackles() {
        return tackles;
    }

    public void setTackles(Integer tackles) {
        this.tackles = tackles;
    }

    public Integer getInterception() {
        return interception;
    }

    public void setInterception(Integer interception) {
        this.interception = interception;
    }

    public Integer getClearances() {
        return clearances;
    }

    public void setClearances(Integer clearances) {
        this.clearances = clearances;
    }

    public Integer getClearancewon() {
        return clearancewon;
    }

    public void setClearancewon(Integer clearancewon) {
        this.clearancewon = clearancewon;
    }

    public Integer getShotsblocked() {
        return shotsblocked;
    }

    public void setShotsblocked(Integer shotsblocked) {
        this.shotsblocked = shotsblocked;
    }

    public Integer getOffsideprovoked() {
        return offsideprovoked;
    }

    public void setOffsideprovoked(Integer offsideprovoked) {
        this.offsideprovoked = offsideprovoked;
    }

    public Integer getFouls() {
        return fouls;
    }

    public void setFouls(Integer fouls) {
        this.fouls = fouls;
    }

    public Integer getDribbleswon() {
        return dribbleswon;
    }

    public void setDribbleswon(Integer dribbleswon) {
        this.dribbleswon = dribbleswon;
    }

    public Integer getWasfouled() {
        return wasfouled;
    }

    public void setWasfouled(Integer wasfouled) {
        this.wasfouled = wasfouled;
    }

    public Integer getDispossessed() {
        return dispossessed;
    }

    public void setDispossessed(Integer dispossessed) {
        this.dispossessed = dispossessed;
    }

    public Integer getTurnover() {
        return turnover;
    }

    public void setTurnover(Integer turnover) {
        this.turnover = turnover;
    }

    public Integer getOffsides() {
        return offsides;
    }

    public void setOffsides(Integer offsides) {
        this.offsides = offsides;
    }

    public Integer getCrossnum() {
        return crossnum;
    }

    public void setCrossnum(Integer crossnum) {
        this.crossnum = crossnum;
    }

    public Integer getCrosswon() {
        return crosswon;
    }

    public void setCrosswon(Integer crosswon) {
        this.crosswon = crosswon;
    }

    public Integer getLongballs() {
        return longballs;
    }

    public void setLongballs(Integer longballs) {
        this.longballs = longballs;
    }

    public Integer getLongballswon() {
        return longballswon;
    }

    public void setLongballswon(Integer longballswon) {
        this.longballswon = longballswon;
    }

    public Integer getThroughball() {
        return throughball;
    }

    public void setThroughball(Integer throughball) {
        this.throughball = throughball;
    }

    public Integer getThroughballwon() {
        return throughballwon;
    }

    public void setThroughballwon(Integer throughballwon) {
        this.throughballwon = throughballwon;
    }

    public Integer getPenaltyprovoked() {
        return penaltyprovoked;
    }

    public void setPenaltyprovoked(Integer penaltyprovoked) {
        this.penaltyprovoked = penaltyprovoked;
    }

    public Integer getPenaltytotal() {
        return penaltytotal;
    }

    public void setPenaltytotal(Integer penaltytotal) {
        this.penaltytotal = penaltytotal;
    }

    public Integer getPenaltygoals() {
        return penaltygoals;
    }

    public void setPenaltygoals(Integer penaltygoals) {
        this.penaltygoals = penaltygoals;
    }

    public Integer getNotpenaltygoals() {
        return notpenaltygoals;
    }

    public void setNotpenaltygoals(Integer notpenaltygoals) {
        this.notpenaltygoals = notpenaltygoals;
    }

    public Integer getAssist() {
        return assist;
    }

    public void setAssist(Integer assist) {
        this.assist = assist;
    }

    public Integer getSecondYellow() {
        return secondYellow;
    }

    public void setSecondYellow(Integer secondYellow) {
        this.secondYellow = secondYellow;
    }

    public Integer getYellow() {
        return yellow;
    }

    public void setYellow(Integer yellow) {
        this.yellow = yellow;
    }

    public Integer getRed() {
        return red;
    }

    public void setRed(Integer red) {
        this.red = red;
    }

    public Integer getShotonpost() {
        return shotonpost;
    }

    public void setShotonpost(Integer shotonpost) {
        this.shotonpost = shotonpost;
    }

    public Integer getClearanceoffline() {
        return clearanceoffline;
    }

    public void setClearanceoffline(Integer clearanceoffline) {
        this.clearanceoffline = clearanceoffline;
    }

    public Integer getPenaltysave() {
        return penaltysave;
    }

    public void setPenaltysave(Integer penaltysave) {
        this.penaltysave = penaltysave;
    }

    public Integer getErrorleadtogoal() {
        return errorleadtogoal;
    }

    public void setErrorleadtogoal(Integer errorleadtogoal) {
        this.errorleadtogoal = errorleadtogoal;
    }

    public Integer getLastmantackle() {
        return lastmantackle;
    }

    public void setLastmantackle(Integer lastmantackle) {
        this.lastmantackle = lastmantackle;
    }

    public Integer getLastmancontest() {
        return lastmancontest;
    }

    public void setLastmancontest(Integer lastmancontest) {
        this.lastmancontest = lastmancontest;
    }

    public Integer getOwngoals() {
        return owngoals;
    }

    public void setOwngoals(Integer owngoals) {
        this.owngoals = owngoals;
    }

    public Boolean getIsbest() {
        return isbest;
    }

    public void setIsbest(Boolean isbest) {
        this.isbest = isbest;
    }
}
