package com.zhcdata.db.model;

import java.util.Date;
import java.util.Objects;

public class SclassInfo {
    private Integer sclassid;

    private String color;

    private String nameJ;

    private String nameF;

    private String nameE;

    private String nameJs;

    private String nameFs;

    private String nameEs;

    private String nameS;

    private Short kind;

    private Short mode;

    private Short countRound;

    private Short currRound;

    private String currMatchseason;

    private String sclassPic;

    private Integer ifstop;

    private Integer sclassType;

    private Short countGroup;

    private Integer bfSimplyDisp;

    private Short sclassSequence;

    private Short infoid;

    private Integer bfIfdisp;

    private Date modifytime;

    private String beginseason;

    private Integer subsclassid;

    private Boolean ifhavesub;

    private Boolean ifsort;

    private Boolean ifhavepaper;

    private Boolean ifshowscore;

    private String rankMatchseason;

    private String sclassRule;

    public Integer getSclassid() {
        return sclassid;
    }

    public void setSclassid(Integer sclassid) {
        this.sclassid = sclassid;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    public String getNameJ() {
        return nameJ;
    }

    public void setNameJ(String nameJ) {
        this.nameJ = nameJ == null ? null : nameJ.trim();
    }

    public String getNameF() {
        return nameF;
    }

    public void setNameF(String nameF) {
        this.nameF = nameF == null ? null : nameF.trim();
    }

    public String getNameE() {
        return nameE;
    }

    public void setNameE(String nameE) {
        this.nameE = nameE == null ? null : nameE.trim();
    }

    public String getNameJs() {
        return nameJs;
    }

    public void setNameJs(String nameJs) {
        this.nameJs = nameJs == null ? null : nameJs.trim();
    }

    public String getNameFs() {
        return nameFs;
    }

    public void setNameFs(String nameFs) {
        this.nameFs = nameFs == null ? null : nameFs.trim();
    }

    public String getNameEs() {
        return nameEs;
    }

    public void setNameEs(String nameEs) {
        this.nameEs = nameEs == null ? null : nameEs.trim();
    }

    public String getNameS() {
        return nameS;
    }

    public void setNameS(String nameS) {
        this.nameS = nameS == null ? null : nameS.trim();
    }

    public Short getKind() {
        return kind;
    }

    public void setKind(Short kind) {
        this.kind = kind;
    }

    public Short getMode() {
        return mode;
    }

    public void setMode(Short mode) {
        this.mode = mode;
    }

    public Short getCountRound() {
        return countRound;
    }

    public void setCountRound(Short countRound) {
        this.countRound = countRound;
    }

    public Short getCurrRound() {
        return currRound;
    }

    public void setCurrRound(Short currRound) {
        this.currRound = currRound;
    }

    public String getCurrMatchseason() {
        return currMatchseason;
    }

    public void setCurrMatchseason(String currMatchseason) {
        this.currMatchseason = currMatchseason == null ? null : currMatchseason.trim();
    }

    public String getSclassPic() {
        return sclassPic;
    }

    public void setSclassPic(String sclassPic) {
        this.sclassPic = sclassPic == null ? null : sclassPic.trim();
    }

    public Integer getIfstop() {
        return ifstop;
    }

    public void setIfstop(Integer ifstop) {
        this.ifstop = ifstop;
    }

    public Integer getSclassType() {
        return sclassType;
    }

    public void setSclassType(Integer sclassType) {
        this.sclassType = sclassType;
    }

    public Short getCountGroup() {
        return countGroup;
    }

    public void setCountGroup(Short countGroup) {
        this.countGroup = countGroup;
    }

    public Integer getBfSimplyDisp() {
        return bfSimplyDisp;
    }

    public void setBfSimplyDisp(Integer bfSimplyDisp) {
        this.bfSimplyDisp = bfSimplyDisp;
    }

    public Short getSclassSequence() {
        return sclassSequence;
    }

    public void setSclassSequence(Short sclassSequence) {
        this.sclassSequence = sclassSequence;
    }

    public Short getInfoid() {
        return infoid;
    }

    public void setInfoid(Short infoid) {
        this.infoid = infoid;
    }

    public Integer getBfIfdisp() {
        return bfIfdisp;
    }

    public void setBfIfdisp(Integer bfIfdisp) {
        this.bfIfdisp = bfIfdisp;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public String getBeginseason() {
        return beginseason;
    }

    public void setBeginseason(String beginseason) {
        this.beginseason = beginseason == null ? null : beginseason.trim();
    }

    public Integer getSubsclassid() {
        return subsclassid;
    }

    public void setSubsclassid(Integer subsclassid) {
        this.subsclassid = subsclassid;
    }

    public Boolean getIfhavesub() {
        return ifhavesub;
    }

    public void setIfhavesub(Boolean ifhavesub) {
        this.ifhavesub = ifhavesub;
    }

    public Boolean getIfsort() {
        return ifsort;
    }

    public void setIfsort(Boolean ifsort) {
        this.ifsort = ifsort;
    }

    public Boolean getIfhavepaper() {
        return ifhavepaper;
    }

    public void setIfhavepaper(Boolean ifhavepaper) {
        this.ifhavepaper = ifhavepaper;
    }

    public Boolean getIfshowscore() {
        return ifshowscore;
    }

    public void setIfshowscore(Boolean ifshowscore) {
        this.ifshowscore = ifshowscore;
    }

    public String getRankMatchseason() {
        return rankMatchseason;
    }

    public void setRankMatchseason(String rankMatchseason) {
        this.rankMatchseason = rankMatchseason == null ? null : rankMatchseason.trim();
    }

    public String getSclassRule() {
        return sclassRule;
    }

    public void setSclassRule(String sclassRule) {
        this.sclassRule = sclassRule == null ? null : sclassRule.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SclassInfo info = (SclassInfo) o;
        return Objects.equals(sclassid, info.sclassid) &&
                Objects.equals(color, info.color) &&
                Objects.equals(nameJ, info.nameJ) &&
                Objects.equals(nameF, info.nameF) &&
                Objects.equals(nameE, info.nameE) &&
                Objects.equals(nameJs, info.nameJs) &&
                Objects.equals(nameFs, info.nameFs) &&
                Objects.equals(nameEs, info.nameEs) &&
                Objects.equals(kind, info.kind) &&
                Objects.equals(mode, info.mode) &&
                Objects.equals(countRound, info.countRound) &&
                Objects.equals(currRound, info.currRound) &&
                Objects.equals(currMatchseason, info.currMatchseason) &&
                Objects.equals(sclassPic, info.sclassPic);
    }

    @Override
    public int hashCode() {

        return Objects.hash(sclassid, color, nameJ, nameF, nameE, nameJs, nameFs, nameEs, nameS, kind, mode, countRound, currRound, currMatchseason, sclassPic, ifstop, sclassType, countGroup, bfSimplyDisp, sclassSequence, infoid, bfIfdisp, modifytime, beginseason, subsclassid, ifhavesub, ifsort, ifhavepaper, ifshowscore, rankMatchseason, sclassRule);
    }
}
