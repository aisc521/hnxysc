package com.zhcdata.jc.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

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
public abstract class  BaseXmlAbstractProtocol implements BaseXmlProtocol{

  public List<Element> xmlRootHandle(String xml, String node) throws DocumentException {
    Document doc = null;
    doc = DocumentHelper.parseText(xml); // 将字符串转为XML
    Element rootElt = doc.getRootElement(); // 获取根节点
   // System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
    List<Element> list = rootElt.elements(node);
    return list;
  }

}
