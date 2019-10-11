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
import com.zhcdata.db.model.EuropeOdds;
import com.zhcdata.db.model.EuropeOddsDetail;
import com.zhcdata.jc.tools.BeanUtils;
import com.zhcdata.jc.tools.HttpUtils;
import com.zhcdata.jc.xml.rsp.EuropeHundredOddsRsp.EuropeHundredOddsRsp;
import com.zhcdata.jc.xml.rsp.EuropeHundredOddsRsp.H;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

//百欧变化
@SuppressWarnings("SpringJavaAutowiringInspection")
//@Configuration
//@EnableScheduling
@Component
@Slf4j
public class EuropeHundredOddsChangedJob implements Job {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private static final SimpleDateFormat sdf_X = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Resource
    private EuropeOddsMapper europeOddsMapper;

    @Resource
    private EuropeOddsDetailMapper detailMapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOGGER.info("百欧赔率变化解析开始");
        long sat = System.currentTimeMillis();
        int insert = 0;
        int update = 0;
        int jumped = 0;
        int nexted = 0;
        String str = null;
        try {
            str = HttpUtils.httpGet("http://interface.win007.com/zq/1x2.aspx?min=3", null);
            if (str.contains("访问频率超出限制")){
                log.error("百欧赔率变化解析完成,访问频率超出限制");
                return;
            }

        } catch (Exception e) {
            LOGGER.error("百欧赔率变化解析获取变化失败"+e.toString());
            return;
        }
        EuropeHundredOddsRsp obj = com.alibaba.fastjson.JSONObject.parseObject(XML.toJSONObject(str).toString(), EuropeHundredOddsRsp.class);
        List<H> items = obj.getC().getH();//每场比赛
        for (int i = 0; i < items.size(); i++) {
            try {
                if (sdf_X.parse(items.get(i).getTime()).getTime()<System.currentTimeMillis())
                    continue;
            } catch (ParseException e) {
                LOGGER.error("百欧赔率表接口,比赛时间格式化失败，跳过比赛"+items.get(i).getId());
                continue;
            }
            List<String> o = items.get(i).getOdds().getO();//每家公司的赔率
            if (BeanUtils.parseTime(items.get(i).getTime()).getTime()<System.currentTimeMillis())
                continue;
            for (int j = 0; j < o.size(); j++) {
                String[] mo = o.get(j).split(",");//当前比赛 当前公司
                //博彩公司ID,博彩公司英文名,初盘主胜,初盘平局,初盘客胜,主胜,平局,客胜,变化时间,博彩公司简体名
                //根据比赛 公司 查询这个赔率 如果没有跳过
                EuropeOdds dbl = europeOddsMapper.selectByMidAndCpyAnd(items.get(i).getId(),mo[0]);
                if (dbl==null)
                    nexted++;
                else {
                    //这局oddsId查询最新的
                    EuropeOddsDetail db = detailMapper.selectByOddsNewest(dbl.getOddsid());
                    EuropeOddsDetail xml = BeanUtils.parseEuropeOddsDetail(mo);
                    if (db==null){
                        //第一次添加
                        xml.setOddsid(dbl.getOddsid());
                        if (detailMapper.insertSelective(xml)>0) insert++;
                        //变更主表
                        dbl.setRealstandoff(xml.getStandoff());
                        dbl.setRealhomewin(xml.getHomewin());
                        dbl.setRealguestwin(xml.getGuestwin());
                        dbl.setStandoffR(xml.getStandoff());
                        dbl.setHomewinR(xml.getHomewin());
                        dbl.setGuestwinR(xml.getGuestwin());
                        europeOddsMapper.updateByPrimaryKeySelective(dbl);
                    }else {
                        if (db.oddsEquals(xml))
                            jumped++;//相同时间 相同赔率 跳过
                        else {
                            //赔率不相同 更新（同样插入一条记录）
                            db.setId(null);
                            db.setGuestwin(xml.getGuestwin());
                            db.setStandoff(xml.getStandoff());
                            db.setHomewin(xml.getHomewin());
                            if (detailMapper.insertSelective(db)>0)
                                update++;
                            //变更主表
                            dbl.setRealstandoff(xml.getStandoff());
                            dbl.setRealhomewin(xml.getHomewin());
                            dbl.setRealguestwin(xml.getGuestwin());
                            dbl.setStandoffR(xml.getStandoff());
                            dbl.setHomewinR(xml.getHomewin());
                            dbl.setGuestwinR(xml.getGuestwin());
                            europeOddsMapper.updateByPrimaryKeySelective(dbl);
                        }
                    }
                }

            }
        }
        LOGGER.info("百欧赔率变化解析完成,新增:"+insert+",跳过:"+jumped+",更新:"+update+",next:"+nexted+"耗时:"+(System.currentTimeMillis()-sat));
    }
}