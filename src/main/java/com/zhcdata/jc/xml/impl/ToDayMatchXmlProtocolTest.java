package com.zhcdata.jc.xml.impl;

import com.zhcdata.jc.xml.BaseXmlProtocolTest;
import com.zhcdata.jc.xml.rsp.ToDayMatchRsp;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;

/**
 * CopyRight (c)1999-2019 : zhcw.com
 * Project : jc-new-server
 * Create Date : 2019/9/10 14:57
 * JDK version : JDK1.8
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 *
 * @author : 高阳
 * @version : 0.0.1
 */
public class ToDayMatchXmlProtocolTest implements BaseXmlProtocolTest {


  @Override
  public String ReqXml(String url) {
    return null;
  }
  @Override
  public List xmlHandleMethod(String xml, Class tClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    //Class<? extends Object> tClass = t.getClass();
    //得到所有属性
    Field[] field = tClass.getDeclaredFields();
    //设置可以访问私有变量
    field[0].setAccessible(true);
    String aa = "1";
    for(Field f :field){
      System.out.println(f.getName());
      String methodName = "set" + f.getName().substring(0,1).toUpperCase()+f.getName().substring(1);
      System.out.println(methodName);
      Method method =  tClass.getDeclaredMethod(methodName, new Class[]{f.getType()});

      //method.invoke(f, new Object[]{getClassTypeValue(f.getType(), aa)});
    }
    return null;
  }
  private static Object getClassTypeValue(Class<?> typeClass, Object value){
    if(typeClass == int.class  || value instanceof Integer){
      if(null == value){
        return 0;
      }
      return value;
    }else if(typeClass == short.class){
      if(null == value){
        return 0;
      }
      return value;
    }else if(typeClass == byte.class){
      if(null == value){
        return 0;
      }
      return value;
    }else if(typeClass == double.class){
      if(null == value){
        return 0;
      }
      return value;
    }else if(typeClass == long.class){
      if(null == value){
        return 0;
      }
      return value;
    }else if(typeClass == String.class){
      if(null == value){
        return "";
      }
      return value;
    }else if(typeClass == boolean.class){
      if(null == value){
        return true;
      }
      return value;
    }else if(typeClass == BigDecimal.class){
      if(null == value){
        return new BigDecimal(0);
      }
      return new BigDecimal(value+"");
    }else {
      return typeClass.cast(value);
    }
  }
  public static void main(String args[]) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

    ToDayMatchRsp rsp = new ToDayMatchRsp();
    new ToDayMatchXmlProtocolTest().xmlHandleMethod("",ToDayMatchRsp.class);

  }
}