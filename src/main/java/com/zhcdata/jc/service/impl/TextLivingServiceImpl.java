package com.zhcdata.jc.service.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.zhcdata.db.mapper.DetailresultMapper;
import com.zhcdata.db.mapper.JcMatchLiveMapper;
import com.zhcdata.db.mapper.TbTeamTechStatisticsMapper;
import com.zhcdata.db.model.DetailResultInfo;
import com.zhcdata.db.model.Detailresult;
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
import java.util.stream.Collectors;

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
    @Resource
    private DetailresultMapper detailresultMapper;
    @Override
    public Map<String, Object> updateTextLiveRedisDataSelective(int matchId) {
//        boolean redisUpdateFlag1 = true;
//        boolean redisUpdateFlag2 = true;
//        if (redisUpdateFlag1 || redisUpdateFlag2) {
            return updateTextLiveRedisData(matchId);
//        }
//        return null;
    }

    @Override
    public List<TextLiving> updateTextLiveRedisDataReturnText(int matchId,int pageNo) {
        Map<String, Object> map = queryEventStandDataSum(matchId);

        List<Detailresult> detailresultList = detailresultMapper.queryDetailresultListByMatchId(matchId);
        LOGGER.error("赛事" + matchId + "事件直播数量为：" + detailresultList.size());
        JsonMapper jsonMapper = JsonMapper.defaultMapper();
        if(map != null){
            map.put("list", Lists.newArrayList());
            map.put("eventlist", detailresultList);
            String timeId = DateFormatUtil.formatDate(Const.YYYYMMDDHHMMSSSSS, new Date());
            redisUtils.set(RedisCodeMsg.PMS_TEXT_LIVE.getName() + matchId + ":TIME_ID",
                    timeId,RedisCodeMsg.PMS_TEXT_LIVE.getSeconds());
            redisUtils.set(RedisCodeMsg.PMS_TEXT_LIVE.getName() + matchId,
                    jsonMapper.toJson(map),RedisCodeMsg.PMS_TEXT_LIVE.getSeconds());
            map.put("timeId", timeId);
        }
        List<TextLiving> textLivings = jcMatchLiveMapper.queryTextLivingList(matchId);
        LOGGER.error("赛事" + matchId + "文字直播数量为：" + textLivings.size());
        if (textLivings.size() > 0) {
            redisUtils.del(RedisCodeMsg.PMS_TEXT_LIVE.getName() + "text:" + matchId);
            String timeId = DateFormatUtil.formatDate(Const.YYYYMMDDHHMMSSSSS, new Date());
            List<Object> collect = textLivings.stream().map(s -> (Object) jsonMapper.toJson(s)).collect(Collectors.toList());
            redisUtils.lleftPushAll(RedisCodeMsg.PMS_TEXT_LIVE.getName() + "text:" + matchId, collect, RedisCodeMsg.PMS_TEXT_LIVE.getSeconds());
            redisUtils.set(RedisCodeMsg.PMS_TEXT_LIVE.getName() + "text:" + matchId + ":TIME_ID",timeId, RedisCodeMsg.PMS_TEXT_LIVE.getSeconds());
            int start = (pageNo - 1) * Const.DEFAULT_PAGE_AMOUNT;
            int end = pageNo * Const.DEFAULT_PAGE_AMOUNT;
            if (textLivings.size() > start) {
                return textLivings.subList(start, textLivings.size() >= end ? end : textLivings.size());
            }
        }
        return Lists.newArrayList();
    }


    @Override
    public Map<String, Object> updateTextLiveRedisData(int matchId) {
        Map<String, Object> map = queryEventStandDataSum(matchId);

        List<TextLiving> textLivings = jcMatchLiveMapper.queryTextLivingList(matchId);
        LOGGER.error("赛事" + matchId + "文字直播数量为：" + textLivings.size());
        JsonMapper jsonMapper = JsonMapper.defaultMapper();
        if (textLivings.size() > 0) {
            redisUtils.del(RedisCodeMsg.PMS_TEXT_LIVE.getName() + "text:" + matchId);
            String timeId = DateFormatUtil.formatDate(Const.YYYYMMDDHHMMSSSSS, new Date());
            List<Object> collect = textLivings.stream().map(s -> (Object)jsonMapper.toJson(s)).collect(Collectors.toList());
            redisUtils.lleftPushAll(RedisCodeMsg.PMS_TEXT_LIVE.getName() + "text:" + matchId, collect, RedisCodeMsg.PMS_TEXT_LIVE.getSeconds());
            redisUtils.set(RedisCodeMsg.PMS_TEXT_LIVE.getName() + "text:" + matchId + ":TIME_ID",timeId, RedisCodeMsg.PMS_TEXT_LIVE.getSeconds());
        }
        List<Detailresult> detailresultList = detailresultMapper.queryDetailresultListByMatchId(matchId);
        LOGGER.error("赛事" + matchId + "事件直播数量为：" + detailresultList.size());
        if(map != null){
            map.put("list", Lists.newArrayList());
            map.put("eventlist", detailresultList);
            String timeId = DateFormatUtil.formatDate(Const.YYYYMMDDHHMMSSSSS, new Date());
            redisUtils.set(RedisCodeMsg.PMS_TEXT_LIVE.getName() + matchId + ":TIME_ID",
                    timeId,RedisCodeMsg.PMS_TEXT_LIVE.getSeconds());
            redisUtils.set(RedisCodeMsg.PMS_TEXT_LIVE.getName() + matchId,
                    jsonMapper.toJson(map),RedisCodeMsg.PMS_TEXT_LIVE.getSeconds());
            map.put("timeId", timeId);
            return map;
        }
        return null;

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
