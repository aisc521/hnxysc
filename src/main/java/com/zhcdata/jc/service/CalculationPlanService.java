package com.zhcdata.jc.service;

import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.jc.exception.BaseException;

import java.util.List;

public interface CalculationPlanService {
    void calculationPlan(List<TbJcPlan> planResults) throws BaseException;
}
