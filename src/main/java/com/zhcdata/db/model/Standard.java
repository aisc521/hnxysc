package com.zhcdata.db.model;

import java.sql.Timestamp;
import java.util.Date;

public class Standard {
    private Integer oddsid;

    private Integer scheduleid;

    private Integer companyid;

    private String firsthomewin;

    private String firststandoff;

    private String firstguestwin;

    private String homewin;

    private String standoff;

    private String guestwin;

    private Date modifytime;

    private Integer result;

    private Boolean closepan;

    private String homewinR;

    private String guestwinR;

    private String standoffR;

    private Boolean isstoplive;

    //private Timestamp startTime;

    public Integer getOddsid() {
        return oddsid;
    }

    public void setOddsid(Integer oddsid) {
        this.oddsid = oddsid;
    }

    public Integer getScheduleid() {
        return scheduleid;
    }

    public void setScheduleid(Integer scheduleid) {
        this.scheduleid = scheduleid;
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public String getFirsthomewin() {
        return firsthomewin;
    }

    public void setFirsthomewin(String firsthomewin) {
        this.firsthomewin = firsthomewin == null ? null : firsthomewin.trim();
    }

    public String getFirststandoff() {
        return firststandoff;
    }

    public void setFirststandoff(String firststandoff) {
        this.firststandoff = firststandoff == null ? null : firststandoff.trim();
    }

    public String getFirstguestwin() {
        return firstguestwin;
    }

    public void setFirstguestwin(String firstguestwin) {
        this.firstguestwin = firstguestwin == null ? null : firstguestwin.trim();
    }

    public String getHomewin() {
        return homewin;
    }

    public void setHomewin(String homewin) {
        this.homewin = homewin == null ? null : homewin.trim();
    }

    public String getStandoff() {
        return standoff;
    }

    public void setStandoff(String standoff) {
        this.standoff = standoff == null ? null : standoff.trim();
    }

    public String getGuestwin() {
        return guestwin;
    }

    public void setGuestwin(String guestwin) {
        this.guestwin = guestwin == null ? null : guestwin.trim();
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Boolean getClosepan() {
        return closepan;
    }

    public void setClosepan(Boolean closepan) {
        this.closepan = closepan;
    }

    public String getHomewinR() {
        return homewinR;
    }

    public void setHomewinR(String homewinR) {
        this.homewinR = homewinR == null ? null : homewinR.trim();
    }

    public String getGuestwinR() {
        return guestwinR;
    }

    public void setGuestwinR(String guestwinR) {
        this.guestwinR = guestwinR == null ? null : guestwinR.trim();
    }

    public String getStandoffR() {
        return standoffR;
    }

    public void setStandoffR(String standoffR) {
        this.standoffR = standoffR == null ? null : standoffR.trim();
    }

    public Boolean getIsstoplive() {
        return isstoplive;
    }

    public void setIsstoplive(Boolean isstoplive) {
        this.isstoplive = isstoplive;
    }

    //public Timestamp getStarttime() {
    //    return startTime;
    //}

    //public void setStarttime(Timestamp starttime) {
    //    this.startTime = starttime;
    //}

    public boolean same(Standard db) {
        return (db.getHomewinR().equals(homewinR) && db.getGuestwinR().equals(guestwinR) && db.getStandoffR().equals(standoffR));
    }
}