package com.zhcdata.jc.protocol.impl;

import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.tools.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 获取足彩期号(
 * @Author cuishuai
 * @Date 2019/9/24 14:27
 */
@Service("20200225")
public class QueryZcDrawNoProtocol implements BaseProtocol {
    @Resource
    private RedisUtils redisUtils;


    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        String re = (String) redisUtils.hget("SOCCER:HSET:DRAWNO", "zc");
        resultMap.put("drawno", re);
        return resultMap;
    }
}
