package com.zhcdata.jc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhcdata.db.mapper.TbJcExpertMapper;
import com.zhcdata.db.mapper.TbJcPlanMapper;
import com.zhcdata.db.mapper.TbJcPurchaseDetailedMapper;
import com.zhcdata.db.model.*;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.dto.PurchasedPlanDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.PayService;
import com.zhcdata.jc.service.TbJcPurchaseDetailedService;
import com.zhcdata.jc.service.TbPlanService;
import com.zhcdata.jc.tools.CommonUtils;
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
        try{
            TbJcPurchaseDetailed tbJcPurchaseDetailed = generatedObject(tbJcPlan,userId,paramMap,list,headBean,cell);

            if("20".equals(paramMap.get("payType"))){//微信native
                result = payService.wechatPay(userId,String.valueOf(tbJcPlan.getPrice()),productName,description,"20",tbJcPurchaseDetailed.getOrderId(),headBean.getSrc(),paramMap.get("ip"));
                if("000000".equals(result.get("resCode"))){
                    insertOrder(tbJcPurchaseDetailed);
                    result.put("orderId",tbJcPurchaseDetailed.getOrderId());
                    result.put("lotteryName","JCZ");
                    result.put("schemeName",tbJcPlan.getTitle());
                }

            }
            if("21".equals(paramMap.get("payType"))){//支付宝支付
                result = payService.aliPay(userId,String.valueOf(tbJcPlan.getPrice()),description,"21",tbJcPurchaseDetailed.getOrderId(),headBean.getSrc(),paramMap.get("ip"));
                if("000000".equals(result.get("resCode"))){
                    insertOrder(tbJcPurchaseDetailed);
                    result.put("orderId",tbJcPurchaseDetailed.getOrderId());
                    result.put("lotteryName","JCZ");
                    result.put("schemeName",tbJcPlan.getTitle());
                }
            }
            if("22".equals(paramMap.get("payType"))){//微信H5
                result = payService.wechatPay(userId,String.valueOf(tbJcPlan.getPrice()),productName,description,"22",tbJcPurchaseDetailed.getOrderId(),headBean.getSrc(),paramMap.get("ip"));
                if("000000".equals(result.get("resCode"))){
                    insertOrder(tbJcPurchaseDetailed);
                    result.put("orderId",tbJcPurchaseDetailed.getOrderId());
                    result.put("lotteryName","JCZ");
                    result.put("schemeName",tbJcPlan.getTitle());
                }
            }
            if("0".equals(paramMap.get("payType"))){//余额支付
                result = payService.moneyPay(String.valueOf(tbJcPlan.getPrice()), "0", userId, tbJcPurchaseDetailed.getOrderId(), headBean.getSrc(), productName);
                if("000000".equals(result.get("resCode"))){
                    //不需要定时任务查询订单信息 直接返回订单是否成功状态 直接修改
                    modifyOrderStatus(result,tbJcPlan,tbJcPurchaseDetailed,list);
                    result.put("orderId",tbJcPurchaseDetailed.getOrderId());
                    result.put("lotteryName","JCZ");
                    result.put("schemeName",tbJcPlan.getTitle());
                }
            }
            if("99".equals(paramMap.get("payType"))){//点播
                result = payService.discountRecommendUse(userId, tbJcPurchaseDetailed.getOrderId(), description, headBean.getSrc());
                if("000000".equals(result.get("resCode"))){
                    //不需要定时任务查询订单信息 直接返回订单是否成功状态 直接修改
                    modifyOrderStatus(result,tbJcPlan,tbJcPurchaseDetailed,list);
                    result.put("orderId",tbJcPurchaseDetailed.getOrderId());
                    result.put("lotteryName","JCZ");
                    result.put("schemeName",tbJcPlan.getTitle());
                }
            }



        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<TbJcPurchaseDetailed> queryOrder() {
        return tbJcPurchaseDetailedMapper.queryOrder();
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
        if("2".equals(tbJcPlan.getType())){//不中全退
            tbJcPurchaseDetailed.setOrderId(commonUtils.createOrderId("JCZF"));//订单id
        }else{
            tbJcPurchaseDetailed.setOrderId(commonUtils.createOrderId("JC"));//订单id

        }
        tbJcPurchaseDetailed.setExpertId(tbJcPlan.getAscriptionExpert());//专家id
        tbJcPurchaseDetailed.setPlanType(String.valueOf(tbJcPlan.getType()));//方案类型
        tbJcPurchaseDetailed.setSrc(headBean.getSrc());



        return tbJcPurchaseDetailed;
    }


    @Transactional(rollbackFor = Exception.class)
    public void modifyOrderStatus(Map<String, Object> result,TbJcPlan tbJcPlan,TbJcPurchaseDetailed tbJcPurchaseDetailed,Integer list) throws BaseException {
        tbJcPurchaseDetailed.setPayId(String.valueOf(result.get("payingId")));
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
        }else{
            tbJcPurchaseDetailed.setFirst("0");
        }
        tbJcPurchaseDetailed.setUpdateTime(new Date());

        tbJcPurchaseDetailed.setThirdMoney(Long.valueOf(tbJcPlan.getPrice()));
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
