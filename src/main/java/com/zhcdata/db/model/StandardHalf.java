package com.zhcdata.db.model;

import java.util.Date;

public class StandardHalf {
    private Integer oddsid;

    private Integer scheduleid;

    private Integer companyid;

    private Float firsthomewin;

    private Float firststandoff;

    private Float firstguestwin;

    private Float homewin;

    private Float standoff;

    private Float guestwin;

    private Date modifytime;

    private Float homewinR;

    private Float standoffR;

    private Float guestwinR;

    //private Date starttime;

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

    public Float getFirsthomewin() {
        return firsthomewin;
    }

    public void setFirsthomewin(Float firsthomewin) {
        this.firsthomewin = firsthomewin;
    }

    public Float getFirststandoff() {
        return firststandoff;
    }

    public void setFirststandoff(Float firststandoff) {
        this.firststandoff = firststandoff;
    }

    public Float getFirstguestwin() {
        return firstguestwin;
    }

    public void setFirstguestwin(Float firstguestwin) {
        this.firstguestwin = firstguestwin;
    }

    public Float getHomewin() {
        return homewin;
    }

    public void setHomewin(Float homewin) {
        this.homewin = homewin;
    }

    public Float getStandoff() {
        return standoff;
    }

    public void setStandoff(Float standoff) {
        this.standoff = standoff;
    }

    public Float getGuestwin() {
        return guestwin;
    }

    public void setGuestwin(Float guestwin) {
        this.guestwin = guestwin;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public Float getHomewinR() {
        return homewinR;
    }

    public void setHomewinR(Float homewinR) {
        this.homewinR = homewinR;
    }

    public Float getStandoffR() {
        return standoffR;
    }

    public void setStandoffR(Float standoffR) {
        this.standoffR = standoffR;
    }

    public Float getGuestwinR() {
        return guestwinR;
    }

    public void setGuestwinR(Float guestwinR) {
        this.guestwinR = guestwinR;
    }

    //public Date getStarttime() {
    //    return starttime;
    //}

    //public void setStarttime(Date starttime) {
    //    this.starttime = starttime;
    //}

    public boolean oddsEquals(StandardHalf mo) {
        return mo.getHomewinR().equals(homewinR) && mo.getStandoffR().equals(standoffR) && guestwinR.equals(mo.getGuestwinR());
    }
}