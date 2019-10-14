package com.zhcdata.jc.protocol.impl;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.dto.PurchasedPlanDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.TbJcPurchaseDetailedService;
import com.zhcdata.jc.tools.Const;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 已购方案
 * @Author cuishuai
 * @Date 2019/9/20 16:31
 */
@Service("20200303")
public class PurchasedPlanProtocol implements BaseProtocol {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Resource
    private TbJcPurchaseDetailedService tbJcPurchaseDetailedService;

    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        Map<String, Object> map = new HashMap<>();
        String userId = paramMap.get("userId");
        if (Strings.isNullOrEmpty(userId)) {
            LOGGER.info(ProtocolCodeMsg.USER_ID_NOT_EXIST.getMsg());
            map.put("resCode", ProtocolCodeMsg.USER_ID_NOT_EXIST.getCode());
            map.put("message", ProtocolCodeMsg.USER_ID_NOT_EXIST.getMsg());
            return map;
        }

        String pageNo = paramMap.get(Const.PAGE_NO);
        if (Strings.isNullOrEmpty(pageNo)) {
            LOGGER.info(ProtocolCodeMsg.PAGE_NO_NOT_ILLEGAL.getMsg());
            map.put("resCode", ProtocolCodeMsg.PAGE_NO_NOT_ILLEGAL.getCode());
            map.put("message", ProtocolCodeMsg.PAGE_NO_NOT_ILLEGAL.getMsg());
            return map;
        }
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        Map resultMap = new HashMap();
        String userId = paramMap.get("userId");
        int pageNo = Integer.parseInt(paramMap.get(Const.PAGE_NO));
        int pageAmount = Integer.parseInt(paramMap.get("pageAmount"));
        if(StringUtils.isBlank(String.valueOf(pageAmount))){
             pageAmount = 20;
        }
        PageInfo<PurchasedPlanDto> pageInfo = tbJcPurchaseDetailedService.queryPurchasedPlanDtoByUserId(pageNo,pageAmount,Long.parseLong(userId));
        resultMap.put("list",pageInfo.getList());
        resultMap.put("pageTotal", pageInfo.getPages());
        resultMap.put("totalNum", pageInfo.getTotal());
        resultMap.put("pageNo", pageInfo.getPageNum());
        return resultMap;
    }
}
