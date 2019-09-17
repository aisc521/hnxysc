package com.zhcdata.jc.xml.rsp;

import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * CopyRight (c)1999-2019 : zhcw.com
 * Project : jc-new-server
 * Create Date : 2019/9/16 18:29
 * JDK version : JDK1.8
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 *
 * @author : 高阳
 * @version : 0.0.1
 */
@Getter
@Setter
public class MoreHandicapOddsARsp {
  @XStreamImplicit(itemFieldName="h")
  private List<String> h;
}
