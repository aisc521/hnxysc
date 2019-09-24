package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.TbPgUCollectMapper;
import com.zhcdata.db.model.TbPgUCollect;
import com.zhcdata.jc.service.TbPgUCollectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/19 14:46
 */
@Service
public class TbPgUCollectServiceImpl implements TbPgUCollectService {
    @Resource
    private TbPgUCollectMapper tbPgUCollectMapper;
    @Override
    public int queryUserCollectCount(TbPgUCollect tbPgUCollect) {
        return tbPgUCollectMapper.queryUserCollectCount(tbPgUCollect);
    }

    @Override
    public int queryUserCollectByMatchId(TbPgUCollect tbPgUCollect) {
        return tbPgUCollectMapper.queryUserCollectByMatchId(tbPgUCollect);
    }

    @Override
    public int updateStatusByUserId(TbPgUCollect tbPgUCollect) {
        return tbPgUCollectMapper.updateStatusByUserId(tbPgUCollect);
    }

    @Override
    public TbPgUCollect queryUserCollectByUserIdAndMacthId(long userId, long macthId) {
        return tbPgUCollectMapper.queryUserCollectByUserIdAndMacthId(userId,macthId);
    }

    @Override
    public int insertTbPgUCollect(TbPgUCollect tbPgUCollect) {
        return tbPgUCollectMapper.insertSelective(tbPgUCollect);
    }
}
