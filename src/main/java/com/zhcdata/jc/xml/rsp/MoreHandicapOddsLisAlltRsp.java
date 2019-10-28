package com.zhcdata.jc.xml.rsp;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * CopyRight (c)1999-2019 : zhcw.com
 * Project : jc-new-server
 * Create Date : 2019/9/16 18:28
 * JDK version : JDK1.8
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 *
 * @author : 高阳
 * @version : 0.0.1
 */
@Setter
@Getter
@XStreamAlias("c")
@ToString
public class MoreHandicapOddsLisAlltRsp {

    //@XStreamImplicit(itemFieldName="a")
    MoreHandicapOddsARsp a;
    //@XStreamImplicit(itemFieldName="o")
    MoreHandicapOddsARsp o;
    //@XStreamImplicit(itemFieldName="d")
    MoreHandicapOddsARsp d;
   // @XStreamImplicit(itemFieldName="ah")
    MoreHandicapOddsARsp ah;
    //@XStreamImplicit(itemFieldName="dh")
    MoreHandicapOddsARsp dh;
}
