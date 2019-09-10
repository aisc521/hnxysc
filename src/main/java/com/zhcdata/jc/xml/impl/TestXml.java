package com.zhcdata.jc.xml.impl;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.zhcdata.jc.tools.HttpUtils;
import com.zhcdata.jc.xml.rsp.ToDayMatchRsp;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.List;

/**
 * CopyRight (c)1999-2019 : zhcw.com
 * Project : jc-new-server
 * Create Date : 2019/9/9 21:18
 * JDK version : JDK1.8
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 *
 * @author : 高阳
 * @version : 0.0.1
 */
public class TestXml {

   public static List<Element> xmlRootHandle(String xml,String node) throws DocumentException {
     Document doc = null;
     doc = DocumentHelper.parseText(xml); // 将字符串转为XML
     Element rootElt = doc.getRootElement(); // 获取根节点
     System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
     List<Element> list = rootElt.elements(node);
     return list;
   }

    public static void main(String args[]) throws Exception {

      String xml = HttpUtils.httpPost("http://interface.win007.com/zq/today.aspx","UTF-8");


      /*List<Element> list = xmlRootHandle(xml,"match");
      for(Element e:list){
        System.out.println("ID="+e.elementTextTrim("ID"));
      }*/
      XStream xStream = new XStream(new DomDriver());
      XStream.setupDefaultSecurity(xStream);
      xStream.allowTypes(new Class[]{List.class, ToDayMatchRsp.class});
      xStream.processAnnotations(ToDayMatchRsp.class);
      List<ToDayMatchRsp> list = (List<ToDayMatchRsp>) xStream.fromXML(xml);
      for (ToDayMatchRsp toDayMatchRsp : list) {
        System.out.println(toDayMatchRsp.getID());
      }


    }
}
