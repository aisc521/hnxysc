package com.zhcdata.jc.dto;

public class VictoryInfo {
    /**
     *
     */
    private String id;

    /**
     *专家id
     */
    private String expertId;

    /**
     *当前几连中
     */
    private String lzNow;

    /**
     *发方案数量
     */
    private String f;

    /**
     *方案命中数
     */
    private String z;

    /**
     *三天命中率
     */
    private Double zThreeDays;

    /**
     *五天命中率
     */
    private Double zFiveDays;

    /**
     *七天命中率
     */
    private Double zSevenDays;

    /**
     *七天回报率
     */
    private Double returnSevenDays;

    /**
     *趋势（黑红黑红红）
     */
    private String trend;

    /**
     *创建时间
     */
    private String createTime;

    /**
     *更新时间
     */
    private String updateTime;

    /**
     *全部回报率
     */
    private Double returnAll;

    /**
     *全部命中率
     */
    private Double zAll;

    /**
     *历史最高连红
     */
    private String lzBig;

    /**
     *七天盈利率
     */
    private Double ylSevenDays;

    /**
     *近10中几
     */
    private String ten_z;

    /**
     *近9中几
     */
    private String nine_z;

    /**
     *近8中几
     */
    private String eight_z;

    /**
     *近7中几
     */
    private String seven_z;

    /**
     *近6中几
     */
    private String six_z;

    /**
     *近5中几
     */
    private String five_z;

    /**
     *近4中几
     */
    private String four_z;

    /**
     *近3中几
     */
    private String three_z;

    /**
     * 专家排序
     */
    private Integer order_By;

    /**
     * 近7天命中情况
     */
    private String sevenDaysHit;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }

    public String getLzNow() {
        return lzNow;
    }

    public void setLzNow(String lzNow) {
        this.lzNow = lzNow;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getLzBig() {
        return lzBig;
    }

    public void setLzBig(String lzBig) {
        this.lzBig = lzBig;
    }

    public String getTen_z() {
        return ten_z;
    }

    public void setTen_z(String ten_z) {
        this.ten_z = ten_z;
    }

    public String getNine_z() {
        return nine_z;
    }

    public void setNine_z(String nine_z) {
        this.nine_z = nine_z;
    }

    public String getEight_z() {
        return eight_z;
    }

    public void setEight_z(String eight_z) {
        this.eight_z = eight_z;
    }

    public String getSeven_z() {
        return seven_z;
    }

    public void setSeven_z(String seven_z) {
        this.seven_z = seven_z;
    }

    public String getSix_z() {
        return six_z;
    }

    public void setSix_z(String six_z) {
        this.six_z = six_z;
    }

    public String getFive_z() {
        return five_z;
    }

    public void setFive_z(String five_z) {
        this.five_z = five_z;
    }

    public String getFour_z() {
        return four_z;
    }

    public void setFour_z(String four_z) {
        this.four_z = four_z;
    }

    public String getThree_z() {
        return three_z;
    }

    public void setThree_z(String three_z) {
        this.three_z = three_z;
    }

    public Integer getOrder_By() {
        return order_By;
    }

    public void setOrder_By(Integer order_By) {
        this.order_By = order_By;
    }

    public Double getzThreeDays() {
        return zThreeDays;
    }

    public void setzThreeDays(Double zThreeDays) {
        this.zThreeDays = zThreeDays;
    }

    public Double getzFiveDays() {
        return zFiveDays;
    }

    public void setzFiveDays(Double zFiveDays) {
        this.zFiveDays = zFiveDays;
    }

    public Double getzSevenDays() {
        return zSevenDays;
    }

    public void setzSevenDays(Double zSevenDays) {
        this.zSevenDays = zSevenDays;
    }

    public Double getReturnSevenDays() {
        return returnSevenDays;
    }

    public void setReturnSevenDays(Double returnSevenDays) {
        this.returnSevenDays = returnSevenDays;
    }

    public Double getReturnAll() {
        return returnAll;
    }

    public void setReturnAll(Double returnAll) {
        this.returnAll = returnAll;
    }

    public Double getzAll() {
        return zAll;
    }

    public void setzAll(Double zAll) {
        this.zAll = zAll;
    }

    public Double getYlSevenDays() {
        return ylSevenDays;
    }

    public void setYlSevenDays(Double ylSevenDays) {
        this.ylSevenDays = ylSevenDays;
    }

    public String getSevenDaysHit() {
        return sevenDaysHit;
    }

    public void setSevenDaysHit(String sevenDaysHit) {
        this.sevenDaysHit = sevenDaysHit;
    }
}
