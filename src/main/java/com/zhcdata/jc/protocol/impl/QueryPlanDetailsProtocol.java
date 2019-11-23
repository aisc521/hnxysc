package com.zhcdata.jc.protocol.impl;

import com.google.common.base.Strings;
import com.zhcdata.db.model.TbJcPlan;
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
import com.zhcdata.jc.tools.CommonUtils;
import com.zhcdata.jc.tools.JcLotteryUtils;
import org.apache.commons.lang3.StringUtils;
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
    @Resource
    private CommonUtils commonUtils;

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

            PlanResult2 planResult2 = new PlanResult2();
            try {
                String grade = "";
                String price = "";
                String planStatus = "";
                String pintroduction = "";
                String planTitle = "";
                List<PlanResult2> result = tbPlanService.queryPlanByIdandUser(id,uid);
                if (result != null && result.size() > 0) {
                    planResult2 = result.get(0);
                    grade = planResult2.getGrade();
                    price = planResult2.getPrice();
                    planStatus = planResult2.getPlanStatus();
                    pintroduction = planResult2.getPintroduction();
                    planTitle = planResult2.getTitle();
                }
                resultMap.put("planStatus", planStatus);
                resultMap.put("title", planTitle);
                resultMap.put("grade", grade);
                resultMap.put("price", price);
                resultMap.put("pintroduction", pintroduction);
                ExpertInfo info = tbJcExpertService.queryExpertDetailsAndUser(tbPlanService.queryExpertIdByPlanId(id),uid);
                if (info != null) {
                    //给专家人气加1
                    /*Integer pop = info.getPopularity();
                    if(pop == null){
                        pop = 0;
                    }
                    tbJcExpertService.updatePopAddOne(info.getId(),pop + 1);*/
                    resultMap.put("eid", info.getId());
                    resultMap.put("nickName", info.getNickName());
                    resultMap.put("img", info.getImg());
                    resultMap.put("lable", info.getLable());
                    resultMap.put("introduction", info.getIntroduction());
                    resultMap.put("fans", info.getFans());
                    resultMap.put("trend", info.getTrend());
                    resultMap.put("lz", commonUtils.JsLz(info));
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
                        map.put("homeTeamName",firstInfo.get("homeName").toString());
                        map.put("awayTeamName",firstInfo.get("awayName").toString());
                        String matchResult = String.valueOf(list.get(i).get("matchResult"));


                        String odds = list.get(i).get("odds").toString();
                        if(StringUtils.isNotBlank(odds) && odds != null && odds != "null"){
                            String[] oddsArr = odds.split("/");
                            if("vs".equals(matchResult)){
                                map.put("rang_sheng", "1.00");
                                map.put("rang_ping", "1.00");
                                map.put("rang_fu", "1.00");
                                map.put("no_rang_sheng", "1.00");
                                map.put("no_rang_ping", "1.00");
                                map.put("no_rang_fu", "1.00");
                            }else{
                                if(oddsArr.length == 6){
                                    map.put("rang_sheng", ""+oddsArr[3]);
                                    map.put("rang_ping", ""+oddsArr[4]);
                                    map.put("rang_fu", ""+oddsArr[5]);
                                    map.put("no_rang_sheng", ""+oddsArr[0]);
                                    map.put("no_rang_ping", ""+oddsArr[1]);
                                    map.put("no_rang_fu", ""+oddsArr[2]);
                                }
                            }
                        }else{
                            if("vs".equals(matchResult)){
                                map.put("rang_sheng", "1.00");
                                map.put("rang_ping", "1.00");
                                map.put("rang_fu", "1.00");
                                map.put("no_rang_sheng", "1.00");
                                map.put("no_rang_ping", "1.00");
                                map.put("no_rang_fu", "1.00");
                            }else{
                                map.put("rang_sheng", ""+list.get(i).get("homeTeamZhu"));
                                map.put("rang_ping", ""+list.get(i).get("homeTeamPing"));
                                map.put("rang_fu", ""+list.get(i).get("homeTeamKe"));
                                map.put("no_rang_sheng", ""+list.get(i).get("awayTeamZhu"));
                                map.put("no_rang_ping", ""+list.get(i).get("awayTeamPing"));
                                map.put("no_rang_fu", ""+list.get(i).get("awayTeamKe"));
                            }
                        }

                        map.put("no_rang_num", "0");
                        map.put("rang_num", ""+list.get(i).get("awayTeamRangballs"));
                        map.put("match_status", ""+list.get(i).get("statusmatch"));
                        map.put("match_result", ""+list.get(i).get("matchResult"));


                        String rang_num = String.valueOf(list.get(i).get("awayTeamRangballs"));
                        //计算那个中了
                        if(StringUtils.isNotBlank(matchResult) && !"null".equals(matchResult)){
                            if("vs".equals(matchResult)){//比赛异常状态返回0
                                map.put("winStatus", "0");
                                map.put("rwinStatus", "0");
                            }else{
                                String[] matchResultArr = matchResult.split(":");
                                Double matchResultDou1 = Double.valueOf(matchResultArr[0]);//住比分
                                Double matchResultDou2 = Double.valueOf(matchResultArr[1]);//客比分
                                //计算胜平负状态
                                String winStatus = "0";
                                String rwinStatus = "0";
                                if(matchResultDou1 > matchResultDou2){//胜
                                    winStatus = "1";
                                }
                                if(Objects.equals(matchResultDou1, matchResultDou2)){//平
                                    winStatus = "2";
                                }
                                if(matchResultDou1 < matchResultDou2){//负
                                    winStatus = "3";
                                }
                                if((matchResultDou1 + Double.valueOf(rang_num)) > matchResultDou2){//让胜
                                    rwinStatus = "1";
                                }
                                if(Objects.equals((matchResultDou1 + Double.valueOf(rang_num)),matchResultDou2)){//让平
                                    rwinStatus = "2";
                                }
                                if((matchResultDou1 + Double.valueOf(rang_num)) < matchResultDou2){//让负
                                    rwinStatus = "3";
                                }
                                map.put("winStatus", ""+winStatus);
                                map.put("rwinStatus", ""+rwinStatus);
                            }
                        }else{
                            map.put("winStatus", "4");//没有赛果
                            map.put("rwinStatus", "4");
                        }

                        map.put("planInfo", ""+list.get(i).get("planInfo"));
                        plan_info.add(map);
                    }
                }
                resultMap.put("plan_info", plan_info);
                resultMap.put("first_time", first);

                if(freeOrPay.get("type")==3){//免费
                    resultMap.put("payStaus", "3");
                }else{
                    resultMap.put("payStaus", "2");//付费
                }
                if(freeOrPay.get("pay")>0){//已购买
                    resultMap.put("buStatus", "1");
                }else{
                    resultMap.put("buStatus", "0");//未购买
                }

                TbJcPlan tbJcPlan = tbPlanService.queryPlanByPlanId(Long.valueOf(id));
                //给当前方案 增加人气值  + 1
                Integer pop = tbJcPlan.getPlanPopularity();
                if(pop == null){
                    pop = 0;
                }
                tbJcPlan.setPlanPopularity(pop + 1);
                tbPlanService.updatePlanByPlanId(tbJcPlan);

            } catch (Exception ex) {
                ex.printStackTrace();
                LOGGER.error("查询方案详情异常" + ex.toString());
            }
            return resultMap;

    }
}
