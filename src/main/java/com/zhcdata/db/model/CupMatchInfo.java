package com.zhcdata.db.model;

public class CupMatchInfo {
    private Integer id;

    private Integer sclassid;

    private Integer cupmatchType;

    private String grouping;

    private Integer area;

    private String matchseason;

    private Short linecount;

    private Boolean isupdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSclassid() {
        return sclassid;
    }

    public void setSclassid(Integer sclassid) {
        this.sclassid = sclassid;
    }

    public Integer getCupmatchType() {
        return cupmatchType;
    }

    public void setCupmatchType(Integer cupmatchType) {
        this.cupmatchType = cupmatchType;
    }

    public String getGrouping() {
        return grouping;
    }

    public void setGrouping(String grouping) {
        this.grouping = grouping == null ? null : grouping.trim();
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getMatchseason() {
        return matchseason;
    }

    public void setMatchseason(String matchseason) {
        this.matchseason = matchseason == null ? null : matchseason.trim();
    }

    public Short getLinecount() {
        return linecount;
    }

    public void setLinecount(Short linecount) {
        this.linecount = linecount;
    }

    public Boolean getIsupdate() {
        return isupdate;
    }

    public void setIsupdate(Boolean isupdate) {
        this.isupdate = isupdate;
    }
}
