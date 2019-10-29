package com.zhcdata.db.mapper;

import com.zhcdata.db.model.TbPgUCollect;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Priority;

public interface TbPgUCollectMapper extends Mapper<TbPgUCollect> {
    int queryUserCollectCount(TbPgUCollect tbPgUCollect);

    int queryUserCollectByMatchId(TbPgUCollect tbPgUCollect);

    int updateStatusByUserId(TbPgUCollect tbPgUCollect);

    TbPgUCollect queryUserCollectByUserIdAndMacthId(@Param("userId") long userId, @Param("macthId") long macthId);

    Integer queryCount(@Param("userId")Long userId);
}