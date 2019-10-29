package com.zhcdata.db.model;

import lombok.ToString;

import java.util.Date;

@ToString
public class StandardDetail {
    private Integer id;

    private Integer oddsid;

    private Float homewin;

    private Float standoff;

    private Float guestwin;

    private Date modifytime;

    private Boolean isearly;

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

    public Boolean getIsearly() {
        return isearly;
    }

    public void setIsearly(Boolean isearly) {
        this.isearly = isearly;
    }

    public boolean oddsEquals(StandardDetail db) {

        if (this.getHomewin()==null && db.getHomewin()!=null)
            return false;
        if (this.getGuestwin()==null && db.getGuestwin()!=null)
            return false;
        if (this.getHomewin()!=null && db.getHomewin()==null)
            return false;
        if (this.getGuestwin()!=null && db.getGuestwin()==null)
            return false;

        if (db == null) return false;
        if (db.getModifytime() != null && modifytime != null)
            if (db.getModifytime().getTime() > modifytime.getTime())
                return true;
        if ((db.getModifytime() == null || modifytime == null) && (db.getModifytime() != null || modifytime != null))
            return false;
        return db.getHomewin().equals(homewin) && db.getGuestwin().equals(guestwin) && db.getStandoff().equals(standoff);
    }
}