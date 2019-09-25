package com.zhcdata.db.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_jc_fans")
public class TbJcFans implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 专家id
     */
    @Column(name = "expert_Id")
    private Long expertId;

    /**
     * 经验数量
     */
    private Long number;

    /**
     * 描述信息（发方案+1 中方案+3）
     */
    @Column(name = "describe_info")
    private Long describeInfo;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

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
     * 获取专家id
     *
     * @return expert_Id - 专家id
     */
    public Long getExpertId() {
        return expertId;
    }

    /**
     * 设置专家id
     *
     * @param expertId 专家id
     */
    public void setExpertId(Long expertId) {
        this.expertId = expertId;
    }

    /**
     * 获取经验数量
     *
     * @return number - 经验数量
     */
    public Long getNumber() {
        return number;
    }

    /**
     * 设置经验数量
     *
     * @param number 经验数量
     */
    public void setNumber(Long number) {
        this.number = number;
    }

    /**
     * 获取描述信息（发方案+1 中方案+3）
     *
     * @return describe_info - 描述信息（发方案+1 中方案+3）
     */
    public Long getDescribeInfo() {
        return describeInfo;
    }

    /**
     * 设置描述信息（发方案+1 中方案+3）
     *
     * @param describeInfo 描述信息（发方案+1 中方案+3）
     */
    public void setDescribeInfo(Long describeInfo) {
        this.describeInfo = describeInfo;
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
}