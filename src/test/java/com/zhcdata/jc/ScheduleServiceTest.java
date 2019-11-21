package com.zhcdata.jc;

import com.zhcdata.jc.service.ScheduleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springside.modules.utils.mapper.JsonMapper;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleServiceTest {

    @Resource
    ScheduleService scheduleService;

    @Test
    public void queryLineupDataByMatch() {
//        Gson gson = new Gson();
//        Map<String, Object> map = scheduleService.queryLineupDataByMatch(1721166L);
//        System.out.println(gson.toJson(map));
    }
    @Test
    public void queryMatchAnalysis() throws ParseException {
//        scheduleService.updateMatchAnalysis(1,1);
//        Map<String, Object> stringObjectMap = scheduleService.matchAnalysisByType(1815086, "0", "0");
//        System.out.println(JsonMapper.defaultMapper().toJson(stringObjectMap));

//        Calendar instance = Calendar.getInstance();
//        instance.add(Calendar.DAY_OF_YEAR, -1);
//        String date = DateFormatUtil.formatDate(Const.YYYY_MM_DD, instance.getTime());
//        scheduleService.sameOddsMatchCompute(date);
    }

}
