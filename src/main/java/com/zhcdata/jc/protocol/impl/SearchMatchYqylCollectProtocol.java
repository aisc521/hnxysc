package com.zhcdata.jc.protocol.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.base.Strings;
import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.dto.MatchResult1;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.tools.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.mapper.JsonMapper;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("20200243")
public class SearchMatchYqylCollectProtocol implements BaseProtocol {

    @Resource
    private RedisUtils redisUtils;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private ScheduleMapper scheduleMapper;

    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        Map<String, Object> map = new HashMap<>();
        String type = paramMap.get("type");             //赛事 让球
        if (Strings.isNullOrEmpty(type)) {
            LOGGER.info("[" + ProtocolCodeMsg.TYPE_NULL.getMsg() + "]:type---" + type);
            map.put("resCode", ProtocolCodeMsg.TYPE_NULL.getCode());
            map.put("message", ProtocolCodeMsg.TYPE_NULL.getMsg());
            return map;
        }

        String matchType = paramMap.get("matchType");   //竞彩、北单、足彩
        if (Strings.isNullOrEmpty(matchType)) {
            LOGGER.info("[" + ProtocolCodeMsg.MATCHTYPE_NO_NULL.getMsg() + "]:matchType---" + matchType);
            map.put("resCode", ProtocolCodeMsg.MATCHTYPE_NO_NULL.getCode());
            map.put("message", ProtocolCodeMsg.MATCHTYPE_NO_NULL.getMsg());
            return map;
        }

//        String matchTime = paramMap.get("matchTime");   //日期
//        if (Strings.isNullOrEmpty(matchTime)) {
//            LOGGER.info("[" + ProtocolCodeMsg.TIME_NULL.getMsg() + "]:matchTime---" + matchTime);
//            map.put("resCode", ProtocolCodeMsg.TIME_NULL.getCode());
//            map.put("message", ProtocolCodeMsg.TIME_NULL.getMsg());
//            return map;
//        }

        String tableType=paramMap.get("tableType");     //赛程、赛果、即时
        if (Strings.isNullOrEmpty(tableType)) {
            LOGGER.info("[" + ProtocolCodeMsg.TABLETYPE_NO_NULL.getMsg() + "]:tableType---" + tableType);
            map.put("resCode", ProtocolCodeMsg.TABLETYPE_NO_NULL.getCode());
            map.put("message", ProtocolCodeMsg.TABLETYPE_NO_NULL.getMsg());
            return map;
        }
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        Map<String, Object> map = new HashMap<>();
        String type = paramMap.get("type");             //赛事 让球
        String matchType = paramMap.get("matchType");   //竞彩 北单 足彩
        String matchTime = paramMap.get("matchTime");   //日期
        String tableType = paramMap.get("tableType");   //赛程、赛果、即时
        String issue=paramMap.get("issue");             //期次
        List<MatchResult1> list = new ArrayList<>();

        String state="";
        if(tableType.equals("11")){
            state="-1,-10,-12,-14,";
        }else if(tableType.equals("22")){
            state="0,";
        }else if(tableType.equals("33")){
            state="0,1,2,3,4,5,-1,-10,-12,-14,";
        }

        int pageNo = 0;
        int currentPageTotal = 0;
        if(!Strings.isNullOrEmpty(issue)) {
            String re = (String)redisUtils.hget("SOCCER:HSET:AGAINSTLIST" + issue + "6","1");
            JavaType javaType = JsonMapper.defaultMapper().buildMapType(Map.class, String.class, Object.class);
            map = JsonMapper.defaultMapper().fromJson(re, javaType);
            JsonMapper jsonMapper = JsonMapper.defaultMapper();
            JavaType javaType1 = jsonMapper.buildCollectionType(List.class, MatchResult1.class);
            String s = JsonMapper.defaultMapper().toJson(map.get("list"));
            list.addAll(jsonMapper.fromJson(s, javaType1));

            StringBuilder sb = new StringBuilder();
            Map<String, Integer> match = new HashMap<>();
            switch (Integer.parseInt(type)){
                case 1:
                    for (MatchResult1 matchResult1 : list) {
                        if(state.contains(matchResult1.getStatusDescFK())) {
                            Integer matchCount = match.get(matchResult1.getMatchName());
                            if (matchCount == null) matchCount = 1;//如果没有，这是第一场
                            else matchCount = matchCount + 1;//如果有，那就加一场
                            match.put(matchResult1.getMatchName(), matchCount);
                        }
                    }
                    break;
                case 2:
                    for (MatchResult1 matchResult1 : list) {
                        if(state.contains(matchResult1.getStatusDescFK())) {
                            if (!Strings.isNullOrEmpty(getPanKou(matchResult1.getMatchPankou()))) {
                                String pan = matchResult1.getMatchPankou();
                                pan = getPanKou(pan);
                                //pan=transformation(pan);
                                //pan=CorrespondingMap.get(pan);
                                Integer matchCount = match.get(pan);
                                if (matchCount == null) matchCount = 1;//如果没有，这是第一场
                                else matchCount = matchCount + 1;//如果有，那就加一场

                                match.put(getPanKou(matchResult1.getMatchPankou()), matchCount);
                            }
                        }
                    }
                    break;
            }
            for (Map.Entry<String, Integer> entry : match.entrySet()) {
                sb.append(entry.getKey()).append(",").append(entry.getValue()).append("|");
            }
            Map<String, Object> result = new HashMap<>();
            result.put("message", "success");
            if (sb.toString().length() > 0) sb.deleteCharAt(sb.length() - 1);
            result.put("info", sb);
            return result;
        }else {
            while (true) {
                pageNo++;
                String re = (String) redisUtils.hget("SOCCER:HSET:AGAINSTLIST" + matchTime + tableType, String.valueOf(pageNo));
                if (Strings.isNullOrEmpty(re)) break;
                JavaType javaType = JsonMapper.defaultMapper().buildMapType(Map.class, String.class, Object.class);
                map = JsonMapper.defaultMapper().fromJson(re, javaType);

                JsonMapper jsonMapper = JsonMapper.defaultMapper();
                JavaType javaType1 = jsonMapper.buildCollectionType(List.class, MatchResult1.class);
                String s = JsonMapper.defaultMapper().toJson(map.get("list"));

                if (currentPageTotal == 0) {
                    currentPageTotal = Integer.parseInt(map.get("pageTotal").toString());
                }

                if (pageNo > currentPageTotal) {
                    break;
                }
                list.addAll(jsonMapper.fromJson(s, javaType1));
            }


            String JCZQ = "";
            String BJDC = "";
            String SF14 = "";
            String drawno = "0";
            if (!matchType.equals("5")) {
                List<MatchResult1> mType = scheduleMapper.queryMatchType(matchTime + " 11:00:00");
                for (int k = 0; k < mType.size(); k++) {
                    if (mType.get(k).getMatchType().equals("JCZQ")) {
                        JCZQ += mType.get(k).getMatchId() + ",";
                    } else if (mType.get(k).getMatchType().equals("BJDC")) {
                        BJDC += mType.get(k).getMatchId() + ",";
                    } else if (mType.get(k).getMatchType().equals("SF14")) {
                        SF14 += mType.get(k).getMatchId() + ",";
                        if (Long.parseLong(mType.get(k).getDrawno()) > Long.parseLong(drawno)) {
                            drawno = mType.get(k).getDrawno();
                        }
                    }
                }
            }


            String mt = "";
            if (matchType.equals("1")) {
                mt = JCZQ;
            } else if (matchType.equals("2")) {
                mt = BJDC;
            } else if (matchType.equals("3")) {
                mt = SF14;
            }

            StringBuilder sb = new StringBuilder();
            Map<String, Integer> match = new HashMap<>();
            if (type.equals("1")) {
                //赛事
                for (MatchResult1 matchResult1 : list) {
                    if (!"5".equals(matchType) && !mt.contains(matchResult1.getMatchId())) {
                        continue;
                    }
                    Integer matchCount = match.get(matchResult1.getMatchName());
                    if (matchCount == null) matchCount = 1;//如果没有，这是第一场
                    else matchCount = matchCount + 1;//如果有，那就加一场
                    match.put(matchResult1.getMatchName(), matchCount);
                }
            } else if (type.equals("2")) {
                //让球
                for (MatchResult1 matchResult1 : list) {
                    if (!"5".equals(matchType) && !mt.contains(matchResult1.getMatchId())) {
                        continue;
                    }
                    if (!Strings.isNullOrEmpty(getPanKou(matchResult1.getMatchPankou()))) {
                        String pan = matchResult1.getMatchPankou();
                        pan = getPanKou(pan);
                        Integer matchCount = match.get(pan);
                        if (matchCount == null) matchCount = 1;//如果没有，这是第一场
                        else matchCount = matchCount + 1;//如果有，那就加一场

                        match.put(getPanKou(matchResult1.getMatchPankou()), matchCount);
                    }
                }
            }


            for (Map.Entry<String, Integer> entry : match.entrySet()) {
                sb.append(entry.getKey()).append(",").append(entry.getValue()).append("|");
            }
            Map<String, Object> result = new HashMap<>();
            result.put("message", "success");
            result.put("issue", drawno);
            if (sb.toString().length() > 0) sb.deleteCharAt(sb.length() - 1);
            result.put("info", sb);
            return result;
        }
    }


    public String getPanKou(String value){
        if(value!=null) {
            if (value.equals("0.0")) {
                value = "0";
            } else if (value.equals("1.0")) {
                value = "1";
            } else if (value.equals("2.0")) {
                value = "2";
            } else if (value.equals("3.0")) {
                value = "3";
            } else if (value.equals("4.0")) {
                value = "4";
            } else if (value.equals("5.0")) {
                value = "5";
            } else if (value.equals("-1.0")) {
                value = "-1";
            } else if (value.equals("-2.0")) {
                value = "-2";
            } else if (value.equals("-3.0")) {
                value = "-3";
            } else if (value.equals("-4.0")) {
                value = "-4";
            } else if (value.equals("-5.0")) {
                value = "-5";
            }
        }
        return value;
    }
}
