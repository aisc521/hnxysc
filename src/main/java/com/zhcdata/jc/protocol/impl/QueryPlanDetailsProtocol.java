package com.zhcdata.jc.protocol.impl;

import com.google.common.base.Strings;
import com.zhcdata.jc.dto.MatchPlanResult1;
import com.zhcdata.jc.dto.PlanResult2;
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
 * @Description 方案详情
 * @Author cuishuai
 * @Date 2019/9/20 15:03
 */
@Service("10200220")
public class QueryPlanDetailsProtocol implements BaseProtocol {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private TbPlanService tbPlanService;

    @Resource
    private TbJcMatchService tbJcMatchService;

    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        Map<String, Object> map = new HashMap<>();
        String id = paramMap.get("id");
        if (Strings.isNullOrEmpty(id)) {
            LOGGER.info("[" + ProtocolCodeMsg.PLANID_NULL.getMsg() + "]:id---" + id);
            map.put("resCode", ProtocolCodeMsg.PLANID_NULL.getCode());
            map.put("message", ProtocolCodeMsg.PLANID_NULL.getMsg());
            return map;
        }
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        String id = paramMap.get("id");

        Map<String, Object> resultMap = new HashMap<>();

        PlanResult2 planResult2 = new PlanResult2();
        try {
            List<PlanResult2> result = tbPlanService.queryPlanById(id);
            if (result != null && result.size() > 0) {
                planResult2 = result.get(0);
                List<MatchPlanResult1> matchPlanResults = tbJcMatchService.queryList1(Long.valueOf(id));
                if (matchPlanResults != null && matchPlanResults.size() > 0) {
                    List<MatchPlanResult1> matchPlanResult2 = new ArrayList<>();
                    for(int i = 0; i < matchPlanResults.size(); i++){
                        MatchPlanResult1 matchPlanResult1 = matchPlanResults.get(i);
                        String planInfo = JcLotteryUtils.OddsInfoChange(matchPlanResult1.getPlanInfo());
                        matchPlanResult1.setPlanInfo(planInfo);
                        matchPlanResult2.add(matchPlanResult1);
                    }
                    planResult2.setList(matchPlanResults);
                }
            }
            resultMap.put("list", result);
        } catch (Exception ex) {
            LOGGER.error("查询方案详情异常" + ex.toString());
        }
        return resultMap;
    }
}
