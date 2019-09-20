package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.TbJcVictoryMapper;
import com.zhcdata.jc.dto.VictoryInfo;
import com.zhcdata.jc.dto.VictoryResult;
import com.zhcdata.jc.service.TbJcVictoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/20 14:54
 */
@Service
public class TbJcVictoryServiceImpl implements TbJcVictoryService {

    @Resource
    private TbJcVictoryMapper tbJcVictoryMapper;
    @Override
    public VictoryResult queryVictory(String expertId) {
        return tbJcVictoryMapper.queryVictory(expertId);
    }

    @Override
    public int insert(VictoryInfo info) {
        return tbJcVictoryMapper.insertVictoryInfo(info);
    }

    @Override
    public int updateById(VictoryInfo info) {
        return tbJcVictoryMapper.updateById(info);
    }
}
