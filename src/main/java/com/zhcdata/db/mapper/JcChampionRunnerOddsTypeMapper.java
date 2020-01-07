package com.zhcdata.db.mapper;

import com.zhcdata.db.model.JcChampionRunnerOddsType;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface JcChampionRunnerOddsTypeMapper extends Mapper<JcChampionRunnerOddsType> {
    JcChampionRunnerOddsType queryJcChampionRunnerOddsTypeByPlayTypeNameAndGameType(@Param("typeRsp") String typeRsp, @Param("type") String type);

    List<JcChampionRunnerOddsType> queryJcChampionRunnerOddsTypeByGameType(String typeRsp);
}