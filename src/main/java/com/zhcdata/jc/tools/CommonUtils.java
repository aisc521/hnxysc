package com.zhcdata.jc.tools;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.zhcdata.jc.dto.*;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springside.modules.utils.number.NumberUtil;
import org.springside.modules.utils.time.ClockUtil;
import org.springside.modules.utils.time.DateFormatUtil;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Title:
 * Description:公共工具类
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司-互联网事业部
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/3/20 11:31
 */
@Component
public class CommonUtils {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);

    @Value("${custom.url.acc}")
    private String accUrl;

    public Map<String, Integer> AWARD_MAP = new HashMap<>();

    public Map<String, String> DEFAULT_MAP = new HashMap<>();

    {
        AWARD_MAP.put("TC_DLT_Q_NUM", 35);
        AWARD_MAP.put("TC_DLT_H_NUM", 12);
        AWARD_MAP.put("FC_SSQ_Q_NUM", 33);
        AWARD_MAP.put("FC_SSQ_H_NUM", 16);
        DEFAULT_MAP.put("payType", Const.ALIPAY_TYPE + "," + Const.WX_OFFLINE_TYPE);
    }


    private static Object lockObj = "lockerOrder";

    private static long orderNumCount = 0L;

    private int maxPerMSECSize = 1000;


    /**
     * 校验请求参数是否存在
     *
     * @param map      响应结果
     * @param paramMap 参数map
     * @return true 通过校验  false 未通过校验
     */
    public boolean validParamExist(Map<String, Object> map, Map<String, String> paramMap, String key, ProtocolCodeMsg protocolCodeMsg) {
        String value = paramMap.get(key);
        if (Strings.isNullOrEmpty(value)) {
            errorMessageToMap(map, protocolCodeMsg);
            return false;
        }
        return true;
    }

    /**
     * 校验请求参数是否存在
     *
     * @param map          响应结果
     * @param paramMap     参数map
     * @param defaultParam 参数对应值校验
     * @return true 通过校验  false 未通过校验
     */
    public boolean validParamExist(Map<String, Object> map, Map<String, String> paramMap, String key,
                                   ProtocolCodeMsg protocolCodeMsg, boolean defaultParam) {
        String value = paramMap.get(key);
        if (Strings.isNullOrEmpty(value)) {
            errorMessageToMap(map, protocolCodeMsg);
            return false;
        }
        if (defaultParam) {
            String s = DEFAULT_MAP.get(key);
            if (!Strings.isNullOrEmpty(s) && !s.contains(value)) {
                errorMessageToMap(map, protocolCodeMsg);
                return false;
            }
        }
        return true;
    }

    /**
     * 校验请求参数是否存在
     *
     * @param map      响应结果
     * @param paramMap 参数map
     * @return true 通过校验  false 未通过校验
     */
    public boolean validParamExistOrNoNum(Map<String, Object> map, Map<String, String> paramMap, String key, ProtocolCodeMsg protocolCodeMsg) {
        String value = paramMap.get(key);
        if (Strings.isNullOrEmpty(value) || !NumberUtil.isNumber(value)) {
            errorMessageToMap(map, protocolCodeMsg);
            return false;
        }
        return true;
    }

    /**
     * 校验请求参数是否存在
     *
     * @param map      响应结果
     * @param paramMap 参数map
     * @return true 通过校验  false 未通过校验
     */
    public boolean validParamExistAndNumber(Map<String, Object> map, Map<String, String> paramMap, String key, ProtocolCodeMsg protocolCodeMsg) {
        String value = paramMap.get(key);
        if (Strings.isNullOrEmpty(value) || !NumberUtil.isNumber(value)) {
            errorMessageToMap(map, protocolCodeMsg);
            return false;
        }
        return true;
    }
    /**
     * 校验请求参数是否存在且是正整数
     *
     * @param map      响应结果
     * @param paramMap 参数map
     * @return true 通过校验  false 未通过校验
     */
    public boolean validParamExistOrNoInteger(Map<String, Object> map, Map<String, String> paramMap, String key,
                                              String limit, ProtocolCodeMsg protocolCodeMsg) {
        String value = paramMap.get(key);
        if (Strings.isNullOrEmpty(value) || !NumberUtil.isNumber(value)
                || new BigDecimal(value).compareTo(new BigDecimal(limit)) < 0 || !isIntegerValue(new BigDecimal(value))) {
            errorMessageToMap(map, protocolCodeMsg);
            return false;
        }
        return true;
    }

    /**
     * 设置消息
     *
     * @param map
     * @param protocolCodeMsg
     */
    public void errorMessageToMap(Map<String, Object> map, ProtocolCodeMsg protocolCodeMsg) {
        map.put(Const.RES_CODE_KEY, protocolCodeMsg.getCode());
        map.put(Const.RESULT_MSG_KEY, protocolCodeMsg.getMsg());
    }

    public void errorMessageToMap(Map<String, Object> map, String resCode, String msg) {
        map.put(Const.RES_CODE_KEY, resCode);
        map.put(Const.RESULT_MSG_KEY, msg);
    }

    /**
     * 订单号生
     * - //充值
     */
    public String createOrderId(String type) {
        //年月日时分秒毫秒+椭机2位数+微秒三位
        String orderId = DateFormatUtil.formatDate("yyyyMMddHHmmssSSS", ClockUtil.currentDate()) + "-";
        String rd = String.valueOf(new Random().nextInt(99));
        if (rd.length() == 1) {
            rd = "0" + rd;
        }
        orderId = orderId + rd + (System.nanoTime() + "").substring(7, 10);
        if (!Strings.isNullOrEmpty(type)) {
            orderId = type + "-" + orderId;
        }
        return orderId;
    }


    /**
     * 奖上奖订单号生成 不能超过32位
     *
     * @param
     * @return
     */

    public String createJsjOrderId(String type) {
        //年月日时分秒毫秒+椭机2位数+微秒三位
        String orderId = DateFormatUtil.formatDate("yyyyMMddHHmmssSSS", ClockUtil.currentDate()) + "-";
        String rd = String.valueOf(new Random().nextInt(99));
        if (rd.length() == 1) {
            rd = "0" + rd;
        }
        orderId = orderId + rd + (System.nanoTime() + "").substring(7, 10);
        if (!Strings.isNullOrEmpty(type)) {
            orderId = type + "-" + orderId;
        }
        //判断是否超过32位
        if (orderId.length() >= 32) {
            orderId.substring(0, 32);
        }
        return orderId;
    }


    public String payTypeToPayInfo(String payType) {
        String payInfo;
        switch (payType) {
            case Const.ALIPAY_TYPE:
                payInfo = "支付宝支付";
                break;
            case Const.WX_OFFLINE_TYPE:
                payInfo = "微信支付";
                break;
            default:
                payInfo = null;
                break;
        }
        return payInfo;
    }

    public String payTypeToNewPayInfo(String payType) {
        String payInfo;
        switch (payType) {
            case "DC_ALIPAY_HDA":
                payInfo = "支付宝支付";
                break;
            case "DC_WECHAT_OFFLINE":
                payInfo = "微信线下支付";
                break;
            case "DC_ALIPAY_OFFLINE":
                payInfo = "支付宝线下支付";
                break;
            case "DC_F_EXCHANGE":
                payInfo = "奖品兑换幸运币";
                break;
            case "DC_PEOPLE_OPR":
                payInfo = "手动赠送";
                break;
            case "DC_ACT_OPR":
                payInfo = "活动赠送";
                break;
            default:
                payInfo = null;
                break;
        }
        return payInfo;
    }

    public String payTypeRechange(String payType) {
        String pay;
        switch (payType) {
            case Const.ALIPAY_TYPE:
                pay = "DC_ALIPAY_HDA";
                break;
            case Const.WX_OFFLINE_TYPE:
                pay = "DC_WECHAT_OFFLINE";
                break;
            case "3":
                pay = "DC_F_EXCHANGE";
                break;
            default:
                pay = null;
                break;
        }
        return pay;
    }

    /**
     * 根据支付方式获取账户中心充值trade
     *
     * @param payType
     * @return
     */
    public String payTypeToAccDepoTrade(String payType) {
        String trade;
        switch (payType) {
            case Const.ALIPAY_TYPE:
                trade = "DEPO_F_SQ_ZFBAP";
                break;
            case Const.WX_OFFLINE_TYPE:
                trade = "DEPO_F_JK_SYS";
                break;
            case "DC_ALIPAY_HDA":
                trade = "DEPO_F_SQ_ZFBAP";
                break;
            case "DC_WECHAT_OFFLINE":
                trade = "DEPO_F_JK_SYS";
                break;
            default:
                trade = null;
                break;
        }
        return trade;
    }

    /**
     * 根据支付方式获取账户中心充值trade
     *
     * @param payType
     * @return
     */
    public String payTypeToAccDepoDicCodeTrade(String payType) {
        String trade;
        switch (payType) {
            case Const.ALIPAY_TYPE:
                //好店啊支付宝
                trade = "DC_ALIPAY_HDA";
                break;
            case Const.WX_OFFLINE_TYPE:
                trade = "DC_WECHAT_OFFLINE";
                break;
            default:
                trade = null;
                break;
        }
        return trade;
    }

    private static final String APPKEY = "zhcwBody!@#";

    public String bodyMd5(Map<String, Object> body) {
        body.put("key", APPKEY);
        TreeMap<String, Object> tmap = new TreeMap<>();
        tmap.putAll(body);
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : tmap.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        if (sb.length() > 1) {
            sb.delete(sb.length() - 1, sb.length());
        }
        String md5 = DigestUtils.md5Hex(sb.toString());
        LOGGER.warn(md5);

        return md5;
    }

    /**
     * 替换号码的分隔符
     *
     * @param num
     * @param delChar  输入时的连接符号，默认为","
     * @param joinChar 输出时的连接符号，默认为","
     * @return
     */
    public String replaceNumSplit(String num, String delChar, String joinChar) {
        String[] strings = StringUtils.delimitedListToStringArray(num, delChar);
        if (joinChar == null) {
            joinChar = ",";
        }
        Joiner joiner = Joiner.on(joinChar).skipNulls();
        num = joiner.join(strings);
        return num;
    }


    /**
     * 定义生成随机数并且装入集合容器的方法
     *
     * @param total 生成随机数的个数
     * @param start 生成随机数的值的范围最小值为start(包含start)
     * @param end   值得范围最大值为end(不包含end)
     *              可取值范围可表示为[start,end)
     * @return
     */
    public Set<String> getRandomNumSet(int total, int start, int end, boolean sort) {
        //1.创建集合容器对象
        Set<String> set;
        if (sort) {
            set = new TreeSet<>();
        } else {
            set = new HashSet<>();
        }
        //2.创建Random对象
        Random r = new Random();
        //循环将得到的随机数进行判断，如果随机数不存在于集合中，则将随机数放入集合中，如果存在，则将随机数丢弃不做操作，进行下一次循环，直到集合长度等于total
        while (set.size() != total) {
            int num = r.nextInt(end - start) + start;
            set.add(num < 10 ? "0" + num : num + "");
        }
        return set;
    }

    /**
     * 判断是否为整数
     * 51.00 true
     * 50.00 true
     * 50.10 false
     *
     * @param decimalVal
     * @return
     */
    public boolean isIntegerValue(BigDecimal decimalVal) {
        return decimalVal.scale() <= 0 || decimalVal.stripTrailingZeros().scale() <= 0;
    }


    /**
     * 解析有关其他系统返回的字符串
     * 如果成功返回boyMap
     */
    public Map<String, Object> handleJson(String payJson) {
        Gson gson = new Gson();
        ProtocolParamDto protocolParamDto = gson.fromJson(payJson, ProtocolParamDto.class);
        if (protocolParamDto != null && protocolParamDto.getMessage() != null) {
            Map<String, Object> bodyMap = protocolParamDto.getMessage().getBody();
            if (bodyMap != null) {
                return bodyMap;
            }
        }
        return null;
    }

    /**
     * 调用账户中心查询
     * @param paramsMap
     * @param src
     * @param transactionType
     * @return
     * @throws Exception
     */
    public Map<String, Object> queryDataFromAcc(Map<String, Object> paramsMap, String transactionType, String src) throws Exception {
        String returnStrJson = HttpUtils.httpPost(accUrl, paramsMap, transactionType, "UTF-8", src, null, "A");
        Map<String, Object> resultMap = handleJson(returnStrJson);
        if (resultMap != null) {
            return resultMap;
        }
        return null;
    }

    /**
     * 彩种信息转换
     * @param money
     * @param
     * @return
     */
    public  String lotterRemark(String money,String code){
        String remark = "其他";
        if("1".equals(money) && "FC_SSQ".equals(code)){
            remark = "12+3";
        }
        if("5".equals(money) && "FC_SSQ".equals(code)){
            remark = "8+2";
        }
        if("10".equals(money) && "FC_SSQ".equals(code)){
            remark = "6+1";
        }
        if("1".equals(money) && "TC_DLT".equals(code)){
            remark = "10+4";
        }
        if("5".equals(money) && "TC_DLT".equals(code)){
            remark = "8+3";
        }
        if("10".equals(money) && "TC_DLT".equals(code)){
            remark = "5+2";
        }
        if("ALL".equals(code)){
            remark = "其他";
        }
        return remark;
    }


    public String getSE() {
        String startDate = "";
        String endDate = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dt = df.format(new Date());
        String hour = dt.substring(11, 13);
        if (Integer.parseInt(hour) > 10) {
            Date date = new Date();//获取当前时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            startDate = df.format(calendar.getTime()).substring(0, 10) + " 11:00:00";

            calendar.add(Calendar.DAY_OF_MONTH, 1);
            endDate = df.format(calendar.getTime()).substring(0, 10) + " 11:00:00";

            //过11点,过了11:00点则不显示昨天的赛果只显示今天的对阵
        } else {
            Date date = new Date();//获取当前时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            endDate = df.format(calendar.getTime()).substring(0, 10) + " 11:00:00";

            calendar.add(Calendar.DAY_OF_MONTH, -1);
            startDate = df.format(calendar.getTime()).substring(0, 10) + " 11:00:00";
            //未过11点,每天中午11:00前都显示昨日比赛结果
        }
        return startDate + "," + endDate;
    }

    public String getSEBd() {
        String startDate = "";
        String endDate = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dt = df.format(new Date());
        String hour = dt.substring(11, 13);
        if (Integer.parseInt(hour) > 9) {
            Date date = new Date();//获取当前时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            startDate = df.format(calendar.getTime()).substring(0, 10) + " 09:59:59";

            calendar.add(Calendar.DAY_OF_MONTH, 1);
            endDate = df.format(calendar.getTime()).substring(0, 10) + " 09:59:59";

            //过11点,过了11:00点则不显示昨天的赛果只显示今天的对阵
        } else {
            Date date = new Date();//获取当前时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            endDate = df.format(calendar.getTime()).substring(0, 10) + " 09:59:59";

            calendar.add(Calendar.DAY_OF_MONTH, -1);
            startDate = df.format(calendar.getTime()).substring(0, 10) + " 09:59:59";
            //未过11点,每天中午11:00前都显示昨日比赛结果
        }
        return startDate + "," + endDate;
    }

    /**
     * 与支付系统的tradeType转换
     */
    public String getPayTradType(int payType,String sysType) {
        String tradType = "";
        switch (payType) {
            case 0:
                tradType = "PAY_"+sysType+"_DJ_XJ";
                break; //竞彩余额支付
            case 3:
                tradType = "PAY_"+sysType+"_DJ_XJ";
                break; //红包支付
            case 20:
                tradType = "DEPOPAY_"+sysType+"_SQ_WXNATIVE";
                break; //微信Native
            case 21:
                tradType = "DEPOPAY_"+sysType+"_SQ_ZFBAP";
                break; //好店啊支付宝支付
            case 22:
                tradType = "DEPOPAY_"+sysType+"_SQ_WXH5";
                break; //微信Native
            default:
                break;
        }
        return tradType;
    }


    public String JsLz(ExpertInfo info){
        String lz = "";
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getLzNow())){
            info.setLzNow("0");
        }
        Integer lh = new BigDecimal(info.getLzNow()).intValue();
        if(lh >= 5){
            lz = info.getLzNow();
            return lz;
        }

        if(info.getTrend().length()==1){
            lz = info.getLzNow();
            return lz;
        }else if(info.getTrend().length()==2){
            lz = info.getLzNow();
            return lz;
        }else if(info.getTrend().length()==3){
            lz = info.getLzNow();
            return lz;
        }else if(info.getTrend().length()==4){
            lz = info.getLzNow();
            return lz;
        }else if(info.getTrend().length()==5){
            lz = info.getLzNow();
            return lz;
        }


        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 9){//10中9
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getNineZ())){
            info.setNineZ("0");
        }
        if(new BigDecimal(info.getNineZ()).intValue() == 8){//9中8
            lz = "9中" + info.getNineZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getEightZ())){
            info.setEightZ("0");
        }
        if(new BigDecimal(info.getEightZ()).intValue() == 7){//8中7
            lz = "8中" + info.getEightZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getSevenZ())){
            info.setSevenZ("0");
        }
        if(new BigDecimal(info.getSevenZ()).intValue() == 6){//7中6
            lz = "7中" + info.getSevenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getSixZ())){
            info.setSixZ("0");
        }
        if(new BigDecimal(info.getSixZ()).intValue() == 5){//6中5
            lz = "6中" + info.getSixZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getFiveZ())){
            info.setFiveZ("0");
        }
        if(new BigDecimal(info.getFiveZ()).intValue() == 4){//五中四
            lz = "5中" + info.getFiveZ();
            return lz;
        }

        if(org.apache.commons.lang3.StringUtils.isBlank(info.getReturnSevenDays())){
            info.setReturnSevenDays("0");
        }

        if(new BigDecimal(info.getReturnSevenDays()).intValue() > 100){//七天回报率大于100
            lz = "回报率:" + new BigDecimal(info.getReturnSevenDays()).intValue() + "%";
            return lz;
        }

        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 8){//10中8
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getNineZ())){
            info.setNineZ("0");
        }
        if(new BigDecimal(info.getNineZ()).intValue() == 7){//9中7
            lz = "9中" + info.getNineZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getEightZ())){
            info.setEightZ("0");
        }
        if(new BigDecimal(info.getEightZ()).intValue() == 6){//8中6
            lz = "8中" + info.getEightZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getSevenZ())){
            info.setSevenZ("0");
        }
        if(new BigDecimal(info.getSevenZ()).intValue() == 5){//7中5
            lz = "7中" + info.getSevenZ();
            return lz;
        }



        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 7){//10中7
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 6){//10中6
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 5){//10中5
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(lh == 2){
            lz = info.getLzNow();
            return lz;
        }
        if(lh == 3){
            lz = info.getLzNow();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getThreeZ())){
            info.setThreeZ("0");
        }
        if(new BigDecimal(info.getThreeZ()).intValue() == 2){//3中2
            lz = "3中" + info.getThreeZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getFourZ())){
            info.setFourZ("0");
        }
        if(new BigDecimal(info.getFourZ()).intValue() == 3){//4中3
            lz = "3中" + info.getFourZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getLzBig())){
            info.setLzBig("0");
        }
        if(new BigDecimal(info.getLzBig()).intValue() >= 8){//历史8连红以及以上
            lz = "历史" + info.getLzBig() + "连红";
            return lz;
        }
        return lz;
    }

    public String JsLz1(PurchasedPlanDto  info){
        String lz = "";
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getLz())){
            info.setLz("0");
        }
        Integer lh = new BigDecimal(info.getLz()).intValue();
        if(lh >= 5){
            lz = info.getLz();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 9){//10中9
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getNineZ())){
            info.setNineZ("0");
        }
        if(new BigDecimal(info.getNineZ()).intValue() == 8){//9中8
            lz = "9中" + info.getNineZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getEightZ())){
            info.setEightZ("0");
        }
        if(new BigDecimal(info.getEightZ()).intValue() == 7){//8中7
            lz = "8中" + info.getEightZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getSevenZ())){
            info.setSevenZ("0");
        }
        if(new BigDecimal(info.getSevenZ()).intValue() == 6){//7中6
            lz = "7中" + info.getSevenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getSixZ())){
            info.setSixZ("0");
        }
        if(new BigDecimal(info.getSixZ()).intValue() == 5){//6中5
            lz = "6中" + info.getSixZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getFiveZ())){
            info.setFiveZ("0");
        }
        if(new BigDecimal(info.getFiveZ()).intValue() == 4){//五中四
            lz = "5中" + info.getFiveZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getReturnSevenDays())){
            info.setReturnSevenDays("0");
        }


        if(new BigDecimal(info.getReturnSevenDays()).intValue() > 100){//七天回报率大于100
            lz = "回报率:" + new BigDecimal(info.getReturnSevenDays()).intValue() + "%";
            return lz;
        }

        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 8){//10中8
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getNineZ())){
            info.setNineZ("0");
        }
        if(new BigDecimal(info.getNineZ()).intValue() == 7){//9中7
            lz = "9中" + info.getNineZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getEightZ())){
            info.setEightZ("0");
        }
        if(new BigDecimal(info.getEightZ()).intValue() == 6){//8中6
            lz = "8中" + info.getEightZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getSevenZ())){
            info.setSevenZ("0");
        }
        if(new BigDecimal(info.getSevenZ()).intValue() == 5){//7中5
            lz = "7中" + info.getSevenZ();
            return lz;
        }

        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 7){//10中7
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 6){//10中6
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 5){//10中5
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(lh == 2){
            lz = info.getLz();
            return lz;
        }
        if(lh == 3){
            lz = info.getLz();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getThreeZ())){
            info.setThreeZ("0");
        }
        if(new BigDecimal(info.getThreeZ()).intValue() == 2){//3中2
            lz = "3中" + info.getThreeZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getFourZ())){
            info.setFourZ("0");
        }
        if(new BigDecimal(info.getFourZ()).intValue() == 3){//4中3
            lz = "3中" + info.getFourZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getLzBig())){
            info.setLzBig("0");
        }
        if(new BigDecimal(info.getLzBig()).intValue() >= 8){//历史8连红以及以上
            lz = "历史" + info.getLzBig() + "连红";
            return lz;
        }
        return lz;
    }


    public String JsLz2(LatestPlanReminderDto info){
        String lz = "";
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getLz())){
            info.setLz("0");
        }
        Integer lh = new BigDecimal(info.getLz()).intValue();
        if(lh >= 5){
            lz = info.getLz();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 9){//10中9
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getNineZ())){
            info.setNineZ("0");
        }
        if(new BigDecimal(info.getNineZ()).intValue() == 8){//9中8
            lz = "9中" + info.getNineZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getEightZ())){
            info.setEightZ("0");
        }
        if(new BigDecimal(info.getEightZ()).intValue() == 7){//8中7
            lz = "8中" + info.getEightZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getSevenZ())){
            info.setSevenZ("0");
        }
        if(new BigDecimal(info.getSevenZ()).intValue() == 6){//7中6
            lz = "7中" + info.getSevenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getSixZ())){
            info.setSixZ("0");
        }
        if(new BigDecimal(info.getSixZ()).intValue() == 5){//6中5
            lz = "6中" + info.getSixZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getFiveZ())){
            info.setFiveZ("0");
        }
        if(new BigDecimal(info.getFiveZ()).intValue() == 4){//五中四
            lz = "5中" + info.getFiveZ();
            return lz;
        }

        if(org.apache.commons.lang3.StringUtils.isBlank(info.getReturnSevenDays())){
            info.setReturnSevenDays("0");
        }

        if(new BigDecimal(info.getReturnSevenDays()).intValue() > 100){//七天回报率大于100
            lz = "回报率:" + new BigDecimal(info.getReturnSevenDays()).intValue() + "%";
            return lz;
        }


        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 8){//10中8
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getNineZ())){
            info.setNineZ("0");
        }
        if(new BigDecimal(info.getNineZ()).intValue() == 7){//9中7
            lz = "9中" + info.getNineZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getEightZ())){
            info.setEightZ("0");
        }
        if(new BigDecimal(info.getEightZ()).intValue() == 6){//8中6
            lz = "8中" + info.getEightZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getSevenZ())){
            info.setSevenZ("0");
        }
        if(new BigDecimal(info.getSevenZ()).intValue() == 5){//7中5
            lz = "7中" + info.getSevenZ();
            return lz;
        }

        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 7){//10中7
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 6){//10中6
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 5){//10中5
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(lh == 2){
            lz = info.getLz();
            return lz;
        }
        if(lh == 3){
            lz = info.getLz();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getThreeZ())){
            info.setThreeZ("0");
        }
        if(new BigDecimal(info.getThreeZ()).intValue() == 2){//3中2
            lz = "3中" + info.getThreeZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getFourZ())){
            info.setFourZ("0");
        }
        if(new BigDecimal(info.getFourZ()).intValue() == 3){//4中3
            lz = "3中" + info.getFourZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getLzBig())){
            info.setLzBig("0");
        }
        if(new BigDecimal(info.getLzBig()).intValue() >= 8){//历史8连红以及以上
            lz = "历史" + info.getLzBig() + "连红";
            return lz;
        }
        return lz;
    }

    public String JsLz2(PlanResult1 info){
        String lz = "";
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getLz())){
            info.setLz("0");
        }
        Integer lh = new BigDecimal(info.getLz()).intValue();
        if(lh >= 5){
            lz = info.getLz();
            return lz;
        }

        if(info.getTrend().length()==1){
            lz = info.getLz();
            return lz;
        }else if(info.getTrend().length()==2){
            lz = info.getLz();
            return lz;
        }else if(info.getTrend().length()==3){
            lz = info.getLz();
            return lz;
        }else if(info.getTrend().length()==4){
            lz = info.getLz();
            return lz;
        }else if(info.getTrend().length()==5){
            lz = info.getLz();
            return lz;
        }

        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 9){//10中9
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getNineZ())){
            info.setNineZ("0");
        }
        if(new BigDecimal(info.getNineZ()).intValue() == 8){//9中8
            lz = "9中" + info.getNineZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getEightZ())){
            info.setEightZ("0");
        }
        if(new BigDecimal(info.getEightZ()).intValue() == 7){//8中7
            lz = "8中" + info.getEightZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getSevenZ())){
            info.setSevenZ("0");
        }
        if(new BigDecimal(info.getSevenZ()).intValue() == 6){//7中6
            lz = "7中" + info.getSevenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getSixZ())){
            info.setSixZ("0");
        }
        if(new BigDecimal(info.getSixZ()).intValue() == 5){//6中5
            lz = "6中" + info.getSixZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getFiveZ())){
            info.setFiveZ("0");
        }
        if(new BigDecimal(info.getFiveZ()).intValue() == 4){//五中四
            lz = "5中" + info.getFiveZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getReturnSevenDays())){
            info.setReturnSevenDays("0");
        }

        if(new BigDecimal(info.getReturnSevenDays()).intValue() > 100){//七天回报率大于100
            lz = "回报率:" + new BigDecimal(info.getReturnSevenDays()).intValue() + "%";
            return lz;
        }


        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 8){//10中8
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getNineZ())){
            info.setNineZ("0");
        }
        if(new BigDecimal(info.getNineZ()).intValue() == 7){//9中7
            lz = "9中" + info.getNineZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getEightZ())){
            info.setEightZ("0");
        }
        if(new BigDecimal(info.getEightZ()).intValue() == 6){//8中6
            lz = "8中" + info.getEightZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getSevenZ())){
            info.setSevenZ("0");
        }
        if(new BigDecimal(info.getSevenZ()).intValue() == 5){//7中5
            lz = "7中" + info.getSevenZ();
            return lz;
        }

        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 7){//10中7
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 6){//10中6
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 5){//10中5
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(lh == 2){
            lz = info.getLz();
            return lz;
        }
        if(lh == 3){
            lz = info.getLz();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getThreeZ())){
            info.setThreeZ("0");
        }
        if(new BigDecimal(info.getThreeZ()).intValue() == 2){//3中2
            lz = "3中" + info.getThreeZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getFourZ())){
            info.setFourZ("0");
        }
        if(new BigDecimal(info.getFourZ()).intValue() == 3){//4中3
            lz = "3中" + info.getFourZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getLzBig())){
            info.setLzBig("0");
        }
        if(new BigDecimal(info.getLzBig()).intValue() >= 8){//历史8连红以及以上
            lz = "历史" + info.getLzBig() + "连红";
            return lz;
        }
        return lz;
    }
    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }

    public void hotExpertLzType(HotExpertDto hotExpertDto,ExpertHotResult hot){
        String orderby = String.valueOf(hotExpertDto.getOrderBy());
        if(orderby.startsWith("1")){//1类
            hot.setType("1");
            hot.setLz(String.valueOf(hotExpertDto.getLzNow()));
            return;
        }
        if(orderby.startsWith("2")){//2类
            hot.setType("2");
            if("209".equals(orderby)){
                hot.setLz("5中4");
            }
            if("208".equals(orderby)){
                hot.setLz("6中5");
            }
            if("207".equals(orderby)){
                hot.setLz("7中6");
            }
            if("206".equals(orderby)){
                hot.setLz("8中7");
            }
            if("205".equals(orderby)){
                hot.setLz("9中8");
            }
            if("204".equals(orderby)){
                hot.setLz("10中9");
            }
            return;
        }
        if(orderby.startsWith("3")){//3类
            hot.setType("2");
            hot.setLz("回报率:" + new BigDecimal(hotExpertDto.getReturnSevenDays()).intValue() + "%");
            return;
        }
        if(orderby.startsWith("4")){//4类
            hot.setType("2");
            if("405".equals(orderby)){
                hot.setLz("10中8");
            }
            if("406".equals(orderby)){
                hot.setLz("9中7");
            }
            if("407".equals(orderby)){
                hot.setLz("8中6");
            }
            if("408".equals(orderby)){
                hot.setLz("7中5");
            }
            return;
        }
        if(orderby.startsWith("5")){//5类
            hot.setType("2");
            if("505".equals(orderby)){
                hot.setLz("10中7");
            }
            if("506".equals(orderby)){
                hot.setLz("10中6");
            }
            if("507".equals(orderby)){
                hot.setLz("10中5");
            }
            return;
        }
        if(orderby.startsWith("6")){//6类
            hot.setType("1");
            hot.setLz(String.valueOf(hotExpertDto.getLzNow()));
            return;
        }
        if(orderby.startsWith("7")){//7类
            hot.setType("2");
            if("702".equals(orderby)){
                hot.setLz("4中3");
            }
            if("703".equals(orderby)){
                hot.setLz("3中2");
            }
            return;
        }
        if(orderby.startsWith("8")){//8类
            hot.setType("1");
            hot.setLz(String.valueOf(hotExpertDto.getLzBig()));
            return;
        }
        if(orderby.startsWith("9")){//9类
            hot.setType("2");
            hot.setLz("历史战绩佳");
            return;
        }

    }

    public String JsLz3(QueryPlanByMatchIdDto info){
        String lz = "";
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getLz())){
            info.setLz("0");
        }
        Integer lh = new BigDecimal(info.getLz()).intValue();
        if(lh >= 5){
            lz = info.getLz();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 9){//10中9
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getNineZ())){
            info.setNineZ("0");
        }
        if(new BigDecimal(info.getNineZ()).intValue() == 8){//9中8
            lz = "9中" + info.getNineZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getEightZ())){
            info.setEightZ("0");
        }
        if(new BigDecimal(info.getEightZ()).intValue() == 7){//8中7
            lz = "8中" + info.getEightZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getSevenZ())){
            info.setSevenZ("0");
        }
        if(new BigDecimal(info.getSevenZ()).intValue() == 6){//7中6
            lz = "7中" + info.getSevenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getSixZ())){
            info.setSixZ("0");
        }
        if(new BigDecimal(info.getSixZ()).intValue() == 5){//6中5
            lz = "6中" + info.getSixZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getFiveZ())){
            info.setFiveZ("0");
        }
        if(new BigDecimal(info.getFiveZ()).intValue() == 4){//五中四
            lz = "5中" + info.getFiveZ();
            return lz;
        }

        if(org.apache.commons.lang3.StringUtils.isBlank(info.getReturnSevenDays())){
            info.setReturnSevenDays("0");
        }

        if(new BigDecimal(info.getReturnSevenDays()).intValue() > 100){//七天回报率大于100
            lz = "回报率:" + new BigDecimal(info.getReturnSevenDays()).intValue() + "%";
            return lz;
        }


        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 8){//10中8
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getNineZ())){
            info.setNineZ("0");
        }
        if(new BigDecimal(info.getNineZ()).intValue() == 7){//9中7
            lz = "9中" + info.getNineZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getEightZ())){
            info.setEightZ("0");
        }
        if(new BigDecimal(info.getEightZ()).intValue() == 6){//8中6
            lz = "8中" + info.getEightZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getSevenZ())){
            info.setSevenZ("0");
        }
        if(new BigDecimal(info.getSevenZ()).intValue() == 5){//7中5
            lz = "7中" + info.getSevenZ();
            return lz;
        }

        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 7){//10中7
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 6){//10中6
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 5){//10中5
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(lh == 2){
            lz = info.getLz();
            return lz;
        }
        if(lh == 3){
            lz = info.getLz();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getThreeZ())){
            info.setThreeZ("0");
        }
        if(new BigDecimal(info.getThreeZ()).intValue() == 2){//3中2
            lz = "3中" + info.getThreeZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getFourZ())){
            info.setFourZ("0");
        }
        if(new BigDecimal(info.getFourZ()).intValue() == 3){//4中3
            lz = "3中" + info.getFourZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getLzBig())){
            info.setLzBig("0");
        }
        if(new BigDecimal(info.getLzBig()).intValue() >= 8){//历史8连红以及以上
            lz = "历史" + info.getLzBig() + "连红";
            return lz;
        }
        return lz;
    }

    public String JsLzExpertInfoDto(ExpertInfoDto info) {
        String lz = "";
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getExpertHitNum())){
            info.setExpertHitNum("0");
        }
        Integer lh = new BigDecimal(info.getExpertHitNum()).intValue();
        if(lh >= 5){
            lz = info.getExpertHitNum();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 9){//10中9
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getNineZ())){
            info.setNineZ("0");
        }
        if(new BigDecimal(info.getNineZ()).intValue() == 8){//9中8
            lz = "9中" + info.getNineZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getEightZ())){
            info.setEightZ("0");
        }
        if(new BigDecimal(info.getEightZ()).intValue() == 7){//8中7
            lz = "8中" + info.getEightZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getSevenZ())){
            info.setSevenZ("0");
        }
        if(new BigDecimal(info.getSevenZ()).intValue() == 6){//7中6
            lz = "7中" + info.getSevenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getSixZ())){
            info.setSixZ("0");
        }
        if(new BigDecimal(info.getSixZ()).intValue() == 5){//6中5
            lz = "6中" + info.getSixZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getFiveZ())){
            info.setFiveZ("0");
        }
        if(new BigDecimal(info.getFiveZ()).intValue() == 4){//五中四
            lz = "5中" + info.getFiveZ();
            return lz;
        }

        if(org.apache.commons.lang3.StringUtils.isBlank(info.getReturnSevenDays())){
            info.setReturnSevenDays("0");
        }

        if(new BigDecimal(info.getReturnSevenDays()).intValue() > 100){//七天回报率大于100
            lz = "回报率:" + new BigDecimal(info.getReturnSevenDays()).intValue() + "%";
            return lz;
        }


        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 8){//10中8
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getNineZ())){
            info.setNineZ("0");
        }
        if(new BigDecimal(info.getNineZ()).intValue() == 7){//9中7
            lz = "9中" + info.getNineZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getEightZ())){
            info.setEightZ("0");
        }
        if(new BigDecimal(info.getEightZ()).intValue() == 6){//8中6
            lz = "8中" + info.getEightZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getSevenZ())){
            info.setSevenZ("0");
        }
        if(new BigDecimal(info.getSevenZ()).intValue() == 5){//7中5
            lz = "7中" + info.getSevenZ();
            return lz;
        }

        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 7){//10中7
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 6){//10中6
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getTenZ())){
            info.setTenZ("0");
        }
        if(new BigDecimal(info.getTenZ()).intValue() == 5){//10中5
            lz = "10中" + info.getTenZ();
            return lz;
        }
        if(lh == 2){
            lz = info.getExpertHitNum();
            return lz;
        }
        if(lh == 3){
            lz = info.getExpertHitNum();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getThreeZ())){
            info.setThreeZ("0");
        }
        if(new BigDecimal(info.getThreeZ()).intValue() == 2){//3中2
            lz = "3中" + info.getThreeZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getFourZ())){
            info.setFourZ("0");
        }
        if(new BigDecimal(info.getFourZ()).intValue() == 3){//4中3
            lz = "3中" + info.getFourZ();
            return lz;
        }
        if(org.apache.commons.lang3.StringUtils.isBlank(info.getLzBig())){
            info.setLzBig("0");
        }
        if(new BigDecimal(info.getLzBig()).intValue() >= 8){//历史8连红以及以上
            lz = "历史" + info.getLzBig() + "连红";
            return lz;
        }
        return lz;
    }
}
