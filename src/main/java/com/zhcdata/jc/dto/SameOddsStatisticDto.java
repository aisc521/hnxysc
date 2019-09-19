package com.zhcdata.jc.dto;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Yore
 * @Date 2019-09-19 10:17
 */
@Data
public class SameOddsStatisticDto implements Serializable {
    private static final long serialVersionUID = 2779194988862235161L;
    private int total;
    private int winCount;
    private int flatCount;
    private int loseCount;
    private int winPanCount;
    private int flatPanCount;
    private int losePanCount;
}
