package com.zhcdata.jc.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author Yore
 * @Date 2019-09-19 10:17
 */
@Data
public class SameOddsDto implements Serializable {
    private static final long serialVersionUID = 2779194988862235161L;
    @JsonIgnore
    private String lotteryType;         //比赛类型 1竞彩 2北单 3足彩 4其他
    @JsonIgnore
    private String issueNum;
    @JsonIgnore
    private String noId;
    private String matchId;
    private String matchName;            //比赛名称(亚冠…)
    private String matchState;            //比赛状态🌟🌟🌟
    private String matchTime;            //比赛时间
    private Date matchTime1;            //比赛时间
    private Date matchTime2;            //上半场或下半场开始的时间
    private String matchMinute;            //进行的分钟数
    private String homeName;            //主队名称
    private String guestName;            //客队名称
    private String homeHalfScore;        //	主队半场得分
    private String guestHalfScore;        //	客队半场得分
    private String homeRedCard;            //主队红牌
    private String guestRedCard;        //	客队红牌
    private String homeYlwCard;            //主队黄牌
    private String guestYlwCard;        //	客队黄牌
    private String homeScore;            //主队得分
    private String guestScore;            //客队得分
    private String text;                //文字描述🌟🌟🌟

    private String issueBD;               //期数🌟🌟🌟🌟  北单使用 90904
    private String issueZC;               //期数🌟🌟🌟🌟  足彩期次 19127期
    private String weekNum;               //期数🌟🌟🌟🌟  竞彩使用 如周一001
    private String num;                   //北单、足彩使用 107

    private String tpeiWinHandicap;        //	同赔胜赔率
    private String tpeiWinOdds;            //同赔胜几率
    private String tpeiFlatHandicap;    //		同赔平赔率
    private String tpeiFlatOdds;        //	同赔平几率
    private String tpeilLoseHandicap;    //		同赔负赔率
    private String tpeilLoseOdds;        //	同赔负几率

    private String tpanWinHandicap;        //	同盘胜盘口
    private String tpanWinOdds;            //同盘胜几率
    private String tpanFlatHandicap;    //		同盘平盘口
    private String tpanFlatOdds;        //	同盘平几率
    private String tpanLoseHandicap;    //		同盘负盘口
    private String tpanLoseOdds;        //	同盘负几率
    @JsonIgnore
    private int firstGoal;  //盘口
    @JsonIgnore
    private int flag; //0按主队赔率  1按客队赔率
    @JsonIgnore
    private int trend;//赔率趋势 1上升  -1 下降

    //同赔精选热度火焰  0无  2中  3大
    private Integer fireFlagWin;
    private Integer fireFlagFlat;
    private Integer fireFlagLose;

    @Override
    public boolean equals(Object o) {
        if (this == o){ return true;}

        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SameOddsDto model = (SameOddsDto) o;
        return Objects.equals(matchId, model.matchId) &&
                Objects.equals(matchName, model.matchName) &&
                Objects.equals(matchState, model.matchState) &&
                Objects.equals(matchTime, model.matchTime) &&
                Objects.equals(matchMinute, model.matchMinute) &&
                Objects.equals(homeName, model.homeName) &&
                Objects.equals(guestName, model.guestName) &&
                Objects.equals(homeHalfScore, model.homeHalfScore) &&
                Objects.equals(guestHalfScore, model.guestHalfScore) &&
                Objects.equals(homeRedCard, model.homeRedCard) &&
                Objects.equals(guestRedCard, model.guestRedCard) &&
                Objects.equals(homeYlwCard, model.homeYlwCard) &&
                Objects.equals(guestYlwCard, model.guestYlwCard) &&
                Objects.equals(homeScore, model.homeScore) &&
                Objects.equals(guestScore, model.guestScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchId, matchName, matchState, matchTime, matchMinute, homeName, guestName, homeHalfScore, guestHalfScore, homeRedCard, guestRedCard, homeYlwCard, guestYlwCard, homeScore, guestScore, text);
    }
}
