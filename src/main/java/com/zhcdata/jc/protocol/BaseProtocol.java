package com.zhcdata.jc.protocol;


import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.exception.BaseException;

import java.util.Map;

/**
 * CopyRight (c)1999-2017 : zhcw.com
 * Comments : BaseProtocol 所有协议必须实现该接口.
 * JDK version : JDK1.8
 * Create Date : 2017/7/10 16:13
 *
 * @author : Watson W
 * @version : 1.0
 * @since : 1.0
 */
public interface BaseProtocol {

  /**
   * 参数验证
   *
   * @param paramMap paramMap
   * @throws Exception
   */
  @SuppressWarnings("JavaDoc")
  Map<String, Object> validParam(Map<String, String> paramMap) throws BaseException;

  /**
   * 业务逻辑
   *
   * @param headBean ProtocolParamDto.HeadBean
   * @param paramMap     Map
   * @return
   * @throws BaseException
   */
  @SuppressWarnings("JavaDoc")
  Map<String, Object> processLogic(ProtocolParamDto.HeadBean headBean, Map<String, String> paramMap) throws Exception;


}
