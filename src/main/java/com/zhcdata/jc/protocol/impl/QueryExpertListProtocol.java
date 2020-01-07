package com.zhcdata.jc.protocol.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.zhcdata.jc.dto.ExpertInfo;
import com.zhcdata.jc.dto.ExpertInfoBdDto;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.dto.PurchasedPlanDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.TbJcExpertService;
import com.zhcdata.jc.tools.RedisUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 专家榜
 * @Author cuishuai
 * @Date 2019/9/20 15:32
 */
@Service("20200224")
public class QueryExpertListProtocol implements BaseProtocol {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Resource
    private RedisUtils redisUtils;

    @Resource
    private TbJcExpertService tbJcExpertService;
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
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        String type = paramMap.get("type");
        Map<String, Object> resultMap = new HashMap<>();
        String re = "";
        try {
            if("1".equals(type)){
                 re = (String )redisUtils.hget("SOCCER:HSET:EXPERTRANKING", "sevenMz");
            }
            if("2".equals(type)){
                 re = (String )redisUtils.hget("SOCCER:HSET:EXPERTRANKING", "nowLh");
            }
            if("3".equals(type)){
                 re = (String )redisUtils.hget("SOCCER:HSET:EXPERTRANKING", "sevenReturn");
            }
            if(StringUtils.isNotBlank(re)){
                resultMap.put("list", JSONObject.parseArray(re, Map.class));
            }else{
                resultMap.put("list", "");
            }
        } catch (Exception ex) {
            LOGGER.error(ex.toString());
        }
        return resultMap;
    }
}
