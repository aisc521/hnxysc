/**
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　　　 ████━████ ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　create by xuan on 2019/9/10
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 */
package com.zhcdata.jc.quartz.job.Match;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.javafx.collections.MappingChange;
import com.zhcdata.jc.tools.HttpUtils;
import com.zhcdata.jc.tools.xml.XmlUtils;
import com.zhcdata.jc.xml_model.MatchList;
import com.zhcdata.jc.xml_model.MatchListModel;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;

@Configuration
@EnableScheduling
public class MatchListJob{

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Scheduled(cron = "0/5 * * * * ?")
    public void execute() throws Exception {
        LOGGER.info("赛程赛果定时任务启动");long s = System.currentTimeMillis();
        String xmlStr = HttpUtils.httpGet("http://interface.win007.com/zq/BF_XML.aspx?date=2019-09-10", null);
        JSONObject jsonObject = XmlUtils.xml2Json(xmlStr);
        if (jsonObject.toJSONString().contains("访问频率超出限制")){
            LOGGER.error(jsonObject.toJSONString());
            return;
        }
        Map<String,JSONObject> map = JSONObject.parseObject(jsonObject.toJSONString(), Map.class);
        JSONObject list = map.get("list");
        JSONArray match = (JSONArray)list.get("match");
        List<MatchListModel> models = match.toJavaList(MatchListModel.class);
        System.out.println(models.size());
        LOGGER.info("赛程赛果定时任务结束,耗时："+(System.currentTimeMillis()-s)+"毫秒");
    }
}