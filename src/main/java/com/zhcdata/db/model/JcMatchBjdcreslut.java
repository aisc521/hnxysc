package com.zhcdata.db.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "jc_match_bjdcreslut")
public class JcMatchBjdcreslut implements Serializable {
    @Column(name = "ID")
    private Long id;

    /**
     * 【球探网比赛ID】用于匹配球探网比赛数据
     */
    @Column(name = "ID_BET007")
    private Long idBet007;

    /**
     * 开奖时间
     */
    @Column(name = "AWARD_TIME")
    private Date awardTime;

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

    public String getConCedNum() {
        return conCedNum;
    }

    public void setConCedNum(String conCedNum) {
        this.conCedNum = conCedNum;
    }

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
     * 获取开奖时间
     *
     * @return AWARD_TIME - 开奖时间
     */
    public Date getAwardTime() {
        return awardTime;
    }

    /**
     * 设置开奖时间
     *
     * @param awardTime 开奖时间
     */
    public void setAwardTime(Date awardTime) {
        this.awardTime = awardTime;
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
}