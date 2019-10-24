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
        String id = paramMap.get("planId");
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
        String id = paramMap.get("planId");
        String uid = paramMap.get("uid");
        Map<String,Long> freeOrPay = tbPlanService.checkFreeOrPayByUidAndPlanId(uid,id);
        Map<String, Object> resultMap = new HashMap<>();
        /*if (freeOrPay.get("type")==3||freeOrPay.get("pay")>0){*/

            PlanResult2 planResult2 = new PlanResult2();
            try {
                List<PlanResult2> result = tbPlanService.queryPlanByIdandUser(id,uid);
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
                    //给专家人气加1
                    Integer pop = info.getPopularity();
                    if(pop == null){
                        pop = 0;
                    }
                    tbJcExpertService.updatePopAddOne(info.getId(),pop + 1);
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
                //Map<String,Object> list = tbPlanService.queryPlanInfoNextTime(id);

                List<Map<String, Object>> plan_info = new ArrayList<>();
                long first = 9999999999999L;
                if (list!=null){
                    for (int i = 0; i < list.size(); i++) {
                        Map<String, Object> map = new HashMap<>();
                        Map<String, Object> firstInfo = list.get(i);

                        map.put("num",firstInfo.get("Num").toString());
                        map.put("matchName",firstInfo.get("matchName").toString());
                        Date dateOfMatch = (Date)firstInfo.get("dateOfMatch");
                        if (dateOfMatch.getTime()<first)
                            first = dateOfMatch.getTime();
                        map.put("time",dateOfMatch.getTime());
                        map.put("homeTeamName",firstInfo.get("homeTeamName").toString());
                        map.put("awayTeamName",firstInfo.get("awayTeamName").toString());

                        map.put("no_rang_num", "0");
                        map.put("no_rang_sheng", ""+list.get(i).get("homeTeamZhu"));
                        map.put("no_rang_ping", ""+list.get(i).get("homeTeamPing"));
                        map.put("no_rang_fu", ""+list.get(i).get("homeTeamKe"));

                        map.put("rang_num", ""+list.get(i).get("awayTeamRangballs"));
                        map.put("rang_sheng", ""+list.get(i).get("awayTeamZhu"));
                        map.put("rang_ping", ""+list.get(i).get("awayTeamPing"));
                        map.put("rang_fu", ""+list.get(i).get("awayTeamKe"));
                        map.put("match_status", ""+list.get(i).get("statusmatch"));
                        map.put("match_result", ""+list.get(i).get("matchResult"));

                        String matchResult = String.valueOf(list.get(i).get("matchResult"));
                        String rang_num = String.valueOf(list.get(i).get("awayTeamRangballs"));
                        //计算那个中了
                        String[] matchResultArr = matchResult.split(":");
                        Double matchResultDou1 = Double.valueOf(matchResultArr[0]);//住比分
                        Double matchResultDou2 = Double.valueOf(matchResultArr[1]);//客比分
                        //计算胜平负状态
                        String winStatus = "0";
                        String rwinStatus = "0";
                        if(matchResultDou1 > matchResultDou2){//胜
                            winStatus = "1";
                        }
                        if(matchResultDou1 == matchResultDou2){//平
                            winStatus = "2";
                        }
                        if(matchResultDou1 < matchResultDou2){//负
                            winStatus = "3";
                        }
                        if((matchResultDou1 + Double.valueOf(rang_num)) > matchResultDou2){//让胜
                            rwinStatus = "1";
                        }
                        if((matchResultDou1 + Double.valueOf(rang_num)) == matchResultDou2){//让平
                            rwinStatus = "2";
                        }
                        if((matchResultDou1 + Double.valueOf(rang_num)) < matchResultDou2){//让负
                            rwinStatus = "3";
                        }
                        map.put("winStatus", ""+winStatus);
                        map.put("rwinStatus", ""+rwinStatus);
                        map.put("planInfo", ""+list.get(i).get("planInfo"));
                        plan_info.add(map);
                    }
                }
                resultMap.put("plan_info", plan_info);
                resultMap.put("first_time", first);
                resultMap.put("planStatus", planResult2.getPlanStatus());
                if(freeOrPay.get("type")==3){//免费
                    resultMap.put("payStaus", "3");
                }else{
                    resultMap.put("payStaus", "2");//付费
                }
                if(freeOrPay.get("pay")>0){//已购买
                    resultMap.put("buyStatus", "0");
                }else{
                    resultMap.put("buyStatus", "1");//未购买
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                LOGGER.error("查询方案详情异常" + ex.toString());
            }
            return resultMap;
       /* }else {
            resultMap.put("resCode","999999");
            resultMap.put("message","方案尚未购买");
            return resultMap;
        }*/

    }
}
