package com.zhcdata.jc.protocol.impl;

import com.google.common.base.Strings;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.enums.RedisCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.TextLivingService;
import com.zhcdata.jc.tools.CommonUtils;
import com.zhcdata.jc.tools.Const;
import com.zhcdata.jc.tools.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.mapper.JsonMapper;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description < 文字直播(10200210)>
 * @Author cuishuai
 * @Date 2019/9/19 15:44
 */
@Service("10200210")
public class TextLivingProtocol implements BaseProtocol {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private CommonUtils commonUtils;

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private TextLivingService textLivingService;


    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        Map<String, Object> map = new HashMap<String, Object>(2);
        //校验比赛id是否存在
        if (!commonUtils.validParamExistAndNumber(map, paramMap, Const.MATCH_ID_KEY, ProtocolCodeMsg.COLLECT_MATCHID)) {
            return map;
        }
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        int matchId = Integer.parseInt(paramMap.get(Const.MATCH_ID_KEY));
        String timeId = paramMap.get("timeId");
        //第一次传 -1
        if (Strings.isNullOrEmpty(timeId)) {
            timeId = "-1";
        }
        Map<String, Object> result = new HashMap<>();
        LOGGER.error("[10200210]文字直播：开始获取redis");
        LOGGER.error("[10200210]文字直播：获取timeId");

        String s = (String)redisUtils.get(RedisCodeMsg.PMS_TEXT_LIVE.getName() + matchId + ":TIME_ID");

        result.put("timeId", timeId);
        if (!Strings.isNullOrEmpty(s) && timeId.equals(s)) {
            LOGGER.error("[10200210]文字直播：timeId一致，不返回数据");
        }else{
            LOGGER.error("[10200210]文字直播：获取matchId：" + matchId + "数据");
            String value = (String)redisUtils.get(RedisCodeMsg.PMS_TEXT_LIVE.getName() + matchId);
            if (!Strings.isNullOrEmpty(value)) {
                LOGGER.error("[10200210]文字直播：matchId：" + matchId + "数据已获取");
                result = JsonMapper.defaultMapper().fromJson(value, Map.class);
                result.put("timeId", Strings.isNullOrEmpty(s) ? timeId : s);
            }else{
                LOGGER.error("[10200210]文字直播：matchId：" + matchId + "数据不存在");
                // TODO: 2019/4/20 上线注释掉
                result = textLivingService.updateTextLiveRedisDataSelective(matchId);
            }
        }
        return result;
    }
}
