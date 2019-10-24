package com.zhcdata.jc.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhcdata.jc.tools.DoubleFormat;
import lombok.Data;

import java.io.Serializable;

/**
 * Title:
 * Description: 欧赔/亚盘/大小球数据
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/9/16 11:13
 */
@Data
public class HandicapOddsResult implements Serializable  {

    private String opId; //赔率公司id
    private String opName; //赔率公司名称
    @JsonSerialize(using = DoubleFormat.class)
    private Double opSatWin; //初始 胜
    @JsonSerialize(using = DoubleFormat.class)
    private Double opSatFlat; //初始 平
    @JsonSerialize(using = DoubleFormat.class)
    private Double opStaLose; //初始 负
    @JsonSerialize(using = DoubleFormat.class)
    private Double opNowWin; //即时 胜
    @JsonSerialize(using = DoubleFormat.class)
    private Double opNowFlat; //即时 平
    @JsonSerialize(using = DoubleFormat.class)
    private Double opNowLose; //即时 负
}
