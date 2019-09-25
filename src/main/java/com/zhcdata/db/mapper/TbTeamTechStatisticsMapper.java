package com.zhcdata.db.mapper;

import com.zhcdata.db.model.PTSInfo;
import com.zhcdata.db.model.TTSInfo;
import com.zhcdata.jc.dto.EventStandDataSum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbTeamTechStatisticsMapper {
    List<TTSInfo> queryTTS(@Param("scheduleID") String scheduleID, @Param("teamID") String teamID);

    int insertSelective(TTSInfo ttsInfo);

    int updateByPrimaryKeySelective(TTSInfo ttsInfo);

    List<EventStandDataSum> queryEventStandDataSum(@Param("matchId") int matchId);
}