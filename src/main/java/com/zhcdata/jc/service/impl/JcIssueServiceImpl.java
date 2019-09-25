package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.JcIsuueMapper;
import com.zhcdata.db.model.JcIsuue;
import com.zhcdata.jc.service.JcIssueService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * CopyRight (c)1999-2019 : zhcw.com
 * Project : jc-new-server
 * Create Date : 2019/9/17 18:27
 * JDK version : JDK1.8
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 *
 * @author : 高阳
 * @version : 0.0.1
 */
@Service
public class JcIssueServiceImpl implements JcIssueService {
  @Resource
  JcIsuueMapper jcIsuueMapper;
  @Override
  public List<Map<String, String>> queryIssueResult() {
    return jcIsuueMapper.queryIssueResult();
  }

  @Override
  public List<Map<String, String>> queryMatchReslutByIssue(String issue) {
    return jcIsuueMapper.queryMatchReslutByIssue(issue);
  }

  @Override
  public List<Map<String, String>> queryAwardNumByIssue(String startIssue) {
    return jcIsuueMapper.queryAwardNumByIssue(startIssue);
  }

  @Override
  public int insert(String lottery,String issue,String result) {
    JcIsuue jcIssue = new JcIsuue();
    jcIssue.setLottery(lottery);
    jcIssue.setIssue(issue);
    jcIssue.setWinNum(result);
    jcIssue.setLotteryName("14场胜负彩");
    jcIssue.setStatus("2");
     Date date = new Date();
    jcIssue.setCreateTime(date);
    jcIssue.setUpdateTime(date);
    jcIssue.setAwardTime(date);
    jcIssue.setYear(date.getYear()+"");
    jcIssue.setMonth(date.getMonth()+"");
    jcIssue.setDay(date.getDay()+"");
    return jcIsuueMapper.insertSelective(jcIssue);
  }

  @Override
  public JcIsuue queryByIssueAndLottery(String lottery, String Issue) {
    Example example = new Example(JcIsuue.class);
    example.createCriteria()
            .andEqualTo("issue", Issue)
            .andEqualTo("lottery", lottery);
    return (JcIsuue) jcIsuueMapper.selectByExample(example);
  }


}
