package com.zhcdata.jc.protocol.impl;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.zhcdata.jc.dto.MatchResult1;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.ScheduleService;
import com.zhcdata.jc.tools.CommonUtils;
import com.zhcdata.jc.tools.Const;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.number.NumberUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description < 关注比赛列表(10200228)>
 * @Author cuishuai
 * @Date 2019/9/19 17:51
 */
@Service("20200230")
public class AttentionListProtocol implements BaseProtocol {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Resource
    private CommonUtils commonUtils;

    @Resource
    private ScheduleService scheduleService;

    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        Map<String, Object> map = new HashMap<>();
       /* String type = paramMap.get("type");
        if (Strings.isNullOrEmpty(type)) {
            LOGGER.info("[" + ProtocolCodeMsg.TYPE_NULL.getMsg() + "]:type---" + type);
            map.put("resCode", ProtocolCodeMsg.TYPE_NULL.getCode());
            map.put("message", ProtocolCodeMsg.TYPE_NULL.getMsg());
            return map;
        }

        String matchTime = paramMap.get("matchTime");
        if (Strings.isNullOrEmpty(matchTime)) {
            LOGGER.info("[" + ProtocolCodeMsg.TIME_NULL.getMsg() + "]:matchTime---" + matchTime);
            map.put("resCode", ProtocolCodeMsg.TIME_NULL.getCode());
            map.put("message", ProtocolCodeMsg.TIME_NULL.getMsg());
            return map;
        }
*/
        String pageNo = paramMap.get("pageNo");
        if (Strings.isNullOrEmpty(pageNo) || !NumberUtil.isNumber(pageNo)) {
            LOGGER.info("[" + ProtocolCodeMsg.PAGE_NO_NOT_ILLEGAL.getMsg() + "]:pageNo---" + pageNo);
            map.put("resCode", ProtocolCodeMsg.PAGE_NO_NOT_ILLEGAL.getCode());
            map.put("message", ProtocolCodeMsg.PAGE_NO_NOT_ILLEGAL.getMsg());
            return map;
        }

        String pageAmount = paramMap.get("pageAmount");
        if (Strings.isNullOrEmpty(pageAmount) || !NumberUtil.isNumber(pageAmount)) {
            LOGGER.info("[" + ProtocolCodeMsg.PAGEAMOUNT_NULL.getMsg() + "]:pageAmount---" + pageAmount);
            map.put("resCode", ProtocolCodeMsg.PAGEAMOUNT_NULL.getCode());
            map.put("message", ProtocolCodeMsg.PAGEAMOUNT_NULL.getMsg());
            return map;
        }
        if(!commonUtils.validParamExistAndNumber(map, paramMap, Const.USER_ID, ProtocolCodeMsg.USER_ID_NOT_EXIST)){
            return map;
        }
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        Map resultMap = new HashMap();
        String userId = paramMap.get(Const.USER_ID);
        String pageNo = paramMap.get("pageNo");
        String pageAmount = paramMap.get("pageAmount");
        if(StringUtils.isBlank(pageAmount)){
            pageAmount = "20";
        }
        PageInfo<MatchResult1> list = scheduleService.queryAttentionList(userId, pageNo, pageAmount);
        resultMap.put("busiCode", "10200201");
        resultMap.put("resCode", "000000");
        resultMap.put("message", "");
        resultMap.put("pageNo", pageNo);
        resultMap.put("pageTotal", list.getTotal());
        resultMap.put("list", list.getList());
        return resultMap;
    }
}
