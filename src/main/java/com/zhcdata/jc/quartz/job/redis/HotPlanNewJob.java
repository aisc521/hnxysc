package com.zhcdata.jc.quartz.job.redis;

import com.google.common.base.Strings;
import com.zhcdata.jc.dto.MatchPlanResult;
import com.zhcdata.jc.dto.PlanResult1;
import com.zhcdata.jc.dto.PlanResult3;
import com.zhcdata.jc.service.TbJcMatchService;
import com.zhcdata.jc.service.TbPlanService;
import com.zhcdata.jc.tools.CommonUtils;
import com.zhcdata.jc.tools.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springside.modules.utils.mapper.JsonMapper;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description 热门方案 新
 * @Author cuishuai
 * @Date 2019/11/8 10:11
 */
@Slf4j
@Component
public class HotPlanNewJob  implements Job {
    @Resource
    private TbPlanService tbPlanService;
    @Resource
    private TbJcMatchService tbJcMatchService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private CommonUtils commonUtils;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOGGER.info("[热门方案定时任务开启]" + df.format(new Date()));
        try {
            String re = (String) redisUtils.hget("SOCCER:HSET:EXPERTALL", "id");
            if (!Strings.isNullOrEmpty(re)) {
                re = re.replace("'", "");
                re = re.substring(0, re.length() - 1);
                String[] ids = re.split(",");
                List<PlanResult1> rm = new ArrayList<>(); //热门
                List<PlanResult1> dc = new ArrayList<>(); //单场
                List<PlanResult1> ecy = new ArrayList<>(); //2串1
                List<PlanResult1> mf = new ArrayList<>(); //免费

                for (int i = 0; i < ids.length; i++) {
                    List<PlanResult1> planList = tbPlanService.queryPlanByExpertIdNoPages(ids[i], null, null);
                    for (int k = 0; k < planList.size(); k++) {
                        PlanResult1 result1 = planList.get(k);
                        String lz = commonUtils.JsLz2(result1);
                        result1.setLz(lz);
                        result1.setzSevenDays(String.valueOf(new BigDecimal(result1.getzSevenDays()).intValue()));
                        List<MatchPlanResult> matchPlanResults = null; //tbJcMatchService.queryList(planList.get(k).getPlanId());
                        if (planList.get(k).getMatchPlanType() != null) {
                            matchPlanResults = tbJcMatchService.queryList(planList.get(k).getPlanId(), planList.get(k).getMatchPlanType());
                        }
                        if (matchPlanResults != null && matchPlanResults.size() > 0) {
                            result1.setList(matchPlanResults);
                        }

                        if (matchPlanResults.size() == 1) {
                            dc.add(result1);
                        } else if (matchPlanResults.size() == 2) {
                            ecy.add(result1);
                        }

                        if (result1.getPlanType().equals("3")) {
                            mf.add(result1);
                        }
                        rm.add(result1);
                    }
                }
                dealPage(rm, "");
                dealPage(ecy, "ecy");
                dealPage(mf, "mf");
                dealPage(dc, "dc");
            } else {
                LOGGER.info("无专家排行信息");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("处理热门方案异常:" + e);
        }
    }

    private void dealPage(List<PlanResult1> result1s, String t) {
        List<PlanResult1> resultNew = new ArrayList<>();
        int p = 1;
        int pc = result1s.size() / 20;
        if (result1s.size() % 20 > 0) {
            pc += 1;
        }
        for (int j = 0; j < result1s.size(); j++) {
            if (result1s.size() < 20) {
                //小于20，直接存
                Map<String, Object> map = new HashMap<String, Object>();
                PlanResult3 planResult3 = new PlanResult3();
                planResult3.setBusiCode("");
                planResult3.setResCode("000000");
                planResult3.setMessage("成功");
                planResult3.setPageNo("1");
                planResult3.setPageTotal("1");
                planResult3.setList(result1s);
                redisUtils.hset("SOCCER:HSET:PLANHOT" + t, String.valueOf(1), JsonMapper.defaultMapper().toJson(planResult3));
                break;
            } else {
                if ((j + 1) % 20 > 0) {
                    resultNew.add(result1s.get(j));

                    //最后一页，不满20条也要存
                    if (j == result1s.size() - 1) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        PlanResult3 planResult3 = new PlanResult3();
                        planResult3.setBusiCode("");
                        planResult3.setResCode("000000");
                        planResult3.setMessage("成功");
                        planResult3.setPageNo(String.valueOf(p));
                        planResult3.setPageTotal(String.valueOf(pc));
                        planResult3.setList(resultNew);
                        redisUtils.hset("SOCCER:HSET:PLANHOT" + t, String.valueOf(p), JsonMapper.defaultMapper().toJson(planResult3));
                    }
                } else {
                    //20条
                    resultNew.add(result1s.get(j));
                    PlanResult3 planResult3 = new PlanResult3();
                    planResult3.setBusiCode("");
                    planResult3.setResCode("000000");
                    planResult3.setMessage("成功");
                    planResult3.setPageNo(String.valueOf(p));
                    planResult3.setPageTotal(String.valueOf(pc));
                    planResult3.setList(resultNew);
                    redisUtils.hset("SOCCER:HSET:PLANHOT" + t, String.valueOf(p), JsonMapper.defaultMapper().toJson(planResult3));
                    p++;
                    resultNew = new ArrayList<>();
                }
            }
        }

        if (result1s.size() == 0) {
            redisUtils.hset("SOCCER:HSET:PLANHOT" + t, String.valueOf(1), JsonMapper.defaultMapper().toJson(new ArrayList<>()));
        }
        //放入缓存，取值即可
        LOGGER.info("[热门方案(" + t + ")定时任务结束]共" + result1s.size() + df.format(new Date()));
    }
}
