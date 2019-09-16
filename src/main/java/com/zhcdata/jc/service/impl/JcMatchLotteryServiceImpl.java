package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.JcMatchLotteryMapper;
import com.zhcdata.db.model.JcMatchLottery;
import com.zhcdata.jc.service.JcMatchLotteryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/12 13:50
 */
@Service
public class JcMatchLotteryServiceImpl implements JcMatchLotteryService {
    @Resource
    private JcMatchLotteryMapper jcMatchLotteryMapper;
    @Override
    public JcMatchLottery queryJcMatchLotteryByIssueNumAndNoId(String issueNum, String noId) {
        return jcMatchLotteryMapper.queryJcMatchLotteryByIssueNumAndNoId(issueNum,noId);
    }
}
