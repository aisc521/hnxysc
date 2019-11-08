package com.zhcdata.jc.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/11/8 9:10
 */
@Getter
@Setter
public class HotExpertDto {
    /**
     * 当前几连中
     */
    private Long lzNow;

    /**
     * 三天命中率
     */
    private Long zThreeDays;
    /**
     * 五天命中率
     */
    private Long zFiveDays;
    /**
     * 七天命中率
     */
    private Long zSevenDays;
    /**
     * 七天回报率
     */
    private Long returnSevenDays;
    /**
     * 趋势（黑红黑红红）
     */
    private String trend;

    /**
     * 全部回报率
     */
    private Long returnAll;
    /**
     * 全部命中率
     */
    private Long zAll;
    /**
     * 历史最高连红
     */
    private Long lzBig;
    /**
     * 七天盈利率
     */
    private Long ylSevenDays;
    /**
     * 近10中几
     */
    private Integer tenZ;
    /**
     * 近9中几
     */
    private Integer nineZ;
    /**
     * 近8中几
     */
    private Integer eightZ;
    /**
     * 近7中几
     */
    private Integer sevenZ;
    /**
     * 近6中几
     */
    private Integer sixZ;
    /**
     * 近5中几
     */
    private Integer fiveZ;
    /**
     * 近4中几
     */
    private Integer fourZ;
    /**
     * 近3中几
     */
    private Integer threeZ;
    /**
     * 排序
     */
    private Integer orderBy;
    /**
     * 专家id
     */
    private Long id;
    /**
     * 专家昵称
     */
    private String nickName;
    /**
     * 专家头像
     */
    private String img;

    /**
     * 专家标签
     */
    private String lable;

    /**
     * 专家介绍
     */
    private String introduction;

    /**
     * 是否上架1 上架 2 下架
     */

    private Long isUpshelf;

    /**
     * 专家等级
     */
    private Long grade;
}
