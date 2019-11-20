package com.zhcdata.jc.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/20 16:30
 */
public class PurchasedPlanDto {
    private String id;
    private String nickName;
    private String img;
    private String lable;
    private String fans;
    private String popularity;
    private String lz;
    private String zThreeDays;
    private String zFiveDays;
    private String zSevenDays;
    private String titleShowType;
    private BigDecimal price;
    private String createTime;

    private String matchName;
    private String hostTeam;
    private String visitTeam;
    private String dateOfMatch;

    private String winStatus;
    private String buyTime;
    private String planHit;
    private String level;



    private String planId;
    private BigDecimal thirdMoney;
    private BigDecimal buyMoney;
    private String firstone;

    private String tenZ;
    private String nineZ;
    private String eightZ;
    private String sevenZ;
    private String sixZ;
    private String fiveZ;
    private String fourZ;
    private String threeZ;

    private String lzBig;

    private String status;

    private String paytype;

    private String orderId;
    private String returnSevenDays;

    public String getReturnSevenDays() {
        return returnSevenDays;
    }

    public void setReturnSevenDays(String returnSevenDays) {
        this.returnSevenDays = returnSevenDays;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTenZ() {
        return tenZ;
    }

    public void setTenZ(String tenZ) {
        this.tenZ = tenZ;
    }

    public String getNineZ() {
        return nineZ;
    }

    public void setNineZ(String nineZ) {
        this.nineZ = nineZ;
    }

    public String getEightZ() {
        return eightZ;
    }

    public void setEightZ(String eightZ) {
        this.eightZ = eightZ;
    }

    public String getSevenZ() {
        return sevenZ;
    }

    public void setSevenZ(String sevenZ) {
        this.sevenZ = sevenZ;
    }

    public String getSixZ() {
        return sixZ;
    }

    public void setSixZ(String sixZ) {
        this.sixZ = sixZ;
    }

    public String getFiveZ() {
        return fiveZ;
    }

    public void setFiveZ(String fiveZ) {
        this.fiveZ = fiveZ;
    }

    public String getFourZ() {
        return fourZ;
    }

    public void setFourZ(String fourZ) {
        this.fourZ = fourZ;
    }

    public String getThreeZ() {
        return threeZ;
    }

    public void setThreeZ(String threeZ) {
        this.threeZ = threeZ;
    }

    public BigDecimal getThirdMoney() {
        return thirdMoney;
    }

    public void setThirdMoney(BigDecimal thirdMoney) {
        this.thirdMoney = thirdMoney;
    }

    public BigDecimal getBuyMoney() {
        return buyMoney;
    }

    public void setBuyMoney(BigDecimal buyMoney) {
        this.buyMoney = buyMoney;
    }

    public String getFirstone() {
        return firstone;
    }

    public void setFirstone(String firstone) {
        this.firstone = firstone;
    }

    private List<MatchPlanResult> list;


    public List<MatchPlanResult> getList() {
        return list;
    }

    public void setList(List<MatchPlanResult> list) {
        this.list = list;
    }

    public PurchasedPlanDto() {
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getWinStatus() {
        return winStatus;
    }

    public void setWinStatus(String winStatus) {
        this.winStatus = winStatus;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public String getPlanHit() {
        return planHit;
    }

    public void setPlanHit(String planHit) {
        this.planHit = planHit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getLz() {
        return lz;
    }

    public void setLz(String lz) {
        this.lz = lz;
    }

    public String getzThreeDays() {
        return zThreeDays;
    }

    public void setzThreeDays(String zThreeDays) {
        this.zThreeDays = zThreeDays;
    }

    public String getzFiveDays() {
        return zFiveDays;
    }

    public void setzFiveDays(String zFiveDays) {
        this.zFiveDays = zFiveDays;
    }

    public String getzSevenDays() {
        return zSevenDays;
    }

    public void setzSevenDays(String zSevenDays) {
        this.zSevenDays = zSevenDays;
    }

    public String getTitleShowType() {
        return titleShowType;
    }

    public void setTitleShowType(String titleShowType) {
        this.titleShowType = titleShowType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getHostTeam() {
        return hostTeam;
    }

    public void setHostTeam(String hostTeam) {
        this.hostTeam = hostTeam;
    }

    public String getVisitTeam() {
        return visitTeam;
    }

    public void setVisitTeam(String visitTeam) {
        this.visitTeam = visitTeam;
    }

    public String getDateOfMatch() {
        return dateOfMatch;
    }

    public void setDateOfMatch(String dateOfMatch) {
        this.dateOfMatch = dateOfMatch;
    }

    public String getLzBig() {
        return lzBig;
    }

    public void setLzBig(String lzBig) {
        this.lzBig = lzBig;
    }
}
