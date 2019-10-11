package com.zhcdata.jc.protocol.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.base.Strings;
import com.zhcdata.jc.dto.MatchPlanResult;
import com.zhcdata.jc.dto.PlanResult1;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.tools.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.mapper.JsonMapper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 查询热门方案
 * @Author cuishuai
 * @Date 2019/9/20 14:59
 */
@Service("20200219")
public class QueryHotPlanProtocl implements BaseProtocol {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Resource
    private RedisUtils redisUtils;

    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        Map<String, Object> map = new HashMap<>();
        String type = paramMap.get("type");
        if (Strings.isNullOrEmpty(type)) {
            LOGGER.info("[" + ProtocolCodeMsg.PLAN_TYPE.getMsg() + "]:type---" + type);
            map.put("resCode", ProtocolCodeMsg.PLAN_TYPE.getCode());
            map.put("message", ProtocolCodeMsg.PLAN_TYPE.getMsg());
            return map;
        }
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        String type = paramMap.get("type");

        List<PlanResult1> f_result = new ArrayList<>();


        try {
            String re = (String) redisUtils.hget("SOCCER:HSET:PLAN", "hot");
            JsonMapper jsonMapper = JsonMapper.defaultMapper();
            JavaType javaType = jsonMapper.buildCollectionType(List.class, PlanResult1.class);
            List<PlanResult1> result = jsonMapper.fromJson(re, javaType);

            for (int j = 0; j < result.size(); j++) {
                if (type.equals("1")) {
                    //单场
                    List<MatchPlanResult> matchPlanResults = result.get(j).getList();
                    if (matchPlanResults != null && matchPlanResults.size() == 1) {
                        f_result.add(result.get(j));
                    }
                } else if (type.equals("2")) {
                    //2串1
                    List<MatchPlanResult> matchPlanResults = result.get(j).getList();
                    if (matchPlanResults != null && matchPlanResults.size() == 2) {
                        f_result.add(result.get(j));
                    }
                } else if (type.equals("3")) {
                    //不中退
                    if (result.get(j).getType() == "3") {
                        f_result.add(result.get(j));
                    }
                } else if (type.equals("4")) {
                    //免费
                    if (result.get(j).getType() == "4") {
                        f_result.add(result.get(j));
                    }
                } else if (type.equals("-1")) {
                    f_result.add(result.get(j));
                }
            }

            if (!Strings.isNullOrEmpty(re)) {
                resultMap.put("list", f_result);
                resultMap.put("message", "成功");
                resultMap.put("resCode", "000000");
                resultMap.put("busiCode", "");
            } else {
                resultMap.put("list", "");
                resultMap.put("message", "查询结果无数据");
                resultMap.put("resCode", "");
                resultMap.put("busiCode", "");
            }
        } catch (Exception ex) {
            LOGGER.error("查询热门方案异常:" + ex);
        }
        return resultMap;

    }
}
