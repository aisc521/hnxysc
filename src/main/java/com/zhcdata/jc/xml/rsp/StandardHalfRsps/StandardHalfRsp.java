/*create by xuan on 2019/9/20

 */
package com.zhcdata.jc.xml.rsp.StandardHalfRsps;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="c")
@Data
public class StandardHalfRsp{
    private C c;
}