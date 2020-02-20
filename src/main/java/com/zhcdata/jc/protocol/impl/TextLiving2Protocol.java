package com.zhcdata.jc.protocol.impl;

import com.google.common.base.Strings;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.dto.TextLiving;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.enums.RedisCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.TextLivingService;
import com.zhcdata.jc.tools.CommonUtils;
import com.zhcdata.jc.tools.Const;
import com.zhcdata.jc.tools.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.mapper.JsonMapper;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description < 文字直播·真(20200242)>
 * @Author Yore
 * @Date 2020/02/20 15:44
 */
@Service("20200242")
public class TextLiving2Protocol implements BaseProtocol {
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
        if (!commonUtils.validParamExistAndNumber(map, paramMap, Const.PAGE_NO, ProtocolCodeMsg.PAGE_NO_NOT_ILLEGAL)) {
            return map;
        }
        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {
        int matchId = Integer.parseInt(paramMap.get(Const.MATCH_ID_KEY));
        String timeId = paramMap.get("timeId");
        int pageNo = Integer.parseInt(paramMap.get("pageNo"));
        //第一次传 -1
        if (Strings.isNullOrEmpty(timeId)) {
            timeId = "-1";
        }
        Map<String, Object> result = new HashMap<>();
        LOGGER.error("[20200242]文字直播：开始获取redis");
        LOGGER.error("[20200242]文字直播：获取timeId");

        String key = RedisCodeMsg.PMS_TEXT_LIVE.getName() + "text:" + matchId;
        String s = (String)redisUtils.get(key + ":TIME_ID");

        result.put("timeId", timeId);
        if (!Strings.isNullOrEmpty(s) && timeId.equals(s)) {
            LOGGER.error("[20200242]文字直播：timeId一致，不返回数据");
        }else{
            LOGGER.error("[20200242]文字直播：获取matchId：" + matchId + "数据");
            List<Object> list = redisUtils.lrange(key, (long) 20 * (pageNo - 1), (long) 20 * pageNo - 1);
            if (list != null && list.size() > 0) {
                LOGGER.error("[20200242]文字直播：matchId：" + matchId + "数据已获取");
                JsonMapper jsonMapper = JsonMapper.defaultMapper();
                List<TextLiving> collect = list.stream().map(o -> jsonMapper.fromJson((String) o, TextLiving.class)).collect(Collectors.toList());
                result.put("list", collect);
            } else {
                LOGGER.error("[20200242]文字直播：matchId：" + matchId + "数据不存在");
                List<TextLiving> textLivings = textLivingService.updateTextLiveRedisDataReturnText(matchId, pageNo);
                result.put("list", textLivings);
            }
            long lsize = redisUtils.lsize(key);
            result.put("totalNum", lsize);
            result.put("pageNo", pageNo);
            long pageTotal = lsize / 20;
            if (pageTotal % 20 > 0) {
                pageTotal++;
            }
            result.put("pageTotal", pageTotal);
        }
        return result;
    }
}
