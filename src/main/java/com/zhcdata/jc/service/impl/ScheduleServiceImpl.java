package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.service.ScheduleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/16 11:14
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Resource
    private ScheduleMapper scheduleMapper;
    @Override
    public Schedule queryScheduleById(Long idBet007) {
        return scheduleMapper.selectByPrimaryKey(Integer.parseInt(String.valueOf(idBet007)));
    }
}
