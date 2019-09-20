package com.zhcdata.db.model;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "tb_jc_dictionary")
public class TbJcDictionary implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 字典类型（1专家标签 2 分成比例 3 所属负责人4方案价格）
     */
    @Column(name = "d_type")
    private Long dType;

    /**
     * key值
     */
    @Column(name = "d_key")
    private Long dKey;

    /**
     * value值
     */
    @Column(name = "d_value")
    private String dValue;

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
     * 获取字典类型（1专家标签 2 分成比例 3 所属负责人4方案价格）
     *
     * @return d_type - 字典类型（1专家标签 2 分成比例 3 所属负责人4方案价格）
     */
    public Long getdType() {
        return dType;
    }

    /**
     * 设置字典类型（1专家标签 2 分成比例 3 所属负责人4方案价格）
     *
     * @param dType 字典类型（1专家标签 2 分成比例 3 所属负责人4方案价格）
     */
    public void setdType(Long dType) {
        this.dType = dType;
    }

    /**
     * 获取key值
     *
     * @return d_key - key值
     */
    public Long getdKey() {
        return dKey;
    }

    /**
     * 设置key值
     *
     * @param dKey key值
     */
    public void setdKey(Long dKey) {
        this.dKey = dKey;
    }

    /**
     * 获取value值
     *
     * @return d_value - value值
     */
    public String getdValue() {
        return dValue;
    }

    /**
     * 设置value值
     *
     * @param dValue value值
     */
    public void setdValue(String dValue) {
        this.dValue = dValue;
    }
}