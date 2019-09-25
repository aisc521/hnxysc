package com.zhcdata.db.model;

import java.util.Objects;

public class PlayerInfo {
    private Integer playerid;

    private Short kind;

    private String nameShort;

    private String nameF;

    private String nameJ;

    private String nameE;

    private String nameEs;

    private String birthday;

    private Short tallness;

    private Short weight;

    private String country;

    private String photo;

    private String introduce;

    private String health;

    private String modifytime;

    private Integer countryid;

    private String expectedvalue;

    private String honorinfo;

    private String enddatecontract;

    private Integer idiomaticfeet;

    private Integer country2id;

    private Integer hotsortnumber;

    public Integer getPlayerid() {
        return playerid;
    }

    public void setPlayerid(Integer playerid) {
        this.playerid = playerid;
    }

    public Short getKind() {
        return kind;
    }

    public void setKind(Short kind) {
        this.kind = kind;
    }

    public String getNameShort() {
        return nameShort;
    }

    public void setNameShort(String nameShort) {
        this.nameShort = nameShort == null ? null : nameShort.trim();
    }

    public String getNameF() {
        return nameF;
    }

    public void setNameF(String nameF) {
        this.nameF = nameF == null ? null : nameF.trim();
    }

    public String getNameJ() {
        return nameJ;
    }

    public void setNameJ(String nameJ) {
        this.nameJ = nameJ == null ? null : nameJ.trim();
    }

    public String getNameE() {
        return nameE;
    }

    public void setNameE(String nameE) {
        this.nameE = nameE == null ? null : nameE.trim();
    }

    public String getNameEs() {
        return nameEs;
    }

    public void setNameEs(String nameEs) {
        this.nameEs = nameEs == null ? null : nameEs.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Short getTallness() {
        return tallness;
    }

    public void setTallness(Short tallness) {
        this.tallness = tallness;
    }

    public Short getWeight() {
        return weight;
    }

    public void setWeight(Short weight) {
        this.weight = weight;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health == null ? null : health.trim();
    }

    public String getModifytime() {
        return modifytime;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
    }

    public Integer getCountryid() {
        return countryid;
    }

    public void setCountryid(Integer countryid) {
        this.countryid = countryid;
    }

    public String getExpectedvalue() {
        return expectedvalue;
    }

    public void setExpectedvalue(String expectedvalue) {
        this.expectedvalue = expectedvalue == null ? null : expectedvalue.trim();
    }

    public String getEnddatecontract() {
        return enddatecontract;
    }

    public void setEnddatecontract(String enddatecontract) {
        this.enddatecontract = enddatecontract;
    }

    public Integer getIdiomaticfeet() {
        return idiomaticfeet;
    }

    public void setIdiomaticfeet(Integer idiomaticfeet) {
        this.idiomaticfeet = idiomaticfeet;
    }

    public Integer getCountry2id() {
        return country2id;
    }

    public void setCountry2id(Integer country2id) {
        this.country2id = country2id;
    }

    public Integer getHotsortnumber() {
        return hotsortnumber;
    }

    public void setHotsortnumber(Integer hotsortnumber) {
        this.hotsortnumber = hotsortnumber;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getHonorinfo() {
        return honorinfo;
    }

    public void setHonorinfo(String honorinfo) {
        this.honorinfo = honorinfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerInfo that = (PlayerInfo) o;
        return Objects.equals(playerid, that.playerid) &&
                Objects.equals(kind, that.kind) &&
                Objects.equals(nameShort, that.nameShort) &&
                Objects.equals(nameF, that.nameF) &&
                Objects.equals(nameJ, that.nameJ) &&
                Objects.equals(nameE, that.nameE) &&
                Objects.equals(nameEs, that.nameEs) &&
                //Objects.equals(birthday, that.birthday) &&
                Objects.equals(tallness, that.tallness) &&
                Objects.equals(weight, that.weight) &&
                Objects.equals(country, that.country) &&
                Objects.equals(photo, that.photo) &&
                Objects.equals(introduce, that.introduce) &&
                Objects.equals(health, that.health) &&
                //Objects.equals(modifytime, that.modifytime) &&
                //Objects.equals(countryid, that.countryid) &&
                Objects.equals(expectedvalue, that.expectedvalue) &&
                //Objects.equals(honorinfo, that.honorinfo) &&
                Objects.equals(enddatecontract, that.enddatecontract) &&
                Objects.equals(idiomaticfeet, that.idiomaticfeet)
                //Objects.equals(country2id, that.country2id) &&
                //Objects.equals(hotsortnumber, that.hotsortnumber)
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerid, kind, nameShort, nameF, nameJ, nameE, nameEs, tallness, weight, country, photo, introduce, health, expectedvalue, enddatecontract, idiomaticfeet);
    }
}
