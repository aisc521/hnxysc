package com.zhcdata.db.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "jc_match_live")
public class JcMatchLive implements Serializable {
    @Column(name = "ID")
    private Long id;

    /**
     * 文字直播内容
     */
    @Column(name = "CONTENT")
    private String content;

    /**
     * 文字直播时间
     */
    @Column(name = "TIME")
    private Date time;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 球探赛程id
     */
    @Column(name = "ScheduleID")
    private Long scheduleid;

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
     * 获取文字直播内容
     *
     * @return CONTENT - 文字直播内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置文字直播内容
     *
     * @param content 文字直播内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取文字直播时间
     *
     * @return TIME - 文字直播时间
     */
    public Date getTime() {
        return time;
    }

    /**
     * 设置文字直播时间
     *
     * @param time 文字直播时间
     */
    public void setTime(Date time) {
        this.time = time;
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

    /**
     * 获取球探赛程id
     *
     * @return ScheduleID - 球探赛程id
     */
    public Long getScheduleid() {
        return scheduleid;
    }

    /**
     * 设置球探赛程id
     *
     * @param scheduleid 球探赛程id
     */
    public void setScheduleid(Long scheduleid) {
        this.scheduleid = scheduleid;
    }
}