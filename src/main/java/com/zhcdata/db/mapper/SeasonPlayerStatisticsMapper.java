package com.zhcdata.db.mapper;

import com.zhcdata.db.model.SeasonPlayerStatistics;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface SeasonPlayerStatisticsMapper extends Mapper<SeasonPlayerStatistics> {
    SeasonPlayerStatistics queryByPlayIdLenIdSeason(@Param("id") String id, @Param("leagueID")String leagueID, @Param("matchSeason")String matchSeason);
}