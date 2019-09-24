package com.zhcdata.db.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_pg_u_collect")
public class TbPgUCollect implements Serializable {
    /**
     * 自增Id
     */
    @Column(name = "ID")
    private Long id;

    /**
     * 用户Id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 雷达赛事ID
     */
    @Column(name = "match_id")
    private Long matchId;

    /**
     * 1 收藏 0取消
     */
    private Integer status;

    /**
     * 比赛时间
     */
    @Column(name = "match_time")
    private Date matchTime;

    /**
     * 过期时间
     */
    @Column(name = "over_time")
    private Date overTime;

    /**
     * 渠道
     */
    private String src;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 1竞彩 2北单 3足彩
     */
    private Integer type;

    private static final long serialVersionUID = 1L;

    /**
     * 获取自增Id
     *
     * @return ID - 自增Id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置自增Id
     *
     * @param id 自增Id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户Id
     *
     * @return user_id - 用户Id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户Id
     *
     * @param userId 用户Id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取雷达赛事ID
     *
     * @return match_id - 雷达赛事ID
     */
    public Long getMatchId() {
        return matchId;
    }

    /**
     * 设置雷达赛事ID
     *
     * @param matchId 雷达赛事ID
     */
    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    /**
     * 获取1 收藏 0取消
     *
     * @return status - 1 收藏 0取消
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置1 收藏 0取消
     *
     * @param status 1 收藏 0取消
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取比赛时间
     *
     * @return match_time - 比赛时间
     */
    public Date getMatchTime() {
        return matchTime;
    }

    /**
     * 设置比赛时间
     *
     * @param matchTime 比赛时间
     */
    public void setMatchTime(Date matchTime) {
        this.matchTime = matchTime;
    }

    /**
     * 获取过期时间
     *
     * @return over_time - 过期时间
     */
    public Date getOverTime() {
        return overTime;
    }

    /**
     * 设置过期时间
     *
     * @param overTime 过期时间
     */
    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }

    /**
     * 获取渠道
     *
     * @return src - 渠道
     */
    public String getSrc() {
        return src;
    }

    /**
     * 设置渠道
     *
     * @param src 渠道
     */
    public void setSrc(String src) {
        this.src = src;
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

    /**
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取1竞彩 2北单 3足彩
     *
     * @return type - 1竞彩 2北单 3足彩
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置1竞彩 2北单 3足彩
     *
     * @param type 1竞彩 2北单 3足彩
     */
    public void setType(Integer type) {
        this.type = type;
    }
}