package com.zhcdata.jc.protocol.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.base.Strings;
import com.zhcdata.jc.dto.MatchPlanResult;
import com.zhcdata.jc.dto.PlanResult1;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.TbJcMatchService;
import com.zhcdata.jc.service.TbPlanService;
import com.zhcdata.jc.tools.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.mapper.JsonMapper;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description 查询热门方案 带userId
 * @Author cuishuai
 * @Date 2019/9/20 14:59
 */
@Service("20200235")
public class QueryHotPlanUserProtocl implements BaseProtocol {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Resource
    private RedisUtils redisUtils;

    @Resource
    private TbPlanService tbPlanService;
    @Resource
    private TbJcMatchService tbJcMatchService;
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
        String userId = paramMap.get("userId");
        if (Strings.isNullOrEmpty(userId)) {
            LOGGER.info("[" + ProtocolCodeMsg.USER_ID_NOT_EXIST.getMsg() + "]:userId---" + userId);
            map.put("resCode", ProtocolCodeMsg.USER_ID_NOT_EXIST.getCode());
            map.put("message", ProtocolCodeMsg.USER_ID_NOT_EXIST.getMsg());
            return map;
        }
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        List<PlanResult1> result = new ArrayList<>();
        List<PlanResult1> f_result = new ArrayList<>();

        String type = paramMap.get("type");
        String userId = paramMap.get("userId");
        String re = (String)redisUtils.hget("SOCCER:HSET:EXPERT", "id");
        if (!Strings.isNullOrEmpty(re)) {
            re = re.replace("'", "");
            re = re.substring(0, re.length() - 1);
            String[] ids = re.split(",");

            for (int i = 0; i < ids.length; i++) {
                List<PlanResult1> planList = tbPlanService.queryPlanByExpertId(ids[i], null,userId);

                for (int k = 0; k < planList.size(); k++) {
                    PlanResult1 result1 = planList.get(k);
                    List<MatchPlanResult> matchPlanResults = tbJcMatchService.queryList(planList.get(k).getId());
                    if (matchPlanResults != null && matchPlanResults.size() > 0) {
                        result1.setList(matchPlanResults);
                    }
                    result.add(result1);
                }
            }

        }

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
        resultMap.put("list",f_result);
        return resultMap;

    }
}
