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
import com.zhcdata.jc.tools.CommonUtils;
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

/**
 * @Description 比赛列表  带userId
 * @Author cuishuai
 * @Date 2019/10/29 15:49
 */
@Service("20200241")
public class MatchListUserIdProtocol implements BaseProtocol {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ScheduleService scheduleService;

    @Resource
    private CommonUtils commonUtils;

    @Resource
    private TbPgUCollectService tbPgUCollectService;

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

    private static final Map<String, String> CorrespondingMap1 = new HashMap<String, String>(){{
        put("受让五球", "-5");
        put("受让四球半/五球","-4.75");
        put("受让四球半","-4.5");
        put("受让四球/四球半","-4.25");
        put("受让四球","-4");
        put("受让三球半/四球","-3.75");
        put("受让三球半","-3.5");
        put("受让三球/三球半","-3.25");
        put("受让三球","-3");
        put("受让两球半/三球","-2.75");
        put("受让两球半","-2.5");
        put("受让两球/两球半","-2.25");
        put("受让两球","-2");
        put("受让球半/两球","-1.75");
        put("受让一球半","-1.5");
        put("受让一球/球半","-1.25");
        put("受让一球","-1");
        put("受让一球","-1.0");
        put("受让半一","-0.75");
        put("受让半球","-0.5");
        put("受让平半","-0.25");
        put("五球","5");
        put("四球半/五球","4.75");
        put("四球半","4.5");
        put("四球/四球半","4.25");
        put("四球","4");
        put("三球半/四球","3.75");
        put("三球半","3.5");
        put("三球/三球半","3.25");
        put("三球","3");
        put("两球半/三球","2.75");
        put("两球半","2.5");
        put("两球/两球半","2.25");
        put("两球","2");
        put("球半/两球","1.75");
        put("一球半","1.5");
        put("一球/球半","1.25");
        put("一球","1");
        put("一球","1.0");
        put("半一","0.75");
        put("半球","0.5");
        put("平半","0.25");
        put("平手","-0");
        put("平手","0");
    }};

    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        Map<String, Object> map = new HashMap<>();
        String type = paramMap.get("type");
        if (Strings.isNullOrEmpty(type)) {
            LOGGER.info("[" + ProtocolCodeMsg.TYPE_NULL.getMsg() + "]:type---" + type);
            map.put("resCode", ProtocolCodeMsg.TYPE_NULL.getCode());
            map.put("message", ProtocolCodeMsg.TYPE_NULL.getMsg());
            return map;
        }

        /*if("3".equals(type)){
            String issueNum = paramMap.get("issueNum");
            if (Strings.isNullOrEmpty(issueNum)) {
                LOGGER.info("[" + ProtocolCodeMsg.ISSUENUM_IS_NOT_EXIT.getMsg() + "]:issueNum---" + issueNum);
                map.put("resCode", ProtocolCodeMsg.ISSUENUM_IS_NOT_EXIT.getCode());
                map.put("message", ProtocolCodeMsg.ISSUENUM_IS_NOT_EXIT.getMsg());
                return map;
            }
        }*/

        String matchTime = paramMap.get("matchTime");
        if (Strings.isNullOrEmpty(matchTime)) {
            LOGGER.info("[" + ProtocolCodeMsg.TIME_NULL.getMsg() + "]:matchTime---" + matchTime);
            map.put("resCode", ProtocolCodeMsg.TIME_NULL.getCode());
            map.put("message", ProtocolCodeMsg.TIME_NULL.getMsg());
            return map;
        }

        String userId = paramMap.get("userId");
        if (Strings.isNullOrEmpty(userId)) {
            LOGGER.info("[" + ProtocolCodeMsg.USER_ID_NOT_EXIST.getMsg() + "]:userId---" + userId);
            map.put("resCode", ProtocolCodeMsg.USER_ID_NOT_EXIST.getCode());
            map.put("message", ProtocolCodeMsg.USER_ID_NOT_EXIST.getMsg());
            return map;
        }

        String pageNo = paramMap.get("pageNo");
        if (Strings.isNullOrEmpty(pageNo) || !NumberUtil.isNumber(pageNo)) {
            LOGGER.info("[" + ProtocolCodeMsg.PAGENO_NULL.getMsg() + "]:pageNo---" + pageNo);
            map.put("resCode", ProtocolCodeMsg.PAGENO_NULL.getCode());
            map.put("message", ProtocolCodeMsg.PAGENO_NULL.getMsg());
            return map;
        }

        String pageAmount = paramMap.get("pageAmount");
        if (Strings.isNullOrEmpty(pageAmount) || !NumberUtil.isNumber(pageAmount)) {
            LOGGER.info("[" + ProtocolCodeMsg.PAGEAMOUNT_NULL.getMsg() + "]:pageAmount---" + pageAmount);
            map.put("resCode", ProtocolCodeMsg.PAGEAMOUNT_NULL.getCode());
            map.put("message", ProtocolCodeMsg.PAGEAMOUNT_NULL.getMsg());
            return map;
        }
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        Map<String, Object> map = new HashMap<>();
        String type = paramMap.get("type");
        String time = paramMap.get("matchTime");
        String pageNo = paramMap.get("pageNo");
        String userId = paramMap.get("userId");
        String issueNum=paramMap.get("issueNum");
        String panKouType=paramMap.get("panKouType");
        String matchType=paramMap.get("matchType");

        List<MatchResult1> newList=new ArrayList<>();

        //赛事类型或盘口赛选直接查数据库
        if(!Strings.isNullOrEmpty(panKouType) ||!Strings.isNullOrEmpty(matchType)){
            if (type.equals("all")) {
                type = "4";
            }else if(type.equals("6")){
                type="3";
            }

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(df.parse(time));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            String endDate=df.format(calendar.getTime());

            PageHelper.startPage(Integer.parseInt(pageNo), 20);
            if(type.equals("4")) {
                newList = scheduleService.queryMacthListForJob(time + " 10:59:59", endDate + " 10:59:59", type, "", "", issueNum, getPanKou(panKouType), getMatchType(matchType)); //全部
            }else if(type.equals("2")) {
                newList = scheduleService.queryMacthListForJob(time + " 09:59:59", endDate + " 09:59:59", type, "", "", issueNum, getPanKou(panKouType), getMatchType(matchType)); //北单
            }else {
                newList = scheduleService.queryMacthListForJob(time + " 11:00:00", endDate + " 11:00:00", type, "", "", issueNum, getPanKou(panKouType), getMatchType(matchType));
            }
            PageInfo<MatchResult1> infos = new PageInfo<>(newList);

            List<MatchResult1> list=new ArrayList<>();
            for(int j=0;j< infos.getList().size();j++){
                MatchResult1 r1=infos.getList().get(j);
                //处理盘口
                r1.setMatchPankou(getPanKou1(r1.getMatchPankou()));
                if(r1.getMatchState().equals("1")){
                    r1.setStatusDescFK("1");
                    if(r1.getMatchTime2()!=null&&!r1.getMatchTime2().contains("0000-00-00 00:00:00")) {
                        Timestamp ts = Timestamp.valueOf(r1.getMatchTime2());
                        String len = getMinute(df1.format(ts), df1.format(new Date()));
                        r1.setMatchState(len+"'");
                    }else {
                        r1.setMatchState("'完'");
                    }
                }else if(r1.getMatchState().equals("3")){
                    r1.setStatusDescFK("3");
                    if(r1.getMatchTime2()!=null&&!r1.getMatchTime2().contains("0000-00-00 00:00:00")) {
                        Timestamp ts = Timestamp.valueOf(r1.getMatchTime2());
                        String len = getMinute(df1.format(ts), df1.format(new Date()));
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

            Integer followNum = tbPgUCollectService.queryCount(Long.valueOf(userId));
            map.put("followNum", followNum);//已关数量
            map.put("pageNo", infos.getPageNum());
            map.put("pageTotal", infos.getPages());
            map.put("totalNum", infos.getTotal());
            map.put("list", list);
        }else {

            if (type.equals("all")) {
                type = "5";
            } else if (type.equals("2")) {
                //北单按期号存的缓存
                //time = scheduleService.queryBdNum(commonUtils.getSE().split(",")[0], commonUtils.getSE().split(",")[1]);
            } else if (type.equals("3")) {
                time = scheduleService.queryZcNum(commonUtils.getSE().split(",")[0], commonUtils.getSE().split(",")[1]);
                //time = paramMap.get("issueNum");
            } else if (type.equals("6")) {
                time = issueNum;
            }
            if (StringUtils.isBlank(time)) {
                map.put("list", "");
            } else {
                String re = (String) redisUtils.hget("SOCCER:HSET:AGAINSTLIST" + time + type, pageNo);
                if (!Strings.isNullOrEmpty(re)) {
                    JavaType javaType = JsonMapper.defaultMapper().buildMapType(Map.class, String.class, Object.class);
                    map = JsonMapper.defaultMapper().fromJson(re, javaType);
                    String s = JsonMapper.defaultMapper().toJson(map.get("list"));
                    JsonMapper jsonMapper = JsonMapper.defaultMapper();
                    JavaType javaType1 = jsonMapper.buildCollectionType(List.class, MatchResult1.class);
                    newList = jsonMapper.fromJson(s, javaType1);
                }else {
                    map.put("pageTotal", "0");
                    map.put("pageNo", "0");
                    map.put("busiCode", "20010201");
                    map.put("resCode", "000000");
                    map.put("message", "成功");
                    map.put("list", null);
                }
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

        return map;
    }

    public List<String> getPanKou(String value) {
        List<String> list = new ArrayList<>();
        if (value.contains(",")) {
            String[] ps = value.split(",");
            for (int i = 0; i < ps.length; i++) {
                list.add(ps[i]);
            }
        } else if(value.length()>0) {
            list.add(value);
        }
        return list;
    }

    public List<String> getMatchType(String value) {
        List<String> list = new ArrayList<>();
        if (value.contains(",")) {
            String[] ps = value.split(",");
            for (int i = 0; i < ps.length; i++) {
                list.add(ps[i]);
            }
        } else if(value.length()>0) {
            list.add(value);
        }
        return list;
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
    public String getPanKou1(String value){
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
