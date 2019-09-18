package com.zhcdata.jc.protocol.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.enums.RedisCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.ScheduleService;
import com.zhcdata.jc.tools.CommonUtils;
import com.zhcdata.jc.tools.Const;
import com.zhcdata.jc.tools.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.mapper.JsonMapper;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title:
 * Description:分析(积分+近期+交锋)
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/9/17 20:05
 */
@Slf4j
@Service("20200205")
public class MatchAnalysisProtocol implements BaseProtocol {
    @Resource
    private CommonUtils commonUtils;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ScheduleService scheduleService;
    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        Map<String, Object> map = new HashMap<>(2);
        if (!commonUtils.validParamExistOrNoNum(map, paramMap, "matchId", ProtocolCodeMsg.MATCH_ID_NOT_ASSIGNED)) {
            return map;
        }
        if (!commonUtils.validParamExistOrNoInteger(map, paramMap, Const.TYPE,"7", ProtocolCodeMsg.TYPE_NOT_ASSIGNED)) {
            return map;
        }
        if (!commonUtils.validParamExistOrNoNum(map, paramMap, Const.TIME_ID, ProtocolCodeMsg.TIME_ID_NOT_ASSIGNED)) {
            return map;
        }
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        String matchId = paramMap.get("matchId");
        String type = paramMap.get("type");
        String timeId = paramMap.get("timeId");
        if (Strings.isNullOrEmpty(timeId)) {
            timeId = "-1";
        }
        Map<String, Object> map = new HashMap<>(2);
        String key = RedisCodeMsg.SOCCER_ANALYSIS.getName() + ":" + matchId;
        String redisTimeId = (String) redisUtils.hget(key, type + "_TIME_ID");
        if (!Strings.isNullOrEmpty(redisTimeId) && redisTimeId.equals(timeId)) {
            map.put("timeId", redisTimeId);
            return map;
        }
        String value = (String) redisUtils.hget(key, type);
        if (!Strings.isNullOrEmpty(value)) {
            map = JsonMapper.defaultMapper().fromJson(value, Map.class);
        } else {
            map = scheduleService.matchAnalysisByType(Integer.parseInt(matchId), type);
        }
        return map;
    }
}
