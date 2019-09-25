package com.zhcdata.db.model;

import java.util.Objects;

public class TTSInfo {
    private Integer id;

    private Integer scheduleid;

    private Integer teamid;

    private Boolean kickofffirst;

    private Boolean firstcorner;

    private Boolean lastcorner;

    private Boolean firstyellow;

    private Boolean lastyellow;

    private Boolean firstsubst;

    private Boolean lastsubst;

    private Boolean firstoffside;

    private Boolean lastoffside;

    private Integer shots;

    private Integer target;

    private Integer offtarget;

    private Integer hitwoodwork;

    private Integer fouls;

    private Integer corner;

    private Integer cornerover;

    private Integer freekick;

    private Integer offside;

    private Integer offsideover;

    private Integer owngoal;

    private Integer yellow;

    private Integer yellowover;

    private Integer red;

    private Integer header;

    private Integer save;

    private Integer gkpounced;

    private Integer lostball;

    private Integer stealsuc;

    private Integer holdup;

    private Integer blocked;

    private Integer longpass;

    private Integer shortpass;

    private Integer successcross;

    private Integer assists;

    private Integer subst;

    private Integer substover;

    private Integer dribbles;

    private Integer throwins;

    private Integer controlpercent;

    private Integer tackle;

    private Integer passball;

    private Integer passballsuc;

    private Integer headersuc;

    private Float rating;

    private Short attack;

    private Short dangerousatt;

    private Integer cornerhalf;

    private Integer controlpercenthalf;

    private String modifytime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScheduleid() {
        return scheduleid;
    }

    public void setScheduleid(Integer scheduleid) {
        this.scheduleid = scheduleid;
    }

    public Integer getTeamid() {
        return teamid;
    }

    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
    }

    public Boolean getKickofffirst() {
        return kickofffirst;
    }

    public void setKickofffirst(Boolean kickofffirst) {
        this.kickofffirst = kickofffirst;
    }

    public Boolean getFirstcorner() {
        return firstcorner;
    }

    public void setFirstcorner(Boolean firstcorner) {
        this.firstcorner = firstcorner;
    }

    public Boolean getLastcorner() {
        return lastcorner;
    }

    public void setLastcorner(Boolean lastcorner) {
        this.lastcorner = lastcorner;
    }

    public Boolean getFirstyellow() {
        return firstyellow;
    }

    public void setFirstyellow(Boolean firstyellow) {
        this.firstyellow = firstyellow;
    }

    public Boolean getLastyellow() {
        return lastyellow;
    }

    public void setLastyellow(Boolean lastyellow) {
        this.lastyellow = lastyellow;
    }

    public Boolean getFirstsubst() {
        return firstsubst;
    }

    public void setFirstsubst(Boolean firstsubst) {
        this.firstsubst = firstsubst;
    }

    public Boolean getLastsubst() {
        return lastsubst;
    }

    public void setLastsubst(Boolean lastsubst) {
        this.lastsubst = lastsubst;
    }

    public Boolean getFirstoffside() {
        return firstoffside;
    }

    public void setFirstoffside(Boolean firstoffside) {
        this.firstoffside = firstoffside;
    }

    public Boolean getLastoffside() {
        return lastoffside;
    }

    public void setLastoffside(Boolean lastoffside) {
        this.lastoffside = lastoffside;
    }

    public Integer getShots() {
        return shots;
    }

    public void setShots(Integer shots) {
        this.shots = shots;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public Integer getOfftarget() {
        return offtarget;
    }

    public void setOfftarget(Integer offtarget) {
        this.offtarget = offtarget;
    }

    public Integer getHitwoodwork() {
        return hitwoodwork;
    }

    public void setHitwoodwork(Integer hitwoodwork) {
        this.hitwoodwork = hitwoodwork;
    }

    public Integer getFouls() {
        return fouls;
    }

    public void setFouls(Integer fouls) {
        this.fouls = fouls;
    }

    public Integer getCorner() {
        return corner;
    }

    public void setCorner(Integer corner) {
        this.corner = corner;
    }

    public Integer getCornerover() {
        return cornerover;
    }

    public void setCornerover(Integer cornerover) {
        this.cornerover = cornerover;
    }

    public Integer getFreekick() {
        return freekick;
    }

    public void setFreekick(Integer freekick) {
        this.freekick = freekick;
    }

    public Integer getOffside() {
        return offside;
    }

    public void setOffside(Integer offside) {
        this.offside = offside;
    }

    public Integer getOffsideover() {
        return offsideover;
    }

    public void setOffsideover(Integer offsideover) {
        this.offsideover = offsideover;
    }

    public Integer getOwngoal() {
        return owngoal;
    }

    public void setOwngoal(Integer owngoal) {
        this.owngoal = owngoal;
    }

    public Integer getYellow() {
        return yellow;
    }

    public void setYellow(Integer yellow) {
        this.yellow = yellow;
    }

    public Integer getYellowover() {
        return yellowover;
    }

    public void setYellowover(Integer yellowover) {
        this.yellowover = yellowover;
    }

    public Integer getRed() {
        return red;
    }

    public void setRed(Integer red) {
        this.red = red;
    }

    public Integer getHeader() {
        return header;
    }

    public void setHeader(Integer header) {
        this.header = header;
    }

    public Integer getSave() {
        return save;
    }

    public void setSave(Integer save) {
        this.save = save;
    }

    public Integer getGkpounced() {
        return gkpounced;
    }

    public void setGkpounced(Integer gkpounced) {
        this.gkpounced = gkpounced;
    }

    public Integer getLostball() {
        return lostball;
    }

    public void setLostball(Integer lostball) {
        this.lostball = lostball;
    }

    public Integer getStealsuc() {
        return stealsuc;
    }

    public void setStealsuc(Integer stealsuc) {
        this.stealsuc = stealsuc;
    }

    public Integer getHoldup() {
        return holdup;
    }

    public void setHoldup(Integer holdup) {
        this.holdup = holdup;
    }

    public Integer getBlocked() {
        return blocked;
    }

    public void setBlocked(Integer blocked) {
        this.blocked = blocked;
    }

    public Integer getLongpass() {
        return longpass;
    }

    public void setLongpass(Integer longpass) {
        this.longpass = longpass;
    }

    public Integer getShortpass() {
        return shortpass;
    }

    public void setShortpass(Integer shortpass) {
        this.shortpass = shortpass;
    }

    public Integer getSuccesscross() {
        return successcross;
    }

    public void setSuccesscross(Integer successcross) {
        this.successcross = successcross;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public Integer getSubst() {
        return subst;
    }

    public void setSubst(Integer subst) {
        this.subst = subst;
    }

    public Integer getSubstover() {
        return substover;
    }

    public void setSubstover(Integer substover) {
        this.substover = substover;
    }

    public Integer getDribbles() {
        return dribbles;
    }

    public void setDribbles(Integer dribbles) {
        this.dribbles = dribbles;
    }

    public Integer getThrowins() {
        return throwins;
    }

    public void setThrowins(Integer throwins) {
        this.throwins = throwins;
    }

    public Integer getControlpercent() {
        return controlpercent;
    }

    public void setControlpercent(Integer controlpercent) {
        this.controlpercent = controlpercent;
    }

    public Integer getTackle() {
        return tackle;
    }

    public void setTackle(Integer tackle) {
        this.tackle = tackle;
    }

    public Integer getPassball() {
        return passball;
    }

    public void setPassball(Integer passball) {
        this.passball = passball;
    }

    public Integer getPassballsuc() {
        return passballsuc;
    }

    public void setPassballsuc(Integer passballsuc) {
        this.passballsuc = passballsuc;
    }

    public Integer getHeadersuc() {
        return headersuc;
    }

    public void setHeadersuc(Integer headersuc) {
        this.headersuc = headersuc;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Short getAttack() {
        return attack;
    }

    public void setAttack(Short attack) {
        this.attack = attack;
    }

    public Short getDangerousatt() {
        return dangerousatt;
    }

    public void setDangerousatt(Short dangerousatt) {
        this.dangerousatt = dangerousatt;
    }

    public Integer getCornerhalf() {
        return cornerhalf;
    }

    public void setCornerhalf(Integer cornerhalf) {
        this.cornerhalf = cornerhalf;
    }

    public Integer getControlpercenthalf() {
        return controlpercenthalf;
    }

    public void setControlpercenthalf(Integer controlpercenthalf) {
        this.controlpercenthalf = controlpercenthalf;
    }

    public String getModifytime() {
        return modifytime;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TTSInfo info = (TTSInfo) o;
        return  Objects.equals(scheduleid, info.scheduleid) &&
                Objects.equals(teamid, info.teamid) &&
                Objects.equals(kickofffirst, info.kickofffirst) &&
                Objects.equals(firstcorner, info.firstcorner) &&
                Objects.equals(lastcorner, info.lastcorner) &&
                Objects.equals(firstyellow, info.firstyellow) &&
                Objects.equals(lastyellow, info.lastyellow) &&
                Objects.equals(firstsubst, info.firstsubst) &&
                Objects.equals(lastsubst, info.lastsubst) &&
                Objects.equals(firstoffside, info.firstoffside) &&
                Objects.equals(lastoffside, info.lastoffside) &&
                Objects.equals(shots, info.shots) &&
                Objects.equals(target, info.target) &&
                Objects.equals(offtarget, info.offtarget) &&
                Objects.equals(hitwoodwork, info.hitwoodwork) &&
                Objects.equals(fouls, info.fouls) &&
                Objects.equals(corner, info.corner) &&
                Objects.equals(cornerover, info.cornerover) &&
                Objects.equals(freekick, info.freekick) &&
                Objects.equals(offside, info.offside) &&
                Objects.equals(offsideover, info.offsideover) &&
                Objects.equals(owngoal, info.owngoal) &&
                Objects.equals(yellow, info.yellow) &&
                Objects.equals(yellowover, info.yellowover) &&
                Objects.equals(red, info.red) &&
                Objects.equals(header, info.header) &&
                Objects.equals(save, info.save) &&
                Objects.equals(gkpounced, info.gkpounced) &&
                Objects.equals(lostball, info.lostball) &&
                Objects.equals(stealsuc, info.stealsuc) &&
                Objects.equals(holdup, info.holdup) &&
                Objects.equals(blocked, info.blocked) &&
                Objects.equals(longpass, info.longpass) &&
                Objects.equals(shortpass, info.shortpass) &&
                Objects.equals(successcross, info.successcross) &&
                Objects.equals(assists, info.assists) &&
                Objects.equals(subst, info.subst) &&
                Objects.equals(substover, info.substover) &&
                Objects.equals(dribbles, info.dribbles) &&
                Objects.equals(throwins, info.throwins) &&
                Objects.equals(controlpercent, info.controlpercent) &&
                Objects.equals(tackle, info.tackle) &&
                Objects.equals(passball, info.passball) &&
                Objects.equals(passballsuc, info.passballsuc) &&
                Objects.equals(headersuc, info.headersuc) &&
                Objects.equals(rating, info.rating) &&
                Objects.equals(attack, info.attack) &&
                Objects.equals(dangerousatt, info.dangerousatt) &&
                Objects.equals(cornerhalf, info.cornerhalf) &&
                Objects.equals(controlpercenthalf, info.controlpercenthalf);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, scheduleid, teamid, kickofffirst, firstcorner, lastcorner, firstyellow, lastyellow, firstsubst, lastsubst, firstoffside, lastoffside, shots, target, offtarget, hitwoodwork, fouls, corner, cornerover, freekick, offside, offsideover, owngoal, yellow, yellowover, red, header, save, gkpounced, lostball, stealsuc, holdup, blocked, longpass, shortpass, successcross, assists, subst, substover, dribbles, throwins, controlpercent, tackle, passball, passballsuc, headersuc, rating, attack, dangerousatt, cornerhalf, controlpercenthalf, modifytime);
    }
}
