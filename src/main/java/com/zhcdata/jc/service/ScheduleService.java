package com.zhcdata.jc.service;

import com.zhcdata.db.model.Schedule;

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

    Map<String, Object> matchAnalysisByType(Integer matchId, String type);

    void updateMatchAnalysis(int before, int after);
}
