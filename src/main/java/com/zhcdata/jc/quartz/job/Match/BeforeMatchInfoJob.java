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
 * 　　　　　　　　　┃　　　┃　　　　create by xuan on 2019/11/12
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
package com.zhcdata.jc.quartz.job.Match;

import com.zhcdata.db.mapper.InjuryBriefingMapper;
import com.zhcdata.db.mapper.InjuryPlayerSuspendMapper;
import com.zhcdata.db.mapper.InjuryRecommendMapper;
import com.zhcdata.db.model.InjuryBriefing;
import com.zhcdata.db.model.InjuryPlayerSuspend;
import com.zhcdata.db.model.InjuryRecommend;
import com.zhcdata.jc.dto.InjuryDto;
import com.zhcdata.jc.dto.InjuryPlayerSuspendDto;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

//17.伤停、预测、赛前简报
@SuppressWarnings("SpringJavaAutowiringInspection")
@Component
@Slf4j
public class BeforeMatchInfoJob implements Job {

    @Resource
    private InjuryBriefingMapper injuryBriefingMapper;//简报

    @Resource
    private InjuryRecommendMapper injuryRecommendMapper;//心水推荐

    @Resource
    private InjuryPlayerSuspendMapper injuryPlayerSuspendMapper;//伤停

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long sta = System.currentTimeMillis();
        //添加随机参数的URL
        String url = "http://interface.win007.com/zq/Injury_new.aspx?type=new&time=" + System.currentTimeMillis();
        List<InjuryDto> list = getListFromXml(url);
        log.info("17.伤停/预测/赛前简报开始解析,数据list长度:" + list.size());
        for (InjuryDto injuryDto : list) {
            //简报
            for (String s : injuryDto.getAList()) {
                try {
                    InjuryBriefing dbs = injuryBriefingMapper.selectByMidAndTeam(injuryDto.getMid(), "A", s.substring(1, 3));
                    InjuryBriefing xml = new InjuryBriefing();
                    xml.setScheduleid(injuryDto.getMid());
                    xml.setContent(s);
                    xml.setMark(s.substring(1, 3));
                    xml.setTeamType("A");
                    if (dbs == null) {
                        xml.setCreateTime(new Timestamp(System.currentTimeMillis()));
                        injuryBriefingMapper.insert(xml);
                        log.info("简报信息新增:" + xml.toString());
                    } else {
                        //已有此数据，看是否一致
                        if (xml.getContent().equals(dbs.getContent()))
                            log.info("简报信息跳过:" + dbs.toString());
                        else {
                            xml.setId(dbs.getId());
                            xml.setCreateTime(dbs.getCreateTime());
                            xml.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                            int a = injuryBriefingMapper.updateByPrimaryKeySelective(xml);
                            if (a > 0)
                                log.info("简报信息更新:" + xml.toString());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("简报信息处理异常" + injuryDto.getMid() + "  A  " + s);
                }
            }


            for (String s : injuryDto.getHList()) {
                try {
                    InjuryBriefing dbs = injuryBriefingMapper.selectByMidAndTeam(injuryDto.getMid(), "H", s.substring(1, 3));
                    InjuryBriefing xml = new InjuryBriefing();
                    xml.setScheduleid(injuryDto.getMid());
                    xml.setContent(s);
                    xml.setMark(s.substring(1, 3));
                    xml.setTeamType("H");
                    if (dbs == null) {
                        xml.setCreateTime(new Timestamp(System.currentTimeMillis()));
                        injuryBriefingMapper.insert(xml);
                    } else {
                        //已有此数据，看是否一致
                        if (xml.getContent().equals(dbs.getContent()))
                            log.info("简报信息跳过:" + dbs.toString());
                        else {
                            xml.setId(dbs.getId());
                            xml.setCreateTime(dbs.getCreateTime());
                            xml.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                            if (injuryBriefingMapper.updateByPrimaryKeySelective(xml) > 0)
                                log.info("简报信息已经更新:" + xml.toString());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("简报信息处理异常" + injuryDto.getMid() + "  H  " + s);
                }
            }

            //心水推荐
            try {
                InjuryRecommend selectExp = new InjuryRecommend();
                selectExp.setScheduleid(injuryDto.getMid());
                InjuryRecommend injuryRecommend = injuryRecommendMapper.selectOne(selectExp);
                if (injuryRecommend == null) {
                    injuryRecommend = new InjuryRecommend();
                    injuryRecommend.setScheduleid(injuryDto.getMid());
                    injuryRecommend.setContent(injuryDto.getRecommend());
                    injuryRecommend.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    if (injuryRecommendMapper.insert(injuryRecommend) > 0)
                        log.info("比赛" + injuryRecommend.getScheduleid() + "新增心水推荐" + injuryRecommend.getContent());
                } else {
                    if (injuryRecommend.getContent().equals(injuryDto.getRecommend()))
                        log.info("比赛" + injuryRecommend.getScheduleid() + "已有心水推荐" + injuryRecommend.getContent());
                    else {
                        injuryRecommend.setContent(injuryDto.getRecommend());
                        injuryRecommend.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                        if (injuryRecommendMapper.updateByPrimaryKeySelective(injuryRecommend) > 0)
                            log.info("比赛" + injuryRecommend.getScheduleid() + "更新心水推荐" + injuryRecommend.getContent());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("心水推荐信息处理异常" + injuryDto.getMid() + "   " + injuryDto.getRecommend());
            }


            //伤停
            List<InjuryPlayerSuspendDto> spd = injuryDto.getSpd();
            for (InjuryPlayerSuspendDto injuryPlayerSuspendDto : spd) {
                try {
                    InjuryPlayerSuspend exp = new InjuryPlayerSuspend();
                    exp.setScheduleId(injuryDto.getMid());
                    exp.setTeamid(injuryPlayerSuspendDto.getTeamID());
                    exp.setPlayerid(injuryPlayerSuspendDto.getPlayerID());
                    InjuryPlayerSuspend db = injuryPlayerSuspendMapper.selectByMidTidPid(exp.getScheduleId(), exp.getTeamid(), exp.getPlayerid());
                    exp.setEnName(injuryPlayerSuspendDto.getName());
                    exp.setCnName(injuryPlayerSuspendDto.getName2());
                    exp.setReasontype(injuryPlayerSuspendDto.getReasonType());
                    exp.setReason(injuryPlayerSuspendDto.getReason());
                    exp.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    if (db == null) {
                        if (injuryPlayerSuspendMapper.insertSelective(exp) > 0)
                            log.info("比赛" + exp.getScheduleId() + "新增伤停信息" + exp.toString());
                    } else {
                        if (exp.equals(db))
                            log.info("比赛" + exp.getScheduleId() + "伤停信息存在" + exp.toString());
                        else {
                            db.setScheduleId(injuryDto.getMid());
                            db.setTeamid(injuryPlayerSuspendDto.getTeamID());
                            db.setPlayerid(injuryPlayerSuspendDto.getPlayerID());
                            db.setEnName(injuryPlayerSuspendDto.getName());
                            db.setCnName(injuryPlayerSuspendDto.getName2());
                            db.setReasontype(injuryPlayerSuspendDto.getReasonType());
                            db.setReason(injuryPlayerSuspendDto.getReason());
                            db.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                            if (injuryPlayerSuspendMapper.updateByPrimaryKeySelective(db) > 0)
                                log.info("比赛" + exp.getScheduleId() + "伤停信息更新为" + db.toString());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("比赛" + injuryDto.getMid() + "伤停信息处理异常" + injuryPlayerSuspendDto.toString());
                }
            }
        }
        log.info("17.伤停/预测/赛前简报解析结束,耗时:" + (System.currentTimeMillis() - sta));
    }


    //解析
    private List<InjuryDto> getListFromXml(String url) {
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = null;
        try {
            document = saxBuilder.build(url);
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
            log.error("从url获取xml数据对象异常");
        }

        List<InjuryDto> result = new ArrayList<>();
        if (document == null) return result;

        Element rootElement = document.getRootElement();
        List<Element> refereeList = rootElement.getChildren();
        for (Element element : refereeList) {
            try {
                InjuryDto item = new InjuryDto();
                List<Element> list = element.getChildren();
                for (Element child : list) {
                    switch (child.getName()) {
                        case "ID":
                            item.setMid(Integer.parseInt(child.getValue()));
                            break;
                        case "Briefing":
                            List<Element> children = child.getChildren();
                            for (Element briefing : children) {
                                List<Element> childrenC = briefing.getChildren();
                                List<String> hContent = new ArrayList<>();
                                for (Element cc : childrenC) {
                                    if (cc.getName().equals("i"))
                                        hContent.add(cc.getValue());
                                }
                                if (briefing.getName().equals("Home"))
                                    item.setHList(hContent);
                                else if (briefing.getName().equals("Away"))
                                    item.setAList(hContent);
                            }
                            break;
                        case "Recommend":
                            item.setRecommend(child.getValue());
                            break;
                        case "PlayerSuspend":
                            List<InjuryPlayerSuspendDto> plist = new ArrayList<>();
                            List<Element> cc = child.getChildren();
                            for (Element cci : cc) {
                                if (cci.getName().equals("i")) {
                                    InjuryPlayerSuspendDto itemC = new InjuryPlayerSuspendDto();
                                    for (Element ccci : cci.getChildren()) {
                                        switch (ccci.getName()) {
                                            case "teamID":
                                                itemC.setTeamID(Integer.parseInt(ccci.getValue()));
                                                break;
                                            case "playerID":
                                                itemC.setPlayerID(Integer.parseInt(ccci.getValue()));
                                                break;
                                            case "Name":
                                                itemC.setName(ccci.getValue());
                                                break;
                                            case "Name2":
                                                itemC.setName2(ccci.getValue());
                                                break;
                                            case "ReasonType":
                                                itemC.setReasonType(Integer.parseInt(ccci.getValue()));
                                                break;
                                            case "Reason":
                                                itemC.setReason(ccci.getValue());
                                                break;
                                        }
                                    }
                                    plist.add(itemC);
                                }
                            }
                            item.setSpd(plist);
                            break;
                    }
                }
                result.add(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String msg = "【其他】埃克塞特城在杯赛中的前景也是一片光明，前2场小组赛取得连胜，基本锁定出线资格。主场是他们的抢分重地，最近2轮主场均零封对手而且打进5球，攻防都很给力。";
        System.out.println(msg.substring(1, 3));
        String text = "<![CDATA[\n" +
                "<TABLE cellSpacing='0' cellPadding='4' width='99%' border='0'> <TR bgColor='#f2f2fe'><TD class='aleft' style='BORDER-RIGHT: #cdcdcd 1px groove; BORDER-BOTTOM: #cdcdcd 1px groove' width='33%'>&nbsp;英格兰</TD><TD class='aleft' style='BORDER-RIGHT: #cdcdcd 1px groove; BORDER-BOTTOM: #cdcdcd 1px groove' width='33%'>&nbsp;近况走势 - <FONT color=#006600>D</FONT><FONT color=#006600>D</FONT><FONT color=#ff0000>W</FONT><FONT color=#ff0000>W</FONT><FONT color=#0000ff>L</FONT><FONT color=#ff0000>W</FONT></TD> <TD class='aleft' style='BORDER-BOTTOM: #cdcdcd 1px groove'>&nbsp;盘路赢输 - <FONT color=#006600>D</FONT><FONT color=#0000ff>L</FONT><FONT color=#ff0000>W</FONT><FONT color=#006600>D</FONT><FONT color=#0000ff>L</FONT><FONT color=#ff0000>W</FONT></TD> </TR> <TR bgColor='#f2f2fe'><TD class='aleft' style='BORDER-RIGHT: #cdcdcd 1px groove; BORDER-BOTTOM: #cdcdcd 1px groove' width='33%'>&nbsp;黑山</TD><TD class='aleft' style='BORDER-RIGHT: #cdcdcd 1px groove; BORDER-BOTTOM: #cdcdcd 1px groove' width='33%'>&nbsp;近况走势 - <FONT color=#006600>D</FONT><FONT color=#0000ff>L</FONT><FONT color=#ff0000>W</FONT><FONT color=#0000ff>L</FONT><FONT color=#006600>D</FONT><FONT color=#0000ff>L</FONT></TD> <TD class='aleft' style='BORDER-BOTTOM: #cdcdcd 1px groove'>&nbsp;盘路赢输 - <FONT color=#006600>D</FONT><FONT color=#0000ff>L</FONT><FONT color=#ff0000>W</FONT><FONT color=#0000ff>L</FONT><FONT color=#0000ff>L</FONT><FONT color=#0000ff>L</FONT></TD> </TR> <TR bgColor='#f2f2fe'><TD class='aleft' style='BORDER-BOTTOM: #cdcdcd 1px groove; TEXT-ALIGN: center' colSpan='3'>信心指数 - <FONT color=#6666ff>英格兰</FONT> ★★★★&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;对赛成绩 - 英格兰 <font color=FF0000 >2胜 3和 0负 </font></TD> </TR> <TR><TD class='aleft' style='BORDER-BOTTOM: #cdcdcd 1px groove' bgColor='#ffffff' colSpan='3'>黑山分组赛开打至今仍未开斋，攻不锐防不稳的表现实在不堪入目，更何况球队实力还明显不及英格兰，黑山今仗凶多吉少。</TD></TR></TABLE>\n" +
                "]]>";
        System.out.println(text.length());
    }
}