package com.zhcdata.jc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.zhcdata.db.mapper.TbJcExpertMapper;
import com.zhcdata.db.mapper.TbJcPlanMapper;
import com.zhcdata.db.mapper.TbJcPurchaseDetailedMapper;
import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.db.model.TbJcPurchaseDetailed;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.dto.PurchasedPlanDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.PayService;
import com.zhcdata.jc.service.TbJcPurchaseDetailedService;
import com.zhcdata.jc.service.TbPlanService;
import com.zhcdata.jc.tools.CommonUtils;
import com.zhcdata.jc.tools.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/20 16:51
 */
@Slf4j
@Service
public class TbJcPurchaseDetailedServiceImpl implements TbJcPurchaseDetailedService {
    @Resource
    private TbJcPurchaseDetailedMapper tbJcPurchaseDetailedMapper;

    @Resource
    private TbJcExpertMapper tbJcExpertMapper;
    @Resource
    private CommonUtils commonUtils;

    @Resource
    private TbJcPlanMapper tbJcPlanMapper;

    @Value("${custom.dbPricd}")
    String dbPricd;
    @Value("${custom.description}")
    String description;
    @Value("${custom.productName}")
    String productName ;



    @Override
    public PageInfo<PurchasedPlanDto> queryPurchasedPlanDtoByUserId(int pageNo, int pageAmount,long userId) {
        PageHelper.startPage(pageNo, pageAmount);
        List<PurchasedPlanDto> list = tbJcPurchaseDetailedMapper.queryPurchasedPlanDtoByUserId(userId);
        return new PageInfo<>(list);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> schemePurchase(TbJcPlan tbJcPlan, String userId, Map<String, String> paramMap,PayService payService,Integer list,ProtocolParamDto.HeadBean headBean,String cell) throws BaseException {
        Map<String, Object> result = new HashMap<>();
        try {
            TbJcPurchaseDetailed tbJcPurchaseDetailed = generatedObject(tbJcPlan, userId, paramMap, list, headBean, cell);
            //判断是否是首单
            String price = String.valueOf(tbJcPlan.getPrice());

            if (!Strings.isNullOrEmpty(paramMap.get("couponId"))) {
                tbJcPurchaseDetailed.setFirst("0");           //使用优惠券相当于放弃首单
                if (!paramMap.get("couponStatus").equals("2")) {
                    //未锁定，则锁定操作 已锁定，下一步
                    result = payService.currencyCouponLock(userId, paramMap.get("couponId"), tbJcPurchaseDetailed.getOrderId(), "方案", headBean.getSrc());
                    if (!"000000".equals(result.get("resCode"))) {
                        return result;
                    }
                }

                tbJcPurchaseDetailed.setCouponId(paramMap.get("couponId"));         //优惠券ID
                tbJcPurchaseDetailed.setAccess(paramMap.get("access"));             //优惠券获取方式 0 付费购买  1免费赠送
                tbJcPurchaseDetailed.setCouponType(paramMap.get("type"));           //优惠券类型 0 通用券 1 代金券 2 打折券

                String type = paramMap.get("type");//0 通用券 1 代金券 2 打折券
                if (type.equals("0")) {
                    //通用券
                    //使用优惠券
                    //result = payService.currencyCouponPay(userId, paramMap.get("couponId"), tbJcPurchaseDetailed.getOrderId(), "方案", headBean.getSrc());
                    //if (!"000000".equals(result.get("resCode"))) {
                    //      return result;
                    //}
                    //通用券变成冻结
                    result = payService.currencyCouponFreeze(userId, paramMap.get("couponId"), tbJcPurchaseDetailed.getOrderId(), "方案", headBean.getSrc());
                    if (!"000000".equals(result.get("resCode"))) {
                        return result;
                    }
                    tbJcPurchaseDetailed.setCouponPayMoney(paramMap.get("couponPrice"));//优惠券金额(免费获取,金额0)
                    tbJcPurchaseDetailed.setPayStatus(Long.parseLong("2"));
                    tbJcPurchaseDetailed.setThirdMoney(new BigDecimal(0));
                    tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(price));
                    tbJcPurchaseDetailed.setPayInfo("优惠券支付");
                    tbJcPurchaseDetailed.setPlanPayType("77");
                    tbJcPurchaseDetailed.setDeductionMoney(Long.valueOf(price));
                    insertOrder(tbJcPurchaseDetailed);
                } else if (type.equals("1")) {
                    //计算代金券 相当于满减
                    if ("EQ".equals(paramMap.get("useType"))) {
                        //指定金额
                        if (new BigDecimal(price).compareTo(new BigDecimal(paramMap.get("useNumber"))) != 0) {
                            result.put("resCode", ProtocolCodeMsg.COUPON_NO_USE.getCode());
                            result.put("message", ProtocolCodeMsg.COUPON_NO_USE.getMsg());
                            return result;
                        }
                    } else if ("GT".equals(paramMap.get("useType"))) {
                        //满减
                        if (new BigDecimal(price).compareTo(new BigDecimal(paramMap.get("useNumber"))) > 0) {
                            result.put("resCode", ProtocolCodeMsg.COUPON_NO_USE.getCode());
                            result.put("message", ProtocolCodeMsg.COUPON_NO_USE.getMsg());
                            return result;
                        }
                    }

                    price = String.valueOf(Integer.parseInt(price) - Integer.valueOf(paramMap.get("denomination")));
                    tbJcPurchaseDetailed.setCouponPayMoney(paramMap.get("couponPrice"));    //优惠券金额(免费获取,金额0)
                    //tbJcPurchaseDetailed.setPayStatus(Long.parseLong("2"));               //支付回来有赋值
                    //tbJcPurchaseDetailed.setThirdMoney(new BigDecimal(price));
                    //tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(price));
                    tbJcPurchaseDetailed.setPayInfo("优惠券支付");
                    //tbJcPurchaseDetailed.setPlanPayType("77");
                    //tbJcPurchaseDetailed.setDeductionMoney(price);
                    tbJcPurchaseDetailed.setThirdMoney(new BigDecimal(price));
                    tbJcPurchaseDetailed.setDeductionMoney(Long.valueOf(paramMap.get("denomination"))); //已优惠金额
                } else if (type.equals("2")) {
                    //计算打折券
                    if ("EQ".equals(paramMap.get("useType"))) {
                        //指定金额
                        if (new BigDecimal(price).compareTo(new BigDecimal(paramMap.get("useNumber"))) != 0) {
                            result.put("message", ProtocolCodeMsg.COUPON_NO_USE.getMsg());
                            result.put("resCode", ProtocolCodeMsg.COUPON_NO_USE.getCode());
                            return result;
                        }
                    } else if ("GT".equals(paramMap.get("useType"))) {
                        //满减
                        if (new BigDecimal(price).compareTo(new BigDecimal(paramMap.get("useNumber"))) > 0) {
                            result.put("message", ProtocolCodeMsg.COUPON_NO_USE.getMsg());
                            result.put("resCode", ProtocolCodeMsg.COUPON_NO_USE.getCode());
                            return result;
                        }
                    }
                    price = String.valueOf(new BigDecimal(price).multiply(new BigDecimal(paramMap.get("denomination"))).divide(new BigDecimal(10)));
                    tbJcPurchaseDetailed.setCouponPayMoney(paramMap.get("couponPrice"));    //优惠券金额(免费获取,金额0)
                    tbJcPurchaseDetailed.setPayInfo("优惠券支付");
                    tbJcPurchaseDetailed.setThirdMoney(new BigDecimal(price));
                    tbJcPurchaseDetailed.setDeductionMoney(Long.valueOf(tbJcPlan.getPrice()-Long.valueOf(price))); //已优惠金额
                }

                if ("000000".equals(result.get("resCode"))) {
                    result.put("orderId", tbJcPurchaseDetailed.getOrderId());
                    result.put("lotteryName", "JCZ");
                    result.put("schemeName", tbJcPlan.getTitle());
                }
                if (type.equals("0")) {
                    return result;
                }
            } else {
                //不使用优惠券,判断首单2元
                if (list <= 0) {//首单
                    price = "2";
                    tbJcPurchaseDetailed.setFirst("1");
                }
            }

            if ("20".equals(paramMap.get("payType"))) {//微信native
                result = payService.wechatPay(userId, price, productName, description, "20", tbJcPurchaseDetailed.getOrderId(), headBean.getSrc(), paramMap.get("ip"));
                if ("000000".equals(result.get("resCode"))) {
                    insertOrder(tbJcPurchaseDetailed);
                    result.put("orderId", tbJcPurchaseDetailed.getOrderId());
                    result.put("lotteryName", "JCZ");
                    result.put("schemeName", tbJcPlan.getTitle());
                }

            }
            if ("21".equals(paramMap.get("payType"))) {//支付宝支付
                result = payService.aliPay(userId, price, description, "21", tbJcPurchaseDetailed.getOrderId(), headBean.getSrc(), paramMap.get("ip"));
                if ("000000".equals(result.get("resCode"))) {
                    insertOrder(tbJcPurchaseDetailed);
                    result.put("orderId", tbJcPurchaseDetailed.getOrderId());
                    result.put("lotteryName", "JCZ");
                    result.put("schemeName", tbJcPlan.getTitle());
                }
            }
            if ("22".equals(paramMap.get("payType"))) {//微信H5
                result = payService.wechatPay(userId, price, productName, description, "22", tbJcPurchaseDetailed.getOrderId(), headBean.getSrc(), paramMap.get("ip"));
                if ("000000".equals(result.get("resCode"))) {
                    insertOrder(tbJcPurchaseDetailed);
                    result.put("orderId", tbJcPurchaseDetailed.getOrderId());
                    result.put("lotteryName", "JCZ");
                    result.put("schemeName", tbJcPlan.getTitle());
                }
            }
            if ("0".equals(paramMap.get("payType"))) {//余额支付
                result = payService.moneyPay(price, "0", userId, tbJcPurchaseDetailed.getOrderId(), headBean.getSrc(), productName);
                if ("000000".equals(result.get("resCode"))) {
                    //不需要定时任务查询订单信息 直接返回订单是否成功状态 直接修改
                    modifyOrderStatus(result, tbJcPlan, tbJcPurchaseDetailed, list);
                    result.put("orderId", tbJcPurchaseDetailed.getOrderId());
                    result.put("lotteryName", "JCZ");
                    result.put("schemeName", tbJcPlan.getTitle());
                }
            }
            if ("99".equals(paramMap.get("payType"))) {//点播
                result = payService.discountRecommendUse(userId, tbJcPurchaseDetailed.getOrderId(), "方案", headBean.getSrc());
                if ("000000".equals(result.get("resCode"))) {
                    //不需要定时任务查询订单信息 直接返回订单是否成功状态 直接修改
                    modifyOrderStatus(result, tbJcPlan, tbJcPurchaseDetailed, list);
                    result.put("orderId", tbJcPurchaseDetailed.getOrderId());
                    result.put("lotteryName", "JCZ");
                    result.put("schemeName", tbJcPlan.getTitle());
                }
            }

            //优惠券最后扣减使用(以免出现扣了优惠券,支付失败的情况)
            if (!Strings.isNullOrEmpty(paramMap.get("couponId"))) {
                if (!paramMap.get("type").equals("0")) {
                    //抵扣券和打折券优惠券扣减(支付成功再扣减)
                    if ("000000".equals(result.get("resCode"))) {
                        result = payService.currencyCouponPay(userId, paramMap.get("couponId"), tbJcPurchaseDetailed.getOrderId(), "购买方案", headBean.getSrc());
                    }else {
                        result=payService.currencyCouponUnLock(userId, paramMap.get("couponId"), tbJcPurchaseDetailed.getOrderId(), "方案", headBean.getSrc());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<TbJcPurchaseDetailed> queryOrder() {
        return tbJcPurchaseDetailedMapper.queryOrder();
    }

    @Override
    public List<TbJcPurchaseDetailed> queryOrderFive() {
        return tbJcPurchaseDetailedMapper.queryOrderFive();
    }

    @Override
    public int updateByExampleSelective(TbJcPurchaseDetailed tbJcPurchaseDetailed, Example example) {
        return tbJcPurchaseDetailedMapper.updateByExampleSelective(tbJcPurchaseDetailed,example);
    }

    @Override
    public TbJcPurchaseDetailed queryTbJcPurchaseDetailedByUserAndPlanId(Long userId, Long schemeId) {
        return tbJcPurchaseDetailedMapper.queryTbJcPurchaseDetailedByUserAndPlanId(userId,schemeId);
    }

    @Override
    public TbJcPurchaseDetailed queryOrderByUserAndOrderId(Long userId, String orderId) {
        return tbJcPurchaseDetailedMapper.queryOrderByUserAndOrderId(userId,orderId);
    }

    @Override
    public Integer queryIsFirstBuy(Long userId) {
        return tbJcPurchaseDetailedMapper.queryIsFirstBuy(userId);
    }

    @Override
    public List<TbJcPurchaseDetailed> queryTbJcPurchaseDetailedByPlanId(Long id) {
        return tbJcPurchaseDetailedMapper.queryTbJcPurchaseDetailedByPlanId(id);
    }

    @Override
    public Integer queryIfHaveSuccessOeder(Long userId) {
        return tbJcPurchaseDetailedMapper.queryIfHaveSuccessOeder(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTbJcPurchaseDetailed(TbJcPurchaseDetailed tbJcPurchaseDetailed,TbJcPurchaseDetailedService tbJcPurchaseDetailedService,TbPlanService tbPlanService) throws BaseException {
        Example example = new Example(TbJcPurchaseDetailed.class);
        example.createCriteria().andEqualTo("id",tbJcPurchaseDetailed.getId());
        int j = tbJcPurchaseDetailedService.updateByExampleSelective(tbJcPurchaseDetailed,example);
        if(j <= 0){
            throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                    ProtocolCodeMsg.UPDATE_FAILE.getMsg());
        }


        //更新专家信息
       /* TbJcExpert tbJcExpert = tbJcExpertService.queryExpertDetailsById(Integer.valueOf(String.valueOf(tbJcPurchaseDetailed.getExpertId())));
        Integer pop = tbJcExpert.getPopularity();
        if(pop == null){
            pop = 0;
        }
        tbJcExpert.setPopularity(pop + 10);
        Example example1 = new Example(TbJcExpert.class);
        example1.createCriteria().andEqualTo("id",tbJcExpert.getId());

        int h = tbJcExpertService.updateByExample(tbJcExpert,example1);
        if(h <= 0){
            throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                    ProtocolCodeMsg.UPDATE_FAILE.getMsg());
        }*/

        //增加对应方案的人气值
        TbJcPlan tbJcPlan1 = tbPlanService.queryPlanByPlanId(tbJcPurchaseDetailed.getSchemeId());
        Integer pop = tbJcPlan1.getPlanPopularity();
        if(pop == null){
            pop = 0;
        }
        tbJcPlan1.setPlanPopularity(pop + 10);
        tbPlanService.updatePlanByPlanId(tbJcPlan1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refundFrozenToMoney(TbJcPlan planResults,TbJcPurchaseDetailedService tbJcPurchaseDetailedService,PayService payService) throws BaseException {
        Map result = new HashMap();
        List<TbJcPurchaseDetailed> tbJcPurchaseDetailedList = tbJcPurchaseDetailedService.queryTbJcPurchaseDetailedByPlanId(planResults.getId());
        if(tbJcPurchaseDetailedList.size() > 0){
            for(int h = 0; h < tbJcPurchaseDetailedList.size(); h++){
                TbJcPurchaseDetailed tbJcPurchaseDetailed = tbJcPurchaseDetailedList.get(h);
                //判断是否是冻结状态  以及订单号 是否是 冻结的订单号
                String pay_status = String.valueOf(tbJcPurchaseDetailed.getPayStatus());
                String order_id = tbJcPurchaseDetailed.getOrderId();
                String[] order = order_id.split("-");
                String payType = String.valueOf(tbJcPurchaseDetailed.getPayType());
                //判断是否支付成功
                if("0".equals(tbJcPurchaseDetailed.getPayStatus())){//未成功
                    log.error("用户 ：" + tbJcPurchaseDetailed.getUserId() + "退款====付款未成功:" +  tbJcPurchaseDetailed.getBuyMoney());
                    continue;
                }
                String remark = "";
                if("20".equals(payType)){
                    remark = "微信支付-方案未中退款";
                    if("1".equals(pay_status) && "JCZF".equals(order[0])){//退款
                        result = payService.refundFrozenToMoney(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(), BigDecimal.valueOf(tbJcPurchaseDetailed.getBuyMoney()),remark,tbJcPurchaseDetailed.getSrc());
                    }
                }
                if("21".equals(payType)){
                    remark = "支付宝支付-方案未中退款";
                    if("1".equals(pay_status) && "JCZF".equals(order[0])){//退款
                        result = payService.refundFrozenToMoney(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(), BigDecimal.valueOf(tbJcPurchaseDetailed.getBuyMoney()),remark,tbJcPurchaseDetailed.getSrc());
                    }
                }
                if("22".equals(payType)){
                    remark = "微信支付-方案未中退款";
                    if("1".equals(pay_status) && "JCZF".equals(order[0])){//退款
                        result = payService.refundFrozenToMoney(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(), BigDecimal.valueOf(tbJcPurchaseDetailed.getBuyMoney()),remark,tbJcPurchaseDetailed.getSrc());
                    }
                }
                if("0".equals(payType)){
                    remark = "余额支付-方案未中退款";
                    if("1".equals(pay_status) && "JCZF".equals(order[0])){//退款
                        result = payService.refundFrozenToMoney(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(), BigDecimal.valueOf(tbJcPurchaseDetailed.getBuyMoney()),remark,tbJcPurchaseDetailed.getSrc());
                    }
                }
                if("99".equals(payType)){
                    remark = "点播卡支付-方案未中退款";
                    if("1".equals(pay_status) && "JCZF".equals(order[0])){//退款
                        result = payService.refundDiscount(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(),"1",remark,tbJcPurchaseDetailed.getSrc());
                    }
                }
                String resCode = String.valueOf(result.get("resCode"));
                if("000000".equals(resCode) || "109023".equals(resCode) || "010124".equals(resCode)){//退款成功
                    //更新订单表信息
                    tbJcPurchaseDetailed.setPayStatus(Long.valueOf(3));
                    tbJcPurchaseDetailed.setAwardStatus(Long.valueOf(0));
                    Example example = new Example(TbJcPurchaseDetailed.class);
                    example.createCriteria().andEqualTo("id",tbJcPurchaseDetailed.getId());
                    int j = tbJcPurchaseDetailedService.updateByExampleSelective(tbJcPurchaseDetailed,example);
                    if(j <= 0){
                        throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                                ProtocolCodeMsg.UPDATE_FAILE.getMsg());
                    }
                    log.error("用户 ：" + tbJcPurchaseDetailed.getUserId() + "退款成功====退款金额:" +  tbJcPurchaseDetailed.getBuyMoney() + "退款类型:" + remark);

                }else{
                    log.error("用户 ：" + tbJcPurchaseDetailed.getUserId() + "退款失败====退款金额:" +  tbJcPurchaseDetailed.getBuyMoney() + "退款类型:" + remark);

                }
            }
        }
    }

    @Override
    public String refundFrozenToMoney(String type ,TbJcPurchaseDetailed tbJcPurchaseDetailed, PayService payService) throws BaseException {
        String remark = "";
        //判断是否是冻结状态  以及订单号 是否是 冻结的订单号
        String pay_status = String.valueOf(tbJcPurchaseDetailed.getPayStatus());
        String order_id = tbJcPurchaseDetailed.getOrderId();
        String[] order = order_id.split("-");
        String payType = String.valueOf(tbJcPurchaseDetailed.getPayType());
        //判断是否支付成功
      /*  if("0".equals(tbJcPurchaseDetailed.getPayStatus())){//未成功
            log.error("用户 ：" + tbJcPurchaseDetailed.getUserId() + "退款====付款未成功:" +  tbJcPurchaseDetailed.getBuyMoney());
            return;
        }*/
        String planStr = "";
        if("1".equals(type)){
            planStr="重复支付退款";
        }
        else if("2".equals(type)){
            planStr="方案过期退款";
        }
        else if("3".equals(type)){
            planStr="首单重复支付退款";
        }
        Map result = new HashMap();
        if("20".equals(payType)){
            //remark = "微信支付-"+planStr;
            remark = planStr;
            if("0".equals(pay_status) && "JCZF".equals(order[0])){//退款
                result = payService.refundFrozenToMoney(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(), BigDecimal.valueOf(tbJcPurchaseDetailed.getBuyMoney()),remark,tbJcPurchaseDetailed.getSrc());
            }
        }
        if("21".equals(payType)){
            //remark = "支付宝支付-"+planStr;
            remark = planStr;
            if("0".equals(pay_status) && "JCZF".equals(order[0])){//退款
                result = payService.refundFrozenToMoney(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(), BigDecimal.valueOf(tbJcPurchaseDetailed.getBuyMoney()),remark,tbJcPurchaseDetailed.getSrc());
            }
        }
        if("22".equals(payType)){
            //remark = "微信支付-"+planStr;
            remark = planStr;
            if("0".equals(pay_status) && "JCZF".equals(order[0])){//退款
                result = payService.refundFrozenToMoney(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(), BigDecimal.valueOf(tbJcPurchaseDetailed.getBuyMoney()),remark,tbJcPurchaseDetailed.getSrc());
            }
        }
        if("0".equals(payType)){
            //remark = "余额支付-"+planStr;
            remark = planStr;
            if("0".equals(pay_status) && "JCZF".equals(order[0])){//退款
                result = payService.refundFrozenToMoney(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(), BigDecimal.valueOf(tbJcPurchaseDetailed.getBuyMoney()),remark,tbJcPurchaseDetailed.getSrc());
            }
        }
        if("99".equals(payType)){
            //remark = "点播卡支付-"+planStr;
            remark = planStr;
            if("0".equals(pay_status) && "JCZF".equals(order[0])){//退款
                result = payService.refundDiscount(tbJcPurchaseDetailed.getUserId(),tbJcPurchaseDetailed.getOrderId(),"1",remark,tbJcPurchaseDetailed.getSrc());
            }
        }
        String resCode = String.valueOf(result.get("resCode"));
       return resCode;
    }

    @Transactional(rollbackFor = Exception.class)
    public TbJcPurchaseDetailed generatedObject(TbJcPlan tbJcPlan, String userId,Map<String, String> paramMap,Integer list,ProtocolParamDto.HeadBean headBean,String cell){
        TbJcPurchaseDetailed tbJcPurchaseDetailed = new TbJcPurchaseDetailed();
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        //tbJcPurchaseDetailed.setPayId();//支付id
        tbJcPurchaseDetailed.setUserId(Long.valueOf(userId));//用户id
        tbJcPurchaseDetailed.setSchemeId(tbJcPlan.getId());//方案id
        //tbJcPurchaseDetailed.setUserName(tbJcUser.getUserName());//用户名
        //tbJcPurchaseDetailed.setCell(tbJcUser.getCell());//用户手机号
        tbJcPurchaseDetailed.setPayStatus(Long.valueOf(0));//支付状态
        if(StringUtils.isNotBlank(cell)){
            tbJcPurchaseDetailed.setCell(cell);
        }
        tbJcPurchaseDetailed.setCreateTime(new Date());//创建时间
        tbJcPurchaseDetailed.setYear(String.valueOf(year));
        tbJcPurchaseDetailed.setMonth(String.valueOf(month));
        tbJcPurchaseDetailed.setDay(String.valueOf(day));
        tbJcPurchaseDetailed.setPayType(Long.valueOf(paramMap.get("payType")));//支付方式
        if("20".equals(paramMap.get("payType"))){
            tbJcPurchaseDetailed.setPayInfo("微信支付");
            if(list <= 0){
                tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(2));//支付金额
            }else{
                tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(tbJcPlan.getPrice()));//支付金额
            }

            tbJcPurchaseDetailed.setPlanPayType("2");//支付类型
        }
        if("21".equals(paramMap.get("payType"))){
            tbJcPurchaseDetailed.setPayInfo("支付宝支付");
            if(list <= 0){
                tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(2));//支付金额
            }else{
                tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(tbJcPlan.getPrice()));//支付金额
            }
            tbJcPurchaseDetailed.setPlanPayType("2");//支付类型
        }
        if("22".equals(paramMap.get("payType"))){
            tbJcPurchaseDetailed.setPayInfo("微信支付");
            if(list <= 0){
                tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(2));//支付金额
            }else{
                tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(tbJcPlan.getPrice()));//支付金额
            }
            tbJcPurchaseDetailed.setPlanPayType("2");//支付类型
        }
        if("0".equals(paramMap.get("payType"))){
            tbJcPurchaseDetailed.setPayInfo("余额支付");
            if(list <= 0){
                tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(2));//支付金额
            }else{
                tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(tbJcPlan.getPrice()));//支付金额
            }
            tbJcPurchaseDetailed.setPlanPayType("2");//支付类型
        }
        if("99".equals(paramMap.get("payType"))){
            tbJcPurchaseDetailed.setPayInfo("点播支付");
            tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(dbPricd));//支付金额
            tbJcPurchaseDetailed.setPlanPayType("99");//支付类型
        }
        if("2".equals(tbJcPlan.getType().toString())){//不中全退
            tbJcPurchaseDetailed.setOrderId(commonUtils.createOrderId("JCZF"));//订单id
        }else{
            tbJcPurchaseDetailed.setOrderId(commonUtils.createOrderId("JC"));//订单id

        }
        tbJcPurchaseDetailed.setExpertId(tbJcPlan.getAscriptionExpert());//专家id
        tbJcPurchaseDetailed.setPlanType(String.valueOf(tbJcPlan.getType()));//方案类型
        tbJcPurchaseDetailed.setSrc(headBean.getSrc());
        if(!Strings.isNullOrEmpty(paramMap.get("couponId"))){
            //修改时间(优惠券支付，创建和修改时间一步完成)
            tbJcPurchaseDetailed.setUpdateTime(new Date());
        }
        return tbJcPurchaseDetailed;
    }


    @Transactional(rollbackFor = Exception.class)
    public void modifyOrderStatus(Map<String, Object> result,TbJcPlan tbJcPlan,TbJcPurchaseDetailed tbJcPurchaseDetailed,Integer list) throws BaseException {
       if(result.get("payingId")!=null){
           tbJcPurchaseDetailed.setPayId(String.valueOf(result.get("payingId")));
       }
        if("2".equals(tbJcPurchaseDetailed.getPlanType())){//不中全退
            /*if(list <= 0){
                tbJcPurchaseDetailed.setPayStatus(Long.valueOf(2));
            }else{
                tbJcPurchaseDetailed.setPayStatus(Long.valueOf(1));
            }*/
            tbJcPurchaseDetailed.setPayStatus(Long.valueOf(1));

        }else{
            tbJcPurchaseDetailed.setPayStatus(Long.valueOf(2));
        }
        //是否首次
        if(list <= 0){
            tbJcPurchaseDetailed.setFirst("1");
            tbJcPurchaseDetailed.setThirdMoney(new BigDecimal("2"));
        }else{
            tbJcPurchaseDetailed.setFirst("0");
            if("99".equals(tbJcPurchaseDetailed.getPlanPayType())){

                //判断点播支付类型
                //普通点播次卡
                if("1".equals(String.valueOf(result.get("type")))){
                    tbJcPurchaseDetailed.setThirdMoney(new BigDecimal(20));
                }
                //周卡
                if("5".equals(String.valueOf(result.get("type")))){
                    tbJcPurchaseDetailed.setThirdMoney(new BigDecimal(10));
                    //点播周卡支付
                    tbJcPurchaseDetailed.setPlanPayType("3");
                    tbJcPurchaseDetailed.setPayInfo("点播周卡支付");
                    tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(10));//支付金额
                    tbJcPurchaseDetailed.setPayType(Long.valueOf(98));
                }
                //免费点播卡
                if("7".equals(String.valueOf(result.get("type")))){
                    tbJcPurchaseDetailed.setThirdMoney(new BigDecimal(0));
                    //免费点播卡支付
                    tbJcPurchaseDetailed.setPlanPayType("4");
                    tbJcPurchaseDetailed.setPayInfo("免费点播卡卡支付");
                    tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(0));//支付金额
                    tbJcPurchaseDetailed.setPayType(Long.valueOf(97));
                }
            }else{
                if(Strings.isNullOrEmpty(tbJcPurchaseDetailed.getCouponId())) {
                    tbJcPurchaseDetailed.setThirdMoney(new BigDecimal(tbJcPlan.getPrice()));
                }
                //tbJcPurchaseDetailed.setThirdMoney(new BigDecimal(tbJcPlan.getPrice()));
            }

        }
        tbJcPurchaseDetailed.setUpdateTime(new Date());


        Example example = new Example(TbJcPurchaseDetailed.class);
        example.createCriteria().andEqualTo("id",tbJcPurchaseDetailed.getId());
        int j = tbJcPurchaseDetailedMapper.insertSelective(tbJcPurchaseDetailed);
        if(j <= 0){
            throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                    ProtocolCodeMsg.INSERT_FAILE.getMsg());
        }


        //增加专家人气
        /*TbJcExpert tbJcExpert = tbJcExpertMapper.queryExpertDetailsById(Integer.valueOf(String.valueOf(tbJcPlan.getAscriptionExpert())));
        Integer pop = tbJcExpert.getPopularity();
        if(pop == null){
            pop = 0;
        }
        tbJcExpert.setPopularity(pop + 10);
        Example example1 = new Example(TbJcPurchaseDetailed.class);
        example1.createCriteria().andEqualTo("id",tbJcExpert.getId());

        int h = tbJcExpertMapper.updateByExample(tbJcExpert,example1);
        if(h <= 0){
            throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                    ProtocolCodeMsg.UPDATE_FAILE.getMsg());
        }*/
        addPlanPopularity(tbJcPlan);


    }

    /**
     * 增加方案人气
     * @param tbJcPlan
     * @throws BaseException
     */
    @Override
    public void addPlanPopularity(TbJcPlan tbJcPlan) throws BaseException {
        //增加对应方案的人气值
        TbJcPlan tbJcPlan1 = tbJcPlanMapper.queryPlanByPlanId(tbJcPlan.getId());
        Integer pop = tbJcPlan1.getPlanPopularity();
        if(pop == null){
            pop = 0;
        }
        tbJcPlan1.setPlanPopularity(pop + 10);
        Example example1 = new Example(TbJcPlan.class);
        example1.createCriteria().andEqualTo("id",tbJcPlan1.getId());

        int h = tbJcPlanMapper.updateByExample(tbJcPlan1,example1);
        if(h <= 0){
            throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                    ProtocolCodeMsg.UPDATE_FAILE.getMsg());
        }
    }

    public void insertOrder( TbJcPurchaseDetailed tbJcPurchaseDetailed) throws BaseException {
        int i = tbJcPurchaseDetailedMapper.insertSelective(tbJcPurchaseDetailed);
        if(i <= 0){
            throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                    ProtocolCodeMsg.INSERT_FAILE.getMsg());
        }
    }
}
