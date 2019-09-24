package com.zhcdata.db.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_jc_user")
public class TbJcUser implements Serializable {
    private Long id;

    /**
     * 用户id
     */
    @Column(name = "USER_ID")
    private Long userId;

    /**
     * 手机号
     */
    @Column(name = "CELL")
    private String cell;

    /**
     * 用户名
     */
    @Column(name = "USER_NAME")
    private String userName;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 0 有效 8无效
     */
    @Column(name = "STATUS")
    private String status;

    /**
     * 渠道
     */
    @Column(name = "SRC")
    private String src;

    /**
     * 邀请码
     */
    @Column(name = "INVITED_CODE")
    private String invitedCode;

    /**
     * 邀请人ID
     */
    @Column(name = "INVITED_USER")
    private Long invitedUser;

    @Column(name = "TOTAL_COMMISSION")
    private BigDecimal totalCommission;

    @Column(name = "VERSION")
    private Integer version;

    @Column(name = "AGENT_TIME")
    private Date agentTime;

    /**
     * 预警push设置，0 关 1开
     */
    @Column(name = "WARN_PUSH")
    private String warnPush;

    /**
     * 注册SRC
     */
    @Column(name = "REGI_SRC")
    private String regiSrc;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
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
     * 获取用户id
     *
     * @return USER_ID - 用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取手机号
     *
     * @return CELL - 手机号
     */
    public String getCell() {
        return cell;
    }

    /**
     * 设置手机号
     *
     * @param cell 手机号
     */
    public void setCell(String cell) {
        this.cell = cell;
    }

    /**
     * 获取用户名
     *
     * @return USER_NAME - 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     *
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
     * 获取0 有效 8无效
     *
     * @return STATUS - 0 有效 8无效
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置0 有效 8无效
     *
     * @param status 0 有效 8无效
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取渠道
     *
     * @return SRC - 渠道
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
     * 获取邀请码
     *
     * @return INVITED_CODE - 邀请码
     */
    public String getInvitedCode() {
        return invitedCode;
    }

    /**
     * 设置邀请码
     *
     * @param invitedCode 邀请码
     */
    public void setInvitedCode(String invitedCode) {
        this.invitedCode = invitedCode;
    }

    /**
     * 获取邀请人ID
     *
     * @return INVITED_USER - 邀请人ID
     */
    public Long getInvitedUser() {
        return invitedUser;
    }

    /**
     * 设置邀请人ID
     *
     * @param invitedUser 邀请人ID
     */
    public void setInvitedUser(Long invitedUser) {
        this.invitedUser = invitedUser;
    }

    /**
     * @return TOTAL_COMMISSION
     */
    public BigDecimal getTotalCommission() {
        return totalCommission;
    }

    /**
     * @param totalCommission
     */
    public void setTotalCommission(BigDecimal totalCommission) {
        this.totalCommission = totalCommission;
    }

    /**
     * @return VERSION
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * @return AGENT_TIME
     */
    public Date getAgentTime() {
        return agentTime;
    }

    /**
     * @param agentTime
     */
    public void setAgentTime(Date agentTime) {
        this.agentTime = agentTime;
    }

    /**
     * 获取预警push设置，0 关 1开
     *
     * @return WARN_PUSH - 预警push设置，0 关 1开
     */
    public String getWarnPush() {
        return warnPush;
    }

    /**
     * 设置预警push设置，0 关 1开
     *
     * @param warnPush 预警push设置，0 关 1开
     */
    public void setWarnPush(String warnPush) {
        this.warnPush = warnPush;
    }

    /**
     * 获取注册SRC
     *
     * @return REGI_SRC - 注册SRC
     */
    public String getRegiSrc() {
        return regiSrc;
    }

    /**
     * 设置注册SRC
     *
     * @param regiSrc 注册SRC
     */
    public void setRegiSrc(String regiSrc) {
        this.regiSrc = regiSrc;
    }
}