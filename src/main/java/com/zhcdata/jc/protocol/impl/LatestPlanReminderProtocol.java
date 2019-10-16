package com.zhcdata.jc.protocol.impl;

import com.zhcdata.jc.dto.LatestPlanReminderDto;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.TbPlanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 最新方案提醒
 * @Author cuishuai
 * @Date 2019/10/12 14:30
 */
@Service("20200231")
public class LatestPlanReminderProtocol implements BaseProtocol {

    @Resource
    private TbPlanService tbPlanService;

    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        List<LatestPlanReminderDto> latestPlanReminderDto = tbPlanService.queryLatestPlanReminder();
        if(latestPlanReminderDto == null){
            resultMap.put("resCode", ProtocolCodeMsg.PLAN_IS_NULL.getCode());
            resultMap.put("message", ProtocolCodeMsg.PLAN_IS_NULL.getMsg());
            return resultMap;
        }
        resultMap.put("list", latestPlanReminderDto);
        return resultMap;
    }
}
