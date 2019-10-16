package com.zhcdata.jc.protocol.impl;

import com.google.common.base.Strings;
import com.zhcdata.jc.dto.ExpertInfo;
import com.zhcdata.jc.dto.MatchPlanResult1;
import com.zhcdata.jc.dto.PlanResult2;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.TbJcExpertService;
import com.zhcdata.jc.service.TbJcMatchService;
import com.zhcdata.jc.service.TbPlanService;
import com.zhcdata.jc.tools.JcLotteryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description 方案详情
 * @Author cuishuai
 * @Date 2019/9/20 15:03
 */
@Service("20200220")
public class QueryPlanDetailsProtocol implements BaseProtocol {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private TbPlanService tbPlanService;

    @Resource
    private TbJcMatchService tbJcMatchService;

    @Resource
    private TbJcExpertService tbJcExpertService;

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
            ExpertInfo info = tbJcExpertService.queryExpertDetails(tbPlanService.queryExpertIdByPlanId(id));
            if (info != null) {
                resultMap.put("eid", info.getId());
                resultMap.put("nickName", info.getNickName());
                resultMap.put("img", info.getImg());
                resultMap.put("lable", info.getLable());
                resultMap.put("introduction", info.getIntroduction());
                resultMap.put("fans", info.getFans());
                resultMap.put("trend", info.getTrend());
                resultMap.put("lz", info.getLzNow());
                resultMap.put("zSevenDays", info.getzSevenDays());
                resultMap.put("returnSevenDays", info.getReturnSevenDays());
                resultMap.put("status", info.getStatus());
            } else {
                resultMap.put("eid", "");
                resultMap.put("nickName", "");
                resultMap.put("img", "");
                resultMap.put("lable", "");
                resultMap.put("introduction", "");
                resultMap.put("fans", "");
                resultMap.put("trend", "");
                resultMap.put("lz", "");
                resultMap.put("zSevenDays", "");
                resultMap.put("returnSevenDays", "");
                resultMap.put("status", "");
            }
            List<Map<String,Object>> list = tbPlanService.queryPlanInfo(id);
            StringBuilder firstMsg = new StringBuilder();
            List<Map<String, Object>> plan_info = new ArrayList<>();
            if (list!=null){
                if (list.size()>0){
                    Map<String, Object> firstInfo = list.get(0);
                    firstMsg.append(firstInfo.get("Num").toString()).append("|");
                    firstMsg.append(firstInfo.get("matchName").toString()).append("|");
                    Date dateOfMatch = (Date)firstInfo.get("dateOfMatch");
                    firstMsg.append(dateOfMatch.getTime()).append("|");
                    firstMsg.append(firstInfo.get("homeTeamName").toString()).append("|");
                    firstMsg.append(firstInfo.get("awayTeamName"));//toString()).append("|");
                }
                for (int i = 0; i < list.size(); i++) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("no_rang_num", "让0");
                    map.put("no_rang_sheng", "胜"+list.get(i).get("homeTeamZhu"));
                    map.put("no_rang_ping", "平"+list.get(i).get("homeTeamPing"));
                    map.put("no_rang_fu", "负"+list.get(i).get("homeTeamKe"));

                    map.put("rang_num", "让"+list.get(i).get("awayTeamRangballs"));
                    map.put("rang_sheng", "胜"+list.get(i).get("awayTeamZhu"));
                    map.put("rang_ping", "平"+list.get(i).get("awayTeamPing"));
                    map.put("rang_fu", "负"+list.get(i).get("awayTeamKe"));
                    plan_info.add(map);
                }
            }
            resultMap.put("first_msg", firstMsg.toString());
            resultMap.put("plan_info", plan_info);

        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error("查询方案详情异常" + ex.toString());
        }
        return resultMap;
    }
}
