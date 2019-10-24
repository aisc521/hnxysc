package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.TbJcRecordFocusMapper;
import com.zhcdata.db.model.TbJcExpert;
import com.zhcdata.db.model.TbJcRecordFocus;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.TbJcRecordFocusService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/10/11 16:07
 */
@Service
public class TbJcRecordFocusServiceImpl implements TbJcRecordFocusService {

    @Resource
    private TbJcRecordFocusMapper recordFocusMapper;

    @Override
    public TbJcRecordFocus queryRecordFocus(Long userId, Long expertId) {
        return recordFocusMapper.queryRecordFocus(userId,expertId);
    }

    @Override
    public void insertRecord(TbJcRecordFocus insertdb) throws BaseException {
        int i = recordFocusMapper.insertSelective(insertdb);
        if(i <= 0){
            throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                    ProtocolCodeMsg.INSERT_FAILE.getMsg());
        }
    }

    @Override
    public void updateRecord(TbJcRecordFocus tbJcRecordFocus) throws BaseException {
        Example example1 = new Example(TbJcRecordFocus.class);
        example1.createCriteria().andEqualTo("id",tbJcRecordFocus.getId());

        int i = recordFocusMapper.updateByExample(tbJcRecordFocus,example1);
        if(i <= 0){
            throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                    ProtocolCodeMsg.UPDATE_FAILE.getMsg());
        }
    }

    @Override
    public Integer queryFollowExpertPushNum(Long userId) {
        return recordFocusMapper.queryFollowExpertPushNum(userId);
    }
}
