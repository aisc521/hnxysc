package com.zhcdata.jc.protocol.impl;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.zhcdata.jc.dto.MatchPlanResult;
import com.zhcdata.jc.dto.PlanResult1;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.TbJcMatchService;
import com.zhcdata.jc.service.TbPlanService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 相关方案  带userId
 * @Author cuishuai
 * @Date 2019/9/20 15:08
 */
@Service("20200236")
public class QueryPlanByMatchIdUserIdProtocol implements BaseProtocol {
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
        String id = paramMap.get("id");
        String pageNo = paramMap.get("pageNo");
        List<PlanResult1> result = new ArrayList<>();

        try {
            List<PlanResult1> list = tbPlanService.queryPlanId(id);
            if (list != null && list.size() > 0) {
                String pIdList = "";
                for (int p = 0; p < list.size(); p++) {
                    pIdList += list.get(p).getId();
                    if (p != list.size() - 1) {
                        pIdList += ",";
                    }
                }

                String userId = paramMap.get("userId");
                PageInfo<PlanResult1> planList1 = tbPlanService.queryPlanByExpertId(id,null,userId,Integer.valueOf(pageNo),20);
                List<PlanResult1> planList = planList1.getList();

                for (int i = 0; i < planList.size(); i++) {
                    PlanResult1 result1 = planList.get(i);
                    List<MatchPlanResult> matchPlanResults = tbJcMatchService.queryList(planList.get(i).getId());
                    if (matchPlanResults != null && matchPlanResults.size() > 0) {
                        result1.setList(matchPlanResults);
                    }
                    result.add(result1);
                }
                resultMap.put("pageTotal",planList1.getTotal());
            }
            resultMap.put("list",result);
            resultMap.put("pageNo",pageNo);
        }catch (Exception ex){
            LOGGER.error("类似方案异常:"+ex);
        }
        return resultMap;
    }
}
