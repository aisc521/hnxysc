package com.zhcdata.db.model;

import java.util.Date;
import java.util.Objects;

public class RefereeInfo {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefereeInfo that = (RefereeInfo) o;
        return Objects.equals(scheduleId, that.scheduleId) &&
                Objects.equals(refereeId, that.refereeId) &&
                Objects.equals(type, that.type) &&
                Objects.equals(ftName, that.ftName) &&
                Objects.equals(jtName, that.jtName) &&
                Objects.equals(enName, that.enName) &&
                Objects.equals(birthday, that.birthday) &&
                Objects.equals(pic, that.pic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleId, refereeId, type, ftName, jtName, enName, birthday, pic);
    }

    private Integer id;

    private Integer scheduleId;

    private Integer refereeId;

    private Integer type;

    private String ftName;

    private String jtName;

    private String enName;

    private String birthday;

    private String pic;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Integer getRefereeId() {
        return refereeId;
    }

    public void setRefereeId(Integer refereeId) {
        this.refereeId = refereeId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFtName() {
        return ftName;
    }

    public void setFtName(String ftName) {
        this.ftName = ftName == null ? null : ftName.trim();
    }

    public String getJtName() {
        return jtName;
    }

    public void setJtName(String jtName) {
        this.jtName = jtName == null ? null : jtName.trim();
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName == null ? null : enName.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}