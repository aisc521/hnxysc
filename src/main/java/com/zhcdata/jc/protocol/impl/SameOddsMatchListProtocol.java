package com.zhcdata.jc.protocol.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.base.Strings;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.dto.SameOddsDto;
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
import org.springside.modules.utils.time.DateFormatUtil;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title:
 * Description:同赔比赛列表
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/9/19 20:35
 */
@Slf4j
@Service("20200212")
public class SameOddsMatchListProtocol implements BaseProtocol {
    @Resource
    private CommonUtils commonUtils;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ScheduleService scheduleService;
    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        Map<String, Object> map = new HashMap<>(2);
        if (!commonUtils.validParamExistOrNoNum(map, paramMap, "matchType", ProtocolCodeMsg.MATCH_ID_NOT_ASSIGNED)) {
            return map;
        }
        if (!commonUtils.validParamExist(map, paramMap,"time", ProtocolCodeMsg.DATE_NOT_ASSIGNED)) {
            return map;
        }
        try {
            DateFormatUtil.pareDate(Const.YYYY_MM_DD, paramMap.get("time"));
        } catch (ParseException e) {
          log.error("时间转换错误",e);
            commonUtils.errorMessageToMap(map,ProtocolCodeMsg.DATE_NOT_ASSIGNED);
            return map;
        }
        if (!commonUtils.validParamExistOrNoNum(map, paramMap, Const.TIME_ID, ProtocolCodeMsg.TIME_ID_NOT_ASSIGNED)) {
            return map;
        }
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        String matchType = paramMap.get("matchType");
        String time = paramMap.get("time");
        String timeId = paramMap.get("timeId");
        if (Strings.isNullOrEmpty(timeId)) {
            timeId = "-1";
        }
        Map<String, Object> map = new HashMap<>(2);
        String type = scheduleService.processingSameOddsTypeToLottery(matchType);
        if ("all".equalsIgnoreCase(type)) {
            matchType = "0";
        }
        String item = matchType + "_" + type;
        String key = RedisCodeMsg.SOCCER_SAME_ODDS_MATCH.getName() + ":" + time;
        String redisTimeId = (String) redisUtils.hget(key, item + "_TIME_ID");
        if (!Strings.isNullOrEmpty(redisTimeId) && redisTimeId.equals(timeId)) {
            map.put("timeId", redisTimeId);
            return map;
        }
        String value = (String) redisUtils.hget(key, item);
        if (!Strings.isNullOrEmpty(value)) {
            JsonMapper jsonMapper = JsonMapper.defaultMapper();
            JavaType javaType = jsonMapper.buildCollectionType(List.class, SameOddsDto.class);
            map.put("data", JsonMapper.defaultMapper().fromJson(value, javaType));
            map.put("timeId", redisTimeId);
        }
        return map;
    }
}
