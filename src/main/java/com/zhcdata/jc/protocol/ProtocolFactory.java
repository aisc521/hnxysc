package com.zhcdata.jc.protocol;

import com.zhcdata.jc.tools.SpringUtil;

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
public class ProtocolFactory {
    public static BaseProtocol getProtocolInstance(String protocolCode) {
        return SpringUtil.getBean(protocolCode, BaseProtocol.class);
    }

    public static void main(String[] args) {

    }
}
