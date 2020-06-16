package com.zhcdata.db.mapper;

import com.zhcdata.db.model.JcMatchLottery;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import javax.annotation.Priority;
import java.util.List;

public interface JcMatchLotteryMapper extends Mapper<JcMatchLottery> {
    JcMatchLottery queryJcMatchLotteryByBet007(@Param("bet007Id") long betoo7,@Param("gameType") String gameType);

    JcMatchLottery queryJcMatchLotteryByBet007_1(@Param("num") long num,@Param("lottery") String lottery,@Param("id") String id);

    JcMatchLottery queryJcMatchLotteryByIssueNumAndNoId(@Param("issueNum") String issueNum, @Param("noId") String noId);

    List<JcMatchLottery> queryJcMatchLotteryByMatchIdAndType(@Param("matchId")Integer matchId);

    List<JcMatchLottery> queryJcMatch(@Param("startTime")String startTime,@Param("endTime")String endTime);

    int updateByMatchId(@Param("matchId")Long matchId,@Param("show")int show);
}