/*create by xuan on 2019/9/11*/
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
import org.json.XML;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.zhcdata.jc.quartz.job.Odds.FlagInfo.*;

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

    @Resource
    private EuropeoddstotalMapper europeoddstotalMapper;

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
            log.error("百欧赔率表解析错误", e);
            e.printStackTrace();
        } finally {
            europe_odds_flag = false;
        }
    }

    private void runs() {
        List<String> mc = europe_odds_mc;//存的是 比赛id：公司id
        String str = null;
        try {
            str = HttpUtils.httpGet("http://interface.win007.com/zq/1x2.aspx", null);
            log.error("百欧赔率表接口获取完成" + str.length());
            if (str.contains("频率超出限制")) {
                log.error("百欧赔率表接口EuropeHundredOddsJob" + str + ",请求时间:" + sdf_X.format(new Date()));
                return;
            }
        } catch (Exception e) {
            log.error("百欧赔率表接口获取失败", e);
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
                log.error("百欧赔率表接口,比赛时间格式化失败，跳过比赛" + item.getId(), e);
                continue;
            }
            List<String> os = item.getOdds().getO();
            for (String o : os) {
                try {

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
                            db = new BeanUtils().parseEuropeOdds(item.getId(), o.split(","));//parse
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
                                    log.error("百欧主表新增百欧详情表错误", e);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        log.info("百欧共有比赛场次：" + items.size());
        if (EuropeTotalRun){
            EuropeTotalRun = false;
            String result = totalHandle(obj);
            log.info("百欧total表解析完成" + result);
        }else EuropeTotalRun = true;
    }


    private String totalHandle(EuropeHundredOddsRsp obj) {
        try {
            long sat = System.currentTimeMillis();
            int odds = 0;
            if (obj == null || obj.getC() == null || obj.getC().getH() == null || obj.getC().getH().size() == 0)
                return "obj检查不合法";
            List<H> items = obj.getC().getH();
            int insert = 0;
            int update = 0;
            for (H item : items) {
                float avg_win = 0;
                float avg_and = 0;
                float avg_lose = 0;
                short cpy_num = 0;

                if (item.getOdds() != null) {
                    List<String> o = item.getOdds().getO();
                    if (BeanUtils.parseTime(item.getTime()).getTime() < System.currentTimeMillis())
                        continue;//如果变化时间在比赛开始时间之后，那就跳过这条变化

                    for (String value : o) {
                        odds++;
                        String[] mo = value.split(",");
                        //博彩公司ID,博彩公司英文名,初盘主胜,初盘平局,初盘客胜,主胜,平局,客胜,变化时间,博彩公司简体名
                        EuropeOdds xml = new BeanUtils().parseEuropeOdds(item.getId(), mo);
                        if (xml.getRealhomewin() != null && xml.getRealstandoff() != null && xml.getRealguestwin() != null) {
                            avg_win += xml.getRealhomewin();
                            avg_and += xml.getRealstandoff();
                            avg_lose += xml.getRealguestwin();
                            cpy_num++;
                        }
                    }

                    Europeoddstotal total = new Europeoddstotal();
                    Europeoddstotal db = europeoddstotalMapper.selectByPrimaryKey(Integer.parseInt(item.getId()));
                    if (db == null) {
                        if (avg_win != 0 && avg_and != 0 && avg_win != 0) {
                            total.setScheduleid(Integer.parseInt(item.getId()));
                            total.setFirsthomewin(avg_win / cpy_num);
                            total.setFirststandoff(avg_and / cpy_num);
                            total.setFirstguestwin(avg_lose / cpy_num);
                            total.setRealhomewin(avg_win / cpy_num);
                            total.setRealstandoff(avg_and / cpy_num);
                            total.setRealguestwin(avg_lose / cpy_num);
                            total.setNum(cpy_num);
                            if (europeoddstotalMapper.insertSelective(total) > 0)
                                insert++;
                        }
                    } else {
                        if (avg_win != 0 && avg_and != 0 && avg_win != 0) {
                            db.setRealhomewin(avg_win / cpy_num);
                            db.setRealstandoff(avg_and / cpy_num);
                            db.setRealguestwin(avg_lose / cpy_num);
                            db.setNum(cpy_num);
                            if (europeoddstotalMapper.updateByPrimaryKeySelective(db) > 0)
                                update++;
                        }
                    }
                }
            }
            return "百欧赔率total表,总:" + odds + ",新增:" + insert + ",更新:" + update + ",耗时:" + (System.currentTimeMillis() - sat);
        } catch (Exception e) {
            log.error("百欧total处理异常", e);
            return "";
        }
    }
}