package com.zhcdata.db.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

public class Sclass implements Serializable {
    @Column(name = "sclassID")
    private Integer sclassid;

    @Column(name = "Color")
    private String color;

    @Column(name = "Name_J")
    private String nameJ;

    @Column(name = "Name_F")
    private String nameF;

    @Column(name = "Name_E")
    private String nameE;

    @Column(name = "Name_JS")
    private String nameJs;

    @Column(name = "Name_FS")
    private String nameFs;

    @Column(name = "Name_ES")
    private String nameEs;

    @Column(name = "Name_S")
    private String nameS;

    @Column(name = "Kind")
    private Short kind;

    @Column(name = "Mode")
    private Short mode;

    @Column(name = "count_round")
    private Short countRound;

    @Column(name = "curr_round")
    private Short currRound;

    @Column(name = "Curr_matchSeason")
    private String currMatchseason;

    @Column(name = "Sclass_pic")
    private String sclassPic;

    private Integer ifstop;

    @Column(name = "Sclass_type")
    private Integer sclassType;

    @Column(name = "count_group")
    private Short countGroup;

    @Column(name = "Bf_simply_disp")
    private Integer bfSimplyDisp;

    @Column(name = "sclass_sequence")
    private Short sclassSequence;

    @Column(name = "InfoID")
    private Short infoid;

    @Column(name = "Bf_IfDisp")
    private Integer bfIfdisp;

    @Column(name = "ModifyTime")
    private Date modifytime;

    @Column(name = "BeginSeason")
    private String beginseason;

    @Column(name = "subSclassID")
    private Integer subsclassid;

    @Column(name = "ifHaveSub")
    private Byte ifhavesub;

    @Column(name = "ifSort")
    private Byte ifsort;

    @Column(name = "ifHavePaper")
    private Byte ifhavepaper;

    @Column(name = "ifShowScore")
    private Byte ifshowscore;

    @Column(name = "rank_matchSeason")
    private String rankMatchseason;

    @Column(name = "sclass_rule")
    private String sclassRule;

    private static final long serialVersionUID = 1L;

    /**
     * @return sclassID
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
     * @return Color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return Name_J
     */
    public String getNameJ() {
        return nameJ;
    }

    /**
     * @param nameJ
     */
    public void setNameJ(String nameJ) {
        this.nameJ = nameJ;
    }

    /**
     * @return Name_F
     */
    public String getNameF() {
        return nameF;
    }

    /**
     * @param nameF
     */
    public void setNameF(String nameF) {
        this.nameF = nameF;
    }

    /**
     * @return Name_E
     */
    public String getNameE() {
        return nameE;
    }

    /**
     * @param nameE
     */
    public void setNameE(String nameE) {
        this.nameE = nameE;
    }

    /**
     * @return Name_JS
     */
    public String getNameJs() {
        return nameJs;
    }

    /**
     * @param nameJs
     */
    public void setNameJs(String nameJs) {
        this.nameJs = nameJs;
    }

    /**
     * @return Name_FS
     */
    public String getNameFs() {
        return nameFs;
    }

    /**
     * @param nameFs
     */
    public void setNameFs(String nameFs) {
        this.nameFs = nameFs;
    }

    /**
     * @return Name_ES
     */
    public String getNameEs() {
        return nameEs;
    }

    /**
     * @param nameEs
     */
    public void setNameEs(String nameEs) {
        this.nameEs = nameEs;
    }

    /**
     * @return Name_S
     */
    public String getNameS() {
        return nameS;
    }

    /**
     * @param nameS
     */
    public void setNameS(String nameS) {
        this.nameS = nameS;
    }

    /**
     * @return Kind
     */
    public Short getKind() {
        return kind;
    }

    /**
     * @param kind
     */
    public void setKind(Short kind) {
        this.kind = kind;
    }

    /**
     * @return Mode
     */
    public Short getMode() {
        return mode;
    }

    /**
     * @param mode
     */
    public void setMode(Short mode) {
        this.mode = mode;
    }

    /**
     * @return count_round
     */
    public Short getCountRound() {
        return countRound;
    }

    /**
     * @param countRound
     */
    public void setCountRound(Short countRound) {
        this.countRound = countRound;
    }

    /**
     * @return curr_round
     */
    public Short getCurrRound() {
        return currRound;
    }

    /**
     * @param currRound
     */
    public void setCurrRound(Short currRound) {
        this.currRound = currRound;
    }

    /**
     * @return Curr_matchSeason
     */
    public String getCurrMatchseason() {
        return currMatchseason;
    }

    /**
     * @param currMatchseason
     */
    public void setCurrMatchseason(String currMatchseason) {
        this.currMatchseason = currMatchseason;
    }

    /**
     * @return Sclass_pic
     */
    public String getSclassPic() {
        return sclassPic;
    }

    /**
     * @param sclassPic
     */
    public void setSclassPic(String sclassPic) {
        this.sclassPic = sclassPic;
    }

    /**
     * @return ifstop
     */
    public Integer getIfstop() {
        return ifstop;
    }

    /**
     * @param ifstop
     */
    public void setIfstop(Integer ifstop) {
        this.ifstop = ifstop;
    }

    /**
     * @return Sclass_type
     */
    public Integer getSclassType() {
        return sclassType;
    }

    /**
     * @param sclassType
     */
    public void setSclassType(Integer sclassType) {
        this.sclassType = sclassType;
    }

    /**
     * @return count_group
     */
    public Short getCountGroup() {
        return countGroup;
    }

    /**
     * @param countGroup
     */
    public void setCountGroup(Short countGroup) {
        this.countGroup = countGroup;
    }

    /**
     * @return Bf_simply_disp
     */
    public Integer getBfSimplyDisp() {
        return bfSimplyDisp;
    }

    /**
     * @param bfSimplyDisp
     */
    public void setBfSimplyDisp(Integer bfSimplyDisp) {
        this.bfSimplyDisp = bfSimplyDisp;
    }

    /**
     * @return sclass_sequence
     */
    public Short getSclassSequence() {
        return sclassSequence;
    }

    /**
     * @param sclassSequence
     */
    public void setSclassSequence(Short sclassSequence) {
        this.sclassSequence = sclassSequence;
    }

    /**
     * @return InfoID
     */
    public Short getInfoid() {
        return infoid;
    }

    /**
     * @param infoid
     */
    public void setInfoid(Short infoid) {
        this.infoid = infoid;
    }

    /**
     * @return Bf_IfDisp
     */
    public Integer getBfIfdisp() {
        return bfIfdisp;
    }

    /**
     * @param bfIfdisp
     */
    public void setBfIfdisp(Integer bfIfdisp) {
        this.bfIfdisp = bfIfdisp;
    }

    /**
     * @return ModifyTime
     */
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * @param modifytime
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * @return BeginSeason
     */
    public String getBeginseason() {
        return beginseason;
    }

    /**
     * @param beginseason
     */
    public void setBeginseason(String beginseason) {
        this.beginseason = beginseason;
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
     * @return ifHaveSub
     */
    public Byte getIfhavesub() {
        return ifhavesub;
    }

    /**
     * @param ifhavesub
     */
    public void setIfhavesub(Byte ifhavesub) {
        this.ifhavesub = ifhavesub;
    }

    /**
     * @return ifSort
     */
    public Byte getIfsort() {
        return ifsort;
    }

    /**
     * @param ifsort
     */
    public void setIfsort(Byte ifsort) {
        this.ifsort = ifsort;
    }

    /**
     * @return ifHavePaper
     */
    public Byte getIfhavepaper() {
        return ifhavepaper;
    }

    /**
     * @param ifhavepaper
     */
    public void setIfhavepaper(Byte ifhavepaper) {
        this.ifhavepaper = ifhavepaper;
    }

    /**
     * @return ifShowScore
     */
    public Byte getIfshowscore() {
        return ifshowscore;
    }

    /**
     * @param ifshowscore
     */
    public void setIfshowscore(Byte ifshowscore) {
        this.ifshowscore = ifshowscore;
    }

    /**
     * @return rank_matchSeason
     */
    public String getRankMatchseason() {
        return rankMatchseason;
    }

    /**
     * @param rankMatchseason
     */
    public void setRankMatchseason(String rankMatchseason) {
        this.rankMatchseason = rankMatchseason;
    }

    /**
     * @return sclass_rule
     */
    public String getSclassRule() {
        return sclassRule;
    }

    /**
     * @param sclassRule
     */
    public void setSclassRule(String sclassRule) {
        this.sclassRule = sclassRule;
    }
}