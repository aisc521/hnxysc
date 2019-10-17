package com.zhcdata.jc.service;

import com.zhcdata.db.model.TbJcMatch;
import com.zhcdata.jc.dto.MatchPlanResult;
import com.zhcdata.jc.dto.MatchPlanResult1;
import com.zhcdata.jc.dto.MatchResult1;
import com.zhcdata.jc.dto.TbScoreInfo;

import java.util.List;

public interface TbJcMatchService {
    List<MatchPlanResult> queryList(String id);

    int updateStatus(String s, String s1);

    List<MatchPlanResult1> queryList1(Long id);

    MatchResult1 queryScore(String matchId);

    TbJcMatch queryJcMatchByPlanId(Long id);
}
