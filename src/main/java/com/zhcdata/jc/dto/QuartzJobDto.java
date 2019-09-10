package com.zhcdata.jc.dto;

import lombok.Data;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/5/14 15:39
 */
@Data
public class QuartzJobDto {
    private String name;
    private String group;
    private String cron;
    private String jobDataMap;
    private String jobClass;
}
