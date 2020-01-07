package com.zhcdata.jc.quartz.job.redis;

import com.github.pagehelper.PageInfo;
import com.zhcdata.jc.dto.ExpertInfoBdDto;
import com.zhcdata.jc.service.TbJcExpertService;
import com.zhcdata.jc.tools.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springside.modules.utils.mapper.JsonMapper;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
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
            List sevemList = sevenMz.getList();
            List newSevemList = new ArrayList();
            for(int i = 0; i < sevemList.size(); i++){
                ExpertInfoBdDto expertInfoBdDto = (ExpertInfoBdDto) sevemList.get(i);
                if(StringUtils.isNotBlank(expertInfoBdDto.getZSevenDays())){
                    expertInfoBdDto.setZSevenDays(new BigDecimal(expertInfoBdDto.getZSevenDays()).intValue() + "");
                }else{
                    expertInfoBdDto.setZSevenDays("0");
                }
                if(StringUtils.isNotBlank(expertInfoBdDto.getReturnSevenDays())){
                    expertInfoBdDto.setReturnSevenDays(new BigDecimal(expertInfoBdDto.getReturnSevenDays()).intValue() + "");
                }else{
                    expertInfoBdDto.setReturnSevenDays("0");
                }
                newSevemList.add(expertInfoBdDto);
            }


            PageInfo<ExpertInfoBdDto> nowLh = tbJcExpertService.queryExpertsByType("2",Integer.valueOf(1),20);//当前连红
            List<ExpertInfoBdDto> nowLhList = nowLh.getList();
            List newNowLhList = new ArrayList();
            for(int i = 0; i < nowLhList.size(); i++){
                ExpertInfoBdDto expertInfoBdDto = (ExpertInfoBdDto) nowLhList.get(i);
                if(StringUtils.isNotBlank(expertInfoBdDto.getZSevenDays())){
                    expertInfoBdDto.setZSevenDays(new BigDecimal(expertInfoBdDto.getZSevenDays()).intValue() + "");
                }else{
                    expertInfoBdDto.setZSevenDays("0");
                }
                if(StringUtils.isNotBlank(expertInfoBdDto.getReturnSevenDays())){
                    expertInfoBdDto.setReturnSevenDays(new BigDecimal(expertInfoBdDto.getReturnSevenDays()).intValue() + "");
                }else{
                    expertInfoBdDto.setReturnSevenDays("0");
                }
                newNowLhList.add(expertInfoBdDto);
            }



            PageInfo<ExpertInfoBdDto> sevenReturn = tbJcExpertService.queryExpertsByType("3",Integer.valueOf(1),20);//七天回报率
            List<ExpertInfoBdDto> sevenReturnList = sevenReturn.getList();
            List newSevenReturnLhList = new ArrayList();
            for(int i = 0; i < sevenReturnList.size(); i++){
                ExpertInfoBdDto expertInfoBdDto = (ExpertInfoBdDto) sevenReturnList.get(i);
                if(StringUtils.isNotBlank(expertInfoBdDto.getZSevenDays())){
                    expertInfoBdDto.setZSevenDays(new BigDecimal(expertInfoBdDto.getZSevenDays()).intValue() + "");
                }else{
                    expertInfoBdDto.setZSevenDays("0");
                }
                if(StringUtils.isNotBlank(expertInfoBdDto.getReturnSevenDays())){
                    expertInfoBdDto.setReturnSevenDays(new BigDecimal(expertInfoBdDto.getReturnSevenDays()).intValue() + "");
                }else{
                    expertInfoBdDto.setReturnSevenDays("0");
                }
                newSevenReturnLhList.add(expertInfoBdDto);
            }




            redisUtils.hset("SOCCER:HSET:EXPERTRANKING", "sevenMz", JsonMapper.defaultMapper().toJson(newSevemList));
            redisUtils.hset("SOCCER:HSET:EXPERTRANKING", "nowLh", JsonMapper.defaultMapper().toJson(newNowLhList));
            redisUtils.hset("SOCCER:HSET:EXPERTRANKING", "sevenReturn", JsonMapper.defaultMapper().toJson(newSevenReturnLhList));
            if(newSevemList == null && newSevemList.size() <= 0){
                LOGGER.info("专家排行榜--七天命中率数据为空=========");
            }
            if(nowLhList == null && nowLhList.size() <= 0){
                LOGGER.info("专家排行榜--当前连红数据为空=========");
            }
            if(sevenReturnList == null && sevenReturnList.size() <= 0){
                LOGGER.info("专家排行榜--七天回报率数据为空=========");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
