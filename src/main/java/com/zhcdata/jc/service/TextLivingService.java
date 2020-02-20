package com.zhcdata.jc.service;

import com.zhcdata.jc.dto.TextLiving;

import java.util.List;
import java.util.Map;

public interface TextLivingService {
    Map<String,Object> updateTextLiveRedisDataSelective(int matchId);

    List<TextLiving> updateTextLiveRedisDataReturnText(int matchId,int pageNo);
    /**
     * 根据比赛id查询统计内容和直播内容，每次执行都更新redis
     * @param matchId 比赛id
     * @return
     */
    Map<String, Object> updateTextLiveRedisData(int matchId);
}
