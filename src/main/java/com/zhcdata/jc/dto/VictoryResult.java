package com.zhcdata.jc.dto;

public class VictoryResult {
    /**
     *自增Id
     */
    private String Id;

    /**
     * 专家Id
     */
    private String expertId;

    /**
     *几连中
     */
    private String lz;

    /**
     *发方案数
     */
    private String f;

    /**
     *方案命中数
     */
    private String z;

    /**
     *三天命中率
     */
    private String zThreeDays;

    /**
     *五天命中率
     */
    private String zFiveDays;

    /**
     *七天命中率
     */
    private String zSevenDays;

    /**
     *七天回报率
     */
    private String returnSevenDays;

    /**
     *趋势
     */
    private String trend;

    /**
     *生成时间
     */
    private String createTime;


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }

    public String getLz() {
        return lz;
    }

    public void setLz(String lz) {
        this.lz = lz;
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

    public String getzThreeDays() {
        return zThreeDays;
    }

    public void setzThreeDays(String zThreeDays) {
        this.zThreeDays = zThreeDays;
    }

    public String getzFiveDays() {
        return zFiveDays;
    }

    public void setzFiveDays(String zFiveDays) {
        this.zFiveDays = zFiveDays;
    }

    public String getzSevenDays() {
        return zSevenDays;
    }

    public void setzSevenDays(String zSevenDays) {
        this.zSevenDays = zSevenDays;
    }

    public String getReturnSevenDays() {
        return returnSevenDays;
    }

    public void setReturnSevenDays(String returnSevenDays) {
        this.returnSevenDays = returnSevenDays;
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
}
