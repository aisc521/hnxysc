package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.TbJcMatchMapper;
import com.zhcdata.db.model.TbJcMatch;
import com.zhcdata.jc.dto.MatchPlanResult;
import com.zhcdata.jc.dto.MatchPlanResult1;
import com.zhcdata.jc.dto.MatchResult1;
import com.zhcdata.jc.dto.TbScoreInfo;
import com.zhcdata.jc.service.TbJcMatchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/20 14:15
 */
@Service
public class TbJcMatchServiceImpl implements TbJcMatchService {
    @Resource
    private TbJcMatchMapper tbJcMatchMapper;
    @Override
    public List<MatchPlanResult> queryList(String id) {
        return tbJcMatchMapper.queryList(id);
    }

    @Override
    public int updateStatus(String status,String score,String id) {
        return tbJcMatchMapper.updateStatus(status,score,Long.valueOf(id));
    }

    @Override
    public List<MatchPlanResult1> queryList1(Long id) {
        return tbJcMatchMapper.queryList1(id);
    }

    @Override
    public MatchResult1 queryScore(String matchId) {

        MatchResult1 queryScore1 = tbJcMatchMapper.queryScore1(matchId);

        return queryScore1;
    }

    @Override
    public TbJcMatch queryJcMatchByPlanId(Long id) {
        return tbJcMatchMapper.queryJcMatchByPlanId(id);
    }
}
