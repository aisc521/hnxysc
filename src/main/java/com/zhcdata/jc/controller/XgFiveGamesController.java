package com.zhcdata.jc.controller;

import com.zhcdata.db.model.JcMatchBjdcPl;
import com.zhcdata.jc.dto.JsonResult;
import com.zhcdata.jc.service.JcMatchBjdcPlService;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description TODO
 * @Author cuishuai
 * @Date 2019/10/27 17:05
 */
@Controller
public class XgFiveGamesController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private JcMatchBjdcPlService jcMatchBjdcPlService;

    @GetMapping("/five/change")
    @ResponseBody
    public void change() {
        try {
            List<JcMatchBjdcPl> jcMatchBjdcPls = jcMatchBjdcPlService.queryJcMatchBdPlByLottery();
            Integer ccount1 = 0;
            Integer count2  = 0;
            for(int i = 0; i < jcMatchBjdcPls.size(); i++){
                JcMatchBjdcPl jcMatchBjdcPl = jcMatchBjdcPls.get(i);
                if("15".equals(jcMatchBjdcPl.getLotteryPlay())){
                    String rate = jcMatchBjdcPl.getRateResult();
                    String[] rateArr = rate.split("\\|");

                    if(rateArr.length == 5){
                        String rateStr = rateArr[2];
                        int j = rateStr.indexOf("胜", rateStr.indexOf("胜")+1);
                        String qian = rateStr.substring(0, j);
                        String hou = rateStr.substring(j,rateStr.length() -1);
                        String result = rateArr[0] + "|" + rateArr[1] + "|" + qian + "|" + hou + "|" + rateArr[3] + "|" + rateArr[4];
                        System.out.println(result);
                        jcMatchBjdcPl.setRateResult(result);
                        jcMatchBjdcPlService.upMatchBdRate(jcMatchBjdcPl);
                        ccount1 ++;
                    }
                }
                if("12".equals(jcMatchBjdcPl.getLotteryPlay())){
                    String rate = jcMatchBjdcPl.getRateResult();
                    String[] rateArr = rate.split("\\|");
                    if(rateArr.length == 19){
                        String rateStr = rateArr[14].replaceAll("：",":");
                        System.out.println(rateStr);
                        String result = rateArr[0] + "|" +
                                rateArr[1] + "|" +
                                rateArr[2] + "|" +
                                rateArr[3] + "|" +
                                rateArr[4] + "|" +
                                rateArr[5] + "|" +
                                rateArr[6] + "|" +
                                rateArr[7] + "|" +
                                rateArr[8] + "|" +
                                rateArr[9] + "|" +
                                rateArr[10] + "|" +
                                rateArr[11] + "|" +
                                rateArr[12] + "|" +
                                rateArr[13] + "|" +
                                rateStr + "|" +
                                rateArr[15] + "|" +
                                rateArr[16] + "|" +
                                rateArr[17] + "|" +
                                rateArr[18] + "|" +
                                rateArr[19] + "|" +
                                rateArr[20];
                        System.out.println(result);
                        jcMatchBjdcPl.setRateResult(result);
                        jcMatchBjdcPlService.upMatchBdRate(jcMatchBjdcPl);
                        count2 ++;
                    }

                }
            }
            LOGGER.info("胜平负==============================" + ccount1);
            LOGGER.info("比分==============================" + count2);
            LOGGER.info("重新计算SP完成==============================");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
