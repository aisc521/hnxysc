package com.zhcdata.jc.dto;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/10/12 14:32
 */
public class LatestPlanReminderDto {
    private String id;//专家id
    private String planId;//方案id
    private String nikeName;//专家昵称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }
}
