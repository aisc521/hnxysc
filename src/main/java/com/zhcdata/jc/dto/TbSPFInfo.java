package com.zhcdata.jc.dto;

public class TbSPFInfo {
    private String matchId;
    private String gameId;
    private String levelRate;
    private String loseRate;
    private String winRate;
    private String type; //1 胜平负 2让球胜平负
    private String concedeNum;
    private String smalltype;

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getLevelRate() {
        return levelRate;
    }

    public void setLevelRate(String levelRate) {
        this.levelRate = levelRate;
    }

    public String getLoseRate() {
        return loseRate;
    }

    public void setLoseRate(String loseRate) {
        this.loseRate = loseRate;
    }

    public String getWinRate() {
        return winRate;
    }

    public void setWinRate(String winRate) {
        this.winRate = winRate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConcedeNum() {
        return concedeNum;
    }

    public void setConcedeNum(String concedeNum) {
        this.concedeNum = concedeNum;
    }

    public String getSmalltype() {
        return smalltype;
    }

    public void setSmalltype(String smalltype) {
        this.smalltype = smalltype;
    }
}
