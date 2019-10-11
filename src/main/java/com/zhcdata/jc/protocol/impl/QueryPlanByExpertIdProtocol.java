package com.zhcdata.jc.protocol.impl;

import com.google.common.base.Strings;
import com.zhcdata.jc.dto.MatchPlanResult;
import com.zhcdata.jc.dto.PlanResult1;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.TbJcMatchService;
import com.zhcdata.jc.service.TbPlanService;
import com.zhcdata.jc.tools.JcLotteryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 推荐-专家已推方案列表
 * @Author cuishuai
 * @Date 2019/9/20 14:08
 */
@Service("20200218")
public class QueryPlanByExpertIdProtocol implements BaseProtocol{
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private TbPlanService tbPlanService;
    @Resource
    private TbJcMatchService TbJcMatchService;
    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        Map<String, Object> map = new HashMap<>();
        String id = paramMap.get("id");
        if (Strings.isNullOrEmpty(id)) {
            LOGGER.info("[" + ProtocolCodeMsg.EXPERT_ID.getMsg() + "]:id---" + id);
            map.put("resCode", ProtocolCodeMsg.EXPERT_ID.getCode());
            map.put("message", ProtocolCodeMsg.EXPERT_ID.getMsg());
            return map;
        }
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();

        String id = paramMap.get("id");
        List<PlanResult1> result = new ArrayList<>();
        try{
            List<PlanResult1> planList = tbPlanService.queryPlanByExpertId(id,null);
            for (int i = 0; i < planList.size(); i++) {
                PlanResult1 result1 = planList.get(i);
                List<MatchPlanResult> matchPlanResults = TbJcMatchService.queryList(planList.get(i).getId());
                if (matchPlanResults != null && matchPlanResults.size() > 0) {
                    List<MatchPlanResult> matchPlanResults1 = new ArrayList<>();
                    for(int j = 0; j < matchPlanResults.size(); j++){
                        MatchPlanResult matchPlanResult1 = matchPlanResults.get(j);
                        String planInfo = JcLotteryUtils.OddsInfoChange(matchPlanResult1.getPlanInfo());
                        matchPlanResult1.setPlanInfo(planInfo);
                        matchPlanResults1.add(matchPlanResult1);
                    }
                    result1.setList(matchPlanResults1);
                }
                result.add(result1);
            }

        }catch (Exception e){
            e.printStackTrace();

            LOGGER.error("查询该专家已发方案异常:" + e);
        }
        resultMap.put("list", result);
        return resultMap;
    }
}
