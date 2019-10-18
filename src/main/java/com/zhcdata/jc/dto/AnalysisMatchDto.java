package com.zhcdata.jc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String satWin;
    //初平
    private String satFlat;
    //初负
    private String satLose;
    //终胜
    private String endWin;
    //终平
    private String endFlat;
    //终负
    private String endLose;
    //盘路
    private String panlu;
    @JsonIgnore
    private Integer result;
}
