package com.zhcdata.db.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

@Data
@Table(name = "schedule")
public class Schedule implements Serializable {
    private static final long serialVersionUID = -8917208735051005037L;
    @Id
    @Column(name = "ScheduleID")
    private Integer scheduleid;

    @Column(name = "SclassID")
    private Integer sclassid;

    @Column(name = "MatchSeason")
    private String matchseason;

    private Short round;

    private String grouping;

    @Column(name = "HomeTeamID")
    private Integer hometeamid;

    @Column(name = "GuestTeamID")
    private Integer guestteamid;

    @Column(name = "HomeTeam")
    private String hometeam;

    @Column(name = "GuestTeam")
    private String guestteam;

    @Column(name = "Neutrality")
    private Boolean neutrality;

    @Column(name = "MatchTime")
    private Date matchtime;

    @Column(name = "MatchTime2")
    private String matchtime2;

    @Column(name = "Location")
    private String location;

    @Column(name = "Home_Order")
    private String homeOrder;

    @Column(name = "Guest_Order")
    private String guestOrder;

    @Column(name = "MatchState")
    private Short matchstate;

    @Column(name = "WeatherIcon")
    private Short weathericon;

    @Column(name = "Weather")
    private String weather;

    @Column(name = "Temperature")
    private String temperature;

    @Column(name = "TV")
    private String tv;

    @Column(name = "Umpire")
    private String umpire;

    @Column(name = "Visitor")
    private Integer visitor;

    @Column(name = "HomeScore")
    private Short homescore;

    @Column(name = "GuestScore")
    private Short guestscore;

    @Column(name = "HomeHalfScore")
    private Short homehalfscore;

    @Column(name = "GuestHalfScore")
    private Short guesthalfscore;

    @Column(name = "Home_Red")
    private Short homeRed;

    @Column(name = "Guest_Red")
    private Short guestRed;

    @Column(name = "Home_Yellow")
    private Short homeYellow;

    @Column(name = "Guest_Yellow")
    private Short guestYellow;

    @Column(name = "bf_changetime")
    private String bfChangetime;

    private Integer shangpan;

    private String grouping2;

    @Column(name = "bfShow")
    private Boolean bfshow;

    @Column(name = "subSclassID")
    private Integer subsclassid;

    @Column(name = "Explainlist")
    private String explainlist;

    @Column(name = "VenuesID")
    private Integer venuesid;

    @Column(name = "isGuessRed")
    private Boolean isguessred;

    @Column(name = "isLive")
    private Boolean islive;

    @Column(name = "homeCorner")
    private Integer homecorner;

    @Column(name = "homeCornerHalf")
    private Integer homecornerhalf;

    @Column(name = "guestCorner")
    private Integer guestcorner;

    @Column(name = "guestCornerHalf")
    private Integer guestcornerhalf;

    @Column(name = "Explain")
    private String explain;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
/*
        System.err.println("---------"+scheduleid+"---------");
        System.out.println(Objects.equals(sclassid, schedule.sclassid));
        System.out.println(Objects.equals(matchseason, schedule.matchseason));
        //System.out.println(Objects.equals(round, schedule.round));
        System.out.println(Objects.equals(grouping, schedule.grouping));
        System.out.println(Objects.equals(hometeamid, schedule.hometeamid));
        System.out.println(Objects.equals(guestteamid, schedule.guestteamid));
        System.out.println(Objects.equals(hometeam, schedule.hometeam));
        System.out.println(Objects.equals(guestteam, schedule.guestteam));
        System.out.println(Objects.equals(neutrality, schedule.neutrality));
        System.out.println(Objects.equals(matchtime, schedule.matchtime));
        System.out.println(Objects.equals(location, schedule.location));
        System.out.println(Objects.equals(homeOrder, schedule.homeOrder));
        System.out.println(Objects.equals(guestOrder, schedule.guestOrder));
        System.out.println(Objects.equals(matchstate, schedule.matchstate));
        System.out.println(Objects.equals(weathericon, schedule.weathericon));
        System.out.println(Objects.equals(weather, schedule.weather));
        System.out.println(Objects.equals(temperature, schedule.temperature));
        System.out.println(Objects.equals(tv, schedule.tv));
        System.out.println(Objects.equals(umpire, schedule.umpire));
        System.out.println(Objects.equals(visitor, schedule.visitor));
        System.out.println(Objects.equals(homescore, schedule.homescore));
        System.out.println(Objects.equals(guestscore, schedule.guestscore));
        System.out.println(Objects.equals(homehalfscore, schedule.homehalfscore));
        System.out.println(Objects.equals(guesthalfscore, schedule.guesthalfscore));
        System.out.println(Objects.equals(homeRed, schedule.homeRed));
        System.out.println(Objects.equals(guestRed, schedule.guestRed));
        System.out.println(Objects.equals(homeYellow, schedule.homeYellow));
        System.out.println(Objects.equals(guestYellow, schedule.guestYellow));
        System.out.println(Objects.equals(bfshow, schedule.bfshow));
        System.out.println(Objects.equals(subsclassid, schedule.subsclassid));
        //System.out.println(Objects.equals(venuesid, schedule.venuesid));
        //System.out.println(Objects.equals(isguessred, schedule.isguessred));
        //System.out.println(Objects.equals(islive, schedule.islive));

*/
        return Objects.equals(sclassid, schedule.sclassid) &&
                Objects.equals(matchseason, schedule.matchseason) &&
                //Objects.equals(round, schedule.round) &&
                Objects.equals(grouping, schedule.grouping) &&
                Objects.equals(hometeamid, schedule.hometeamid) &&
                Objects.equals(guestteamid, schedule.guestteamid) &&
                Objects.equals(hometeam, schedule.hometeam) &&
                Objects.equals(guestteam, schedule.guestteam) &&
                Objects.equals(neutrality, schedule.neutrality) &&
                Objects.equals(matchtime, schedule.matchtime) &&
                Objects.equals(location, schedule.location) &&
                Objects.equals(homeOrder, schedule.homeOrder) &&
                Objects.equals(guestOrder, schedule.guestOrder) &&
                Objects.equals(matchstate, schedule.matchstate) &&
                Objects.equals(weathericon, schedule.weathericon) &&
                Objects.equals(weather, schedule.weather) &&
                Objects.equals(temperature, schedule.temperature) &&
                Objects.equals(tv, schedule.tv) &&
                Objects.equals(umpire, schedule.umpire) &&
                Objects.equals(visitor, schedule.visitor) &&
                Objects.equals(homescore, schedule.homescore) &&
                Objects.equals(guestscore, schedule.guestscore) &&
                Objects.equals(homehalfscore, schedule.homehalfscore) &&
                Objects.equals(guesthalfscore, schedule.guesthalfscore) &&
                Objects.equals(homeRed, schedule.homeRed) &&
                Objects.equals(guestRed, schedule.guestRed) &&
                Objects.equals(homeYellow, schedule.homeYellow) &&
                Objects.equals(guestYellow, schedule.guestYellow) &&
                Objects.equals(bfshow, schedule.bfshow) &&
                Objects.equals(subsclassid, schedule.subsclassid);
                //Objects.equals(venuesid, schedule.venuesid);
                //Objects.equals(isguessred, schedule.isguessred) &&
                //Objects.equals(islive, schedule.islive);






    }

    @Override
    public int hashCode() {
        return Objects.hash(sclassid, matchseason, round, grouping, hometeamid, guestteamid, hometeam, guestteam, neutrality, matchtime, location, homeOrder, guestOrder, matchstate, weathericon, weather, temperature, tv, umpire, visitor, homescore, guestscore, homehalfscore, guesthalfscore, homeRed, guestRed, homeYellow, guestYellow, bfshow, subsclassid, venuesid, isguessred, islive);
    }

    //private static final long serialVersionUID = 1L;

    /**
     * @return ScheduleID
     */
    public Integer getScheduleid() {
        return scheduleid;
    }

    /**
     * @param scheduleid
     */
    public void setScheduleid(Integer scheduleid) {
        this.scheduleid = scheduleid;
    }

    /**
     * @return SclassID
     */
    public Integer getSclassid() {
        return sclassid;
    }

    /**
     * @param sclassid
     */
    public void setSclassid(Integer sclassid) {
        this.sclassid = sclassid;
    }

    /**
     * @return MatchSeason
     */
    public String getMatchseason() {
        return matchseason;
    }

    /**
     * @param matchseason
     */
    public void setMatchseason(String matchseason) {
        this.matchseason = matchseason;
    }

    /**
     * @return round
     */
    public Short getRound() {
        return round;
    }

    /**
     * @param round
     */
    public void setRound(Short round) {
        this.round = round;
    }

    /**
     * @return grouping
     */
    public String getGrouping() {
        return grouping;
    }

    /**
     * @param grouping
     */
    public void setGrouping(String grouping) {
        this.grouping = grouping;
    }

    /**
     * @return HomeTeamID
     */
    public Integer getHometeamid() {
        return hometeamid;
    }

    /**
     * @param hometeamid
     */
    public void setHometeamid(Integer hometeamid) {
        this.hometeamid = hometeamid;
    }

    /**
     * @return GuestTeamID
     */
    public Integer getGuestteamid() {
        return guestteamid;
    }

    /**
     * @param guestteamid
     */
    public void setGuestteamid(Integer guestteamid) {
        this.guestteamid = guestteamid;
    }

    /**
     * @return HomeTeam
     */
    public String getHometeam() {
        return hometeam;
    }

    /**
     * @param hometeam
     */
    public void setHometeam(String hometeam) {
        this.hometeam = hometeam;
    }

    /**
     * @return GuestTeam
     */
    public String getGuestteam() {
        return guestteam;
    }

    /**
     * @param guestteam
     */
    public void setGuestteam(String guestteam) {
        this.guestteam = guestteam;
    }

    /**
     * @return Neutrality
     */
    public Boolean getNeutrality() {
        return neutrality;
    }

    /**
     * @param neutrality
     */
    public void setNeutrality(Boolean neutrality) {
        this.neutrality = neutrality;
    }

    /**
     * @return MatchTime
     */
    public Date getMatchtime() {
        return matchtime;
    }

    /**
     * @param matchtime
     */
    public void setMatchtime(Date matchtime) {
        this.matchtime = matchtime;
    }

    /**
     * @return MatchTime2
     */
    public String getMatchtime2() {
        return matchtime2;
    }

    /**
     * @param matchtime2
     */
    public void setMatchtime2(String matchtime2) {
        this.matchtime2 = matchtime2;
    }

    /**
     * @return Location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return Home_Order
     */
    public String getHomeOrder() {
        return homeOrder;
    }

    /**
     * @param homeOrder
     */
    public void setHomeOrder(String homeOrder) {
        this.homeOrder = homeOrder;
    }

    /**
     * @return Guest_Order
     */
    public String getGuestOrder() {
        return guestOrder;
    }

    /**
     * @param guestOrder
     */
    public void setGuestOrder(String guestOrder) {
        this.guestOrder = guestOrder;
    }

    /**
     * @return MatchState
     */
    public Short getMatchstate() {
        return matchstate;
    }

    /**
     * @param matchstate
     */
    public void setMatchstate(Short matchstate) {
        this.matchstate = matchstate;
    }

    /**
     * @return WeatherIcon
     */
    public Short getWeathericon() {
        return weathericon;
    }

    /**
     * @param weathericon
     */
    public void setWeathericon(Short weathericon) {
        this.weathericon = weathericon;
    }

    /**
     * @return Weather
     */
    public String getWeather() {
        return weather;
    }

    /**
     * @param weather
     */
    public void setWeather(String weather) {
        this.weather = weather;
    }

    /**
     * @return Temperature
     */
    public String getTemperature() {
        return temperature;
    }

    /**
     * @param temperature
     */
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    /**
     * @return TV
     */
    public String getTv() {
        return tv;
    }

    /**
     * @param tv
     */
    public void setTv(String tv) {
        this.tv = tv;
    }

    /**
     * @return Umpire
     */
    public String getUmpire() {
        return umpire;
    }

    /**
     * @param umpire
     */
    public void setUmpire(String umpire) {
        this.umpire = umpire;
    }

    /**
     * @return Visitor
     */
    public Integer getVisitor() {
        return visitor;
    }

    /**
     * @param visitor
     */
    public void setVisitor(Integer visitor) {
        this.visitor = visitor;
    }

    /**
     * @return HomeScore
     */
    public Short getHomescore() {
        return homescore;
    }

    /**
     * @param homescore
     */
    public void setHomescore(Short homescore) {
        this.homescore = homescore;
    }

    /**
     * @return GuestScore
     */
    public Short getGuestscore() {
        return guestscore;
    }

    /**
     * @param guestscore
     */
    public void setGuestscore(Short guestscore) {
        this.guestscore = guestscore;
    }

    /**
     * @return HomeHalfScore
     */
    public Short getHomehalfscore() {
        return homehalfscore;
    }

    /**
     * @param homehalfscore
     */
    public void setHomehalfscore(Short homehalfscore) {
        this.homehalfscore = homehalfscore;
    }

    /**
     * @return GuestHalfScore
     */
    public Short getGuesthalfscore() {
        return guesthalfscore;
    }

    /**
     * @param guesthalfscore
     */
    public void setGuesthalfscore(Short guesthalfscore) {
        this.guesthalfscore = guesthalfscore;
    }

    /**
     * @return Home_Red
     */
    public Short getHomeRed() {
        return homeRed;
    }

    /**
     * @param homeRed
     */
    public void setHomeRed(Short homeRed) {
        this.homeRed = homeRed;
    }

    /**
     * @return Guest_Red
     */
    public Short getGuestRed() {
        return guestRed;
    }

    /**
     * @param guestRed
     */
    public void setGuestRed(Short guestRed) {
        this.guestRed = guestRed;
    }

    /**
     * @return Home_Yellow
     */
    public Short getHomeYellow() {
        return homeYellow;
    }

    /**
     * @param homeYellow
     */
    public void setHomeYellow(Short homeYellow) {
        this.homeYellow = homeYellow;
    }

    /**
     * @return Guest_Yellow
     */
    public Short getGuestYellow() {
        return guestYellow;
    }

    /**
     * @param guestYellow
     */
    public void setGuestYellow(Short guestYellow) {
        this.guestYellow = guestYellow;
    }

    /**
     * @return bf_changetime
     */
    public String getBfChangetime() {
        return bfChangetime;
    }

    /**
     * @param bfChangetime
     */
    public void setBfChangetime(String bfChangetime) {
        this.bfChangetime = bfChangetime;
    }

    /**
     * @return shangpan
     */
    public Integer getShangpan() {
        return shangpan;
    }

    /**
     * @param shangpan
     */
    public void setShangpan(Integer shangpan) {
        this.shangpan = shangpan;
    }

    /**
     * @return grouping2
     */
    public String getGrouping2() {
        return grouping2;
    }

    /**
     * @param grouping2
     */
    public void setGrouping2(String grouping2) {
        this.grouping2 = grouping2;
    }

    /**
     * @return bfShow
     */
    public Boolean getBfshow() {
        return bfshow;
    }

    /**
     * @param bfshow
     */
    public void setBfshow(Boolean bfshow) {
        this.bfshow = bfshow;
    }

    /**
     * @return subSclassID
     */
    public Integer getSubsclassid() {
        return subsclassid;
    }

    /**
     * @param subsclassid
     */
    public void setSubsclassid(Integer subsclassid) {
        this.subsclassid = subsclassid;
    }

    /**
     * @return Explainlist
     */
    public String getExplainlist() {
        return explainlist;
    }

    /**
     * @param explainlist
     */
    public void setExplainlist(String explainlist) {
        this.explainlist = explainlist;
    }

    /**
     * @return VenuesID
     */
    public Integer getVenuesid() {
        return venuesid;
    }

    /**
     * @param venuesid
     */
    public void setVenuesid(Integer venuesid) {
        this.venuesid = venuesid;
    }

    /**
     * @return isGuessRed
     */
    public Boolean getIsguessred() {
        return isguessred;
    }

    /**
     * @param isguessred
     */
    public void setIsguessred(Boolean isguessred) {
        this.isguessred = isguessred;
    }

    /**
     * @return isLive
     */
    public Boolean getIslive() {
        return islive;
    }

    /**
     * @param islive
     */
    public void setIslive(Boolean islive) {
        this.islive = islive;
    }

    /**
     * @return homeCorner
     */
    public Integer getHomecorner() {
        return homecorner;
    }

    /**
     * @param homecorner
     */
    public void setHomecorner(Integer homecorner) {
        this.homecorner = homecorner;
    }

    /**
     * @return homeCornerHalf
     */
    public Integer getHomecornerhalf() {
        return homecornerhalf;
    }

    /**
     * @param homecornerhalf
     */
    public void setHomecornerhalf(Integer homecornerhalf) {
        this.homecornerhalf = homecornerhalf;
    }

    /**
     * @return guestCorner
     */
    public Integer getGuestcorner() {
        return guestcorner;
    }

    /**
     * @param guestcorner
     */
    public void setGuestcorner(Integer guestcorner) {
        this.guestcorner = guestcorner;
    }

    /**
     * @return guestCornerHalf
     */
    public Integer getGuestcornerhalf() {
        return guestcornerhalf;
    }

    /**
     * @param guestcornerhalf
     */
    public void setGuestcornerhalf(Integer guestcornerhalf) {
        this.guestcornerhalf = guestcornerhalf;
    }

    /**
     * @return Explain
     */
    public String getExplain() {
        return explain;
    }

    /**
     * @param explain
     */
    public void setExplain(String explain) {
        this.explain = explain;
    }


    public boolean matchEquals(Schedule xml) {
        try {
            return xml.getGuestscore().equals(guestscore)&&xml.getHomescore().equals(homescore)&&xml.matchstate.equals(matchstate)&&xml.matchtime.getTime()==matchtime.getTime()&&homehalfscore.equals(xml.getHomehalfscore())&&xml.getGuesthalfscore().equals(guesthalfscore)&&xml.getHomeRed().equals(homeRed)&&guestRed.equals(xml.getGuestRed())&&xml.getHomeYellow().equals(homeYellow)&&xml.getGuestYellow().equals(guestYellow);
        }catch (Exception e){
            return false;
        }
    }
}