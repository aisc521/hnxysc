package com.zhcdata.jc.tools;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/10/24 14:21
 */
public class DoubleFormat extends JsonSerializer<Double> {
    DecimalFormat df = new DecimalFormat("###.##");
    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if(value !=null){
            gen.writeString(df.format(value));
        }
    }
}
