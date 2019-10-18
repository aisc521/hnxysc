package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.TbJcExpertMapper;
import com.zhcdata.db.model.TbJcExpert;
import com.zhcdata.jc.dto.ExpertInfo;
import com.zhcdata.jc.service.TbJcExpertService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

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
    public List<ExpertInfo> queryExperts() {
        return tbJcExpertMapper.queryExperts();
    }

    @Override
    public List<ExpertInfo> queryExpertsByType(String type) {
        return tbJcExpertMapper.queryExpertsByType(type);
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
    public void updatePopAddOne(int eid) {
        tbJcExpertMapper.updatePopAddOneByEid(eid);
    }

    @Override
    public int updateByExample(TbJcExpert tbJcExpert, Example example1) {
        return tbJcExpertMapper.updateByExample(tbJcExpert,example1);
    }


}
