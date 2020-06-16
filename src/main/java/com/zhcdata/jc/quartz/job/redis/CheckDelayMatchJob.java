package com.zhcdata.jc.quartz.job.redis;

import com.zhcdata.db.mapper.JcMatchLotteryMapper;
import com.zhcdata.db.model.JcMatchLottery;
import com.zhcdata.db.model.JcSchedule;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.tools.JcLotteryUtils;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.LotterType.LotteryTypeMatchFristRsp;
import com.zhcdata.jc.xml.rsp.InstantLotteryRsp.LotterType.LotteryTypeMatchRsp;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
@Slf4j
public class CheckDelayMatchJob  implements Job {
    @Resource
    private JcMatchLotteryMapper jcMatchLotteryMapper;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Value("${custom.qiutan.url.lotterTypeMacthUrl}")
    String requestUrl;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //逻辑说明：依次验证未来几天的竞彩赛事，把接口中没有返回的竞彩赛事处理掉。
        //针对延期的竞彩赛事，再开赛，已不属于竞彩的问题。

        try {
            SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");

            SimpleDateFormat df = new SimpleDateFormat("HH");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            String hour = df.format(calendar.getTime());

            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(new Date());

            if (Integer.parseInt(hour) < 11) {
                calendar.add(Calendar.DAY_OF_MONTH, -1);
            } else {
                calendar1.add(Calendar.DAY_OF_MONTH, 1);
            }

            String schedule = "";
            try {
                LotteryTypeMatchFristRsp object = (LotteryTypeMatchFristRsp) new QiuTanXmlComm().handleMothod(requestUrl, LotteryTypeMatchFristRsp.class, LotteryTypeMatchRsp.class);
                List<LotteryTypeMatchRsp> lotteryTypeMatchRspList = object.getList();
                if (lotteryTypeMatchRspList != null && lotteryTypeMatchRspList.size() > 0) {
                    for (int i = 0; i < lotteryTypeMatchRspList.size(); i++) {
                        LotteryTypeMatchRsp lotteryTypeMatchRsp = lotteryTypeMatchRspList.get(i);
                        if (!"0".equals(lotteryTypeMatchRsp.getID_bet007())) {
                            String gameType = JcLotteryUtils.JcLotterZh(lotteryTypeMatchRsp.getLotteryName().trim());
                            if (gameType.equals("JCZQ")) {
                                schedule += lotteryTypeMatchRsp.getID_bet007() + ".";
                            }
                        }
                    }
                } else {
                    log.error("彩票赛程与球探网ID关联表定时任务返回数据为空");
                }
            } catch (Exception e) {
                log.error("彩票赛程与球探网ID关联表定时任务启动异常", e);
                e.printStackTrace();
            }
            LOGGER.info("彩票场次与比赛ID关联表接口 获取竞彩赛事ID" + schedule);

            for (int i = 0; i < 4; i++) {
                String s = df1.format(calendar.getTime()) + " 11:00:00";
                String e = df1.format(calendar1.getTime()) + " 11:00:00";
                List<JcMatchLottery> list = jcMatchLotteryMapper.queryJcMatch(s, e);
                for (int k = 0; k < list.size(); k++) {
                    LOGGER.info(list.get(k).getIdBet007().toString());
                    if (!schedule.contains(list.get(k).getIdBet007().toString())) {
                        //这只负责处理 不应该显示的竞彩比赛
                        LOGGER.info(list.get(k).getIdBet007().toString());
                        jcMatchLotteryMapper.updateByMatchId(list.get(k).getIdBet007(), 0);
                    }
                }

                calendar.add(Calendar.DAY_OF_MONTH, 1);
                calendar1.add(Calendar.DAY_OF_MONTH, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
