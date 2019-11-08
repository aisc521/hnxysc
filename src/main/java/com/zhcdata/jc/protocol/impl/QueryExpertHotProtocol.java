package com.zhcdata.jc.protocol.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.tools.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 查询热门专家
 * @Author cuishuai
 * @Date 2019/9/20 14:01
 */
@Service("20200216")
public class QueryExpertHotProtocol implements BaseProtocol {
    @Resource
    private RedisUtils redisUtils;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            String re = (String )redisUtils.hget("SOCCER:HSET:EXPERT", "hot");
            if(!Strings.isNullOrEmpty(re)) {
                resultMap.put("list", JSONObject.parseArray(re, Map.class));
                resultMap.put("message", "成功");
                resultMap.put("resCode", "000000");
                resultMap.put("busiCode", "");
            }else {
                resultMap.put("list", "");
                resultMap.put("message", "查询结果无数据");
                resultMap.put("resCode", "000000");
                resultMap.put("busiCode", "");
            }
        } catch (Exception ex) {
            LOGGER.error("查询热门专家异常：",ex);
        }
        return resultMap;
    }
}
