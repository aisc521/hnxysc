package com.zhcdata.jc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhcdata.jc.tools.DoubleFormat;
import lombok.Data;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/10/9 10:15
 */
@Data
public class AnalysisMatchDto {
    //赛事名称
    private String matchName;
    //主队名称
    private String homeName;
    //客队名称
    private String guestName;
    //主队比分
    private Integer homeScore;
    //客队比分
    private Integer guestScore;
    //初胜
    @JsonSerialize(using = DoubleFormat.class)
    private Double satWin;
    //初平
    @JsonSerialize(using = DoubleFormat.class)
    private Double satFlat;
    //初负
    @JsonSerialize(using = DoubleFormat.class)
    private Double satLose;
    //终胜
    @JsonSerialize(using = DoubleFormat.class)
    private Double endWin;
    //终平
    @JsonSerialize(using = DoubleFormat.class)
    private Double endFlat;
    //终负
    @JsonSerialize(using = DoubleFormat.class)
    private Double endLose;
    //盘路
    @JsonSerialize(using = DoubleFormat.class)
    private Double panlu;
    @JsonIgnore
    private Integer result;
}
