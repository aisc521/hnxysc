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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.number.NumberUtil;

import javax.annotation.Resource;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Value("${custom.qiutan.url.yuMing}")
    private String imgYuMing;
   @Value("${custom.qiutan.url.imageUrl}")
    private String imagUrl;
    @Value("${custom.qiutan.url.localUrl}")
    private String localUrl;
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
            resultMap.put("matchName",mo.getMatchName());
            //获取图标信息

            resultMap.put("matchDate", mo.getMatchTime());

            if(mo.getMatchState().equals("未")){
                resultMap.put("matchMakeTime","未");
            }else if(mo.getMatchState().equals("完")){
                resultMap.put("matchType","3");
                resultMap.put("matchMakeTime","完");
            }else if(mo.getMatchState().equals("中")){
                resultMap.put("matchMakeTime","中");
            }else if(mo.getMatchState().equals("取消")){
                resultMap.put("matchMakeTime","未");
                resultMap.put("matchState", "取消");
            }else if(mo.getMatchState().equals("腰斩")){
                resultMap.put("matchMakeTime","未");
                resultMap.put("matchState", "腰斩");
            }else if(mo.getMatchState().equals("推迟")){
                resultMap.put("matchMakeTime","未");
                resultMap.put("matchState", "推迟");
            }
            else {
                if(mo.getMatchState().equals("1")){
                    if(!mo.getMatchTime2().contains("0000-00-00 00:00:00")) {
                        Timestamp ts = Timestamp.valueOf(mo.getMatchTime2());
                        String len = getMinute(df.format(ts), df.format(new Date()));
                        resultMap.put("matchMakeTime",len+"'");
                    }
                }else if(mo.getMatchState().equals("3")){
                    resultMap.put("matchType","2");
                    if(!mo.getMatchTime2().contains("0000-00-00 00:00:00")) {
                        Timestamp ts = Timestamp.valueOf(mo.getMatchTime2());
                        String len = getMinute(df.format(ts), df.format(new Date()));
                        resultMap.put("matchMakeTime",(45 + Integer.valueOf(len)) > 90 ? "90+'" : String.valueOf(45 + Integer.valueOf(len))+"'");
                    }
                }
            }

            String team = (String) redisUtils.get("SOCCER:HSET:SOCRELIVE:" + paramMap.get("matchId"));
            IconAndTimeDto result = new IconAndTimeDto();
            if (team == null || team .equals("") || team .equals("[]")) {
                result = scheduleService.selectIconAndTime(Integer.valueOf(matchId));
                redisUtils.set("SOCCER:HSET:SOCRELIVE:" + matchId,JSONObject.toJSONString(result));
                redisUtils.expire("SOCCER:HSET:SOCRELIVE:" + matchId, RedisCodeMsg.SAME_ODDS.getSeconds());
            }else{
                net.sf.json.JSONObject jsonObject1 = net.sf.json.JSONObject.fromObject(team);
                result = (IconAndTimeDto)net.sf.json.JSONObject.toBean(jsonObject1, IconAndTimeDto.class);
            }
            //主队

//            String teamId = (String) redisUtils.hget("SOCCER:HSET:IMG", "teamId");
//            if (teamId == null) {
//                teamId = "";
//            }
//            if (!teamId.contains(result.getHomeId())) {
            if (!redisUtils.sHasKey("SOCCER:TEAM_IMAGE",result.getHomeId())) {
                String img = result.getHomeImg();
                if (!Strings.isNullOrEmpty(img)) {
                    String localUrl1 = localUrl + img;
                    File file = new File(localUrl1);
                    String parentStr = file.getParent();
                    File parent = new File(parentStr);
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    FileUtils.downloadPicture(imagUrl + img + "?win007=sell", localUrl1);
                    redisUtils.sAdd("SOCCER:TEAM_IMAGE", result.getHomeId());
                }
            }

            //客队
//            String teamId1 = (String) redisUtils.hget("SOCCER:HSET:IMG", "teamId");
//            if (teamId1 == null) {
//                teamId1 = "";
//            }
//            if (!teamId1.contains(result.getGuestId())) {
            if (!redisUtils.sHasKey("SOCCER:TEAM_IMAGE",result.getGuestId())) {
                String img = result.getGuestImg();
                if (!Strings.isNullOrEmpty(img)) {
                    String localUrl2 = localUrl + img;
                    File file = new File(localUrl2);
                    String parentStr = file.getParent();
                    File parent = new File(parentStr);
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    FileUtils.downloadPicture(imagUrl + img + "?win007=sell", localUrl2);
//                redisUtils.hset("SOCCER:HSET:IMG", "teamId", teamId1 + result.getGuestImg() + ",");
                    redisUtils.sAdd("SOCCER:TEAM_IMAGE", result.getGuestId());
                }
            }

            //计算上下半场
//            if("1".equals(mo.getMatchState())){
//                resultMap.put("matchType", 1);
//            }
//            if("3".equals(mo.getMatchState())){
//                resultMap.put("matchType", 2);
//            }
//            if("0".equals(mo.getMatchState())){
//                resultMap.put("matchType", 0);
//            }
//            if("-1".equals(mo.getMatchState())){
//                resultMap.put("matchType", 3);
//            }else {
//                resultMap.put("matchType", 0);
//            }

            resultMap.put("guestIcon", Strings.isNullOrEmpty(result.getGuestImg()) ? null : imgYuMing + result.getGuestImg());
            resultMap.put("hostIcon",Strings.isNullOrEmpty(result.getHomeImg()) ? null : imgYuMing+ result.getHomeImg());
            resultMap.put("resCode", ProtocolCodeMsg.SUCCESS.getCode());
            resultMap.put("message", ProtocolCodeMsg.SUCCESS.getMsg());
            resultMap.put("timeId", String.valueOf(System.currentTimeMillis()));
            return resultMap;
        }
        return null;
    }

    private String getMinute(String s, String e) {
        String str = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1 = sdf.parse(s);     //开始时间
            Date d2 = sdf.parse(e);     //结束时间

            long diff = d2.getTime() - d1.getTime();
            long nd = 1000 * 24 * 60 * 60;
            long nh = 1000 * 60 * 60;
            long nm = 1000 * 60;

            long day = diff / nd;
            long hour1 = diff % nd / nh;
            long min = diff % nd % nh / nm;
            str = String.valueOf(min);
        } catch (Exception ex) {
            LOGGER.error("计算比赛时间异常" + "s:" + s + "e:" , ex);
        }
        return str;
    }
}
