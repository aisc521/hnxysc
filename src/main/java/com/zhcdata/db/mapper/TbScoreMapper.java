package com.zhcdata.db.mapper;

import com.zhcdata.db.model.ScoreInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbScoreMapper {
    List<ScoreInfo> queryScore(@Param("SclassID") String SclassID,@Param("subSclassID") String subSclassID,@Param("Homeorguest") String Homeorguest,@Param("TeamID") String TeamID,@Param("Season") String Season);

    int insertSelective(ScoreInfo scoreInfo);

    int updateByPrimaryKeySelective(ScoreInfo scoreInfo);
}
