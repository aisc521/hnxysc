package com.zhcdata.jc.protocol.impl;

import com.google.common.base.Strings;
import com.zhcdata.jc.dto.ExpertInfo;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.TbJcExpertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 专家详情
 * @Author cuishuai
 * @Date 2019/9/20 14:03
 */
@Service("20200217")
public class QueryExpertDetailsProtocol implements BaseProtocol {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private TbJcExpertService tbJcExpertService;
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
        String expertId = paramMap.get("id");
        Map<String, Object> resultMap = new HashMap<>();
        try{
            ExpertInfo info = tbJcExpertService.queryExpertDetails(expertId);
            if (info != null) {
                resultMap.put("id", info.getId());
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
                resultMap.put("message", "成功");
                resultMap.put("resCode", "000000");
                resultMap.put("busiCode", "");
            } else {
                resultMap.put("id", "");
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
                resultMap.put("message", "专家id不存在");
                resultMap.put("resCode", "");
                resultMap.put("busiCode", "");
            }
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("查询专家详情异常" + e.toString());
        }
        return resultMap;
    }
}
