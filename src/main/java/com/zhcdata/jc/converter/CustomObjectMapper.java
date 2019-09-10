package com.zhcdata.jc.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.zhcdata.jc.tools.Const;
import org.springside.modules.utils.time.DateFormatUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * CopyRight (c)1999-2017 : zhcw.com
 * Project : zhcw-framework
 * Comments : 自定义Jackson ObjectMapper 设置json返回样式(spring mvc中使用)
 * JDK version : JDK1.8
 * Create Date : 2017/7/7 17:13
 *
 * @author : Watson W
 * @version : 1.0
 * @since : 1.0
 */
public class CustomObjectMapper extends ObjectMapper {

  private static final long serialVersionUID = 3162916457983273657L;

  /**
   * 设置BigDecimal格式化小数点两位样式
   */
  private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

  public CustomObjectMapper() {
    super();
    // 数字也加引号
    this.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
    this.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
    this.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    this.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

    // 设置null转换""
    this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
      @Override
      public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers)
        throws IOException, JsonProcessingException {
        gen.writeString("");
      }
    });

    // BigDecimal保留小数点后两位为0配置
    SimpleModule moduleBigDecimal = new SimpleModule();
    moduleBigDecimal.addSerializer(BigDecimal.class, new JsonSerializer<BigDecimal>() {
      @Override
      public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers)
        throws IOException, JsonProcessingException {
        //DECIMAL_FORMAT.setRoundingMode(RoundingMode.HALF_UP); //默认不开启四舍五入 需要四舍五入的时候开启这个
        gen.writeString(DECIMAL_FORMAT.format(value));
      }
    });

    // Date统一格式化样式
    SimpleModule moduleTime = new SimpleModule();
    moduleTime.addSerializer(Date.class, new JsonSerializer<Date>() {
      @Override
      public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)
        throws IOException, JsonProcessingException {
        gen.writeString(DateFormatUtil.formatDate(Const.YYYY_MM_DD_HH_MM_SS, value));
      }
    });
    this.registerModules(moduleBigDecimal, moduleTime);
  }
}
