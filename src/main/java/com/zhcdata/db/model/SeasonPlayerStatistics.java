package com.zhcdata.db.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "season_player_statistics")
public class SeasonPlayerStatistics implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    /**
     * 球员ID
     */
    @Column(name = "player_id")
    private Long playerId;

    /**
     * 球队ID
     */
    @Column(name = "team_id")
    private Long teamId;

    /**
     * 主客场标识
     */
    @Column(name = "if_home")
    private Long ifHome;

    /**
     * 联赛ID
     */
    @Column(name = "league_id")
    private Long leagueId;

    /**
     * 赛季
     */
    @Column(name = "match_season")
    private String matchSeason;

    /**
     * 出场次数
     */
    @Column(name = "sch_sum")
    private Long schSum;

    /**
     * 替补次数
     */
    @Column(name = "back_sum")
    private Long backSum;

    /**
     * 上场时间
     */
    @Column(name = "play_time")
    private String playTime;

    /**
     * 进球数(不含点球)
     */
    private Long goals;

    /**
     * 射进点球数
     */
    @Column(name = "penalty_goals")
    private Long penaltyGoals;

    /**
     * 射门
     */
    private Long shots;

    /**
     * 射正
     */
    @Column(name = "shots_target")
    private Long shotsTarget;

    /**
     * 被犯规,被侵犯
     */
    @Column(name = "was_fouled")
    private Long wasFouled;

    /**
     * 越位
     */
    private Long offside;

    /**
     * 获得全场最佳次数
     */
    @Column(name = "best_sum")
    private Long bestSum;

    /**
     * 评分
     */
    private Float rating;

    /**
     * 传球数
     */
    @Column(name = "total_pass")
    private Long totalPass;

    /**
     * 传球成功数
     */
    @Column(name = "pass_suc")
    private Long passSuc;

    /**
     * 关键传球数
     */
    @Column(name = "key_pass")
    private Long keyPass;

    /**
     * 助攻
     */
    private Long assist;

    /**
     * 长传
     */
    @Column(name = "long_ball")
    private Long longBall;

    /**
     * 长传成功数
     */
    @Column(name = "long_ball_suc")
    private Long longBallSuc;

    /**
     * 直塞数
     */
    @Column(name = "through_ball")
    private Long throughBall;

    /**
     * 直塞成功数
     */
    @Column(name = "through_ball_suc")
    private Long throughBallSuc;

    /**
     * 带球摆脱
     */
    @Column(name = "dribbles_suc")
    private Long dribblesSuc;

    /**
     * 横传
     */
    @Column(name = "cross_num")
    private Long crossNum;

    /**
     * 横传成功数
     */
    @Column(name = "cross_suc")
    private Long crossSuc;

    /**
     * 铲断
     */
    private Long tackle;

    /**
     * 拦截
     */
    private Long interception;

    /**
     * 解围
     */
    private Long clearance;

    /**
     * 偷球
     */
    private Long dispossessed;

    /**
     * 封堵
     */
    @Column(name = "shots_blocked")
    private Long shotsBlocked;

    /**
     * 头球
     */
    @Column(name = "aertal_suc")
    private Long aertalSuc;

    /**
     * 犯规
     */
    private Long fouls;

    /**
     * 红牌
     */
    private Long red;

    /**
     * 黄牌
     */
    private Long yellow;

    /**
     * 失误
     */
    @Column(name = "turn_over")
    private Long turnOver;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    /**
     * 获取球员ID
     *
     * @return player_id - 球员ID
     */
    public Long getPlayerId() {
        return playerId;
    }

    /**
     * 设置球员ID
     *
     * @param playerId 球员ID
     */
    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    /**
     * 获取球队ID
     *
     * @return team_id - 球队ID
     */
    public Long getTeamId() {
        return teamId;
    }

    /**
     * 设置球队ID
     *
     * @param teamId 球队ID
     */
    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    /**
     * 获取主客场标识
     *
     * @return if_home - 主客场标识
     */
    public Long getIfHome() {
        return ifHome;
    }

    /**
     * 设置主客场标识
     *
     * @param ifHome 主客场标识
     */
    public void setIfHome(Long ifHome) {
        this.ifHome = ifHome;
    }

    /**
     * 获取联赛ID
     *
     * @return league_id - 联赛ID
     */
    public Long getLeagueId() {
        return leagueId;
    }

    /**
     * 设置联赛ID
     *
     * @param leagueId 联赛ID
     */
    public void setLeagueId(Long leagueId) {
        this.leagueId = leagueId;
    }

    /**
     * 获取赛季
     *
     * @return match_season - 赛季
     */
    public String getMatchSeason() {
        return matchSeason;
    }

    /**
     * 设置赛季
     *
     * @param matchSeason 赛季
     */
    public void setMatchSeason(String matchSeason) {
        this.matchSeason = matchSeason;
    }

    /**
     * 获取出场次数
     *
     * @return sch_sum - 出场次数
     */
    public Long getSchSum() {
        return schSum;
    }

    /**
     * 设置出场次数
     *
     * @param schSum 出场次数
     */
    public void setSchSum(Long schSum) {
        this.schSum = schSum;
    }

    /**
     * 获取替补次数
     *
     * @return back_sum - 替补次数
     */
    public Long getBackSum() {
        return backSum;
    }

    /**
     * 设置替补次数
     *
     * @param backSum 替补次数
     */
    public void setBackSum(Long backSum) {
        this.backSum = backSum;
    }

    /**
     * 获取上场时间
     *
     * @return play_time - 上场时间
     */
    public String getPlayTime() {
        return playTime;
    }

    /**
     * 设置上场时间
     *
     * @param playTime 上场时间
     */
    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    /**
     * 获取进球数(不含点球)
     *
     * @return goals - 进球数(不含点球)
     */
    public Long getGoals() {
        return goals;
    }

    /**
     * 设置进球数(不含点球)
     *
     * @param goals 进球数(不含点球)
     */
    public void setGoals(Long goals) {
        this.goals = goals;
    }

    /**
     * 获取射进点球数
     *
     * @return penalty_goals - 射进点球数
     */
    public Long getPenaltyGoals() {
        return penaltyGoals;
    }

    /**
     * 设置射进点球数
     *
     * @param penaltyGoals 射进点球数
     */
    public void setPenaltyGoals(Long penaltyGoals) {
        this.penaltyGoals = penaltyGoals;
    }

    /**
     * 获取射门
     *
     * @return shots - 射门
     */
    public Long getShots() {
        return shots;
    }

    /**
     * 设置射门
     *
     * @param shots 射门
     */
    public void setShots(Long shots) {
        this.shots = shots;
    }

    /**
     * 获取射正
     *
     * @return shots_target - 射正
     */
    public Long getShotsTarget() {
        return shotsTarget;
    }

    /**
     * 设置射正
     *
     * @param shotsTarget 射正
     */
    public void setShotsTarget(Long shotsTarget) {
        this.shotsTarget = shotsTarget;
    }

    /**
     * 获取被犯规,被侵犯
     *
     * @return was_fouled - 被犯规,被侵犯
     */
    public Long getWasFouled() {
        return wasFouled;
    }

    /**
     * 设置被犯规,被侵犯
     *
     * @param wasFouled 被犯规,被侵犯
     */
    public void setWasFouled(Long wasFouled) {
        this.wasFouled = wasFouled;
    }

    /**
     * 获取越位
     *
     * @return offside - 越位
     */
    public Long getOffside() {
        return offside;
    }

    /**
     * 设置越位
     *
     * @param offside 越位
     */
    public void setOffside(Long offside) {
        this.offside = offside;
    }

    /**
     * 获取获得全场最佳次数
     *
     * @return best_sum - 获得全场最佳次数
     */
    public Long getBestSum() {
        return bestSum;
    }

    /**
     * 设置获得全场最佳次数
     *
     * @param bestSum 获得全场最佳次数
     */
    public void setBestSum(Long bestSum) {
        this.bestSum = bestSum;
    }

    /**
     * 获取评分
     *
     * @return rating - 评分
     */
    public Float getRating() {
        return rating;
    }

    /**
     * 设置评分
     *
     * @param rating 评分
     */
    public void setRating(Float rating) {
        this.rating = rating;
    }

    /**
     * 获取传球数
     *
     * @return total_pass - 传球数
     */
    public Long getTotalPass() {
        return totalPass;
    }

    /**
     * 设置传球数
     *
     * @param totalPass 传球数
     */
    public void setTotalPass(Long totalPass) {
        this.totalPass = totalPass;
    }

    /**
     * 获取传球成功数
     *
     * @return pass_suc - 传球成功数
     */
    public Long getPassSuc() {
        return passSuc;
    }

    /**
     * 设置传球成功数
     *
     * @param passSuc 传球成功数
     */
    public void setPassSuc(Long passSuc) {
        this.passSuc = passSuc;
    }

    /**
     * 获取关键传球数
     *
     * @return key_pass - 关键传球数
     */
    public Long getKeyPass() {
        return keyPass;
    }

    /**
     * 设置关键传球数
     *
     * @param keyPass 关键传球数
     */
    public void setKeyPass(Long keyPass) {
        this.keyPass = keyPass;
    }

    /**
     * 获取助攻
     *
     * @return assist - 助攻
     */
    public Long getAssist() {
        return assist;
    }

    /**
     * 设置助攻
     *
     * @param assist 助攻
     */
    public void setAssist(Long assist) {
        this.assist = assist;
    }

    /**
     * 获取长传
     *
     * @return long_ball - 长传
     */
    public Long getLongBall() {
        return longBall;
    }

    /**
     * 设置长传
     *
     * @param longBall 长传
     */
    public void setLongBall(Long longBall) {
        this.longBall = longBall;
    }

    /**
     * 获取长传成功数
     *
     * @return long_ball_suc - 长传成功数
     */
    public Long getLongBallSuc() {
        return longBallSuc;
    }

    /**
     * 设置长传成功数
     *
     * @param longBallSuc 长传成功数
     */
    public void setLongBallSuc(Long longBallSuc) {
        this.longBallSuc = longBallSuc;
    }

    /**
     * 获取直塞数
     *
     * @return through_ball - 直塞数
     */
    public Long getThroughBall() {
        return throughBall;
    }

    /**
     * 设置直塞数
     *
     * @param throughBall 直塞数
     */
    public void setThroughBall(Long throughBall) {
        this.throughBall = throughBall;
    }

    /**
     * 获取直塞成功数
     *
     * @return through_ball_suc - 直塞成功数
     */
    public Long getThroughBallSuc() {
        return throughBallSuc;
    }

    /**
     * 设置直塞成功数
     *
     * @param throughBallSuc 直塞成功数
     */
    public void setThroughBallSuc(Long throughBallSuc) {
        this.throughBallSuc = throughBallSuc;
    }

    /**
     * 获取带球摆脱
     *
     * @return dribbles_suc - 带球摆脱
     */
    public Long getDribblesSuc() {
        return dribblesSuc;
    }

    /**
     * 设置带球摆脱
     *
     * @param dribblesSuc 带球摆脱
     */
    public void setDribblesSuc(Long dribblesSuc) {
        this.dribblesSuc = dribblesSuc;
    }

    /**
     * 获取横传
     *
     * @return cross_num - 横传
     */
    public Long getCrossNum() {
        return crossNum;
    }

    /**
     * 设置横传
     *
     * @param crossNum 横传
     */
    public void setCrossNum(Long crossNum) {
        this.crossNum = crossNum;
    }

    /**
     * 获取横传成功数
     *
     * @return cross_suc - 横传成功数
     */
    public Long getCrossSuc() {
        return crossSuc;
    }

    /**
     * 设置横传成功数
     *
     * @param crossSuc 横传成功数
     */
    public void setCrossSuc(Long crossSuc) {
        this.crossSuc = crossSuc;
    }

    /**
     * 获取铲断
     *
     * @return tackle - 铲断
     */
    public Long getTackle() {
        return tackle;
    }

    /**
     * 设置铲断
     *
     * @param tackle 铲断
     */
    public void setTackle(Long tackle) {
        this.tackle = tackle;
    }

    /**
     * 获取拦截
     *
     * @return interception - 拦截
     */
    public Long getInterception() {
        return interception;
    }

    /**
     * 设置拦截
     *
     * @param interception 拦截
     */
    public void setInterception(Long interception) {
        this.interception = interception;
    }

    /**
     * 获取解围
     *
     * @return clearance - 解围
     */
    public Long getClearance() {
        return clearance;
    }

    /**
     * 设置解围
     *
     * @param clearance 解围
     */
    public void setClearance(Long clearance) {
        this.clearance = clearance;
    }

    /**
     * 获取偷球
     *
     * @return dispossessed - 偷球
     */
    public Long getDispossessed() {
        return dispossessed;
    }

    /**
     * 设置偷球
     *
     * @param dispossessed 偷球
     */
    public void setDispossessed(Long dispossessed) {
        this.dispossessed = dispossessed;
    }

    /**
     * 获取封堵
     *
     * @return shots_blocked - 封堵
     */
    public Long getShotsBlocked() {
        return shotsBlocked;
    }

    /**
     * 设置封堵
     *
     * @param shotsBlocked 封堵
     */
    public void setShotsBlocked(Long shotsBlocked) {
        this.shotsBlocked = shotsBlocked;
    }

    /**
     * 获取头球
     *
     * @return aertal_suc - 头球
     */
    public Long getAertalSuc() {
        return aertalSuc;
    }

    /**
     * 设置头球
     *
     * @param aertalSuc 头球
     */
    public void setAertalSuc(Long aertalSuc) {
        this.aertalSuc = aertalSuc;
    }

    /**
     * 获取犯规
     *
     * @return fouls - 犯规
     */
    public Long getFouls() {
        return fouls;
    }

    /**
     * 设置犯规
     *
     * @param fouls 犯规
     */
    public void setFouls(Long fouls) {
        this.fouls = fouls;
    }

    /**
     * 获取红牌
     *
     * @return red - 红牌
     */
    public Long getRed() {
        return red;
    }

    /**
     * 设置红牌
     *
     * @param red 红牌
     */
    public void setRed(Long red) {
        this.red = red;
    }

    /**
     * 获取黄牌
     *
     * @return yellow - 黄牌
     */
    public Long getYellow() {
        return yellow;
    }

    /**
     * 设置黄牌
     *
     * @param yellow 黄牌
     */
    public void setYellow(Long yellow) {
        this.yellow = yellow;
    }

    /**
     * 获取失误
     *
     * @return turn_over - 失误
     */
    public Long getTurnOver() {
        return turnOver;
    }

    /**
     * 设置失误
     *
     * @param turnOver 失误
     */
    public void setTurnOver(Long turnOver) {
        this.turnOver = turnOver;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}