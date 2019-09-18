package com.zhcdata.db.mapper;

import com.zhcdata.db.model.ScoreInfo;
import com.zhcdata.jc.dto.IntegralRankingDto;
import com.zhcdata.jc.tools.CustomInterfaceMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbScoreMapper extends CustomInterfaceMapper<ScoreInfo> {
    List<ScoreInfo> queryScore(@Param("SclassID") String SclassID,@Param("subSclassID") String subSclassID,@Param("Homeorguest") String Homeorguest,@Param("TeamID") String TeamID,@Param("Season") String Season);

    int insertSelective(ScoreInfo scoreInfo);

    int updateByPrimaryKeySelective(ScoreInfo scoreInfo);

    /**
     * 查询积分排名数据
     * @param sclassid 赛事类型id
     * @param subSclassID 子联赛id
     * @param homeorguest 主客队数据 1 主场，0 客场 否则为总数据
     * @param teamId 队伍id
     * @param season 赛季
     * @return
     */
    IntegralRankingDto queryIntegralRanking(@Param("sclassId") int sclassid,@Param("subSclassID") Integer subSclassID,@Param("homeorguest") Integer homeorguest,@Param("teamid") Integer teamId,@Param("season") String season);
}
