package com.zhcdata.db.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_jc_victory")
public class TbJcVictory implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 专家id
     */
    @Column(name = "expert_id")
    private Long expertId;

    /**
     * 当前几连中
     */
    @Column(name = "lz_now")
    private Long lzNow;

    /**
     * 发方案数量
     */
    private Long f;

    /**
     * 方案命中数
     */
    private Long z;

    /**
     * 三天命中率
     */
    @Column(name = "z_three_days")
    private Long zThreeDays;

    /**
     * 五天命中率
     */
    @Column(name = "z_five_days")
    private Long zFiveDays;

    /**
     * 七天命中率
     */
    @Column(name = "z_seven_days")
    private Long zSevenDays;

    /**
     * 七天回报率
     */
    @Column(name = "return_seven_days")
    private Long returnSevenDays;

    /**
     * 趋势（黑红黑红红）
     */
    private String trend;

    /**
     * 全部回报率
     */
    @Column(name = "return_all")
    private Long returnAll;

    /**
     * 全部命中率
     */
    @Column(name = "z_all")
    private Long zAll;

    /**
     * 历史最高连红
     */
    @Column(name = "lz_big")
    private Long lzBig;

    /**
     * 七天盈利率
     */
    @Column(name = "yl_seven_days")
    private Long ylSevenDays;

    /**
     * 近10中几
     */
    @Column(name = "ten_z")
    private Integer tenZ;

    /**
     * 近9中几
     */
    @Column(name = "nine_z")
    private Integer nineZ;

    /**
     * 近8中几
     */
    @Column(name = "eight_z")
    private Integer eightZ;

    /**
     * 近7中几
     */
    @Column(name = "seven_z")
    private Integer sevenZ;

    /**
     * 近6中几
     */
    @Column(name = "six_z")
    private Integer sixZ;

    /**
     * 近5中几
     */
    @Column(name = "five_z")
    private Integer fiveZ;

    /**
     * 近4中几
     */
    @Column(name = "four_z")
    private Integer fourZ;

    /**
     * 近3中几
     */
    @Column(name = "three_z")
    private Integer threeZ;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;


    /**
     * 排序
     */
    @Column(name = "order_By")
    private Integer orderBy;

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取专家id
     *
     * @return expert_id - 专家id
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
     * 获取当前几连中
     *
     * @return lz_now - 当前几连中
     */
    public Long getLzNow() {
        return lzNow;
    }

    /**
     * 设置当前几连中
     *
     * @param lzNow 当前几连中
     */
    public void setLzNow(Long lzNow) {
        this.lzNow = lzNow;
    }

    /**
     * 获取发方案数量
     *
     * @return f - 发方案数量
     */
    public Long getF() {
        return f;
    }

    /**
     * 设置发方案数量
     *
     * @param f 发方案数量
     */
    public void setF(Long f) {
        this.f = f;
    }

    /**
     * 获取方案命中数
     *
     * @return z - 方案命中数
     */
    public Long getZ() {
        return z;
    }

    /**
     * 设置方案命中数
     *
     * @param z 方案命中数
     */
    public void setZ(Long z) {
        this.z = z;
    }

    /**
     * 获取三天命中率
     *
     * @return z_three_days - 三天命中率
     */
    public Long getzThreeDays() {
        return zThreeDays;
    }

    /**
     * 设置三天命中率
     *
     * @param zThreeDays 三天命中率
     */
    public void setzThreeDays(Long zThreeDays) {
        this.zThreeDays = zThreeDays;
    }

    /**
     * 获取五天命中率
     *
     * @return z_five_days - 五天命中率
     */
    public Long getzFiveDays() {
        return zFiveDays;
    }

    /**
     * 设置五天命中率
     *
     * @param zFiveDays 五天命中率
     */
    public void setzFiveDays(Long zFiveDays) {
        this.zFiveDays = zFiveDays;
    }

    /**
     * 获取七天命中率
     *
     * @return z_seven_days - 七天命中率
     */
    public Long getzSevenDays() {
        return zSevenDays;
    }

    /**
     * 设置七天命中率
     *
     * @param zSevenDays 七天命中率
     */
    public void setzSevenDays(Long zSevenDays) {
        this.zSevenDays = zSevenDays;
    }

    /**
     * 获取七天回报率
     *
     * @return return_seven_days - 七天回报率
     */
    public Long getReturnSevenDays() {
        return returnSevenDays;
    }

    /**
     * 设置七天回报率
     *
     * @param returnSevenDays 七天回报率
     */
    public void setReturnSevenDays(Long returnSevenDays) {
        this.returnSevenDays = returnSevenDays;
    }

    /**
     * 获取趋势（黑红黑红红）
     *
     * @return trend - 趋势（黑红黑红红）
     */
    public String getTrend() {
        return trend;
    }

    /**
     * 设置趋势（黑红黑红红）
     *
     * @param trend 趋势（黑红黑红红）
     */
    public void setTrend(String trend) {
        this.trend = trend;
    }

    /**
     * 获取全部回报率
     *
     * @return return_all - 全部回报率
     */
    public Long getReturnAll() {
        return returnAll;
    }

    /**
     * 设置全部回报率
     *
     * @param returnAll 全部回报率
     */
    public void setReturnAll(Long returnAll) {
        this.returnAll = returnAll;
    }

    /**
     * 获取全部命中率
     *
     * @return z_all - 全部命中率
     */
    public Long getzAll() {
        return zAll;
    }

    /**
     * 设置全部命中率
     *
     * @param zAll 全部命中率
     */
    public void setzAll(Long zAll) {
        this.zAll = zAll;
    }

    /**
     * 获取历史最高连红
     *
     * @return lz_big - 历史最高连红
     */
    public Long getLzBig() {
        return lzBig;
    }

    /**
     * 设置历史最高连红
     *
     * @param lzBig 历史最高连红
     */
    public void setLzBig(Long lzBig) {
        this.lzBig = lzBig;
    }

    /**
     * 获取七天盈利率
     *
     * @return yl_seven_days - 七天盈利率
     */
    public Long getYlSevenDays() {
        return ylSevenDays;
    }

    /**
     * 设置七天盈利率
     *
     * @param ylSevenDays 七天盈利率
     */
    public void setYlSevenDays(Long ylSevenDays) {
        this.ylSevenDays = ylSevenDays;
    }

    /**
     * 获取近10中几
     *
     * @return ten_z - 近10中几
     */
    public Integer getTenZ() {
        return tenZ;
    }

    /**
     * 设置近10中几
     *
     * @param tenZ 近10中几
     */
    public void setTenZ(Integer tenZ) {
        this.tenZ = tenZ;
    }

    /**
     * 获取近9中几
     *
     * @return nine_z - 近9中几
     */
    public Integer getNineZ() {
        return nineZ;
    }

    /**
     * 设置近9中几
     *
     * @param nineZ 近9中几
     */
    public void setNineZ(Integer nineZ) {
        this.nineZ = nineZ;
    }

    /**
     * 获取近8中几
     *
     * @return eight_z - 近8中几
     */
    public Integer getEightZ() {
        return eightZ;
    }

    /**
     * 设置近8中几
     *
     * @param eightZ 近8中几
     */
    public void setEightZ(Integer eightZ) {
        this.eightZ = eightZ;
    }

    /**
     * 获取近7中几
     *
     * @return seven_z - 近7中几
     */
    public Integer getSevenZ() {
        return sevenZ;
    }

    /**
     * 设置近7中几
     *
     * @param sevenZ 近7中几
     */
    public void setSevenZ(Integer sevenZ) {
        this.sevenZ = sevenZ;
    }

    /**
     * 获取近6中几
     *
     * @return six_z - 近6中几
     */
    public Integer getSixZ() {
        return sixZ;
    }

    /**
     * 设置近6中几
     *
     * @param sixZ 近6中几
     */
    public void setSixZ(Integer sixZ) {
        this.sixZ = sixZ;
    }

    /**
     * 获取近5中几
     *
     * @return five_z - 近5中几
     */
    public Integer getFiveZ() {
        return fiveZ;
    }

    /**
     * 设置近5中几
     *
     * @param fiveZ 近5中几
     */
    public void setFiveZ(Integer fiveZ) {
        this.fiveZ = fiveZ;
    }

    /**
     * 获取近4中几
     *
     * @return four_z - 近4中几
     */
    public Integer getFourZ() {
        return fourZ;
    }

    /**
     * 设置近4中几
     *
     * @param fourZ 近4中几
     */
    public void setFourZ(Integer fourZ) {
        this.fourZ = fourZ;
    }

    /**
     * 获取近3中几
     *
     * @return three_z - 近3中几
     */
    public Integer getThreeZ() {
        return threeZ;
    }

    /**
     * 设置近3中几
     *
     * @param threeZ 近3中几
     */
    public void setThreeZ(Integer threeZ) {
        this.threeZ = threeZ;
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
}