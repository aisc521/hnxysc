package com.zhcdata.db.mapper;

import com.zhcdata.db.model.JcChampionRunnerOdds;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface JcChampionRunnerOddsMapper extends Mapper<JcChampionRunnerOdds> {
    JcChampionRunnerOdds queryJcChampionRunnerOddsByTypeAndMatchIdAndTeams(@Param("typeRsp") String typeRsp, @Param("matchID") String matchID, @Param("teams") String teams);
}