package com.zhcdata.jc.protocol.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.zhcdata.jc.dto.IconAndTimeDto;
import com.zhcdata.jc.dto.MatchResult1;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.enums.RedisCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.ScheduleService;
import com.zhcdata.jc.service.TbJcMatchService;
import com.zhcdata.jc.tools.FileUtils;
import com.zhcdata.jc.tools.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.number.NumberUtil;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 比分直播
 * @Author cuishuai
 * @Date 2019/9/24 15:04
 */
@Service("20200206")
public class ScoreLiveCollectProtocol implements BaseProtocol{

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private TbJcMatchService tbJcMatchService;

    @Resource
    private ScheduleService scheduleService;
    //@Value("${custom.qiutan.url.yuMing}")
    private String imgYuMing;
   // @Value("${custom.qiutan.url.imageUrl}")
    private String imagUrl;
   // @Value("${custom.qiutan.url.locolUrl}")
    private String locolUrl;
    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        Map<String, Object> map = new HashMap<>();
        String matchId = paramMap.get("matchId");

        if (Strings.isNullOrEmpty(matchId) || !NumberUtil.isNumber(matchId)) {
            LOGGER.info("[" + ProtocolCodeMsg.LINEUP_MATCHID.getMsg() + "]:matchId---" + matchId);
            map.put("message", ProtocolCodeMsg.LINEUP_MATCHID.getMsg());
            map.put("resCode", ProtocolCodeMsg.LINEUP_MATCHID.getCode());
            return map;
        }

        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        Map resultMap = new HashMap();
        String matchId = paramMap.get("matchId");
        MatchResult1 mo = tbJcMatchService.queryScore(matchId);
        if(mo != null){
            //主队客队名称
            resultMap.put("homeName", mo.getHomeName());
            resultMap.put("guestName", mo.getGuestName());
            //主客队半全场比分
            resultMap.put("hostHalfScore", mo.getHomeHalfScore());
            resultMap.put("hostScore", mo.getHomeScore());
            resultMap.put("guestHalfScore", mo.getGuestHalfScore());
            resultMap.put("guestScore", mo.getGuestScore());
            //比赛进行时间
            resultMap.put("matchMakeTime", mo.getMakeTime());
            //比赛状态
            resultMap.put("matchState", mo.getMatchState());

            //获取图标信息

            String team = (String) redisUtils.hget("SOCCER:HSET:SOCRELIVE"+"team" + paramMap.get("matchId"),"v");
            IconAndTimeDto result = new IconAndTimeDto();
            if (team == null || team .equals("") || team .equals("[]")) {
                result = scheduleService.selectIconAndTime(Integer.valueOf(matchId));
                redisUtils.hset("SOCCER:HSET:SOCRELIVE"+ "team" + matchId, JSONObject.toJSONString(result),"v");
                redisUtils.expire("SOCCER:HSET:SOCRELIVE"+"team" + matchId, RedisCodeMsg.SAME_ODDS.getSeconds());
            }else{
                net.sf.json.JSONObject jsonObject1 = net.sf.json.JSONObject.fromObject(team);
                result = (IconAndTimeDto)net.sf.json.JSONObject.toBean(jsonObject1, IconAndTimeDto.class);
            }
            //主队
            resultMap.put("hostIcon", result.getHomeImg());
            String teamId = (String) redisUtils.hget("SOCCER:HSET:IMG", "teamId");
            if (teamId == null) {
                teamId = "";
            }
            if (!teamId.contains(result.getHomeId())) {
                String img = result.getHomeImg();                    //主队
                locolUrl = locolUrl + img;
                FileUtils.downloadPicture(imagUrl + img + "?win007=sell", locolUrl);
                redisUtils.hset("SOCCER:HSET:IMG", "teamId", teamId + result.getHomeId() + ",");
                resultMap.put("hostIcon",imgYuMing+ locolUrl);
            }



            //客队
            resultMap.put("guestIcon", result.getGuestImg());
            String teamId1 = (String) redisUtils.hget("SOCCER:HSET:IMG", "teamId");
            if (teamId == null) {
                teamId = "";
            }
            if (!teamId.contains(result.getGuestId())) {
                String img = result.getGuestImg();                    //主队
                locolUrl = locolUrl + img;
                FileUtils.downloadPicture(imagUrl + img + "?win007=sell", locolUrl);
                redisUtils.hset("SOCCER:HSET:IMG", "teamId", teamId + result.getGuestImg() + ",");
            }
            resultMap.put("guestIcon",imgYuMing+ locolUrl);
            resultMap.put("matchDate", result.getTime());
            //计算上下半场
            if("1".equals(mo.getMatchState())){
                resultMap.put("matchType", 1);
            }
            if("3".equals(mo.getMatchState())){
                resultMap.put("matchType", 2);
            }
            if("0".equals(mo.getMatchState())){
                resultMap.put("matchType", 0);
            }
            if("-1".equals(mo.getMatchState())){
                resultMap.put("matchType", 3);
            }else {
                resultMap.put("matchType", 0);
            }
            resultMap.put("resCode", ProtocolCodeMsg.SUCCESS.getCode());
            resultMap.put("message", ProtocolCodeMsg.SUCCESS.getMsg());
            resultMap.put("timeId", String.valueOf(System.currentTimeMillis()));
            return resultMap;
        }
        return null;
    }
}
