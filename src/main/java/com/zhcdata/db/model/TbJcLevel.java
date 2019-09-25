package com.zhcdata.db.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_jc_level")
public class TbJcLevel implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 等级
     */
    private Long level;

    /**
     * 经验区间
     */
    @Column(name = "jy_qj")
    private String jyQj;

    /**
     * 预计升级时间
     */
    @Column(name = "yjsj_time")
    private Long yjsjTime;

    /**
     * 方案金额
     */
    @Column(name = "fa_je")
    private Long faJe;

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
     * 获取等级
     *
     * @return level - 等级
     */
    public Long getLevel() {
        return level;
    }

    /**
     * 设置等级
     *
     * @param level 等级
     */
    public void setLevel(Long level) {
        this.level = level;
    }

    /**
     * 获取经验区间
     *
     * @return jy_qj - 经验区间
     */
    public String getJyQj() {
        return jyQj;
    }

    /**
     * 设置经验区间
     *
     * @param jyQj 经验区间
     */
    public void setJyQj(String jyQj) {
        this.jyQj = jyQj;
    }

    /**
     * 获取预计升级时间
     *
     * @return yjsj_time - 预计升级时间
     */
    public Long getYjsjTime() {
        return yjsjTime;
    }

    /**
     * 设置预计升级时间
     *
     * @param yjsjTime 预计升级时间
     */
    public void setYjsjTime(Long yjsjTime) {
        this.yjsjTime = yjsjTime;
    }

    /**
     * 获取方案金额
     *
     * @return fa_je - 方案金额
     */
    public Long getFaJe() {
        return faJe;
    }

    /**
     * 设置方案金额
     *
     * @param faJe 方案金额
     */
    public void setFaJe(Long faJe) {
        this.faJe = faJe;
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