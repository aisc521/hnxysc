package com.zhcdata.db.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "jc_match_bjdc")
public class JcMatchBjdc implements Serializable {
    @Column(name = "ID")
    private Long id;

    @Column(name = "LOTTERY_NAME")
    private String lotteryName;

    @Column(name = "LOTTERY")
    private String lottery;

    @Column(name = "ISSUE_NUM")
    private String issueNum;

    @Column(name = "NO_ID")
    private String noId;

    @Column(name = "ID_BET007")
    private Long idBet007;

    @Column(name = "START_TIME")
    private Date startTime;

    @Column(name = "SPORT")
    private String sport;

    @Column(name = "HOME")
    private String home;

    @Column(name = "AWAY")
    private String away;

    @Column(name = "HOME_ID")
    private Long homeId;

    @Column(name = "AWAY_ID")
    private Long awayId;

    @Column(name = "TURN")
    private String turn;

    @Column(name = "lEAGUE")
    private String league;

    @Column(name = "RECORD_ID")
    private Long recordId;

    private static final long serialVersionUID = 1L;

    /**
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return LOTTERY_NAME
     */
    public String getLotteryName() {
        return lotteryName;
    }

    /**
     * @param lotteryName
     */
    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    /**
     * @return LOTTERY
     */
    public String getLottery() {
        return lottery;
    }

    /**
     * @param lottery
     */
    public void setLottery(String lottery) {
        this.lottery = lottery;
    }

    /**
     * @return ISSUE_NUM
     */
    public String getIssueNum() {
        return issueNum;
    }

    /**
     * @param issueNum
     */
    public void setIssueNum(String issueNum) {
        this.issueNum = issueNum;
    }

    /**
     * @return NO_ID
     */
    public String getNoId() {
        return noId;
    }

    /**
     * @param noId
     */
    public void setNoId(String noId) {
        this.noId = noId;
    }

    /**
     * @return ID_BET007
     */
    public Long getIdBet007() {
        return idBet007;
    }

    /**
     * @param idBet007
     */
    public void setIdBet007(Long idBet007) {
        this.idBet007 = idBet007;
    }

    /**
     * @return START_TIME
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * @return SPORT
     */
    public String getSport() {
        return sport;
    }

    /**
     * @param sport
     */
    public void setSport(String sport) {
        this.sport = sport;
    }

    /**
     * @return HOME
     */
    public String getHome() {
        return home;
    }

    /**
     * @param home
     */
    public void setHome(String home) {
        this.home = home;
    }

    /**
     * @return AWAY
     */
    public String getAway() {
        return away;
    }

    /**
     * @param away
     */
    public void setAway(String away) {
        this.away = away;
    }

    /**
     * @return HOME_ID
     */
    public Long getHomeId() {
        return homeId;
    }

    /**
     * @param homeId
     */
    public void setHomeId(Long homeId) {
        this.homeId = homeId;
    }

    /**
     * @return AWAY_ID
     */
    public Long getAwayId() {
        return awayId;
    }

    /**
     * @param awayId
     */
    public void setAwayId(Long awayId) {
        this.awayId = awayId;
    }

    /**
     * @return TURN
     */
    public String getTurn() {
        return turn;
    }

    /**
     * @param turn
     */
    public void setTurn(String turn) {
        this.turn = turn;
    }

    /**
     * @return lEAGUE
     */
    public String getLeague() {
        return league;
    }

    /**
     * @param league
     */
    public void setLeague(String league) {
        this.league = league;
    }

    /**
     * @return RECORD_ID
     */
    public Long getRecordId() {
        return recordId;
    }

    /**
     * @param recordId
     */
    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }
}