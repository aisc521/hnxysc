package com.zhcdata.jc.quartz.job.redis;

import com.zhcdata.db.model.TbJcPlan;
import com.zhcdata.jc.dto.MatchPlanResult;
import com.zhcdata.jc.dto.SPFListDto;
import com.zhcdata.jc.dto.TbSPFInfo;
import com.zhcdata.jc.dto.TbScoreResult;
import com.zhcdata.jc.service.TbJcMatchService;
import com.zhcdata.jc.service.TbPlanService;
import com.zhcdata.jc.tools.RedisUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description 计算方案 是否命中
 * @Author cuishuai
 * @Date 2019/9/20 14:27
 */
public class CalculationPlanJob implements Job {
    @Resource
    private RedisUtils redisUtils;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private TbPlanService tbPlanService;

    @Resource
    private TbJcMatchService tbJcMatchService;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            LOGGER.info("[计算方案是否命中定时任务开启]" + df.format(new Date()));
            List<TbJcPlan> planResults = tbPlanService.queryPlanList(null, "1"); //正在进行的方案列表
            if (planResults != null && planResults.size() > 0) {
                for (int i = 0; i < planResults.size(); i++) {

                    int result = 1;     //标识该方案是否中出,有一场未中,就是不中
                    int z_count = 0;      //已中方案数量 例如推3中1,推3中2
                    List<MatchPlanResult> matchPlanResults = tbJcMatchService.queryList(String.valueOf(planResults.get(i).getId())); //该方案的赛事信息
                    if (matchPlanResults != null && matchPlanResults.size() > 0) {
                        for (int k = 0; k < matchPlanResults.size(); k++) {
                            String planInfo = matchPlanResults.get(k).getPlanInfo();

                            List<TbScoreResult> scoreResults = tbPlanService.queryScore(matchPlanResults.get(k).getMatchId()); //该赛事得分信息
                            if (scoreResults != null && scoreResults.size() > 0) {
                                if (scoreResults.get(0).getStatusType().equals("finished")) {
                                    //该赛事已结束，计算方案
                                    int hScore = Integer.valueOf(scoreResults.get(0).getValue());
                                    int vScore = Integer.valueOf(scoreResults.get(1).getValue());
                                    int z = 0; //胜平负和让球胜平负,有一个中了,就算中

                                    String spf = planInfo.split("\\|")[0];      //胜平负
                                    String rqspf = planInfo.split("\\|")[1];    //让球胜平负
                                    if (!spf.equals("0,0,0")) {
                                        if (!spf.split(",")[0].equals("0")) {
                                            //买胜
                                            if (hScore > vScore) {
                                                z = 1;
                                            }
                                        }

                                        if (!spf.split(",")[1].equals("0")) {
                                            //买平
                                            if (hScore == vScore) {
                                                z = 1;
                                            }
                                        }

                                        if (!spf.split(",")[2].equals("0")) {
                                            //买负
                                            if (hScore < vScore) {
                                                z = 1;
                                            }
                                        }
                                    } else {
                                        SPFListDto spfs = tbPlanService.querySPFList(matchPlanResults.get(k).getMatchId());
                                        if (spfs != null) {
                                            hScore = hScore + Integer.parseInt(spfs.getAwayTeamRangballs());
                                            if (!rqspf.split(",")[0].equals("0")) {
                                                //买胜
                                                if (hScore > vScore) {
                                                    z = 1;
                                                }
                                            }

                                            if (!rqspf.split(",")[1].equals("0")) {
                                                //买平
                                                if (hScore == vScore) {
                                                    z = 1;
                                                }
                                            }

                                            if (!rqspf.split(",")[2].equals("0")) {
                                                //买负
                                                if (hScore < vScore) {
                                                    z = 1;
                                                }
                                            }
                                        }
                                    }

                                    if (z == 0) {
                                        //胜平负,都没中
                                        result = 0;
                                    } else {
                                        //推荐比赛，中一场+1
                                        z_count += 1;
                                    }

                                    //处理单场比赛结果、中奖状态
                                    tbJcMatchService.updateStatus(String.valueOf(z), hScore + ":" + vScore);

                                }
                                //未结束的不处理
                            }
                        }

                        if (result == 0) {
                            //未中
                            tbPlanService.updateStatus("0", matchPlanResults.size() + "中" + z_count, String.valueOf(planResults.get(i).getId()));
                        } else {
                            //已中
                            tbPlanService.updateStatus("1", matchPlanResults.size() + "中" + z_count, String.valueOf(planResults.get(i).getId()));
                        }
                    }
                }
            }
            LOGGER.info("[计算方案是否命中定时任务结束]" + df.format(new Date()) + " 共：" + planResults.size() + "个方案");

        } catch (Exception ex) {
            LOGGER.error("计算方案是否命中定时任务异常：" + ex);
        }
    }
}
