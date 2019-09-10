package com.zhcdata.jc.xml;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * CopyRight (c)1999-2019 : zhcw.com
 * Project : jc-new-server
 * Create Date : 2019/9/9 20:43
 * JDK version : JDK1.8
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 * @author : 高阳
 * @version : 0.0.1
 */
public interface BaseXmlProtocolTest<T> {

  public String ReqXml(String url);

  public List<T> xmlHandleMethod(String xml,Class c) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

}
