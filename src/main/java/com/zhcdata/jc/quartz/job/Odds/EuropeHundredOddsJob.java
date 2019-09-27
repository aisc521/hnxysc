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

import com.zhcdata.db.mapper.EuropeOddsMapper;
import com.zhcdata.db.model.EuropeOdds;
import com.zhcdata.jc.tools.BeanUtils;
import com.zhcdata.jc.tools.HttpUtils;
import com.zhcdata.jc.xml.rsp.EuropeHundredOddsRsp.C;
import com.zhcdata.jc.xml.rsp.EuropeHundredOddsRsp.EuropeHundredOddsRsp;
import com.zhcdata.jc.xml.rsp.EuropeHundredOddsRsp.H;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;

//百家欧赔
@Configuration
@EnableScheduling
public class EuropeHundredOddsJob {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Resource
    private EuropeOddsMapper europeOddsMapper;

    //@Scheduled(cron = "1 * * * * ?")
   /* @Scheduled(fixedRate = 90000)*/
    public void execute() throws Exception {
        LOGGER.info("百欧赔率表解析开始");
        long sat = System.currentTimeMillis();
        int insert = 0;
        int update = 0;
        int jumped = 0;
        int odds = 0;
        int odds1 = 0;
        String str = HttpUtils.httpGet("http://interface.win007.com/zq/1x2.aspx?day=3", null);
        JSONObject jsonObject = XML.toJSONObject(str);
        String s = jsonObject.toString();
        EuropeHundredOddsRsp obj = com.alibaba.fastjson.JSONObject.parseObject(s, EuropeHundredOddsRsp.class);

        List<H> items = obj.getC().getH();
        System.out.println("共有比赛场次：" + items.size());
        for (int i = 0; i < items.size(); i++) {
            odds1 = odds1 + items.get(i).getOdds().getO().size();
        }
        System.out.println("共有赔率条数：" + odds1);


        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getOdds() != null) {
                List<String> o = items.get(i).getOdds().getO();
                //System.out.println(BeanUtils.parseTime(items.get(i).getTime()).getTime() +" - "+ System.currentTimeMillis());
                if (BeanUtils.parseTime(items.get(i).getTime()).getTime() < System.currentTimeMillis())
                    continue;

                for (int j = 0; j < o.size(); j++) {
                    odds++;
                    String[] mo = o.get(j).split(",");
                    //博彩公司ID,博彩公司英文名,初盘主胜,初盘平局,初盘客胜,主胜,平局,客胜,变化时间,博彩公司简体名
                    EuropeOdds dbl = europeOddsMapper.selectByMidAndCpyAnd(items.get(i).getId(), mo[0]);
                    EuropeOdds xml = BeanUtils.parseEuropeOdds(items.get(i).getId(), mo);
                    if (dbl == null) {
                        //没有 需要插入
                        if (europeOddsMapper.insertSelective(xml) > 0) insert++;
                    } else {
                        //对比赔率是否相同
                        if (dbl.oddsEquals(xml))
                            jumped++;
                        else {
                            dbl.setRealhomewin(xml.getRealhomewin());
                            dbl.setRealstandoff(xml.getRealstandoff());
                            dbl.setRealguestwin(xml.getRealguestwin());
                            dbl.setModifytime(xml.getModifytime());
                            if (europeOddsMapper.updateByPrimaryKeySelective(dbl) > 0)
                                update++;
                        }
                    }
                }
            }else System.out.println("1111111111111111111111");
        }
        LOGGER.info("百欧赔率表,总:"+odds+"新增:" + insert + ",跳过:" + jumped + ",更新:" + update + ",耗时:" + (System.currentTimeMillis() - sat));
        LOGGER.info("-------------");

    }
}