package com.zhcdata.jc.protocol.impl;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.zhcdata.jc.dto.MatchResult1;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.quartz.job.redis.MatchListDataJob;
import com.zhcdata.jc.service.ScheduleService;
import com.zhcdata.jc.tools.CommonUtils;
import com.zhcdata.jc.tools.Const;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.number.NumberUtil;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description < 关注比赛列表(10200228)>
 * @Author cuishuai
 * @Date 2019/9/19 17:51
 */
@Service("20200230")
public class AttentionListProtocol implements BaseProtocol {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Resource
    private CommonUtils commonUtils;

    @Resource
    private ScheduleService scheduleService;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        Map<String, Object> map = new HashMap<>();
       /* String type = paramMap.get("type");
        if (Strings.isNullOrEmpty(type)) {
            LOGGER.info("[" + ProtocolCodeMsg.TYPE_NULL.getMsg() + "]:type---" + type);
            map.put("resCode", ProtocolCodeMsg.TYPE_NULL.getCode());
            map.put("message", ProtocolCodeMsg.TYPE_NULL.getMsg());
            return map;
        }

        String matchTime = paramMap.get("matchTime");
        if (Strings.isNullOrEmpty(matchTime)) {
            LOGGER.info("[" + ProtocolCodeMsg.TIME_NULL.getMsg() + "]:matchTime---" + matchTime);
            map.put("resCode", ProtocolCodeMsg.TIME_NULL.getCode());
            map.put("message", ProtocolCodeMsg.TIME_NULL.getMsg());
            return map;
        }
*/
        String pageNo = paramMap.get("pageNo");
        if (Strings.isNullOrEmpty(pageNo) || !NumberUtil.isNumber(pageNo)) {
            LOGGER.info("[" + ProtocolCodeMsg.PAGE_NO_NOT_ILLEGAL.getMsg() + "]:pageNo---" + pageNo);
            map.put("resCode", ProtocolCodeMsg.PAGE_NO_NOT_ILLEGAL.getCode());
            map.put("message", ProtocolCodeMsg.PAGE_NO_NOT_ILLEGAL.getMsg());
            return map;
        }

        String pageAmount = paramMap.get("pageAmount");
        if (Strings.isNullOrEmpty(pageAmount) || !NumberUtil.isNumber(pageAmount)) {
            LOGGER.info("[" + ProtocolCodeMsg.PAGEAMOUNT_NULL.getMsg() + "]:pageAmount---" + pageAmount);
            map.put("resCode", ProtocolCodeMsg.PAGEAMOUNT_NULL.getCode());
            map.put("message", ProtocolCodeMsg.PAGEAMOUNT_NULL.getMsg());
            return map;
        }
        if(!commonUtils.validParamExistAndNumber(map, paramMap, Const.USER_ID, ProtocolCodeMsg.USER_ID_NOT_EXIST)){
            return map;
        }
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        Map resultMap = new HashMap();
        String userId = paramMap.get(Const.USER_ID);
        String pageNo = paramMap.get("pageNo");
        String pageAmount = paramMap.get("pageAmount");
        if(StringUtils.isBlank(pageAmount)){
            pageAmount = "20";
        }
        PageInfo<MatchResult1> list = scheduleService.queryAttentionList(userId, pageNo, pageAmount);

        List<MatchResult1> result1s=new ArrayList<>();
        for(int v=0;v<list.getList().size();v++){
            MatchResult1 r1=list.getList().get(v);
            //处理盘口
            //r1.setMatchPankou(getPanKou1(r1.getMatchPankou()));
            if(r1.getMatchState().equals("1")){
                r1.setStatusDescFK("2");
                r1.setStatusescFK("2");
                if(r1.getMatchTime2()!=null&&!r1.getMatchTime2().contains("0000-00-00 00:00:00")) {
                    Timestamp ts = Timestamp.valueOf(r1.getMatchTime2());
                    String len = getMinute(df.format(ts), df.format(new Date()));
                    r1.setMatchState(len+"'");
                }
            }else if(r1.getMatchState().equals("3")){
                r1.setStatusDescFK("3");
                r1.setStatusescFK("3");
                if(r1.getMatchTime2()!=null&&!r1.getMatchTime2().contains("0000-00-00 00:00:00")) {
                    Timestamp ts = Timestamp.valueOf(r1.getMatchTime2());
                    String len = getMinute(df.format(ts), df.format(new Date()));
                    r1.setMatchState((45 + Integer.valueOf(len)) > 90 ? "90+'" : String.valueOf(45 + Integer.valueOf(len))+"'");
                }
            }

            if(r1.getMatchState().equals("未")) {
                r1.setStatusDescFK("1");
            }
            result1s.add(r1);
        }

        resultMap.put("busiCode", "10200201");
        resultMap.put("resCode", "000000");
        resultMap.put("message", "");
        resultMap.put("pageNo", pageNo);
        resultMap.put("pageTotal", list.getPages());
        resultMap.put("list", result1s);
        return resultMap;
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
