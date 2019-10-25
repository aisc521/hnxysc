package com.zhcdata.jc.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhcdata.jc.tools.DoubleFormat;
import lombok.Data;

import java.io.Serializable;

/**
 * Title:
 * Description: 欧赔/亚盘/大小球详情
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/9/16 时间
 */
@Data
public class HandicapOddsDetailsResult implements Serializable {
    //赔率出现时间
    private String oddTime;
    //胜
    @JsonSerialize(using = DoubleFormat.class)
    private Double oddWin;
    //平
    @JsonSerialize(using = DoubleFormat.class)
    private Double oddFlat;
    //负
    @JsonSerialize(using = DoubleFormat.class)
    private Double oddLose;

}
