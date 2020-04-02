package com.zhcdata.jc.service;

import com.zhcdata.db.model.TbJcMatch;
import com.zhcdata.jc.dto.MatchInfoDto;
import com.zhcdata.jc.dto.MatchPlanResult;
import com.zhcdata.jc.dto.MatchPlanResult1;
import com.zhcdata.jc.dto.MatchResult1;

import java.util.List;
import java.util.Map;

public interface TbJcMatchService {

    List<MatchPlanResult> queryList(String id,String matchPlanType);

    List<MatchPlanResult> queryList(String id);

    Map<String,Integer> queryMatchStatus(Long planId);

    int updateStatus(String s, String s1,String id);

    List<MatchPlanResult1> queryList1(Long id);

    MatchResult1 queryScore(String matchId);

    TbJcMatch queryJcMatchByPlanId(Long id);

    List<MatchInfoDto> queryMatchInfoDtoByPlanId(String planId);
}
