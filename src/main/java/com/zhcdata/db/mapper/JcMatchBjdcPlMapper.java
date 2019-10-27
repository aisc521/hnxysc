package com.zhcdata.db.mapper;

import com.zhcdata.db.model.JcMatchBjdcPl;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface JcMatchBjdcPlMapper extends Mapper<JcMatchBjdcPl> {
    List<JcMatchBjdcPl> queryJcMatchBjdcPlByIssuemAndNoId(@Param("issueNum") String issueNum, @Param("noId") String noId);
    //按时间查询比赛完成的数据
    List<Map<String,String>> queryBjdcListReuslt(@Param("startDate") String startDate,@Param("endvDate") String endDate);

  int queryTodayMatchCount(@Param("startDate") String startDate,@Param("endvDate") String endDate);

    List<JcMatchBjdcPl> queryBjdcByMatchId(@Param("matchId") Integer matchId);

  String queryTOdayMatchIssue(@Param("startDate") String startDate,@Param("endvDate") String endDate);
}