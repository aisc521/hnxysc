package com.zhcdata.db.model;

public class LiveInfo  {
    private Long id;

    private String content;

    private String time;

    private String createTime;

    private Long scheduleid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getScheduleid() {
        return scheduleid;
    }

    public void setScheduleid(Long scheduleid) {
        this.scheduleid = scheduleid;
    }
}
