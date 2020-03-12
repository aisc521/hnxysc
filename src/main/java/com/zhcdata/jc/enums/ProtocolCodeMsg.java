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


    //2.1.8  欧赔/亚盘/大小球数据(10200208)
    MATCH_ID_NOT_ASSIGNED("020801", "比赛Id不合法"),
    TYPE_NOT_ASSIGNED("020802", "状态值不合法"),
    TIME_ID_NOT_ASSIGNED("020803", "timeId不合法"),
    SELECT_NOT_ASSIGNED("020804", "select不合法"),
    //2.1.9欧赔/亚盘/大小球详情
    OP_ID_NOT_ASSIGNED("020O901", "opId不合法"),
    //公共号相关参数提示
    BDJC_DAY("090101","日期不能为空"),
    BDJC_FLAG("090102","标识不能为空"),
    //2.1.9欧赔/亚盘/大小球详情
    DATE_NOT_ASSIGNED("021201", "日期不合法"),
    //2.1.3.同初赔分析(10200203)
    PAM_NOT_ASSIGNED("020301", "水位变化不合法"),
    ODDS_COMPANY_NOT_ASSIGNED("020302", "竞彩公司不合法"),
    MATCH_TYPE_NOT_ASSIGNED("020303", "比赛类型matchType不合法"),
    MATCH_YEARS_NOT_ASSIGNED("020304", "比赛年限不合法"),
    CHG_TIMES_NOT_ASSIGNED("020305", "变盘次数不合法"),
    ODDS_WATER_NOT_ASSIGNED("020306", "赔率或水位不合法"),
    GOAL_NOT_ASSIGNED("020307", "盘口不合法"),
    ZK_FLAG_NOT_ASSIGNED("020308", "主客队标识不合法"),

    SUCCESS("000000", "成功"),
    FAIL("444444", "失败"),
    DATABASE_EXCEPTION("888888", "数据异常"),


    UPDATE_FAILE("888888","更新失败"),
    INSERT_FAILE("888888","插入失败"),
    //收藏取消
    COLLECT_MATCHID("020201", "非法的比赛ID"),
    COLLECT_TYPE("020202", "收藏类型不合法"),
    COLLECT_MATCHTIME("020203", "开赛时间不能为空"),
    COLLECT_FLAG_FAIL("020206", "标志非法"),
    COLLECT_COUNT("020204", "收藏已达上限"),
    COLLECT_DB_FAIL("020205", "操作失败，请稍后重试"),
    NO_COLLECT_INFO("020207","无对应的收藏记录"),
    //关注列表
    TYPE_NULL("2001020101","type不能为空"),
    TIME_NULL("2001020101","matchTime不能为空"),
    PAGEAMOUNT_NULL("2001020101","每页总条数不能为空"),
    PAGENO_NULL("2001020101","当前页能为空"),
    EXPERT_ID("1020021701","专家ID不能为空"),
    PLAN_TYPE("1020021901","方案类型不能为空"),
    PLANID_NULL("1020022001","方案ID不能为空"),
    SEARCH_MATCH_TYPE("02171", "比赛类型不合法"),
    SEARCH_MATCH_MATCH_TYPE("02172", "标签代码不合法"),
    SEARCH_MATCH_MATCH_TIME("02173", "时间参数不合法"),
    NO_GANE_TYPE("02174", "无对应的比赛类型信息"),
    LINEUP_MATCHID("020201", "非法的比赛ID"),
    PAY_TYPE("090402","支付方式不能为空"),
    USER_BUY_FAIL("101018", "购买失败，稍后重试!"),
    PLAN_IS_NULL("101019", "无对应的方案信息!"),
    MATCH_IS_NULL("101028", "无对应的比赛信息!"),
    USER_IS_NULL("101020", "无对应的用户信息!"),
    MONEY_IS_NULL("101020", "金额不合法!"),
    PLAN_IS_END("101021", "方案已经结束!"),
    ORDER_IS_NULL("101022", "无对应订单信息!"),
    IS_BUY_PLAN("101022", "此用户此方案已经购买，不能重复购买!"),
    STATUS_IS_NOT_EXIT("101023", "状态信息不合法!"),
    ISSUENUM_IS_NOT_EXIT("101024", "期号不能为空!"),
    USERMENUBUY_MENU_MONEY_ERROR("101025", "不在交易时间范围内!"),
    ZFB_ERROR("20200228", "暂不支持该支付方式"),
    MONEY_ERROR("101026", "支付宝支付金额错误!"),
    FIRST_BUY_ERROR("101027", "首单不支持点播卡支付!"),
    EXPERT_ERROR("101028", "专家信息为空!"),
    CELL_IS_NOY_NULL("101029", "手机号不能为空!"),
    SF14_startIssue("090401","开始期次不能为空"),

    PAY_FLAG("022801","标识不能为空"),

    PAY_FLAG_EXIT("022802","请重新获取支付"),

    COUPON_OVERDUE("022803","该优惠券无法使用，请重新选择！"),

    COUPON_ALREADY_USED("022804","该优惠券无法使用，请重新选择！"),

    COUPON_LOCKING_FAIL("022805","优惠券锁定失败"),

    COUPON_NO_USE("022805","该优惠券无法使用，请重新选择！"),

    COUPONID_NO_NULL("022805","优惠券ID不能为空"),

    TABLETYPE_NO_NULL("020101","tableType类型不能为空"),

    MATCHTYPE_NO_NULL("020101","matchType不能为空"),

    UID_NO_NULL("022002","uid不能为空"),
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
