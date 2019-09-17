package com.zhcdata.jc.dto;

import lombok.Data;

@Data
public class HistoryMatchDto {
    private String matchDate;//比赛时间
    private String matchName;//赛事名称
    private String homeName;//主队名称
    private String guestName;//客队名称
    private String hostHalfScore;//主队半场得分
    private String guestHalfScore;//客队半场得分
    private String hostScore;//主队得分
    private String guestScore;//客队得分
    private String panlu;//盘路

}
