package com.zhcdata.jc.quartz.job.Match;

import com.zhcdata.db.mapper.*;
import com.zhcdata.db.model.*;
import com.zhcdata.jc.tools.BeanUtils;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.MatchDelOrEditRsp;
import com.zhcdata.jc.xml.rsp.MatchListRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 获取24小时内的赛程删除，比赛时间修改记录
 */
@Configuration
@EnableScheduling
public class MatchListDelOrEditJob {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static List<String> cache = new ArrayList<>();

    private static Map<String, String> editCache = new HashMap<>();


    @Resource
    ScheduleMapper scheduleMapper;


    @Resource
    LetgoalMapper letgoalMapper;

    @Resource
    LetGoalDetailMapper letGoalDetailMapper;

    @Resource
    LetGoalhalfMapper letGoalhalfMapper;

    @Resource
    LetGoalhalfDetailMapper letGoalhalfDetailMapper;

    @Resource
    MultiLetgoalMapper multiLetgoalMapper;

    @Resource
    MultiLetGoalDetailMapper multiLetGoalDetailMapper;

    @Resource
    MultiLetGoalhalfMapper multiLetGoalhalfMapper;

    @Resource
    MultiLetGoalhalfDetailMapper multiLetGoalhalfDetailMapper;

    @Resource
    TotalScoreMapper totalScoreMapper;

    @Resource
    TotalScoreDetailMapper totalScoreDetailMapper;

    @Resource
    TotalScorehalfMapper totalScorehalfMapper;

    @Resource
    TotalScorehalfDetailMapper totalScorehalfDetailMapper;

    @Resource
    MultiTotalScoreMapper multiTotalScoreMapper;

    @Resource
    MultiTotalScoreDetailMapper multiTotalScoreDetailMapper;

    @Resource
    MultiTotalScorehalfMapper multiTotalScorehalfMapper;

    @Resource
    MultiTotalScorehalfDetailMapper multiTotalScorehalfDetailMapper;

    @Resource
    StandardMapper standardMapper;

    @Resource
    StandardDetailMapper standardDetailMapper;

    @Resource
    StandardHalfMapper standardHalfMapper;

    @Resource
    StandardHalfDetailMapper standardHalfDetailMapper;

    @Resource
    EuropeOddsMapper europeOddsMapper;

    @Resource
    EuropeOddsDetailMapper europeOddsDetailMapper;

    @Resource
    EuropeoddstotalMapper europeoddstotalMapper;


    @Scheduled(cron = "1 0/2 * * * ?")
    public void execute() throws Exception {
        LOGGER.info("比赛删除&修改时间记录定时任务启动");
        long s = System.currentTimeMillis();
        int update = 0;
        int delete = 0;
        //int repeat = 0;
        List<MatchDelOrEditRsp> list = new QiuTanXmlComm().handleMothodList("http://interface.win007.com/zq/ModifyRecord.aspx", MatchDelOrEditRsp.class);
        for (int i = 0; i < list.size(); i++) {
            //if (!cache.contains(list.get(i).getID())) {//如果缓存中没有这个id
            if (list.get(i).getType().equals("modify")) {//修改
                if (editCache.get(list.get(i).getID()) != null && editCache.get(list.get(i).getID()).equals(list.get(i).getMatchtime()))
                    continue;
                Schedule schedule = new Schedule();
                schedule.setScheduleid(Integer.parseInt(list.get(i).getID()));
                schedule.setMatchtime(sdf.parse(BeanUtils.parseToFormat(list.get(i).getMatchtime())));
                int i1 = scheduleMapper.updateByPrimaryKeySelective(schedule);
                if (editCache.size() > 500)
                    editCache.clear();
                editCache.put(list.get(i).getID(), list.get(i).getMatchtime());
                if (i1 > 0)
                    update++;

            } else if (list.get(i).getType().equals("delete")) {//删除
                if (scheduleMapper.deleteByPrimaryKey(Integer.parseInt(list.get(i).getID())) > 0) {
                    delete++;

                    //删除赔率表
                    List<Letgoal> letgoal = letgoalMapper.selectByMid(list.get(i).getID());
                    letgoalMapper.deleteByMid(letgoal.get(i).getScheduleid());
                    for (Letgoal value : letgoal)
                        letGoalDetailMapper.deleteByOddsId(value.getOddsid());

                    List<LetGoalhalf> letGoalhalfList = letGoalhalfMapper.selectByMid(list.get(i).getID());
                    letGoalhalfMapper.deleteByMid(list.get(i).getID());
                    for (LetGoalhalf letGoalhalf : letGoalhalfList)
                        letGoalhalfDetailMapper.deleteByOddsId(letGoalhalf.getOddsid());

                    List<MultiLetgoal> multiLetgoals = multiLetgoalMapper.selectByMid(list.get(i).getID());
                    multiLetgoalMapper.deleteByMid(list.get(i).getID());
                    for (MultiLetgoal item : multiLetgoals)
                        multiLetGoalDetailMapper.deleteByOddsId(item.getOddsid());

                    List<MultiLetGoalhalf> multiLetGoalhalves = multiLetGoalhalfMapper.selectByMid(list.get(i).getID());
                    multiLetGoalhalfMapper.deleteByMid(list.get(i).getID());
                    for (MultiLetGoalhalf item : multiLetGoalhalves)
                        multiLetGoalhalfDetailMapper.deleteByOddsId(item.getOddsid());

                    List<TotalScore> totalScores = totalScoreMapper.selectByMid(list.get(i).getID());
                    totalScoreMapper.deleteByMid(list.get(i).getID());
                    for (TotalScore item : totalScores)
                        totalScoreDetailMapper.deleteByOddsId(item.getOddsid());

                    List<TotalScorehalf> totalScorehalves = totalScorehalfMapper.selectByMid(list.get(i).getID());
                    totalScorehalfMapper.deleteByMid(list.get(i).getID());
                    for (TotalScorehalf item : totalScorehalves)
                        totalScorehalfDetailMapper.deleteByOddsId(item.getOddsid());

                    List<MultiTotalScore> multiTotalScores = multiTotalScoreMapper.selectByMid(list.get(i).getID());
                    multiTotalScoreMapper.deleteByMid(list.get(i).getID());
                    for (MultiTotalScore item : multiTotalScores)
                        multiTotalScoreDetailMapper.deleteByOddsId(item.getOddsid());

                    List<MultiTotalScorehalf> multiTotalScorehalves = multiTotalScorehalfMapper.selectByMid(list.get(i).getID());
                    multiTotalScorehalfMapper.deleteByMid(list.get(i).getID());
                    for (MultiTotalScorehalf item : multiTotalScorehalves)
                        multiTotalScorehalfDetailMapper.deleteByOddsId(item.getOddsid());

                    List<Standard> standards = standardMapper.selectByMid(list.get(i).getID());
                    standardMapper.deleteByMid(list.get(i).getID());
                    for (Standard standard : standards)
                        standardDetailMapper.deleteByOddsId(standard.getOddsid());

                    List<StandardHalf> standardHalves = standardHalfMapper.selectByMid(list.get(i).getID());
                    standardHalfMapper.deleteByMid(list.get(i).getID());
                    for (StandardHalf standardHalf : standardHalves)
                        standardHalfDetailMapper.deleteByOddsId(standardHalf.getOddsid());

                    List<EuropeOdds> europeOdds = europeOddsMapper.selectByMid(list.get(i).getID());
                    europeOddsMapper.deleteByMid(list.get(i).getID());
                    for (EuropeOdds item : europeOdds)
                        europeOddsDetailMapper.deleteByOddsId(item.getOddsid());

                    europeoddstotalMapper.deleteByPrimaryKey(Integer.parseInt(list.get(i).getID()));
                }
            }
            LOGGER.info("比赛删除&修改时间记录定时任务结束,共：" + list.size() + ",更新:" + update + "条,删除:" + delete + "耗时：" + (System.currentTimeMillis() - s) + "毫秒");
        }
    }
}