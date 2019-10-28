/*create by xuan on 2019/9/11

 */
package com.zhcdata.jc.quartz.job.Odds;

import com.zhcdata.db.mapper.EuropeoddstotalMapper;
import com.zhcdata.db.model.EuropeOdds;
import com.zhcdata.db.model.Europeoddstotal;
import com.zhcdata.jc.tools.BeanUtils;
import com.zhcdata.jc.tools.HttpUtils;
import com.zhcdata.jc.xml.rsp.EuropeHundredOddsRsp.EuropeHundredOddsRsp;
import com.zhcdata.jc.xml.rsp.EuropeHundredOddsRsp.H;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

//百家欧赔
@SuppressWarnings("SpringJavaAutowiringInspection")
@Component
@Slf4j
public class EuropeHundredOddsTotal implements Job {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Resource
    private EuropeoddstotalMapper europeoddstotalMapper;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("百欧赔率total表解析开始");
        long sat = System.currentTimeMillis();
        int odds = 0;
        String str = null;
        try {
            str = HttpUtils.httpGet("http://interface.win007.com/zq/1x2.aspx?day=3", null);
            if (str.contains("访问频率超出限制")){
                log.error("百欧赔率total表接口访问超出限制");
                return;
            }
        } catch (Exception e) {
            log.error("百欧赔率表接口获取失败" , e);
        }
        JSONObject jsonObject = XML.toJSONObject(str);
        String s = jsonObject.toString();
        EuropeHundredOddsRsp obj = com.alibaba.fastjson.JSONObject.parseObject(s, EuropeHundredOddsRsp.class);

        if (obj==null||obj.getC()==null||obj.getC().getH()==null||obj.getC().getH().size()==0)
            return;
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
        LOGGER.info("百欧赔率total表,总:" + odds + ",新增:" + insert + ",更新:" + update + ",耗时:" + (System.currentTimeMillis() - sat));
    }
}