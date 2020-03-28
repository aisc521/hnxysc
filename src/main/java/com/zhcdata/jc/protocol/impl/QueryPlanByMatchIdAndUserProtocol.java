package com.zhcdata.jc.protocol.impl;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.dto.MatchInfoDto;
import com.zhcdata.jc.dto.PlanIdDto;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.dto.QueryPlanByMatchIdDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.ScheduleService;
import com.zhcdata.jc.service.TbJcMatchService;
import com.zhcdata.jc.service.TbPlanService;
import com.zhcdata.jc.tools.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/10/26 12:08
 */
@Service("20200240")
public class QueryPlanByMatchIdAndUserProtocol implements BaseProtocol {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private TbPlanService tbPlanService;
    @Resource
    private TbJcMatchService tbJcMatchService;
    @Resource
    private CommonUtils commonUtils;
    @Resource
    private ScheduleService scheduleService;

    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        Map<String, Object> map = new HashMap<>();
        String macchId = paramMap.get("matchId");
        if (Strings.isNullOrEmpty(macchId)) {
            LOGGER.info("[" + ProtocolCodeMsg.MATCH_ID_NOT_ASSIGNED.getMsg() + "]:machId---" + macchId);
            map.put("resCode", ProtocolCodeMsg.MATCH_ID_NOT_ASSIGNED.getCode());
            map.put("message", ProtocolCodeMsg.MATCH_ID_NOT_ASSIGNED.getMsg());
            return map;
        }
        String pageNo = paramMap.get("pageNo");
        if (Strings.isNullOrEmpty(pageNo)) {
            LOGGER.info("[" + ProtocolCodeMsg.PAGE_NO_NOT_ILLEGAL.getMsg() + "]:pageNo---" + pageNo);
            map.put("resCode", ProtocolCodeMsg.PAGE_NO_NOT_ILLEGAL.getCode());
            map.put("message", ProtocolCodeMsg.PAGE_NO_NOT_ILLEGAL.getMsg());
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
        String matchId = paramMap.get("matchId");
        String pageNo = paramMap.get("pageNo");
        String userId = paramMap.get("userId");
        PageInfo<PlanIdDto> planIdDtos = tbPlanService.selectPlanIdByMatchId(matchId,Integer.valueOf(pageNo),20);
        List<PlanIdDto> planIdDtoList = planIdDtos.getList();
        List list = new ArrayList();

        if(planIdDtoList!= null && planIdDtoList.size()>0) {
            String[] a = new String[planIdDtoList.size()];
            for (int i = 0; i < planIdDtoList.size(); i++) {
                a[i] = String.valueOf(planIdDtoList.get(i).getPlanId());
            }
            //判断是否是已经完场的比赛
            Schedule schedule = scheduleService.queryScheduleById(Long.valueOf(matchId));
            if (schedule == null) {
                return resultMap;
            }
            List<QueryPlanByMatchIdDto> queryPlanByMatchIdDto1 = new ArrayList<>();
            //已经完场的比赛
            if ("-1".equals(String.valueOf(schedule.getMatchstate()))) {
                queryPlanByMatchIdDto1 = tbPlanService.queryPlanInfoByPlanIdandUserIdList(a, userId, "2");
            } else {
                queryPlanByMatchIdDto1 = tbPlanService.queryPlanInfoByPlanIdandUserIdList(a, userId, "1");
            }
            if (queryPlanByMatchIdDto1 != null && queryPlanByMatchIdDto1.size() > 0) {
                for (int j = 0; j < queryPlanByMatchIdDto1.size(); j++) {
                    QueryPlanByMatchIdDto queryPlanByMatchIdDto = queryPlanByMatchIdDto1.get(j);
                    queryPlanByMatchIdDto.setPlanId(queryPlanByMatchIdDto.getPlanId());
                    String lz = commonUtils.JsLz3(queryPlanByMatchIdDto);
                    queryPlanByMatchIdDto.setzSevenDays(String.valueOf(new BigDecimal(queryPlanByMatchIdDto.getzSevenDays()).intValue()));
                    queryPlanByMatchIdDto.setzFiveDays(String.valueOf(new BigDecimal(queryPlanByMatchIdDto.getzFiveDays()).intValue()));
                    queryPlanByMatchIdDto.setzThreeDays(String.valueOf(new BigDecimal(queryPlanByMatchIdDto.getzThreeDays()).intValue()));
                    queryPlanByMatchIdDto.setLz(lz);
                    List<MatchInfoDto> matchInfoDtos = tbJcMatchService.queryMatchInfoDtoByPlanId(queryPlanByMatchIdDto.getPlanId());
                    queryPlanByMatchIdDto.setList(matchInfoDtos);
                    queryPlanByMatchIdDto.setMatchPlanType("1");
                    list.add(queryPlanByMatchIdDto);
                }
            }
        }



        resultMap.put("pageTotal",planIdDtos.getPages());
        resultMap.put("pageNo", pageNo);
        resultMap.put("list",list);
        return resultMap;
    }
}
