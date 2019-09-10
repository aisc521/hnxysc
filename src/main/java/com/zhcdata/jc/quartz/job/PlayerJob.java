package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.TbPlayerMapper;
import com.zhcdata.db.model.PlayerInfo;
import com.zhcdata.jc.tools.HttpUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

/**
 * 球员资料
 */
@Configuration
@EnableScheduling
public class PlayerJob {

    @Resource
    TbPlayerMapper tbPlayerMapper;

    @Async
    @Scheduled(cron = "21 37 18 ? * *")
    public void work() {
        try {
            String url = "http://interface.win007.com/zq/Player_XML.aspx?day=1";
            String re = HttpUtils.getHtmlResult(url);

            Document doc = DocumentHelper.parseText(re);
            Element rootElt = doc.getRootElement();                    // 获取根节点
            System.out.println("根节点：" + rootElt.getName());         // 拿到根节点的名称
            Iterator iter = rootElt.elementIterator("i");
            while (iter.hasNext()) {
                Element recordEle = (Element) iter.next();
                Attribute UniqueMatchIdAttr = recordEle.attribute("id");
                String UniqueMatchId = UniqueMatchIdAttr.getValue();    //联赛ID
                System.out.println("id：" + UniqueMatchId);
            }

            List<PlayerInfo> list = tbPlayerMapper.queryPlayer("--");

            String sdssd = "";
        } catch (Exception ex) {
            System.out.println("异常信息：" + ex.getMessage());
        }

    }
}
