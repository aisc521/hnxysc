package com.zhcdata.jc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhcdata.db.mapper.TbJcExpertMapper;
import com.zhcdata.db.mapper.TbJcPurchaseDetailedMapper;
import com.zhcdata.db.model.*;
import com.zhcdata.jc.dto.ProtocolParamDto;
import com.zhcdata.jc.dto.PurchasedPlanDto;
import com.zhcdata.jc.enums.ProtocolCodeMsg;
import com.zhcdata.jc.exception.BaseException;
import com.zhcdata.jc.service.PayService;
import com.zhcdata.jc.service.TbJcPurchaseDetailedService;
import com.zhcdata.jc.tools.CommonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private TbJcExpertMapper tbJcExpertMapper;
    @Resource
    private CommonUtils commonUtils;

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
    public Map<String, Object> schemePurchase(TbJcPlan tbJcPlan, String userId, Map<String, String> paramMap,PayService payService,List<TbJcPurchaseDetailed> list,ProtocolParamDto.HeadBean headBean) throws BaseException {
        Map<String, Object> result = new HashMap<>();
        try{
            TbJcPurchaseDetailed tbJcPurchaseDetailed = generatedObject(tbJcPlan,userId,paramMap,list,headBean);

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
                result = payService.aliPay(userId,String.valueOf(tbJcPlan.getPrice()),description,"21",tbJcPurchaseDetailed.getOrderId(),headBean.getSrc(),"192.168.64.140");
                if("000000".equals(result.get("resCode"))){
                    insertOrder(tbJcPurchaseDetailed);
                    result.put("orderId",tbJcPurchaseDetailed.getOrderId());
                    result.put("lotteryName","JCZ");
                    result.put("schemeName",tbJcPlan.getTitle());
                }
            }
            if("22".equals(paramMap.get("payType"))){//微信H5
                result = payService.wechatPay(userId,String.valueOf(tbJcPlan.getPrice()),productName,description,"22",tbJcPurchaseDetailed.getOrderId(),headBean.getSrc(),"192.168.64.140");
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
            if("1".equals(paramMap.get("payType"))){//点播
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
    public List<TbJcPurchaseDetailed> queryIsFirstBuy(Long userId) {
        return tbJcPurchaseDetailedMapper.queryIsFirstBuy(userId);
    }

    @Override
    public List<TbJcPurchaseDetailed> queryTbJcPurchaseDetailedByPlanId(Long id) {
        return tbJcPurchaseDetailedMapper.queryTbJcPurchaseDetailedByPlanId(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public TbJcPurchaseDetailed generatedObject(TbJcPlan tbJcPlan, String userId,Map<String, String> paramMap,List<TbJcPurchaseDetailed> list,ProtocolParamDto.HeadBean headBean){
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

        tbJcPurchaseDetailed.setCreateTime(new Date());//创建时间
        tbJcPurchaseDetailed.setYear(String.valueOf(year));
        tbJcPurchaseDetailed.setMonth(String.valueOf(month));
        tbJcPurchaseDetailed.setDay(String.valueOf(day));
        tbJcPurchaseDetailed.setPayType(Long.valueOf(paramMap.get("payType")));//支付方式
        if("20".equals(paramMap.get("payType"))){
            tbJcPurchaseDetailed.setPayInfo("微信支付");
            if(list.size() <= 0){
                tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(2));//支付金额
            }else{
                tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(tbJcPlan.getPrice()));//支付金额
            }

            tbJcPurchaseDetailed.setPlanPayType("2");//支付类型
        }
        if("21".equals(paramMap.get("payType"))){
            tbJcPurchaseDetailed.setPayInfo("支付宝支付");
            if(list.size() <= 0){
                tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(2));//支付金额
            }else{
                tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(tbJcPlan.getPrice()));//支付金额
            }
            tbJcPurchaseDetailed.setPlanPayType("2");//支付类型
        }
        if("22".equals(paramMap.get("payType"))){
            tbJcPurchaseDetailed.setPayInfo("微信支付");
            if(list.size() <= 0){
                tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(2));//支付金额
            }else{
                tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(tbJcPlan.getPrice()));//支付金额
            }
            tbJcPurchaseDetailed.setPlanPayType("2");//支付类型
        }
        if("0".equals(paramMap.get("payType"))){
            tbJcPurchaseDetailed.setPayInfo("余额支付");
            if(list.size() <= 0){
                tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(2));//支付金额
            }else{
                tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(tbJcPlan.getPrice()));//支付金额
            }
            tbJcPurchaseDetailed.setPlanPayType("2");//支付类型
        }
        if("1".equals(paramMap.get("payType"))){
            tbJcPurchaseDetailed.setPayInfo("点播支付");
            tbJcPurchaseDetailed.setBuyMoney(Long.valueOf(dbPricd));//支付金额
            tbJcPurchaseDetailed.setPlanPayType("1");//支付类型
        }
        if("2".equals(tbJcPlan.getType())){//不中全退
            if(list.size() <= 0){
                tbJcPurchaseDetailed.setOrderId(commonUtils.createOrderId("JC"));//订单id
            }else{
                tbJcPurchaseDetailed.setOrderId(commonUtils.createOrderId("JCZF"));//订单id
            }

        }else{
            if(list.size() <= 0){
                tbJcPurchaseDetailed.setOrderId(commonUtils.createOrderId("JC"));//订单id
            }else{
                tbJcPurchaseDetailed.setOrderId(commonUtils.createOrderId("JCZF"));//订单id
            }
        }
        tbJcPurchaseDetailed.setExpertId(tbJcPlan.getAscriptionExpert());//专家id
        tbJcPurchaseDetailed.setPlanType(String.valueOf(tbJcPlan.getType()));//方案类型
        tbJcPurchaseDetailed.setSrc(headBean.getSrc());

        //是否首次
        if(list.size() <= 0){
            tbJcPurchaseDetailed.setFirst("1");
        }else{
            tbJcPurchaseDetailed.setFirst("0");
        }

        return tbJcPurchaseDetailed;
    }


    @Transactional(rollbackFor = Exception.class)
    public void modifyOrderStatus(Map<String, Object> result,TbJcPlan tbJcPlan,TbJcPurchaseDetailed tbJcPurchaseDetailed,List<TbJcPurchaseDetailed> list) throws BaseException {
        tbJcPurchaseDetailed.setPayId(String.valueOf(result.get("payingId")));
        if("2".equals(tbJcPurchaseDetailed.getPlanType())){//不中全退
            if(list.size() <= 0){
                tbJcPurchaseDetailed.setPayStatus(Long.valueOf(2));
            }else{
                tbJcPurchaseDetailed.setPayStatus(Long.valueOf(1));
            }

        }else{
            tbJcPurchaseDetailed.setPayStatus(Long.valueOf(2));
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
        TbJcExpert tbJcExpert = tbJcExpertMapper.queryExpertDetailsById(Integer.valueOf(String.valueOf(tbJcPlan.getAscriptionExpert())));
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
