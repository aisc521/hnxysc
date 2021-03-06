package com.zhcdata.jc.protocol.impl;

import com.google.common.base.Strings;
import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.jc.dto.ExpertInfo;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.TbJcExpertService;
import com.zhcdata.jc.service.TbPlanService;
import com.zhcdata.jc.tools.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
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
    private CommonUtils commonUtils;
    @Resource
    private TbJcExpertService tbJcExpertService;
    @Resource
    private TbPlanService tbPlanService;
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
            //查询从专家 是否是发单超过七天
            TbJcPlan plan = tbPlanService.queryOnePlan(expertId);
            if (info != null) {
                resultMap.put("id", info.getId());
                resultMap.put("nickName", info.getNickName());
                resultMap.put("img", info.getImg());
                resultMap.put("lable", info.getLable());
                resultMap.put("introduction", info.getIntroduction());
                resultMap.put("fans", info.getFans());
                resultMap.put("trend", ternd(info.getTrend()));
                //判断连中 连红
                String lz = commonUtils.JsLz(info);
                resultMap.put("lz", lz);
                resultMap.put("nowLz", info.getLzNow());

                if(StringUtils.isNotBlank(info.getzSevenDays())){
                    resultMap.put("zSevenDays", new BigDecimal(info.getzSevenDays()).intValue());
                }else{
                    resultMap.put("zSevenDays", "");
                }
                if(StringUtils.isNotBlank(info.getReturnSevenDays())){
                    resultMap.put("returnSevenDays", new BigDecimal(info.getReturnSevenDays()).intValue());
                }else{
                    resultMap.put("returnSevenDays", "");
                }
                resultMap.put("status", info.getStatus());
                resultMap.put("grade", info.getGrade());
                if(plan != null){
                    Date create = plan.getCreateTime();
                    Date now = new Date();
                    Integer dayNum = commonUtils.differentDays(create,now);
                    if(dayNum <= 7){
                        resultMap.put("smaller", "1");//小于七天
                    }else{
                        resultMap.put("smaller", "0");//大于等于七天
                    }
                }else {
                    resultMap.put("smaller", "1");//小于七天
                }
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
                resultMap.put("grade", "");
                resultMap.put("message", "专家id不存在");
                resultMap.put("resCode", "");
                resultMap.put("busiCode", "");
                resultMap.put("smaller","");
            }
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("查询专家详情异常" ,e);
        }
        return resultMap;
    }

    public String ternd(String trend){
        if(trend.length() > 10){
            trend = trend.substring(trend.length() - 10,trend.length());
        }
        StringBuffer s = new StringBuffer(trend);
        for(int index = 0; index < s.length();index++){
            if(index%2==0){
                if(index + 1 != s.length()){
                    if(index < 19){
                        s.insert(index+1,",");
                    }

                }
            }
        }
        return s.toString();
    }

}
