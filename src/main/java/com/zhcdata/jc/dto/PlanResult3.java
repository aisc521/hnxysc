package com.zhcdata.jc.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/10/24 15:41
 */
@Getter
@Setter
public class PlanResult3 {

    private String busiCode;
    private String resCode;
    private String message;
    private String pageNo;
    private String pageTotal;
    private List<PlanResult1> list;



}
