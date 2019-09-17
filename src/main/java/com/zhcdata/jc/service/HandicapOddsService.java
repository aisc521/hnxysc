package com.zhcdata.jc.service;

/**
 * Title:
 * Description: 盘口、赔率相关service
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/9/16 10:09
 */
public interface HandicapOddsService {

    /**
     * 获取Count天之前的赔率数据
     * @param count
     */
    void updateHandicapOddsData(int count);

    /**
     * 获取Count天之前的赔率公司变化数据
     * @param count
     */
    void updateHandicapOddsDetailData(int count);
}
