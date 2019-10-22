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

import com.zhcdata.db.mapper.EuropeOddsDetailMapper;
import com.zhcdata.db.mapper.EuropeOddsMapper;
import com.zhcdata.db.mapper.EuropeoddstotalMapper;
import com.zhcdata.db.model.EuropeOdds;
import com.zhcdata.db.model.EuropeOddsDetail;
import com.zhcdata.db.model.Europeoddstotal;
import com.zhcdata.jc.tools.BeanUtils;
import com.zhcdata.jc.tools.HttpUtils;
import com.zhcdata.jc.xml.rsp.EuropeHundredOddsRsp.EuropeHundredOddsRsp;
import com.zhcdata.jc.xml.rsp.EuropeHundredOddsRsp.H;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.json.XML;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.zhcdata.jc.quartz.job.Odds.FlagInfo.europe_odds_flag;
import static com.zhcdata.jc.quartz.job.Odds.FlagInfo.europe_odds_mc;

//百家欧赔
@SuppressWarnings("SpringJavaAutowiringInspection")
//@Configuration
//@EnableScheduling
@Component
@Slf4j
public class EuropeHundredOddsJob implements Job {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private static final SimpleDateFormat sdf_X = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Resource
    private EuropeOddsMapper europeOddsMapper;

    //@Resource
    //private EuropeoddstotalMapper europeoddstotalMapper;

    @Resource
    private EuropeOddsDetailMapper europeOddsDetailMapper;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        if (europe_odds_flag)
            return;
        try {
            europe_odds_flag = true;
            LOGGER.info("百欧赔率表解析开始");
            runs();
        } catch (Exception e) {
            log.error("百欧赔率表解析错误" + e);
            e.printStackTrace();
        } finally {
            europe_odds_flag = false;
        }
    }

    public void runs() {
        List<String> mc = europe_odds_mc;//存的是 比赛id：公司id
        String str = null;
        try {
            //str = HttpUtils.httpGet("http://interface.win007.com/zq/1x2.aspx?day=3", null);
            str = HttpUtils.httpGet("http://interface.win007.com/zq/1x2.aspx", null);
        } catch (Exception e) {
            log.error("百欧赔率表接口获取失败" + e.toString());
        }
        EuropeHundredOddsRsp obj = com.alibaba.fastjson.JSONObject.parseObject(XML.toJSONObject(str).toString(), EuropeHundredOddsRsp.class);
        List<H> items = obj.getC().getH();
        for (H item : items) {
            try {
                if (sdf_X.parse(item.getTime()).getTime() < System.currentTimeMillis())
                    continue;
                if (item.getOdds() == null || item.getOdds().getO() == null)
                    continue;
            } catch (ParseException e) {
                log.error("百欧赔率表接口,比赛时间格式化失败，跳过比赛" + item.getId());
                continue;
                //e.printStackTrace();
            }
            List<String> os = item.getOdds().getO();
            for (String o : os) {
                try {
                    if ((item.getId() + ":" + o.split(",")[0]).equals("1727683:1129")){
                        System.out.println("--------------");
                        System.out.println(mc.contains(item.getId() + ":" + o.split(",")[0]));
                        System.out.println("sdf");
                    }
                    if (!mc.contains(item.getId() + ":" + o.split(",")[0])) {
                        //log.info("mc list中包含比赛:" + item.getId() + ",公司id:" + o.split(",")[0]);
                        //} else {//首次添加，检查europe有没有，往europe中添加数据
                        if (europe_odds_mc.size() > 100000)//如果mc里面超过15w条，移除1条
                            europe_odds_mc.remove(0);
                        europe_odds_mc.add(item.getId() + ":" + o.split(",")[0]);//新增到mc
                        if (StringUtils.isEmpty(item.getId()))
                            continue;
                        EuropeOdds db = europeOddsMapper.selectByMidAndCpyAnd(item.getId(), o.split(",")[0]);
                        if (db == null) {//新增
                            db = BeanUtils.parseEuropeOdds(item.getId(), o.split(","));//parse
                            if (europeOddsMapper.insertSelective(db) > 0) {
                                try {
                                    EuropeOdds afterInsert = europeOddsMapper.selectByMidAndCpyAnd(item.getId(), o.split(",")[0]);

                                    //子表新增初赔信息
                                    EuropeOddsDetail detail = new EuropeOddsDetail();
                                    detail.setHomewin(db.getFirsthomewin());
                                    detail.setStandoff(db.getFirststandoff());
                                    detail.setGuestwin(db.getFirstguestwin());
                                    detail.setModifytime(db.getModifytime());
                                    detail.setOddsid(afterInsert.getOddsid());
                                    if (europeOddsDetailMapper.insertSelective(detail) > 0) {
                                        log.error("百欧主表新增百欧详情表" + detail.toString());
                                    }
                                } catch (Exception e) {
                                    log.error("百欧主表新增百欧详情表错误"+e);
                                }
                            }
                        }
                    }else {
                        System.out.println(mc.indexOf("1727683:1129"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("共有比赛场次：" + items.size());
    }
}