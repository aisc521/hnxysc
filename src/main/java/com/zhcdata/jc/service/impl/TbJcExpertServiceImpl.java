package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.TbJcExpertMapper;
import com.zhcdata.jc.dto.ExpertInfo;
import com.zhcdata.jc.service.TbJcExpertService;
import org.springframework.stereotype.Service;

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
}
