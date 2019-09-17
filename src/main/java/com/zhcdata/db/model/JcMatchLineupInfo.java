package com.zhcdata.db.model;

import java.util.Date;

public class JcMatchLineupInfo {
    private Long id;

    private String homeArray;

    private String awayArray;

    private String homeLineup;

    private String awayLineup;

    private String homeBackup;

    private String awayBackup;

    private String createTime;

    private String updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHomeArray() {
        return homeArray;
    }

    public void setHomeArray(String homeArray) {
        this.homeArray = homeArray == null ? null : homeArray.trim();
    }

    public String getAwayArray() {
        return awayArray;
    }

    public void setAwayArray(String awayArray) {
        this.awayArray = awayArray == null ? null : awayArray.trim();
    }

    public String getHomeLineup() {
        return homeLineup;
    }

    public void setHomeLineup(String homeLineup) {
        this.homeLineup = homeLineup == null ? null : homeLineup.trim();
    }

    public String getAwayLineup() {
        return awayLineup;
    }

    public void setAwayLineup(String awayLineup) {
        this.awayLineup = awayLineup == null ? null : awayLineup.trim();
    }

    public String getHomeBackup() {
        return homeBackup;
    }

    public void setHomeBackup(String homeBackup) {
        this.homeBackup = homeBackup == null ? null : homeBackup.trim();
    }

    public String getAwayBackup() {
        return awayBackup;
    }

    public void setAwayBackup(String awayBackup) {
        this.awayBackup = awayBackup == null ? null : awayBackup.trim();
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
}
