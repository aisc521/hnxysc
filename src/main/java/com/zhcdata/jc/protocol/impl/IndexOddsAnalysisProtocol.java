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
 * Description:赔率指数分析
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/10/14 19:08
 */
@Slf4j
@Service("20200213")
public class IndexOddsAnalysisProtocol implements BaseProtocol {
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
        //赔率水位变化 1：0-0.1  2：0.1-0.3  3：0.3-0.5  4：0.5+    0：全部
        if (!commonUtils.validParamExistOrNoNum(map, paramMap, "pam", ProtocolCodeMsg.PAM_NOT_ASSIGNED)) {
            return map;
        }
        //初赔
        if (!commonUtils.validParamExistOrNoNum(map, paramMap, "satSat", ProtocolCodeMsg.PAM_NOT_ASSIGNED)) {
            return map;
        }
        //终赔
        if (!commonUtils.validParamExistOrNoNum(map, paramMap, "endEnd", ProtocolCodeMsg.PAM_NOT_ASSIGNED)) {
            return map;
        }
        //主客队
        if (!commonUtils.validParamExistOrNoNum(map, paramMap, "zkFlag", ProtocolCodeMsg.PAM_NOT_ASSIGNED)) {
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
        //初赔
        String satSat = paramMap.get("satSat");
        //终赔
        String endEnd = paramMap.get("endEnd");
        String zkFlag = paramMap.get("zkFlag");
        String startDate = null;
        if (!Strings.isNullOrEmpty(matchYears) && !"0".equals(matchYears)) {
            //判断起始查询年份 N年内区间，如果为0或空，则是查询全部
            //matchYears
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.YEAR, 0 - Integer.parseInt(matchYears));
            instance.set(Calendar.DAY_OF_YEAR, 1);
            startDate = DateFormatUtil.formatDate(Const.YYYY_MM_DD, instance.getTime());
        }

        //得到赔率公司id
        Map<String, Object> map = handicapOddsService.queryMatchDataByOdds(Double.parseDouble(satSat), Double.parseDouble(endEnd), oddsCompany,
                matchType, startDate, pam, zkFlag);
        return map;
    }
}
