package com.zhcdata.jc.dto;

public class IntegralRankingDto {
    private String matchCount;//总场次
    private String winCount;//胜场次
    private String flatCount;//平场次
    private String loseCount;//负场次
    private String goal;//得
    private String lost;//失
    private String goalDiff;//净
    private String score;//得分
    private String rank;//排行

    public IntegralRankingDto() {
        this.matchCount = "0";
        this.winCount = "0";
        this.flatCount = "0";
        this.loseCount = "0";
        this.goal = "0";
        this.lost = "0";
        this.goalDiff = "0";
        this.score = "0";
        this.rank = "0";
    }

    public String getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(String matchCount) {
        this.matchCount = matchCount;
    }

    public String getWinCount() {
        return winCount;
    }

    public void setWinCount(String winCount) {
        this.winCount = winCount;
    }

    public String getFlatCount() {
        return flatCount;
    }

    public void setFlatCount(String flatCount) {
        this.flatCount = flatCount;
    }

    public String getLoseCount() {
        return loseCount;
    }

    public void setLoseCount(String loseCount) {
        this.loseCount = loseCount;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getLost() {
        return lost;
    }

    public void setLost(String lost) {
        this.lost = lost;
    }

    public String getGoalDiff() {
        return goalDiff;
    }

    public void setGoalDiff(String goalDiff) {
        this.goalDiff = goalDiff;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
