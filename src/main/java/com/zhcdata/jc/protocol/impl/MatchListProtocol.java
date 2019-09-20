package com.zhcdata.jc.protocol.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.base.Strings;
import com.zhcdata.jc.dto.MatchResult1;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.ScheduleService;
import com.zhcdata.jc.tools.CommonUtils;
import com.zhcdata.jc.tools.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.utils.mapper.JsonMapper;
import org.springside.modules.utils.number.NumberUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/20 10:29
 */
public class MatchListProtocol implements BaseProtocol {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ScheduleService scheduleService;

    @Resource
    private CommonUtils commonUtils;
    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        Map<String, Object> map = new HashMap<>();
        String type = paramMap.get("type");
        if (Strings.isNullOrEmpty(type)) {
            LOGGER.info("[" + ProtocolCodeMsg.TYPE_NULL.getMsg() + "]:type---" + type);
            map.put("resCode", ProtocolCodeMsg.TYPE_NULL.getCode());
            map.put("message", ProtocolCodeMsg.TYPE_NULL.getMsg());
            return map;
        }

        String matchTime = paramMap.get("matchTime");
        if (Strings.isNullOrEmpty(matchTime)) {
            LOGGER.info("[" + ProtocolCodeMsg.TIME_NULL.getMsg() + "]:matchTime---" + matchTime);
            map.put("resCode", ProtocolCodeMsg.TIME_NULL.getCode());
            map.put("message", ProtocolCodeMsg.TIME_NULL.getMsg());
            return map;
        }

        String pageNo = paramMap.get("pageNo");
        if (Strings.isNullOrEmpty(pageNo) || !NumberUtil.isNumber(pageNo)) {
            LOGGER.info("[" + ProtocolCodeMsg.PAGENO_NULL.getMsg() + "]:pageNo---" + pageNo);
            map.put("resCode", ProtocolCodeMsg.PAGENO_NULL.getCode());
            map.put("message", ProtocolCodeMsg.PAGENO_NULL.getMsg());
            return map;
        }

        String pageAmount = paramMap.get("pageAmount");
        if (Strings.isNullOrEmpty(pageAmount) || !NumberUtil.isNumber(pageAmount)) {
            LOGGER.info("[" + ProtocolCodeMsg.PAGEAMOUNT_NULL.getMsg() + "]:pageAmount---" + pageAmount);
            map.put("resCode", ProtocolCodeMsg.PAGEAMOUNT_NULL.getCode());
            map.put("message", ProtocolCodeMsg.PAGEAMOUNT_NULL.getMsg());
            return map;
        }
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        Map<String, Object> map = new HashMap<>();
        String type = paramMap.get("type");
        String time = paramMap.get("matchTime");
        String pageNo = paramMap.get("pageNo");
        if(type.equals("all")){
            type="5";
        }else if(type.equals("2")) {
            //北单按期号存的缓存
            time = scheduleService.queryBdNum(commonUtils.getSE().split(",")[0], commonUtils.getSE().split(",")[1]);
        }else if(type.equals("3")){
            time = scheduleService.queryZcNum(commonUtils.getSE().split(",")[0], commonUtils.getSE().split(",")[1]);
        }
        String re = (String) redisUtils.hget("SOCCER:HSET:AGAINSTLIST"+time + type, pageNo);
        if(!Strings.isNullOrEmpty(re)){
            JavaType javaType = JsonMapper.defaultMapper().buildMapType(Map.class,String.class,Object.class);
            map = JsonMapper.defaultMapper().fromJson(re, javaType);
            String s=JsonMapper.defaultMapper().toJson(map.get("list"));
            JsonMapper jsonMapper = JsonMapper.defaultMapper();
            JavaType javaType1 = jsonMapper.buildCollectionType(List.class, MatchResult1.class);
            List<MatchResult1> newList=jsonMapper.fromJson(s, javaType1);
            map.put("list",newList);
        }
        return null;
    }
}
