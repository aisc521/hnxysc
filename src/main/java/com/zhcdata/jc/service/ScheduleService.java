package com.zhcdata.jc.service;

import com.zhcdata.db.model.Schedule;

import java.text.ParseException;
import java.util.Map;

public interface ScheduleService {
    Schedule queryScheduleById(Long idBet007);

    /**
     * 根据比赛id获取对应阵容
     * @param matchId 比赛id
     * @return
     * @Author Yore
     * @Date 2019-09-17 11:46
     */
    Map<String, Object> queryLineupDataByMatch(Long matchId);

    /**
     * 更新之前{before}天，之后{after}天的阵容数据
     * @Author Yore
     * @Date 2019-09-17 14:35
     */
    void updateLineupDataRedis(int before,int after);

    /**
     * 指定比赛分析数据
     * @param matchId 比赛id
     * @param type NULL 全部分析并放入缓存 0默认数据, 战绩：1同主客,2同赛事,3没有选择条件,4是同主客+同赛事,  历史交锋：5主客相同，6没有选择条件
     * @return
     */
    Map<String, Object> matchAnalysisByType(Integer matchId, String type);

    /**
     * 更新之前{before}天，之后{after}天的比赛分析数据
     * @param before
     * @param after
     */
    void updateMatchAnalysis(int before, int after);

    /**
     * 同赔精选比赛计算
     * @param date
     */
    void sameOddsMatchCompute(String date) throws ParseException;

    /**
     * 更新同赔精选比赛
     * @param date
     */
    void updateSameOddsMatchData(String date);

    /**
     * 根据类型转换为彩种 1竞彩 2北单 3足彩 其他为all
     * @param type
     * @return
     */
    String processingSameOddsTypeToLottery(String type);
}
