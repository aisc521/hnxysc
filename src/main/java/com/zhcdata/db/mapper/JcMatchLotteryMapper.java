package com.zhcdata.db.mapper;

import com.zhcdata.db.model.JcMatchLottery;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import javax.annotation.Priority;

public interface JcMatchLotteryMapper extends Mapper<JcMatchLottery> {
    JcMatchLottery queryJcMatchLotteryByBet007(@Param("bet007Id") long betoo7,@Param("gameType") String gameType);

    JcMatchLottery queryJcMatchLotteryByIssueNumAndNoId(@Param("issueNum") String issueNum, @Param("noId") String noId);
}