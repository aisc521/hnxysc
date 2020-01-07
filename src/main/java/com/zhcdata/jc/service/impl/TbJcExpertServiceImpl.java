package com.zhcdata.jc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhcdata.db.mapper.TbJcExpertMapper;
import com.zhcdata.db.model.TbJcExpert;
import com.zhcdata.jc.dto.ExpertInfo;
import com.zhcdata.jc.dto.ExpertInfoBdDto;
import com.zhcdata.jc.dto.ExpertInfoDto;
import com.zhcdata.jc.dto.HotExpertDto;
import com.zhcdata.jc.service.TbJcExpertService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/20 13:51
 */
@Service
public class TbJcExpertServiceImpl implements TbJcExpertService {
    @Resource
    private TbJcExpertMapper tbJcExpertMapper;
    @Override
    public List<ExpertInfo> queryHotExperts(String type) {
        return tbJcExpertMapper.queryHotExperts(type);
    }

    @Override
    public ExpertInfo queryExpertDetails(String expertId) {
        return tbJcExpertMapper.queryExpertDetails(Long.parseLong(expertId));
    }

    @Override
    public List<ExpertInfo> queryExperts(String time) {
        return tbJcExpertMapper.queryExperts(time);
    }

    @Override
    public List<ExpertInfo> queryExpertsAll() {
        return tbJcExpertMapper.queryExpertsAll();
    }

    @Override
    public PageInfo<ExpertInfoBdDto> queryExpertsByType(String type, Integer pageNo, Integer pageAmount) {

        PageHelper.startPage(pageNo, pageAmount);

        List<ExpertInfoBdDto> list = tbJcExpertMapper.queryExpertsByType(type);
        return new PageInfo<>(list);

    }

    @Override
    public List<ExpertInfo> query(String userId) {
        return tbJcExpertMapper.query(userId);
    }

    @Override
    public TbJcExpert queryExpertDetailsById(int id) {
        return tbJcExpertMapper.queryExpertDetailsById(id);
    }

    @Override
    public void updatePopAddOne(int eid,int pop) {
        tbJcExpertMapper.updatePopAddOneByEid(eid,pop);
    }

    @Override
    public int updateByExample(TbJcExpert tbJcExpert, Example example1) {
        return tbJcExpertMapper.updateByExample(tbJcExpert,example1);
    }

    @Override
    public ExpertInfo queryExpertDetailsAndUser(String expertId, String userId) {
        return tbJcExpertMapper.queryExpertDetailsAndUser(expertId,userId);
    }

    @Override
    public List<ExpertInfoDto> queryExpertInfo(String userId) {
        return tbJcExpertMapper.queryExpertInfo(userId);
    }

    @Override
    public List<HotExpertDto> queryExpertHotInfoOrder() {
        return tbJcExpertMapper.queryExpertHotInfoOrder();
    }

    @Override
    public List<HotExpertDto> queryExpertHotInfoOrderLimitSeven() {
        return tbJcExpertMapper.queryExpertHotInfoOrderLimitSeven();
    }


}
