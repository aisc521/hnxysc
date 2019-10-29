package com.zhcdata.jc.protocol;

import com.zhcdata.jc.tools.SpringUtil;
import com.zhcdata.jc.xml.BaseXmlProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Title:
 * Description:
 * Copyright: Copyright (c) 2019
 * Company: 北京世纪中彩数据技术有限公司-互联网事业部
 *
 * @author Yore
 * @version 1.0
 * @Date 2019/3/20 10:27
 */
@Component
public class ProtocolFactory {
    @Autowired
    private final Map<String, BaseProtocol> strategyMap = new ConcurrentHashMap<>(25);

    public BaseProtocol getProtocolInstance(String protocolCode) {
        return strategyMap.get(protocolCode);
    }
    public static BaseXmlProtocol getQiuTanProtocolInstance(String protocolCode) {
        return SpringUtil.getBean(protocolCode, BaseXmlProtocol.class);
    }
    public static void main(String[] args) {

    }
}
