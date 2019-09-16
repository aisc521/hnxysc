package com.zhcdata.db.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "jc_schedule")
public class JcSchedule implements Serializable {
    /**
     * 
赛程id，唯一标识
     */
    @Column(name = "ID")
    private Integer id;

    /**
     * 球探赛程id
     */
    @Column(name = "ScheduleID")
    private Integer scheduleid;

    /**
     * 在当期中的序号，周三001
     */
    @Column(name = "MatchID")
    private String matchid;

    /**
     * 比赛时间
     */
    @Column(name = "MatchTime")
    private Date matchtime;

    /**
     * 停售时间
     */
    @Column(name = "SelloutTime")
    private Date sellouttime;

    /**
     * 赛事名
     */
    @Column(name = "Sclass")
    private String sclass;

    /**
     * 主场球队名
     */
    @Column(name = "HomeTeam")
    private String hometeam;

    /**
     * 主场球队ID
     */
    @Column(name = "HomeTeamID")
    private Integer hometeamid;

    /**
     * 客队名
     */
    @Column(name = "GuestTeam")
    private String guestteam;

    /**
     * 客队id
     */
    @Column(name = "GuestTeamID")
    private Integer guestteamid;

    /**
     * 主场球队名 繁体名
     */
    @Column(name = "HomeTeamF")
    private String hometeamf;

    /**
     * 客队名 繁体名
     */
    @Column(name = "GuestTeamF")
    private String guestteamf;

    /**
     * 状态：未=0,上=1,中=2下=3,待定=-11,腰斩= -12中断=-13,推迟=-14取消=-99,完场=-1
     */
    @Column(name = "MatchState")
    private Short matchstate;

    /**
     * 主队入球
     */
    @Column(name = "HomeScore")
    private Short homescore;

    /**
     * 客队入球
     */
    @Column(name = "GuestScore")
    private Short guestscore;

    /**
     * 主队半场入球
     */
    @Column(name = "HomeHalfScore")
    private Short homehalfscore;

    /**
     * 客队半场入球
     */
    @Column(name = "GuestHalfScore")
    private Short guesthalfscore;

    /**
     * 单场让球
     */
    @Column(name = "PolyGoal")
    private Float polygoal;

    /**
     * 是否已结束，结束则不更新相关数据
     */
    @Column(name = "IsEnd")
    private Boolean isend;

    /**
     * 状态
            0：未开售，1：销售中2：已截止，3：已开奖4：已返奖
     */
    private Integer state;

    /**
     * 和球探的赛程比较是否主客调换，调换为true，否则false
     */
    @Column(name = "IsTurned")
    private Boolean isturned;

    /**
     * 1：彩果已审核 0：未审核
     */
    @Column(name = "IsAudit")
    private Boolean isaudit;

    /**
     * 主队官网名
     */
    @Column(name = "HomeTeamGov")
    private String hometeamgov;

    /**
     * 客队官网名
     */
    @Column(name = "GuestTeamGov")
    private String guestteamgov;

    /**
     * 是否开售让球胜平负单关
     */
    @Column(name = "Single101")
    private Boolean single101;

    /**
     * 是否开售比分单关
     */
    @Column(name = "Single102")
    private Boolean single102;

    /**
     * 是否开售进球数单关
     */
    @Column(name = "Single103")
    private Boolean single103;

    /**
     * 是否开售半全场单关
     */
    @Column(name = "Single104")
    private Boolean single104;

    /**
     * 是否开售胜平负单关
     */
    @Column(name = "Single105")
    private Boolean single105;

    /**
     * 下半场开场时间
     */
    @Column(name = "MatchTime2")
    private Date matchtime2;

    /**
     * 添加的时间
     */
    @Column(name = "AddTime")
    private Date addtime;

    private static final long serialVersionUID = 1L;

    /**
     * 获取
赛程id，唯一标识
     *
     * @return ID - 
赛程id，唯一标识
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置
赛程id，唯一标识
     *
     * @param id 
赛程id，唯一标识
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取球探赛程id
     *
     * @return ScheduleID - 球探赛程id
     */
    public Integer getScheduleid() {
        return scheduleid;
    }

    /**
     * 设置球探赛程id
     *
     * @param scheduleid 球探赛程id
     */
    public void setScheduleid(Integer scheduleid) {
        this.scheduleid = scheduleid;
    }

    /**
     * 获取在当期中的序号，周三001
     *
     * @return MatchID - 在当期中的序号，周三001
     */
    public String getMatchid() {
        return matchid;
    }

    /**
     * 设置在当期中的序号，周三001
     *
     * @param matchid 在当期中的序号，周三001
     */
    public void setMatchid(String matchid) {
        this.matchid = matchid;
    }

    /**
     * 获取比赛时间
     *
     * @return MatchTime - 比赛时间
     */
    public Date getMatchtime() {
        return matchtime;
    }

    /**
     * 设置比赛时间
     *
     * @param matchtime 比赛时间
     */
    public void setMatchtime(Date matchtime) {
        this.matchtime = matchtime;
    }

    /**
     * 获取停售时间
     *
     * @return SelloutTime - 停售时间
     */
    public Date getSellouttime() {
        return sellouttime;
    }

    /**
     * 设置停售时间
     *
     * @param sellouttime 停售时间
     */
    public void setSellouttime(Date sellouttime) {
        this.sellouttime = sellouttime;
    }

    /**
     * 获取赛事名
     *
     * @return Sclass - 赛事名
     */
    public String getSclass() {
        return sclass;
    }

    /**
     * 设置赛事名
     *
     * @param sclass 赛事名
     */
    public void setSclass(String sclass) {
        this.sclass = sclass;
    }

    /**
     * 获取主场球队名
     *
     * @return HomeTeam - 主场球队名
     */
    public String getHometeam() {
        return hometeam;
    }

    /**
     * 设置主场球队名
     *
     * @param hometeam 主场球队名
     */
    public void setHometeam(String hometeam) {
        this.hometeam = hometeam;
    }

    /**
     * 获取主场球队ID
     *
     * @return HomeTeamID - 主场球队ID
     */
    public Integer getHometeamid() {
        return hometeamid;
    }

    /**
     * 设置主场球队ID
     *
     * @param hometeamid 主场球队ID
     */
    public void setHometeamid(Integer hometeamid) {
        this.hometeamid = hometeamid;
    }

    /**
     * 获取客队名
     *
     * @return GuestTeam - 客队名
     */
    public String getGuestteam() {
        return guestteam;
    }

    /**
     * 设置客队名
     *
     * @param guestteam 客队名
     */
    public void setGuestteam(String guestteam) {
        this.guestteam = guestteam;
    }

    /**
     * 获取客队id
     *
     * @return GuestTeamID - 客队id
     */
    public Integer getGuestteamid() {
        return guestteamid;
    }

    /**
     * 设置客队id
     *
     * @param guestteamid 客队id
     */
    public void setGuestteamid(Integer guestteamid) {
        this.guestteamid = guestteamid;
    }

    /**
     * 获取主场球队名 繁体名
     *
     * @return HomeTeamF - 主场球队名 繁体名
     */
    public String getHometeamf() {
        return hometeamf;
    }

    /**
     * 设置主场球队名 繁体名
     *
     * @param hometeamf 主场球队名 繁体名
     */
    public void setHometeamf(String hometeamf) {
        this.hometeamf = hometeamf;
    }

    /**
     * 获取客队名 繁体名
     *
     * @return GuestTeamF - 客队名 繁体名
     */
    public String getGuestteamf() {
        return guestteamf;
    }

    /**
     * 设置客队名 繁体名
     *
     * @param guestteamf 客队名 繁体名
     */
    public void setGuestteamf(String guestteamf) {
        this.guestteamf = guestteamf;
    }

    /**
     * 获取状态：未=0,上=1,中=2下=3,待定=-11,腰斩= -12中断=-13,推迟=-14取消=-99,完场=-1
     *
     * @return MatchState - 状态：未=0,上=1,中=2下=3,待定=-11,腰斩= -12中断=-13,推迟=-14取消=-99,完场=-1
     */
    public Short getMatchstate() {
        return matchstate;
    }

    /**
     * 设置状态：未=0,上=1,中=2下=3,待定=-11,腰斩= -12中断=-13,推迟=-14取消=-99,完场=-1
     *
     * @param matchstate 状态：未=0,上=1,中=2下=3,待定=-11,腰斩= -12中断=-13,推迟=-14取消=-99,完场=-1
     */
    public void setMatchstate(Short matchstate) {
        this.matchstate = matchstate;
    }

    /**
     * 获取主队入球
     *
     * @return HomeScore - 主队入球
     */
    public Short getHomescore() {
        return homescore;
    }

    /**
     * 设置主队入球
     *
     * @param homescore 主队入球
     */
    public void setHomescore(Short homescore) {
        this.homescore = homescore;
    }

    /**
     * 获取客队入球
     *
     * @return GuestScore - 客队入球
     */
    public Short getGuestscore() {
        return guestscore;
    }

    /**
     * 设置客队入球
     *
     * @param guestscore 客队入球
     */
    public void setGuestscore(Short guestscore) {
        this.guestscore = guestscore;
    }

    /**
     * 获取主队半场入球
     *
     * @return HomeHalfScore - 主队半场入球
     */
    public Short getHomehalfscore() {
        return homehalfscore;
    }

    /**
     * 设置主队半场入球
     *
     * @param homehalfscore 主队半场入球
     */
    public void setHomehalfscore(Short homehalfscore) {
        this.homehalfscore = homehalfscore;
    }

    /**
     * 获取客队半场入球
     *
     * @return GuestHalfScore - 客队半场入球
     */
    public Short getGuesthalfscore() {
        return guesthalfscore;
    }

    /**
     * 设置客队半场入球
     *
     * @param guesthalfscore 客队半场入球
     */
    public void setGuesthalfscore(Short guesthalfscore) {
        this.guesthalfscore = guesthalfscore;
    }

    /**
     * 获取单场让球
     *
     * @return PolyGoal - 单场让球
     */
    public Float getPolygoal() {
        return polygoal;
    }

    /**
     * 设置单场让球
     *
     * @param polygoal 单场让球
     */
    public void setPolygoal(Float polygoal) {
        this.polygoal = polygoal;
    }

    /**
     * 获取是否已结束，结束则不更新相关数据
     *
     * @return IsEnd - 是否已结束，结束则不更新相关数据
     */
    public Boolean getIsend() {
        return isend;
    }

    /**
     * 设置是否已结束，结束则不更新相关数据
     *
     * @param isend 是否已结束，结束则不更新相关数据
     */
    public void setIsend(Boolean isend) {
        this.isend = isend;
    }

    /**
     * 获取状态
            0：未开售，1：销售中2：已截止，3：已开奖4：已返奖
     *
     * @return state - 状态
            0：未开售，1：销售中2：已截止，3：已开奖4：已返奖
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置状态
            0：未开售，1：销售中2：已截止，3：已开奖4：已返奖
     *
     * @param state 状态
            0：未开售，1：销售中2：已截止，3：已开奖4：已返奖
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取和球探的赛程比较是否主客调换，调换为true，否则false
     *
     * @return IsTurned - 和球探的赛程比较是否主客调换，调换为true，否则false
     */
    public Boolean getIsturned() {
        return isturned;
    }

    /**
     * 设置和球探的赛程比较是否主客调换，调换为true，否则false
     *
     * @param isturned 和球探的赛程比较是否主客调换，调换为true，否则false
     */
    public void setIsturned(Boolean isturned) {
        this.isturned = isturned;
    }

    /**
     * 获取1：彩果已审核 0：未审核
     *
     * @return IsAudit - 1：彩果已审核 0：未审核
     */
    public Boolean getIsaudit() {
        return isaudit;
    }

    /**
     * 设置1：彩果已审核 0：未审核
     *
     * @param isaudit 1：彩果已审核 0：未审核
     */
    public void setIsaudit(Boolean isaudit) {
        this.isaudit = isaudit;
    }

    /**
     * 获取主队官网名
     *
     * @return HomeTeamGov - 主队官网名
     */
    public String getHometeamgov() {
        return hometeamgov;
    }

    /**
     * 设置主队官网名
     *
     * @param hometeamgov 主队官网名
     */
    public void setHometeamgov(String hometeamgov) {
        this.hometeamgov = hometeamgov;
    }

    /**
     * 获取客队官网名
     *
     * @return GuestTeamGov - 客队官网名
     */
    public String getGuestteamgov() {
        return guestteamgov;
    }

    /**
     * 设置客队官网名
     *
     * @param guestteamgov 客队官网名
     */
    public void setGuestteamgov(String guestteamgov) {
        this.guestteamgov = guestteamgov;
    }

    /**
     * 获取是否开售让球胜平负单关
     *
     * @return Single101 - 是否开售让球胜平负单关
     */
    public Boolean getSingle101() {
        return single101;
    }

    /**
     * 设置是否开售让球胜平负单关
     *
     * @param single101 是否开售让球胜平负单关
     */
    public void setSingle101(Boolean single101) {
        this.single101 = single101;
    }

    /**
     * 获取是否开售比分单关
     *
     * @return Single102 - 是否开售比分单关
     */
    public Boolean getSingle102() {
        return single102;
    }

    /**
     * 设置是否开售比分单关
     *
     * @param single102 是否开售比分单关
     */
    public void setSingle102(Boolean single102) {
        this.single102 = single102;
    }

    /**
     * 获取是否开售进球数单关
     *
     * @return Single103 - 是否开售进球数单关
     */
    public Boolean getSingle103() {
        return single103;
    }

    /**
     * 设置是否开售进球数单关
     *
     * @param single103 是否开售进球数单关
     */
    public void setSingle103(Boolean single103) {
        this.single103 = single103;
    }

    /**
     * 获取是否开售半全场单关
     *
     * @return Single104 - 是否开售半全场单关
     */
    public Boolean getSingle104() {
        return single104;
    }

    /**
     * 设置是否开售半全场单关
     *
     * @param single104 是否开售半全场单关
     */
    public void setSingle104(Boolean single104) {
        this.single104 = single104;
    }

    /**
     * 获取是否开售胜平负单关
     *
     * @return Single105 - 是否开售胜平负单关
     */
    public Boolean getSingle105() {
        return single105;
    }

    /**
     * 设置是否开售胜平负单关
     *
     * @param single105 是否开售胜平负单关
     */
    public void setSingle105(Boolean single105) {
        this.single105 = single105;
    }

    /**
     * 获取下半场开场时间
     *
     * @return MatchTime2 - 下半场开场时间
     */
    public Date getMatchtime2() {
        return matchtime2;
    }

    /**
     * 设置下半场开场时间
     *
     * @param matchtime2 下半场开场时间
     */
    public void setMatchtime2(Date matchtime2) {
        this.matchtime2 = matchtime2;
    }

    /**
     * 获取添加的时间
     *
     * @return AddTime - 添加的时间
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * 设置添加的时间
     *
     * @param addtime 添加的时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
}