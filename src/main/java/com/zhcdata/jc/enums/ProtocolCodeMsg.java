package com.zhcdata.jc.enums;

/**
 * CopyRight (c)1999-2017 : zhcw.com
 * Project : win-the-goods
 * Comments : 错误编码和错误消息
 * JDK version : JDK1.8
 * Create Date : 2019-06-19 13:43
 *
 * @author : Yore
 * @version : 1.0
 * @since : 1.0
 */
public enum ProtocolCodeMsg {

    SERVER_BUSY("102001", "服务器繁忙"),
    JSON_PARS_ERROR("102002", "JSON解析错误"),
    SYS_TYPE_NOT_MATCH("102003", "系统类型不匹配"),
    MESSAGEID_NOT_ASSIGNED("102004", "messageID未赋值"),
    TIMESTAMP_NOT_ASSIGNED("102005", "timeStamp未赋值"),
    MESSENGERID_NOT_ASSIGNED("102006", "messengerID未赋值"),
    TRANSACTIONTYPE_NOT_ASSIGNED("102007", "transactionType未赋值"),
    DIGEST_VERIFICATION_FAILED("102008", "digest校验不通过"),
    SRC_NOT_ASSIGNED("102009", "src未赋值"),
    SYSTYPE_NOT_ASSIGNED("102010", "sysType未赋值"),
    TT_NOT_ILLEGAL("102011", "协议号不合法"),
    TT_NOT_EXIST("102012", "协议号不存在"),
    REQUEST_TRANS_MESSAGE_NULL("102013", "请求参数为空"),
    MESSAGE_NULL("102014", "Message为空"),
    HEAD_NULL("102015", "Head为空"),
    PARAMETER_STRING_TO_OBJECT_FAILED("102016", "参数串转换对象失败"),

    PROVINCE_CODE_NOT_EXIST("102017", "分省编码为空"),
    LOTTEYR_NAME_NOT_EXIST("102018", "彩种名称为空"),
    ISSUE_NOT_EXIST("102019", "期次为空"),
    TIME_ID_NOT_EXIST("102020", "timeId不合法"),
    USER_ID_NOT_EXIST("102021", "userId不合法"),
    VALUE_NOT_EXIST("102022", "value为空"),
    TYPE_NOT_EXIST("102023", "type为空"),
    TYPE_NOT_ILLEGAL("102024", "type不合法"),
    PAGE_NO_NOT_ILLEGAL("102025", "页码不合法"),

    USER_INFO_NO_EXIST("102047","用户信息不存在"),


    PAY_TYPE_ILLEGAL("040801", "支付方式非法"),
    USER_PAY_ERROR("040802", "购买失败，请重试"),
    USER_RECHARGE_ERROR("040803", "充值失败，请重试"),
    ACCOUNT_RECHARGE_MONEY("040804", "充值金额不合法"),
    ORDER_ID_NOT_EXIST("040805", "orderId为空"),
    ORDER_ID_NOT_HAVE("040806", "查询订单不存在"),
    LUCKY_NUM_INSERT_ERROR("040807","幸运号码生成失败"),

    USER_WIN_EXCHANGE_NOT_EXIST("041601","该奖品已领取，无法重复提交"),
    USER_WIN_EXCHANGE_ERROR("041602","兑换失败"),

    ITEM_ISSUE_CAN_NOT_JOIN("030601","该奖品暂无法参与"),
    ITEM_BUY_TIME_NUMBER_ILLEGAL("030602","参与人次非法"),
    ITEM_BUY_JOIN_FAIL("030603","参与失败"),
    ITEM_BUY_MONEY_NOT_ENOUGH("030604","幸运币不足，请获取后再参与！"),
    ITEM_BUY_PROVINCE_GET_ERROR("030605","省份获取失败，请重试"),
    ITEM_BUY_CODE_CREATE_ERROR("030606","奖品幸运码生产失败"),
    ITEM_ISSUE_REMAIN_NOT_ENOUGH("030607","剩余幸运码不足"),
    ITEM_JOIN_PEOPLE_ENOUGH("030608","该期人次已满 是否参与最新一期"),
    ITEM_BUY_ENOUGH("030609","参与失败，请重试！"),

    SUCCESS("000000", "成功"),
    FAIL("444444", "失败"),
    DATABASE_EXCEPTION("888888", "数据异常"),


    ;
    private String code;

    private String msg;

    ProtocolCodeMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
