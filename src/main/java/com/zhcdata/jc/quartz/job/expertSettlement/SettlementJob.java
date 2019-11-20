/*
package com.zhcdata.jc.quartz.job.expertSettlement;

import com.zhcdata.db.model.TbJcExpertSettlement;
import com.zhcdata.jc.dto.SettlementDto;
import com.zhcdata.jc.service.TbJcExpertSettlementService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleState;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

*/
/**
 * @Description 专家销售统计 一个月一次
 * @Author cuishuai
 * @Date 2019/11/15 14:57
 *//*

@Configuration
@EnableScheduling
@Slf4j
@Component
public class SettlementJob implements Job {

    @Resource
    private TbJcExpertSettlementService tbJcExpertSettlementService;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.error("专家销售结算统计定时任务启动" );
        long s = System.currentTimeMillis();
        try{
            //Date类型转为String
            Date date = new Date();
            //设置字符串格式
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = simpleDateFormat.format(date);

            List<SettlementDto> tbJcExpertSettlementList = tbJcExpertSettlementService.queryTbJcExpertSettlement();
            for(int i = 0; i < tbJcExpertSettlementList.size(); i++){
                SettlementDto settlementDto = tbJcExpertSettlementList.get(i);
                TbJcExpertSettlement tbJcExpertSettlement = new TbJcExpertSettlement();
                tbJcExpertSettlement.setStatisticsDate(dateString);
                tbJcExpertSettlement.setNikeName(settlementDto.getNikeName());
                tbJcExpertSettlement.setMobile(settlementDto.getMobile());
                tbJcExpertSettlement.setExpertPerson(settlementDto.getAscription());
                tbJcExpertSettlement.setExpertPlanNum(settlementDto.getExpert_plan_num());
                tbJcExpertSettlement.setSalePrice(settlementDto.getSale_price());
                tbJcExpertSettlement.setPayOndemandPrice(settlementDto.getPay_ondemand_price());
                tbJcExpertSettlement.setOndemandAverage(settlementDto.getOndemand_average());
                tbJcExpertSettlement.setFreeOndemandPrice(settlementDto.getFree_ondemand_price());
                tbJcExpertSettlement.setSettlementAmount(settlementDto.getSettlement_amount());
                tbJcExpertSettlement.setDivideProport(settlementDto.getDivide_proport());
                tbJcExpertSettlement.setRealIncome(settlementDto.getReal_income());
                tbJcExpertSettlement.setSettlemenStatus(Long.valueOf(0));
                tbJcExpertSettlement.setCreateTime(new Date());
                tbJcExpertSettlement.setUpdateTime(new Date());
                tbJcExpertSettlement.setExpertPersonName(settlementDto.getExpert_person_name());
                tbJcExpertSettlement.setExpertId(settlementDto.getId());
                tbJcExpertSettlementService.insertTbJcExpertSettlement(tbJcExpertSettlement);
            }
        }catch (Exception e){
            log.error("专家销售结算统计定时任务异常:" + e.getCause());
            e.printStackTrace();
        }

        log.error("专家销售结算统计定时任务结束,耗时："+(System.currentTimeMillis()-s)+"毫秒");
    }
}
*/
