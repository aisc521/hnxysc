package com.zhcdata.jc.service;

import com.zhcdata.jc.dto.MatchPlanResult;
import com.zhcdata.jc.dto.MatchPlanResult1;

import java.util.List;

public interface TbJcMatchService {
    List<MatchPlanResult> queryList(String id);

    int updateStatus(String s, String s1);

    List<MatchPlanResult1> queryList1(Long id);
}
