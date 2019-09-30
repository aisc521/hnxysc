package com.zhcdata.db.mapper;

import com.zhcdata.db.model.EuropeOdds;
import com.zhcdata.jc.dto.SimplifyOdds;

import java.util.List;

public interface EuropeOddsMapper {
    int deleteByPrimaryKey(Integer oddsid);

    int insert(EuropeOdds record);

    int insertSelective(EuropeOdds record);

    EuropeOdds selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(EuropeOdds record);

    int updateByPrimaryKey(EuropeOdds record);

    EuropeOdds selectByMidAndCpyAnd(String mid, String cpy);

    List<EuropeOdds> selectByMid(String mid);

    void deleteByMid(String mid);
}