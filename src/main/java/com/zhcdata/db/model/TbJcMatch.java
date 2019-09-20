package com.zhcdata.db.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_jc_match")
public class TbJcMatch implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 方案id
     */
    @Column(name = "plan_id")
    private Long planId;

    /**
     * 赛事id
     */
    @Column(name = "match_id")
    private Long matchId;

    /**
     * 联赛名称
     */
    @Column(name = "match_name")
    private String matchName;

    /**
     * 赛事num
     */
    @Column(name = "match_num")
    private String matchNum;

    /**
     * 开赛时间
     */
    @Column(name = "date_of_match")
    private String dateOfMatch;

    /**
     * 主队名称
     */
    @Column(name = "home_team")
    private String homeTeam;

    /**
     * 客队名称
     */
    @Column(name = "visiting_team")
    private String visitingTeam;

    /**
     * 购买的赔率信息
     */
    @Column(name = "planInfo")
    private String planinfo;

    /**
     * 赛果
     */
    @Column(name = "matchResult")
    private String matchresult;

    /**
     * 是否中奖 0未中 1中
     */
    private Integer status;

    @Column(name = "createTime")
    private Date createtime;

    private static final long serialVersionUID = 1L;

    /**
     * 获取id
     *
     * @return id - id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取方案id
     *
     * @return plan_id - 方案id
     */
    public Long getPlanId() {
        return planId;
    }

    /**
     * 设置方案id
     *
     * @param planId 方案id
     */
    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    /**
     * 获取赛事id
     *
     * @return match_id - 赛事id
     */
    public Long getMatchId() {
        return matchId;
    }

    /**
     * 设置赛事id
     *
     * @param matchId 赛事id
     */
    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    /**
     * 获取联赛名称
     *
     * @return match_name - 联赛名称
     */
    public String getMatchName() {
        return matchName;
    }

    /**
     * 设置联赛名称
     *
     * @param matchName 联赛名称
     */
    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    /**
     * 获取赛事num
     *
     * @return match_num - 赛事num
     */
    public String getMatchNum() {
        return matchNum;
    }

    /**
     * 设置赛事num
     *
     * @param matchNum 赛事num
     */
    public void setMatchNum(String matchNum) {
        this.matchNum = matchNum;
    }

    /**
     * 获取开赛时间
     *
     * @return date_of_match - 开赛时间
     */
    public String getDateOfMatch() {
        return dateOfMatch;
    }

    /**
     * 设置开赛时间
     *
     * @param dateOfMatch 开赛时间
     */
    public void setDateOfMatch(String dateOfMatch) {
        this.dateOfMatch = dateOfMatch;
    }

    /**
     * 获取主队名称
     *
     * @return home_team - 主队名称
     */
    public String getHomeTeam() {
        return homeTeam;
    }

    /**
     * 设置主队名称
     *
     * @param homeTeam 主队名称
     */
    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    /**
     * 获取客队名称
     *
     * @return visiting_team - 客队名称
     */
    public String getVisitingTeam() {
        return visitingTeam;
    }

    /**
     * 设置客队名称
     *
     * @param visitingTeam 客队名称
     */
    public void setVisitingTeam(String visitingTeam) {
        this.visitingTeam = visitingTeam;
    }

    /**
     * 获取购买的赔率信息
     *
     * @return planInfo - 购买的赔率信息
     */
    public String getPlaninfo() {
        return planinfo;
    }

    /**
     * 设置购买的赔率信息
     *
     * @param planinfo 购买的赔率信息
     */
    public void setPlaninfo(String planinfo) {
        this.planinfo = planinfo;
    }

    /**
     * 获取赛果
     *
     * @return matchResult - 赛果
     */
    public String getMatchresult() {
        return matchresult;
    }

    /**
     * 设置赛果
     *
     * @param matchresult 赛果
     */
    public void setMatchresult(String matchresult) {
        this.matchresult = matchresult;
    }

    /**
     * 获取是否中奖 0未中 1中
     *
     * @return status - 是否中奖 0未中 1中
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置是否中奖 0未中 1中
     *
     * @param status 是否中奖 0未中 1中
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return createTime
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}