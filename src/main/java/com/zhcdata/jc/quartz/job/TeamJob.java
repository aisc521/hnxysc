package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.TbTeamMapper;
import com.zhcdata.db.model.TeamInfo;
import com.zhcdata.jc.tools.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 球队资料
 */
@Slf4j
@Configuration
@EnableScheduling
public class TeamJob implements Job {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    TbTeamMapper tbTeamMapper;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey key = context.getJobDetail().getKey();
        JobKey jobKey = context.getTrigger().getJobKey();
        long start = System.currentTimeMillis();
        log.error("Instance detail: " + key + " trigger:" + jobKey + "开始执行");
        String url = "http://interface.win007.com/zq/Team_XML.aspx";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            String xml = HttpUtils.getHtmlResult(url);
            SAXReader saxReader = new SAXReader();
            org.dom4j.Document docDom4j = saxReader.read(new ByteArrayInputStream(xml.getBytes("utf-8")));
            org.dom4j.Element root = docDom4j.getRootElement();
            List<Element> childElements = root.elements();
            for (org.dom4j.Element one : childElements) {

                TeamInfo info = new TeamInfo();
                info.setKind(Short.valueOf("1"));
                List<Element> twos = one.elements();
                for (org.dom4j.Element two : twos) {
                    if (two.getName().equals("id")) {
                        info.setTeamid(Integer.valueOf(two.getText()));         //球队 ID
                    } else if (two.getName().equals("lsID")) {
                        info.setSclassid(Integer.valueOf(two.getText()));       //所属联赛ID
                    } else if (two.getName().equals("g")) {
                        info.setNameJ(two.getText());                           //简体名
                    } else if (two.getName().equals("b")) {
                        info.setNameF(two.getText());                           //繁体名
                    } else if (two.getName().equals("e")) {
                        info.setNameE(two.getText());                           //英文名
                    } else if (two.getName().equals("Found")) {
                        info.setFoundDate(two.getText());                       //球队成立日期
                    } else if (two.getName().equals("Area")) {
                        info.setArea(two.getText());                            //所在地
                    } else if (two.getName().equals("gym")) {
                        info.setGymnasium(two.getText());                       //球场
                    } else if (two.getName().equals("Capacity")) {
                        if(!two.getText().equals("")) {
                            info.setCapacity(Integer.valueOf(two.getText()));   //球场容量
                        }
                    } else if (two.getName().equals("Flag")) {
                        info.setFlag(two.getText());                            //队标
                    } else if (two.getName().equals("addr")) {
                        info.setAddress(two.getText());                         //地址
                    } else if (two.getName().equals("URL")) {
                        info.setUrl(two.getText());                             //球队网址
                    } else if (two.getName().equals("master")) {
                        info.setDrillmaster(two.getText());                     //主教练
                    }
                }
                info.setModifytime(df.format(new Date()));

                List<TeamInfo> list = tbTeamMapper.queryTeam(info.getTeamid().toString());
                if (list != null && list.size() > 0) {
                    if (!info.equals(list.get(0))) {
                        log.info("球队信息已经存在" + info.getTeamid());
                        if (tbTeamMapper.updateByPrimaryKeySelective(info) > 0) {
                            log.info("球队信息修改成功" + info.getTeamid());
                        } else {
                            log.info("球队信息修改失败" + info.getTeamid());
                        }
                    }
                } else {
                    log.info("球队信息已经存在" + info.getTeamid());
                    if (tbTeamMapper.insertSelective(info) > 0) {
                        log.info("球队信息添加成功" + info.getTeamid());
                    } else {
                        log.info("球队信息添加失败" + info.getTeamid());
                    }
                }
            }
        } catch (Exception ex) {
            log.error("球队信息处理异常" + ex.toString());
        }
        log.error("Instance detail: " + key + " trigger:" + jobKey + "执行完成");
        long end = System.currentTimeMillis();
        log.error("Instance detail: " + key + " trigger:" + jobKey + "执行耗时" + ((double) (end - start) / 1000) + "(s)");
    }
}
