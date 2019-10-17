package com.zhcdata.jc.quartz.job.redis;

import com.google.common.base.Strings;
import com.zhcdata.jc.dto.MatchPlanResult;
import com.zhcdata.jc.dto.PlanResult1;
import com.zhcdata.jc.service.TbJcMatchService;
import com.zhcdata.jc.service.TbPlanService;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description 热门方案缓存信息
 * @Author cuishuai
 * @Date 2019/9/20 14:21
 */
@Slf4j
@Component
public class HotPlanJob implements Job {

    @Resource
    private TbPlanService tbPlanService;
    @Resource
    private TbJcMatchService tbJcMatchService;

    @Resource
    private RedisUtils redisUtils;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            LOGGER.info("[热门方案定时任务开启]" + df.format(new Date()));
            String re = (String)redisUtils.hget("SOCCER:HSET:EXPERT", "id");
            if (!Strings.isNullOrEmpty(re)) {
                re = re.replace("'", "");
                re = re.substring(0, re.length() - 1);
                String[] ids = re.split(",");

                List<PlanResult1> result = new ArrayList<>();

                for (int i = 0; i < ids.length; i++) {
                    List<PlanResult1> planList = tbPlanService.queryPlanByExpertId(ids[i], null,null);

                    for (int k = 0; k < planList.size(); k++) {
                        PlanResult1 result1 = planList.get(k);
                        List<MatchPlanResult> matchPlanResults = tbJcMatchService.queryList(planList.get(k).getId());
                        if (matchPlanResults != null && matchPlanResults.size() > 0) {
                            result1.setList(matchPlanResults);
                        }
                        result.add(result1);
                    }
                }

                //放入缓存，取值即可
                redisUtils.hset("SOCCER:HSET:PLAN", "hot", JsonMapper.defaultMapper().toJson(result));
                LOGGER.info("[热门方案定时任务结束]共" + result.size() + df.format(new Date()));
            }
        } catch (Exception ex) {
            LOGGER.error("处理热门方案异常:" + ex);
        }
    }
}
