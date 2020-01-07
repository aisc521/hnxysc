package com.zhcdata.jc.service;

import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.jc.exception.BaseException;

public interface CalculationPlanNewService {

    void calculationPlan(TbJcPlan tbJcPlan) throws BaseException;

}
