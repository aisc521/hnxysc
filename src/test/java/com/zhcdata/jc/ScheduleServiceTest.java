package com.zhcdata.jc;

import com.google.gson.Gson;
import com.zhcdata.jc.service.ScheduleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleServiceTest {

    @Resource
    ScheduleService scheduleService;

    @Test
    public void queryLineupDataByMatch() {
        Gson gson = new Gson();
        Map<String, Object> map = scheduleService.queryLineupDataByMatch(1721166L);
        System.out.println(gson.toJson(map));
    }
//    @Test
//    public void queryMatchAnalysis() {
//        scheduleService.updateMatchAnalysis(1,1);
//    }

}
