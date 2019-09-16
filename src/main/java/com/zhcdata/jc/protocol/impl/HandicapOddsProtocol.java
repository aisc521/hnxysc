package com.zhcdata.jc.protocol.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.enums.RedisCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
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
 * Description:欧赔/亚盘/大小球数据
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/9/16 9:58
 */
@Slf4j
@Service("10200208")
public class HandicapOddsProtocol implements BaseProtocol {
    @Resource
    private CommonUtils commonUtils;
    @Resource
    private RedisUtils redisUtils;

    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        Map<String, Object> map = new HashMap<>(2);
        if (!commonUtils.validParamExistOrNoNum(map, paramMap, "matchId", ProtocolCodeMsg.MATCH_ID_NOT_ASSIGNED)) {
            return map;
        }
        if (!commonUtils.validParamExistOrNoNum(map, paramMap, Const.TYPE, ProtocolCodeMsg.TYPE_NOT_ASSIGNED)) {
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
        String key = RedisCodeMsg.SOCCER_HSET_ODDS_QT.getName() + ":" + matchId;
        if ("0".equals(type)) {
            //0 查询所有盘口
            String redisTimeId = (String) redisUtils.hget(key, "TIME_ID");
            //对比timeId是否一致，如果一致，则不返回有效数据
            if (!Strings.isNullOrEmpty(redisTimeId) && redisTimeId.equals(timeId)) {
                map.put("ouData", Lists.newArrayList());
                map.put("ypData", Lists.newArrayList());
                map.put("bsData", Lists.newArrayList());
                map.put("timeId", redisTimeId);
                return map;
            }
            //欧赔
            String value = (String) redisUtils.hget(key, "1");
            if (!Strings.isNullOrEmpty(value)) {
                List list = JsonMapper.defaultMapper().fromJson(value, List.class);
                map.put("ouData", list);
            } else {
                map.put("ouData", Lists.newArrayList());
            }
            //亚盘
            value = (String) redisUtils.hget(key, "2");
            if (!Strings.isNullOrEmpty(value)) {
                List list = JsonMapper.defaultMapper().fromJson(value, List.class);
                map.put("ypData", list);
            } else {
                map.put("ypData", Lists.newArrayList());
            }
            //大小球
            value = (String) redisUtils.hget(key, "3");
            if (!Strings.isNullOrEmpty(value)) {
                List list = JsonMapper.defaultMapper().fromJson(value, List.class);
                map.put("bsData", list);
            } else {
                map.put("bsData", Lists.newArrayList());
            }
            map.put("timeId", redisTimeId);
        } else {
            //查询指定盘口类型
            String redisTimeId = (String) redisUtils.hget(key, type + "_TIME_ID");
            //对比timeId是否一致，如果一致，则不返回有效数据
            if (!Strings.isNullOrEmpty(redisTimeId) && redisTimeId.equals(timeId)) {
                map.put("list", Lists.newArrayList());
                map.put("timeId", redisTimeId);
                return map;
            }
            //如果不一致，则查询redis数据
            String value = (String) redisUtils.hget(key, type);
            if (!Strings.isNullOrEmpty(value)) {
                List list = JsonMapper.defaultMapper().fromJson(value, List.class);
                map.put("list", list);
            } else {
                map.put("list", Lists.newArrayList());
            }
            map.put("timeId", redisTimeId);
        }
        return map;
    }

}
