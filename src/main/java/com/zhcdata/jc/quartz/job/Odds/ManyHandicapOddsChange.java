package com.zhcdata.jc.quartz.job.Odds;

import com.zhcdata.db.mapper.*;
import com.zhcdata.db.model.*;
import com.zhcdata.jc.tools.BeanUtils;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.MoreHandicapOddsLisAlltRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 21.多盘口赔率：30秒内变化赔率接口
 */
//@Configuration
//@EnableScheduling
@Component
public class ManyHandicapOddsChange {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Resource
    private LetGoalDetailMapper letGoalDetailMapper;//让球盘赔率变化

    @Resource
    private MultiLetGoalDetailMapper multiLetGoalDetailMapper;//让球多盘赔率变化

    @Resource
    private LetgoalMapper letgoalMapper;//让球赔率表

    @Resource
    private StandardDetailMapper standardDetailMapper;//标准胜平负变化表

    @Resource
    private StandardMapper standardMapper;//胜平负表

    @Resource
    private TotalScoreDetailMapper totalScoreDetailMapper;//大小球详情表

    @Resource
    private TotalScoreMapper totalScoreMapper;

    @Resource
    MultiTotalScoreMapper multiTotalScoreMapper;

    @Resource
    private MultiTotalScoreDetailMapper multiTotalScoreDetailMapper;

    @Resource
    private LetGoalhalfDetailMapper letGoalhalfDetailMapper;

    @Resource
    private LetGoalhalfMapper letGoalhalfMapper;

    @Resource
    private MultiLetGoalhalfDetailMapper multiLetGoalhalfDetailMapper;

    @Resource
    private MultiLetGoalhalfMapper multiLetGoalhalfMapper;

    @Resource
    private TotalScorehalfDetailMapper totalScorehalfDetailMapper;

    @Resource
    private TotalScorehalfMapper totalScorehalfMapper;

    @Resource
    private MultiTotalScorehalfDetailMapper multiTotalScorehalfDetailMapper;

    @Resource
    private MultiTotalScorehalfMapper multiTotalScorehalfMapper;

    //@Scheduled(cron = "0/30 * * * * ?")
    public void execute(){
        LOGGER.error("多盘赔率详情任务开始");
        long now = System.currentTimeMillis();
        int oddsDetailInsert = 0;
        int oddsDetailUpdate = 0;
        int oddsDetailJump = 0;
        int oddsDetailNext = 0;

        int mOddsDetailInsert = 0;
        int mOddsDetailUpdate = 0;
        int mOddsDetailJump = 0;
        int mOddsDetailNext = 0;

        int europeInsert = 0;
        int europeUpdate = 0;
        int europeJump = 0;
        int europeNext = 0;

        int bsInsert = 0;
        int bsUpdate = 0;
        int bsJump = 0;
        int bsNext = 0;

        int letGoalhalfDetailInsert = 0;
        int letGoalhalfDetailUpdate = 0;
        int letGoalhalfDetailJump = 0;
        int letGoalhalfDetailNext = 0;


        int mLetGoalhalfDetailInsert = 0;
        int mLetGoalhalfDetailUpdate = 0;
        int mLetGoalhalfDetailJump = 0;
        int mLetGoalhalfDetailNext = 0;

        int obshInsert = 0;//one big small half
        int obshUpdate = 0;//one big small half
        int obshjump = 0;//one big small half
        int obshNext = 0;//one big small half

        int mbshInsert = 0;//more big small half
        int mbshUpdate = 0;//more big small half
        int mbshjump = 0;//more big small half
        int mbshNext = 0;//more big small half

        String url = "http://interface.win007.com/zq/ch_odds_m.xml";
        QiuTanXmlComm parseXml = new QiuTanXmlComm();
        MoreHandicapOddsLisAlltRsp rsp = (MoreHandicapOddsLisAlltRsp) parseXml.handleMothodHttpGet(url, MoreHandicapOddsLisAlltRsp.class);

        //大小球 多盘 半场 赔率
        //比赛ID,公司ID,时盘盘口,即时盘大球赔率,即时盘小球赔率,盘口序号,变盘时间
        LOGGER.info("大小球半场多盘更新任务开始");
        if(rsp.getDh()!=null){
            for (int i = 0; i < rsp.getDh().getH().size(); i++) {
                String[] info = rsp.getDh().getH().get(i).split(",");
                if (info[5].equals("1")){//单盘
                    TotalScorehalfDetail xml = BeanUtils.parseTotalScorehalfDetail(info);
                    //比赛id，公司ID获取数据库最新一条
                    TotalScorehalfDetail db = totalScorehalfDetailMapper.selectByMidAndCpy(info[0],info[1]);
                    if (db==null){
                        //是否有此盘口
                        TotalScorehalf pdb = totalScorehalfMapper.selectByMidAndCpy(info[0],info[1]);
                        if (pdb==null)obshNext++;
                        else {
                            xml.setOddsid(pdb.getOddsid());
                            if (totalScorehalfDetailMapper.insertSelective(xml)>0)obshInsert++;
                        }
                    }else {
                        if (db.oddsEquals(xml))obshjump++;else {
                            xml.setOddsid(db.getOddsid());
                            if (totalScorehalfDetailMapper.insertSelective(xml)>0)
                                obshUpdate++;
                        }
                    }
                }else {
                    MultiTotalScorehalfDetail xml = BeanUtils.parseMultiTotalScorehalfDetail(info);
                    MultiTotalScorehalfDetail db = multiTotalScorehalfDetailMapper.selectByMidAndCpyAndNum(info[0],info[1],info[5]);
                if (db==null){
                    //要不要插入
                    MultiTotalScorehalf pdb =  multiTotalScorehalfMapper.selectByMidAndCpyAndNum(info[0],info[1],info[5]);
                    if (pdb==null)mbshNext++;
                    else {
                        xml.setOddsid(pdb.getOddsid());
                        if (multiTotalScorehalfDetailMapper.insertSelective(xml)>0)mbshInsert++;
                    }
                }else {
                    //更新
                    if (xml.oddsEquals(db))mbshjump++;else {
                        xml.setOddsid(db.getOddsid());
                        if (multiTotalScorehalfDetailMapper.insertSelective(xml)>0) {
                            mbshUpdate++;
                        }
                    }
                }
                }
            }
            LOGGER.info("大小球(单盘):总条数" + rsp.getDh().getH().size() +",插入"+obshInsert+",更新"+obshUpdate+"，跳过"+obshjump+",next"+obshNext);
            LOGGER.info("大小球(多盘):插入"+mbshInsert+",更新"+mbshUpdate+"，跳过"+mbshjump+",next"+mbshNext);
        }else LOGGER.info("大小球半场多盘更新任务结束");

        
        

        //半场亚赔（让球盘）变化数据:cahh
        //比赛ID,公司ID,即时盘口,主队即时赔率,客队即时赔率,是否走地,盘口序号,变盘时间
        LOGGER.info("半场亚盘更新任务开始");
        if (rsp.getAh()!=null) {
            for (int i = 0; i < rsp.getAh().getH().size(); i++) {
                String[] info = rsp.getAh().getH().get(i).split(",");
                if (info[6].equals("1")) {//存单盘
                    //数组转对象
                    LetGoalhalfDetail xml = BeanUtils.parseLetGoalhalfDetail(info);
                    //查询最新一条数据
                    LetGoalhalfDetail db = letGoalhalfDetailMapper.selectByMidAndCpy(info[0], info[1]);
                    if (db == null) {
                        LetGoalhalf fdb = letGoalhalfMapper.selectByMatchIdAndCmp(Integer.parseInt(info[0]), Integer.parseInt(info[1]));
                        if (fdb == null) letGoalhalfDetailNext++;//没有数据数据盘口 跳过
                        else {
                            //这可能是初盘初盘 新增
                            xml.setOddsid(fdb.getOddsid());
                            if (letGoalhalfDetailMapper.insertSelective(xml) > 0) letGoalhalfDetailInsert++;
                        }
                    } else {
                        //以查到最新的赔率，判断要不要插入形式de更新
                        if (db.oddsEquals(xml)) letGoalhalfDetailJump++;
                        else {
                            xml.setOddsid(db.getOddsid());
                            if (letGoalhalfDetailMapper.insertSelective(xml) > 0) letGoalhalfDetailUpdate++;
                        }
                    }
                } else {
                    //存多盘
                    MultiLetGoalhalfDetail xml = BeanUtils.parseMultiLetGoalhalfDetail(info);
                    MultiLetGoalhalfDetail db = multiLetGoalhalfDetailMapper.selectByMidAndCpyAndNum(info[0], info[1], info[6]);
                    if (db == null) {
                        //看是没有盘口还是没有此盘口de数据
                        MultiLetGoalhalf pdb = multiLetGoalhalfMapper.selectByMatchIdAndCmpAndNum(info[0], info[1], info[6]);
                        if (pdb == null) mLetGoalhalfDetailNext++;
                        else {
                            xml.setOddsid(pdb.getOddsid());
                            if (multiLetGoalhalfDetailMapper.insertSelective(xml) > 0) mLetGoalhalfDetailInsert++;
                        }
                    } else {
                        //检查是不是要更新
                        if (xml.oddsEquals(db)) mLetGoalhalfDetailJump++;
                        else {
                            db.setId(null);
                            db.setUpodds(xml.getUpodds());
                            db.setGoal(xml.getGoal());
                            db.setDownodds(xml.getDownodds());
                            db.setAddtime(xml.getAddtime());
                            if (multiLetGoalhalfDetailMapper.insertSelective(db) > 0) mLetGoalhalfDetailUpdate++;
                        }
                    }
                }
            }
            LOGGER.info("半场亚盘(单盘):总条数" + rsp.getAh().getH().size() +",插入"+letGoalhalfDetailInsert+",更新"+letGoalhalfDetailUpdate+"，跳过"+letGoalhalfDetailJump+",next"+letGoalhalfDetailNext);
            LOGGER.info("半场亚盘(多盘):插入"+mLetGoalhalfDetailInsert+",更新"+mLetGoalhalfDetailUpdate+"，跳过"+mLetGoalhalfDetailJump+",next"+mLetGoalhalfDetailNext);
        }else LOGGER.info("半场亚盘暂无数据");
        /*大小球变化数据 比赛ID,公司ID,时盘盘口,即时盘大球赔率,即时盘小球赔率,盘口序号,变盘时间,是否封盘2*/
        List<String> bs = rsp.getD().getH();
        for (int i = 0; i < bs.size(); i++) {
            if (bs.get(i).split(",")[5].equals("1")){//单盘
                TotalScoreDetail xml = BeanUtils.parseTotalScoreDetail(bs.get(i).split(","));
                TotalScoreDetail db = totalScoreDetailMapper.selectByMidAndCpy(bs.get(i).split(",")[0],bs.get(i).split(",")[1]);
                if (db==null){
                    TotalScore dbP = totalScoreMapper.selectTotalScoreByMatchAndCpy(Integer.parseInt(bs.get(i).split(",")[0]), Integer.parseInt(bs.get(i).split(",")[1]));
                    if (dbP==null)bsNext++;
                    else {
                        db = new TotalScoreDetail();
                        db.setOddsid(dbP.getOddsid());
                        db.setGoal(xml.getGoal());
                        db.setUpodds(xml.getUpodds());
                        db.setDownodds(xml.getDownodds());
                        db.setModifytime(xml.getModifytime());
                        if (totalScoreDetailMapper.insert(db)>0) bsInsert++;
                    }
                }else {
                    if (db.oddsEquals(xml))bsJump++;
                    else {
                        db.setId(null);
                        db.setGoal(xml.getGoal());
                        db.setUpodds(xml.getUpodds());
                        db.setDownodds(xml.getDownodds());
                        db.setModifytime(xml.getModifytime());
                        if (totalScoreDetailMapper.insert(db)>0) bsUpdate++;
                    }
                }
            }else {//多盘
                MultiTotalScoreDetail xml = BeanUtils.parseparseMultiTotalScoreDetail(bs.get(i).split(","));
                MultiTotalScoreDetail db = multiTotalScoreDetailMapper.selectByMidAndCpyAndNum(bs.get(i).split(",")[0],bs.get(i).split(",")[1],bs.get(i).split(",")[5]);
                if (db==null){
                    MultiTotalScore dbP = multiTotalScoreMapper.selectTotalScoreByMatchAndCpyAndNum(Integer.parseInt(bs.get(i).split(",")[0]), Integer.parseInt(bs.get(i).split(",")[1]), Integer.parseInt(bs.get(i).split(",")[5]));
                    if (dbP==null)bsNext++;
                    else {
                        db = new MultiTotalScoreDetail();
                        db.setOddsid(dbP.getOddsid());
                        db.setGoal(xml.getGoal());
                        db.setUpodds(xml.getUpodds());
                        db.setDownodds(xml.getDownodds());
                        db.setAddtime(xml.getAddtime());
                        if (multiTotalScoreDetailMapper.insert(db)>0) bsInsert++;
                    }
                }else {
                    if (db.oddsEquals(xml))bsJump++;
                    else {
                        db.setId(null);
                        db.setGoal(xml.getGoal());
                        db.setUpodds(xml.getUpodds());
                        db.setDownodds(xml.getDownodds());
                        db.setAddtime(xml.getAddtime());
                        if (multiTotalScoreDetailMapper.insert(db)>0) bsUpdate++;
                    }
                }
            }
        }

        LOGGER.info("大小球  插入:"+bsInsert+",更新:"+bsUpdate+",跳过:"+bsJump+",next:"+bsNext);


        /*欧赔-胜平负*/
        List<String> coh = rsp.getO().getH();
        if (coh!=null && coh.size()>0){

            LOGGER.error("欧赔(胜平负)详情,变化数据总条数:"+coh.size());
            for (String s : coh) {
                //比赛ID,公司ID,即时盘主胜赔率,即时盘平局赔率,即时盘客胜赔率,盘口序号,变盘时间,是否封盘2
                String[] info = s.split(",");
                //查询最新的是不是这个赔率，如果没有或不一样 插入
                StandardDetail db = standardDetailMapper.selectByMidAndCpy(info[0], info[1]);
                StandardDetail xml = BeanUtils.parseStandardDetail(info);
                if (db == null) {
                    Standard mo = standardMapper.selectByMidAndCpy(info[0], info[1]);
                    if (mo == null) europeNext++;
                    else
                        xml.setOddsid(mo.getOddsid());
                    if (standardDetailMapper.insert(xml) > 0) europeInsert++;
                } else {
                    if (xml.oddsEquals(db)) europeJump++;
                    else {
                        db.setId(null);
                        db.setHomewin(xml.getHomewin());
                        db.setStandoff(xml.getStandoff());
                        db.setGuestwin(xml.getGuestwin());
                        if (standardDetailMapper.insertSelective(db) > 0) europeUpdate++;
                    }
                }
            }
            LOGGER.info("欧赔详情  插入:"+europeInsert+",更新:"+europeUpdate+",跳过:"+europeJump+",next:"+europeNext);
        }else LOGGER.info("欧赔详情  暂无数据");


        List<String> cah = rsp.getA().getH();
        //亚赔（让球盘）变化数据:比赛ID,公司ID,即时盘口,主队即时赔率,客队即时赔率,是否封盘1,是否走地,盘口序号,变盘时间,是否封盘2
        LOGGER.error("多盘赔率详情,亚赔（让球盘）变化数据总条数:"+cah.size());
        for (int i = 0; i < cah.size(); i++) {
            String[] item = cah.get(i).split(",");
            if (item[7].equals("1")) {
                //存到单盘口
                LetGoalDetail xml = BeanUtils.parseLetGoalDetail(item);
                //查询此比赛最新的一条赔率
                LetGoalDetail db  = letGoalDetailMapper.selectByMatchAndCpyOrderByTimeDescLimit1(Integer.parseInt(item[0]),Integer.parseInt(item[1]));
                if (db==null){
                    //Letgoal letgoal = letgoalMapper.selectByMatchIdAndCompany(item[0], item[1]);
                    Map<String,Object> info = letgoalMapper.selectOddsIdAndStartTimeByByMatchIdAndCompany(item[0], item[1]);
                    if (info!=null && info.get("oid") != null && info.get("time")!=null){
                        xml.setOddsid(Integer.parseInt(info.get("oid").toString()));
                        Date time = (Timestamp) info.get("time");
                        if (time.getTime()>getEndOfDay(null).getTime())
                            xml.setIsearly(true);else xml.setIsearly(false);
                        if (letGoalDetailMapper.insertSelective(xml)>0) {
                            oddsDetailInsert++;
                        }
                    }else oddsDetailNext++;
                }else{
                    if (xml.oddsEquals(db))oddsDetailJump++;
                    else {
                        Map<String,Object> info = letgoalMapper.selectOddsIdAndStartTimeByByMatchIdAndCompany(item[0], item[1]);
                        if (info.get("oid")!=null&&info.get("time")!=null){
                            Date time = (Timestamp) info.get("time");
                            if (time.getTime()>getEndOfDay(null).getTime())
                                xml.setIsearly(true);else xml.setIsearly(false);
                        }else oddsDetailNext++;
                        xml.setOddsid(db.getOddsid());
                        if (letGoalDetailMapper.updateByPrimaryKeySelective(xml)>0)
                            oddsDetailUpdate++;
                    }
                }
            }else{
                //存到多盘口
                MultiLetGoalDetail xml = BeanUtils.parseMultiLetGoalDetail(item);
                Map<String,Object> info = letgoalMapper.selectOddsIdAndStartTimeByByMatchIdAndCompany(item[0], item[1]);
                if (info!=null && info.get("oid")!=null&&info.get("time")!=null){
                    //MultiLetgoal mdb = multiLetgoalMapper.selectByMatchIdAndCompanyAndHandicapNum(item[0], item[1], Short.parseShort(item[7]));
                    MultiLetGoalDetail mdb = multiLetGoalDetailMapper.selectByMidAndCpyAndNum(item[0], item[1],item[7]);
                    if (mdb!=null&&mdb.getOddsid()!=null&&mdb.getOddsid()>0){
                        //对比要不要更新
                        if (mdb.oddsEquals(xml))mOddsDetailJump++;else {
                            //更新 插入
                            xml.setOddsid(Integer.parseInt(info.get("oid").toString()));
                            Date time = (Timestamp) info.get("time");
                            if (time.getTime()>getEndOfDay(null).getTime()){
                                xml.setType(new Short("1"));
                            }else xml.setType(new Short("2"));
                            if (multiLetGoalDetailMapper.insertSelective(xml)>0)mOddsDetailUpdate++;
                        }
                    }else {
                        //没有此记录 插入
                        xml.setOddsid(Integer.parseInt(info.get("oid").toString()));
                        Date time = (Timestamp) info.get("time");
                        if (time.getTime()>getEndOfDay(null).getTime())
                            xml.setType(new Short("1"));else xml.setType(new Short("2"));
                        if (multiLetGoalDetailMapper.insertSelective(xml)>0)mOddsDetailInsert++;
                    }

                }else mOddsDetailNext++;
            }
        }

        LOGGER.info("单盘赔率详情  插入:"+oddsDetailInsert+",更新:"+oddsDetailUpdate+",跳过:"+oddsDetailJump+",next:"+oddsDetailNext);
        LOGGER.info("多盘赔率详情  插入:"+mOddsDetailInsert+",更新:"+mOddsDetailUpdate+",跳过:"+mOddsDetailJump+",next:"+mOddsDetailNext);
        LOGGER.info("多盘赔率详情任务结束，耗时"+(System.currentTimeMillis()-now)+"毫秒");
    }

    private Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(year, month, day, 23, 59, 59);
        return calendar.getTime();
    }
}