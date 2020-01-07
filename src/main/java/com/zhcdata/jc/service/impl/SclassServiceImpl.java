package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.SclassMapper;
import com.zhcdata.db.mapper.TbSclassMapper;
import com.zhcdata.db.model.Sclass;
import com.zhcdata.jc.service.SclassService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/11/12 18:04
 */
@Service
public class SclassServiceImpl implements SclassService {

    @Resource
    private SclassMapper sclassMapper;
    @Override
    public List<Sclass> querySclassInfo() {
        return sclassMapper.querySclassInfo();
    }

}
