package com.zhcdata.jc.tools;

import java.util.Arrays;
import java.util.List;

/**
 * CopyRight (c)1999-2017 : zhcw.com
 * Project : zhcw-framework
 * Comments : 时间格式化样式常量定义
 * JDK version : JDK1.8
 * Create Date : 2017/7/7 18:48
 *
 * @author : Watson W
 * @version : 1.0
 * @since : 1.0
 */
public interface Const {

    String PROVINCE_CODE = "provinceCode";

    String USER_ID = "userId";

    String ALIPAY_AMOUNT_LIMIT = "20";

    String TYPE = "type";

    String VALUE_ID = "valueId";

    int DEFAULT_PAGE_AMOUNT = 20;
    String PAGE_NO = "pageNo";

    String LOTTERY_NAME = "lotteryName";

    String ISSUE = "issue";

    String MONEY = "money";

    String WX_OFFLINE_TYPE = "2";

    String ALIPAY_TYPE = "1";

    List<String> MUNICIPALITY_LIST = Arrays.asList("中国", "北京", "天津", "重庆", "上海");

    String SYS_RECHARGE_TYPE = "DEPO_F_JK_SYS";

    String SYS_PAY_TYPE = "PAY_F_KK_XJ";

    String OPR_ACC_SYS_TYPE = "F";

    String ACC_ORDER_EXIST_CODE = "010401";

    String ACCOUNT_BALANCE_INSUFFICIENT = "101025";

    String TIME_ID = "timeId";

    String SYS_TYPE = "WTG";

    String SUCCESS_STR = "success";

    String FAILURE_STR = "failure";

    String ENCODE_UTF8 = "UTF-8";

    /**
     * result code key in result map
     */
    String RES_CODE_KEY = "resCode";

    /**
     * result message key in result map
     */
    String RESULT_MSG_KEY = "message";
    /**
     * dateFormat:yyyy-MM-dd HH:mm:ss
     */
    String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * dateFormat:yyyy-MM-dd HH:mm:ss EE
     */
    String YYYY_MM_DD_HH_MM_SS_EE = "yyyy-MM-dd HH:mm:ss EE";

    /**
     * dateFormat:yyyyMMddHHmmss
     */
    String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /**
     * dateFormat:yyyyMMddHHmmssSSS
     */
    String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

    /**
     * dateFormat:yyyyMMddHHmmssEE
     */
    String YYYYMMDDHHMMSSEE = "yyyyMMddHHmmssEE";

    /**
     * dateFormat:yyyy-MM-dd EE
     */
    String YYYY_MM_DD_EE = "yyyy-MM-dd EE";

    /**
     * dateFormat:yyyy-MM-dd
     */
    String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * dateFormat:yyyyMMdd
     */
    String YYYYMMDD = "yyyyMMdd";

    /**
     * dateFormat:yyyyMMddEE
     */
    String YYYYMMDDEE = "yyyyMMddEE";

    /**
     * dateFormat:HH:mm:ss
     */
    String HH_MM_SS = "HH:mm:ss";

    /**
     * dateFormat:HHmmss
     */
    String HHMMSS = "HHmmss";

    /**
     * dateFormat:yyyyMM
     */
    String YYYYMM = "yyyyMM";

    /**
     * dateFormat:EE (星期)
     */
    String EE = "EE";

    /**
     * dateFormat:yyyy-MM-dd'T'HH:mm:ss (ISO8601) example:2014-10-27T19:10:28.000+08:00
     */
    String YYYY_MM_DD_T_HH_MM_SS_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * dateFormat:yyyy-MM-dd'T'HH:mm:ss.SSSZ (ISO8601) example:2014-10-27T19:08:55.381+08:00
     */
    String YYYY_MM_DD_T_HH_MM_SS_SSSZ_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    /**
     * dateFormat:yyyy-MM-dd'T'HH:mm:ss.SSSZ (ISO8601) example:
     */
    String YYYY_MM_DD_T_HH_MM_SS_SSSXXX_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZZZ";

    /**
     * @author : cuishuai
     * Create Date : 2019-06-21 9:00
     * start
     * ======================================================================================================
     */
    String ORDER_ID = "orderId";
    String ISSUE_ID = "issueId";
    String ADDRESS_ID = "addressId";
    String WIN_ID = "winId";
    String CELL = "cell";
    String ACCNAME = "accName";
    String PROVINCENAME = "provinceName";
    String PROVINCECODE = "provinceCode";
    String CITYNAME = "cityName";
    String AREA = "area";
    String ADDRESS = "address";
    String IS_DEFAULT = "isDefault";
    String ACCEPT_ADDRESS_ID = "acceptAddressId";
    String AREA_CODE = "areaCode";
    String ONE_FOUR_SFC = "14场胜负彩";
    String SINGLE_R_SPF = "单场让球胜平负";
    String JC_FOOTBALL = "竞彩足球";
    /**
     * end
     * ======================================================================================================
     */


}
