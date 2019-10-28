package com.zhcdata.jc.protocol.impl;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.zhcdata.jc.dto.MatchInfoDto;
import com.zhcdata.jc.dto.PlanIdDto;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.dto.QueryPlanByMatchIdDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.TbJcMatchService;
import com.zhcdata.jc.service.TbPlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 相关方案  根据matchId查询 不带userId
 * @Author cuishuai
 * @Date 2019/10/26 11:07
 */
@Service("20200239")
public class QueryPlanByMatchIdProtocol implements BaseProtocol {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Resource
    private TbPlanService tbPlanService;
    @Resource
    private TbJcMatchService tbJcMatchService;
    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        Map<String, Object> map = new HashMap<>();
        String machId = paramMap.get("matchId");
        if (Strings.isNullOrEmpty(machId)) {
            LOGGER.info("[" + ProtocolCodeMsg.MATCH_ID_NOT_ASSIGNED.getMsg() + "]:machId---" + machId);
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
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        //根据matchId 查询相关的方案id
        Map<String, Object> resultMap = new HashMap<>();
        String matchId = paramMap.get("matchId");
        String pageNo = paramMap.get("pageNo");
        PageInfo<PlanIdDto> planIdDtos = tbPlanService.selectPlanIdByMatchId(matchId,Integer.valueOf(pageNo),20);
        List<PlanIdDto> planIdDtoList = planIdDtos.getList();
        List list = new ArrayList();
        if(planIdDtoList.size() > 0){
            for(int i = 0; i < planIdDtoList.size(); i++){
                //根据planId 查询 方案信息
                QueryPlanByMatchIdDto queryPlanByMatchIdDto = tbPlanService.queryPlanInfoByPlanId(planIdDtoList.get(i).getPlanId());
                if(queryPlanByMatchIdDto != null){
                    List<MatchInfoDto> matchInfoDtos = tbJcMatchService.queryMatchInfoDtoByPlanId(planIdDtoList.get(i).getPlanId());
                    queryPlanByMatchIdDto.setList(matchInfoDtos);
                    list.add(queryPlanByMatchIdDto);
                }
            }
        }
        resultMap.put("pageTotal",planIdDtos.getPages());
        resultMap.put("totalNum", planIdDtos.getTotal());
        resultMap.put("list",list);
        return resultMap;
    }
}
