package com.zhcdata.jc.quartz.job.Match;

import com.zhcdata.db.model.JcIsuue;
import com.zhcdata.jc.service.JcIssueService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * CopyRight (c)1999-2019 : zhcw.com
 * Project : jc-new-server
 * Create Date : 2019/9/17 15:36
 * JDK version : JDK1.8
 * Comments : <胜负14赛果需要定时任务自已算出来>
 *
 * @author : 高阳
 * @version : 0.0.1
 */
@Slf4j
@Component
public class Sf14MatchResultJob implements Job {
  @Resource
  JcIssueService jcIssueService;
  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    JobKey key = context.getJobDetail().getKey();
    JobKey jobKey = context.getTrigger().getJobKey();
    long start = System.currentTimeMillis();
    log.error("Instance detail: " + key + " trigger:" + jobKey + "开始执行");
      //先查询需要入库的期
    List<Map<String,String>> list = jcIssueService.queryIssueResult();
    for (Map<String, String> map : list) {
      String matchIssue = map.get("matchIssue");
      String issue = map.get("issue");
      if(issue.equals(matchIssue)){
        log.info("");
        continue;
      }
      //查出相应期的比过赛
      StringBuffer matchRuslt= new StringBuffer();
      List<Map<String,String>> MatchList = jcIssueService.queryMatchReslutByIssue(matchIssue);
      for (Map<String, String> m : MatchList) {
         int HomeScore = Integer.parseInt(m.get("HomeScore"));
         int GuestScore = Integer.parseInt(m.get("GuestScore"));
         String MatchState = m.get("MatchState");
         matchRuslt =  getHandleResult(HomeScore,GuestScore,matchRuslt,MatchState);
      }
      //先查询期次表是否有存在的期次
      JcIsuue jcIsuue = jcIssueService.queryByIssueAndLottery("SF14",matchIssue);
      if(jcIsuue!=null){
        continue;
      }
      //入库期次表
      int iset = jcIssueService.insert("SF14",matchIssue,matchRuslt.toString());
      if(iset<1){
        log.info("彩种=SF14，期次={},赛果={} 入库失败",matchIssue,matchRuslt.toString());
      }
    }
    long end = System.currentTimeMillis();
    log.error("Instance detail: " + key + " trigger:" + jobKey + "执行耗时" + ((double) (end - start) / 1000) + "(s)");
  }

  public StringBuffer getHandleResult(int HomeScore,int GuestScore,StringBuffer matchRuslt,String MatchState){
    String f = "";
    if("-10".equals(MatchState)||"-12".equals(MatchState)){
      f= "*" ;
    }
    else if(HomeScore>GuestScore){
      f = "3" ;
    }else if(HomeScore == GuestScore){
      f = "1";
    }else if(HomeScore < GuestScore){
      f = "0";
    }
    if("".equals(matchRuslt.toString())){
      matchRuslt.append(f);
    }else{
      matchRuslt.append(","+f);
    }
    return matchRuslt;
  }
}
