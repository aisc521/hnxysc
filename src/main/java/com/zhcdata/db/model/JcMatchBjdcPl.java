package com.zhcdata.db.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "jc_match_bjdc_pl")
public class JcMatchBjdcPl implements Serializable {
    /**
     * 主键
     */
    @Column(name = "ID")
    private Long id;

    /**
     * 【期号】用于匹配彩票赛事场次/赔率，竞彩为空
     */
    @Column(name = "ISSUE_NUM")
    private String issueNum;

    /**
     * 【场次】用于匹配彩票赛事场次/赔率
     */
    @Column(name = "NO_ID")
    private String noId;

    /**
     * 玩法名称
            11-胜平负
            12-比分
            13-总进球
            14-半全场胜平负
            15-让球胜平负
     */
    @Column(name = "LOTTERY_NAME")
    private String lotteryName;

    /**
     * 玩法类型
            11-胜平负
            12-比分
            13-总进球
            14-半全场胜平负
            15-让球胜平负
     */
    @Column(name = "LOTTERY_PLAY")
    private String lotteryPlay;

    /**
     * 【球探网比赛ID】用于匹配球探网比赛数据
     */
    @Column(name = "ID_BET007")
    private Long idBet007;

    /**
     * 玩法类型0过关，1单场和串关
     */
    @Column(name = "PLAY_TYPE")
    private Integer playType;

    /**
     * 时时变更赔率
            胜:1.0,平:2.0,负:3.0
     */
    @Column(name = "RATE_RESULT")
    private String rateResult;

    /**
     * 状态 0正常 
     */
    @Column(name = "STATUS")
    private Integer status;

    /**
     * 比赛结果 玩法:赛果:赔率;
     */
    @Column(name = "MATCH_RESULT")
    private String matchResult;

    /**
     * 更新时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 让球数
     */

    @Column(name = "CONCED_NUM")
    private String conCedNum;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键
     *
     * @return ID - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取【期号】用于匹配彩票赛事场次/赔率，竞彩为空
     *
     * @return ISSUE_NUM - 【期号】用于匹配彩票赛事场次/赔率，竞彩为空
     */
    public String getIssueNum() {
        return issueNum;
    }

    /**
     * 设置【期号】用于匹配彩票赛事场次/赔率，竞彩为空
     *
     * @param issueNum 【期号】用于匹配彩票赛事场次/赔率，竞彩为空
     */
    public void setIssueNum(String issueNum) {
        this.issueNum = issueNum;
    }

    /**
     * 获取【场次】用于匹配彩票赛事场次/赔率
     *
     * @return NO_ID - 【场次】用于匹配彩票赛事场次/赔率
     */
    public String getNoId() {
        return noId;
    }

    /**
     * 设置【场次】用于匹配彩票赛事场次/赔率
     *
     * @param noId 【场次】用于匹配彩票赛事场次/赔率
     */
    public void setNoId(String noId) {
        this.noId = noId;
    }

    /**
     * 获取玩法名称
            11-胜平负
            12-比分
            13-总进球
            14-半全场胜平负
            15-让球胜平负
     *
     * @return LOTTERY_NAME - 玩法名称
            11-胜平负
            12-比分
            13-总进球
            14-半全场胜平负
            15-让球胜平负
     */
    public String getLotteryName() {
        return lotteryName;
    }

    /**
     * 设置玩法名称
            11-胜平负
            12-比分
            13-总进球
            14-半全场胜平负
            15-让球胜平负
     *
     * @param lotteryName 玩法名称
            11-胜平负
            12-比分
            13-总进球
            14-半全场胜平负
            15-让球胜平负
     */
    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    /**
     * 获取玩法类型
            11-胜平负
            12-比分
            13-总进球
            14-半全场胜平负
            15-让球胜平负
     *
     * @return LOTTERY_PLAY - 玩法类型
            11-胜平负
            12-比分
            13-总进球
            14-半全场胜平负
            15-让球胜平负
     */
    public String getLotteryPlay() {
        return lotteryPlay;
    }

    /**
     * 设置玩法类型
            11-胜平负
            12-比分
            13-总进球
            14-半全场胜平负
            15-让球胜平负
     *
     * @param lotteryPlay 玩法类型
            11-胜平负
            12-比分
            13-总进球
            14-半全场胜平负
            15-让球胜平负
     */
    public void setLotteryPlay(String lotteryPlay) {
        this.lotteryPlay = lotteryPlay;
    }

    /**
     * 获取【球探网比赛ID】用于匹配球探网比赛数据
     *
     * @return ID_BET007 - 【球探网比赛ID】用于匹配球探网比赛数据
     */
    public Long getIdBet007() {
        return idBet007;
    }

    /**
     * 设置【球探网比赛ID】用于匹配球探网比赛数据
     *
     * @param idBet007 【球探网比赛ID】用于匹配球探网比赛数据
     */
    public void setIdBet007(Long idBet007) {
        this.idBet007 = idBet007;
    }

    /**
     * 获取玩法类型0过关，1单场和串关
     *
     * @return PLAY_TYPE - 玩法类型0过关，1单场和串关
     */
    public Integer getPlayType() {
        return playType;
    }

    /**
     * 设置玩法类型0过关，1单场和串关
     *
     * @param playType 玩法类型0过关，1单场和串关
     */
    public void setPlayType(Integer playType) {
        this.playType = playType;
    }

    /**
     * 获取时时变更赔率
            胜:1.0,平:2.0,负:3.0
     *
     * @return RATE_RESULT - 时时变更赔率
            胜:1.0,平:2.0,负:3.0
     */
    public String getRateResult() {
        return rateResult;
    }

    /**
     * 设置时时变更赔率
            胜:1.0,平:2.0,负:3.0
     *
     * @param rateResult 时时变更赔率
            胜:1.0,平:2.0,负:3.0
     */
    public void setRateResult(String rateResult) {
        this.rateResult = rateResult;
    }

    /**
     * 获取状态 0正常 
     *
     * @return STATUS - 状态 0正常 
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 0正常 
     *
     * @param status 状态 0正常 
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取比赛结果 玩法:赛果:赔率;
     *
     * @return MATCH_RESULT - 比赛结果 玩法:赛果:赔率;
     */
    public String getMatchResult() {
        return matchResult;
    }

    /**
     * 设置比赛结果 玩法:赛果:赔率;
     *
     * @param matchResult 比赛结果 玩法:赛果:赔率;
     */
    public void setMatchResult(String matchResult) {
        this.matchResult = matchResult;
    }

    /**
     * 获取更新时间
     *
     * @return UPDATE_TIME - 更新时间
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
     * @return CREATE_TIME - 创建时间
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

    public String getConCedNum() {
        return conCedNum;
    }

    public void setConCedNum(String conCedNum) {
        this.conCedNum = conCedNum;
    }
}