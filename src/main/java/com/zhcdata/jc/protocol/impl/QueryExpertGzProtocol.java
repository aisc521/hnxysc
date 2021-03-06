package com.zhcdata.jc.protocol.impl;

import com.google.common.base.Strings;
import com.zhcdata.jc.dto.ExpertInfo;
import com.zhcdata.jc.dto.ExpertInfoDto;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.TbJcExpertService;
import com.zhcdata.jc.tools.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 已关注专家
 * @Author cuishuai
 * @Date 2019/9/20 16:25
 */
@Service("20200302")
public class QueryExpertGzProtocol implements BaseProtocol {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private CommonUtils commonUtils;
    @Resource
    private TbJcExpertService tbJcExpertService;
    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        Map<String, Object> map = new HashMap<>();
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
        String userId = paramMap.get("userId");
        try {
            List<ExpertInfoDto> list = tbJcExpertService.queryExpertInfo(userId);
            List newList = new ArrayList();
            for(int i = 0; i < list.size(); i++){
                ExpertInfoDto expertInfoDto = list.get(i);
                String lz = commonUtils.JsLzExpertInfoDto(expertInfoDto);
                expertInfoDto.setExpertHitNum(lz);
                newList.add(expertInfoDto);
            }
            resultMap.put("list",newList);
        } catch (Exception ex) {
            LOGGER.error("已关注专家列表" + ex.getMessage());
        }
        return resultMap;
    }
}
