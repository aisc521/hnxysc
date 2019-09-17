package com.zhcdata.jc.protocol.impl;

import com.fasterxml.jackson.databind.JavaType;
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
import java.util.Map;

/**
 * Title:
 * Description:阵容数据
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/9/17 9:56
 */
@Slf4j
@Service("20200207")
public class LineupDataProtocol implements BaseProtocol {
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
        if (!commonUtils.validParamExistOrNoNum(map, paramMap, Const.TIME_ID, ProtocolCodeMsg.TIME_ID_NOT_ASSIGNED)) {
            return map;
        }
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        String matchId = paramMap.get("matchId");
        String timeId = paramMap.get("timeId");
        if (Strings.isNullOrEmpty(timeId)) {
            timeId = "-1";
        }
        Map<String, Object> map = new HashMap<>(2);
        String key = RedisCodeMsg.SOCCER_LINEUP_DATA.getName() + ":" + matchId;
        String redisTimeId = (String) redisUtils.hget(key, "TIME_ID");
        //对比timeId是否一致，如果一致，则不返回有效数据
        if (!Strings.isNullOrEmpty(redisTimeId) && redisTimeId.equals(timeId)) {
            map.put("infoList", Lists.newArrayList());
            map.put("timeId", redisTimeId);
            return map;
        }
        String value = (String) redisUtils.hget(key, "data");

        if (Strings.isNullOrEmpty(value)) {
            map = scheduleService.queryLineupDataByMatch(Long.parseLong(matchId));
        } else {
            JsonMapper jsonMapper = JsonMapper.defaultMapper();
            JavaType javaType = jsonMapper.buildMapType(Map.class, String.class, Object.class);
            map = jsonMapper.fromJson(value, javaType);
            map.put("timeId", redisTimeId);
        }
        return map;
    }

}
