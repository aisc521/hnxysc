package com.zhcdata.jc.tools;

import org.apache.commons.beanutils.PropertyUtilsBean;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

/**
 * Title:
 * Description: map 工具类
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author cuishuai
 * @version 1.0
 * @Date 2019/6/24 17:37
 */
public class MapUtils {

    /**
     * 将bean转化为map
     *
     * @param bean
     * @return
     */
    public static Map<String, Object> getMap(Object bean) {
        return beanToMap(bean);
    }

    /**
     * 将javabean实体类转为map类型，然后返回一个map类型的值
     *
     * @return
     */
    public static Map<String, Object> beanToMap(Object beanObj) {
        Map<String, Object> params = new HashMap<String, Object>(0);
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(beanObj);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (!"class".equals(name)) {
                    params.put(name, propertyUtilsBean.getNestedProperty(beanObj, name));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }
}
