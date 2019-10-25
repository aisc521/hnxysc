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
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

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
            int p=0;
            for (org.dom4j.Element one : childElements) {
                p++;
                System.out.println(p);
                TeamInfo info = new TeamInfo();
                info.setKind(Short.valueOf("1"));
                List<Element> twos = one.elements();
                for (org.dom4j.Element two : twos) {
                    if (two.getName().equals("id")) {
                        info.setTeamid(Integer.valueOf(two.getText()));             //球队 ID
                    } else if (two.getName().equals("lsID")) {
                        info.setSclassid(Integer.valueOf(two.getText()));           //所属联赛ID
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
                        if(two.getText().length()>0) {
                            info.setCapacity(Integer.valueOf(two.getText()));       //球场容量
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
                    //此接口不做数据是否改变的判断,直接修改(该接口一天一次，没关系)
                    if(!info.equals(list.get(0))) {
                        if (tbTeamMapper.updateByPrimaryKeySelective(info) > 0) {
                            log.info("[球队资料]修改成功" + info.getTeamid() + df.format(new Date()));
                        } else {
                            log.info("[球队资料]修改失败" + info.getTeamid() + df.format(new Date()));
                        }
                    }
                } else {
                    if (tbTeamMapper.insertSelective(info) > 0) {
                        log.info("[球队资料]添加成功" + info.getTeamid() + df.format(new Date()));
                    } else {
                        log.info("[球队资料]添加失败" + info.getTeamid() + df.format(new Date()));
                    }
                }
            }
        } catch (Exception ex) {
            log.error("[球队资料]处理异常" + ex.toString());
            ex.printStackTrace();
        }
        log.error("Instance detail: " + key + " trigger:" + jobKey + "执行完成");
        long end = System.currentTimeMillis();
        log.error("Instance detail: " + key + " trigger:" + jobKey + "执行耗时" + ((double) (end - start) / 1000) + "(s)");
    }
}
