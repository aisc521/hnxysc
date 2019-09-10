package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.JcLotterTypeBdMapper;
import com.zhcdata.db.mapper.JcLotterTypeJcMapper;
import com.zhcdata.db.mapper.JcLotterTypeZcMapper;
import com.zhcdata.db.model.JcLotterTypeZc;
import com.zhcdata.jc.service.LotteryTypeMatchJobService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.ws.ServiceMode;

/**
 * @Description @Description 对应接口： 8.彩票赛程与球探网ID关联表
 *              功   能： 区分赛事彩种信息
 * @Author cuishuai
 * @Date 2019/9/10 16:27
 */
@Service
public class LotteryTypeMatchJobServiceImpl implements LotteryTypeMatchJobService {
    @Resource
    private JcLotterTypeZcMapper jcLotterTypeZcMapper;
    @Resource
    private JcLotterTypeJcMapper jcLotterTypeJcMapper;
    @Resource
    private JcLotterTypeBdMapper jcLotterTypeBdMapper;

    @Override
    public JcLotterTypeZc queryJcLotterTypeZcByIDBet007(long idBet007) {
        return jcLotterTypeZcMapper.queryJcLotterTypeZcByIDBet007(idBet007);
    }
}
