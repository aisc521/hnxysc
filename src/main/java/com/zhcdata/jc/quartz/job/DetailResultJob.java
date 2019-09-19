package com.zhcdata.jc.quartz.job;

import com.zhcdata.db.mapper.ScheduleMapper;
import com.zhcdata.db.mapper.TbDetailResultMapper;
import com.zhcdata.db.model.DetailResultInfo;
import com.zhcdata.db.model.Schedule;
import com.zhcdata.jc.tools.HttpUtils;
import com.zhcdata.jc.xml.QiuTanXmlComm;
import com.zhcdata.jc.xml.rsp.DetailResultRsp;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Configuration
@EnableScheduling
public class DetailResultJob {

    @Resource
    ScheduleMapper scheduleMapper;

    @Resource
    TbDetailResultMapper tbDetailResultMapper;

    @Async
    @Scheduled(cron = "41 19 11 ? * *")
    public void work() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String url = "http://interface.win007.com/zq/Event_XML.aspx?type=new&date=" + df.format(new Date());

        try {
            String xml = HttpUtils.getHtmlResult(url);
            //String xml = "<event><item>1656206^1^1^10^H.約納遜 (助攻:D.奥拉夫臣)^113049^H.约纳逊 (助攻:D.奥拉弗森)^6893316^136401</item><item>1656206^0^3^16^奎伊^154217^奎伊^6893323^</item><item>1656206^0^1^57^奧達卡爾臣 (助攻:維克托.安德森)^140193^马格努斯·卡尔森 (助攻:维克托·安德森)^6893531^175398</item><item>1656206^1^3^64^A.祖漢尼臣^41872^A.祖汉尼臣^6893559^</item><item>1656206^1^3^66^A.埃布森^113052^A.埃布森^6893568^</item><item>1656206^1^3^67^D.奥拉夫臣^136401^D.奥拉弗森^6893580^</item><item>1656206^1^1^86^丹尼爾臣^37657^丹尼尔森^6893652^</item><item>1656206^1^1^90^E.阿斯蒙德松^145364^E.阿斯蒙德松^6893653^</item><item>1656208^0^3^21^普里斯特利格里菲斯^163827^普里斯特利格里菲斯^6892732^</item></event>";
            SAXReader saxReader = new SAXReader();
            org.dom4j.Document docDom4j = saxReader.read(new ByteArrayInputStream(xml.getBytes("utf-8")));
            org.dom4j.Element root = docDom4j.getRootElement();
            List<Element> childElements = root.elements();
            for (org.dom4j.Element one : childElements) {
                System.out.println(one.getText());
                String[] values=one.getText().split("\\^");

                //赛程ID^主客队标志^事件类型^时间^球员名字^球名ID^简体球员名^记录ID(不重复）^助攻球员ID
                //     0         1        2    3       4      5         6              7          8
                DetailResultInfo info=new DetailResultInfo();
                info.setId(Integer.valueOf(values[7]));                 //详细结果 ID
                info.setScheduleid(Integer.parseInt(values[0]));        //赛程 ID
                info.setHappentime(Short.parseShort(values[3]));        //发生时间
                Schedule scheduleInfo=scheduleMapper.selectByPrimaryKey(info.getScheduleid());
                if(scheduleInfo!=null) {                                //球队 ID
                    if (values[1].equals("1")) {
                        info.setTeamid(scheduleInfo.getHometeamid());
                    } else {
                        info.setTeamid(scheduleInfo.getGuestteamid());
                    }
                }
                info.setPlayername(values[4]);                          //球员名称
                info.setPlayerid(Integer.parseInt(values[5]));          //球员 ID
                info.setKind(Short.valueOf(values[2]));                 //类型
                info.setModifytime(df.format(new Date()));              //修改时间
                info.setPlayernameJ(values[6]);                         //球员简体名
                info.setPlayeridIn(Integer.valueOf(values[8]));         //球员
                List<DetailResultInfo> queryDetailResult=tbDetailResultMapper.queryDetailResult(info.getScheduleid().toString(),info.getId().toString());
                if(queryDetailResult==null||queryDetailResult.size()==0){
                    if(tbDetailResultMapper.insertSelective(info)>0){
                        log.info("比赛详细事件接口插入成功");
                    }else {
                        log.info("比赛详细事件接口插入失败");
                    }
                }else {
                    if(tbDetailResultMapper.updateByPrimaryKeySelective(info)>0){
                        log.info("比赛详细事件接口修改成功");
                    }else {
                        log.info("比赛详细事件接口修改失败");
                    }
                }
            }
        } catch (Exception ex) {
            log.error("比赛详细事件接口处理异常:" + ex.getMessage());
        }
    }
}
