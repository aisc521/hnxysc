package com.zhcdata.db.mapper;

import com.zhcdata.db.model.JcIsuue;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface JcIsuueMapper extends Mapper<JcIsuue> {

  List<Map<String,String>> queryIssueResult();

  List<Map<String,String>> queryMatchReslutByIssue(@Param("issue") String issue);

  List<Map<String,String>> queryAwardNumByIssue(@Param("startIssue") String startIssue);

}