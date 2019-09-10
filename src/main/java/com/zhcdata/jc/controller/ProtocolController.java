package com.zhcdata.jc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;
import com.zhcdata.jc.converter.CustomObjectMapper;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.enums.RedisCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.exception.ProtocolNotExistException;
import com.zhcdata.jc.protocol.BaseProtocol;
import com.zhcdata.jc.protocol.ProtocolFactory;
import com.zhcdata.jc.tools.Const;
import com.zhcdata.jc.tools.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.utils.mapper.JsonMapper;
import org.springside.modules.utils.time.ClockUtil;
import org.springside.modules.utils.time.DateFormatUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司-互联网事业部
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/3/19 16:52
 */
@Controller
@RequestMapping("/protocol")
public class ProtocolController {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProtocolController.class);

    @Resource
    private RedisUtils redisUtils;

    /**
     * 调度中心
     *
     * @param transMessage 参数串
     * @return ProtocolParamDto
     */
    @PostMapping
    @ResponseBody
    public ProtocolParamDto dispatchCenter(@RequestParam("transMessage") String transMessage, String ip) {
        // 定时器(供计算请求耗时使用)
        final Stopwatch stopwatch = Stopwatch.createStarted();
        LOGGER.error("[请求参数]:ip---{}\t\ttransMessage---{} ", ip, transMessage);
        ProtocolParamDto result = new ProtocolParamDto();
        ProtocolParamDto.Message message = new ProtocolParamDto.Message();
        Map<String, Object> body;
        String transactionType = null;
        ProtocolParamDto.HeadBean head = null;
        CustomObjectMapper objectMapper = new CustomObjectMapper();
        try {
            if (Strings.isNullOrEmpty(transMessage) || "null".equals(transMessage)) {
                LOGGER.info("[" + ProtocolCodeMsg.REQUEST_TRANS_MESSAGE_NULL.getMsg() + "]:transMessage---" + transMessage);
                return buildErrMsg(result,
                        ProtocolCodeMsg.REQUEST_TRANS_MESSAGE_NULL.getCode(),
                        ProtocolCodeMsg.REQUEST_TRANS_MESSAGE_NULL.getMsg(),
                        new ProtocolParamDto.HeadBean());
            }

            // 请求参数json转换为对象
            ProtocolParamDto paramDto = JsonMapper.nonEmptyMapper().fromJson(transMessage, ProtocolParamDto.class);
            // 2-JSON解析错误
            if (null == paramDto) {
                //noinspection ConstantConditions
                LOGGER.info("[" + ProtocolCodeMsg.JSON_PARS_ERROR.getMsg() + "]:paramDto---" + paramDto);
                return buildErrMsg(result, ProtocolCodeMsg.JSON_PARS_ERROR.getCode(),
                        ProtocolCodeMsg.JSON_PARS_ERROR.getMsg(),
                        new ProtocolParamDto.HeadBean());
            }
            head = paramDto.getMessage().getHead();
            transactionType = head == null ? "" : head.getTransactionType();
            Map reqBody = paramDto.getMessage().getBody();
            if (!Strings.isNullOrEmpty(ip)) {
                reqBody.put("ip", ip);
            }
            // 校验数据
            ProtocolParamDto validParamResult = validParam(paramDto, result);
            if (null != validParamResult) {
                return validParamResult;
            }

            // 将请求头更新到返回对象中 更新时间戳
            assert head != null;
            head.setTimeStamp(head.getTimeStamp() + "|"
                    + DateFormatUtil.formatDate(Const.YYYYMMDDHHMMSSSSS, ClockUtil.currentDate()));

            BaseProtocol baseProtocol = ProtocolFactory.getProtocolInstance(transactionType);

            //noinspection unchecked
            final Map<String, Object> validParams = baseProtocol.validParam(reqBody);

            if (null != validParams) {
                // 请求参数(body)校验失败
                validParams.put(Const.RESULT_MSG_KEY, matchMsg(transactionType,
                        validParams.get(Const.RES_CODE_KEY).toString(),
                        validParams.get(Const.RESULT_MSG_KEY).toString()));
                body = validParams;
            } else {
                //noinspection unchecked
                body = baseProtocol.processLogic(head, reqBody);

                if (null == body) {
                    body = new HashMap<>(2);
                    body.put(Const.RES_CODE_KEY, ProtocolCodeMsg.SUCCESS.getCode());
                    body.put(Const.RESULT_MSG_KEY, ProtocolCodeMsg.SUCCESS.getMsg());
                } else {
                    if (null == body.get(Const.RES_CODE_KEY)) {
                        body.put(Const.RES_CODE_KEY, ProtocolCodeMsg.SUCCESS.getCode());
                        body.put(Const.RESULT_MSG_KEY, ProtocolCodeMsg.SUCCESS.getMsg());
                    }
                }
                body.put(Const.RESULT_MSG_KEY, matchMsg(transactionType, body.get(Const.RES_CODE_KEY).toString(), body.get(Const.RESULT_MSG_KEY).toString()));

            }
            head.setResCode(ProtocolCodeMsg.SUCCESS.getCode());
            head.setMessage(ProtocolCodeMsg.SUCCESS.getMsg());

            message.setHead(head);
            message.setBody(body);
            result.setMessage(message);

        } catch (ProtocolNotExistException e) {
            LOGGER.error("[" + ProtocolCodeMsg.TT_NOT_EXIST.getMsg() + "]:协议号---" + transactionType, e);
            return buildErrMsg(result, ProtocolCodeMsg.TT_NOT_EXIST.getCode(), ProtocolCodeMsg.TT_NOT_EXIST.getMsg(), head);
        } catch (BaseException e) {
            LOGGER.error("[" + e.getMessage() + "]:协议号---" + transactionType, e);
            return buildErrMsg(result, e.getCode(), matchMsg(transactionType, e.getCode(), e.getMessage()), head);
        } catch (Exception e) {
            LOGGER.error("[" + ProtocolCodeMsg.SERVER_BUSY.getMsg() + "]:协议号---" + transactionType, e);
            return buildErrMsg(result, ProtocolCodeMsg.SERVER_BUSY.getCode(), ProtocolCodeMsg.SERVER_BUSY.getMsg(), head);
        } finally {
            try {
                LOGGER.error("[" + transactionType + "] 调用结束返回消息体:" + objectMapper.writeValueAsString(result));
                long nanos = stopwatch.elapsed(TimeUnit.MILLISECONDS);
                LOGGER.error("[" + transactionType + "] 协议耗时: " + nanos + "ms-------------------------protocol time consuming----------------------");
            } catch (JsonProcessingException e) {
                LOGGER.error("[" + ProtocolCodeMsg.JSON_PARS_ERROR.getMsg() + "]:协议号---" + transactionType, e);
                result = buildErrMsg(result, ProtocolCodeMsg.JSON_PARS_ERROR.getCode(), ProtocolCodeMsg.JSON_PARS_ERROR.getMsg(), head);
            }
        }

        return result;
    }

    /**
     * 匹配返回消息内容
     *
     * @param transactionType 协议号
     * @param code            消息编码
     * @param defaultMsg      默认消息内容
     * @return 匹配后的消息内容
     */
    private String matchMsg(String transactionType, String code, String defaultMsg) {
        String result;
        String msg;
        if (ProtocolCodeMsg.SUCCESS.getCode().equals(code)) {
            msg = (String) redisUtils.hget(RedisCodeMsg.GOODS_COMM_MSG_LIST.getName(), transactionType + code);
        } else {
            msg = (String) redisUtils.hget(RedisCodeMsg.GOODS_COMM_MSG_LIST.getName(), code);
        }
        if (Strings.isNullOrEmpty(msg)) {
            result = defaultMsg;
        } else {
            result = msg;
        }
        return result;
    }


    /**
     * 校验请求参数
     *
     * @param paramDto 参数对象
     * @param result   操作结果
     * @return ProtocolParamDto
     */
    private ProtocolParamDto validParam(ProtocolParamDto paramDto, ProtocolParamDto result) {

        // paramDto为空
        if (null == paramDto) {
            //noinspection ConstantConditions
            LOGGER.warn("[" + ProtocolCodeMsg.PARAMETER_STRING_TO_OBJECT_FAILED.getMsg() + "]:ProtocolParamDto---" + paramDto);
            return buildErrMsg(result, ProtocolCodeMsg.PARAMETER_STRING_TO_OBJECT_FAILED.getCode(),
                    ProtocolCodeMsg.PARAMETER_STRING_TO_OBJECT_FAILED.getMsg(), new ProtocolParamDto.HeadBean());
        }
        // Message对象为空
        if (null == paramDto.getMessage()) {
            LOGGER.warn("[" + ProtocolCodeMsg.MESSAGE_NULL.getMsg() + "]:Message---" + paramDto.getMessage());
            return buildErrMsg(result, ProtocolCodeMsg.MESSAGE_NULL.getCode(),
                    ProtocolCodeMsg.MESSAGE_NULL.getMsg(), new ProtocolParamDto.HeadBean());
        }

        ProtocolParamDto.HeadBean head = paramDto.getMessage().getHead();

        // Head为空
        if (null == head) {
            //noinspection ConstantConditions
            LOGGER.warn("[" + ProtocolCodeMsg.HEAD_NULL.getMsg() + "]:Head---" + head);
            return buildErrMsg(result, ProtocolCodeMsg.HEAD_NULL.getCode(),
                    ProtocolCodeMsg.HEAD_NULL.getMsg(), new ProtocolParamDto.HeadBean());
        }
        // 校验系统类型为空
        if (Strings.isNullOrEmpty(head.getSysType())) {
            LOGGER.warn("[" + ProtocolCodeMsg.SYSTYPE_NOT_ASSIGNED.getMsg() + "]:sysType---" + head.getSysType());
            return buildErrMsg(result, ProtocolCodeMsg.SYSTYPE_NOT_ASSIGNED.getCode(),
                    ProtocolCodeMsg.SYSTYPE_NOT_ASSIGNED.getMsg(), head);
        }
        // 标志渠道信息
        if (Strings.isNullOrEmpty(head.getSrc())) {
            LOGGER.warn("[" + ProtocolCodeMsg.SRC_NOT_ASSIGNED.getMsg() + "]:src---" + head.getSrc());
            return buildErrMsg(result, ProtocolCodeMsg.SRC_NOT_ASSIGNED.getCode(),
                    ProtocolCodeMsg.SRC_NOT_ASSIGNED.getMsg(), head);
        }
        // 消息编号
        if (Strings.isNullOrEmpty(head.getMessageID())) {
            LOGGER.warn("[" + ProtocolCodeMsg.MESSAGEID_NOT_ASSIGNED.getMsg() + "]:messengeID---" + head.getMessageID());
            return buildErrMsg(result, ProtocolCodeMsg.MESSAGEID_NOT_ASSIGNED.getCode(),
                    ProtocolCodeMsg.MESSAGEID_NOT_ASSIGNED.getMsg(), head);
        }
        // 消息发送方编号
        if (Strings.isNullOrEmpty(head.getMessengerID())) {
            LOGGER.warn("[" + ProtocolCodeMsg.MESSENGERID_NOT_ASSIGNED.getMsg() + "]:messengerID---" + head.getMessengerID());
            return buildErrMsg(result, ProtocolCodeMsg.MESSENGERID_NOT_ASSIGNED.getCode(),
                    ProtocolCodeMsg.MESSENGERID_NOT_ASSIGNED.getMsg(), head);
        }
        // 校验协议号为空
        if (Strings.isNullOrEmpty(head.getTransactionType())) {
            LOGGER.warn("[" + ProtocolCodeMsg.TRANSACTIONTYPE_NOT_ASSIGNED.getMsg() + "]:transactionType---" + head.getTransactionType());
            return buildErrMsg(result, ProtocolCodeMsg.TRANSACTIONTYPE_NOT_ASSIGNED.getCode(),
                    ProtocolCodeMsg.TRANSACTIONTYPE_NOT_ASSIGNED.getMsg(), head);
        }
        // 校验时间戳
        if (Strings.isNullOrEmpty(head.getTimeStamp())) {
            LOGGER.warn("[" + ProtocolCodeMsg.TIMESTAMP_NOT_ASSIGNED.getMsg() + "]:timeStamp---" + head.getTimeStamp());
            return buildErrMsg(result, ProtocolCodeMsg.TIMESTAMP_NOT_ASSIGNED.getCode(),
                    ProtocolCodeMsg.TIMESTAMP_NOT_ASSIGNED.getMsg(), head);
        }
        // 类型不匹配
        if (!head.getSysType().equals(Const.SYS_TYPE)) {
            LOGGER.warn("[" + ProtocolCodeMsg.SYS_TYPE_NOT_MATCH.getMsg() + "]:sysType---" + head.getSysType());
            return buildErrMsg(result, ProtocolCodeMsg.SYS_TYPE_NOT_MATCH.getMsg(),
                    ProtocolCodeMsg.SYS_TYPE_NOT_MATCH.getCode(), head);
        }
        return null;
    }

    /**
     * 构建错误信息
     *
     * @param result  ProtocolParamDto
     * @param resCode 错误编码
     * @param resMsg  错误信息
     * @return ProtocolParamDto
     */
    private ProtocolParamDto buildErrMsg(ProtocolParamDto result,
                                         String resCode,
                                         String resMsg,
                                         ProtocolParamDto.HeadBean headBean) {
        ProtocolParamDto.Message message = new ProtocolParamDto.Message();
        Map<String, Object> resultBody = new HashMap<>(2);
        headBean.setResCode(ProtocolCodeMsg.SUCCESS.getCode());
        headBean.setMessage(ProtocolCodeMsg.SUCCESS.getMsg());
        resultBody.put(Const.RES_CODE_KEY, resCode);
        resultBody.put(Const.RESULT_MSG_KEY, resMsg);
        message.setHead(headBean);
        message.setBody(resultBody);
        result.setMessage(message);
        return result;
    }
}
