package com.zhcdata.jc.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.zhcdata.jc.tools.HttpUtils;
import com.zhcdata.jc.xml.rsp.ToDayMatchRsp;

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
    try {
      String xml = "";
      try {
        xml = HttpUtils.httpPost(url, "UTF-8");

      } catch (Exception e) {
        e.printStackTrace();
      }
      XStream xStream = new XStream(new DomDriver());
      XStream.setupDefaultSecurity(xStream);
      xStream.allowTypes(new Class[]{List.class, clas});
      xStream.processAnnotations(clas);
      List<T> list = (List<T>) xStream.fromXML(xml);

      return list;
    }catch (Exception ex){
      String str=ex.getMessage();
    }
    return null;
  }
  public static void main(String argsp[]){
    //String xml = HttpUtils.httpPost("http://interface.win007.com/zq/today.aspx","UTF-8");
    List<ToDayMatchRsp> list  = new QiuTanXmlComm().handleMothod("http://interface.win007.com/zq/today.aspx",ToDayMatchRsp.class);
    for (ToDayMatchRsp a : list) {
      System.out.println(a.getID());

    }
  }
}
