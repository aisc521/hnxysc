package com.zhcdata.jc.service;

import com.zhcdata.db.model.TbJcRecordFocus;
import com.zhcdata.jc.exception.BaseException;

public interface TbJcRecordFocusService {
    TbJcRecordFocus queryRecordFocus(Long userId, Long expertId);

    void insertRecord(TbJcRecordFocus insertdb) throws BaseException;

    void updateRecord(TbJcRecordFocus tbJcRecordFocus) throws BaseException;

    Integer queryFollowExpertPushNum(Long userId);
}
