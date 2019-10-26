package com.zhcdata.jc.protocol.impl;

import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Strings;
import com.zhcdata.db.model.JcMatchBjdcPl;
import com.zhcdata.db.model.JcMatchLottery;
import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.db.model.JcSchedulesp;
import com.zhcdata.jc.dto.MatchInfoForBdDto;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.enums.RedisCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.*;
import com.zhcdata.jc.tools.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.mapper.JsonMapper;
import org.springside.modules.utils.number.NumberUtil;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/20 15:19
 */
@Service("20200223")
public class MatchBettingCollectProtocol implements BaseProtocol {
    //log
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchBettingCollectProtocol.class);

    @Resource
    private JcMatchBjdcPlService jcMatchBjdcPlService;
    @Resource
    private RedisUtils redisUtils;

    @Resource
    private JcMatchLotteryService jcMatchLotteryService;
    @Resource
    private JcSchedulespService jcSchedulespService;
    @Resource
    private ScheduleService scheduleService;

    @Resource
    private JcScheduleService jcScheduleService;
    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {

        //校验赛事matchId
        String matchId = paramMap.get("matchId");//1赛事 2盘口
        if (Strings.isNullOrEmpty(matchId) || !NumberUtil.isNumber(matchId)) {
            Map<String, Object> map = new HashMap<>();
            LOGGER.info("[" + ProtocolCodeMsg.SEARCH_MATCH_TYPE.getMsg() + "]:type---" + matchId);
            map.put("resCode", ProtocolCodeMsg.SEARCH_MATCH_TYPE.getCode());
            map.put("message", ProtocolCodeMsg.SEARCH_MATCH_TYPE.getMsg());
            return map;
        }
        String type = paramMap.get("type");//1赛事 2盘口
        if (Strings.isNullOrEmpty(type) || !NumberUtil.isNumber(type)) {
            Map<String, Object> map = new HashMap<>();
            LOGGER.info("[" + ProtocolCodeMsg.TYPE_NOT_ASSIGNED.getMsg() + "]:type---" + type);
            map.put("resCode", ProtocolCodeMsg.TYPE_NOT_ASSIGNED.getCode());
            map.put("message", ProtocolCodeMsg.TYPE_NOT_ASSIGNED.getMsg());
            return map;
        }

        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        String matchId = paramMap.get("matchId");
        String timeId = paramMap.get("timeId");
        String type = paramMap.get("type");
        if (StringUtils.isEmpty(timeId))
            timeId = "0";
        if("1".equals(type)){//竞彩
            Map<String, Object> result = JSONArray.parseObject((String) redisUtils.hget("SOCCER:BETTING:FIVEMETHOD:" + matchId, "v"), Map.class);
            if (timeId.equals(result.get("timeId").toString())) {
                Map<String, Object> resultMap = new HashMap<>();
                //基础信息
                result.put("message", "success");
                result.put("timeId", timeId);
                return resultMap;
            } else{
                return result;
            }
        }else{//北单
            Map<String, Object> result = JSONArray.parseObject((String) redisUtils.hget("SOCCER:BETTING:FIVEMETHODBJDC:" + matchId, "v"), Map.class);
            if (timeId.equals(result.get("timeId").toString())) {
                Map<String, Object> resultMap = new HashMap<>();
                //基础信息
                result.put("message", "success");
                result.put("timeId", timeId);
                return resultMap;
            } else{
                return result;
            }
        }


    }

    public void autoWork() {
        List<Integer> matchIds = scheduleService.selectMatchIdExceedNow();
        Integer count = 0;
        long sat = System.currentTimeMillis();
        for (int i = 0; i < matchIds.size(); i++) {
            try {
                List oneMatch = getOneMatch(matchIds.get(i));

                if(oneMatch.size() > 0){

                    for(int j = 0; j < oneMatch.size(); j++){
                        Map<String, Object> re = (Map<String, Object>) oneMatch.get(j);
                        if("JC".equals(re.get("type"))){
                            redisUtils.hset("SOCCER:BETTING:FIVEMETHOD:" + matchIds.get(i), "v", JsonMapper.defaultMapper().toJson(re));
                            redisUtils.expire("SOCCER:BETTING:FIVEMETHOD:" + matchIds.get(i), RedisCodeMsg.SAME_ODDS.getSeconds());
                        }else if("BJDC".equals(re.get("type"))){
                            redisUtils.hset("SOCCER:BETTING:FIVEMETHODBJDC:" + matchIds.get(i), "v", JsonMapper.defaultMapper().toJson(re));
                            redisUtils.expire("SOCCER:BETTING:FIVEMETHODBJDC:" + matchIds.get(i), RedisCodeMsg.SAME_ODDS.getSeconds());
                        }

                    }

                }
                count++;
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("五种玩法赔率查询生成失败，比赛id：" + matchIds.get(i));
            }
        }
        LOGGER.info("五种玩法赔率查询生成完成，操作比赛：" + count + "场,耗时：" + (System.currentTimeMillis() - sat) + "毫秒");
    }



    public List getOneMatch(Integer matchId) {
        Map<String, Object> bdresult = new HashMap<>();
        Map<String, Object> jcresult = new HashMap<>();
        List resultList = new ArrayList();
        //判断是竞彩还是北单比赛
        List<JcMatchLottery> jcMatchLotteries = jcMatchLotteryService.queryJcMatchLotteryByMatchIdAndType(matchId);
        if(jcMatchLotteries.size() == 2){//既是北单又是竞彩
            bdresult = generateBjdcFive(matchId);
            jcresult = generateJcFive(matchId);
            resultList.add(bdresult);
            resultList.add(jcresult);
        }else if(jcMatchLotteries.size() == 1){//是北单或者竞彩
            if("BJDC".equals(jcMatchLotteries.get(0).getLottery())){//北单

                bdresult = generateBjdcFive(matchId);
                resultList.add(bdresult);
            }else{
                jcresult = generateJcFive(matchId);
                resultList.add(jcresult);
            }

        }
        return resultList;
    }


    public Map<String, Object> generateJcFive(Integer matchId){
        Map<String, Object> result = new HashMap<>();
        JcSchedulesp jcScheduleSp = jcSchedulespService.queryJcSchedulespById(matchId);
        JcSchedule jcSchedule = jcScheduleService.queryJcScheduleByBet007(matchId);

        if(jcScheduleSp != null && jcSchedule != null){
            //胜平负
            StringBuilder spfStr = new StringBuilder();
            spfStr.append("胜").append("|").append(jcScheduleSp.getSf3()).append(",");
            spfStr.append("平").append("|").append(jcScheduleSp.getSf1()).append(",");
            spfStr.append("负").append("|").append(jcScheduleSp.getSf0()).append(",");
            spfStr.append("让球").append("|").append("0");

            //让球胜平负
            StringBuilder rqSpfStr = new StringBuilder();
            rqSpfStr.append("让胜").append("|").append(jcScheduleSp.getWl3()).append(",");
            rqSpfStr.append("让平").append("|").append(jcScheduleSp.getWl1()).append(",");
            rqSpfStr.append("让负").append("|").append(jcScheduleSp.getWl0()).append(",");
            rqSpfStr.append("让球数").append("|").append(jcSchedule.getPolygoal());

            StringBuilder bfStr = new StringBuilder();
            bfStr.append("1:0").append("|").append(jcScheduleSp.getSw10()).append(",");
            bfStr.append("2:0").append("|").append(jcScheduleSp.getSw20()).append(",");
            bfStr.append("2:1").append("|").append(jcScheduleSp.getSw21()).append(",");
            bfStr.append("3:0").append("|").append(jcScheduleSp.getSw30()).append(",");
            bfStr.append("3:1").append("|").append(jcScheduleSp.getSw31()).append(",");
            bfStr.append("3:2").append("|").append(jcScheduleSp.getSw32()).append(",");
            bfStr.append("4:0").append("|").append(jcScheduleSp.getSw40()).append(",");
            bfStr.append("4:1").append("|").append(jcScheduleSp.getSw41()).append(",");
            bfStr.append("4:2").append("|").append(jcScheduleSp.getSw42()).append(",");
            bfStr.append("5:0").append("|").append(jcScheduleSp.getSw50()).append(",");
            bfStr.append("5:1").append("|").append(jcScheduleSp.getSw51()).append(",");
            bfStr.append("5:2").append("|").append(jcScheduleSp.getSw52()).append(",");
            bfStr.append("胜其他").append("|").append(jcScheduleSp.getSw5()).append(",");
            bfStr.append("0:0").append("|").append(jcScheduleSp.getSd00()).append(",");
            bfStr.append("1:1").append("|").append(jcScheduleSp.getSd11()).append(",");
            bfStr.append("2:2").append("|").append(jcScheduleSp.getSd22()).append(",");
            bfStr.append("3:3").append("|").append(jcScheduleSp.getSd33()).append(",");
            bfStr.append("平其他").append("|").append(jcScheduleSp.getSd4()).append(",");
            bfStr.append("0:1").append("|").append(jcScheduleSp.getSl01()).append(",");
            bfStr.append("0:2").append("|").append(jcScheduleSp.getSl02()).append(",");
            bfStr.append("1:2").append("|").append(jcScheduleSp.getSl12()).append(",");
            bfStr.append("0:3").append("|").append(jcScheduleSp.getSl03()).append(",");
            bfStr.append("1:3").append("|").append(jcScheduleSp.getSl13()).append(",");
            bfStr.append("2:3").append("|").append(jcScheduleSp.getSl23()).append(",");
            bfStr.append("0:4").append("|").append(jcScheduleSp.getSl04()).append(",");
            bfStr.append("1:4").append("|").append(jcScheduleSp.getSl14()).append(",");
            bfStr.append("2:4").append("|").append(jcScheduleSp.getSl24()).append(",");
            bfStr.append("0:5").append("|").append(jcScheduleSp.getSl05()).append(",");
            bfStr.append("1:5").append("|").append(jcScheduleSp.getSl15()).append(",");
            bfStr.append("2:5").append("|").append(jcScheduleSp.getSl25()).append(",");
            bfStr.append("负其他").append("|").append(jcScheduleSp.getSl5()).append(",");
            bfStr.append("让球数").append("|").append("0");


            //总进球
            StringBuilder zjqStr = new StringBuilder();
            zjqStr.append("0").append("|").append(jcScheduleSp.getT0()).append(",");
            zjqStr.append("1").append("|").append(jcScheduleSp.getT1()).append(",");
            zjqStr.append("2").append("|").append(jcScheduleSp.getT2()).append(",");
            zjqStr.append("3").append("|").append(jcScheduleSp.getT3()).append(",");
            zjqStr.append("4").append("|").append(jcScheduleSp.getT4()).append(",");
            zjqStr.append("5").append("|").append(jcScheduleSp.getT5()).append(",");
            zjqStr.append("6").append("|").append(jcScheduleSp.getT6()).append(",");
            zjqStr.append("7").append("|").append(jcScheduleSp.getT7());


            //半全场
            StringBuilder bqcSpfStr = new StringBuilder();
            bqcSpfStr.append("胜胜").append("|").append(jcScheduleSp.getHt33()).append(","); //胜胜
            bqcSpfStr.append("胜平").append("|").append(jcScheduleSp.getHt31()).append(",");//胜平
            bqcSpfStr.append("胜负").append("|").append(jcScheduleSp.getHt30()).append(",");//胜负
            bqcSpfStr.append("平胜").append("|").append(jcScheduleSp.getHt13()).append(",");//平胜
            bqcSpfStr.append("平平").append("|").append(jcScheduleSp.getHt11()).append(",");//平平
            bqcSpfStr.append("平负").append("|").append(jcScheduleSp.getHt10()).append(",");//平负
            bqcSpfStr.append("负胜").append("|").append(jcScheduleSp.getHt03()).append(",");//负胜
            bqcSpfStr.append("负平").append("|").append(jcScheduleSp.getHt01()).append(",");//负平
            bqcSpfStr.append("负负").append("|").append(jcScheduleSp.getHt00());//负负
            //基础信息
            result.put("message", "success");
            result.put("gameid", jcSchedule.getScheduleid());
            result.put("game_name", jcSchedule.getSclass());
            result.put("num", jcSchedule.getMatchid());
            result.put("home_team", jcSchedule.getHometeam());
            result.put("visit_team", jcSchedule.getGuestteam());
            result.put("match_date", new SimpleDateFormat("yyyy-MM-dd HH:ss:mm").format(jcSchedule.getMatchtime()));

            result.put("spf", spfStr);//胜平负
            result.put("rqspf", rqSpfStr);//让球胜平负
            result.put("bf", bfStr);//比分
            result.put("zjq", zjqStr);//比分
            result.put("bqcspf", bqcSpfStr);//比分
            result.put("timeId", String.valueOf(System.currentTimeMillis()));
            result.put("type","JC");
        }else{
            //基础信息
            result.put("message", "faile");
            result.put("gameid", "");
            result.put("game_name", "");
            result.put("num", "");
            result.put("home_team", "");
            result.put("visit_team", "");
            result.put("match_date", "");

            result.put("spf", "");//胜平负
            result.put("rqspf", "");//让球胜平负
            result.put("bf", "");//比分
            result.put("zjq", "");//比分
            result.put("bqcspf", "");//比分
            result.put("timeId", String.valueOf(System.currentTimeMillis()));
            result.put("type","JC");
        }
        return result;
    }


    public Map<String, Object> generateBjdcFive(Integer matchId){
        //根据比赛id 查询北单的sp
        Map<String, Object> result = new HashMap<>();
        StringBuilder rspfStr = new StringBuilder();
        StringBuilder bfStr = new StringBuilder();
        StringBuilder zjqStr = new StringBuilder();
        StringBuilder bqcSpfStr = new StringBuilder();
        StringBuilder sxds = new StringBuilder();
        List<JcMatchBjdcPl> list = jcMatchBjdcPlService.queryBjdcByMatchId(matchId);

            for(int i = 0; i < list.size(); i++){
                JcMatchBjdcPl jcMatchBjdcPl = list.get(i);
                String rat = jcMatchBjdcPl.getRateResult();
                String[] ratArr = rat.split("\\|");
                if("15".equals(jcMatchBjdcPl.getLotteryPlay())){//胜平负
                    rspfStr.append("胜").append("|").append(ratArr[0].split(":")[2]).append(",");
                    rspfStr.append("平").append("|").append(ratArr[1].split(":")[2]).append(",");
                    rspfStr.append("负").append("|").append(ratArr[2].split(":")[2].replaceAll("胜其他","")).append(",");
                    String str1 = ratArr[2].split(":")[4].substring(0,ratArr[2].split(":")[4].length() -1);

                    rspfStr.append("胜其他").append("|").append(str1).append(",");
                    rspfStr.append("平其他").append("|").append(ratArr[3].split(":")[2]).append(",");
                    rspfStr.append("负其他").append("|").append(ratArr[4].split(":")[2]).append(",");
                    rspfStr.append("让球").append("|").append("0");
                    result.put("rqspf", rspfStr);//让球胜平负

                }
                if("12".equals(jcMatchBjdcPl.getLotteryPlay())){//比分

                    bfStr.append("0:0").append("|").append(ratArr[0].split(":")[2]).append(",");
                    bfStr.append("0:1").append("|").append(ratArr[1].split(":")[2]).append(",");
                    bfStr.append("0:2").append("|").append(ratArr[2].split(":")[2]).append(",");
                    bfStr.append("0:3").append("|").append(ratArr[3].split(":")[2]).append(",");
                    bfStr.append("1:1").append("|").append(ratArr[4].split(":")[2]).append(",");
                    bfStr.append("1:2").append("|").append(ratArr[5].split(":")[2]).append(",");
                    bfStr.append("1:3").append("|").append(ratArr[6].split(":")[2]).append(",");
                    bfStr.append("2:0").append("|").append(ratArr[7].split(":")[2]).append(",");
                    bfStr.append("2:1").append("|").append(ratArr[8].split(":")[2]).append(",");
                    bfStr.append("2:2").append("|").append(ratArr[9].split(":")[2]).append(",");
                    bfStr.append("2:3").append("|").append(ratArr[10].split(":")[2]).append(",");
                    bfStr.append("3:0").append("|").append(ratArr[11].split(":")[2]).append(",");
                    bfStr.append("3:1").append("|").append(ratArr[12].split(":")[2]).append(",");
                    bfStr.append("3:2").append("|").append(ratArr[13].split(":")[2]).append(",");
                    String str1=ratArr[14].substring(0, ratArr[14].indexOf("："));
                    String str2=ratArr[14].substring(str1.length()+1, ratArr[14].length());
                    bfStr.append("3:3").append("|").append(str2.split(":")[1]).append(",");
                    bfStr.append("4:0").append("|").append(ratArr[15].split(":")[2]).append(",");
                    bfStr.append("4:1").append("|").append(ratArr[16].split(":")[2]).append(",");
                    bfStr.append("4:2").append("|").append(ratArr[17].split(":")[2]).append(",");
                    bfStr.append("0:4").append("|").append(ratArr[18].split(":")[2]).append(",");
                    bfStr.append("1:4").append("|").append(ratArr[19].split(":")[2]).append(",");
                    bfStr.append("2:4").append("|").append(ratArr[20].split(":")[2]).append(",");//比分
                    result.put("bf", bfStr);//比分
                }
                if("13".equals(jcMatchBjdcPl.getLotteryPlay())){//总进球
                    zjqStr.append("0").append("|").append(ratArr[0].split(":")[2]).append(",");
                    zjqStr.append("1").append("|").append(ratArr[1].split(":")[2]).append(",");
                    zjqStr.append("2").append("|").append(ratArr[2].split(":")[2]).append(",");
                    zjqStr.append("3").append("|").append(ratArr[3].split(":")[2]).append(",");
                    zjqStr.append("4").append("|").append(ratArr[4].split(":")[2]).append(",");
                    zjqStr.append("5").append("|").append(ratArr[5].split(":")[2]).append(",");
                    zjqStr.append("6").append("|").append(ratArr[6].split(":")[2]).append(",");
                    zjqStr.append("7").append("|").append(ratArr[7].split(":")[2]);
                    result.put("zjq", zjqStr);//比分

                }
                if("14".equals(jcMatchBjdcPl.getLotteryPlay())){//半全场
                    bqcSpfStr.append("胜胜").append("|").append(ratArr[0].split(":")[2]).append(","); //胜胜
                    bqcSpfStr.append("胜平").append("|").append(ratArr[1].split(":")[2]).append(",");//胜平
                    bqcSpfStr.append("胜负").append("|").append(ratArr[2].split(":")[2]).append(",");//胜负
                    bqcSpfStr.append("平胜").append("|").append(ratArr[3].split(":")[2]).append(",");//平胜
                    bqcSpfStr.append("平平").append("|").append(ratArr[4].split(":")[2]).append(",");//平平
                    bqcSpfStr.append("平负").append("|").append(ratArr[5].split(":")[2]).append(",");//平负
                    bqcSpfStr.append("负胜").append("|").append(ratArr[6].split(":")[2]).append(",");//负胜
                    bqcSpfStr.append("负平").append("|").append(ratArr[7].split(":")[2]).append(",");//负平
                    bqcSpfStr.append("负负").append("|").append(ratArr[8].split(":")[2]);//负负
                    result.put("bqcspf", bqcSpfStr);//比分
                }
                if("16".equals(jcMatchBjdcPl.getLotteryPlay())){//上下单双  //上下单双  11 上单  10 上双 01 下单 00 下双

                    sxds.append("上单").append("|").append(ratArr[0].split(":")[2]);//
                    sxds.append("上双").append("|").append(ratArr[1].split(":")[2]);//
                    sxds.append("下单").append("|").append(ratArr[2].split(":")[2]);//
                    sxds.append("下双").append("|").append(ratArr[3].split(":")[2]);//
                    result.put("sxds", sxds);//比分
                }

                //根据matchId  查询比赛信息
                MatchInfoForBdDto matchInfoForBdDtos = scheduleService.quertMatchInfo(matchId);
                result.put("message", "success");
                result.put("gameid", jcMatchBjdcPl.getIdBet007());
                result.put("game_name", matchInfoForBdDtos.getMatchName());
                result.put("num", matchInfoForBdDtos.getIssueNum());
                result.put("home_team", matchInfoForBdDtos.getHomeTeam());
                result.put("visit_team", matchInfoForBdDtos.getGuestTeam());
                result.put("match_date", matchInfoForBdDtos.getDateOfMatch());
            }

        result.put("type","BJDC");
        return result;
    }



}
