package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.PlayerinteamMapper;
import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.mapper.TbJcMatchLineupMapper;
import com.zhcdata.db.model.JcMatchLineupInfo;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.dto.LineupDataDto;
import com.zhcdata.jc.enums.RedisCodeMsg;
import com.zhcdata.jc.service.ScheduleService;
import com.zhcdata.jc.tools.Const;
import com.zhcdata.jc.tools.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.mapper.JsonMapper;
import org.springside.modules.utils.time.ClockUtil;
import org.springside.modules.utils.time.DateFormatUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/16 11:14
 */
@Slf4j
@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Resource
    private ScheduleMapper scheduleMapper;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private TbJcMatchLineupMapper matchLineupMapper;
    @Override
    public Schedule queryScheduleById(Long idBet007) {
        return scheduleMapper.selectByPrimaryKey(Integer.parseInt(String.valueOf(idBet007)));
    }

    @Override
    public Map<String, Object> queryLineupDataByMatch(Long matchId) {
        Map<String, Object> map = new HashMap<>(3);
        //根据比赛id获取阵容
        JcMatchLineupInfo lineupInfo = matchLineupMapper.selectByPrimaryKey(matchId);
        //处理阵容数据
        List<LineupDataDto> infoList = processingLineupData(lineupInfo);
        //组装数据
        map.put("hostTeamLineUp", lineupInfo.getHomeArray());
        map.putIfAbsent("hostTeamLineUp", "");
        map.put("guestTeamLineUp", lineupInfo.getAwayArray());
        map.putIfAbsent("guestTeamLineUp", "");
        map.put("infoList", infoList);
        //处理redis
        String key = RedisCodeMsg.SOCCER_LINEUP_DATA.getName() + ":" + matchId;
        redisUtils.hset(key, "data", JsonMapper.defaultMapper().toJson(map));
        String timeId = DateFormatUtil.formatDate(Const.YYYYMMDDHHMMSSSSS, ClockUtil.currentDate());
        redisUtils.hset(key, "TIME_ID", timeId, RedisCodeMsg.SOCCER_LINEUP_DATA.getSeconds());
        map.put("timeId", timeId);
        return map;
    }

    @Override
    public void updateLineupDataRedis(int before, int after){
        List<Integer> matchIds = scheduleMapper.selectScheduleIdByTime(before, after);
        log.error("查询到的前后一天的比赛记录数为：{}", matchIds.size());
        for (Integer matchId : matchIds) {
            queryLineupDataByMatch(matchId.longValue());
        }
    }
    private List<LineupDataDto> processingLineupData(JcMatchLineupInfo lineupInfo) {
        List<LineupDataDto> list = new ArrayList<>();
        String homeLineup = lineupInfo.getHomeLineup();
        if (Strings.isNotBlank(homeLineup)) {
            processingLineupString("1", "1", list, homeLineup);
        }
        String homeBackup = lineupInfo.getHomeBackup();
        if (Strings.isNotBlank(homeBackup)) {
            processingLineupString("1", "2", list, homeBackup);
        }
        String awayLineup = lineupInfo.getAwayLineup();
        if (Strings.isNotBlank(awayLineup)) {
            processingLineupString("2", "1", list, awayLineup);
        }
        String awayBackup = lineupInfo.getAwayBackup();
        if (Strings.isNotBlank(awayBackup)) {
            processingLineupString("2", "2", list, awayBackup);
        }
        return list;
    }

    private void processingLineupString(String flag,String type, List<LineupDataDto> list,String lineup){
        String[] split = lineup.split(";");
        for (String s : split) {
            String[] split1 = s.split(",");
            LineupDataDto dataDto = new LineupDataDto();
            //球员id
            dataDto.setPlayerId(Integer.parseInt(split1[0]));
            //球员号码
            dataDto.setPlayerNum(split1[3]);
            //球员名
            dataDto.setPlayerName(split1[1]);
            // 【位置】
            // 4列：
            // 0:守门员，1:后卫，2:后腰，3:中场，4:前锋
            // 5列：
            // 0:守门员，1:后卫，2:后腰，3:中场，4:前腰，5:前锋
            dataDto.setPlayerRole(split1[4]);
            //1 首发,2替补
            dataDto.setType(type);
            //1.主队，2客队
            dataDto.setFlag(flag);
            list.add(dataDto);
        }
    }
}
