package com.zhcdata.db.mapper;

import com.zhcdata.db.model.DetailResultInfo;
import com.zhcdata.db.model.TeamInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbDetailResultMapper {
    List<DetailResultInfo> queryDetailResult(@Param("ScheduleID") String ScheduleID,@Param("ID") String ID);

    List<DetailResultInfo> queryDetailList(@Param("ScheduleID") String ScheduleID);

    int deleteByPrimaryKey(@Param("id") int id);

    int insertSelective(DetailResultInfo detailResultInfo);

    int updateByPrimaryKeySelective(DetailResultInfo detailResultInfo);
}