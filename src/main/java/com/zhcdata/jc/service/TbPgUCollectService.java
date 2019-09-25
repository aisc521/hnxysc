package com.zhcdata.jc.service;

import com.zhcdata.db.model.TbPgUCollect;

public interface TbPgUCollectService {
    /**
     * 查询用户已经收藏条数
     * @param tbPgUCollect
     * @return
     */
    int queryUserCollectCount(TbPgUCollect tbPgUCollect);

    int queryUserCollectByMatchId(TbPgUCollect tbPgUCollect);

    int updateStatusByUserId(TbPgUCollect tbPgUCollect);

    TbPgUCollect queryUserCollectByUserIdAndMacthId(long l, long l1);

    int insertTbPgUCollect(TbPgUCollect tbPgUCollect);
}
