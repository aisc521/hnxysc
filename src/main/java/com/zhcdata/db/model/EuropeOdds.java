package com.zhcdata.db.model;

import java.util.Date;

public class EuropeOdds {
    private Integer oddsid;

    private Integer scheduleid;

    private Integer companyid;

    private Float firsthomewin;

    private Float firststandoff;

    private Float firstguestwin;

    private Float realhomewin;

    private Float realstandoff;

    private Float realguestwin;

    private Date modifytime;

    private Float homewinR;

    private Float guestwinR;

    private Float standoffR;

    private Byte isstoplive;

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

    public Float getRealhomewin() {
        return realhomewin;
    }

    public void setRealhomewin(Float realhomewin) {
        this.realhomewin = realhomewin;
    }

    public Float getRealstandoff() {
        return realstandoff;
    }

    public void setRealstandoff(Float realstandoff) {
        this.realstandoff = realstandoff;
    }

    public Float getRealguestwin() {
        return realguestwin;
    }

    public void setRealguestwin(Float realguestwin) {
        this.realguestwin = realguestwin;
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

    public Float getGuestwinR() {
        return guestwinR;
    }

    public void setGuestwinR(Float guestwinR) {
        this.guestwinR = guestwinR;
    }

    public Float getStandoffR() {
        return standoffR;
    }

    public void setStandoffR(Float standoffR) {
        this.standoffR = standoffR;
    }

    public Byte getIsstoplive() {
        return isstoplive;
    }

    public void setIsstoplive(Byte isstoplive) {
        this.isstoplive = isstoplive;
    }

    public boolean oddsEquals(EuropeOdds xml) {
        return xml.getRealhomewin().equals(realhomewin)&&xml.getRealstandoff().equals(realstandoff)&&xml.getRealguestwin().equals(realguestwin)&&xml.getModifytime().getTime()==modifytime.getTime();
    }
}