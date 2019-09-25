package com.zhcdata.jc.protocol.impl;

import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.JcIssueService;
import com.zhcdata.jc.tools.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CopyRight (c)1999-2019 : zhcw.com
 * Project : jc-new-server
 * Create Date : 2019/9/16 14:55
 * JDK version : JDK1.8
 * Comments :
 * 查询北京单场比赛结果
 * 给国彩公共号开奖中心用
 * @author : 高阳
 * @version : 0.0.1
 */
@Slf4j
@Service("20200904")
public class Sf14AwadResultProtocol implements BaseProtocol {
  @Resource
  private CommonUtils commonUtils;
  @Resource
  JcIssueService jcIssueService;
  @Override
  public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
    Map<String, Object> map = new HashMap<String, Object>(2);

    if (!commonUtils.validParamExistOrNoNum(map, paramMap, "startIssue", ProtocolCodeMsg.BDJC_DAY)) {
        return map;
    }
    return null;
  }

  @Override
  public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {

    String startIssue = paramMap.get("startIssue");
    List<Map<String,String>>  list = jcIssueService.queryAwardNumByIssue(startIssue);
    Map<String, Object>  returnMap = new HashMap<String,Object>();
    returnMap.put("content",list);
    return returnMap;
  }
}
