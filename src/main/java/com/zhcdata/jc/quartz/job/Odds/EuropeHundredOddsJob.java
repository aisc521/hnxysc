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
 * 　　　　　　　　　┃　　　┃　　　　create by xuan on 2019/9/11
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
package com.zhcdata.jc.quartz.job.Odds;

import com.zhcdata.jc.tools.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

//百家欧赔
@Configuration
@EnableScheduling
public class EuropeHundredOddsJob {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    //@Scheduled(cron = "0/20 * * * * ?")
    public void execute() throws Exception {
        String str = HttpUtils.httpGet("http://interface.win007.com/zq/Odds_Mult.aspx", null);
        String[] oddsCollection = str.split("\\u0024");
        if (oddsCollection.length==5){
            //解析 第一部分，亚赔（让球盘）
            String[] LetBall = oddsCollection[0].split(";");
            String[] Europe = oddsCollection[1].split(";");
            String[] BigMin = oddsCollection[2].split(";");
            System.out.println("共有亚盘条数:"+LetBall.length);
            List<String> singleAsia = new ArrayList<>();
            for (int i = 0; i < LetBall.length; i++) {
                if (LetBall[i].split(",")[10].equals("1"))
                    singleAsia.add(LetBall[i]);
            }
            System.err.println("亚盘单盘口条数:" + singleAsia.size());
        }else System.out.println("多盘赔率接口解析错误，collection size："+oddsCollection.length);
        LOGGER.info("多盘赔率接口解析完成");
    }
}