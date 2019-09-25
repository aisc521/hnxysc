package com.zhcdata.db.mapper;

import com.zhcdata.db.model.TbJcVictory;
import com.zhcdata.jc.dto.VictoryInfo;
import com.zhcdata.jc.dto.VictoryResult;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface TbJcVictoryMapper extends Mapper<TbJcVictory> {

    //专家战绩
    VictoryResult queryVictory(@Param("expertId") String expertId);


    int updateById(VictoryInfo info);

    int insertVictoryInfo(VictoryInfo info);
}