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
                //判断连中 连红
                String lz = JsLz(info);
                resultMap.put("lz", lz);
                resultMap.put("zSevenDays", info.getzSevenDays());
                resultMap.put("returnSevenDays", info.getReturnSevenDays());
                resultMap.put("status", info.getStatus());
                resultMap.put("grade", info.getGrade());
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
            }
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("查询专家详情异常" + e.toString());
        }
        return resultMap;
    }

    public String JsLz(ExpertInfo info){
        String lz = info.getLzBig();
        Integer lh = Integer.valueOf(info.getLzNow());
        if(lh >= 4){
            lz = info.getLzNow();
            return lz;
        }
        if(Integer.valueOf(info.getFiveZ()) == 4){//五中四
            lz = "5中" + info.getFiveZ();
            return lz;
        }
        if(Integer.valueOf(info.getSixZ()) == 5){//6中5
            lz = "6中" + info.getSixZ();
            return lz;
        }
        if(Integer.valueOf(info.getSevenZ()) == 6){//7中6
            lz = "7中" + info.getSevenZ();
            return lz;
        }
        if(Integer.valueOf(info.getEightZ()) == 7){//8中7
            lz = "8中" + info.getEightZ();
            return lz;
        }
        if(Integer.valueOf(info.getNineZ()) == 8){//9中8
            lz = "9中" + info.getNineZ();
            return lz;
        }
        if(Integer.valueOf(info.getTenZ()) == 9){//10中9
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(Integer.valueOf(info.getSevenZ()) == 5){//7中5
            lz = "7中" + info.getSevenZ();
            return lz;
        }
        if(Integer.valueOf(info.getEightZ()) == 6){//8中6
            lz = "8中" + info.getEightZ();
            return lz;
        }
        if(Integer.valueOf(info.getNineZ()) == 7){//9中7
            lz = "9中" + info.getNineZ();
            return lz;
        }
        if(Integer.valueOf(info.getTenZ()) == 8){//10中8
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(Integer.valueOf(info.getTenZ()) == 7){//10中7
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(Integer.valueOf(info.getTenZ()) == 6){//10中6
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(Integer.valueOf(info.getTenZ()) == 5){//10中5
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(lh == 2){
            lz = info.getLzNow();
            return lz;
        }
        if(lh == 3){
            lz = info.getLzNow();
            return lz;
        }
        if(Integer.valueOf(info.getThreeZ()) == 2){//3中2
            lz = "3中" + info.getThreeZ();
            return lz;
        }
        if(Integer.valueOf(info.getFourZ()) == 3){//4中3
            lz = "3中" + info.getFourZ();
            return lz;
        }
        return lz;
    }
}
