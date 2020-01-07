package com.zhcdata.jc.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/10/26 11:25
 */
@Getter
@Setter
public class QueryPlanByMatchIdDto {
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
    private String price;
    private String createTime;
    private String buStatus;
    private String grade;
    private String planStatus;
    private List<MatchInfoDto> list;
    private String planId;
    private String tenZ;
    private String nineZ;
    private String eightZ;
    private String sevenZ;
    private String sixZ;
    private String fiveZ;
    private String fourZ;
    private String threeZ;
    private String returnSevenDays;
    private String lzBig;
    private String planHit;
}
