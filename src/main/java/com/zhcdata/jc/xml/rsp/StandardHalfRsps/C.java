package com.zhcdata.jc.xml.rsp.StandardHalfRsps;


import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

//@XmlRootElement(name="h")
@Data
public class C{
   private List<H> h;
}

