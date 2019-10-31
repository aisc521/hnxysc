package com.zhcdata.db.mapper;

import com.zhcdata.db.model.Detailresult;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DetailresultMapper extends Mapper<Detailresult> {
    List<Detailresult> queryDetailresultListByMatchId(@Param("matchId") int matchId);
}