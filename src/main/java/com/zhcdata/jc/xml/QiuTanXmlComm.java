package com.zhcdata.jc.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.zhcdata.jc.tools.HttpUtils;

import java.util.List;

/**
 * CopyRight (c)1999-2019 : zhcw.com
 * Project : jc-new-server
 * Create Date : 2019/9/10 18:36
 * JDK version : JDK1.8
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 *
 * @author : 高阳
 * @version : 0.0.1
 */
public class QiuTanXmlComm<T>  {

  public List<T> handleMothod(String url, Class<T> clas){
    String xml = "";
    try {
      xml = HttpUtils.httpPost(url,"UTF-8");

    } catch (Exception e) {
      e.printStackTrace();
    }
    XStream xStream = new XStream(new DomDriver());
    XStream.setupDefaultSecurity(xStream);
    xStream.allowTypes(new Class[]{List.class,clas});
    xStream.processAnnotations(clas);
    List<T> list = (List<T>) xStream.fromXML(xml);

    return list;
  }
}
