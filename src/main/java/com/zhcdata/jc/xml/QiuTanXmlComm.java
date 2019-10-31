package com.zhcdata.jc.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.zhcdata.jc.tools.HttpUtils;
import com.zhcdata.jc.xml.rsp.MoreHandicapOddsARsp;
import com.zhcdata.jc.xml.rsp.MoreHandicapOddsLisAlltRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  private final Logger LOGGER = LoggerFactory.getLogger(getClass());
  public Object handleMothodHttpGet(String url, Class ...clas){
    String xml = "";
    Object obj = null;
    try {
      xml = HttpUtils.httpGet(url,"UTF-8");
      XStream xStream = new XStream(new DomDriver());
      XStream.setupDefaultSecurity(xStream);
      xStream.allowTypes(clas);
      xStream.processAnnotations(clas);
      xml = xml.replaceAll("\uFEFF", "");
      obj = xStream.fromXML(xml);
    } catch (Exception e) {
      LOGGER.error("解析异常 返回长度="+xml.length()+" url"+url+"="+xml,e);
      e.printStackTrace();
    }
    return obj;
  }
  public Object handleMothod(String url, Class ...clas){
    String xml = "";
    Object obj = null;
    try {
      xml = HttpUtils.httpPost(url,"UTF-8");
      XStream xStream = new XStream(new DomDriver());
      XStream.setupDefaultSecurity(xStream);
      //xStream.allowTypes(new Class[]{List.class,clas});
      xStream.allowTypes(clas);
      xStream.processAnnotations(clas);
      //List<T> list = (List<T>) xStream.fromXML(xml);
      obj = xStream.fromXML(xml);
    } catch (Exception e) {
      LOGGER.error("解析异常 返回长度="+xml.length()+" url"+url+"="+xml,e);
    }
    return obj;
  }
  public List<T> handleMothodList(String url, Class ...clas){
    try {
      String xml = "";
      List<T> list = null;
      try {
        xml = HttpUtils.httpPost(url, "UTF-8");
        XStream xStream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xStream);
        //xStream.allowTypes(new Class[]{List.class,clas});
        xStream.allowTypes(clas);
        xStream.processAnnotations(clas);
        list = (List<T>) xStream.fromXML(xml);
      } catch (Exception e) {
        LOGGER.error("解析异常 返回长度="+xml.length()+" url"+url+"="+xml,e);
      }
      return list;
    }catch (Exception ex){
      String sd=ex.getMessage();
      ex.printStackTrace();
    }
    return null;
  }

  public List<T> handleMothodTeamList(String url, Class ...clas){
      String xml = "";
      List<T> list = null;
      try {
        xml = HttpUtils.getHtmlResult(url);
        XStream xStream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xStream);
        //xStream.allowTypes(new Class[]{List.class,clas});
        xStream.allowTypes(clas);
        xStream.processAnnotations(clas);
        list = (List<T>) xStream.fromXML(xml);
      } catch (Exception e) {
        LOGGER.error("解析异常 返回长度="+xml.length()+" url"+url+"="+xml,e);
      }
      return list;
  }

  public static void main(String argsp[]){
    //String xml = HttpUtils.httpPost("http://interface.win007.com/zq/today.aspx","UTF-8");
   /* List<ToDayMatchRsp> list  = (List<ToDayMatchRsp>) new QiuTanXmlComm().handleMothod("http://interface.win007.com/zq/today.aspx",List.class,ToDayMatchRsp.class);
    for (ToDayMatchRsp a : list) {
      System.out.println(a.getID());

    }*/

  /* String url = "http://interface.win007.com/zq/JcZqOdds.aspx";
    LotteryTypeMatchFristRsp object  = (LotteryTypeMatchFristRsp) new QiuTanXmlComm().handleMothod(url,LotteryTypeMatchFristRsp.class,LotteryTypeMatchRsp.class);
    List<LotteryTypeMatchRsp> list = object.getList();
    for (LotteryTypeMatchRsp lotteryTypeMatchRsp : list) {
      System.out.println(lotteryTypeMatchRsp.getID());
    }*/
    String url = "http://interface.win007.com/zq/ch_odds_m.xml";
    MoreHandicapOddsLisAlltRsp rsp  = (MoreHandicapOddsLisAlltRsp) new QiuTanXmlComm().handleMothodHttpGet(url,MoreHandicapOddsLisAlltRsp.class,List.class,MoreHandicapOddsARsp.class);
    System.out.println(rsp);
    List<String> listA = rsp.getA().getH();
    for (String bean : listA) {
      System.out.println(bean);
    }
  }
}
