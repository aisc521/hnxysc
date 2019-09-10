package com.zhcdata.jc.xml;

import com.zhcdata.jc.protocol.ProtocolFactory;
import com.zhcdata.jc.tools.HttpUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CopyRight (c)1999-2019 : zhcw.com
 * Project : jc-new-server
 * Create Date : 2019/9/10 16:34
 * JDK version : JDK1.8
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 *
 * @author : 高阳
 * @version : 0.0.1
 */
@Service
public class QiuTanXmlUntil<T> {

  public List<T> handleMothod(String name){

    BaseXmlProtocol<T> baseProtocol = ProtocolFactory.getQiuTanProtocolInstance(name);
    String url = baseProtocol.getUrl();
    String xml = "";
    List<T> list = null;
    try {
      xml = HttpUtils.httpPost(url,"UTF-8");
      list = baseProtocol.xmlHandleMethod(xml);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return list;
  }
}
