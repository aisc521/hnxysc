package com.zhcdata.jc.quartz.job.redis;

import com.github.pagehelper.PageInfo;
import com.zhcdata.jc.dto.ExpertInfoBdDto;
import com.zhcdata.jc.service.TbJcExpertService;
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
import java.util.List;

/**
 * @Description 专家排行榜定时任务
 * @Author cuishuai
 * @Date 2019/11/12 9:37
 */
@Slf4j
@Component
public class ExpertRankingListJob implements Job{
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private TbJcExpertService tbJcExpertService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try{
            PageInfo<ExpertInfoBdDto> sevenMz = tbJcExpertService.queryExpertsByType("1",Integer.valueOf(1),20);//七天命中率
            List<ExpertInfoBdDto> sevenMzList = sevenMz.getList();

            PageInfo<ExpertInfoBdDto> nowLh = tbJcExpertService.queryExpertsByType("2",Integer.valueOf(1),20);//当前连红
            List<ExpertInfoBdDto> nowLhList = nowLh.getList();

            PageInfo<ExpertInfoBdDto> sevenReturn = tbJcExpertService.queryExpertsByType("3",Integer.valueOf(1),20);//七天回报率
            List<ExpertInfoBdDto> sevenReturnList = sevenReturn.getList();

            if(sevenMzList != null && sevenMzList.size() > 0){
                redisUtils.hset("SOCCER:HSET:EXPERTRANKING", "sevenMz", JsonMapper.defaultMapper().toJson(sevenMzList));
            }else{
                LOGGER.info("专家排行榜--七天命中率数据为空=========");
            }
            if(nowLhList != null && nowLhList.size() > 0){
                redisUtils.hset("SOCCER:HSET:EXPERTRANKING", "nowLh", JsonMapper.defaultMapper().toJson(nowLhList));
            }else{
                LOGGER.info("专家排行榜--当前连红数据为空=========");
            }
            if(sevenReturnList != null && sevenReturnList.size() > 0){
                redisUtils.hset("SOCCER:HSET:EXPERRTANKING", "sevenReturn", JsonMapper.defaultMapper().toJson(sevenReturnList));
            }else{
                LOGGER.info("专家排行榜--七天回报率数据为空=========");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
