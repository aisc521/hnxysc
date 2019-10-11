package com.zhcdata.jc.protocol.impl;

import com.github.pagehelper.PageInfo;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.service.JcMatchBjdcPlService;
import com.zhcdata.jc.tools.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@Service("20200901")
public class BjdcMatchResultProtocol implements BaseProtocol {
  @Resource
  private CommonUtils commonUtils;
  @Resource
  JcMatchBjdcPlService jcMatchBjdcPlService;
  @Override
  public Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException {
    Map<String, Object> map = new HashMap<String, Object>(2);

    if (!commonUtils.validParamExist(map, paramMap, "date", ProtocolCodeMsg.BDJC_DAY)) {
        return map;
    }
    if (!commonUtils.validParamExist(map, paramMap, "flag", ProtocolCodeMsg.BDJC_FLAG)) {
      return map;
    }
    return null;
  }

  @Override
  public Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception {


    String pageNo = String.valueOf(paramMap.get("pageNo"));
    String pageAmount = String.valueOf(paramMap.get("pageAmount"));
    if(StringUtils.isBlank(pageNo)||"null".equals(pageNo)){
      pageNo = "1";
    }
    if(StringUtils.isBlank(pageAmount)||"null".equals(pageAmount)){
      pageAmount = "20";
    }
    int pageAmountInt = Integer.parseInt(pageAmount);
    int pageNoInt = Integer.parseInt(pageNo);

    String date = paramMap.get("date");

    //查询北京单场的数据
    PageInfo<Map<String, String>> pageInfo =  jcMatchBjdcPlService.queryBjdcListReuslt(pageNoInt, pageAmountInt,date);
    //需要对查询结果编译处理或者sql处理
    List<Map<String,String>> list = pageInfo.getList();
    Map<String, Object>  returnMap = new HashMap<String,Object>();
    returnMap.put("pageTotal", pageInfo.getPages());
    returnMap.put("openNum", pageInfo.getTotal());
    returnMap.put("pageNo", pageInfo.getPageNum());
    returnMap.put("content",pageInfo.getList());

    return returnMap;
  }
}
