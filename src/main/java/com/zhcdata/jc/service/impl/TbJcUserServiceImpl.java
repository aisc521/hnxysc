package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.TbJcUserMapper;
import com.zhcdata.db.model.TbJcUser;
import com.zhcdata.jc.service.TbJcUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/10/10 16:32
 */
@Service
public class TbJcUserServiceImpl implements TbJcUserService {
    @Resource
    private TbJcUserMapper tbJcUserMapper;

    @Override
    public TbJcUser queryTbJcUserById(Long userId) {
        return tbJcUserMapper.queryTbJcUserById(userId);
    }
}
