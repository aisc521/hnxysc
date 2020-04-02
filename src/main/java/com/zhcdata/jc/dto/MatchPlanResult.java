package com.zhcdata.jc.dto;

public class MatchPlanResult {
    private String matchId;
    private String matchNum;
    private String matchName;
    private String hostTeam;
    private String visitTeam;
    private String planInfo;
    private String dateOfMatch;
    private String isWin;
    private String id;
    private String homeScore;
    private String guestScore;
    private String polyGoal;

    private String matchState;

    private String matchPlanType;
    private String odds;


    public String getMatchState() {
        return matchState;
    }

    public void setMatchState(String matchState) {
        this.matchState = matchState;
    }


    public String getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(String homeScore) {
        this.homeScore = homeScore;
    }

    public String getGuestScore() {
        return guestScore;
    }

    public void setGuestScore(String guestScore) {
        this.guestScore = guestScore;
    }

    public String getPolyGoal() {
        return polyGoal;
    }

    public void setPolyGoal(String polyGoal) {
        this.polyGoal = polyGoal;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsWin() {
        return isWin;
    }

    public void setIsWin(String isWin) {
        this.isWin = isWin;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getMatchNum() {
        return matchNum;
    }

    public void setMatchNum(String matchNum) {
        this.matchNum = matchNum;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getHostTeam() {
        return hostTeam;
    }

    public void setHostTeam(String hostTeam) {
        this.hostTeam = hostTeam;
    }

    public String getVisitTeam() {
        return visitTeam;
    }

    public void setVisitTeam(String visitTeam) {
        this.visitTeam = visitTeam;
    }

    public String getPlanInfo() {
        return planInfo;
    }

    public void setPlanInfo(String planInfo) {
        this.planInfo = planInfo;
    }

    public String getDateOfMatch() {
        return dateOfMatch;
    }

    public void setDateOfMatch(String dateOfMatch) {
        this.dateOfMatch = dateOfMatch;
    }

    public String getMatchPlanType() { return matchPlanType; }

    public void setMatchPlanType(String matchPlanType) { this.matchPlanType = matchPlanType; }

    public String getOdds() { return odds; }

    public void setOdds(String odds) { this.odds = odds; }
}
