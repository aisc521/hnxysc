package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.jc.dto.HandicapOddsDetailsResult;
import com.zhcdata.jc.dto.HandicapOddsResult;
import com.zhcdata.jc.enums.RedisCodeMsg;
import com.zhcdata.jc.service.HandicapOddsService;
import com.zhcdata.jc.tools.Const;
import com.zhcdata.jc.tools.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.mapper.JsonMapper;
import org.springside.modules.utils.time.ClockUtil;
import org.springside.modules.utils.time.DateFormatUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * Title:
 * Description:盘口、赔率相关service
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/9/16 10:09
 */
@Slf4j
@Service
public class HandicapOddsServiceImpl implements HandicapOddsService {
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ScheduleMapper scheduleMapper;
    private String[] types = {"1", "2", "3"};
    public static int[] OP_COM = {16, 80, 81, 82, 90, 104, 115, 158, 255, 281, 545, 1129};
    public static int[] OTHER_COM = {1,3,8,12,17,23,24,31};

    @Override
    public void updateHandicapOddsData(int count) {
        log.error("查询比赛");
        //根据条件查询比赛id
        List<Integer> list = scheduleMapper.selectScheduleIdByDate(count);
        log.error("查询{}天前的比赛数量为{}", count, list.size());
        log.error("开始遍历比赛，查询数据");
        long l = ClockUtil.currentTimeMillis();
        //遍历比赛
        for (Integer matchId : list) {
            String key = RedisCodeMsg.SOCCER_HSET_ODDS_QT.getName() + ":" + matchId;
            boolean updateFlag = false;
            //遍历类型
            for (String type : types) {
                //获取对应赔率
                List<HandicapOddsResult> results = scheduleMapper.selectOddsResultsByMatchId(matchId, type);
                if (results != null && results.size() > 0) {
                    updateFlag = true;
                    //更新redis
                    updateRedis(key, type, results,RedisCodeMsg.SOCCER_HSET_ODDS_QT.getSeconds());
                }
            }
            if (updateFlag) {
                redisUtils.hset(key, "TIME_ID", DateFormatUtil.formatDate(Const.YYYYMMDDHHMMSSSSS, ClockUtil.currentDate()), RedisCodeMsg.SOCCER_HSET_ODDS_QT.getSeconds());
            }
        }
        long e = ClockUtil.currentTimeMillis();
        log.error("比赛数据计算完毕，总耗时为：{}ms", e - l);


    }

    @Override
    public void updateHandicapOddsDetailData(int count) {
        //根据条件查询比赛id
        List<Integer> list = scheduleMapper.selectScheduleIdByDate(count);
        for (Integer matchId : list) {
            String key = RedisCodeMsg.SOCCER_ODDS_DETAIL.getName() + ":" + matchId;
            for (String type : types) {
                int[] coms = null;
                if ("1".equals(type)) {
                    coms = OP_COM;
                } else {
                    coms = OTHER_COM;
                }
                for (int comId : coms) {
                    List<HandicapOddsDetailsResult> results = scheduleMapper.selectOddsResultDetailByMatchId(matchId, comId, type);
                    updateRedis(key, type + ":" + comId, results, RedisCodeMsg.SOCCER_ODDS_DETAIL.getSeconds());
                }
            }
        }
    }

    private void updateRedis(String key, String type, List results,long time) {
        String json = JsonMapper.defaultMapper().toJson(results);
        String timeIdKey = type + "_" + "TIME_ID";
        String timeId = DateFormatUtil.formatDate(Const.YYYYMMDDHHMMSSSSS, ClockUtil.currentDate());
        redisUtils.hset(key, type, json);
        redisUtils.hset(key, timeIdKey, timeId);
        redisUtils.expire(key, time);
    }
}
