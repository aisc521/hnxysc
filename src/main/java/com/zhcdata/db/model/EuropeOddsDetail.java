package com.zhcdata.db.model;

import java.util.Date;

public class EuropeOddsDetail {
    private Integer id;

    private Integer oddsid;

    private Float homewin;

    private Float standoff;

    private Float guestwin;

    private Date modifytime;

    private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOddsid() {
        return oddsid;
    }

    public void setOddsid(Integer oddsid) {
        this.oddsid = oddsid;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public boolean oddsEquals(EuropeOddsDetail xml) {
        return xml.getGuestwin().equals(guestwin) && xml.getStandoff().equals(standoff) && xml.getHomewin().equals(homewin) && xml.getModifytime().getTime() == modifytime.getTime();
    }
}