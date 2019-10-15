package com.zhcdata.jc.protocol.impl;

import com.google.common.base.Strings;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.HandicapOddsService;
import com.zhcdata.jc.tools.CommonUtils;
import com.zhcdata.jc.tools.Const;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.number.NumberUtil;
import org.springside.modules.utils.time.DateFormatUtil;

import javax.annotation.Resource;
import java.util.*;

/**
 * Title:
 * Description:盘口指数分析
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/10/15 10:03
 */
@Slf4j
@Service("20200214")
public class IndexHandicapAnalysisProtocol implements BaseProtocol {
    @Resource
    private CommonUtils commonUtils;
    @Resource
    private HandicapOddsService handicapOddsService;

    static List<String> yearTypes = Arrays.asList(new String[]{"", "0", "1", "2", "5", "10"});

    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        Map<String, Object> map = new HashMap<>(2);
        //比赛类型 (1 全部/2 五大联赛/3 北单竞彩/4 其他比赛)
        if (!commonUtils.validParamExistOrNoNum(map, paramMap, "matchType", ProtocolCodeMsg.MATCH_TYPE_NOT_ASSIGNED)) {
            return map;
        }
        //赔率盘口公司  同盘：1澳彩   同赔：(1  BET365/2 威廉/3 立博/4 bwin /5 竞彩)
        if (!commonUtils.validParamExistOrNoNum(map, paramMap, "oddsCompany", ProtocolCodeMsg.ODDS_COMPANY_NOT_ASSIGNED)) {
            return map;
        }
        //matchYears 比赛年限 比赛年限 0 全部 1，2，5，10
        String matchYears = paramMap.get("matchYears");
        if (!Strings.isNullOrEmpty(matchYears) && !yearTypes.contains(matchYears)) {
            commonUtils.errorMessageToMap(map, ProtocolCodeMsg.MATCH_YEARS_NOT_ASSIGNED);
            return map;
        }
        //赔率水位变化 变盘次数为0次时使用，复选时已竖线|分隔 1：0-0.05   2：0.05-0.1   3：0.1-0.2   4：0.2-0.3   5：0.3-0.5   6：0.5+   0：全部
        if (!commonUtils.validParamExist(map, paramMap, "pam", ProtocolCodeMsg.PAM_NOT_ASSIGNED)) {
            return map;
        }
        //初赔
        if (!commonUtils.validParamExistOrNoNum(map, paramMap, "satSatOdds", ProtocolCodeMsg.ODDS_WATER_NOT_ASSIGNED)) {
            return map;
        }
        //终赔
        if (!commonUtils.validParamExistOrNoNum(map, paramMap, "endEndOdds", ProtocolCodeMsg.ODDS_WATER_NOT_ASSIGNED)) {
            return map;
        }
        //类型 1不变盘口 2变盘口
        if (!commonUtils.validParamExistOrNoNum(map, paramMap, Const.TYPE, ProtocolCodeMsg.TYPE_NOT_ASSIGNED)) {
            return map;
        }
        if ("1".equals(paramMap.get(Const.TYPE))) {
            String pam = paramMap.get("pam");
            if (Strings.isNullOrEmpty(pam) || NumberUtil.isNumber(pam.replaceAll("|", ""))) {
                commonUtils.errorMessageToMap(map, ProtocolCodeMsg.PAM_NOT_ASSIGNED);
                return map;
            }
        }
        //初 盘口
        if (!commonUtils.validParamExistOrNoNum(map, paramMap, "satPankou", ProtocolCodeMsg.GOAL_NOT_ASSIGNED)) {
            return map;
        }
        //终 盘口
        if (!commonUtils.validParamExistOrNoNum(map, paramMap, "endPankou", ProtocolCodeMsg.GOAL_NOT_ASSIGNED)) {
            return map;
        }
        //chgTimes 变盘次数,同赔、同盘全部为-1
        if (!commonUtils.validParamExistOrNoNum(map, paramMap, "chgTimes", ProtocolCodeMsg.CHG_TIMES_NOT_ASSIGNED)) {
            return map;
        }

        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        Integer matchType = Integer.parseInt(paramMap.get("matchType"));
        String matchYears = paramMap.get("matchYears");
        String oddsCompany = paramMap.get("oddsCompany");
        String pam = paramMap.get("pam");
        String type = paramMap.get(Const.TYPE);
        //初赔
        String satSat = paramMap.get("satSatOdds");
        //终赔
        String endEnd = paramMap.get("endEndOdds");
        //初赔
        String satPankou = paramMap.get("satPankou");
        //终赔
        String endPankou = paramMap.get("endPankou");
        String chgTimes = paramMap.get("chgTimes");
        String startDate = null;
        if (!Strings.isNullOrEmpty(matchYears) && !"0".equals(matchYears)) {
            //判断起始查询年份 N年内区间，如果为0或空，则是查询全部
            //matchYears
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.YEAR, 0 - Integer.parseInt(matchYears));
            instance.set(Calendar.DAY_OF_YEAR, 1);
            startDate = DateFormatUtil.formatDate(Const.YYYY_MM_DD, instance.getTime());
        }
        if ("1".equals(type)) {
            endPankou = satPankou;
            chgTimes = "0";
        }
        Integer changeTimes = Integer.parseInt(chgTimes);
        //得到赔率公司id
        Map<String, Object> map = handicapOddsService.queryMatchDataByHandicap(Float.parseFloat(satPankou), Float.parseFloat(endPankou), oddsCompany,
                matchType, startDate, pam,satSat,endEnd,changeTimes);
        return map;
    }
}
