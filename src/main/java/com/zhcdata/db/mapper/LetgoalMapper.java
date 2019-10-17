package com.zhcdata.db.mapper;


import com.zhcdata.db.model.Letgoal;
import com.zhcdata.db.model.Standard;
import com.zhcdata.jc.dto.AnalysisDto;
import com.zhcdata.jc.dto.AnalysisMatchDto;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface LetgoalMapper {

    int deleteByPrimaryKey(Integer oddsid);

    int insert(Letgoal record);

    int insertSelective(Letgoal record);

    Letgoal selectByPrimaryKey(Integer oddsid);

    int updateByPrimaryKeySelective(Letgoal record);

    int updateByPrimaryKey(Letgoal record);

    Letgoal selectByMatchIdAndCompany(String matchId, String cpy);

    Map<String, Object> selectOddsIdAndStartTimeByByMatchIdAndCompany(String matchId, String cpy);

    List<Letgoal> selectByMid(String mid);

    void deleteByMid(int mid);

    void updateOddsByOddsId(Integer oddsid, Float upodds, Float goal, Float downodds, Date modifytime);

    //Letgoal selectByMidAndCpyNew(String mid, String cpy);


    AnalysisDto queryHandicapsByCompanyAndMatch(@Param("matchId") Integer matchId, @Param("companyId") Integer companyId);

    List<AnalysisMatchDto> querySameHandicapsMatchByOdds(@Param("companyId")Integer companyId, @Param("matchType")Integer matchType,
                                                    @Param("beginDate") String beginDate,@Param("changeTimes") Integer changeTimes,
                                                     @Param("oddsId") Integer oddsId,@Param("satWin") Double satWin,
                                                     @Param("satLose") Double satLose,@Param("satFlat") Double satFlat);
    List<AnalysisMatchDto> querySameHandicapsMatchByChangeOdds(@Param("companyId")Integer companyId, @Param("matchType")Integer matchType,
                                                    @Param("changeOdds") List<Map<String,Double>> changeOdds,@Param("beginDate") String beginDate,
                                                    @Param("oddsId") Integer oddsId,@Param("satWin") Double satWin, @Param("satLose") Double satLose,
                                                    @Param("satFlat") Double satFlat);

    List<AnalysisMatchDto> querySameHandicapsMatchByOddsAndNoChange(@Param("companyId")Integer companyId,@Param("matchType")Integer matchType,
                                                           @Param("satGoal") Double satGoal,@Param("endGoal") Double endGoal,
                                                           @Param("beginDate") String beginDate,@Param("changeTimes") Integer changeTimes,
                                                           @Param("satSatOdd") Double satSatOdd,@Param("satEndOdd") Double satEndOdd,
                                                           @Param("endSatOdd") Double endSatOdd,@Param("endEndOdd") Double endEndOdd);

    List<AnalysisMatchDto> querySameHandicapsMatchByOddsAndChange(@Param("companyId")Integer companyId,@Param("matchType")Integer matchType,
                                                           @Param("satGoal") Double satGoal,@Param("endGoal") Double endGoal,
                                                                  @Param("changeOdds") List<Map<String,Double>> changeOdds,
                                                           @Param("beginDate") String beginDate,@Param("changeTimes") Integer changeTimes,
                                                           @Param("satSatOdd") Double satSatOdd,@Param("satEndOdd") Double satEndOdd,
                                                           @Param("endSatOdd") Double endSatOdd,@Param("endEndOdd") Double endEndOdd);
}