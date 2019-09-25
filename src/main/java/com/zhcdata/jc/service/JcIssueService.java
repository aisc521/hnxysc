package com.zhcdata.jc.service;

import com.zhcdata.db.model.JcIsuue;

import java.util.List;
import java.util.Map;

/**
 * CopyRight (c)1999-2019 : zhcw.com
 * Project : jc-new-server
 * Create Date : 2019/9/17 18:08
 * JDK version : JDK1.8
 * Comments : <针对期次操作>
 *
 * @author : 高阳
 * @version : 0.0.1
 */
public interface JcIssueService {
  //查询需要算的胜负14的期次
  List<Map<String,String>>  queryIssueResult();
  //按期次查询所有比赛的赛果
  List<Map<String,String>> queryMatchReslutByIssue(String issue);
  //按期次查询开奖号
  List<Map<String,String>> queryAwardNumByIssue(String startIssue);

  int insert(String lottery,String issue,String result);

  JcIsuue queryByIssueAndLottery(String lottery,String Issue);
}
