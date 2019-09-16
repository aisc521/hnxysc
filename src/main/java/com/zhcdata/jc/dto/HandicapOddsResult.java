package com.zhcdata.jc.dto;

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
    private String opSatWin; //初始 胜
    private String opSatFlat; //初始 平
    private String opStaLose; //初始 负
    private String opNowWin; //即时 胜
    private String opNowFlat; //即时 平
    private String opNowLose; //即时 负
}
