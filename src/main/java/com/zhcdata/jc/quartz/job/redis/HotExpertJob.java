package com.zhcdata.jc.quartz.job.redis;

import com.zhcdata.jc.dto.ExpertHotResult;
import com.zhcdata.jc.dto.HotExpertDto;
import com.zhcdata.jc.service.TbJcExpertService;
import com.zhcdata.jc.service.TbPlanService;
import com.zhcdata.jc.tools.CommonUtils;
import com.zhcdata.jc.tools.RedisUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springside.modules.utils.mapper.JsonMapper;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description 热门专家  和全部 专家 redis
 * @Author cuishuai
 * @Date 2019/11/8 9:02
 */
@Configuration
@EnableScheduling
public class HotExpertJob  implements Job {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    private RedisUtils redisUtils;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Resource
    private TbPlanService tbPlanService;
    @Resource
    private TbJcExpertService tbJcExpertService;
    @Resource
    private CommonUtils commonUtils;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOGGER.info("[热门专家定时任务开启]" + df.format(new Date()));
        try{
            List<ExpertHotResult> hotList = new ArrayList<>();
            String expertId = ""; //专家Id集合

            //热门专家
            List<HotExpertDto> hotExpertDtosSeven = tbJcExpertService.queryExpertHotInfoOrderLimitSeven();
            for(int i = 0; i < hotExpertDtosSeven.size(); i++){
                ExpertHotResult hot = new ExpertHotResult();
                HotExpertDto hotExpertDto = hotExpertDtosSeven.get(i);
                hot.setId(Integer.parseInt(String.valueOf(hotExpertDto.getId())));
                hot.setNickName(hotExpertDto.getNickName());
                hot.setPushed(getPlanCount(Integer.parseInt(String.valueOf(hotExpertDto.getId()))));
                hot.setLabel(hotExpertDto.getLable());
                hot.setImg(hotExpertDto.getImg());
                hot.setGrade(String.valueOf(hotExpertDto.getGrade()));
                commonUtils.hotExpertLzType(hotExpertDto,hot);
                hotList.add(hot);
                expertId += "'" + hotExpertDto.getId() + "',";
            }
            //将热门专家放入缓存
            redisUtils.hset("SOCCER:HSET:EXPERT", "id", expertId);
            redisUtils.hset("SOCCER:HSET:EXPERT", "hot", JsonMapper.defaultMapper().toJson(hotList));
            //全部专家
            List<ExpertHotResult> hotListAll = new ArrayList<>();
            String expertIdAll = ""; //专家Id集合

            List<HotExpertDto> hotExpertDtos = tbJcExpertService.queryExpertHotInfoOrder();
            for(int j = 0; j < hotExpertDtos.size(); j++ ){
                ExpertHotResult hot = new ExpertHotResult();
                HotExpertDto hotExpertDto = hotExpertDtos.get(j);
                hot.setId(Integer.parseInt(String.valueOf(hotExpertDto.getId())));
                hot.setNickName(hotExpertDto.getNickName());
                hot.setPushed(getPlanCount(Integer.parseInt(String.valueOf(hotExpertDto.getId()))));
                hot.setLabel(hotExpertDto.getLable());
                hot.setImg(hotExpertDto.getImg());
                commonUtils.hotExpertLzType(hotExpertDto,hot);
                hot.setGrade(String.valueOf(hotExpertDto.getGrade()));
                hotListAll.add(hot);
                expertIdAll += "'" + hotExpertDto.getId() + "',";
            }
            //将全部专家排序放入缓存
            redisUtils.hset("SOCCER:HSET:EXPERTALL", "id", expertIdAll);
            redisUtils.hset("SOCCER:HSET:EXPERTALL", "hot", JsonMapper.defaultMapper().toJson(hotListAll));

        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error("热门专家定时任务异常：" , e);
        }
    }
    private String getPlanCount(int expertId){
        return String.valueOf(tbPlanService.queryPlanCountByExpertId(String.valueOf(expertId)));
    }
}
