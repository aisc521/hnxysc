package com.zhcdata.jc.service;

import com.zhcdata.jc.dto.VictoryInfo;
import com.zhcdata.jc.dto.VictoryResult;
import org.apache.ibatis.annotations.Param;

public interface TbJcVictoryService {
    VictoryResult queryVictory(String expertId);

    int insert (VictoryInfo info);

    int updateById(VictoryInfo info);
}
