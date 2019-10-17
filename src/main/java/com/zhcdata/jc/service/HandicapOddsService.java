package com.zhcdata.jc.service;

import java.util.Map;

/**
 * Title:
 * Description: 盘口、赔率相关service
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/9/16 10:09
 */
public interface HandicapOddsService {

    /**
     * 获取Count天之前的赔率数据
     * @param count
     */
    void updateHandicapOddsData(int count);

    /**
     * 获取Count天之前的赔率公司变化数据
     * @param count
     */
    void updateHandicapOddsDetailData(int count);

    /**
     * 同初赔、初盘比赛数据
     * @param type TPEI 同赔   TPAN 同盘 非空
     * @param matchId 基础比赛id 非空
     * @param oddsCompany 查询指定公司赔率、盘口 非空
     * @param matchType 赛事类型 1全部 2五大联赛 2北单竞彩 4其他
     * @param beginDate 指定开始时间 可空
     * @param chgTimes  变盘次数 0，1，2，3，4+(传值为4)，全部（全部为空）
     * @param pam       低赔方初赔至终赔变化幅度差(绝对值)
     *                  同赔单选 1：0-0.1   2：0.1-0.3   3：0.3-0.5   4：0.5+   5：全部
     *                  同盘复选 变盘次数为0次时使用，复选时已竖线|分隔 1：0-0.05   2：0.05-0.1   3：0.1-0.2   4：0.2-0.3   5：0.3-0.5   6：0.5+   空或0：全部
     * @return
     */
    Map<String,Object> sameBeginOddsGoalMatch(String type,Integer matchId,  String oddsCompany, Integer matchType,String beginDate,Integer chgTimes,
                                               String pam);

    /**
     * 指数分析 - 根据赔率查询对应比赛
     * @param satSat        初赔
     * @param endEnd        终赔
     * @param oddsCompany   赔率公司
     * @param matchType     赛事类型 1全部 2五大联赛 2北单竞彩 4其他
     * @param beginDate     指定开始时间 可空
     * @param pam           低赔方初赔至终赔变化幅度差(绝对值) 同赔单选 1：0-0.1   2：0.1-0.3   3：0.3-0.5   4：0.5+   5或0：全部
     * @param zkFlag        赔率查询主客队 1主 2客
     * @return
     */
    Map<String,Object> queryMatchDataByOdds(Double satSat,Double endEnd, String oddsCompany, Integer matchType,String beginDate,String pam,String zkFlag);

    /**
     * 指数分析 - 根据盘口查询对应比赛
     * @param satGoal       初始盘口
     * @param endGoal       最终盘口
     * @param oddsCompany   赔率公司
     * @param matchType     赛事类型 1全部 2五大联赛 2北单竞彩 4其他
     * @param beginDate     指定开始时间 可空
     * @param pam           同盘复选 变盘次数为0次时使用，复选时已竖线|分隔 1：0-0.05   2：0.05-0.1   3：0.1-0.2   4：0.2-0.3   5：0.3-0.5   6：0.5+   空或0：全部
     * @param satOdds       主队起始水位
     * @param endOdds       主队结束水位
     * @param changeTimes      变盘次数
     * @return
     */
    Map<String,Object> queryMatchDataByHandicap(Double satGoal,Double endGoal, String oddsCompany, Integer matchType,
                                                String beginDate,String pam,String satOdds,String endOdds,Integer changeTimes);


}
