package com.zhcdata.jc.protocol.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.zhcdata.db.model.TbPgUCollect;
import com.zhcdata.jc.dto.MatchResult1;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.ScheduleService;
import com.zhcdata.jc.service.TbPgUCollectService;
import com.zhcdata.jc.tools.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.mapper.JsonMapper;
import org.springside.modules.utils.number.NumberUtil;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("20200245")
public class MatchListYqylUserIdProtocol  implements BaseProtocol {

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private TbPgUCollectService tbPgUCollectService;

    @Resource
    private ScheduleService scheduleService;

    @Resource
    private  MatchListProtocol matchListProtocol;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());


    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        Map<String, Object> map = new HashMap<>();
        String tableType = paramMap.get("tableType");   //状态 1赛果 2赛程 3即时
        if (Strings.isNullOrEmpty(tableType)) {
            LOGGER.info("[" + ProtocolCodeMsg.TABLETYPE_NO_NULL.getMsg() + "]:tableType---" + tableType);
            map.put("resCode", ProtocolCodeMsg.TABLETYPE_NO_NULL.getCode());
            map.put("message", ProtocolCodeMsg.TABLETYPE_NO_NULL.getMsg());
            return map;
        }

//        String matchTime = paramMap.get("matchTime");    //时间 日期
//        if (Strings.isNullOrEmpty(matchTime)) {
//            LOGGER.info("[" + ProtocolCodeMsg.TIME_NULL.getMsg() + "]:matchTime---" + matchTime);
//            map.put("resCode", ProtocolCodeMsg.TIME_NULL.getCode());
//            map.put("message", ProtocolCodeMsg.TIME_NULL.getMsg());
//            return map;
//        }

        String pageNo = paramMap.get("pageNo");             //页码
        if (Strings.isNullOrEmpty(pageNo) || !NumberUtil.isNumber(pageNo)) {
            LOGGER.info("[" + ProtocolCodeMsg.PAGENO_NULL.getMsg() + "]:pageNo---" + pageNo);
            map.put("resCode", ProtocolCodeMsg.PAGENO_NULL.getCode());
            map.put("message", ProtocolCodeMsg.PAGENO_NULL.getMsg());
            return map;
        }

        String userId = paramMap.get("userId");              //用户ID
        if (Strings.isNullOrEmpty(userId)) {
            LOGGER.info("[" + ProtocolCodeMsg.USER_ID_NOT_EXIST.getMsg() + "]:userId---" + userId);
            map.put("resCode", ProtocolCodeMsg.USER_ID_NOT_EXIST.getCode());
            map.put("message", ProtocolCodeMsg.USER_ID_NOT_EXIST.getMsg());
            return map;
        }

        String type= paramMap.get("type");                  //1竞彩 2北单 3足彩 4全部
        if (Strings.isNullOrEmpty(type)) {
            LOGGER.info("[" + ProtocolCodeMsg.TYPE_NULL.getMsg() + "]:type---" + type);
            map.put("resCode", ProtocolCodeMsg.TYPE_NULL.getCode());
            map.put("message", ProtocolCodeMsg.TYPE_NULL.getMsg());
            return map;
        }
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        Map<String, Object> map = new HashMap<>();
        String tableType = paramMap.get("tableType");       //类型 赛果11 赛程22 即时33
        String time = paramMap.get("matchTime");            //时间
        String pageNo = paramMap.get("pageNo");             //页码
        String userId = paramMap.get("userId");             //用户ID
        String panKouType = paramMap.get("panKouType");     //盘口
        String matchType = paramMap.get("matchType");       //赛事类型 法甲 法乙
        String type= paramMap.get("type");                  //1竞彩 2北单 3足彩 4全部
        String issue = paramMap.get("issue");               //选择足彩，就要传期次

        List<MatchResult1> newList = new ArrayList<>();
        if (!Strings.isNullOrEmpty(panKouType) || !Strings.isNullOrEmpty(matchType) || !type.equals("5") || !Strings.isNullOrEmpty(issue)) {
            if (type.equals("all")) {
                type = "4";
            }else if(type.equals("6")){
                type="3";
            }
            String endDate="";
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            if(!Strings.isNullOrEmpty(time)) {
                time = time + " 10:59:59";
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(df.parse(time));
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                endDate = df.format(calendar.getTime()) + " 10:59:59";
            }

            String state="";
            if(tableType.equals("11")){
                state="3"; // 赛果
            }else if(tableType.equals("22")){
                state="0"; //赛程
            }else if(tableType.equals("33")){
                state="1"; //即时
            }

            PageHelper.startPage(Integer.parseInt(pageNo), 20);
            newList = scheduleService.queryMacthListForJob(time, endDate , type, "", state, issue, matchListProtocol.getPanKou(panKouType), matchListProtocol.getMatchType(matchType)); //全部

            PageInfo<MatchResult1> infos = new PageInfo<>(newList);

            List<MatchResult1> list=new ArrayList<>();
            for(int j=0;j< infos.getList().size();j++){
                MatchResult1 r1=infos.getList().get(j);
                //处理盘口
                r1.setMatchPankou(matchListProtocol.getPanKou1(r1.getMatchPankou()));
                if(r1.getMatchState().equals("1")){
                    r1.setStatusDescFK("1");
                    if(r1.getMatchTime2()!=null&&!r1.getMatchTime2().contains("0000-00-00 00:00:00")) {
                        Timestamp ts = Timestamp.valueOf(r1.getMatchTime2());
                        String len = matchListProtocol.getMinute(df1.format(ts), df1.format(new Date()));
                        r1.setMatchState(len+"'");
                    }else {
                        r1.setMatchState("'完'");
                    }
                }else if(r1.getMatchState().equals("3")){
                    r1.setStatusDescFK("3");
                    if(r1.getMatchTime2()!=null&&!r1.getMatchTime2().contains("0000-00-00 00:00:00")) {
                        Timestamp ts = Timestamp.valueOf(r1.getMatchTime2());
                        String len = matchListProtocol.getMinute(df1.format(ts), df1.format(new Date()));
                        r1.setMatchState((45 + Integer.valueOf(len)) > 90 ? "90+'" : String.valueOf(45 + Integer.valueOf(len))+"'");
                    }else {
                        r1.setMatchState("'完'");
                    }
                }else if(r1.getMatchState().equals("中")){
                    r1.setStatusDescFK("2");
                }else if(r1.getMatchState().equals("(完)")){
                    r1.setStatusDescFK("-1");
                }else if(r1.getMatchState().equals("未")) {
                    r1.setStatusDescFK("0");
                }else if(r1.getMatchState().equals("完")) {
                    r1.setStatusDescFK("-1");
                }else if(r1.getMatchState().equals("推迟")){
                    r1.setStatusDescFK("0");
                }else if(r1.getMatchState().equals("腰斩")){
                    r1.setStatusDescFK("0");
                }else if(r1.getMatchState().equals("取消")){
                    r1.setStatusDescFK("0");
                }
                list.add(r1);
            }

            if (StringUtils.isNotBlank(userId)) {
                Integer followNum = tbPgUCollectService.queryCount(Long.valueOf(userId));
                map.put("followNum", followNum);//已关数量
            }
            map.put("pageNo", infos.getPageNum());
            map.put("pageTotal", infos.getPages());
            map.put("totalNum", infos.getTotal());
            map.put("list", list);
        }else {

            if (StringUtils.isBlank(time)) {
                map.put("list", "");
            } else {
                String re = (String) redisUtils.hget("SOCCER:HSET:AGAINSTLIST" + time + tableType, pageNo);
                if (!Strings.isNullOrEmpty(re)) {
                    JavaType javaType = JsonMapper.defaultMapper().buildMapType(Map.class, String.class, Object.class);
                    map = JsonMapper.defaultMapper().fromJson(re, javaType);
                    String s = JsonMapper.defaultMapper().toJson(map.get("list"));
                    JsonMapper jsonMapper = JsonMapper.defaultMapper();
                    JavaType javaType1 = jsonMapper.buildCollectionType(List.class, MatchResult1.class);
                    newList = jsonMapper.fromJson(s, javaType1);
                    map.put("list", newList);
                } else {
                    map.put("pageTotal", "0");
                    map.put("pageNo", "0");
                    map.put("busiCode", "20010201");
                    map.put("resCode", "000000");
                    map.put("message", "成功");
                    map.put("list", new ArrayList<>());
                }

                List<MatchResult1> result = new ArrayList<>();
                //根据userId  和比赛id查询此产比赛此用户是否关注
                if (StringUtils.isNotBlank(userId)) {
                    for (int i = 0; i < newList.size(); i++) {
                        MatchResult1 matchResult1 = newList.get(i);
                        String matchId = matchResult1.getMatchId();

                        TbPgUCollect tbPgUCollect = tbPgUCollectService.queryUserCollectByUserIdAndMacthId(Long.valueOf(userId), Long.valueOf(matchId));
                        if (tbPgUCollect != null) {
                            matchResult1.setIscollect("1");
                        }
                        result.add(matchResult1);
                    }
                    Integer followNum = tbPgUCollectService.queryCount(Long.valueOf(userId));
                    map.put("followNum", followNum);//已关数量
                    map.put("list", result);
                } else {
                    map.put("followNum", "0");//已关数量
                    map.put("list", newList);
                }
            }
        }

        return map;
    }
}