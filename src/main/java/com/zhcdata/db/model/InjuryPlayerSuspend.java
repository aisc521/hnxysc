package com.zhcdata.db.model;

import lombok.ToString;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

@ToString
@Table(name = "injury_player_suspend")
public class InjuryPlayerSuspend implements Serializable {
    @Id
    private Integer id;

    @Column(name = "schedule_id")
    private Integer scheduleId;

    @Column(name = "teamID")
    private Integer teamid;

    @Column(name = "playerID")
    private Integer playerid;

    @Column(name = "en_name")
    private String enName;

    @Column(name = "cn_name")
    private String cnName;

    @Column(name = "ReasonType")
    private Integer reasontype;

    @Column(name = "Reason")
    private String reason;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return schedule_id
     */
    public Integer getScheduleId() {
        return scheduleId;
    }

    /**
     * @param scheduleId
     */
    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    /**
     * @return teamID
     */
    public Integer getTeamid() {
        return teamid;
    }

    /**
     * @param teamid
     */
    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
    }

    /**
     * @return playerID
     */
    public Integer getPlayerid() {
        return playerid;
    }

    /**
     * @param playerid
     */
    public void setPlayerid(Integer playerid) {
        this.playerid = playerid;
    }

    /**
     * @return en_name
     */
    public String getEnName() {
        return enName;
    }

    /**
     * @param enName
     */
    public void setEnName(String enName) {
        this.enName = enName;
    }

    /**
     * @return cn_name
     */
    public String getCnName() {
        return cnName;
    }

    /**
     * @param cnName
     */
    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    /**
     * @return ReasonType
     */
    public Integer getReasontype() {
        return reasontype;
    }

    /**
     * @param reasontype
     */
    public void setReasontype(Integer reasontype) {
        this.reasontype = reasontype;
    }

    /**
     * @return Reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InjuryPlayerSuspend that = (InjuryPlayerSuspend) o;
        if (cnName!=null) cnName = StringUtils.deleteWhitespace(cnName);
        if (that.cnName!=null) that.cnName = StringUtils.deleteWhitespace(that.cnName);
        return Objects.equals(scheduleId, that.scheduleId) &&
                Objects.equals(teamid, that.teamid) &&
                Objects.equals(playerid, that.playerid) &&
                Objects.equals(enName, that.enName) &&
                Objects.equals(cnName, that.cnName) &&
                Objects.equals(reasontype, that.reasontype) &&
                Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId, teamid, playerid, enName, cnName, reasontype, reason);
    }
}