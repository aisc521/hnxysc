package com.zhcdata.jc.quartz.job.redis;

import com.zhcdata.jc.dto.ExpertHotResult;
import com.zhcdata.jc.dto.ExpertInfo;
import com.zhcdata.jc.service.TbJcExpertService;
import com.zhcdata.jc.service.TbPlanService;
import com.zhcdata.jc.tools.RedisUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springside.modules.utils.mapper.JsonMapper;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class ExpertJob implements Job {

    @Resource
    private TbJcExpertService tbJcExpertService;
    @Resource
    private TbPlanService tbPlanService;
    @Resource
    private RedisUtils redisUtils;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //处理热门专家
    @Async
    //@Scheduled(cron = "1 1/2 * * * *")
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("[热门专家定时任务开启]" + df.format(new Date()));
        try {
            List<ExpertHotResult> list = new ArrayList<>();

            String expertId = ""; //专家Id集合

            //1、4连红及以上
            List<ExpertInfo> list1 = tbJcExpertService.queryHotExperts("1");
            for (int i = 0; i < list1.size(); i++) {
                ExpertHotResult hot = new ExpertHotResult();
                hot.setId(list1.get(i).getId());
                hot.setNickName(list1.get(i).getNickName());
                hot.setLz(list1.get(i).getLzNow());
                hot.setPushed(getPlanCount(list1.get(i).getId()));
                hot.setType("1");
                hot.setLabel(list1.get(i).getLable());
                hot.setImg(list1.get(i).getImg());
                hot.setGrade(list1.get(i).getGrade());
                list.add(hot);
                expertId += "'" + list1.get(i).getId() + "',";
            }

            //2、5中4，6中5，7中6，8中7，9中8，10中9
            List<ExpertInfo> list2 = tbJcExpertService.queryHotExperts("2");
            for (int i = 0; i < list2.size(); i++) {
                if (expertId.contains("'" + list2.get(i).getId() + "'")) {
                    //如果其它条件已选中该专家，则跳过
                    continue;
                }
                ExpertHotResult hot = new ExpertHotResult();
                hot.setId(list2.get(i).getId());
                hot.setNickName(list2.get(i).getNickName());
                if (list2.get(i).getFiveZ() != null && list2.get(i).getFiveZ().equals("4")) {
                    hot.setLz("5中4");
                } else if (list2.get(i).getSixZ() != null && list2.get(i).getSixZ().equals("5")) {
                    hot.setLz("6中5");
                } else if (list2.get(i).getSevenZ() != null && list2.get(i).getSevenZ().equals("6")) {
                    hot.setLz("7中6");
                } else if (list2.get(i).getSevenZ() != null && list2.get(i).getSevenZ().equals("7")) {
                    hot.setLz("8中7");
                } else if (list2.get(i).getNineZ() != null && list2.get(i).getNineZ().equals("8")) {
                    hot.setLz("9中8");
                } else if (list2.get(i).getTenZ() != null && list2.get(i).getTenZ().equals("9")) {
                    hot.setLz("10中9");
                }
                hot.setType("2");
                hot.setPushed(getPlanCount(list2.get(i).getId()));
                hot.setLabel(list2.get(i).getLable());
                hot.setImg(list2.get(i).getImg());
                hot.setGrade(list2.get(i).getGrade());
                list.add(hot);
                expertId += "'" + list2.get(i).getId() + "',";
            }

            //3、回报率100％以上（近7天）
            List<ExpertInfo> list3 = tbJcExpertService.queryHotExperts("3");
            for (int i = 0; i < list3.size(); i++) {
                if (expertId.contains("'" + list3.get(i).getId() + "'")) {
                    //如果其它条件已选中该专家，则跳过
                    continue;
                }
                ExpertHotResult hot = new ExpertHotResult();
                hot.setId(list3.get(i).getId());
                hot.setNickName(list3.get(i).getNickName());
                hot.setLz(list3.get(i).getReturnSevenDays() + "%");
                hot.setPushed(getPlanCount(list3.get(i).getId()));
                hot.setLabel(list3.get(i).getLable());
                hot.setImg(list3.get(i).getImg());
                hot.setGrade(list3.get(i).getGrade());
                hot.setType("2");
                list.add(hot);
                expertId += "'" + list3.get(i).getId() + "',";
            }

            //4、7中5，8中6，9中7，10中8
            List<ExpertInfo> list4 = tbJcExpertService.queryHotExperts("4");
            for (int i = 0; i < list4.size(); i++) {
                if (expertId.contains("'" + list4.get(i).getId() + "'")) {
                    //如果其它条件已选中该专家，则跳过
                    continue;
                }
                ExpertHotResult hot = new ExpertHotResult();
                hot.setId(list4.get(i).getId());
                hot.setNickName(list4.get(i).getNickName());
                if (list4.get(i).getSevenZ() != null && list4.get(i).getSevenZ().equals("5")) {
                    hot.setLz("7中5");
                } else if (list4.get(i).getEightZ() != null && list4.get(i).getEightZ().equals("6")) {
                    hot.setLz("8中6");
                } else if (list4.get(i).getNineZ() != null && list4.get(i).getNineZ().equals("7")) {
                    hot.setLz("9中7");
                } else if (list4.get(i).getTenZ() != null && list4.get(i).getTenZ().equals("8")) {
                    hot.setLz("10中8");
                }

                hot.setType("2");
                hot.setPushed(getPlanCount(list4.get(i).getId()));
                hot.setImg(list4.get(i).getImg());
                hot.setGrade(list4.get(i).getGrade());
                list.add(hot);
                expertId += "'" + list4.get(i).getId() + "',";
            }

            //5、10中7，10中6，10中5
            List<ExpertInfo> list5 = tbJcExpertService.queryHotExperts("5");
            for (int i = 0; i < list5.size(); i++) {
                if (expertId.contains("'" + list5.get(i).getId() + "'")) {
                    //如果其它条件已选中该专家，则跳过
                    continue;
                }
                ExpertHotResult hot = new ExpertHotResult();
                hot.setId(list5.get(i).getId());
                hot.setNickName(list5.get(i).getNickName());
                if (list5.get(i).getTenZ() != null && list5.get(i).getTenZ().equals("7")) {
                    hot.setLz("10中7");
                } else if (list5.get(i).getTenZ() != null && list5.get(i).getTenZ().equals("6")) {
                    hot.setLz("10中6");
                } else if (list5.get(i).getTenZ() != null && list5.get(i).getTenZ().equals("5")) {
                    hot.setLz("10中5");
                }
                hot.setType("2");
                hot.setPushed(getPlanCount(list5.get(i).getId()));
                hot.setLabel(list5.get(i).getLable());
                hot.setImg(list5.get(i).getImg());
                hot.setGrade(list5.get(i).getGrade());
                list.add(hot);
                expertId += "'" + list5.get(i).getId() + "',";
            }

            //6、2连红和3连红
            List<ExpertInfo> list6 = tbJcExpertService.queryHotExperts("6");
            for (int i = 0; i < list6.size(); i++) {
                if (expertId.contains("'" + list6.get(i).getId() + "'")) {
                    //如果其它条件已选中该专家，则跳过
                    continue;
                }
                ExpertHotResult hot = new ExpertHotResult();
                hot.setId(list6.get(i).getId());
                hot.setNickName(list6.get(i).getNickName());
                hot.setLz(list6.get(i).getLzNow());
                hot.setPushed(getPlanCount(list6.get(i).getId()));
                hot.setImg(list6.get(i).getImg());
                hot.setGrade(list6.get(i).getGrade());
                hot.setLabel(list6.get(i).getLable());
                hot.setType("1");
                list.add(hot);
                expertId += "'" + list6.get(i).getId() + "',";
            }

            //7、3中2，4中3
            List<ExpertInfo> list7 = tbJcExpertService.queryHotExperts("7");
            for (int i = 0; i < list7.size(); i++) {
                if (expertId.contains("'" + list7.get(i).getId() + "'")) {
                    //如果其它条件已选中该专家，则跳过
                    continue;
                }
                ExpertHotResult hot = new ExpertHotResult();
                hot.setId(list7.get(i).getId());
                hot.setNickName(list7.get(i).getNickName());
                if (list7.get(i).getThreeZ() != null && list7.get(i).getThreeZ().equals("2")) {
                    hot.setLz("3中2");
                } else if (list7.get(i).getFourZ() != null && list7.get(i).getFourZ().equals("3")) {
                    hot.setLz("4中3");
                }
                hot.setType("2");
                hot.setPushed(getPlanCount(list7.get(i).getId()));
                hot.setLabel(list7.get(i).getLable());
                hot.setImg(list7.get(i).getImg());
                hot.setGrade(list7.get(i).getGrade());
                list.add(hot);
                expertId += "'" + list7.get(i).getId() + "',";
            }

            //8、历史8连红及以上
            List<ExpertInfo> list8 = tbJcExpertService.queryHotExperts("8");
            for (int i = 0; i < list8.size(); i++) {
                if (expertId.contains("'" + list8.get(i).getId() + "'")) {
                    //如果其它条件已选中该专家，则跳过
                    continue;
                }
                ExpertHotResult hot = new ExpertHotResult();
                hot.setId(list8.get(i).getId());
                hot.setNickName(list8.get(i).getNickName());
                hot.setLz("8");
                hot.setPushed(getPlanCount(list8.get(i).getId()));
                hot.setLabel(list8.get(i).getLable());
                hot.setImg(list8.get(i).getImg());
                hot.setGrade(list8.get(i).getGrade());
                hot.setType("1");
                list.add(hot);
                expertId += "'" + list8.get(i).getId() + "',";
            }

            LOGGER.info("[热门专家定时任务结束]" + df.format(new Date()));
            LOGGER.info("[4连红及以上]" + list1.size()+"个");
            LOGGER.info("[回报率100％以上（近7天）]" + list2.size()+"个");
            LOGGER.info("[7中5，8中6，9中7，10中8]" + list3.size()+"个");
            LOGGER.info("[10中7，10中6，10中5]" + list4.size()+"个");
            LOGGER.info("[2连红和3连红]" + list5.size()+"个");
            LOGGER.info("[3中2,4中3]" + list6.size()+"个");
            LOGGER.info("[历史8连红及以上]" + list7.size()+"个");
            LOGGER.info("[专家ID]" + expertId);
            redisUtils.hset("SOCCER:HSET:EXPERT", "id", expertId);
            redisUtils.hset("SOCCER:HSET:EXPERT", "hot", JsonMapper.defaultMapper().toJson(list));
        } catch (Exception ex) {
            System.out.println(ex);
            LOGGER.error("热门专家定时任务异常：" , ex);
        }
    }

    private String getPlanCount(int expertId){
        return String.valueOf(tbPlanService.queryPlanCountByExpertId(String.valueOf(expertId)));
    }
}
