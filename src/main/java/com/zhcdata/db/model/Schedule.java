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

}