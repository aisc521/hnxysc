/*create by xuan on 2019/9/11

 */
package com.zhcdata.jc.quartz.job.Odds;

import com.zhcdata.db.mapper.StandardHalfDetailMapper;
import com.zhcdata.db.mapper.StandardHalfMapper;
import com.zhcdata.db.model.StandardHalf;
import com.zhcdata.db.model.StandardHalfDetail;
import com.zhcdata.jc.tools.BeanUtils;
import com.zhcdata.jc.tools.HttpUtils;
import com.zhcdata.jc.xml.rsp.StandardHalfRsps.H;
import com.zhcdata.jc.xml.rsp.StandardHalfRsps.StandardHalfRsp;
import lombok.extern.slf4j.Slf4j;
import org.json.XML;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

//半场欧赔
/*@Configuration
@EnableScheduling*/
@Component
@Slf4j
public class StandardHalfOddsJob implements Job {
    @Resource
    private StandardHalfMapper standardHalfMapper;
    @Resource
    private StandardHalfDetailMapper standardHalfDetailMapper;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   // @Scheduled(cron = "0/10 * * * * ?")
    public void execute() throws Exception {
        log.info("半场欧赔赔率解析开始");
        int insert = 0;
        int update = 0;
        int jump = 0;
        int detailInsert = 0;
        int detailUpdate = 0;
        String xml = HttpUtils.httpGet("http://interface.win007.com/zq/Odds_1x2_half.aspx", null);
        StandardHalfRsp obj = com.alibaba.fastjson.JSONObject.parseObject(XML.toJSONObject(xml).toString(), StandardHalfRsp.class);
        List<H> hItem = obj.getC().getH();
        for (int i = 0; i < hItem.size(); i++) {
            //H item = hItem.get(i);
            List<StandardHalf> mos = BeanUtils.parseStandardHalf(hItem.get(i));
            for (int j = 0; j < mos.size(); j++) {
                StandardHalf db = standardHalfMapper.selectByMidAndCpy(mos.get(j).getScheduleid(), mos.get(j).getCompanyid());
                if (db == null) {
                    //新增
                    int i1 = standardHalfMapper.insertSelective(mos.get(j));
                    if (i1 > 0) {
                        insert++;
                        //新增到变化表
                        StandardHalf ndb = standardHalfMapper.selectByMidAndCpy(mos.get(j).getScheduleid(), mos.get(j).getCompanyid());
                        if (ndb != null) {
                            StandardHalfDetail detail = BeanUtils.parseStandardHalfDetail(mos.get(j));
                            detail.setOddsid(ndb.getOddsid());
                            //HomeWin 主胜水位 单精度型 上盘水位、赔率
                            //Standoff 和局水位 单精度型 盘口
                            //GuestWin 客胜水位 单精度型 下盘水位
                            //ModifyTime 修改时间 日期型 水位/盘口的修改时间
                            if (standardHalfDetailMapper.insertSelective(detail) > 0) detailInsert++;
                        }

                    }
                } else {
                    if (db.oddsEquals(mos.get(j)))
                        jump++;
                    else {
                        //更新
                        db.setHomewin(mos.get(j).getHomewin());//终盘
                        db.setStandoff(mos.get(j).getStandoff());//终盘
                        db.setGuestwin(mos.get(j).getGuestwin());//终盘

                        db.setHomewinR(mos.get(j).getHomewinR());//即时主胜
                        db.setStandoffR(mos.get(j).getStandoffR());//即时和局
                        db.setGuestwinR(mos.get(j).getGuestwinR());//即时主胜
                        if (standardHalfMapper.updateByPrimaryKeySelective(db) > 0) {
                            update++;
                            StandardHalfDetail detail = BeanUtils.parseStandardHalfDetail(db);
                            detail.setOddsid(db.getOddsid());
                            if (standardHalfDetailMapper.insertSelective(detail) > 0) detailUpdate++;

                        }
                    }

                }
            }
        }
        log.info("半场欧赔赔率解析结束,新增:" + insert + ",更新:" + update + ",跳过:" + jump + ",详情新增:" + detailInsert + ",详情更新:" + detailUpdate);
    }
}