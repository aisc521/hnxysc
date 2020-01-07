package com.zhcdata.jc.protocol.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.zhcdata.jc.dto.MatchPlanResult;
import com.zhcdata.jc.dto.PlanResult1;
import com.zhcdata.jc.dto.PlanResult3;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.tools.RedisUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
 * @Description 查询热门方案 不带userId
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
        String pageNo = paramMap.get("pageNo");
        if (Strings.isNullOrEmpty(pageNo)) {
            LOGGER.info("[" + ProtocolCodeMsg.PAGE_NO_NOT_ILLEGAL.getMsg() + "]:pageNo---" + pageNo);
            map.put("resCode", ProtocolCodeMsg.PAGE_NO_NOT_ILLEGAL.getCode());
            map.put("message", ProtocolCodeMsg.PAGE_NO_NOT_ILLEGAL.getMsg());
            return map;
        }
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        String type = paramMap.get("type");
        String pageNo = paramMap.get("pageNo");
        List<PlanResult1> f_result = new ArrayList<>();
        try {
            String re = (String) redisUtils.hget("SOCCER:HSET:PLANHOT", pageNo);
            JSONObject jsonObject = JSONObject.fromObject(re);
            System.out.println(jsonObject);
            PlanResult3 planResult3 = com.alibaba.fastjson.JSONObject.parseObject(re,PlanResult3.class);
            List<PlanResult1> result = planResult3.getList();
            for (int j = 0; j < result.size(); j++) {
                if (type.equals("2")) {
                    //单场

                    List<MatchPlanResult> matchPlanResults = result.get(j).getList();
                    if (matchPlanResults != null && matchPlanResults.size() == 1) {
                        f_result.add(result.get(j));
                    }
                } else if (type.equals("1")) {
                    //2串1
                    List<MatchPlanResult> matchPlanResults = result.get(j).getList();
                    if (matchPlanResults != null && matchPlanResults.size() == 2) {
                        f_result.add(result.get(j));
                    }
                } else if (type.equals("3")) {
                    //不中退
                    if (result.get(j).getPlanType().equals("2")) {
                        f_result.add(result.get(j));
                    }
                } else if (type.equals("4")) {
                    //免费
                    if (result.get(j).getPlanType().equals("3")) {
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
                resultMap.put("pageNo", jsonObject.get("pageNo"));
                resultMap.put("pageTotal", jsonObject.get("pageTotal"));
            } else {
                resultMap.put("list", "");
                resultMap.put("message", "查询结果无数据");
                resultMap.put("resCode", "");
                resultMap.put("busiCode", "");
                resultMap.put("pageNo","");
                resultMap.put("pageTotal", "");
            }
        } catch (Exception ex) {
            LOGGER.error("查询热门方案异常:" + ex);
        }
        return resultMap;

    }
}
