package com.zhcdata.db.mapper;


import com.zhcdata.db.model.ClubRecordInfo;
import com.zhcdata.db.model.WorldRankInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WorldRankingMapper {
    int insertSelective(WorldRankInfo worldRankInfo);

    int updateByTeamID(WorldRankInfo worldRankInfo);

    List<WorldRankInfo> selectByTeamID(@Param("TeamID") Integer TeamID,@Param("Type") String Type);
}