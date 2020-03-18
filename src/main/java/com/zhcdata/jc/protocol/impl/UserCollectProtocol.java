package com.zhcdata.jc.protocol.impl;

import com.google.common.base.Strings;
import com.zhcdata.db.model.JcMatchLottery;
import com.zhcdata.db.model.TbPgUCollect;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.LotteryTypeMatchJobService;
import com.zhcdata.jc.service.TbPgUCollectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.number.NumberUtil;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description < 收藏/取消收藏(10200202)>
 * @Author cuishuai
 * @Date 2019/9/19 14:32
 */
@Service("20200202")
public class UserCollectProtocol implements BaseProtocol {
    @Resource
    private TbPgUCollectService tbPgUCollectService;
    @Resource
    private LotteryTypeMatchJobService lotteryTypeMatchJobService;


    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
        Map<String, Object> map = new HashMap<>();
        String userId = paramMap.get("userId");
        String matchId = paramMap.get("matchId");
        String type = paramMap.get("type");
        String matchTime = paramMap.get("matchTime");
        String flag = paramMap.get("flag");
        if (Strings.isNullOrEmpty(userId) || !NumberUtil.isNumber(userId)) {
            LOGGER.info("[" + ProtocolCodeMsg.USER_ID_NOT_EXIST.getMsg() + "]:userId---" + userId);
            map.put("resCode", ProtocolCodeMsg.USER_ID_NOT_EXIST.getCode());
            map.put("message", ProtocolCodeMsg.USER_ID_NOT_EXIST.getMsg());
            return map;
        }
        if (Strings.isNullOrEmpty(matchId) || !NumberUtil.isNumber(matchId)) {
            LOGGER.info("[" + ProtocolCodeMsg.COLLECT_MATCHID.getMsg() + "]:matchId---" + matchId);
            map.put("resCode", ProtocolCodeMsg.COLLECT_MATCHID.getCode());
            map.put("message", ProtocolCodeMsg.COLLECT_MATCHID.getMsg());
            return map;
        }
        if (!"F".equals(type) && !"T".equals(type)) {
            LOGGER.info("[" + ProtocolCodeMsg.COLLECT_TYPE.getMsg() + "]:type---" + type);
            map.put("resCode", ProtocolCodeMsg.COLLECT_TYPE.getCode());
            map.put("message", ProtocolCodeMsg.COLLECT_TYPE.getMsg());
            return map;
        }
        if (Strings.isNullOrEmpty(matchTime)) {
            LOGGER.info("[" + ProtocolCodeMsg.COLLECT_MATCHTIME.getMsg() + "]:matchTime---" + matchTime);
            map.put("resCode", ProtocolCodeMsg.COLLECT_MATCHTIME.getCode());
            map.put("message", ProtocolCodeMsg.COLLECT_MATCHTIME.getMsg());
            return map;
        }
        if (Strings.isNullOrEmpty(flag) || !NumberUtil.isNumber(flag)) {
            LOGGER.info("[" + ProtocolCodeMsg.COLLECT_FLAG_FAIL.getMsg() + "]:flag---" + matchId);
            map.put("resCode", ProtocolCodeMsg.COLLECT_FLAG_FAIL.getCode());
            map.put("message", ProtocolCodeMsg.COLLECT_FLAG_FAIL.getMsg());
            return map;
        }

        return null;
    }

    @Override
    public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {

        Map<String, Object> resultMap = new HashMap<>();
        String userId = paramMap.get("userId");
        String matchId = paramMap.get("matchId");
        String type = paramMap.get("type").equals("T") ? "1" : "0";
        String src = headBean.getSrc();
        String flag = paramMap.get("flag");
        String matchTime = paramMap.get("matchTime");
        //判断是收藏操作还是 取消收藏操作
        if("1".equals(type)){//收藏
            TbPgUCollect tbPgUCollect = new TbPgUCollect();
            tbPgUCollect.setUserId(Long.parseLong(userId));
            //查询此用户已经收藏数据条数
            int count = tbPgUCollectService.queryUserCollectCount(tbPgUCollect);
            if (count >= 20&&type.equals("1")) {//收藏条数 到达上限
                resultMap.put("resCode", ProtocolCodeMsg.COLLECT_COUNT.getCode());
                resultMap.put("message", ProtocolCodeMsg.COLLECT_COUNT.getMsg());
                return resultMap;
            }
            tbPgUCollect.setMatchId(Long.parseLong(matchId));
            //查询此条收藏是否存在  存在修改 不存在 新增
            count = tbPgUCollectService.queryUserCollectByMatchId(tbPgUCollect);
            if(count > 0){
                //修改
                tbPgUCollect.setStatus(Integer.parseInt(type));
                tbPgUCollect.setType(Integer.parseInt(flag));
                tbPgUCollect.setMatchId(Long.parseLong(matchId));
                tbPgUCollect.setMatchTime(df.parse(matchTime));
                tbPgUCollect.setOverTime(calenUntil(matchTime));
                int update = tbPgUCollectService.updateStatusByUserId(tbPgUCollect);
                if(update > 0){
                    Integer followNum = tbPgUCollectService.queryCount(Long.valueOf(userId));
                    resultMap.put("followNum",followNum);//已关数量
                    return resultMap;
                }
                else{
                    resultMap.put("resCode", ProtocolCodeMsg.COLLECT_DB_FAIL.getCode());
                    resultMap.put("message", ProtocolCodeMsg.COLLECT_DB_FAIL.getMsg());
                    return resultMap;
                }
            }

            //新增
            tbPgUCollect.setStatus(1);
            tbPgUCollect.setMatchTime(df.parse(matchTime));
            tbPgUCollect.setOverTime(calenUntil(matchTime));
            tbPgUCollect.setSrc(src);
            tbPgUCollect.setCreateTime(new Date());
            tbPgUCollect.setUpdateTime(new Date());
            tbPgUCollect.setType(Integer.parseInt(flag));
            if(tbPgUCollect.getType()==5) {
                //查询lottery
                JcMatchLottery lottery = lotteryTypeMatchJobService.queryJcMatchLotteryByBet007(Long.parseLong(matchId), "JCZQ");
                if (lottery != null) {
                    tbPgUCollect.setType(1);//竞彩
                }
                JcMatchLottery lottery1 = lotteryTypeMatchJobService.queryJcMatchLotteryByBet007(Long.parseLong(matchId), "BJDC");
                if (lottery1 != null) {
                    tbPgUCollect.setType(2);//北单
                }
                JcMatchLottery lottery2 = lotteryTypeMatchJobService.queryJcMatchLotteryByBet007(Long.parseLong(matchId), "SF14");
                if (lottery2 != null) {
                    tbPgUCollect.setType(3);//足彩
                }

                if (lottery == null && lottery2 == null && lottery1 == null) {
                    tbPgUCollect.setType(4);//其他
                }
            }

            int insert = tbPgUCollectService.insertTbPgUCollect(tbPgUCollect);
            if(insert > 0){
                //收藏成功
                Integer followNum = tbPgUCollectService.queryCount(Long.valueOf(userId));
                resultMap.put("followNum",followNum);//已关数量
                return resultMap;
            }else{
                resultMap.put("resCode", ProtocolCodeMsg.COLLECT_DB_FAIL.getCode());
                resultMap.put("message", ProtocolCodeMsg.COLLECT_DB_FAIL.getMsg());
                return resultMap;
            }
        }

        //取消收藏
        TbPgUCollect tbPgUCollect = tbPgUCollectService.queryUserCollectByUserIdAndMacthId(Long.parseLong(userId),Long.parseLong(matchId));
        if(tbPgUCollect != null){
            tbPgUCollect.setStatus(0);//取消收藏
            int update = tbPgUCollectService.updateStatusByUserId(tbPgUCollect);
            if(update > 0){
                Integer followNum = tbPgUCollectService.queryCount(Long.valueOf(userId));
                resultMap.put("followNum",followNum);//已关数量
                return resultMap;
            }
            else{
                resultMap.put("resCode", ProtocolCodeMsg.COLLECT_DB_FAIL.getCode());
                resultMap.put("message", ProtocolCodeMsg.COLLECT_DB_FAIL.getMsg());
                return resultMap;
            }
        }else{
            resultMap.put("resCode", ProtocolCodeMsg.NO_COLLECT_INFO.getCode());
            resultMap.put("message", ProtocolCodeMsg.NO_COLLECT_INFO.getMsg());
            return resultMap;
        }
    }
    //按字符串计算比赛时间加5天
    public static Date calenUntil(String matchTime) {

        Date date = null;
        try {
            date = df.parse(matchTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 2);//让日期加5
        return calendar.getTime();
    }
}
