package com.zhcdata.db.mapper;

import com.zhcdata.db.model.EuropeOddsDetail;

public interface EuropeOddsDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EuropeOddsDetail record);

    int insertSelective(EuropeOddsDetail record);

    EuropeOddsDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EuropeOddsDetail record);

    int updateByPrimaryKey(EuropeOddsDetail record);

    EuropeOddsDetail selectByOddsNewest(Integer oddsid);

    void deleteByOddsId(Integer oddsid);
}