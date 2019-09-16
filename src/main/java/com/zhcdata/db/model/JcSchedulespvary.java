package com.zhcdata.db.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "jc_schedulespvary")
public class JcSchedulespvary implements Serializable {
    /**
     * 编号
     */
    @Column(name = "VaryID")
    private Integer varyid;

    /**
     * 赔率ID
     */
    @Column(name = "SPID")
    private Integer spid;

    /**
     * 彩种id
     */
    @Column(name = "TypeID")
    private Short typeid;

    /**
     * Sp变化，脚本数组格式，记录变化的大小以及方向
     */
    @Column(name = "SP")
    private String sp;

    /**
     * 
            变化时间
     */
    @Column(name = "ChangeTime")
    private Date changetime;

    private static final long serialVersionUID = 1L;

    /**
     * 获取编号
     *
     * @return VaryID - 编号
     */
    public Integer getVaryid() {
        return varyid;
    }

    /**
     * 设置编号
     *
     * @param varyid 编号
     */
    public void setVaryid(Integer varyid) {
        this.varyid = varyid;
    }

    /**
     * 获取赔率ID
     *
     * @return SPID - 赔率ID
     */
    public Integer getSpid() {
        return spid;
    }

    /**
     * 设置赔率ID
     *
     * @param spid 赔率ID
     */
    public void setSpid(Integer spid) {
        this.spid = spid;
    }

    /**
     * 获取彩种id
     *
     * @return TypeID - 彩种id
     */
    public Short getTypeid() {
        return typeid;
    }

    /**
     * 设置彩种id
     *
     * @param typeid 彩种id
     */
    public void setTypeid(Short typeid) {
        this.typeid = typeid;
    }

    /**
     * 获取Sp变化，脚本数组格式，记录变化的大小以及方向
     *
     * @return SP - Sp变化，脚本数组格式，记录变化的大小以及方向
     */
    public String getSp() {
        return sp;
    }

    /**
     * 设置Sp变化，脚本数组格式，记录变化的大小以及方向
     *
     * @param sp Sp变化，脚本数组格式，记录变化的大小以及方向
     */
    public void setSp(String sp) {
        this.sp = sp;
    }

    /**
     * 获取
            变化时间
     *
     * @return ChangeTime - 
            变化时间
     */
    public Date getChangetime() {
        return changetime;
    }

    /**
     * 设置
            变化时间
     *
     * @param changetime 
            变化时间
     */
    public void setChangetime(Date changetime) {
        this.changetime = changetime;
    }
}