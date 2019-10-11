package com.zhcdata.jc.service.impl;

import com.zhcdata.db.mapper.TbJcPurchaseDetailedMapper;
import com.zhcdata.db.model.JcMatchBjdcPl;
import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.db.model.TbJcPurchaseDetailed;
import com.zhcdata.db.model.TbJcUser;
import com.zhcdata.jc.dto.PurchasedPlanDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.PayService;
import com.zhcdata.jc.service.TbJcPurchaseDetailedService;
import com.zhcdata.jc.tools.CommonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/9/20 16:51
 */
@Service
public class TbJcPurchaseDetailedServiceImpl implements TbJcPurchaseDetailedService {
    @Resource
    private TbJcPurchaseDetailedMapper tbJcPurchaseDetailedMapper;

    @Resource
    private CommonUtils commonUtils;

    @Value("${custom.dbPricd}")
    String dbPricd;
    @Value("${custom.description}")
    String description;
    @Value("${custom.productName}")
    String productName ;



    @Override
    public List<PurchasedPlanDto> queryPurchasedPlanDtoByUserId(long userId) {
        return tbJcPurchaseDetailedMapper.queryPurchasedPlanDtoByUserId(userId);
    }


    @Override
    public Map<String, Object> schemePurchase(TbJcPlan tbJcPlan, TbJcUser tbJcUser, Map<String, String> paramMap,PayService payService) throws BaseException {
        TbJcPurchaseDetailed tbJcPurchaseDetailed = generatedObject(tbJcPlan,tbJcUser,paramMap);
        Map<String, Object> result = new HashMap<>();
        if("20".equals(paramMap.get("payType"))){//微信native
            result = payService.wechatPay(String.valueOf(tbJcUser.getUserId()),String.valueOf(tbJcPlan.getPrice()),productName,description,"20",tbJcPurchaseDetailed.getOrderId(),paramMap.get("src"),paramMap.get("ip"));
        }
        if("21".equals(paramMap.get("payType"))){//支付宝支付
            result = payService.aliPay(String.valueOf(tbJcUser.getUserId()),String.valueOf(tbJcPlan.getPrice()),description,"21",tbJcPurchaseDetailed.getOrderId(),paramMap.get("src"),paramMap.get("ip"));
        }
        if("22".equals(paramMap.get("payType"))){//微信H5
            result = payService.wechatPay(String.valueOf(tbJcUser.getUserId()),String.valueOf(tbJcPlan.getPrice()),productName,description,"22",tbJcPurchaseDetailed.getOrderId(),paramMap.get("src"),paramMap.get("ip"));
        }
        if("0".equals(paramMap.get("payType"))){//余额支付
            result = payService.moneyPay(String.valueOf(tbJcPlan.getPrice()), "0", String.valueOf(tbJcUser.getUserId()), tbJcPurchaseDetailed.getOrderId(), paramMap.get("src"), productName);
            //不需要定时任务查询订单信息 直接返回订单是否成功状态 直接修改
            modifyOrderStatus(result);
        }
        if("1".equals(paramMap.get("payType"))){//点播
            result = payService.discountRecommendUse(String.valueOf(tbJcUser.getUserId()), tbJcPurchaseDetailed.getOrderId(), description, paramMap.get("src"));
            //不需要定时任务查询订单信息 直接返回订单是否成功状态 直接修改
            modifyOrderStatus(result);
        }
        int i = tbJcPurchaseDetailedMapper.insertSelective(tbJcPurchaseDetailed);
        if(i <= 0){
            throw new BaseException(ProtocolCodeMsg.INSERT_FAILE.getCode(),
                    ProtocolCodeMsg.INSERT_FAILE.getMsg());
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

    private TbJcPurchaseDetailed generatedObject(TbJcPlan tbJcPlan, TbJcUser tbJcUser,Map<String, String> paramMap){
        TbJcPurchaseDetailed tbJcPurchaseDetailed = new TbJcPurchaseDetailed();
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        //tbJcPurchaseDetailed.setPayId();//支付id
        tbJcPurchaseDetailed.setUserId(tbJcUser.getUserId());//用户id
        tbJcPurchaseDetailed.setSchemeId(tbJcPlan.getId());//方案id
        tbJcPurchaseDetailed.setUserName(tbJcUser.getUserName());//用户名
        tbJcPurchaseDetailed.setCell(tbJcUser.getCell());//用户手机号
        tbJcPurchaseDetailed.setPayStatus(Long.valueOf(0));//支付状态

        tbJcPurchaseDetailed.setCreateTime(new Date());//创建时间
        tbJcPurchaseDetailed.setYear(String.valueOf(year));
        tbJcPurchaseDetailed.setMonth(String.valueOf(month));
        tbJcPurchaseDetailed.setDay(String.valueOf(day));
        tbJcPurchaseDetailed.setPayType(Long.valueOf(paramMap.get("payType")));//支付方式
        if("20".equals(paramMap.get("payType"))){
            tbJcPurchaseDetailed.setPayInfo("微信支付");
            tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(tbJcPlan.getPrice()));//支付金额
            tbJcPurchaseDetailed.setPlanPayType("2");//支付类型
        }
        if("21".equals(paramMap.get("payType"))){
            tbJcPurchaseDetailed.setPayInfo("支付宝支付");
            tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(tbJcPlan.getPrice()));//支付金额
            tbJcPurchaseDetailed.setPlanPayType("2");//支付类型
        }
        if("22".equals(paramMap.get("payType"))){
            tbJcPurchaseDetailed.setPayInfo("微信支付");
            tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(tbJcPlan.getPrice()));//支付金额
            tbJcPurchaseDetailed.setPlanPayType("2");//支付类型
        }
        if("0".equals(paramMap.get("payType"))){
            tbJcPurchaseDetailed.setPayInfo("余额支付");
            tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(tbJcPlan.getPrice()));//支付金额
            tbJcPurchaseDetailed.setPlanPayType("2");//支付类型
        }
        if("1".equals(paramMap.get("payType"))){
            tbJcPurchaseDetailed.setPayInfo("点播支付");
            tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(dbPricd));//支付金额
            tbJcPurchaseDetailed.setPlanPayType("1");//支付类型
        }
        if("2".equals(tbJcPlan.getType())){//不中全退
            tbJcPurchaseDetailed.setOrderId(commonUtils.createOrderId("JCZF"));//订单id
        }else{
            tbJcPurchaseDetailed.setOrderId(commonUtils.createOrderId("JC"));//订单id
        }
        tbJcPurchaseDetailed.setExpertId(tbJcPlan.getAscriptionExpert());//专家id
        tbJcPurchaseDetailed.setPlanType(String.valueOf(tbJcPlan.getType()));//方案类型
        tbJcPurchaseDetailed.setSrc(paramMap.get("src"));
        return tbJcPurchaseDetailed;
    }


    public void modifyOrderStatus(Map<String, Object> result) throws BaseException {
        String orderId = String.valueOf(result.get("orderId"));
        TbJcPurchaseDetailed tbJcPurchaseDetailed = tbJcPurchaseDetailedMapper.queryPurchasedPlanByOrderId(orderId);
        if(tbJcPurchaseDetailed == null){
            throw new BaseException(ProtocolCodeMsg.ORDER_IS_NULL.getCode(),
                    ProtocolCodeMsg.ORDER_IS_NULL.getMsg());
        }
        tbJcPurchaseDetailed.setPayId(String.valueOf(result.get("payingId")));
        if("2".equals(tbJcPurchaseDetailed.getPlanType())){//不中全退
            tbJcPurchaseDetailed.setPayStatus(Long.valueOf(1));
        }else{
            tbJcPurchaseDetailed.setPayStatus(Long.valueOf(2));
        }
        tbJcPurchaseDetailed.setUpdateTime(new Date());

        //获取返回金额 实际支付金额  ******************  返回字段名称暂时未定
        tbJcPurchaseDetailed.setThirdMoney(Long.valueOf(String.valueOf(result.get("amount"))));


        Example example = new Example(TbJcPurchaseDetailed.class);
        example.createCriteria().andEqualTo("id",tbJcPurchaseDetailed.getId());
        int j = tbJcPurchaseDetailedMapper.updateByExampleSelective(tbJcPurchaseDetailed,example);
        if(j <= 0){
            throw new BaseException(ProtocolCodeMsg.UPDATE_FAILE.getCode(),
                    ProtocolCodeMsg.UPDATE_FAILE.getMsg());
        }
    }
}
