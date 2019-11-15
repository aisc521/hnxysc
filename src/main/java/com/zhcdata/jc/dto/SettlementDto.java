package com.zhcdata.jc.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/11/15 15:58
 */
@Getter
@Setter
public class SettlementDto {
    private String nikeName;
    private String mobile;
    private String ascription;
    private Long expert_plan_num;
    private BigDecimal sale_price;
    private BigDecimal pay_ondemand_price;
    private BigDecimal ondemand_average;
    private BigDecimal free_ondemand_price;
    private String divide_proport;
    private String expert_person_name;
    private Long id;
    private BigDecimal real_income;
    private BigDecimal settlement_amount;

}
