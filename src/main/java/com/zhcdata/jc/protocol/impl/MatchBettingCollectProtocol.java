package com.zhcdata.jc.protocol.impl;

import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Strings;
import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.db.model.JcSchedulesp;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.enums.RedisCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.JcScheduleService;
import com.zhcdata.jc.service.JcSchedulespService;
import com.zhcdata.jc.service.ScheduleService;
import com.zhcdata.jc.tools.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.mapper.JsonMapper;
import org.springside.modules.utils.number.NumberUtil;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/20 15:19
 */
@Service("10200223")
public class MatchBettingCollectProtocol implements BaseProtocol {
    //log
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchBettingCollectProtocol.class);

    @Resource
    private RedisUtils redisUtils;

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

        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        String matchId = paramMap.get("matchId");
        String timeId = paramMap.get("timeId");
        if (StringUtils.isEmpty(timeId))
            timeId = "0";
        Map<String, Object> result = JSONArray.parseObject((String) redisUtils.hget("SOCCER:BETTING:FIVEMETHOD:" + matchId, "v"), Map.class);
        if (result.get("timeId").toString().equals(timeId)) {
            Map<String, Object> resultMap = new HashMap<>();
            //基础信息
            result.put("message", "success");
            result.put("timeId", timeId);
            return resultMap;
        } else
            return result;
    }

    public void autoWork() {
        List<Integer> matchIds = scheduleService.selectMatchIdExceedNow();
        Integer count = 0;
        long sat = System.currentTimeMillis();
        for (int i = 0; i < matchIds.size(); i++) {
            try {
                Map<String, Object> oneMatch = getOneMatch(matchIds.get(i));
                String message = oneMatch.get("message").toString();
                if("success".equals(message)){
                    redisUtils.hset("SOCCER:BETTING:FIVEMETHOD:" + matchIds.get(i), "v", JsonMapper.defaultMapper().toJson(oneMatch));
                    redisUtils.expire("SOCCER:BETTING:FIVEMETHOD:" + matchIds.get(i), RedisCodeMsg.SAME_ODDS.getSeconds());
                }
                count++;
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("五种玩法赔率查询生成失败，比赛id：" + matchIds.get(i));
            }
        }
        LOGGER.info("五种玩法赔率查询生成完成，操作比赛：" + count + "场,耗时：" + (System.currentTimeMillis() - sat) + "毫秒");
    }

    public Map<String, Object> getOneMatch(Integer matchId) {
        Map<String, Object> result = new HashMap<>();
        JcSchedulesp jcScheduleSp = jcSchedulespService.queryJcSchedulespById(matchId);
        JcSchedule jcSchedule = jcScheduleService.queryJcScheduleByBet007(matchId);

        if(jcScheduleSp != null && jcSchedule != null){
            //胜平负
            StringBuilder spfStr = new StringBuilder();
            spfStr.append("win").append("|").append(jcScheduleSp.getSf3()).append(",");
            spfStr.append("level").append("|").append(jcScheduleSp.getSf1()).append(",");
            spfStr.append("lose").append("|").append(jcScheduleSp.getSf0()).append(",");
            spfStr.append("concedenum").append("|").append("0");

            //让球胜平负
            StringBuilder rqSpfStr = new StringBuilder();
            rqSpfStr.append("win").append("|").append(jcScheduleSp.getWl3()).append(",");
            rqSpfStr.append("level").append("|").append(jcScheduleSp.getWl1()).append(",");
            rqSpfStr.append("lose").append("|").append(jcScheduleSp.getWl0()).append(",");
            rqSpfStr.append("concedenum").append("|").append(jcSchedule.getPolygoal());

            StringBuilder bfStr = new StringBuilder();
            bfStr.append("onetozero").append("|").append(jcScheduleSp.getSw10()).append(",");
            bfStr.append("twotozero").append("|").append(jcScheduleSp.getSw20()).append(",");
            bfStr.append("twotoone").append("|").append(jcScheduleSp.getSw21()).append(",");
            bfStr.append("threetozero").append("|").append(jcScheduleSp.getSw30()).append(",");
            bfStr.append("threetoone").append("|").append(jcScheduleSp.getSw31()).append(",");
            bfStr.append("threetotwo").append("|").append(jcScheduleSp.getSw32()).append(",");
            bfStr.append("fourtozero").append("|").append(jcScheduleSp.getSw40()).append(",");
            bfStr.append("fourtoone").append("|").append(jcScheduleSp.getSw41()).append(",");
            bfStr.append("fourtotwo").append("|").append(jcScheduleSp.getSw42()).append(",");
            bfStr.append("fivetozero").append("|").append(jcScheduleSp.getSw50()).append(",");
            bfStr.append("fivetoone").append("|").append(jcScheduleSp.getSw51()).append(",");
            bfStr.append("fivetotwo").append("|").append(jcScheduleSp.getSw52()).append(",");
            bfStr.append("winother").append("|").append(jcScheduleSp.getSw5()).append(",");
            bfStr.append("zerotozero").append("|").append(jcScheduleSp.getSd00()).append(",");
            bfStr.append("onetoone").append("|").append(jcScheduleSp.getSd11()).append(",");
            bfStr.append("twototwo").append("|").append(jcScheduleSp.getSd22()).append(",");
            bfStr.append("threetothree").append("|").append(jcScheduleSp.getSd33()).append(",");
            bfStr.append("levelother").append("|").append(jcScheduleSp.getSd4()).append(",");
            bfStr.append("zerotoone").append("|").append(jcScheduleSp.getSl01()).append(",");
            bfStr.append("zerototwo").append("|").append(jcScheduleSp.getSl02()).append(",");
            bfStr.append("onetotwo").append("|").append(jcScheduleSp.getSl12()).append(",");
            bfStr.append("zerotothree").append("|").append(jcScheduleSp.getSl03()).append(",");
            bfStr.append("onetothree").append("|").append(jcScheduleSp.getSl13()).append(",");
            bfStr.append("twotothree").append("|").append(jcScheduleSp.getSl23()).append(",");
            bfStr.append("zerotofour").append("|").append(jcScheduleSp.getSl04()).append(",");
            bfStr.append("onetofour").append("|").append(jcScheduleSp.getSl14()).append(",");
            bfStr.append("twotofour").append("|").append(jcScheduleSp.getSl24()).append(",");
            bfStr.append("zerotofive").append("|").append(jcScheduleSp.getSl05()).append(",");
            bfStr.append("onetofive").append("|").append(jcScheduleSp.getSl15()).append(",");
            bfStr.append("twotofive").append("|").append(jcScheduleSp.getSl25()).append(",");
            bfStr.append("lostother").append("|").append(jcScheduleSp.getSl5()).append(",");
            bfStr.append("concedenum").append("|").append("0");


            //总进球
            StringBuilder zjqStr = new StringBuilder();
            zjqStr.append("totalgoal0rate").append("|").append(jcScheduleSp.getT0()).append(",");
            zjqStr.append("totalgoal1rate").append("|").append(jcScheduleSp.getT1()).append(",");
            zjqStr.append("totalgoal2rate").append("|").append(jcScheduleSp.getT2()).append(",");
            zjqStr.append("totalgoal3rate").append("|").append(jcScheduleSp.getT3()).append(",");
            zjqStr.append("totalgoal4rate").append("|").append(jcScheduleSp.getT4()).append(",");
            zjqStr.append("totalgoal5rate").append("|").append(jcScheduleSp.getT5()).append(",");
            zjqStr.append("totalgoal6rate").append("|").append(jcScheduleSp.getT6()).append(",");
            zjqStr.append("totalgoal7rate").append("|").append(jcScheduleSp.getT7());


            //半全场
            StringBuilder bqcSpfStr = new StringBuilder();
            bqcSpfStr.append("winwinrate").append("|").append(jcScheduleSp.getHt33()).append(","); //胜胜
            bqcSpfStr.append("winlevelrate").append("|").append(jcScheduleSp.getHt31()).append(",");//胜平
            bqcSpfStr.append("winloserate").append("|").append(jcScheduleSp.getHt30()).append(",");//胜负
            bqcSpfStr.append("levelwinrate").append("|").append(jcScheduleSp.getHt13()).append(",");//平胜
            bqcSpfStr.append("levellevelrate").append("|").append(jcScheduleSp.getHt11()).append(",");//平平
            bqcSpfStr.append("levelloserate").append("|").append(jcScheduleSp.getHt10()).append(",");//平负
            bqcSpfStr.append("losewinrate").append("|").append(jcScheduleSp.getHt03()).append(",");//负胜
            bqcSpfStr.append("loselevelrate").append("|").append(jcScheduleSp.getHt01()).append(",");//负平
            bqcSpfStr.append("loseloserate").append("|").append(jcScheduleSp.getHt00());//负负
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
        }
        return result;
    }
}
