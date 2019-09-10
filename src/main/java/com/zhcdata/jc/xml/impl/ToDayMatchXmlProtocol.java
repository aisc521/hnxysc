package com.zhcdata.jc.xml.impl;

import com.zhcdata.jc.xml.BaseXmlAbstractProtocol;
import com.zhcdata.jc.xml.BaseXmlProtocol;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
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
@Service
public class ToDayMatchXmlProtocol<ToDayMatchRsp> extends BaseXmlAbstractProtocol implements BaseXmlProtocol {

  @Value("${custom.qiutan.url.todayurl}")
  String url;
  @Override
  public String getUrl() {
    return url;
  }
  @Override
  public List<ToDayMatchRsp> xmlHandleMethod(String xml) throws Exception {
    List<ToDayMatchRsp> returnList = new ArrayList<>();
    List<Element> list = xmlRootHandle(xml,"match");
    for(Element e:list){
      System.out.println("ID="+e.elementTextTrim("ID"));
    }
    return returnList;
  }
}