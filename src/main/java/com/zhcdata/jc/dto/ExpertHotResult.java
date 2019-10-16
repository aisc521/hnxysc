package com.zhcdata.jc.dto;

public class ExpertHotResult {
    /**
     * 专家ID
     */
    private int id;

    /**
     * 专家昵称 国彩专家老杜
     */
    private String nickName;

    /**
     *头像
     */
    private String img;

    /**
     * 连中
     */
    private String  lz;

    /**
     * 推ing
     */
    private String pushed;

    /**
     * 连中或连红
     */
    private String type;


    /**
     * 标签
     * @return
     */
    private String label;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getLz() {
        return lz;
    }

    public void setLz(String lz) {
        this.lz = lz;
    }

    public String getPushed() {
        return pushed;
    }

    public void setPushed(String pushed) {
        this.pushed = pushed;
    }
}
