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
import com.zhcdata.jc.dto.SimplifyOdds;
import com.zhcdata.jc.tools.BeanUtils;
import com.zhcdata.jc.tools.HttpUtils;
import com.zhcdata.jc.xml.rsp.EuropeHundredOddsRsp.EuropeHundredOddsRsp;
import com.zhcdata.jc.xml.rsp.EuropeHundredOddsRsp.H;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

//百家欧赔
//@Configuration
//@EnableScheduling
public class EuropeHundredOddsChangedJob {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Resource
    private EuropeOddsMapper europeOddsMapper;

    @Resource
    private EuropeOddsDetailMapper detailMapper;

    //@Scheduled(cron = "0/30 * * * * ?")//十分钟内赔率变化
    public void execute() throws Exception {
        LOGGER.info("百欧赔率变化解析开始");
        long sat = System.currentTimeMillis();
        int insert = 0;
        int update = 0;
        int jumped = 0;
        int nexted = 0;
        String str = HttpUtils.httpGet("http://interface.win007.com/zq/1x2.aspx?min=10", null);
        EuropeHundredOddsRsp obj = com.alibaba.fastjson.JSONObject.parseObject(XML.toJSONObject(str).toString(), EuropeHundredOddsRsp.class);
        List<H> items = obj.getC().getH();//每场比赛
        for (int i = 0; i < items.size(); i++) {
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
                    }else {
                        if (db.oddsEquals(xml))
                            jumped++;//相同时间 相同赔率 跳过
                        else {
                            //赔率不相同 更新（同样插入一条记录）
                            db.setGuestwin(xml.getGuestwin());
                            db.setStandoff(xml.getStandoff());
                            db.setHomewin(xml.getHomewin());
                            if (detailMapper.insertSelective(db)>0)
                                update++;
                        }
                    }
                }

            }
        }
        LOGGER.info("百欧赔率变化解析完成,新增:"+insert+",跳过:"+jumped+",更新:"+update+",next:"+nexted+"耗时:"+(System.currentTimeMillis()-sat));
    }
}