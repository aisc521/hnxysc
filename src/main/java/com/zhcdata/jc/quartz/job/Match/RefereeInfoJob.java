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

import com.zhcdata.db.mapper.RefereeInfoMapper;
import com.zhcdata.db.model.RefereeInfo;
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
import java.util.Date;
import java.util.List;

//21.裁判数据
@SuppressWarnings("SpringJavaAutowiringInspection")
@Component
@Slf4j
public class RefereeInfoJob implements Job {

    @Resource
    private RefereeInfoMapper mapper;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //添加随机参数的URL
        String url = "http://interface.win007.com/zq/Referee.aspx?time=" + System.currentTimeMillis();
        log.info("裁判数据请求开始,URL:" + url);
        List<RefereeInfo> list = getListFromXml(url);
        for (RefereeInfo refereeInfo : list) {
            try {
                RefereeInfo model = mapper.selectByMidAndTypeAndPid(refereeInfo.getScheduleId(), refereeInfo.getType(), refereeInfo.getRefereeId());
                if (model == null) {
                    refereeInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    if (mapper.insertSelective(refereeInfo) > 0)
                        log.info("裁判信息新增完成," + refereeInfo.toString());
                } else {
                    if (!model.equals(refereeInfo)) {
                        refereeInfo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                        refereeInfo.setId(model.getId());
                        mapper.updateByPrimaryKeySelective(refereeInfo);
                        log.info("裁判信息更新完成," + refereeInfo.toString());
                    } else log.info("裁判信息跳过," + refereeInfo.toString());

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private List<RefereeInfo> getListFromXml(String url) {
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = null;
        try {
            document = saxBuilder.build(url);
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
            log.error("从url获取xml数据对象异常");
        }

        List<RefereeInfo> result = new ArrayList<>();
        if (document == null) return result;
        Element rootElement = document.getRootElement();
        List<Element> refereeList = rootElement.getChildren();
        for (Element element : refereeList) {
            try {
                RefereeInfo item = new RefereeInfo();
                List<Element> list = element.getChildren();
                for (Element child : list) {
                    switch (child.getName()) {
                        case "scheduleID":
                            item.setScheduleId(Integer.parseInt(child.getValue()));
                            break;
                        case "typeID":
                            item.setType(Integer.parseInt(child.getValue()));
                            break;
                        case "refereeID":
                            item.setRefereeId(Integer.parseInt(child.getValue()));
                            break;
                        case "Name_J":
                            item.setJtName(child.getValue());
                            break;
                        case "name_f":
                            item.setFtName(child.getValue());
                            break;
                        case "Name_E":
                            item.setEnName(child.getValue());
                            break;
                        case "Birthday":
                            item.setBirthday(child.getValue());
                            break;
                        case "Photo":
                            item.setPic(child.getValue());
                            break;
                    }
                }
                item.setCreateTime(new Date());
                result.add(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}