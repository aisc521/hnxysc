package com.zhcdata.jc.protocol.impl;

import com.google.common.base.Strings;
import com.zhcdata.db.model.TbJcRecordFocus;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.TbJcRecordFocusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 关注专家
 * @Author cuishuai
 * @Date 2019/10/11 16:03
 */
@Service("20200305")
public class AttentionExprtProtocol implements BaseProtocol {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Resource
    private TbJcRecordFocusService tbJcRecordFocusService;
    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        Map<String, Object> map = new HashMap<>();
        String userId = paramMap.get("userId");
        if (Strings.isNullOrEmpty(userId)) {
            LOGGER.info("[" + ProtocolCodeMsg.USER_ID_NOT_EXIST.getMsg() + "]:userId---" + userId);
            map.put("resCode", ProtocolCodeMsg.TIME_NULL.getCode());
            map.put("message", ProtocolCodeMsg.TIME_NULL.getMsg());
            return map;
        }
        String expertId = paramMap.get("expertId");
        if (Strings.isNullOrEmpty(expertId)) {
            LOGGER.info("[" + ProtocolCodeMsg.EXPERT_ID.getMsg() + "]:expertId---" + expertId);
            map.put("resCode", ProtocolCodeMsg.TIME_NULL.getCode());
            map.put("message", ProtocolCodeMsg.TIME_NULL.getMsg());
            return map;
        }
        String status = paramMap.get("status");
        if (Strings.isNullOrEmpty(status)) {
            LOGGER.info("[" + ProtocolCodeMsg.STATUS_IS_NOT_EXIT.getMsg() + "]:status---" + status);
            map.put("resCode", ProtocolCodeMsg.STATUS_IS_NOT_EXIT.getCode());
            map.put("message", ProtocolCodeMsg.STATUS_IS_NOT_EXIT.getMsg());
            return map;
        }
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {

        Map<String, Object> resultMap = new HashMap<>();
        TbJcRecordFocus tbJcRecordFocus = tbJcRecordFocusService.queryRecordFocus(Long.valueOf(String.valueOf(paramMap.get("userId"))),Long.valueOf(String.valueOf(paramMap.get("expertId"))));
        if(tbJcRecordFocus == null){//新增数据
            String status = paramMap.get("status");
            if(status.equals(tbJcRecordFocus.getStatus())){//状态相同 提示 不能进行相同操作
                resultMap.put("resCode", ProtocolCodeMsg.STATUS_IS_NOT_EXIT.getCode());
                resultMap.put("message", ProtocolCodeMsg.STATUS_IS_NOT_EXIT.getMsg());
                return resultMap;
            }
            //新增数据
            if("0".equals(paramMap.get("status"))){
                resultMap.put("resCode", ProtocolCodeMsg.STATUS_IS_NOT_EXIT.getCode());
                resultMap.put("message", ProtocolCodeMsg.STATUS_IS_NOT_EXIT.getMsg());
                return resultMap;
            }
            TbJcRecordFocus insertdb = new TbJcRecordFocus();
            insertdb.setCreateTime(new Date());
            insertdb.setExpertId(Integer.valueOf(paramMap.get("expertId")));
            insertdb.setStatus(Integer.valueOf(paramMap.get("status")));
            insertdb.setUserId(Integer.valueOf(paramMap.get("userId")));
            tbJcRecordFocusService.insertRecord(insertdb);
        }else{//更新数据
            if(paramMap.get("status").equals(tbJcRecordFocus.getStatus())){//状态相同 提示不能进行重复操作
                resultMap.put("resCode", ProtocolCodeMsg.STATUS_IS_NOT_EXIT.getCode());
                resultMap.put("message", ProtocolCodeMsg.STATUS_IS_NOT_EXIT.getMsg());
                return resultMap;
            }
            tbJcRecordFocus.setStatus(Integer.valueOf(paramMap.get("status")));
            tbJcRecordFocusService.updateRecord(tbJcRecordFocus);
        }
        return null;
    }
}
