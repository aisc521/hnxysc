package com.zhcdata.jc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Title:
 * Description: 同初赔、同初盘分析 数据传输对象
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/10/9 9:21
 */
@Data
public class AnalysisDto {
    @JsonIgnore
    private Integer oddsId;
    /**
     * 主队头像
     */
    private String hostIcon;
    /**
     * 主队名称
     */
    private String hostName;
    /**
     * 客队头像
     */
    private String guestIcon;
    /**
     * 客队名称
     */
    private String guestName;
    /**
     * 比赛时间
     */
    private Date matchDate;
    /**
     * 同初赔、同初盘比赛场次
     */
    private Integer matchTotal;
    /**
     * 胜场数
     */
    private Integer matchWin;
    /**
     * 平场数
     */
    private Integer matchFlat;
    /**
     * 负场数
     */
    private Integer matchLose;
    /**
     * 胜占比
     */
    private String ratioWin;
    /**
     * 平占比
     */
    private String ratioFlat;
    /**
     * 负占比
     */
    private String ratioLose;
    /**
     * 初胜
     */
    private Double satWin;
    /**
     * 初平
     */
    private Double satFlat;
    /**
     * 初负
     */
    private Double satLose;
    /**
     * 最近赛事
     */
    List<AnalysisMatchDto> list;

}
