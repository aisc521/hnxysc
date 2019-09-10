package com.zhcdata.jc.service;

import com.zhcdata.db.model.JcLotterTypeZc;

/**
 * @Description 对应接口： 8.彩票赛程与球探网ID关联表
 *              功   能： 区分赛事彩种信息
 */
public interface LotteryTypeMatchJobService {
    /**
     * 根据IDBet007 查询是否有对应数据信息
     * @param l
     * @return
     */
    JcLotterTypeZc queryJcLotterTypeZcByIDBet007(long l);
}
