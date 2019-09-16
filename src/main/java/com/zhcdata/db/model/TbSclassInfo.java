package com.zhcdata.db.model;

import java.util.Date;

public class TbSclassInfo {
    private Integer infoid;

    private String namecn;

    private String nameen;

    private String namefn;

    private String flagpic;

    private Short infoorder;

    private Integer infoType;

    private String modifytime;

    private Short allorder;

    public Integer getInfoid() {
        return infoid;
    }

    public void setInfoid(Integer infoid) {
        this.infoid = infoid;
    }

    public String getNamecn() {
        return namecn;
    }

    public void setNamecn(String namecn) {
        this.namecn = namecn == null ? null : namecn.trim();
    }

    public String getNameen() {
        return nameen;
    }

    public void setNameen(String nameen) {
        this.nameen = nameen == null ? null : nameen.trim();
    }

    public String getNamefn() {
        return namefn;
    }

    public void setNamefn(String namefn) {
        this.namefn = namefn == null ? null : namefn.trim();
    }

    public String getFlagpic() {
        return flagpic;
    }

    public void setFlagpic(String flagpic) {
        this.flagpic = flagpic == null ? null : flagpic.trim();
    }

    public Short getInfoorder() {
        return infoorder;
    }

    public void setInfoorder(Short infoorder) {
        this.infoorder = infoorder;
    }

    public Integer getInfoType() {
        return infoType;
    }

    public void setInfoType(Integer infoType) {
        this.infoType = infoType;
    }

    public String getModifytime() {
        return modifytime;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
    }

    public Short getAllorder() {
        return allorder;
    }

    public void setAllorder(Short allorder) {
        this.allorder = allorder;
    }
}
