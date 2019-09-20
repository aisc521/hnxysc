package com.zhcdata.jc.service.impl;

import com.google.common.base.Strings;
import com.zhcdata.db.mapper.JcMatchLiveMapper;
import com.zhcdata.db.mapper.TbTeamTechStatisticsMapper;
import com.zhcdata.jc.dto.EventStandDataSum;
import com.zhcdata.jc.dto.TextLiving;
import com.zhcdata.jc.enums.RedisCodeMsg;
import com.zhcdata.jc.service.TextLivingService;
import com.zhcdata.jc.tools.Const;
import com.zhcdata.jc.tools.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.mapper.JsonMapper;
import org.springside.modules.utils.time.DateFormatUtil;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/19 16:18
 */
@Service
public class TextLivingServiceImpl implements TextLivingService {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());


    @Resource
    private TbTeamTechStatisticsMapper tbTeamTechStatisticsMapper;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private JcMatchLiveMapper jcMatchLiveMapper;
    @Override
    public Map<String, Object> updateTextLiveRedisDataSelective(int matchId) {
        boolean redisUpdateFlag1 = true;
        boolean redisUpdateFlag2 = true;
        if (redisUpdateFlag1 || redisUpdateFlag2) {
            return updateTextLiveRedisData(matchId);
        }
        return null;
    }


    @Override
    public Map<String, Object> updateTextLiveRedisData(int matchId) {
        Map<String, Object> map = queryEventStandDataSum(matchId);

        List<TextLiving> textLivings = jcMatchLiveMapper.queryTextLivingList(matchId);
        LOGGER.error("赛事" + matchId + "文字直播数量为：" + textLivings.size());
        map.put("list", textLivings == null?new ArrayList<>():textLivings);

        String timeId = DateFormatUtil.formatDate(Const.YYYYMMDDHHMMSSSSS, new Date());
        redisUtils.set(RedisCodeMsg.PMS_TEXT_LIVE.getName() + matchId + ":TIME_ID",
                timeId,RedisCodeMsg.PMS_TEXT_LIVE.getSeconds());
        redisUtils.set(RedisCodeMsg.PMS_TEXT_LIVE.getName() + matchId,
                 JsonMapper.defaultMapper().toJson(map),RedisCodeMsg.PMS_TEXT_LIVE.getSeconds());
        map.put("timeId", timeId);
        return map;
    }
    /**
     * 根据比赛id查询内容统计
     * @param matchId 比赛id
     * @return
     */
    public Map<String, Object> queryEventStandDataSum(int matchId) {
        Map<String, Object> map = new HashMap<>(2);
        //查询统计结果
        List<EventStandDataSum> list = tbTeamTechStatisticsMapper.queryEventStandDataSum(matchId);

        LOGGER.error("赛事" + matchId + "统计结果数量为：" + list.size());
        if (list != null && list.size() > 0) {
            for (EventStandDataSum sum : list) {
                //判断是主队还是客队，标明结果前缀
                String prefix = null;
                switch (sum.getZk()) {
                    case "1":
                        prefix = "home";
                        break;
                    case "2":
                        prefix = "away";
                        break;
                    default:
                        break;
                }
                //如果类型正常，则进行赋值
                if (!Strings.isNullOrEmpty(prefix)) {
                    //射门
                    map.put(prefix + "Shemen", sum.getShots());
                    //射正
                    map.put(prefix + "Shezheng", sum.getTarget());
                    //反攻
                    //map.put(prefix + "Fcount", sum.getCounter());
                    //红牌
                    map.put(prefix + "RedCard", sum.getRed());
                    //黄牌
                    map.put(prefix + "YlwCard", sum.getYellow());
                    //犯规
                    map.put(prefix + "NoRule", sum.getFouls());
                    //越位
                    map.put(prefix + "Yuewei", sum.getOffside());
                }
            }
        }
        return map;
    }



}
