package com.zhcdata.jc.protocol.impl;

import com.zhcdata.jc.dto.LatestPlanReminderDto;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.TbPlanService;
import com.zhcdata.jc.tools.CommonUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    private static String warn = "大神带你飞，速速查看;火速围观，沾沾喜气;跟随红单一起来;收米很简单，连中嗨翻天;猛戳来收米，中奖很简单;大神带你飞，速速查看;火速围观，沾沾喜气;跟随红单一起来;收米很简单，连中嗨翻天;猛戳来收米，中奖很简单";

    @Resource
    private TbPlanService tbPlanService;

    @Resource
    private CommonUtils commonUtils;
    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        List<LatestPlanReminderDto> latestPlanReminderDto = tbPlanService.queryLatestPlanReminder();
        List<LatestPlanReminderDto> arrayList = new ArrayList<>();
        if(latestPlanReminderDto != null){
            for(int i = 0; i < latestPlanReminderDto.size(); i++){
                String warStr = warn.split(";")[Integer.valueOf((System.currentTimeMillis() + "").substring(11,12))];
                LatestPlanReminderDto latestPlanReminderDto1 = latestPlanReminderDto.get(i);
                String lz = commonUtils.JsLz2(latestPlanReminderDto1);
                latestPlanReminderDto1.setLz(lz);
                latestPlanReminderDto1.setWarn(warStr);
                arrayList.add(latestPlanReminderDto1);
            }
            resultMap.put("list", arrayList);
            return resultMap;
        }else{
            resultMap.put("resCode", ProtocolCodeMsg.PLAN_IS_NULL.getCode());
            resultMap.put("message", ProtocolCodeMsg.PLAN_IS_NULL.getMsg());
        }
        return resultMap;
    }

}
