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

import com.zhcdata.db.mapper.*;
import com.zhcdata.db.model.*;
import com.zhcdata.jc.tools.BeanUtils;
import com.zhcdata.jc.tools.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;


//即时多盘
@Configuration
@EnableScheduling
public class ImmediateManyHandicapJob {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final SimpleDateFormat sdfTodayEnd = new SimpleDateFormat("yyyy-MM-dd 23:59:59");

    @Resource
    private LetgoalMapper letgoalMapper;

    @Resource
    private MultiLetgoalMapper multiLetgoalMapper;

    @Resource
    private StandardMapper standardMapper;

    @Resource
    private TotalScoreMapper totalScoreMapper;

    @Resource
    private MultiTotalScoreMapper multiTotalScoreMapper;

    @Resource
    private LetGoalhalfMapper letGoalhalfMapper;

    @Resource
    private MultiLetGoalhalfMapper multiLetGoalhalfMapper;

    @Resource
    private MultiTotalScorehalfMapper multiTotalScorehalfMapper;

    @Resource
    private TotalScorehalfMapper totalScorehalfMapper;

    //@Resource
    //private LetGoalDetailMapper letGoalDetailMapper;

    //@Resource
    //private LetGoalhalfDetailMapper letGoalhalfDetailMapper;

    @Resource
    private ScheduleMapper scheduleMapper;


    //@Scheduled(cron = "1 * * * * ?")
    public void execute() throws Exception {
        try {
            long l = System.currentTimeMillis();
            LOGGER.info("多盘赔率接口解析开始");
            int update = 0;
            int detailUpdate = 0;
            int manyUpdate = 0;
            int insert = 0;
            int manyInsert = 0;
            int jump = 0;

            int europeInsert = 0;
            int europeUpdate = 0;
            int europeJump = 0;

            int bmInsert = 0;
            int bmUpdate = 0;
            int bmJump = 0;

            int mBmInsert = 0;
            int mBmJump = 0;
            int mBmUpdate = 0;

            int halfLetGoalInsert = 0;
            int halfLetGoalUpdate = 0;
            int halfLetGoalDetailUpdate = 0;
            int halfLetGoalJump = 0;

            int mHalfLetGoalInsert = 0;
            int mHalfLetGoalUpdate = 0;
            int mHalfLetGoalJump = 0;

            int totalScoreHalfInsert = 0;
            int totalScoreHalfUpdate = 0;
            int totalScoreHalfJump = 0;

            int mTotalScoreHalfInsert = 0;
            int mTotalScoreHalfUpdate = 0;
            int mTotalScoreHalfJump = 0;

            String str = HttpUtils.httpGet("http://interface.win007.com/zq/Odds_Mult.aspx", null);
            String[] oddsCollection = str.split("\\u0024");

            Date todayEnd = new Date();
            todayEnd = sdf.parse(sdfTodayEnd.format(todayEnd));

            if (oddsCollection.length == 5) {
                //解析 第一部分，亚赔（让球盘）
                String[] LetBall = oddsCollection[0].split(";");
                String[] Europe = oddsCollection[1].split(";");
                String[] BigMin = oddsCollection[2].split(";");

                String[] halfLetBall = oddsCollection[3].split(";");//让球 半场 亚盘
                String[] halfBigMin = oddsCollection[4].split(";");//大小球 半场

                LOGGER.info("多盘赔率接口解析,亚盘条数:" + LetBall.length + ",半场亚盘条数:" + halfLetBall.length + "欧赔条数:" + Europe.length + "大小球条数:" + BigMin.length + ",半场大小球条数:" + halfBigMin.length);


                for (int i = 0; i < Europe.length; i++) {
                    try {
                        Standard xml = BeanUtils.parseStandard(Europe[i]);
                        Standard db = standardMapper.selectMatchIdAndCpy(xml.getScheduleid(), xml.getCompanyid());
                        if (db != null && db.getOddsid() != null) {
                            xml.setOddsid(db.getOddsid());
                            //这是已经有了
                            if (!xml.same(db)) {//判断是不是一样 不一样更新
                                if (standardMapper.updateByPrimaryKeySelective(xml) > 0) europeUpdate++;
                            } else europeJump++;
                        } else if (standardMapper.insertSelective(xml) > 0) europeInsert++;//没有 插入 打日志
                    } catch (Exception e) {
                        LOGGER.error("欧赔数据解析错误,数据体:" + Europe[i] + ",error:" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                for (String s : LetBall)
                    try {
                        if (s.split(",")[10].equals("1")) {
                            //处理单盘口:查有无此比赛,此公司数据
                            Letgoal db = letgoalMapper.selectByMatchIdAndCompany(s.split(",")[0], s.split(",")[1]);
                            Letgoal xml = BeanUtils.parseLetgoal(s);
                            if (db != null && db.getScheduleid() > 0) {
                                //有此条目，看有没有变化，需不需要更新
                                xml.setOddsid(db.getOddsid());
                                if (!xml.nowOddsSame(db)) {
                                    if (letgoalMapper.updateByPrimaryKeySelective(xml) > 0) {
                                        //Schedule match = scheduleMapper.selectByPrimaryKey(db.getScheduleid());
                                        update++;
                                        //LetGoalDetail detail = new LetGoalDetail();
                                        //detail.setOddsid(db.getOddsid());
                                        //detail.setUpodds(xml.getUpodds());
                                        //detail.setGoal(xml.getGoal());
                                        //detail.setDownodds(xml.getDownodds());
                                        //detail.setModifytime(xml.getModifytime());
                                        //detail.setIsearly(match.getMatchtime().getTime()>todayEnd.getTime());//是不是早餐盘
                                        //if (letGoalDetailMapper.insert(detail)>0) {
                                        //    detailUpdate++;
                                        //}
                                    }
                                } else jump++;
                            } else {
                                //数据库没有此条数据，直接插入
                                if (letgoalMapper.insertSelective(xml) > 0) insert++;
                            }
                        } else {
                            //这是多盘口
                            MultiLetgoal db = multiLetgoalMapper.selectByMatchIdAndCompanyAndHandicap(s.split(",")[0], s.split(",")[1], s.split(",")[2]);
                            MultiLetgoal xml = BeanUtils.parseMultiLetgoall(s);
                            if (db != null && db.getScheduleid() > 0) {
                                //有此条目，看有没有变化，需不需要更新
                                xml.setOddsid(db.getOddsid());
                                if (!xml.nowOddsSame(db)) {
                                    if (multiLetgoalMapper.updateByPrimaryKeySelective(xml) > 0) {
                                        manyUpdate++;
                                    }
                                } else jump++;
                            } else {
                                //数据库没有此条数据，直接插入
                                if (multiLetgoalMapper.insertSelective(xml) > 0) manyInsert++;
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.error("亚盘数据解析错误,数据体:" + s + ",error:" + e.getMessage());
                        e.printStackTrace();
                    }


                //大小球 没有 新增 有变化 更新 没变 跳过
                for (int i = 0; i < BigMin.length; i++) {
                    if (BigMin[i].split(",")[8].equals("1")) {
                        TotalScore score = BeanUtils.parseTotalScore(BigMin[i]);
                        //如果是单盘口赔率
                        TotalScore scoreDb = totalScoreMapper.selectTotalScoreByMatchAndCpyAndFristHandicap(score.getScheduleid(), score.getCompanyid(), score.getFirstgoal());
                        if (scoreDb == null || scoreDb.getOddsid() == null || scoreDb.getOddsid() < 0) {
                            //没有此比赛 此公司 此初盘的记录，那就新增
                            if (totalScoreMapper.insertSelective(score) > 0) bmInsert++;
                        } else {
                            //对比即时赔率变化了没有
                            if (scoreDb.oddsEquals(score)) bmJump++;
                            else {
                                //变化了 更新
                                TotalScore updateModel = new TotalScore();
                                updateModel.setOddsid(scoreDb.getOddsid());
                                updateModel.setGoal(scoreDb.getGoal());
                                updateModel.setUpodds(scoreDb.getUpodds());
                                updateModel.setDownodds(scoreDb.getDownodds());
                                if (totalScoreMapper.updateByPrimaryKeySelective(updateModel) > 0) bmUpdate++;
                            }
                        }
                    } else {
                        //这是多盘口赔率
                        MultiTotalScore xml = BeanUtils.parseMultiTotalScore(BigMin[i]);
                        MultiTotalScore db = multiTotalScoreMapper.selectByMatchIdAndCpyAndNum(xml.getScheduleid(), xml.getCompanyid(), xml.getNum());
                        if (db == null || db.getOddsid() == null || db.getOddsid() < 0) {
                            //没有此比赛 此公司 此初盘的记录，那就新增
                            if (multiTotalScoreMapper.insertSelective(xml) > 0) mBmInsert++;
                        } else {
                            //对比即时赔率变化了没有
                            if (db.oddsEquals(xml)) mBmJump++;
                            else {
                                //变化了 更新
                                MultiTotalScore updateModel = new MultiTotalScore();
                                updateModel.setOddsid(db.getOddsid());
                                updateModel.setGoal(db.getGoal());
                                updateModel.setUpodds(db.getUpodds());
                                updateModel.setDownodds(db.getDownodds());
                                if (multiTotalScoreMapper.updateByPrimaryKeySelective(updateModel) > 0) mBmUpdate++;
                            }
                        }
                    }
                }

                //半场亚盘
                for (int i = 0; i < halfLetBall.length; i++) {
                    if (halfLetBall[i].split(",")[9].equals("1")) {//单盘口
                        //比赛ID,公司ID,初盘盘口,主队初盘赔率,客队初盘赔率,即时盘口,主队即时赔率,客队即时赔率,是否走地,盘口序号,变盘时间
                        LetGoalhalf hlb = BeanUtils.parseHalfLetGoal(halfLetBall[i]);
                        LetGoalhalf db = letGoalhalfMapper.selectByMatchIdAndCmpAndFristGoal(hlb.getScheduleid(), hlb.getCompanyid(), hlb.getFirstgoal());
                        if (db != null && db.getOddsid() != null && db.getOddsid() > 0) {
                            //检查是否需要更新
                            if (hlb.oddsEquals(db)) halfLetGoalJump++;
                            else {
                                LetGoalhalf mo = new LetGoalhalf();
                                mo.setOddsid(db.getOddsid());
                                mo.setGoal(hlb.getGoal());
                                mo.setUpodds(hlb.getUpodds());
                                mo.setDownodds(hlb.getDownodds());
                                mo.setModifytime(hlb.getModifytime());
                                if (letGoalhalfMapper.updateByPrimaryKeySelective(mo) > 0) {
                                    halfLetGoalUpdate++;
                                    //LetGoalhalfDetail toDb = new LetGoalhalfDetail();
                                    //toDb.setOddsid(mo.getOddsid());
                                    //toDb.setUpodds(mo.getUpodds());
                                    //toDb.setGoal(mo.getGoal());
                                    //toDb.setDownodds(mo.getDownodds());
                                    //toDb.setModifytime(mo.getModifytime());
                                    //if (letGoalhalfDetailMapper.insertSelective(toDb)>0) {
                                    //    halfLetGoalDetailUpdate++;
                                    //}
                                }
                            }
                        } else {
                            if (letGoalhalfMapper.insertSelective(hlb) > 0) halfLetGoalInsert++;
                        }
                    } else {
                        //多盘
                        MultiLetGoalhalf hlb = BeanUtils.parseMultiLetGoalhalf(halfLetBall[i]);
                        MultiLetGoalhalf db = multiLetGoalhalfMapper.selectByMatchIdAndCmpAndFristGoalAndNum(hlb.getScheduleid(), hlb.getCompanyid(), hlb.getFirstgoal(), hlb.getNum());
                        if (db != null && db.getOddsid() != null && db.getOddsid() > 0) {
                            //检查是否需要更新
                            if (hlb.oddsEquals(db)) mHalfLetGoalJump++;
                            else {
                                MultiLetGoalhalf mo = new MultiLetGoalhalf();
                                mo.setOddsid(db.getOddsid());
                                mo.setGoal(db.getGoal());
                                mo.setUpodds(db.getUpodds());
                                mo.setDownodds(db.getDownodds());
                                if (multiLetGoalhalfMapper.updateByPrimaryKeySelective(mo) > 0) {
                                    mHalfLetGoalUpdate++;
                                }
                            }
                        } else {
                            if (multiLetGoalhalfMapper.insertSelective(hlb) > 0) mHalfLetGoalInsert++;
                        }
                    }
                }

                //半场大小球
                for (int i = 0; i < halfBigMin.length; i++) {

                    //比赛ID,公司ID,初盘盘口,初盘大球赔率,初盘小球赔率,即时盘盘口,即时盘大球赔率,即时盘小球赔率,盘口序号,变盘时间
                    if (halfBigMin[i].split(",")[8].equals("1")) {
                        //单盘
                        TotalScorehalf mo = BeanUtils.parseTotalScorehalf(halfBigMin[i]);

                        TotalScorehalf db = totalScorehalfMapper.selectByMatchIdAndCpyAndFristGoal(mo.getScheduleid(), mo.getCompanyid(), mo.getFirstgoal());
                        if (db != null && db.getOddsid() != null && db.getOddsid() > 0) {
                            //已有数据
                            if (!db.oddsEquals(mo)) {
                                TotalScorehalf toDb = new TotalScorehalf();
                                toDb.setOddsid(db.getOddsid());
                                toDb.setGoal(db.getGoal());
                                toDb.setUpodds(db.getUpodds());
                                toDb.setDownodds(db.getDownodds());
                                if (totalScorehalfMapper.updateByPrimaryKeySelective(toDb) > 0) totalScoreHalfUpdate++;
                            } else totalScoreHalfJump++;
                        } else {
                            if (totalScorehalfMapper.insertSelective(mo) > 0) totalScoreHalfInsert++;
                        }
                    } else {

                        //多盘
                        MultiTotalScorehalf mo = BeanUtils.parseMultiTotalScorehalf(halfBigMin[i]);
                        MultiTotalScorehalf db = multiTotalScorehalfMapper.selectByMatchIdAndCpyAndNum(mo.getScheduleid(), mo.getCompanyid(), mo.getNum());
                        if (db != null && db.getOddsid() != null && db.getOddsid() > 0) {
                            //已有数据
                            if (!db.oddsEquals(mo)) {
                                MultiTotalScorehalf toDb = new MultiTotalScorehalf();
                                toDb.setOddsid(db.getOddsid());
                                toDb.setGoal(db.getGoal());
                                toDb.setUpodds(db.getUpodds());
                                toDb.setDownodds(db.getDownodds());
                                if (multiTotalScorehalfMapper.updateByPrimaryKeySelective(toDb) > 0)
                                    mTotalScoreHalfUpdate++;
                            } else mTotalScoreHalfJump++;
                        } else {
                            if (multiTotalScorehalfMapper.insertSelective(mo) > 0) mTotalScoreHalfInsert++;
                        }
                    }
                }


            } else System.out.println("多盘赔率接口解析错误，collection size：" + oddsCollection.length);
            LOGGER.info("亚盘单盘更新:" + update + ",单盘插入:" + insert + ",多盘更新:" + manyUpdate + ",多盘插入:" + manyInsert + ",详情插入:" + detailUpdate + ",未变化:" + jump);
            LOGGER.info("半场亚盘 单盘更新:" + halfLetGoalUpdate + ",单盘插入:" + halfLetGoalInsert + ",单盘跳过:" + halfLetGoalJump + ",详情更新:" + halfLetGoalDetailUpdate);
            LOGGER.info("半场亚盘 多盘更新:" + mHalfLetGoalUpdate + ",多盘插入:" + mHalfLetGoalInsert + ",多盘跳过:" + mHalfLetGoalJump);
            LOGGER.info("欧赔更新:" + europeUpdate + ",欧赔插入:" + europeInsert + ",跳过(未变化):" + europeJump);
            LOGGER.info("大小球单盘口更新:" + bmUpdate + ",单盘插入:" + bmInsert + ",跳过(未变化):" + bmJump);
            LOGGER.info("大小球多盘口更新:" + mBmUpdate + ",单盘插入:" + mBmInsert + ",跳过(未变化):" + mBmJump);
            LOGGER.info("大小球半场单盘口更新:" + totalScoreHalfUpdate + ",单盘插入:" + totalScoreHalfInsert + ",跳过(未变化):" + totalScoreHalfJump);
            LOGGER.info("大小球半场多盘口更新:" + mTotalScoreHalfUpdate + ",多盘插入:" + mTotalScoreHalfInsert + ",跳过(未变化):" + mTotalScoreHalfJump);
            LOGGER.info("多盘赔率接口解析完成,耗时:" + (System.currentTimeMillis() - l));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}