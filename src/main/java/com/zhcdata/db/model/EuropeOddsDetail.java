package com.zhcdata.db.model;

import lombok.ToString;

import java.util.Date;

@ToString
public class EuropeOddsDetail {
    private Integer id;

    private Integer oddsid;

    private Float homewin;

    private Float standoff;

    private Float guestwin;

    private Date modifytime;

    private Integer type;

    private boolean first = false;

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

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
        try {
            if (modifytime == null && xml.getModifytime() == null)
                return true;//两个都没有变化时间，认为他们没变
            if (modifytime == null && xml.getModifytime() != null)
                return false;
            if (modifytime != null && xml.getModifytime() == null)
                return false;
            else
                return xml.getGuestwin().equals(guestwin) && xml.getStandoff().equals(standoff) && xml.getHomewin().equals(homewin) && xml.getModifytime().getTime() == modifytime.getTime();
        } catch (Exception e) {
            System.err.println("89UASFU89HAKJN" + e.toString());
            e.printStackTrace();
            return false;
        }
    }
}