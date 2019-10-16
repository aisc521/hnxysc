package com.zhcdata.jc.dto;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/10/12 14:55
 */
public class HorseRaceLampDto {
    private String id;//专家id
    private String nikeName;//专家昵称
    private String lable;//专家标签
    private String victory;//战果
    private String warn;//随机字符串

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getVictory() {
        return victory;
    }

    public void setVictory(String victory) {
        this.victory = victory;
    }

    public String getWarn() {
        return warn;
    }

    public void setWarn(String warn) {
        this.warn = warn;
    }
}
