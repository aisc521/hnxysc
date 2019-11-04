package com.zhcdata.jc.protocol.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.zhcdata.db.model.TbJcExpert;
import com.zhcdata.jc.dto.ExpertHotResult;
import com.zhcdata.jc.dto.HorseRaceLampDto;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.TbJcExpertService;
import com.zhcdata.jc.tools.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description 跑马灯
 * @Author cuishuai
 * @Date 2019/10/12 14:49
 */
@Service("20200232")
public class HorseRaceLampProtocol implements BaseProtocol {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private TbJcExpertService tbJcExpertService;

    private static String warn = "大神带你飞，速速查看;火速围观，沾沾喜气;跟随红单一起来;收米很简单，连中嗨翻天;猛戳来收米，中奖很简单;大神带你飞，速速查看;火速围观，沾沾喜气;跟随红单一起来;收米很简单，连中嗨翻天;猛戳来收米，中奖很简单";

    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        List hotList = new ArrayList();
        try {
            String re = (String )redisUtils.hget("SOCCER:HSET:EXPERT", "hot");
            if(!Strings.isNullOrEmpty(re)) {
                List list = JSONObject.parseArray(re, Map.class);
                for(int i = 0 ; i < list.size(); i++){
                    if(i <= 15){
                        //查询专家标签
                        ExpertHotResult hot = (ExpertHotResult) list.get(i);
                        TbJcExpert tbJcExpert = tbJcExpertService.queryExpertDetailsById(hot.getId());
                        if(tbJcExpert == null){
                            resultMap.put("list", "");
                            resultMap.put("message", "查询结果无数据");
                            resultMap.put("resCode", "000000");
                            resultMap.put("busiCode", "");
                        }
                        HorseRaceLampDto horseRaceLampDto = new HorseRaceLampDto();
                        horseRaceLampDto.setId(String.valueOf(hot.getId()));
                        horseRaceLampDto.setNikeName(hot.getNickName());
                        horseRaceLampDto.setVictory(hot.getLz());
                        String warStr = warn.split(";")[Integer.valueOf((System.currentTimeMillis() + "").substring(11,12))];
                        horseRaceLampDto.setWarn(warStr);
                        horseRaceLampDto.setGrade(String.valueOf(tbJcExpert.getGrade()));
                        horseRaceLampDto.setLable(tbJcExpert.getLable());
                        hotList.add(horseRaceLampDto);
                    }
                }
                resultMap.put("list", hotList);
                resultMap.put("message", "成功");
                resultMap.put("resCode", "000000");
                resultMap.put("busiCode", "");
            }else {
                resultMap.put("list", "");
                resultMap.put("message", "查询结果无数据");
                resultMap.put("resCode", "000000");
                resultMap.put("busiCode", "");
            }
        } catch (Exception ex) {
            LOGGER.error("跑马灯数据查询异常：",ex);
        }
        return resultMap;
    }
}
