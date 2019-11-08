package com.zhcdata.db.mapper;

import com.zhcdata.db.model.TbJcMatch;
import com.zhcdata.jc.dto.MatchInfoDto;
import com.zhcdata.jc.dto.MatchPlanResult;
import com.zhcdata.jc.dto.MatchPlanResult1;
import com.zhcdata.jc.dto.MatchResult1;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface TbJcMatchMapper extends Mapper<TbJcMatch> {
    List<MatchPlanResult> queryList(@Param("planId")String id);

    Map<String, Integer> queryMatchStatus(@Param("planId")Long id);



    int updateStatus(@Param("status") String status,@Param("score") String score,@Param("id") Long id);

    List<MatchPlanResult1> queryList1(@Param("planId") Long planId);

    MatchResult1 queryScore1(@Param("matchId") String matchId);

    TbJcMatch queryJcMatchByPlanId(@Param("planId") Long id);

    List<MatchInfoDto> queryMatchInfoDtoByPlanId(@Param("planId")String planId);
}