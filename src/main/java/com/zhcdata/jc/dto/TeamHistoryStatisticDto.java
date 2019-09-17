package com.zhcdata.jc.dto;
import lombok.Data;

import java.io.Serializable;

@Data
public class TeamHistoryStatisticDto implements Serializable {
    private static final long serialVersionUID = -5355941667301451036L;
    private String win;
    private String flat;
    private String lose;
    private String goal;
    private String lost;

    public TeamHistoryStatisticDto() {
        win = "0";
        flat = "0";
        lose = "0";
        goal = "0";
        lost = "0";
    }
}
