package com.zhcdata.jc.protocol.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.base.Strings;
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
import org.springside.modules.utils.number.NumberUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 赛事筛选
 * @Author cuishuai
 * @Date 2019/9/20 15:12
 */
@Service("20200222")
public class SearchMatchCollectProtocol implements BaseProtocol {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchMatchCollectProtocol.class);

    private static final Map<String, String> CorrespondingMap = new HashMap<String, String>(){{
        put("-5", "受让五球");
        put("-4.75", "受让四球半/五球");
        put("-4.5", "受让四球半");
        put("-4.25", "受让四球/四球半");
        put("-4", "受让四球");
        put("-3.75", "受让三球半/四球");
        put("-3.5", "受让三球半");
        put("-3.25", "受让三球/三球半");
        put("-3", "受让三球");
        put("-2.75", "受让两球半/三球");
        put("-2.5", "受让两球半");
        put("-2.25", "受让两球/两球半");
        put("-2", "受让两球");
        put("-1.75", "受让球半/两球");
        put("-1.5", "受让一球半");
        put("-1.25", "受让一球/球半");
        put("-1", "受让一球");
        put("-1.0", "受让一球");
        put("-0.75", "受让半一");
        put("-0.5", "受让半球");
        put("-0.25", "受让平半");
        put("5", "五球");
        put("4.75", "四球半/五球");
        put("4.5", "四球半");
        put("4.25", "四球/四球半");
        put("4", "四球");
        put("3.75", "三球半/四球");
        put("3.5", "三球半");
        put("3.25", "三球/三球半");
        put("3", "三球");
        put("2.75", "两球半/三球");
        put("2.5", "两球半");
        put("2.25", "两球/两球半");
        put("2", "两球");
        put("1.75", "球半/两球");
        put("1.5", "一球半");
        put("1.25", "一球/球半");
        put("1", "一球");
        put("1.0", "一球");
        put("0.75", "半一");
        put("0.5", "半球");
        put("0.25", "平半");
        put("-0","平手");
        put("0","平手");
    }};

    @Resource
    private RedisUtils redisUtils;

    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        Map<String, Object> map = new HashMap<>();
        //校验赛事matchId
        String type = paramMap.get("type");//1赛事 2盘口
        if (Strings.isNullOrEmpty(type) || !NumberUtil.isNumber(type)) {
            LOGGER.info("[" + ProtocolCodeMsg.SEARCH_MATCH_TYPE.getMsg() + "]:type---" + type);
            map.put("resCode", ProtocolCodeMsg.SEARCH_MATCH_TYPE.getCode());
            map.put("message", ProtocolCodeMsg.SEARCH_MATCH_TYPE.getMsg());
            return map;
        }
        String matchType = paramMap.get("matchType");//标签 1精彩 2北单 3足彩 5全部
        if (Strings.isNullOrEmpty(matchType) || !NumberUtil.isNumber(matchType)) {
            LOGGER.info("[" + ProtocolCodeMsg.SEARCH_MATCH_MATCH_TYPE.getMsg() + "]:matchType---" + matchType);
            map.put("resCode", ProtocolCodeMsg.SEARCH_MATCH_MATCH_TYPE.getCode());
            map.put("message", ProtocolCodeMsg.SEARCH_MATCH_MATCH_TYPE.getMsg());
            return map;
        }
        String matchTime = paramMap.get("matchTime");
        if (Strings.isNullOrEmpty(matchTime)) {
            LOGGER.info("[" + ProtocolCodeMsg.SEARCH_MATCH_MATCH_TIME.getMsg() + "]:matchTime---" + matchTime);
            map.put("resCode", ProtocolCodeMsg.SEARCH_MATCH_MATCH_TIME.getCode());
            map.put("message", ProtocolCodeMsg.SEARCH_MATCH_MATCH_TIME.getMsg());
            return map;
        }
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        Map<String, Object> map = new HashMap<>();
        String type = paramMap.get("type");
        String matchType = paramMap.get("matchType");
        String matchTime = paramMap.get("matchTime");
        List<MatchResult1> list = new ArrayList<>();//此类型的所有比赛
        if (matchType.equalsIgnoreCase("3")) {//足彩（按期）
            String re = (String)redisUtils.hget("SOCCER:HSET:AGAINSTLIST" + matchTime + matchType,"1");
            JavaType javaType = JsonMapper.defaultMapper().buildMapType(Map.class, String.class, Object.class);
            map = JsonMapper.defaultMapper().fromJson(re, javaType);
            JsonMapper jsonMapper = JsonMapper.defaultMapper();
            JavaType javaType1 = jsonMapper.buildCollectionType(List.class, MatchResult1.class);
            String s = JsonMapper.defaultMapper().toJson(map.get("list"));
            list.addAll(jsonMapper.fromJson(s, javaType1));
        } else {//全部 精彩 + 二期北单
            int pageNo = 0;
            while (true) {
                pageNo++;
                String re = (String) redisUtils.hget("SOCCER:HSET:AGAINSTLIST" + matchTime + matchType, String.valueOf(pageNo));
                if (Strings.isNullOrEmpty(re)) break;
                JavaType javaType = JsonMapper.defaultMapper().buildMapType(Map.class, String.class, Object.class);
                map = JsonMapper.defaultMapper().fromJson(re, javaType);

                JsonMapper jsonMapper = JsonMapper.defaultMapper();
                JavaType javaType1 = jsonMapper.buildCollectionType(List.class, MatchResult1.class);
                String s = JsonMapper.defaultMapper().toJson(map.get("list"));
                list.addAll(jsonMapper.fromJson(s, javaType1));
            }
        }

        StringBuilder sb = new StringBuilder();
        Map<String, Integer> match = new HashMap<>();
        switch (Integer.parseInt(type)){
            case 1:
                for (MatchResult1 matchResult1 : list) {
                    Integer matchCount = match.get(matchResult1.getMatchName());
                    if (matchCount == null) matchCount = 1;//如果没有，这是第一场
                    else matchCount = matchCount + 1;//如果有，那就加一场
                    match.put(matchResult1.getMatchName(), matchCount);
                }
                break;
            case 2:
                for (MatchResult1 matchResult1 : list) {
                    if (!Strings.isNullOrEmpty(matchResult1.getMatchPankou())){
                        Integer matchCount = match.get(CorrespondingMap.get(transformation(matchResult1.getMatchPankou())));
                        if (matchCount == null) matchCount = 1;//如果没有，这是第一场
                        else matchCount = matchCount + 1;//如果有，那就加一场
                        match.put(CorrespondingMap.get(transformation(matchResult1.getMatchPankou())), matchCount);
                        //System.out.println(transformation(matchResult1.getMatchPankou())+"   : " +
                        //        "  "+CorrespondingMap.get(transformation(matchResult1.getMatchPankou())));
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
    }


    private String transformation(String matchPankou) {
        if (matchPankou.contains("/")){
            String str = "";
            boolean flag = true;
            if (matchPankou.startsWith("-")){
                flag = false;
                matchPankou = matchPankou.replace("-", "");
            }
            String[] split = matchPankou.split("/");
            if (split.length==2)
                str = String.valueOf(Double.parseDouble(split[0])+Double.parseDouble(split[1])/2);
            if (!flag)str = "-"+str;
            return str;
        }else return matchPankou;
    }
}
