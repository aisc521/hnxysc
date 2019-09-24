package com.zhcdata.db.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_jc_record_focus")
public class TbJcRecordFocus implements Serializable {
    private Integer id;

    /**
     * 专家ID
     */
    @Column(name = "expert_Id")
    private Integer expertId;

    /**
     * 用户ID
     */
    @Column(name = "user_Id")
    private Integer userId;

    /**
     * 1 关注 0 取消关注
     */
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_Time")
    private Date createTime;

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
     * 获取专家ID
     *
     * @return expert_Id - 专家ID
     */
    public Integer getExpertId() {
        return expertId;
    }

    /**
     * 设置专家ID
     *
     * @param expertId 专家ID
     */
    public void setExpertId(Integer expertId) {
        this.expertId = expertId;
    }

    /**
     * 获取用户ID
     *
     * @return user_Id - 用户ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取1 关注 0 取消关注
     *
     * @return status - 1 关注 0 取消关注
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置1 关注 0 取消关注
     *
     * @param status 1 关注 0 取消关注
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取创建时间
     *
     * @return create_Time - 创建时间
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