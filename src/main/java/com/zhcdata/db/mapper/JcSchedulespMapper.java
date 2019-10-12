package com.zhcdata.db.mapper;

import com.zhcdata.db.model.JcSchedulesp;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface JcSchedulespMapper extends Mapper<JcSchedulesp> {
    JcSchedulesp queryJcSchedulespByScId(@Param("bet007Id") int bet007);

    //按时间查询比赛完成的数据
    List<Map<String,String>> queryJczqListReuslt(@Param("startDate") String startDate, @Param("endvDate") String endDate);
}